## Tema POO  - POO TV
### Autor Marinescu Alexandru Gabriel 322 CA

### Prezentarea aplicatiei
      In aceasta tema am avut de implementat un backend simplu al unei platforme specifice
    vizualizării de filme și seriale, precum Netflix sau HBO MAX.Am avut in vedere scrierea
    unui cod cat mai generic, in vederea posibilitatii adaugarii usoare de noi functionalitati
    pentru etapa a doua a proiectului.In aceasta etapa am creat diverse pagini pe care un utilizator
    poate naviga, precum: login, register, movies, upgrades.Pentru a accesa o anumita pagina, trebuie
    sa fim pe cea anterioara ierahic, de exemplu pentru a accesa pagina de login, trebuia sa ne aflam
    pe homepage neautentificat.In cazul in care vrem sa navigam pe o pagina incorecta, se va afisa
    la output un mesaj standard de eroare, iar in caz de succes pentru anumite pagini se vor printa
    lista de filme a utilizatorului curent si datele despre acesta.

### Implementare
      In implementare temei am avut in vedere de a respecta principiile OOP, de a crea cat mai multe
    clase cu functionalitatile lor specifice.Astfel, principalele clase pe care le-am utilizat sunt
    urmatoarele:
        - clasa pentru baza de date, in care retin datele citite din input: lista de filme, lista
    de utilizatori si lista de comenzi pe care trebuie sa le execut de-a lungul accesarii aplicatie.
    In aceasta clasa adaug utilizatori noi in sistem, verific daca un utilizator este deja inregistrat si
    modific datele legate de filme pentru fiecare utilizator existent.
        - clasa pentru utilizatori, in care modific atributele utilizatorului curent de-a lungul
    aplicatiei si verific daca un utilizator se afla in baza de date.
        - clasa pentru filme, in  care modific atributele unui film, in functie de actiunea curenta.
    De exemplu, se paote actualiza rating-ul bazat pe ratingurile date de utlizatori atunci cand
    vizioneaza filmul respectiv.
        - clasa pentru pagina curenta, in care retin numele paginii pe care ma aflu la un moment dat, lista
    de filme si utilizatorul logat.Astfel, aplicatie incepe de pe pagina de homepage cu lista de filme
    goala si fara a avea niciun utilizator logat si se actualizeaza pe parcurs, in functie de numele
    paginii pe care vrem sa navigam si actiunile care trebuiesc efectuate pe respectiva pagina
        - clasele pentru afisari de erori, respectiv output in care afisam in format jackson mesaj standard
    de eroare in cazul in care actiunea pe care dorim sa o efectuam este invalida sau afisam lista de filme
    si datele despre utilizatorul logat
        - clasele de actiuni de comenzi pentru pagina curenta sau pagina pe care dorim sa ne mutam, in care
    cream cate o metoda pentru fiecare tip de actiune(login, register, logout etc) si o rezolvam
        - clasele pentru realizarea design pattern-ului observer care se afla in pachetul observnotifications
    si care ajuta la notificarea userului despre un film adaugat sau sters.
        - clasele pentru fiecare tip de comanda din cadrul operatiei on page, unde implementez metoda comuna
    din interfata conform fiecarei actiuni in parte

### Design pattern-uri
      In realizarea aplicatiei am avut in vederea folosirea a 2 design pattern-uri: Singleton si Strategy.
    Astfel, am utilizat Singleton pentru baza de date, caci este normal sa o instantiem o singura data
    si pentru utilizatorul logat pe pagina curenta.S
      Strategy am folosit in 2 locuri: atunci cand dorim sa filtram filmele dupa un anumit criteriu, precum
    nume, actori si genuri si atunci cand dorim sa sortam filmele curente dupa rating sau durata. 
    Implementarile lor se gasesc in pachetele "filtersactions" si "sortactions", ele fiind apelate din
    pachetul "datainput" cu clasele "Filter", respectiv "Sort".
      Factory pentru tipurile de comenzi de pe pagina de change page, caci erau foarte multe si am ales sa fac
    pentru fiecare tip de comanda(login, register, purchase, watch, like, rate etc) o clasa avand o metoda care
    implementeaza metoda din interfata "Command". Astfel, in metoda solveMethod din cadrul clasei
    "ActionForOnePage" verific ce tip de comanda am, pentru a creea o instanta noua a unui obiect aferent
    clasei corespunzatoare comenzii primite, dupa care apelez metoda de executeCommand care este metoda
    comuna tuturor claselor care implementeaza interfata Command.
      Observer am folosit pentru a trimite notificari userilor din baza de date atunci cand este adaugat
    sau sters un film. Astfel, in cadrul pachetului observnotifications am creat o interfata "NotificationDataBase"
    care are o metoda de update, notificand userii prin adaugarea unei notificari cu mesajul de add/delete si 
    numele filmului. In cadrul clasei NotificationForUser am implementat metoda interfetei, care adauga
    la finalul listei de notificari a unui user o noua notificare. In NotifyUsersFromDataBase am o lista
    de useri, in care ii adaug pe toti din baza de date si cate o metoda de add/delete, unde notific
    userul curent daca s-a executat cu succes respectiva actiune.

### Flow-ul programului
      Programul incepe din Main, creand outputul pentru fisierul jackson si realizam citirea din fisierul
    de intrare, iar datele se retin in baza de date.Astfel, parcurgem toate actiunile pe rand in ordinea
    data din input si verificam la fiecare pas daca avem de executat o actiune de tipul "on page" sau 
    "change page".In functie de tipul acesteia, ne ducem pe clasa aferenta din pachetul "actionforpages"
    si rezolvam actiunea din clasa "ActionForChangePage" pentru operatia de "change page" sau "ActionForOnePage"
    pentru actiunea de "on page".In cadrul fiecarei functionalitati de rezolvat(login, register, movies etc)
    testam toate cazurile de eroare posibile afisand mesaj de eroare standard la output daca se intalneste o eroare.
    In cazul in care comanda se executa cu succes printam lista de filme si datele utilizatorului curent.
    
    