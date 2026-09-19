package structures;
import interfaces.Identifiable;


public class MyHashTable<N extends Identifiable> {
    
    /**
     * A private class for a HashNode
     * <p>A HashNode is a single store of data in MyHashTable</p>
     */
    private class HashNode {
        N object; 
        HashNode next; 
        
       /**
        * A constructor for the HashNode
        * 
        * @param object The object to be stored in the node
        */
        public HashNode(N object) {
            this.object = object;
            this.next = null;
        }

        /**
         * A setter that links the current node to another
         * 
         * @param hn The node that the current one will be linked to
         */
        public void setNext(HashNode hn) {
            this.next = hn;
        }

        /**
         * A getter that retrieves the object being stored
         * 
         * @return The object being stored
         */
        public N getObject() {
            return object;
        }

        /**
         * A getter that retrieves the HashNode it is linked to
         * 
         * @return The HashNode the current one is linked to
         */
        public HashNode getNext() {
            return next;
        }
    }

    private HashNode[] table;
    private int size; // Number of movies stored
    private int capacity; // Size of the table

    
   /**
    * A constructor for MyHashTable
    * 
    * @param capacity The capacity of the table
    */
   @SuppressWarnings("unchecked")
    public MyHashTable(int capacity) {
        this.capacity = capacity;
        this.table = (HashNode[]) new MyHashTable.HashNode[capacity];
        this.size = 0;
    }

    /**
     * A method that computes the hash for a given key
     * 
     * @param id The given key that the hash is computed for
     * @return The computed hash value
     */
    private int hash(int id) {
        int h = id ^ (id >>> 16);
        h = (h & 0x7FFFFFFF) % capacity; // Ensures that index is positive
        return h;
    }

    /**
     * A method that puts an object into the table
     * <p>It first computes the hash, wraps the object in a HashNode, and then inserts it into a chain at the computed location</p>
     * 
     * @param object The object to be inserted/added
     * @return TRUE if successful, FALSE otherwise
     */
    public Boolean put(N object) {
        int key = hash(object.getID());
        HashNode hn = new HashNode(object);
        hn.setNext(table[key]);
        table[key] = hn;
        size++;

        if (size >= capacity * 0.75) {
            resize();
        }
        return true; 
    }

    /**
     * A getter that retrieves the object with the given id
     * 
     * @param id The id of the object to be retrieved
     * @return The object with the given id
     */
    public N get(int id) {
        int key = hash(id);
        HashNode hnode = table[key];

        while (hnode != null) {
            if (hnode.getObject().getID() == id) {
                return hnode.getObject();
            }
            hnode = hnode.getNext();
        }
        return null;
    }

    /**
     * A method that removes an object with the given id
     * 
     * @param id The id of the object to be removed
     * @return TRUE if successful, FALSE otherwise
     */
    public boolean remove(int id) {
        int key = hash(id);
        HashNode cell = table[key];

        if (cell == null) {                 // If empty
            return false;
        } else if (cell.getObject().getID() == id) {    // If it is in the cell
            if (cell.getNext() == null) {
                table[key] = null;
                size--;
                return true;
            } else {                        
                table[key] = cell.getNext();
                size--;
                return true;
            }
        } else {                            // If it is in the chain
            HashNode prev = cell;
            cell = cell.getNext();

            while (true) {

                if (cell == null) {
                    return false;
                } else if (cell.getObject().getID() == id) {
                    prev.setNext(cell.getNext());
                    size--;
                    return true;
                } else {
                cell = cell.getNext();
                prev = prev.getNext();
                }
            }
        }
    }

    /**
     * Checks if the table is empty
     * 
     * @return TRUE if empty, FALSE otherwise
     */
    public Boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the amount of elements in the table
     * 
     * @return The amount of elements in the table
     */
    public int getSize() {
        return size;
    }

    /**
     * Retrieves the ID of every single object present in the table
     * 
     * @return An array of the ID of every object in the table
     */
    public int[] getAllIDs() {
        int[] ids = new int[this.size];
        int index = 0;

        for (int i = 0; i < capacity; i++) {
            HashNode current = table[i];
            while (current != null) {
                ids[index] = current.getObject().getID();
                index++;
                current = current.getNext();
            }
            if (index == getSize()) {
                break;
            }
        }
        return ids;
    }

    /**
     * Returns all the objects
     * 
     * @return A linked list of every object in the table
     */
    public MyLinkedList<N> getAllObjects() {
        MyLinkedList<N> list = new MyLinkedList<N>();
        int index = 0;
        
        for (int i = 0; i < capacity; i++) {
            HashNode current = table[i];
            while (current != null) {
                list.add(current.getObject());
                index++;
                current = current.getNext();
            }
            if (index == getSize()) {
                break;
            }
        }
        
        return list;
    }

    
    /**
     * A method that resizes the hash table
     * <p>This is triggered if the size reaches 75% of the capacity</p>
     */
    @SuppressWarnings("unchecked")
    public void resize() {
        HashNode[] oldData = table;
        int newCapacity = capacity * 2;
        this.capacity = newCapacity;
        int oldSize = this.size;
        this.table = (HashNode[]) new MyHashTable.HashNode[newCapacity];
        this.size = 0;
        int index = 0;

        for (int i = 0; i < oldData.length; i++) {
            HashNode current = oldData[i];

            while (current != null) {
                put(current.getObject());
                index++;
                current = current.getNext();
            }

            if (index == oldSize) {
                break;
            }
        }
    }
}