import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by carlospienovi1 on 5/28/16.
 */
public class Simulator {

    private static final int CICLO_TIEMPO = 1;
    private static final int CICLO_GARZON_DEFAULT = 3;
    private static final int CICLO_MILLAN_DEFAULT = 2;
    private static final int MAX_AUTOS_DIRECCION = 15;

    /**
     * This queue will hold only vehicle objects that enter in the left lane on Main Street headed East.
     */
    private LinkedQueue<Vehicle> EGarzonL = new LinkedQueue<Vehicle>();
    /**
     * This queue will hold only vehicle objects that enter in the right lane on Main Street headed East.
     */
    private LinkedQueue<Vehicle> EGarzonR = new LinkedQueue<Vehicle>();
    /**
     * This queue will hold only vehicle objects that enter in the left lane on Main Street headed West.
     */
    private LinkedQueue<Vehicle> WGarzonL = new LinkedQueue<Vehicle>();
    /**
     * This queue will hold only vehicle objects that enter in the right lane on Main Street headed West.
     */
    private LinkedQueue<Vehicle> WGarzonR = new LinkedQueue<Vehicle>();
    /**
     * This queue will hold only vehicle objects that enter in the left lane on Church Street headed North.
     */
    private LinkedQueue<Vehicle> NMillanL = new LinkedQueue<Vehicle>();
    /**
     * This queue will hold only vehicle objects that enter in the right lane on Church Street headed North.
     */
    private LinkedQueue<Vehicle> NMillanR = new LinkedQueue<Vehicle>();
    /**
     * This queue will hold only vehicle objects that enter in the left lane on Church Street headed South.
     */
    private LinkedQueue<Vehicle> SMillanL = new LinkedQueue<Vehicle>();
    /**
     * This queue will hold only vehicle objects that enter in the right lane on Church Street headed South.
     */
    private LinkedQueue<Vehicle> SMillanR = new LinkedQueue<Vehicle>();
    /**
     * This integer value will keep track of the current time during the simulation.
     */
    int time = 0;

    int cicloGarzon = CICLO_GARZON_DEFAULT;
    int cicloMillan = CICLO_MILLAN_DEFAULT;

    /**
     * This integer value will keep track of the vehicle numbers of the cars that arrive at the intersection.
     */
    int vehicleNum = 1;
    /**
     * This creates a filewriter object that allows us for the ability to write streams of characters to a file.
     */
    FileWriter fw;
    /**
     * This creates a bufferedwriter object that allows for the efficient writing to the file.
     */
    BufferedWriter bw;
    /**
     * This creates a printwriter object that allows for the ability to use the print methods when writing to the file.
     */
    PrintWriter outFile;

    /**
     * This is a constructor. It sets up the ability for a simulator object to be instantiated.
     */
    public Simulator() {
    }

    /**
     * This method calls all others methods in this class. It firsts instantiates the objects associated with
     * writing to the file. It then calls the populate method to start the simulation and create car objects. The
     * amount of car objects created is between 7 and 12. Until all queues are empty, the simulation will continue
     * to run. During the simulation, the cars will move in the North/South direction for 6 seconds, then more
     * cars will arrive (between 8 and 15). Then the cars will move in the East/ West direction for 6 seconds,
     * and more cars will arrive (between 3 and 15).
     *
     * @throws IOException this is done in case their is an error printing to the file
     */
    public void simulate() {
        try {
            fw = new FileWriter("output.txt");
            bw = new BufferedWriter(fw);
            outFile = new PrintWriter(bw);

            outFile.print("---Start of simulation, time set to 0.--- \n");
            populate((int) (Math.random() * (13 - 7) + 7));
            while (!queuesEmpty()) {
                checkBus();

                outFile.print("---Light changed. Now processing Av. Millán traffic--- \n");
                outFile.print("Av. Millan SUR LEFT = " + SMillanL.size() + "\n");
                outFile.print("Av. Millan SUR RIGHT = " + SMillanR.size() + "\n");
                outFile.print("Av. Millan NORTE LEFT = " + NMillanL.size() + "\n");
                outFile.print("Av. Millan NORTE RIGHT = " + NMillanR.size() + "\n");

                outFile.print("#omnibus = " + (EGarzonL.size() + WGarzonL.size()) + "\n");

                outFile.print("Ciclo verde Av. Millan = " + cicloMillan + "\n");

                moveNorthSouth();
                populate((int) (Math.random() * (16 - 8) + 8));
                outFile.println();
                outFile.print("---Light changed. Now processing Corredor Garzón traffic---\n");
                moveEastWest();
                populate((int) (Math.random() * (16 - 3) + 3));
                outFile.println();
            }
            outFile.close();
        } catch (IOException e) {
            System.err.println("Error printing to file");
        }
    }

