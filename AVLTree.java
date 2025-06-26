import java.util.ArrayList;

/**
 * A node in an AVL tree, representing a single element with a value,
 * height, and references to left and right child nodes.
 */
class AVLNode {
    int value; // The value of the node
    int height; // The height of the node
    AVLNode left, right; // References to the left and right children

    /**
     * Constructs a new AVLNode with the specified value.
     * The height is initialized to 1 since it is a leaf node when created.
     *
     * @param value the value to be stored in the node
     */
    AVLNode(int value) {
        this.value = value;
        this.height = 1; // New nodes are added as leaves
    }
}

/**
 * A self-balancing binary search tree implementation using the AVL tree algorithm.
 * The tree maintains balance after insertion and deletion, ensuring operations
 * have a time complexity of O(log n) in the average case.
 */
public class AVLTree {
    private AVLNode root; // The root node of the AVL tree

    /**
     * Get the height of a node.
     *
     * @param node the node whose height is to be retrieved
     * @return the height of the node, or 0 if the node is null
     */
    private int height(AVLNode node) {
        return node == null ? 0 : node.height;
    }

    /**
     * Get the balance factor of a node.
     *
     * @param node the node whose balance factor is to be calculated
     * @return the balance factor of the node, or 0 if the node is null
     */
    private int getBalance(AVLNode node) {
        return node == null ? 0 : height(node.left) - height(node.right);
    }

