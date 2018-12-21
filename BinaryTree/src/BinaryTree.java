//Kenny Xiong 5317957
//Dennis Xiong 5331544
import java.util.ArrayList;

public class BinaryTree<K extends Comparable<K>, V> {
    private Node<K, V> root;

    public BinaryTree(Node root) {
        this.root = root;
    }

    public Node<K, V> getRoot() {
        return this.root;
    }


    /**
     * A recursive method that adds a node to the binary tree, given the key and value, by using the compareTo method.
     *
     * @param newNode the current node that is being compared
     * @param key the key that we want add to the binary tree
     * @param value the value that we want to add to the binary tree
     */
    public void recursiveAdd(Node<K, V> newNode, K key, V value) {
        Node<K, V> addNode = new Node<K, V>(key, value);

        //Checks if current node (newNode) key value is equal to the key for addNode, if yes then a new node will be added.
        if (newNode.getKey().compareTo(key) == 0) {
            newNode.setValue(value);
        }

        //Checks if the value of the current node's key is less then the wanted key, if yes then go through the left side of the binary tree.
        if (key.compareTo(newNode.getKey()) < 0) {
            //if current node's left node is empty, then set a node with the wanted key and value.
            if (newNode.getLeft() == null) {
                newNode.setLeft(addNode);
            } else {
                //if current node's left node is not empty, then repeat method, but with the current node's left node.
                recursiveAdd(newNode.getLeft(), key, value);
            }
        }

        //Checks if the value of the current node's key is more then the wanted key, if yes then go through the right side of the binary tree.
        else if (key.compareTo(newNode.getKey()) > 0) {

            //if current node's left node is empty, then set a node with the wanted key and value.
            if (newNode.getRight() == null) {
                newNode.setRight(addNode);
            }

            //if current node's left node is not empty, then repeat method, but with the current node's left node.
            else {
                recursiveAdd(newNode.getRight(), key, value);
            }
        }
    }

    /**
     * Method adds a node to the binary tree by using a helper method called recursiveAdd.
     *
     * @param key is the key we want to add to the node
     * @param value is the value that we want to add to the node
     */
    public void add(K key, V value) {
        //Instants a node for the wanted key and value
        Node<K, V> newNode = new Node<>(key, value);

        //if binary tree is empty, then first node or root is equal to the newNode
        if (root == null) {
            root = newNode;
        }

        //if binary tree is not empty, then use the recursiveAdd method with the root, and the wanted key and value as the parameters.
        else {
            recursiveAdd(root, key, value);
        }
    }

    /**
     * Method that recursive loops until it finds the wanted key in the binary tree and then returns the value of the key.
     *
     * @param key is the key of the value we want to return
     * @param tempNode is the current node we are at as we loop through the binary tree.
     * @return the value if the key is found in the binary tree
     */
    public V recursiveFind(K key, Node<K, V> tempNode) {

        //if the current node (temp node) is empty, the return null
        if (tempNode == null) {
            return null;
        }

        //if current node key is equal to the wanted key, then return the current node value
        else if (key.compareTo(tempNode.getKey()) == 0) {
            return tempNode.getValue();
        }

        //if current node key is less than the wanted key, then recursive itself but with the current node's left node.
        else if (key.compareTo(tempNode.getKey()) < 0) {
            return recursiveFind(key, tempNode.getLeft());
        }

        //if current node key is more than the wanted key, then recursive itself but with the current node's right node.
        else {
            return recursiveFind(key, tempNode.getRight());
        }
    }

    /**
     * Method to return the value of a key within the binary tree using a recursive helper method.
     *
     * @param key is the wanted key that we want to return value of within the binary tree
     * @return the value of the wanted key in the binary tree.
     */
    public V find(K key) {
        //Instants current node (tempNode) be equal to root
        Node<K, V> tempNode = root;
        //Uses recursiveFind method to find the and return the value of the wanted key
        return recursiveFind(key, tempNode);
    }

