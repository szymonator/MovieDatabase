package stores;

import java.time.LocalDateTime;

import interfaces.IRatings;
import structures.*;

public class Ratings implements IRatings {
    Stores stores;

    private MyHashTable<UserRatingNode> userTable;
    private MyHashTable<MovieRatingNode> movieTable;

    private MyAVLTree<Integer, Integer> userTree; // stores rating count, with user id
    private MyAVLTree<Integer, Integer> movieTree; // stores rating count, with movie id
    private MyAVLTree<Float, Integer> averageTree; // stores average ratings, with movie id

    private int size;

    /**
     * The constructor for the Ratings data store. This is where you should
     * initialise your data structures.
     * @param stores An object storing all the different key stores,
     *               including itself
     */
    public Ratings(Stores stores) {
        this.stores = stores;

        this.userTable = new MyHashTable<UserRatingNode>(10000);
        this.movieTable = new MyHashTable<MovieRatingNode>(10000);

        this.userTree = new MyAVLTree<Integer, Integer>();
        this.movieTree = new MyAVLTree<Integer, Integer>();
        this.averageTree = new MyAVLTree<Float, Integer>();

        size = 0;
    }

    /**
     * Adds a rating to the data structure. The rating is made unique by its user ID
     * and its movie ID
     * 
     * @param userID    The user ID
     * @param movieID   The movie ID
     * @param rating    The rating gave to the film by this user (between 0 and 5
     *                  inclusive)
     * @param timestamp The time at which the rating was made
     * @return TRUE if the data able to be added, FALSE otherwise
     */
    @Override
    public boolean add(int userid, int movieid, float rating, LocalDateTime timestamp) {
        if (rating < 0 || rating > 5) {
            return false;
        }

        Rating ratingObject = new Rating(userid, movieid, rating, timestamp);   // initialising
        UserRatingNode userRating = userTable.get(userid);
        MovieRatingNode movieRating = movieTable.get(movieid);

        if (userRating != null && userRating.checkExistence(movieid)) {         // check if rating already exists (not unique)
            return false; 
        }

        if (userRating != null) {                                               // if user exists rated, remove from trees due to key change
            userTree.remove(userRating.getAmount(), userid);
        }

        if (movieRating != null) {                                              // if movie exists, remove from trees due to key change
            movieTree.remove(movieRating.getAmount(), movieid);
            averageTree.remove(movieRating.getAverageRating(), movieid);
        }

        if (userRating == null) {                                               // create new user and add their rating
            userTable.put(new UserRatingNode(userid, ratingObject));
        } else {
            userRating.addRating(ratingObject);                                 // unless they exist, in which case add their rating
        }

        if (movieRating == null) {                                              // same for movies
            movieTable.put(new MovieRatingNode(movieid, ratingObject));
        } else {
            movieRating.addRating(ratingObject);
        }

        UserRatingNode updatedUser = userTable.get(userid);
        MovieRatingNode updatedMovie = movieTable.get(movieid);

        userTree.insert(updatedUser.getAmount(), userid);                       // update trees with new keys/values
        movieTree.insert(updatedMovie.getAmount(), movieid);
        averageTree.insert(updatedMovie.getAverageRating(), movieid);
        
        size++;
        return true;
    }

    /**
     * Removes a given rating, using the user ID and the movie ID as the unique
     * identifier
     * 
     * @param userID  The user ID
     * @param movieID The movie ID
     * @return TRUE if the data was removed successfully, FALSE otherwise
     */
    @Override
    public boolean remove(int userid, int movieid) {
        UserRatingNode userRating = userTable.get(userid);
        MovieRatingNode movieRating = movieTable.get(movieid);
        if (userRating == null || movieRating == null) {                        // if they don't exist, false
            return false;
        }

        Rating toRemove = userRating.getRating(movieid);                        

        userTree.remove(userRating.getAmount(), userid);                        // remove from trees
        movieTree.remove(movieRating.getAmount(), movieid);
        averageTree.remove(movieRating.getAverageRating(), movieid);

        userRating.removeRating(toRemove);                                      // remove from tables
        movieRating.removeRating(toRemove);

        size--;
        return true;
    }

    /**
     * Sets a rating for a given user ID and movie ID. Therefore, should the given
     * user have already rated the given movie, the new data should overwrite the
     * existing rating. However, if the given user has not already rated the given
     * movie, then this rating should be added to the data structure
     * 
     * @param userID    The user ID
     * @param movieID   The movie ID
     * @param rating    The new rating to be given to the film by this user (between
     *                  0 and 5 inclusive)
     * @param timestamp The time at which the new rating was made
     * @return TRUE if the data able to be added/updated, FALSE otherwise
     */
    @Override
    public boolean set(int userid, int movieid, float rating, LocalDateTime timestamp) {
        if (rating < 0 || rating > 5) {
            return false;
        }

        UserRatingNode userRating = userTable.get(userid);
        MovieRatingNode movieRating = movieTable.get(movieid);

        boolean ratingExists = (userRating != null && userRating.checkExistence(movieid));  // if rating doesn't exist yet, simply execute add
        if (!ratingExists) {
            return add(userid, movieid, rating, timestamp);
        }

        averageTree.remove(movieRating.getAverageRating(), movieid);                        // remove from averageTree due to key change

        Rating oldRating = userRating.getRating(movieid);
        oldRating.setRating(rating);                                                        // use set methods to set new attributes
        oldRating.setTimestamp(timestamp);

        movieRating.recalculateAverage();
        userRating.recalculateAverage();

        float average = movieTable.get(movieid).getAverageRating();
        averageTree.insert(average, movieid);                                               // update averageTree with new key value pair

        return true;
    }

