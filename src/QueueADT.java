/**
 * Created by carlospienovi1 on 5/28/16.
 */
public interface QueueADT<T> {
    /**
     * Adds one element to the end of the queue.
     *
     * @param element that is going to be added
     */
    public void enqueue(T element);

    /**
     * Removes and returns the element at the front of the queue.
     *
     * @return the first element in the queue
     */
    public T dequeue();

    /**
     * Returns without removing the element at the front of the queue.
     *
     * @return the first element in the queue
     */
    public T first();

    /**
     * Returns true if this queue contains no elements.
     *
     * @return true if this queue is empty
     */
    public boolean isEmpty();

    /**
     * Returns the number of elements in this queue.
     *
     * @return the integer value of the size of the queue
     */
    public int size();

    /**
     * Returns a string representation of this queue.
     *
     * @return the string representation of the queue
     */
    public String toString();
}
