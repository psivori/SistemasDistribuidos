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
 
    public void simulate() {
        try {
            fw = new FileWriter("output.txt");
            bw = new BufferedWriter(fw);
            outFile = new PrintWriter(bw);

            outFile.print("---Start of simulation, time set to 0.--- \n");
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


}
