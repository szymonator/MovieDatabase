package structures;

public class MyLinkedList<E> {
    
    private MyListElement<E> head;
    private int size;

    /**
     * The constructor for the Linked List
     */
    public MyLinkedList() {
        head = null;
        size = 0;
    }

    /**
     * A getter for the head
     * 
     * @return The head of the Linked List
     */
    public MyListElement<E> getHead() {
        return head;
    }

    /**
     * Checks if the list is empty
     * 
     * @return TRUE if the list is empty, FALSE otherwise
     */
    public Boolean isEmpty() {
        return size == 0;
    }

    /**
     * A getter for the size
     * 
     * @return The size of the Linked List
     */
    public int getSize() {
        return size;
    }

    /* 
     * Adds the passed value to the list
     * 
     * @param value The value to be added
     */
    public void add(E value) {
        MyListElement<E> e = new MyListElement<>(value);

        if (!isEmpty()) {
            e.setNext(head);
        }
        this.head = e;
        size++;
    }

    /**
     * Removes the passed value and returns true/false based on success
     * 
     * @param value The value to be removed
     * @return TRUE if successful, FALSE otherwise
     */
    public Boolean remove(E value) {
        if (isEmpty()) {
            return false;
        }
        if (head.getVal().equals(value)) {
            head = head.getNext(); 
            size--;
            return true;
        }
        MyListElement<E> current = head;
        while (current.getNext() != null) {
            if (current.getNext().getVal().equals(value)) {
                current.setNext(current.getNext().getNext());
                size--;
                return true;
            }
            current = current.getNext();
        }
        return false;
    }

    /**
     * Retrieves the value the lies at the given index in the list
     * 
     * @param index The index where the value lies
     * @return The value at the index
     */
    public E get(int index) {
        if (isEmpty() || index >= getSize()) {
            return null;
        }
        MyListElement<E> temp = head;
        for (int i=0; i<index; i++) {
            temp = temp.getNext();
        }
        return temp.getVal();
    }

    /**
     * Returns a string version of the linked list
     * 
     * @return a string version of the list
     */
    public String toString() {
        if (isEmpty()) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder("[");
        for (int i=0; i<getSize(); i++) {
            sb.append(get(i));
            if (i < getSize() - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

}
