/**
 * Created by carlospienovi1 on 5/28/16.
 */
public class EmptyCollectionException extends RuntimeException {
    /**
     * Sets up this exception with a fitting message.
     *
     * @param collection
     */
    public EmptyCollectionException(String collection) {
        super("The " + collection + " is empty.");
    }
}
