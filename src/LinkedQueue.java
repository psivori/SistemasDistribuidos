import Enums.Direction;
import Enums.Lane;
import Enums.Street;



/**
 * Created by carlospienovi1 on 5/28/16.
 */
public class LinkedQueue<T> implements QueueADT<T> {
    /**
     * This integer value is used to help determine where you are in the linked queue.
     */
    private int count;
    /**
     * This is an instantiation of the generic linked node class that will help move through the elements of the queue.
     */
    private LinearNode<T> head, tail;
   
    private Street street;
    private Direction direction;
    private Lane lane;

    /**
     * Provides the ability to instantiate an empty queue.
     * Since there are no elements, the front and end will be the same and equivalent to null.
     */
    public LinkedQueue(Street street, Direction direction, Lane lane) {
        count = 0;
        head = tail = null;
        this.street = street; 
        this.direction = direction; 
        this.lane = lane; 
    }

    /**
     * Adds the element to the end of this queue.
     *
     * @param element to be added to the tail of the queue
     */
    public void enqueue(T element) {
        LinearNode<T> node = new LinearNode<T>(element);

        if (isEmpty())
            head = node;
        else
            tail.setNext(node);

        tail = node;
        count++;
    }

    /**
     * Removes and returns the element at the front of the queue and then moves the counter accordingly.
     *
     * @return the element at the head of this queue
     * @throws EmptyCollectionException if the queue is empty
     */
    public T dequeue() throws EmptyCollectionException {
        if (isEmpty())
            throw new EmptyCollectionException("queue");

        T result = head.getElement();
        head = head.getNext();
        count--;

        if (isEmpty())
            tail = null;

        return result;
    }

    /**
     * Returns a reference to the element at the head of this queue without removing it.
     *
     * @return the first element in this queue
     * @throws EmptyCollectionException if the queue is empty
     */
    public T first() throws EmptyCollectionException {
        if (!isEmpty())
            return (head.getElement());
        else
            throw new EmptyCollectionException("Nothing to peek");

    }

    /**
     * Returns true if this queue contains no elements.
     *
     * @return true if this queue is empty
     */
    public boolean isEmpty() {
        return (count == 0);
    }

    /**
     * Returns the number of elements currently in this queue.
     *
     * @return the number of elements in the queue
     */
    public int size() {
        return (count);
    }

    /**
     * Returns a string representation of this queue.
     *
     * @return the string representation of the queue
     */
    public String toString() {
        String queueString = "";
        try {
            LinearNode<T> elem = head;
            T elem2 = elem.getElement();

            for (int i = count; i > 0; i--) {
                queueString += (elem2);

                if (i > 1) {
                    queueString += "\n";
                    elem = elem.getNext();
                    elem2 = elem.getElement();
                }
            }

        } catch (NullPointerException e) {
            System.err.println("The tail is empty");
        }

        return queueString;
    }

	public Street getStreet() {
		return street;
	}

	public Direction getDirection() {
		return direction;
	}

	public Lane getLane() {
		return lane;
	}

    
    
}