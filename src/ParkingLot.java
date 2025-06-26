/**
 * The ParkingLot class represents a parking lot with a specified capacity constraint and truck limit.
 * It manages trucks in two sections: waiting and ready.
 */
public class ParkingLot {
    private int capacityConstraint; // The capacity constraint of the parking lot
    private int truckLimit; // The maximum number of trucks the parking lot can hold
    private Queue<Truck> waitingSection; // Queue for trucks waiting to be moved to the ready section
    private Queue<Truck> readySection; // Queue for trucks ready to be loaded

    /**
     * Default constructor initializing empty waiting and ready sections.
     */
    ParkingLot() {
        waitingSection = new Queue<>();
        readySection = new Queue<>();
    }

    /**
     * Constructor to create a ParkingLot with a specified capacity constraint and truck limit.
     *
     * @param capacityConstraint the capacity constraint of the parking lot
     * @param truckLimit         the maximum number of trucks allowed in the parking lot
     */
    ParkingLot(int capacityConstraint, int truckLimit) {
        this();
        this.capacityConstraint = capacityConstraint;
        this.truckLimit = truckLimit;
    }

    /**
     * Adds a truck to the waiting section if the total number of trucks in both
     * waiting and ready sections is less than the truck limit.
     *
     * @param truck the Truck object to be added
     * @return true if the truck was successfully added to the waiting section, false otherwise
     */
    public boolean addTruckToWaiting(Truck truck) {
        if (waitingSection.size() + readySection.size() < truckLimit) {
            waitingSection.add(truck);
            return true;
        }
        return false;
    }

    /**
     * Moves the next truck from the waiting section to the ready section.
     *
     * @return the Truck object moved to the ready section, or null if the waiting section is empty
     */
    public Truck moveTruckToReady() {
        if (!waitingSection.isEmpty()) {
            Truck truck = waitingSection.poll();
            readySection.add(truck);
            return truck;
        }
        return null;
    }

    /**
     * Checks if a truck can be added to the parking lot (either waiting or ready section).
     *
     * @return true if a truck can be added, false if the truck limit has been reached
     */
    public boolean canAddTruck() {
        return (waitingSection.size() + readySection.size()) < truckLimit;
    }

    /**
     * Gets the capacity constraint of the parking lot.
     *
     * @return the capacity constraint
     */
    public int getCapacityConstraint() {
        return capacityConstraint;
    }

    /**
     * Gets the truck limit of the parking lot.
     *
     * @return the maximum number of trucks allowed
     */
    public int getTruckLimit() {
        return truckLimit;
    }

    /**
     * Gets the waiting section queue.
     *
     * @return the queue of trucks in the waiting section
     */
    public Queue<Truck> getWaitingSection() {
        return waitingSection;
    }

    /**
     * Gets the ready section queue.
     *
     * @return the queue of trucks in the ready section
     */
    public Queue<Truck> getReadySection() {
        return readySection;
    }
}
