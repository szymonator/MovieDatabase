package structures;

public class MyAVLTree<K extends Comparable<? super K>, V> {
    
    /**
     * A class for a single node of MyAVLTree
     * 
     * <p>Each AVLNode stores a key by which it is ordered in the tree.
     * The height of the node is also stored, as are the left and right children.
     * Since values inserted may have the same key, the node holds a linked list containing all the values.</p>
     */
    private class AVLNode {

        private K key;
        private int height;
        private AVLNode left;
        private AVLNode right;
        private MyLinkedList<V> values;

       /**
        * A constructor for AVLNode
        * 
        * @param key The key of the node
        * @param object The object the node will store
        */
        public AVLNode(K key, V object) {
            this.key = key;
            this.height = 1;
            this.left = null;
            this.right = null;
            this.values = new MyLinkedList<V>();
            values.add(object);
        }

        /**
         * A getter that retrieves the left child of the node
         * 
         * @return The left child of the node
         */
        public AVLNode getLeft() {
            return this.left;
        }


        /**
         * A setter that sets the left child of the node
         * 
         * @param node The new left child of the node
         */
        public void setLeft(AVLNode node) {
            this.left = node;
        }

        /**
         * A getter that retrieves the right child of the node
         * 
         * @return The right child of the node
         */
        public AVLNode getRight() {
            return this.right;
        }

        /**
         * A setter that sets the right child of the node
         * 
         * @param node The new right child of the node
         */
        public void setRight(AVLNode node) {
            this.right = node;
        }

        /**
         * A getter that retrieves the height of the node
         * 
         * @return The height of the node
         */
        public int getHeight() {
            return this.height;
        }

        /**
         * A setter that sets the height of the node
         * 
         * @param height The new height of the node
         */
        public void setHeight(int height) {
            this.height = height;
        }

        /**
         * A getter that retrieves the key of the node
         * 
         * @return The key of the node
         */
        public K getKey() {
            return this.key;
        }

        /**
         * A setter that sets the key of the node
         * 
         * @param key The new key of the node
         */
        private void setKey(K key) {
            this.key = key;
        }

        /**
         * A getter that computes the balance of the node
         * <p>balance = left_height - right_height</p>
         * 
         * @return The balance of the node
         */
        public int getBalance() {
            int balance = 0;
            if (left != null) {
                balance += left.getHeight();
            }
            if (right != null) {
                balance -= right.getHeight();
            }
            return balance;
        }

        /**
         * A method that adds a new value to the AVLNode
         * 
         * @param value The value to be added
         */
        public void addValue(V value) {
            values.add(value);
        }

        /**
         * A method that retrieves all the values stored by the node
         * 
         * @return A linked list of all the nodes
         */
        public MyLinkedList<V> getValues() {
            return values;
        }

        /**
         * A method that sets the values stored by the node
         * 
         * @param list The new values to be stored
         */
        public void setValues(MyLinkedList<V> list) {
            this.values = list;
        }

    }

    private AVLNode root;
    private int size;

   /**
    * A constructor for MyAVLTree
    * 
    */
    public MyAVLTree() {
        root = null;
        size = 0;
    }

    /**
     * A public wrapper method that calls a recursive method to insert the given value at the given key
     * 
     * @param key The key where the value should be inserted
     * @param value The value to be inserted
     */
    public void insert(K key, V value) {
        root = insert(root, key, value);
    }

    /**
     * A recursive method that inserts the given value at the given key
     * 
     * <p>Inserts the value by traversing the tree recursively, and comparing keys as it goes. Once it reaches the same key or a leaf,
     * it adds the value. It then executes the <code>allRotations</code> method to perform the necessary rotations as it returns back 
     * up to the root node, at which point it terminates.</p>
     * 
     * @link allRotations
     * @param node The node currently being inspected
     * @param key The key where the value should be inserted
     * @param value The value to be inserted
     * @return
     */
    private AVLNode insert(AVLNode node, K key, V value) {
        if (node == null) {
            size++;
            return new AVLNode(key, value);
        }

        int cmp = key.compareTo(node.getKey());

        if (cmp < 0) {
            node.setLeft(insert(node.getLeft(), key, value));
        } else if (cmp > 0) {
            node.setRight(insert(node.getRight(), key, value));
        } else {                                                    // node to insert found
            node.addValue(value);
            size++;
            return node;
        }

        // returning back up the tree, setting new balance and new height of each node
        node.setHeight(1 + Math.max(height(node.getLeft()), height(node.getRight())));
        int balance = balance(node);

        return allRotations(balance, node); // rotate and return the node
    }

    /**
     * Retrieves the height of the node
     * 
     * @param node The node in question
     * @return The height of the given node
     */
    private int height(AVLNode node) {
        if (node == null) {
            return 0;
        } else {
            return node.getHeight();
        }
    }

    /**
     * Retrieves the balance of the node
     * 
     * @param node The node in question
     * @return The balance of the given node
     */
    private int balance(AVLNode node) {
        if (node == null) {
            return 0;
        } else {
            return node.getBalance();
        }
    }

