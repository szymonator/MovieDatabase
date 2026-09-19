package structures;

public class MyListElement<E> {
    
    private E value;
    private MyListElement<E> next;

   /**
    * A constructor for the list element
    * 
    * @param value
    */
    public MyListElement(E value) {
        this.value = value;
        this.next = null;
    }

    /**
     * A getter for the value that the element holds
     * 
     * @return the value being stored
     */
    public E getVal() {
        return value;
    }

    /**
     * A setter for the value that that the element holds
     * 
     * @param value The new value to be set
     */
    public void setVal(E value) {
        this.value = value;
    }

    /**
     * A getter for the element that this one is linked to
     * 
     * @return The linked element
     */
    public MyListElement<E> getNext() {
        return next;
    }

    /**
     * A setter for the element that this one is linked to
     * 
     * @param next The new linked element
     */
    public void setNext(MyListElement<E> next) {
        this.next = next;
    }

}
