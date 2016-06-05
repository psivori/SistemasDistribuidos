/**
 * Created by carlospienovi1 on 5/28/16.
 */
public class Vehicle{

	private int vehicleNumber;
	private long arrivalTime;
	private long departureTime;
	private String street;
	private String direction;
	private String lane;


	public Vehicle(int vehicleNum, String direction, String street, String lane) {
		this.arrivalTime = System.currentTimeMillis();
		vehicleNumber = vehicleNum;
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

	public void setDepartureTime(long time) {
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
			return "sur";
		else if (direction.equals("N"))
			return "norte";
		else if (direction.equals("W"))
			return "oeste";
		else
			return "este";
	}

	/**
	 * Returns a string representation of the object
	 *
	 * @return string representation
	 */
	public String toString() {
		this.setDepartureTime(System.currentTimeMillis());
		float waittime = (float)(departureTime - arrivalTime) / 1000 ;

		return "[Time " + departureTime + "] Omnibus #" + vehicleNumber + " (" + getBound() + "). Tiempo total de espera " + waittime + " segundos.";
	}
}