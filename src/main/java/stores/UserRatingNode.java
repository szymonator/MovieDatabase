package stores;

import interfaces.Identifiable;
import structures.MyLinkedList;
import structures.MyListElement;

public class UserRatingNode implements Identifiable {
    
    private int ID;
    private MyLinkedList<Rating> ratings;
    private int amount;
    private float averageRating;

   /**
    * A constructor for the UserRatingNode class
    * 
    * @param ID the ID of the node
    * @param rating the rating stored in the object
    */
    public UserRatingNode(int ID, Rating rating) {
        this.ID = ID;
        this.ratings = new MyLinkedList<Rating>();
        ratings.add(rating);
        amount = 1;
        averageRating = rating.getRating();
        
    }

    /**
     * A getter method that retrieves the ID stored in the node
     * 
     * @return the ID
     */
    public int getID() {
        return this.ID;
    }

    /**
     * Traverses the ratings list to return a <code>Rating</code> object based on the given id
     * @param movieID the id of the rating to be returned
     * @return a <code>Rating</code> object with the given id
     */
    public Rating getRating(int movieID) {
        MyListElement<Rating> current = ratings.getHead();
        while (current != null) {
            if (current.getVal().getMovieID() == movieID) {
                return current.getVal();
            }
            current = current.getNext();
        }
        return null;
    }

    /**
     * A method that recalculates the average rating of all the ratings stored by the node
     */
    public void recalculateAverage() {
        if (amount == 0) {
            averageRating = 0;
        }
        MyListElement<Rating> current = ratings.getHead();
        float total = 0;
        while (current != null) {
            total += current.getVal().getRating();
            current = current.getNext();
        }
        averageRating = total/amount;
    }

    /**
     * A method that retrieves an arry of all the ratings stored by this node
     * 
     * @return a float array of all the ratings stored
     */
    public float[] getAllRatings() {
        float[] arr = new float[ratings.getSize()];
        MyListElement<Rating> current = ratings.getHead();
        for (int i=ratings.getSize()-1; i>=0; i--) {
            arr[i] = current.getVal().getRating();
            current = current.getNext();
        }
        return arr;
    }

    /**
     * Adds the rating object to the list
     * 
     * <p>updates average in the process</p>
     * @param rating the rating object to be added
     */
    public void addRating(Rating rating) {
        ratings.add(rating);
        averageRating = (averageRating*amount + rating.getRating())/(amount+1);
        amount++;
    }

    /**
     * Checks the existence of a rating with the given movieID
     * 
     * @param movieID the movieID of the rating to search for
     * @return TRUE if exists, FALSE otherwise
     */
    public Boolean checkExistence(int movieID) {
        Boolean contains = false;
        MyListElement<Rating> current = ratings.getHead();
        for (int i=ratings.getSize()-1; i>=0; i--) {
            if (current.getVal().getMovieID() == movieID) {
                contains = true;
                break;
            }
            current = current.getNext();
        }
        return contains;
    }

    /**
     * Removes the given rating from the node
     * 
     * <p>Updates average in the process</p>
     * @param rating the rating to be removed
     * @return TRUE if successful, FALSE otherwise
     */
    public Boolean removeRating(Rating rating) {
        Boolean toReturn =  ratings.remove(rating);
        if (!toReturn) {
            return false;
        }
        if (amount == 1) {
            amount = 0;
            averageRating = 0;
        } else {
            averageRating = (averageRating*amount - rating.getRating())/(amount-1);
            amount--;
        }
        return toReturn;
    }

    /**
     * A getter that retrieves the amount of ratings stored
     * 
     * @return the amount of ratings stored
     */
    public int getAmount() {
        return ratings.getSize();
    }

    /**
     * A getter that retrieves the average of the ratings stored
     * 
     * @return the average rating of the ratings stored
     */
    public float getAverageRating() {
        return averageRating;
    }
}