    /**
     * Get all the ratings for a given film
     * 
     * @param movieID The movie ID
     * @return An array of ratings. If there are no ratings or the film cannot be
     *         found in Ratings, then return an empty array
     */
    @Override
    public float[] getMovieRatings(int movieid) {
        if (movieTable.get(movieid) != null) {
            return movieTable.get(movieid).getAllRatings();
        }
        return new float[0];
    }

    /**
     * Get all the ratings for a given user
     * 
     * @param userID The user ID
     * @return An array of ratings. If there are no ratings or the user cannot be
     *         found in Ratings, then return an empty array
     */
    @Override
    public float[] getUserRatings(int userid) {
        if (userTable.get(userid) != null) {
            return userTable.get(userid).getAllRatings();
        }
        return new float[0];
    }

    /**
     * Get the average rating for a given film
     * 
     * @param movieID The movie ID
     * @return Produces the average rating for a given film. 
     *         If the film cannot be found in Ratings, but does exist in the Movies store, return 0.0f. 
     *         If the film cannot be found in Ratings or Movies stores, return -1.0f.
     */
    @Override
    public float getMovieAverageRating(int movieid) {
        MovieRatingNode node = movieTable.get(movieid);
        if (node != null) {
            return node.getAverageRating();
        } else if (stores.getMovies().contains(movieid)) {
            return 0.0f;
        }
        return -1;
    }

    /**
     * Get the average rating for a given user
     * 
     * @param userID The user ID
     * @return Produces the average rating for a given user. If the user cannot be
     *         found in Ratings, or there are no rating, return -1.0f
     */
    @Override
    public float getUserAverageRating(int userid) {
        if (userTable.get(userid) != null) {
            return userTable.get(userid).getAverageRating();
        }
        return -1.0f;
    }

    /**
     * Gets the top N movies with the most ratings, in order from most to least
     * 
     * @param num The number of movies that should be returned
     * @return A sorted array of movie IDs with the most ratings. The array should be
     *         no larger than num. If there are less than num movies in the store,
     *         then the array should be the same length as the number of movies in Ratings
     */
    @Override
    public int[] getMostRatedMovies(int num) {
        long startTime = System.nanoTime();
        
        MyLinkedList<Integer> IDs = movieTree.getTopN(num);
        MyListElement<Integer> current = IDs.getHead();
        int[] arr = new int[IDs.getSize()];
        for (int i=IDs.getSize()-1; i>=0; i--) {
            arr[i] = current.getVal();
            current = current.getNext();
        }
        
        long endTime = System.nanoTime();
        System.out.println("getMostRatedMovies executed in: " + (endTime - startTime) / 1_000_000.0 + " ms");
        return arr;
    }

    /**
     * Gets the top N users with the most ratings, in order from most to least
     * 
     * @param num The number of users that should be returned
     * @return A sorted array of user IDs with the most ratings. The array should be
     *         no larger than num. If there are less than num users in the store,
     *         then the array should be the same length as the number of users in Ratings
     */
    @Override
    public int[] getMostRatedUsers(int num) {
        long startTime = System.nanoTime();
        
        MyLinkedList<Integer> IDs = userTree.getTopN(num);
        MyListElement<Integer> current = IDs.getHead();
        int[] arr = new int[IDs.getSize()];
        for (int i=IDs.getSize()-1; i>=0; i--) {
            arr[i] = current.getVal();
            current = current.getNext();
        }
        
        long endTime = System.nanoTime();
        System.out.println("getMostRatedUsers executed in: " + (endTime - startTime) / 1_000_000.0 + " ms");
        return arr;
    }

    /**
     * Get the number of ratings that a movie has
     * 
     * @param movieid The movie id to be found
     * @return The number of ratings the specified movie has. 
     *         If the movie exists in the Movies store, but there are no ratings for it, then return 0. 
     *         If the movie does not exist in the Ratings or Movies store, then return -1.
     */
    @Override
    public int getNumRatings(int movieid) {
        MovieRatingNode node = movieTable.get(movieid);
        if (node != null) {
            return node.getAmount();
        } else if (stores.getMovies().contains(movieid)) {
            return 0;
        }
        return -1;
    }

    /**
     * Get the highest average rated film IDs, in order of their average rating
     * (highest first).
     * 
     * @param numResults The maximum number of results to be returned
     * @return An array of the film IDs with the highest average ratings, highest
     *         first. If there are less than num movies in the store,
     *         then the array should be the same length as the number of movies in Ratings
     */
    @Override
    public int[] getTopAverageRatedMovies(int numResults) {
        long startTime = System.nanoTime();
        
        MyLinkedList<Integer> IDs = averageTree.getTopN(numResults);
        MyListElement<Integer> current = IDs.getHead();
        int[] arr = new int[IDs.getSize()];
        for (int i=IDs.getSize()-1; i>=0; i--) {
            arr[i] = current.getVal();
            current = current.getNext();
        }
        
        long endTime = System.nanoTime();
        System.out.println("getTopAverageRatedMovies executed in: " + (endTime - startTime) / 1_000_000.0 + " ms");
        return arr;
    }

    /**
     * Gets the number of ratings in the data structure
     * 
     * @return The number of ratings in the data structure
     */
    @Override
    public int size() {
        return size;
    }

}