    /**
     * A public wrapper for the recursive <code>get()</code> method, gets the values present at the given key
     * 
     * @param key The key of the values to be retrieved
     * @return The linked list of values at the key
     */
    public MyLinkedList<V> get(K key) {
        return get(root, key);
    }

    /**
     * A recursive method that traverses the tree to retrieve the values at a given key
     * 
     * @param node the current node being inspected
     * @param key the key where the values are present
     * @return the linked list of values at key, or null if unsuccessful
     */
    private MyLinkedList<V> get(AVLNode node, K key) {
        if (node == null) {
            return null; 
        }

        int cmp = key.compareTo(node.getKey());

        if (cmp < 0) {
            return get(node.getLeft(), key);
        } else if (cmp > 0) {
            return get(node.getRight(), key);
        } else {
            return node.getValues();
        }
    }

    /**
     * A public wrapper for the recursive <code>remove</code> function, removes a given value at a given key.
     * 
     * @param key The key where the values to be removed lie
     * @param value The value to be removed
     * @return TRUE if successful, FALSE otherwise
     */
    public Boolean remove(K key, V value) {
        boolean[] success = new boolean[1]; // defaults to false
        root = remove(root, key, value, success);
        return success[0];
    }

    /**
     * A recursive method that traverses the tree till it finds the value to remove, at the given key.
     * <p>If the value cannot be removed, success[0] is not updated, and contains FALSE. 
     * If the value is found and removed, success[0] is updated to TRUE, and the necessary rotations are 
     * performed on the way back up the tree as the recursive methods return.</p>
     * 
     * @param node
     * @param key
     * @param value
     * @param success
     * @return
     */
    private AVLNode remove(AVLNode node, K key, V value, boolean[] success) {
        if (node == null) {
            success[0] = false;
            return null; 
        }

        int cmp = key.compareTo(node.getKey());

        if (cmp < 0) {
            node.setLeft(remove(node.getLeft(), key, value, success));
        } else if (cmp > 0) {
            node.setRight((remove(node.getRight(), key, value, success)));
        } else {                                                                // node found
            if (!node.values.remove(value)) {                                   // node unable to be removed
                success[0] = false;                                             
                return node;
            } else {                                                            // node removed successfully
                size--;
                success[0] = true;
                if (node.values.getSize() == 0) {                               // node values are empty so entire node is to be removed
                    
                    if (node.getLeft() == null && node.getRight() == null) {    // if node is a leaf
                        return null;                                            
                    } else if (node.getLeft() == null) {                        // if node has at least one child
                        return node.getRight();                                 
                    } else if (node.getRight() == null) {
                        return node.getLeft();
                    } else {                                                    // node has two children, so a successor is chosen
                        AVLNode successor = getMinNode(node.getRight());
                        node.setKey(successor.getKey());
                        node.setValues(successor.getValues());
                        node.setRight(removeEntireNode(node.getRight(), successor.getKey(), new boolean[1]));
                    }

                }
            }
        }
        // returning back up the tree, height and balance of current node is updated
        node.setHeight(1 + Math.max(height(node.getLeft()), height(node.getRight())));
        int balance = balance(node);

        return allRotations(balance, node); // rotations performed and node is returned
        
    }

    /**
     * A helper method that returns the node with the smallest key that is a child of the given one
     * 
     * @param node The node who's children will be searched
     * @return The smallest child of the given node
     */
    private AVLNode getMinNode(AVLNode node) {
        AVLNode current = node;
        while (current.getLeft() != null) {
            current = current.getLeft();
        }
        return current;
    }

    /**
     * A public method that wraps the private recursive <code>removeEntireNode()</code> method, which completely removes the node
     * 
     * @param key The key of the node to be removed
     * @return TRUE if successful, FALSE otherwise
     */
    public Boolean removeEntireNode(K key) {
        boolean[] success = new boolean[1]; // defaults to false
        root = removeEntireNode(root, key, success);
        return success[0];
    }

    /**
     * A recursive method that traverses the tree till it finds the node to remove, with the given key.
     * <p>If the node cannot be removed, success[0] is not updated, and contains FALSE. 
     * If the node is found and removed, success[0] is updated to TRUE, and the necessary rotations are 
     * performed on the way back up the tree as the recursive methods return.</p>
     * 
     * @param node
     * @param key
     * @param success
     * @return
     */
    private AVLNode removeEntireNode(AVLNode node, K key, boolean[] success) {
        if (node == null) {
            success[0] = false;
            return null;
        }

        int cmp = key.compareTo(node.getKey());

        if (cmp < 0) {
            node.setLeft(removeEntireNode(node.getLeft(), key, success));
        } else if (cmp > 0) {
            node.setRight(removeEntireNode(node.getRight(), key, success));
        } else {                                                            // node found
            success[0] = true;
            size--;
            return node.getRight();
        }

        // updating height and balance as the method returns back up the tree
        node.setHeight(1 + Math.max(height(node.getLeft()), height(node.getRight())));
        int balance = balance(node);

        return allRotations(balance, node); // performing rotations and returning
    }