    private void checkBus() {
        int autosNorte = NMillanL.size() + NMillanR.size();
        int autosSur = SMillanL.size() + SMillanR.size();

        boolean pocosAutosEsperando = autosNorte < MAX_AUTOS_DIRECCION && autosSur < MAX_AUTOS_DIRECCION;
        boolean alMenosUnOmnibus = !EGarzonL.isEmpty() || !WGarzonL.isEmpty();
        boolean muchosOmnibus = EGarzonL.size() > cicloGarzon || WGarzonL.size() > cicloGarzon;

        if (pocosAutosEsperando && alMenosUnOmnibus) {
            // alargas ciclo
            cicloMillan = CICLO_MILLAN_DEFAULT - 1;
        } else {
            // vuelve ciclo normal
            cicloMillan = CICLO_MILLAN_DEFAULT;
        }

        if (pocosAutosEsperando && muchosOmnibus) {
            // alargas ciclo
            cicloGarzon = CICLO_GARZON_DEFAULT + 1;
        } else {
            // vuelve ciclo normal
            cicloGarzon = CICLO_GARZON_DEFAULT;
        }
    }

    /**
     * This method is only called once when first populating the intersection. It instantiates the amount of cars
     * according to the number that is passed in. The time they are created, their arrival times, is
     * the current time in the simulation. Each time a new vehicle is created, the vehicle number increases by 1.
     * Depending on what lane and direction the car enters the simulation, it is placed correctly into its
     * corresponding queue.
     *
     * @param randomNum of cars to be instantiated
     */
    private void populate(int randomNum) {
        int count = 0;
        while (count < randomNum && vehicleNum <= 120) {
            Vehicle car = new Vehicle(vehicleNum, time, time);
            count++;
            vehicleNum++;
            if (car.getStreet() == Vehicle.Street.Main && car.getDirection() == Vehicle.Direction.E && car.getLane() == Vehicle.Lane.Left)
                EGarzonL.enqueue(car);
            else if (car.getStreet() == Vehicle.Street.Main && car.getDirection() == Vehicle.Direction.E && car.getLane() == Vehicle.Lane.Right)
                EGarzonR.enqueue(car);
            else if (car.getStreet() == Vehicle.Street.Main && car.getDirection() == Vehicle.Direction.W && car.getLane() == Vehicle.Lane.Left)
                WGarzonL.enqueue(car);
            else if (car.getStreet() == Vehicle.Street.Main && car.getDirection() == Vehicle.Direction.W && car.getLane() == Vehicle.Lane.Right)
                WGarzonR.enqueue(car);
            else if (car.getStreet() == Vehicle.Street.Church && car.getDirection() == Vehicle.Direction.N && car.getLane() == Vehicle.Lane.Left)
                NMillanL.enqueue(car);
            else if (car.getStreet() == Vehicle.Street.Church && car.getDirection() == Vehicle.Direction.N && car.getLane() == Vehicle.Lane.Right)
                NMillanR.enqueue(car);
            else if (car.getStreet() == Vehicle.Street.Church && car.getDirection() == Vehicle.Direction.S && car.getLane() == Vehicle.Lane.Left)
                SMillanL.enqueue(car);
            else
                SMillanR.enqueue(car);
        }

        printQueues();
    }

