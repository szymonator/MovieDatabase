package stores;

import java.time.LocalDate;

import interfaces.IMovies;
import structures.*;

public class Movies implements IMovies{
    Stores stores;

    private MyHashTable<MovieNode> moviesTable;
    private MyHashTable<CollectionNode> collectionTable;
    private MyAVLTree<LocalDate, Integer> movieTree;

    /**
     * The constructor for the Movies data store. This is where you should
     * initialise your data structures.
     * @param stores An object storing all the different key stores,
     *               including itself
     */
    public Movies(Stores stores) {
        this.stores = stores;
        this.moviesTable = new MyHashTable<MovieNode>(1511); // 2:3 ratio of keys to spaces is best for minimising hash table collisions
        this.collectionTable = new MyHashTable<CollectionNode>(137);
        this.movieTree = new MyAVLTree<LocalDate, Integer>();
    }

    /**
     * Adds data about a film to the data structure
     * 
     * @param id               The unique ID for the film
     * @param title            The English title of the film
     * @param originalTitle    The original language title of the film
     * @param overview         An overview of the film
     * @param tagline          The tagline for the film (empty string if there is no
     *                         tagline)
     * @param status           Current status of the film
     * @param genres           An array of Genre objects related to the film
     * @param release          The release date for the film
     * @param budget           The budget of the film in US Dollars
     * @param revenue          The revenue of the film in US Dollars
     * @param languages        An array of ISO 639 language codes for the film
     * @param originalLanguage An ISO 639 language code for the original language of
     *                         the film
     * @param runtime          The runtime of the film in minutes
     * @param homepage         The URL to the homepage of the film
     * @param adult            Whether the film is an adult film
     * @param video            Whether the film is a "direct-to-video" film
     * @param poster           The unique part of the URL of the poster (empty if
     *                         the URL is not known)
     * @return TRUE if the data able to be added, FALSE otherwise
     */
    @Override
    public boolean add(int id, String title, String originalTitle, String overview, String tagline, String status, Genre[] genres, LocalDate release, long budget, long revenue, String[] languages, String originalLanguage, double runtime, String homepage, boolean adult, boolean video, String poster) {
        if (moviesTable.get(id) == null) {
            MovieNode movie = new MovieNode(id, title, originalTitle, overview, tagline, status, genres, release, budget, revenue, languages, originalLanguage, runtime, homepage, adult, video, poster);
            moviesTable.put(movie);
            if (release != null) {
                movieTree.insert(release, id);
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Removes a film from the data structure, and any data
     * added through this class related to the film
     * 
     * @param id The film ID
     * @return TRUE if the film has been removed successfully, FALSE otherwise
     */
    @Override
    public boolean remove(int id) {
        if (moviesTable.get(id) == null) {
            return false;
        }
        LocalDate date = moviesTable.get(id).getRelease();
        boolean removed = moviesTable.remove(id);
        if (!removed) {
            return false;
        }
        movieTree.remove(date, id);
        return removed;
    }

    /**
     * Gets all the IDs for all films
     * 
     * @return An array of all film IDs stored
     */
    @Override
    public int[] getAllIDs() {
        long startTime = System.nanoTime();
        
        int[] result = this.moviesTable.getAllIDs();
        
        long endTime = System.nanoTime();
        System.out.println("getAllIDs executed in: " + (endTime - startTime) / 1_000_000.0 + " ms");
        return result;
    }

    public boolean contains(int movieID) {
        return this.moviesTable.get(movieID) != null;
    }

    /**
     * Finds the film IDs of all films released within a given range. If a film is
     * released either on the start or end dates, then that film should not be
     * included
     * 
     * @param start The start point of the range of dates
     * @param end   The end point of the range of dates
     * @return An array of film IDs that were released between start and end
     */
    @Override
    public int[] getAllIDsReleasedInRange(LocalDate start, LocalDate end) {
        long startTime = System.nanoTime();

        MyLinkedList<Integer> IDsList = movieTree.getBetween(start, end);
        MyListElement<Integer> current = IDsList.getHead();
        int[] arr = new int[IDsList.getSize()];
        for (int i=0; i<IDsList.getSize(); i++) {
            arr[i] = current.getVal();
            current = current.getNext();
        }

        long endTime = System.nanoTime();
        System.out.println("getAllIDsReleasedInRange executed in: " + (endTime - startTime) / 1_000_000.0 + " ms");
        return arr;
    }


    /**
     * Gets the title of a particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The title of the requested film. If the film cannot be found, then
     *         return null
     */
    @Override
    public String getTitle(int id) {
        MovieNode mn = moviesTable.get(id);
        if (mn != null) {
            return mn.getTitle();
        } else {
            return null;
        }
    }

    /**
     * Gets the original title of a particular film, given the ID number of that
     * film
     * 
     * @param id The movie ID
     * @return The original title of the requested film. If the film cannot be
     *         found, then return null
     */
    @Override
    public String getOriginalTitle(int id) {
        MovieNode mn = moviesTable.get(id);
        if (mn != null) {
            return mn.getOriginalTitle();
        } else {
            return null;
        }
    }

    /**
     * Gets the overview of a particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The overview of the requested film. If the film cannot be found, then
     *         return null
     */
    @Override
    public String getOverview(int id) {
        MovieNode mn = moviesTable.get(id);
        if (mn != null) {
            return mn.getOverview();
        } else {
            return null;
        }
    }

    /**
     * Gets the tagline of a particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The tagline of the requested film. If the film cannot be found, then
     *         return null
     */
    @Override
    public String getTagline(int id) {
        MovieNode mn = moviesTable.get(id);
        if (mn != null) {
            return mn.getTagline();
        } else {
            return null;
        }
    }

    /**
     * Gets the status of a particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The status of the requested film. If the film cannot be found, then
     *         return null
     */
    @Override
    public String getStatus(int id) {
        MovieNode mn = moviesTable.get(id);
        if (mn != null) {
            return mn.getStatus();
        } else {
            return null;
        }
    }

    /**
     * Gets the genres of a particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The genres of the requested film. If the film cannot be found, then
     *         return null
     */
    @Override
    public Genre[] getGenres(int id) {
        MovieNode mn = moviesTable.get(id);
        if (mn != null) {
            return mn.getGenres();
        } else {
            return null;
        }
    }

    /**
     * Gets the release date of a particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The release date of the requested film. If the film cannot be found,
     *         then return null
     */
    @Override
    public LocalDate getRelease(int id) {
        MovieNode mn = moviesTable.get(id);
        if (mn != null) {
            return mn.getRelease();
        } else {
            return null;
        }
    }

    /**
     * Gets the budget of a particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The budget of the requested film. If the film cannot be found, then
     *         return -1
     */
    @Override
    public long getBudget(int id) {
        MovieNode mn = moviesTable.get(id);
        if (mn != null) {
            return mn.getBudget();
        } else {
            return -1;
        }
    }

    /**
     * Gets the revenue of a particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The revenue of the requested film. If the film cannot be found, then
     *         return -1
     */
    @Override
    public long getRevenue(int id) {
        MovieNode mn = moviesTable.get(id);
        if (mn != null) {
            return mn.getRevenue();
        } else {
            return -1;
        }
    }

    /**
     * Gets the languages of a particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The languages of the requested film. If the film cannot be found,
     *         then return null
     */
    @Override
    public String[] getLanguages(int id) {
        MovieNode mn = moviesTable.get(id);
        if (mn != null) {
            return mn.getLanguages();
        } else {
            return null;
        }
    }

    /**
     * Gets the original language of a particular film, given the ID number of that
     * film
     * 
     * @param id The movie ID
     * @return The original language of the requested film. If the film cannot be
     *         found, then return null
     */
    @Override
    public String getOriginalLanguage(int id) {
        MovieNode mn = moviesTable.get(id);
        if (mn != null) {
            return mn.getOriginalLanguage();
        } else {
            return null;
        }
    }

    /**
     * Gets the runtime of a particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The runtime of the requested film. If the film cannot be found, then
     *         return -1.0d
     */
    @Override
    public double getRuntime(int id) {
        MovieNode mn = moviesTable.get(id);
        if (mn != null) {
            return mn.getRuntime();
        } else {
            return -1.0d;
        }
    }

    /**
     * Gets the homepage of a particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The homepage of the requested film. If the film cannot be found, then
     *         return null
     */
    @Override
    public String getHomepage(int id) {
        MovieNode mn = moviesTable.get(id);
        if (mn != null) {
            return mn.getHomepage();
        } else {
            return null;
        }
    }

    /**
     * Gets weather a particular film is classed as "adult", given the ID number of
     * that film
     * 
     * @param id The movie ID
     * @return The "adult" status of the requested film. If the film cannot be
     *         found, then return false
     */
    @Override
    public boolean getAdult(int id) {
        MovieNode mn = moviesTable.get(id);
        if (mn != null) {
            return mn.getAdult();
        } else {
            return false;
        }
    }

    /**
     * Gets weather a particular film is classed as "direct-to-video", given the ID
     * number of that film
     * 
     * @param id The movie ID
     * @return The "direct-to-video" status of the requested film. If the film
     *         cannot be found, then return false
     */
    @Override
    public boolean getVideo(int id) {
        MovieNode mn = moviesTable.get(id);
        if (mn != null) {
            return mn.getVideo();
        } else {
            return false;
        }
    }

    /**
     * Gets the poster URL of a particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The poster URL of the requested film. If the film cannot be found,
     *         then return null
     */
    @Override
    public String getPoster(int id) {
        MovieNode mn = moviesTable.get(id);
        if (mn != null) {
            return mn.getPoster();
        } else {
            return null;
        }
    }

    /**
     * Sets the average IMDb score and the number of reviews used to generate this
     * score, for a particular film
     * 
     * @param id          The movie ID
     * @param voteAverage The average score on IMDb for the film
     * @param voteCount   The number of reviews on IMDb that were used to generate
     *                    the average score for the film
     * @return TRUE if the data able to be added, FALSE otherwise
     */
    @Override
    public boolean setVote(int id, double voteAverage, int voteCount) {
        MovieNode mn = moviesTable.get(id);
        if (mn != null) {
            mn.setVoteCount(voteCount);
            mn.setVoteAverage(voteAverage);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Gets the average score for IMDb reviews of a particular film, given the ID
     * number of that film
     * 
     * @param id The movie ID
     * @return The average score for IMDb reviews of the requested film. If the film
     *         cannot be found, then return -1.0d
     */
    @Override
    public double getVoteAverage(int id) {
        MovieNode mn = moviesTable.get(id);
        if (mn != null) {
            return mn.getVoteAverage();
        } else {
            return -1.0d;
        }
    }

    /**
     * Gets the amount of IMDb reviews used to generate the average score of a
     * particular film, given the ID number of that film
     * 
     * @param id The movie ID
     * @return The amount of IMDb reviews used to generate the average score of the
     *         requested film. If the film cannot be found, then return -1
     */
    @Override
    public int getVoteCount(int id) {
        MovieNode mn = moviesTable.get(id);
        if (mn != null) {
            return mn.getVoteCount();
        } else {
            return -1;
        }
    }

    /**
     * Adds a given film to a collection. The collection is required to have an ID
     * number, a name, and a URL to a poster for the collection
     * 
     * @param filmID                 The movie ID
     * @param collectionID           The collection ID
     * @param collectionName         The name of the collection
     * @param collectionPosterPath   The URL where the poster can
     *                               be found
     * @param collectionBackdropPath The URL where the backdrop can
     *                               be found
     * @return TRUE if the data able to be added, FALSE otherwise
     */
    @Override
    public boolean addToCollection(int filmID, int collectionID, String collectionName, String collectionPosterPath, String collectionBackdropPath) {
        MovieNode mn = moviesTable.get(filmID);
        if (mn != null) {
            mn.setCollectionID(collectionID);
            mn.setCollectionName(collectionName);
            mn.setCollectionPosterPath(collectionPosterPath);
            mn.setCollectionBackdropPath(collectionBackdropPath);
            
            if (collectionTable.get(collectionID) == null) {
                CollectionNode node = new CollectionNode(collectionID, collectionName, collectionPosterPath, collectionBackdropPath, filmID);
                collectionTable.put(node);
                return true;
            } else {
                collectionTable.get(collectionID).addFilmID(filmID);
                return true;
            }
        }
        return false;
    }

    /**
     * Get all films that belong to a given collection
     * 
     * @param collectionID The collection ID to be searched for
     * @return An array of film IDs that correspond to the given collection ID. If
     *         there are no films in the collection ID, or if the collection ID is
     *         not valid, return an empty array.
     */
    @Override
    public int[] getFilmsInCollection(int collectionID) {
        CollectionNode cn = collectionTable.get(collectionID);
        if (cn != null) {
            return cn.getFilmIDs();
        } else {
            return new int[0];
        }
    }

    /**
     * Gets the name of a given collection
     * 
     * @param collectionID The collection ID
     * @return The name of the collection. If the collection cannot be found, then
     *         return null
     */
    @Override
    public String getCollectionName(int collectionID) {
        CollectionNode cn = collectionTable.get(collectionID);
        if (cn != null) {
            return cn.getCollectionName();
        } else {
            return null;
        }
    }

    /**
     * Gets the poster URL for a given collection
     * 
     * @param collectionID The collection ID
     * @return The poster URL of the collection. If the collection cannot be found,
     *         then return null
     */
    @Override
    public String getCollectionPoster(int collectionID) {
        CollectionNode cn = collectionTable.get(collectionID);
        if (cn != null) {
            return cn.getCollectionPosterPath();
        } else {
            return null;
        }
    }

    /**
     * Gets the backdrop URL for a given collection
     * 
     * @param collectionID The collection ID
     * @return The backdrop URL of the collection. If the collection cannot be
     *         found, then return null
     */
    @Override
    public String getCollectionBackdrop(int collectionID) {
        CollectionNode cn = collectionTable.get(collectionID);
        if (cn != null) {
            return cn.getCollectionBackdropPath();
        } else {
            return null;
        }
    }

    /**
     * Gets the collection ID of a given film
     * 
     * @param filmID The movie ID
     * @return The collection ID for the requested film. If the film cannot be
     *         found, then return -1
     */
    @Override
    public int getCollectionID(int filmID) {
        MovieNode mn = moviesTable.get(filmID);
        if (mn != null) {
            return mn.getCollectionID();
        } else {
            return -1;
        }
    }

    /**
     * Sets the IMDb ID for a given film
     * 
     * @param filmID The movie ID
     * @param imdbID The IMDb ID
     * @return TRUE if the data able to be set, FALSE otherwise
     */
    @Override
    public boolean setIMDB(int filmID, String imdbID) {
        MovieNode mn = moviesTable.get(filmID);
        if (mn != null) {
            mn.setImdbID(imdbID);
            return true;
        }
        return false;
    }

    /**
     * Gets the IMDb ID for a given film
     * 
     * @param filmID The movie ID
     * @return The IMDb ID for the requested film. If the film cannot be found,
     *         return null
     */
    @Override
    public String getIMDB(int filmID) {
        MovieNode mn = moviesTable.get(filmID);
        if (mn != null) {
            return mn.getImdbID();
        } else {
            return null;
        }
    }

    /**
     * Sets the popularity of a given film. If the popularity for a film already exists, replace it with the new value
     * 
     * @param id         The movie ID
     * @param popularity The popularity of the film
     * @return TRUE if the data able to be set, FALSE otherwise
     */
    @Override
    public boolean setPopularity(int id, double popularity) {
        MovieNode mn = moviesTable.get(id);
        if (mn != null) {
            mn.setPopularity(popularity);
            return true;
        }
        return false;
    }

    /**
     * Gets the popularity of a given film
     * 
     * @param id The movie ID
     * @return The popularity value of the requested film. If the film cannot be
     *         found, then return -1.0d. If the popularity has not been set, return 0.0
     */
    @Override
    public double getPopularity(int id) {
        MovieNode mn = moviesTable.get(id);
        if (mn != null) {
            return mn.getPopularity();
        } else {
            return -1.0d;
        }
    }

    /**
     * Adds a production company to a given film
     * 
     * @param id      The movie ID
     * @param company A Company object that represents the details on a production
     *                company
     * @return TRUE if the data able to be added, FALSE otherwise
     */
    @Override
    public boolean addProductionCompany(int id, Company company) {
        MovieNode mn = moviesTable.get(id);
        if (mn != null) {
            mn.addProductionCompany(company);
            return true;
        }
        return false;
    }

    /**
     * Adds a production country to a given film
     * 
     * @param id      The movie ID
     * @param country A ISO 3166 string containing the 2-character country code
     * @return TRUE if the data able to be added, FALSE otherwise
     */
    @Override
    public boolean addProductionCountry(int id, String country) {
        MovieNode mn = moviesTable.get(id);
        if (mn != null) {
            mn.addProductionCountries(country);
            return true;
        }
        return false;
    }

    /**
     * Gets all the production companies for a given film
     * 
     * @param id The movie ID
     * @return An array of Company objects that represent all the production
     *         companies that worked on the requested film. If the film cannot be
     *         found, then return null
     */
    @Override
    public Company[] getProductionCompanies(int id) {
        MovieNode mn = moviesTable.get(id);
        if (mn != null) {
            return mn.getProductionCompanies();
        }
        return null;
    }

    /**
     * Gets all the production companies for a given film
     * 
     * @param id The movie ID
     * @return An array of Strings that represent all the production countries (in
     *         ISO 3166 format) that worked on the requested film. If the film
     *         cannot be found, then return null
     */
    @Override
    public String[] getProductionCountries(int id) {
        MovieNode mn = moviesTable.get(id);
        if (mn != null) {
            return mn.getProductionCountries();
        }
        return null;
    }

    /**
     * States the number of movies stored in the data structure
     * 
     * @return The number of movies stored in the data structure
     */
    @Override
    public int size() {
       return moviesTable.getSize();
    }

    /**
     * Produces a list of movie IDs that have the search term in their title,
     * original title or their overview
     * 
     * @param searchTerm The term that needs to be checked
     * @return An array of movie IDs that have the search term in their title,
     *         original title or their overview. If no movies have this search term,
     *         then an empty array should be returned
     */
    @Override
    public int[] findFilms(String searchTerm) {
        long startTime = System.nanoTime();
        
        String search = searchTerm.toLowerCase();
        MyLinkedList<MovieNode> list = moviesTable.getAllObjects();
        MyLinkedList<Integer> idsList = new MyLinkedList<>();
        MyListElement<MovieNode> current = list.getHead();
        MovieNode movie;

        for (int i=0; i<list.getSize(); i++) {
            movie = current.getVal();
            boolean match = (movie.getTitle() != null && movie.getTitle().toLowerCase().contains(search)) ||
                            (movie.getOriginalTitle() != null && movie.getOriginalTitle().toLowerCase().contains(search)) ||
                            (movie.getOverview() != null && movie.getOverview().toLowerCase().contains(search));

            if (match) {
                idsList.add(movie.getID());
            }
            current = current.getNext();
        }
        
        MyListElement<Integer> intCurrent = idsList.getHead();
        int[] arr = new int[idsList.getSize()];
        for (int i=0; i<idsList.getSize(); i++) {
            arr[i] = intCurrent.getVal();
            intCurrent = intCurrent.getNext();
        }
        
        long endTime = System.nanoTime();
        System.out.println("findFilms executed in: " + (endTime - startTime) / 1_000_000.0 + " ms");
        return arr;
    }
}
