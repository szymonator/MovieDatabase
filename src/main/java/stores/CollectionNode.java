package stores;

import interfaces.Identifiable;
import structures.MyLinkedList;
import structures.MyListElement;

public class CollectionNode implements Identifiable {

    private int collectionID;

    private String collectionName;
    private String collectionPosterPath;
    private String collectionBackdropPath;

    private MyLinkedList<Integer> idList;

   /**
    * A constructor for the CollectionNode class
    * 
    * @param cID
    * @param name
    * @param posterPath
    * @param backdropPath
    * @param id
    */
    public CollectionNode(int cID, String name, String posterPath, String backdropPath, int id) {
        this.collectionID = cID;
        this.collectionName = name;
        this.collectionPosterPath = posterPath;
        this.collectionBackdropPath = backdropPath;

        this.idList = new MyLinkedList<Integer>();
        this.idList.add(id);
    }

    /**
     * Retrieves the collection ID
     * 
     * @return the collection ID
     */
    public int getID() {
        return collectionID;
    }

    /**
     * Retrieves the collection name
     * 
     * @return the collection name
     */
    public String getCollectionName() {
        return collectionName;
    }

    /**
     * Retrieves the collection poster path
     * 
     * @return the collection poster path
     */
    public String getCollectionPosterPath() {
        return collectionPosterPath;
    }

    /**
     * Retrieves the collection backdrop path
     * 
     * @return the collection backdrop path
     */
    public String getCollectionBackdropPath() {
        return collectionBackdropPath;
    }

    /**
     * Retrieves an array of Film IDs in the collection
     * 
     * @return an array of Film IDs in the collection
     */
    public int[] getFilmIDs() {
        int[] arr = new int[idList.getSize()];
        MyListElement<Integer> current = idList.getHead();
        for (int i=0; i<idList.getSize(); i++) {
            arr[i] = current.getVal();
            current = current.getNext();
        }
        return arr;
    }

    /**
     * Adds a film id to the collection
     * 
     * @param id the id to be added
     */
    public void addFilmID(int id) {
        idList.add(id);
    }
}