    /**
     * A method that performs a right rotation on a given node
     * 
     * @param node The node to be rotated
     * @return The rotated node
     */
    private AVLNode rightRotate(AVLNode node) {
        AVLNode x = node;
        AVLNode y = node.getLeft();
        AVLNode T2 = y.getRight();

        y.setRight(x);
        x.setLeft(T2);

        x.setHeight(1 + Math.max(height(node.getLeft()), height(node.getRight())));
        y.setHeight(1 + Math.max(height(node.getLeft()), height(node.getRight())));

        return y;
    }

    /**
     * A method that performs a left rotation on a given node
     * 
     * @param node The node to be rotated
     * @return The rotated node
     */
    private AVLNode leftRotate(AVLNode node) {
        AVLNode x = node;
        AVLNode y = node.getRight();
        AVLNode T2 = y.getLeft();

        y.setLeft(x);
        x.setRight(T2);

        x.setHeight(1 + Math.max(height(x.getLeft()), height(x.getRight())));
        y.setHeight(1 + Math.max(height(y.getLeft()), height(y.getRight())));

        return y;
    }

    /**
     * A method that checks the given balance and node to decide how to rotate the node
     * 
     * @param balance The balance of the node to be rotated
     * @param node The node to be rotated
     * @return
     */
    private AVLNode allRotations(int balance, AVLNode node) {

        // case 1 - left left
        if (balance > 1 && balance(node.getLeft()) >= 0) {
            return rightRotate(node);
        }
        // case 2 - left right
        if (balance > 1 && balance(node.getLeft()) < 0) {
            node.setLeft(leftRotate(node.getLeft()));
            return rightRotate(node);
        }
        // case 3 - right right
        if (balance < -1 && balance(node.getRight()) <= 0){
            return leftRotate(node);
        }
        // case 4 - right left 
        if (balance < -1 && balance(node.getRight()) > 0) {
            node.setRight(rightRotate(node.getRight()));
            return leftRotate(node);
        }
        return node; // never reached
    }

    /**
     * A public wrapper for the recursive <code>getTopN()</code> method which returns N values with the highest key
     * 
     * @param num The number of values to be returned
     * @return A linked list of the values to be returned
     */
    public MyLinkedList<V> getTopN(int num) {
        MyLinkedList<V> result = new MyLinkedList<V>();
        int[] index = new int[1]; 
        getTopN(root, result, index, num);
        return result;
    }

    /**
     * A recursive method that performs a reverse in-order traverse (right, root, left) to retrieve the top N highest values
     * 
     * @param node The node being currently inspected
     * @param result The list of values to be returned at the end
     * @param index The number of values currently retrieved
     * @param max The maximum number of values to be retrieved
     */
    private void getTopN(AVLNode node, MyLinkedList<V> result, int[] index, int max) {

        if (node == null || index[0] >= max) {
            return;
        }
        
        // right
        getTopN(node.getRight(), result, index, max);

        // root
        if (index[0] < max) {
            MyLinkedList<V> movies = node.getValues(); 
            MyListElement<V> current = movies.getHead();
            
            while (current != null && index[0] < max) { // gets all the values stored until max reached or we reach the end
                result.add(current.getVal());
                index[0]++;
                current = current.getNext();
            }
        }

        // left
        getTopN(node.getLeft(), result, index, max);
    }

    /**
     * A public wrapper method for the recursive <code>getBetween</code> method that retrieves all the values with keys between the given start and end parameters (non-inclusive)
     * 
     * @param start the lower key - all values retrieved should have a key higher than this
     * @param end the higher key - all values retrieved should have a key lower than this
     * @return
     */
    public MyLinkedList<V> getBetween(K start, K end) {
        MyLinkedList<V> idsList = new MyLinkedList<V>();
        getBetween(root, start, end, idsList);
        return idsList;
    }

    /**
     * A recursive method that retrieves a linked list of all the values with keys between <code>start</code> and <code>end</code> (non-inclusive), which are given
     * 
     * @param node the node currently being inspected
     * @param start the smallest key
     * @param end the largest key
     * @param idsList the list storing the retrieved values
     */
    private void getBetween(AVLNode node, K start, K end, MyLinkedList<V> idsList) {
        if (node == null) {
            return;
        }

        boolean before = node.getKey().compareTo(start) > 0;
        boolean after = node.getKey().compareTo(end) < 0;
        if (before && after) {                              // keys are within both bounds
            MyLinkedList<V> movies = node.getValues(); 
            MyListElement<V> current = movies.getHead();
            
            while (current != null) {
                idsList.add(current.getVal());
                current = current.getNext();
            }
        } else if (before) {
            // right - key is larger than the upper bound
            getBetween(node.getLeft(), start, end, idsList);
        } else if (after) {
            // left - key is smaller than the lower bound
            getBetween(node.getRight(), start, end, idsList);

        } 
    }

    /**
     *
     * @return
     */
    public int getSize() {
        return size;
    }

    /**
     *
     * @return
     */
    public AVLNode getRoot() {
        return root;
    }

    

}
