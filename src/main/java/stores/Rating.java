package stores;

import java.time.LocalDateTime;

public class Rating {
    
    private int userID;
    private int movieID;
    private float rating;
    private LocalDateTime timestamp;

   /**
    * A constructor for the Rating object
    * 
    * @param user the id of the user that made the rating
    * @param movie the id of the movie that was rated
    * @param rating the rating value
    * @param timestamp the timestamp
    */
    public Rating(int user, int movie, float rating, LocalDateTime timestamp) {
        this.userID = user;
        this.movieID = movie;
        this.rating = rating;
        this.timestamp = timestamp;
    }

    /**
     * Retrieves the userID of the rating
     * 
     * @return the userID of the rating
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Retrieves the movieID of the rating
     * 
     * @return the movieID of the rating
     */
    public int getMovieID() {
        return movieID;
    }

    /**
     * Retrieves the rating value
     * 
     * @return the rating value
     */
    public float getRating() {
        return rating;
    }

    /**
     * Sets the rating value
     * 
     * @param rating the value to be set
     */
    public void setRating(float rating) {
        this.rating = rating;
    }

    /**
     * Retrieves the timestamp
     * 
     * @return the timestamp
     */
    public LocalDateTime getTimestamp() {
        return this.timestamp;
    }

    /**
     * Sets the timestamp
     * 
     * @param timestamp the timestamp to be set
     */
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

}
