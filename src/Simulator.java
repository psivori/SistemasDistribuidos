import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import Enums.Direction;
import Enums.Lane;
import Enums.Street;

/**
 * Created by carlospienovi1 on 5/28/16.
 */
public class Simulator {

    /**
     * This integer value will keep track of the vehicle numbers of the cars that arrive at the intersection.
     */
    int vehicleNum = 1;

    /**
     * This is a constructor. It sets up the ability for a simulator object to be instantiated.
     */
    public Simulator() {
    }
 
    public void simulate() {
        try {
           

            //populate((int) (Math.random() * (13 - 7) + 7));
            while (!queuesEmpty()) {
                checkBus();
                outFile.print("Av. Millan SUR LEFT = " + SMillanL.size() + "\n");
                outFile.print("Av. Millan SUR RIGHT = " + SMillanR.size() + "\n");
                outFile.print("Av. Millan NORTE LEFT = " + NMillanL.size() + "\n");
                outFile.print("Av. Millan NORTE RIGHT = " + NMillanR.size() + "\n");
                outFile.print("#omnibus = " + (EGarzonL.size() + WGarzonL.size()) + "\n");
                outFile.print("Ciclo verde Av. Millan = " + cicloMillan + "\n");

                moveNorthSouth();
                //populate((int) (Math.random() * (16 - 8) + 8));
                outFile.println();
                moveEastWest();
                //populate((int) (Math.random() * (16 - 3) + 3));
                outFile.println();
            }
            outFile.close();
        } catch (IOException e) {
            System.err.println("Error printing to file");
        }
    }

   

}