    private void printQueues() {
        System.out.println("BUS = " + EGarzonL.size());
        System.out.println("EGarzonR = " + EGarzonR.size());
        System.out.println("BUS = " + WGarzonL.size());
        System.out.println("WGarzonR = " + WGarzonR.size());
        System.out.println("NMillanL = " + NMillanL.size());
        System.out.println("NMillanR = " + NMillanR.size());
        System.out.println("SMillanL = " + SMillanL.size());
        System.out.println("SMillanR = " + SMillanR.size());
    }

    /**
     * This method simulates the movement of vehicles in the North / South direction. Four queues are operated
     * on at this traffic light, but the NMillanL one will be used to exemplify the functionality of the method.
     * If there are cars waiting to move and while only two are allowed to move, a new vehicle object is
     * instantiated. The first car in the spot is then dequeued and stored into the new car object. Its departure
     * time is set to the current time on the clock, which was previously increased by 3 seconds each time the loop
     * begins.
     *
     * @throws EmptyCollectionException this is done in case the queue is empty
     */
    private void moveNorthSouth() {
        int i = 0;
        while (i < cicloMillan) {
            time += CICLO_TIEMPO;
            try {
                if (!NMillanL.isEmpty()) {
                    Vehicle car = NMillanL.dequeue();
                    car.setDepartureTime(time);
                    outFile.println(car);
                }
            } catch (EmptyCollectionException e) {
            }
            try {
                if (!NMillanR.isEmpty()) {
                    Vehicle car = NMillanR.dequeue();
                    car.setDepartureTime(time);
                    outFile.println(car);
                }
            } catch (EmptyCollectionException e) {
            }
            try {
                if (!SMillanL.isEmpty()) {
                    Vehicle car = SMillanL.dequeue();
                    car.setDepartureTime(time);
                    outFile.println(car);
                }
            } catch (EmptyCollectionException e) {
            }
            try {
                if (!SMillanR.isEmpty()) {
                    Vehicle car = SMillanR.dequeue();
                    car.setDepartureTime(time);
                    outFile.println(car);
                }
            } catch (EmptyCollectionException e) {
            }
            i++;
        }
    }

    /**
     * This method mimics the above method. However, in this instance, only vehicles in the east and west
     * lanes will be operated on.
     */
    private void moveEastWest() {
        int i = 0;
        while (i < cicloGarzon) {
            time += CICLO_TIEMPO;
            try {
                if (!EGarzonL.isEmpty()) {
                    Vehicle car = new Vehicle(0, 0, 0);
                    car = EGarzonL.dequeue();
                    car.setDepartureTime(time);
                    outFile.println(car);
                }
            } catch (EmptyCollectionException e) {
            }
            try {
                if (!EGarzonR.isEmpty()) {
                    Vehicle car = new Vehicle(0, 0, 0);
                    car = EGarzonR.dequeue();
                    car.setDepartureTime(time);
                    outFile.println(car);
                }
            } catch (EmptyCollectionException e) {
            }
            try {
                if (!WGarzonL.isEmpty()) {
                    Vehicle car = new Vehicle(0, 0, 0);
                    car = WGarzonL.dequeue();
                    car.setDepartureTime(time);
                    outFile.println(car);
                }
            } catch (EmptyCollectionException e) {
            }
            try {
                if (!WGarzonR.isEmpty()) {
                    Vehicle car = new Vehicle(0, 0, 0);
                    car = WGarzonR.dequeue();
                    car.setDepartureTime(time);
                    outFile.println(car);
                }
            } catch (EmptyCollectionException e) {
            }
            i++;
        }
    }

    /**
     * This method goes through each queue and checks to see if they are empty. At the end, if all queues are
     * empty, it returns a true value. This method is later used to know when to stop the simulation.
     */
    private boolean queuesEmpty() {
        boolean empty;

        empty = EGarzonL.isEmpty();

        if (empty)
            empty = EGarzonR.isEmpty();
        if (empty)
            empty = WGarzonL.isEmpty();
        if (empty)
            empty = WGarzonR.isEmpty();
        if (empty)
            empty = NMillanL.isEmpty();
        if (empty)
            empty = NMillanR.isEmpty();
        if (empty)
            empty = SMillanL.isEmpty();
        if (empty)
            empty = SMillanR.isEmpty();

        return empty;
    }

}
