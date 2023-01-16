import actionforpages.ActionForBackPage;
import actionforpages.ActionForChangePage;
import actionforpages.ActionForOnePage;
import currentdata.CurrentPage;
import database.DataBase;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import datainput.*;
import filtersactions.FilterByCountry;
import outputformat.StandardOutput;
import sortactions.SortByLikes;

import java.io.IOException;
import java.util.*;

public class SolveCommands {
    /**
     *
     * @param inputData
     * @param output
     * @param objectMapper
     * @throws IOException
     */
    public static void commands(final DataBase inputData, final ArrayNode output,
                                final ObjectMapper objectMapper) throws IOException {
        CurrentPage currentPage = new CurrentPage("homepage");
        ArrayList<CurrentPage> pages = new ArrayList<CurrentPage>();
        for (int i = 0; i < inputData.getActions().size(); i++) {
            String currentCommand = inputData.getActions().get(i).getType();
            switch (currentCommand) {
                case "on page":
                    ActionForOnePage.executeAction(i, currentPage, inputData, objectMapper,
                            output, pages);
                    break;
                case "change page":
                    ActionForChangePage.executeAction(i, currentPage, inputData, objectMapper,
                            output, pages);
                    break;
                case "back":
                    ActionForBackPage.executeAction(currentPage, objectMapper, output, pages);
                    break;
                case "database":
                    DataBase.executeAction(i, currentPage, inputData, objectMapper, output, pages);
                    break;
                default:
                    break;
            }
        }

        recommendation(currentPage, inputData, objectMapper, output);

        currentPage.getCurrentUser().setUser((Users) null);
        inputData.getUsers().forEach(Users::resetUser);
        inputData.getMovies().forEach(Movies::resetMovies);
    }


    public static void recommendation(final CurrentPage currentPage, final DataBase inputData,
                                      final ObjectMapper objectMapper, final ArrayNode output) {
        if (currentPage.getCurrentUser().getUser() != null) {
            if (currentPage.getCurrentUser().getUser().getCredentials()
                    .getAccountType().equals("premium")) {
                ArrayList<Movies> acceptedMovies = FilterByCountry.acceptedCountries(
                        inputData.getMovies(), currentPage.getCurrentUser().getUser());

                SortByLikes sortByLikes = new SortByLikes();
                Sort newSort = new Sort();
                newSort.applySortStrategy(sortByLikes, acceptedMovies,
                        "decreasing");

                HashMap<String, Integer> genreLikes = new HashMap<>();
                if (currentPage.getCurrentUser().getUser().getLikedMovies() == null) {
                    currentPage.getCurrentUser().getUser().setLikedMovies(new ArrayList<Movies>());
                }

                for (int i = 0; i < currentPage.getCurrentUser().getUser()
                        .getLikedMovies().size(); i++) {
                    Movies currentMovie = currentPage.getCurrentUser().getUser()
                            .getLikedMovies().get(i);
                    for (int j = 0; j < currentMovie.getGenres().size(); j++) {
                        if (genreLikes.containsKey(currentMovie.getGenres().get(j))) {
                            genreLikes.put(currentMovie.getGenres().get(j), genreLikes.get(
                                    currentMovie.getGenres().get(j)) + 1);
                        } else {
                            genreLikes.put(currentMovie.getGenres().get(j), 1);
                        }
                    }
                }

                ArrayList<Recommendation> genreWithLikes = new ArrayList<Recommendation>();

                for (Map.Entry<String, Integer> entry: genreLikes.entrySet()) {
                    genreWithLikes.add(new Recommendation(entry.getKey(), entry.getValue()));
                }

                Collections.sort(genreWithLikes, new Comparator<Recommendation>() {
                    @Override
                    public int compare(final Recommendation o1, final Recommendation o2) {
                        if (o1.getLikes() == o2.getLikes()) {
                            return o1.getGenre().compareTo(o2.getGenre());
                        } else {
                            return Integer.compare(o2.getLikes(), o1.getLikes());
                        }
                    }
                });

                int ok = 0;
                for (int i = 0; i < genreWithLikes.size() && ok == 0; i++) {
                    for (int j = 0; j < acceptedMovies.size() && ok == 0; j++) {
                        int check = 0;
                        for (int k = 0; k < currentPage.getCurrentUser().getUser()
                                .getWatchedMovies().size() && check == 0; k++) {
                            if (currentPage.getCurrentUser().getUser().getWatchedMovies()
                                    .get(k).getName().equals(acceptedMovies.get(j).getName())) {
                                check = 1;
                            }
                        }

                        if (check == 0) {
                            if (acceptedMovies.get(j).getGenres().contains(
                                    genreWithLikes.get(i).getGenre())) {
                                ok = 1;
                                currentPage.getCurrentUser().getUser().getNotifications().add(
                                        new Notifications(acceptedMovies.get(j).getName(),
                                                "Recommendation"));
                            }
                        }
                    }
                }

                if (ok == 0) {
                    currentPage.getCurrentUser().getUser().getNotifications().add(
                            new Notifications("No recommendation",
                                    "Recommendation"));
                }

                currentPage.setName("Recommendation");
                StandardOutput.printoutput(currentPage, objectMapper, output);
            }
        }
    }
}
