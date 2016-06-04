/**
 * Created by carlospienovi1 on 5/28/16.
 */
public class Vehicle{

    private int vehicleNumber;
    private int arrivalTime;
    private int departureTime;
    private String street;
    private String direction;
    private String lane;

    
    public Vehicle(int vehicleNum, int aTime, int dTime, String direction, String street, String lane) {
        vehicleNumber = vehicleNum;
        arrivalTime = aTime;
        departureTime = dTime;
        this.direction = direction;
        this.street = street;
        this.lane = lane;
    }

        
    public String getStreet() {
		return street;
	}

	public String getDirection() {
		return direction;
	}

	public String getLane() {
		return lane;
	}

	public void setDepartureTime(int time) {
        departureTime = time;
    }

 
    public int getVehicleNumber() {
        return vehicleNumber;
    }

    /**
     * This method sets the string value of bound to the associated direction in which the car starts off in.
     *
     * @return the string representation of the original bound
     */
    public String getBound() {
        if (direction.equals("S"))
            return "southbound";
        else if (direction.equals("N"))
            return "northbound";
        else if (direction.equals("W"))
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
        if (lane.equals("L"))
            return "continued straight";
        else if (lane.equals("R") && direction.equals("S"))
            return "turned right and headed westbound";
        else if (lane.equals("R") && direction.equals("S"))
            return "turned right and headed eastbound";
        else if (lane.equals("R") && direction.equals("S"))
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
        String waittime = (departureTime - arrivalTime) + "";
        return "[Time " + departureTime + "] Vehicle #" + vehicleNumber + " (" + getBound() + ") " + getContinuation() + ". Total wait time " + waittime + " seconds.";
    }
}