    /**
     * Method loops itself recursively to orderly add all of the binary tree values into an array list.
     *
     * @param currentNode is the node the we are current on as we loop through the binary tree
     * @param array is the list that we want to add our binary tree values into.
     */
    public void Flattenrecursive(Node<K, V> currentNode, ArrayList array) {

        //if the current node is empty then return nothing
        if (currentNode == null) {
            return;
        }
        //Calls to itself, but with the current node left node.
        Flattenrecursive(currentNode.getLeft(), array);

        //Adds the current node value into the array
        array.add(currentNode.getValue());

        //Calls to itself, but with the current node right node.
        Flattenrecursive(currentNode.getRight(), array);
    }

    /**
     * Method uses a recursively helper method to create an array list full of binary tree values that sorted from min to max.
     *
     * @return the complete array list
     */
    @SuppressWarnings("unchecked")
    public V[] flatten() {
        //Instants an array list
        ArrayList<V> array = new ArrayList<>();
        //Uses Flattenrecursive method to loop through the binary tree, and add the values to array list in a orderly way.
        Flattenrecursive(root, array);
        //Instants an array list with the v[] type
        V[] finalArray = (V[]) array.toArray();
        return finalArray;
    }

    /**
     * Method recursively loops itself returns a node associated with the given key.
     *
     * @param key is the key of the node that we want
     * @param tempNode is node we are current at as we loop through the binary tree
     * @return the wanted node
     */
    public Node<K, V> getNode(K key, Node<K, V> tempNode) {
        //if current node (tempNode) is empty, then return null
        if (tempNode == null) {
            return null;
        }

        //if current node key is equal to wanted key, then return the current node
        else if (key.compareTo(tempNode.getKey()) == 0) {
            return tempNode;
        }

        // if current node key is less than the wanted key, then return getNode method, but with current node's left node.
        else if (key.compareTo(tempNode.getKey()) < 0) {
            return getNode(key, tempNode.getLeft());
        }

        // Return getNode method, but with current node's right node.
        else {
            return getNode(key, tempNode.getRight());
        }
    }


    /**
     *Method recursively loop through binary tree until the node's child's key is found then return parent node.
     *
     * @param key is the key of the child node that we want find the parent of
     * @param tempNode is the current node we are at as we loop through the binary tree
     * @return parent node
     */
    public Node<K, V> getParentNode(K key, Node<K, V> tempNode) {

        //Instants the right and left node of tempNode
        Node<K, V> rightNode = tempNode.getRight();
        Node<K, V> leftNode = tempNode.getLeft();

        //Check if root's key is the same as given key. If yes, then there are no parent node. Hence, return null.
        if (root.getKey().compareTo(key) == 0) {
            return null;
        }
        //if leftNode is not empty and leftNode key is equal to child key, then return tempNode
        if (leftNode != null && leftNode.getKey().compareTo(key) == 0) {
            return tempNode;
        }
        //if rightNode is not empty and rightNode key is equal to child key, then return tempNode
        if (rightNode != null && rightNode.getKey().compareTo(key) == 0) {
            return tempNode;
        }
        //if child's key is less than the current node key, then call recursive method with the tempNode left node.
        if (key.compareTo(tempNode.getKey()) < 0) {
            return getParentNode(key, tempNode.getLeft());
        }

        // returns the tempNode's right node by using getNode.
        // child's key is less than current node key, recursive call with tempNode's right node.
        else {
            return getNode(key, tempNode.getRight());
        }
    }

