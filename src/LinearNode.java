/**
 * Created by carlospienovi1 on 5/28/16.
 */
public class LinearNode<T> {
    private LinearNode<T> next;
    private T element;

    /**
     * Provides the ability to instantiate an empty node.
     */
    public LinearNode() {
        next = null;
        element = null;
    }

    /**
     * Provides the ability to instantiate a node with that specific element
     *
     * @param elem to be stored
     */
    public LinearNode(T elem) {
        next = null;
        element = elem;
    }

    /**
     * Returns the next node in the chain.
     *
     * @return reference to next node
     */
    public LinearNode<T> getNext() {
        return next;
    }

    /**
     * Sets the next node in the chain.
     *
     * @paramnode you want to be following
     */
    public void setNext(LinearNode<T> node) {
        next = node;
    }

    /**
     * Returns the element stored in the current node.
     *
     * @return element stored at the node
     */
    public T getElement() {
        return element;
    }

    /**
     * Sets the element stored in the current node.
     *
     * @param elem to be stored at the node
     */
    public void setElement(T elem) {
        element = elem;
    }
}