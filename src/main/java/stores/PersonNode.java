package stores;

import interfaces.Identifiable;
import structures.MyLinkedList;
import structures.MyListElement;

public class PersonNode implements Identifiable{
    
    private Person person;
    private MyLinkedList<Integer> filmIDs;
    private MyLinkedList<Integer> starsInIDs;
    private int creditCount;

   /**
    * The constructor for the PersonNode object
    * 
    * @param person the person object to be stored
    * @param filmID the ID of the film the person is associated with
    * @param order the casting order of the person - this is 99 for all crew members
    */
    public PersonNode(Person person, int filmID, int order) {
        this.person = person;
        this.filmIDs = new MyLinkedList<Integer>();
        this.starsInIDs = new MyLinkedList<Integer>();
        addFilm(filmID, order);
        creditCount = 1;
    }

    /**
     * Retrieves the ID of the person stored
     * 
     * @return the ID of the person stored
     */
    public int getID() {
        return this.person.getID();
    }

    /**
     * Retrieves an integer array of all the IDs of the films the person has been a part of
     * 
     * @return an integer array of all the IDs of the films the person has been a part of
     */
    public int[] getFilmIds() {
        int[] arr = new int[filmIDs.getSize()];
        MyListElement<Integer> current = filmIDs.getHead();
        for (int i=filmIDs.getSize()-1; i>=0; i--) {
            arr[i] = current.getVal();
            current = current.getNext();
        }
        return arr;
    }

    /**
     * Retrieves an integer array of all the IDs of the films the person has starred in
     * 
     * @return an integer array of all the IDs of the films the person has starred in
     */
    public int[] getStarsInIds() {
        int[] arr = new int[starsInIDs.getSize()];
        MyListElement<Integer> current = starsInIDs.getHead();
        for (int i=starsInIDs.getSize()-1; i>=0; i--) {
            arr[i] = current.getVal();
            current = current.getNext();
        }
        return arr;
    }

    /**
     * Retrieves the amount of times the person has been credited
     * 
     * @return the amount of times the person has been credited
     */
    public int getCreditCount() {
        return this.creditCount;
    }

    /**
     * Adds a new movie credit to the store
     * 
     * @param id the id of the movie
     * @param order the casting order of the person in the given movie
     */
    public void addFilm(int id, int order) {
        filmIDs.add(id);
        if (order < 4) {
            starsInIDs.add(id);
        }
        creditCount++;
    }

    /**
     * Retrieves the person object
     * 
     * @return the person object
     */
    public Person getPerson() {
        return this.person;
    }

}