    /**
     * Method uses method getNode and getParentNode to remove node associated with given key. The method makes a copy of
     * binary tree in case where the node with given key has two children and update the binary tree after the removal of the node.
     *
     * @param key is the key of the node we want to remove.
     */
    public void remove(K key) {
        //Instants the node using the getNode method that starts at the root.
        Node<K, V> nodeToRemove = getNode(key, root);

        //if nodeToRemove is empty, then return nothing
        if (nodeToRemove == null) {
            return;
        }

        //Instants the left and right nodes of nodeToRemove
        Node<K, V> left = nodeToRemove.getLeft();
        Node<K, V> right = nodeToRemove.getRight();

        //Instants a parent node using the getParentNode method
        Node<K, V> parent = getParentNode(key, root);

        //first case: children nodes of the nodeToRemove are null
        if (left == null && right == null) {
            //
            if (parent.getLeft().equals(nodeToRemove)) {    //remove nodeToRemove by setting parent node child to null depending on if
                                                            //child is on the right or left
                parent.setLeft(null);
            }
            //
            else {
                parent.setRight(null);
            }
        }
        //second case: nodeToRemove has left child
        //if nodeToRemove has left child, remove nodeToRemove by setting the left child of nodeToRemove as left or right(depend on parent location and key) child of nodeToRemove's parent.
        else if (left != null && right == null) {
            if (parent.getLeft().equals(nodeToRemove)) {
                parent.setLeft(left);
            }
            else {
                parent.setRight(left);
            }
        }
        //third case: nodeToRemove has right child
        //if nodeToRemove has right child, remove nodeToRemove by setting the right child of nodeToRemove as left or right(depend on parent location and key)child of nodeToRemove's parent.
        else if (right!= null && left == null) {
            //
            if (parent.getLeft().equals(nodeToRemove)) {
                parent.setLeft(right);
            }
            //
            else {
                parent.setRight(right);
            }
        }
        //fourth case: nodeToRemove has two children
        else {
            //using min method to find the node with smallest key. tempTree is a copy of the binary tree.
            Node<K, V> smallest = min(nodeToRemove.getRight());
            Node<K, V> tempTree = root;

            //Keep looping if tempTree is not equal to nodeToRemove. Goal is to get tempTree to be equal to nodeToRemove
            while (!tempTree.equals(nodeToRemove)) {
                //if tempTree key is less than nodeToRemove key, make tempTree equal to right node of tempTree
                if (tempTree.getKey().compareTo(nodeToRemove.getKey()) < 0) {
                    tempTree = tempTree.getRight();
                }
                //tempTree key is greater than nodeToRemove then make tempTree equal to left node of tempTree
                else {
                    tempTree = tempTree.getLeft();
                }
            }
            if (parent == null) { // special case for removing the root
                tempTree = smallest;
                tempTree.setLeft(nodeToRemove.getLeft());
                root = tempTree; //copy of binary tree
            }
            else {
                //check tempTree left child is the node we want to remove
                if (parent.getLeft().equals(nodeToRemove)) {
                    tempTree = smallest; //set tempTree to the smallest node we found using min method
                    tempTree.setLeft(nodeToRemove.getLeft());   //set tempTree left to left node of nodeToRemove
                    parent.setLeft(tempTree);//Finally, set parent left node to tempTree
                }
                //check tempTree right child is the node we want to remove
                else {
                    tempTree = smallest;     //set tempTree to the smallest node we found using min method
                    tempTree.setLeft(nodeToRemove.getLeft());   //set tempTree left to left node of nodeToRemove
                    parent.setRight(tempTree);  //set parent right to tempTree
                }
            }
        }
    }

    /**
     * Method uses recursive call to find the smallest key by calling until its left node is null then return the current node
     *
     * @param root the first node which the min method calls on
     * @return root
     */
    public Node<K, V> min(Node<K, V> root) {
        if (root.getLeft() == null) {
            return root;
        } else {
            return min(root.getLeft());
        }
    }

    /**
     * Method checks if subTree(other) is a subtree of our binaryTree. The method uses the equals method from Node class.
     * @param other is the subtree
     * @return true
     */
    public boolean containsSubtree(BinaryTree<K, V> other) {
        if (other == null || other.root == null) {  //checks if subTree actually exists
            return true;
        }
        Node<K, V> node = getNode(other.getRoot().getKey(), root);  //get the root Node of our binaryTree
        if (node == null) { //if root Node does not exists, return false;
            return false;
        }
        return node.equals(other.getRoot()); // compare root node and other root node
    }

}
