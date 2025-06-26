/**
 * The Truck class represents a truck with a unique ID, maximum capacity, and current load.
 * It provides methods to manage and query the state of the truck's load.
 */
public class Truck {
    private int ID; // The unique identifier for the truck
    private int maxCapacity; // The maximum capacity the truck can hold
    private int load; // The current load of the truck

    /**
     * Default constructor for the Truck class.
     * Initializes the truck with no ID, zero capacity, and zero load.
     */
    Truck() {
    }

    /**
     * Constructor to create a Truck with a specified ID and maximum capacity.
     * The initial load is set to 0.
     *
     * @param ID           the unique identifier for the truck
     * @param maxCapacity  the maximum capacity the truck can hold
     */
    Truck(int ID, int maxCapacity) {
        this.ID = ID;
        this.maxCapacity = maxCapacity;
        this.load = 0;
    }

    /**
     * Gets the unique identifier of the truck.
     *
     * @return the ID of the truck
     */
    public int getId() {
        return ID;
    }

    /**
     * Gets the maximum capacity of the truck.
     *
     * @return the maximum capacity
     */
    public int getMaxCapacity() {
        return maxCapacity;
    }

    /**
     * Sets the maximum capacity of the truck.
     *
     * @param givenCapacity the new maximum capacity to be set
     * @return the updated maximum capacity
     */
    public int setMaxCapacity(int givenCapacity) {
        return this.maxCapacity = givenCapacity;
    }

    /**
     * Gets the current load of the truck.
     *
     * @return the current load
     */
    public int getLoad() {
        return load;
    }

    /**
     * Sets the current load of the truck.
     *
     * @param load the new load to be set
     */
    public void setLoad(int load) {
        this.load = load;
    }

    /**
     * Calculates and returns the remaining capacity of the truck.
     *
     * @return the remaining capacity (maxCapacity - load)
     */
    public int getRemainingCapacity() {
        return maxCapacity - load;
    }

    /**
     * Checks if the truck is full.
     *
     * @return true if the truck's load is greater than or equal to its maximum capacity, false otherwise
     */
    public boolean isFull() {
        return load >= maxCapacity;
    }
}
