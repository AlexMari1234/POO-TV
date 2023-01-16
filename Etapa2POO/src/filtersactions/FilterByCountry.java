package filtersactions;
import datainput.Movies;
import datainput.Users;

import java.util.ArrayList;

public class FilterByCountry {

    /**
     * Filters a list of Movies objects based on the country of the current user.
     * @param inputMovies the list of Movies objects to filter
     * @param currentUser the current user object containing the country of the user
     * @return a list of Movies objects that are not banned in the country of the current user
     */
    public static ArrayList<Movies> acceptedCountries(final ArrayList<Movies> inputMovies,
                                                      final Users currentUser) {
        ArrayList<Movies> acceptedMovies = new ArrayList<Movies>();
        for (int i = 0; i < inputMovies.size(); i++) {
            if (!inputMovies.get(i).getCountriesBanned().contains(currentUser
                    .getCredentials().getCountry())) {
                acceptedMovies.add(inputMovies.get(i));
            }
        }

        return acceptedMovies;
    }
}
