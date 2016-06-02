import Enums.Direction;
import Enums.Lane;
import Enums.Street;



/**
 * Created by carlospienovi1 on 5/28/16.
 */
public class Vehicle{
    /**
     * This integer value will represent the vehicle number, which will start at 1 and increment with each new car.
     */
    private int vehicleNumber;
    /**
     * This integer value will represent the time at which the car arrives at the intersection.
     */
    private int arrivalTime;
    /**
     * This integer value will represent the time at which the car leaves at the intersection.
     */
    private int departureTime;
    /**
     * This enumerated type will represent the street that the car pulls up at.
     */
    private Street street;
    /**
     * This enumerated type will represent the direction in which the car is heading.
     */
    private Direction direction;
    /**
     * This enumerated type will represent the lane in which the car is in.
     */
    private Lane lane;

    /**
     * This is a constructor. It sets up the ability for a car object to be instantiated.
     * Each time a vehicle is created, it is provided a random direction, street, and lane as well
     * as given the values it is passed in through the parameters.
     *
     * @param vehicleNum The int value of this object's vehicle number.
     * @param aTime      The int value of this object's arrival time.
     * @param dTime      The int value of this object's departure time.
     */
    public Vehicle(int vehicleNum, int aTime, int dTime, LinkedQueue<Vehicle> queue) {
        vehicleNumber = vehicleNum;
        arrivalTime = aTime;
        departureTime = dTime;
        direction = queue.getDirection();
        street = queue.getStreet();
        lane = queue.getLane();
    }

    /**
     * This is an accessor method that allows an outside class to view the direction of a car object.
     *
     * @return the direction of the car
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * This is an accessor method that allows an outside class to view the street of a car object.
     *
     * @return the street of the car
     */
    public Street getStreet() {
        return street;
    }

    /**
     * This is an accessor method that allows an outside class to view the lane of a car object.
     *
     * @return the lane of the object
     */
    public Lane getLane() {
        return lane;
    }

    /**
     * This is a mutator method that allows an outside class to set the departure time of a car object.
     *
     * @param time
     */
    public void setDepartureTime(int time) {
        departureTime = time;
    }

    /**
     * This is an accessor method that allows an outside class to view the vehicle number of a car object.
     *
     * @return the vehicle number of the object
     */
    public int getVehicleNumber() {
        return vehicleNumber;
    }

    /**
     * This method sets the string value of bound to the associated direction in which the car starts off in.
     *
     * @return the string representation of the original bound
     */
    public String getBound() {
        if (direction == Direction.S)
            return "southbound";
        else if (direction == Direction.N)
            return "northbound";
        else if (direction == Direction.W)
            return "westbound";
        else
            return "eastbound";
    }

    /**
     * This method sets the string value of continuation to the associated direction in which the car is headed.
     * If the car is going straight, the direction stays the same. If the car is turning, the continuation value
     * mimics that by displaying a new bound that has shifted by 90 degrees.
     *
     * @return the string representation of the new bound
     */
    private String getContinuation() {
        if (lane == Lane.Left)
            return "continued straight";
        else if (lane == Lane.Right && direction == Direction.S)
            return "turned right and headed westbound";
        else if (lane == Lane.Right && direction == Direction.N)
            return "turned right and headed eastbound";
        else if (lane == Lane.Right && direction == Direction.W)
            return "turned right and headed northbound";
        else
            return "turned right and headed southbound";
    }

    /**
     * Returns a string representation of the object
     *
     * @return string representation
     */
    public String toString() {
        String waittime = String.format("%02d",
                (departureTime - arrivalTime));
        return "[Time " + String.format("%02d", departureTime) + "] Vehicle #" + vehicleNumber + " (" + getBound() + ") " + getContinuation() + ". Total wait time " + waittime + " seconds.";
    }
}