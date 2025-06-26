import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Main class to read and process commands from an input file using a FleetAction object
 * and write the output to an output file.
 */
public class Main {

    /**
     * The main method that serves as the entry point for the program.
     * It reads input commands from given input file, processes them, and writes output to "output.txt".
     */
    public static void main(String[] args) {
        String inputFile = args[0];
        String outputFile = args[1];
        FleetAction fleetAction = new FleetAction();

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                String command = parts[0];

                switch (command) {
                    case "create_parking_lot":
                        // Create a new parking lot with a capacity constraint and a truck limit
                        int capacityConstraint = Integer.parseInt(parts[1]);
                        int truckLimit = Integer.parseInt(parts[2]);
                        fleetAction.createParkingLot(capacityConstraint, truckLimit);
                        // No output needed for this command
                        break;

                    case "delete_parking_lot":
                        // Delete a parking lot by its capacity constraint
                        int deleteCapacity = Integer.parseInt(parts[1]);
                        fleetAction.deleteParkingLot(deleteCapacity);
                        // No output needed for this command
                        break;

                    case "add_truck":
                        // Add a truck to the fleet with a specified ID and capacity
                        int truckId = Integer.parseInt(parts[1]);
                        int capacity = Integer.parseInt(parts[2]);
                        int result = fleetAction.addingTruck(truckId, capacity);
                        writer.write(result + "\r\n"); // Write the result to output.txt
                        break;

                    case "ready":
                        // Mark trucks in a parking lot as ready based on capacity constraint
                        int readyCapacity = Integer.parseInt(parts[1]);
                        String readyResult = fleetAction.readyCommand(readyCapacity);
                        writer.write(readyResult + "\n"); // Write the result to output.txt
                        break;

                    case "load":
                        // Load a specified amount into a truck or parking lot
                        int loadCapacity = Integer.parseInt(parts[1]);
                        int loadAmount = Integer.parseInt(parts[2]);
                        String loadResult = fleetAction.receivingLoad(loadCapacity, loadAmount);
                        writer.write(loadResult + "\r\n"); // Write the result to output.txt
                        break;

                    case "count":
                        // Count the number of trucks in a parking lot based on a capacity constraint
                        int countCapacity = Integer.parseInt(parts[1]);
                        int truckCount = fleetAction.countingTruck(countCapacity);
                        writer.write(truckCount + "\r\n"); // Write the truck count to output.txt
                        break;

                    default:
                        // Handle unknown commands
                        System.out.println("Unknown command: " + command);
                        break;
                }
            }
        } catch (IOException e) {
            // Handle exceptions related to file I/O
            e.printStackTrace();
        }
    }
}