    /**
     * Perform a right rotation on the subtree rooted with the specified node.
     *
     * @param y the node to rotate
     * @return the new root of the rotated subtree
     */
    private AVLNode rightRotate(AVLNode y) {
        AVLNode x = y.left;
        AVLNode T2 = x.right;

        // Perform rotation
        x.right = y;
        y.left = T2;

        // Update heights
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    /**
     * Perform a left rotation on the subtree rooted with the specified node.
     *
     * @param x the node to rotate
     * @return the new root of the rotated subtree
     */
    private AVLNode leftRotate(AVLNode x) {
        AVLNode y = x.right;
        AVLNode T2 = y.left;

        // Perform rotation
        y.left = x;
        x.right = T2;

        // Update heights
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }

    /**
     * Insert a value into the AVL tree.
     *
     * @param value the value to insert
     */
    public void insert(int value) {
        root = insert(root, value);
    }

    /**
     * Insert a value into the subtree rooted with the specified node.
     * Performs balancing after insertion if necessary.
     *
     * @param node  the root node of the subtree
     * @param value the value to insert
     * @return the new root of the subtree
     */
    private AVLNode insert(AVLNode node, int value) {
        if (node == null) {
            return new AVLNode(value);
        }

        if (value < node.value) {
            node.left = insert(node.left, value);
        } else if (value > node.value) {
            node.right = insert(node.right, value);
        } else {
            return node;
        }

        node.height = Math.max(height(node.left), height(node.right)) + 1;
        int balance = getBalance(node);

        if (balance > 1 && value < node.left.value) {
            return rightRotate(node);
        }

        if (balance < -1 && value > node.right.value) {
            return leftRotate(node);
        }

        if (balance > 1 && value > node.left.value) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        if (balance < -1 && value < node.right.value) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    /**
     * Find the largest value in the AVL tree smaller than the given value.
     *
     * @param value the reference value
     * @return the largest value smaller than the specified value, or -1 if no such value exists
     */
    public int findLargestSmallerThan(int value) {
        AVLNode current = root;
        int result = -1;

        while (current != null) {
            if (current.value < value) {
                result = current.value;
                current = current.right;
            } else {
                current = current.left;
            }
        }

        return result;
    }

    /**
     * Find the smallest value in the AVL tree greater than the given value.
     *
     * @param value the reference value
     * @return the smallest value greater than the specified value, or -1 if no such value exists
     */
    public int findSmallestGreaterThan(int value) {
        AVLNode current = root;
        int result = -1;

        while (current != null) {
            if (current.value > value) {
                result = current.value;
                current = current.left;
            } else {
                current = current.right;
            }
        }

        return result;
    }

    /**
     * Convert the AVL tree to a sorted ArrayList using in-order traversal.
     *
     * @return an ArrayList containing elements of the AVL tree in sorted order
     */
    public ArrayList<Integer> toArrayList() {
        ArrayList<Integer> result = new ArrayList<>();
        inOrderTraversal(root, result);
        return result;
    }

    /**
     * Perform an in-order traversal and collect elements into a list.
     *
     * @param node   the current node
     * @param result the list to collect elements
     */
    private void inOrderTraversal(AVLNode node, ArrayList<Integer> result) {
        if (node != null) {
            inOrderTraversal(node.left, result);
            result.add(node.value);
            inOrderTraversal(node.right, result);
        }
    }

    /**
     * Search for a value in the AVL tree.
     *
     * @param value the value to search for
     * @return true if the value is found, false otherwise
     */
    public boolean search(int value) {
        return search(root, value);
    }

    /**
     * Helper method to search for a value in a subtree.
     *
     * @param node  the root node of the subtree
     * @param value the value to search for
     * @return true if the value is found, false otherwise
     */
    private boolean search(AVLNode node, int value) {
        if (node == null) {
            return false;
        }
        if (value == node.value) {
            return true;
        }
        return value < node.value ? search(node.left, value) : search(node.right, value);
    }

    /**
     * Delete a value from the AVL tree.
     *
     * @param value the value to delete
     */
    public void delete(int value) {
        root = delete(root, value);
    }

    /**
     * Delete a value from the subtree rooted with the specified node.
     * Balances the tree after deletion if necessary.
     *
     * @param node  the root node of the subtree
     * @param value the value to delete
     * @return the new root of the subtree
     */
    private AVLNode delete(AVLNode node, int value) {
        if (node == null) {
            return null;
        }

        if (value < node.value) {
            node.left = delete(node.left, value);
        } else if (value > node.value) {
            node.right = delete(node.right, value);
        } else {
            if (node.left == null || node.right == null) {
                node = (node.left != null) ? node.left : node.right;
            } else {
                AVLNode temp = minValueNode(node.right);
                node.value = temp.value;
                node.right = delete(node.right, temp.value);
            }
        }

        if (node == null) {
            return null;
        }

        node.height = Math.max(height(node.left), height(node.right)) + 1;
        int balance = getBalance(node);

        if (balance > 1 && getBalance(node.left) >= 0) {
            return rightRotate(node);
        }

        if (balance > 1 && getBalance(node.left) < 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        if (balance < -1 && getBalance(node.right) <= 0) {
            return leftRotate(node);
        }

        if (balance < -1 && getBalance(node.right) > 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    /**
     * Get the node with the minimum value in the subtree rooted with the specified node.
     *
     * @param node the root node of the subtree
     * @return the node with the minimum value
     */
    private AVLNode minValueNode(AVLNode node) {
        AVLNode current = node;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    /**
     * Collects elements greater than or equal to the specified value into a list.
     *
     * @param value the reference value
     * @param b     unused parameter for compatibility
     * @return a list of elements greater than or equal to the specified value
     */
    public ArrayList<Integer> tailSet(int value, boolean b) {
        ArrayList<Integer> result = new ArrayList<>();
        collectTailSet(root, value, result);
        return result;
    }

    /**
     * Helper method to collect elements greater than or equal to a specified value.
     *
     * @param node   the current node
     * @param value  the reference value
     * @param result the list to collect elements
     */
    private void collectTailSet(AVLNode node, int value, ArrayList<Integer> result) {
        if (node == null) {
            return;
        }

        if (node.value >= value) {
            collectTailSet(node.left, value, result);
            result.add(node.value);
        }

        collectTailSet(node.right, value, result);
    }

    /**
     * Get the size (number of nodes) of the AVL tree.
     *
     * @return the number of nodes in the tree
     */
    public int size() {
        return size(root);
    }

    /**
     * Helper method to get the size of a subtree.
     *
     * @param node the root node of the subtree
     * @return the number of nodes in the subtree
     */
    private int size(AVLNode node) {
        if (node == null) {
            return 0;
        }
        return 1 + size(node.left) + size(node.right);
    }
}
