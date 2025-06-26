/**
 * The FleetAction class manages operations related to parking lots, trucks, and loading/unloading operations.
 * It utilizes AVL trees for efficient searching and storing capacity constraints and truck data.
 */
public class FleetAction {
    AVLTree parkingLotsTree; // AVL tree containing all parking lots by their capacity constraints
    AVLTree readytoLoadParkingLots; // AVL tree containing parking lots ready to load
    AVLTree addTrucktoWaitingParkingLots; // AVL tree containing parking lots for addingTruck method.
    AVLTree readytoWaitingParkingLots; // AVL tree containing parking lots transitioning for readyCommand method.
    HashMap<Integer, ParkingLot> parkingLotsMap; // HashMap mapping capacity constraints to ParkingLot objects

    /**
     * Constructor to initialize AVL trees and HashMap for managing parking lots and their states.
     */
    FleetAction() {
        parkingLotsTree = new AVLTree();
        readytoLoadParkingLots = new AVLTree();
        readytoWaitingParkingLots = new AVLTree();
        addTrucktoWaitingParkingLots = new AVLTree();
        parkingLotsMap = new HashMap<>();
    }

    /**
     * Creates a parking lot with a specified capacity constraint and truck limit.
     * If the parking lot already exists, the operation is skipped.
     *
     * @param givenCapacityConstraint the capacity constraint for the parking lot
     * @param givenTruckLimit         the truck limit for the parking lot
     */
    public void createParkingLot(int givenCapacityConstraint, int givenTruckLimit) {
        if (!parkingLotsMap.containsKey(givenCapacityConstraint)) {
            ParkingLot newLot = new ParkingLot(givenCapacityConstraint, givenTruckLimit);
            parkingLotsMap.put(givenCapacityConstraint, newLot);
            parkingLotsTree.insert(givenCapacityConstraint);
            addTrucktoWaitingParkingLots.insert(givenCapacityConstraint);
        }
    }

    /**
     * Deletes a parking lot with a given capacity constraint.
     * Removes associated data from AVL trees and HashMap.
     *
     * @param givenCapacityConstraint the capacity constraint of the parking lot to delete
     */
    public void deleteParkingLot(int givenCapacityConstraint) {
        if (parkingLotsMap.containsKey(givenCapacityConstraint)) {
            parkingLotsTree.delete(givenCapacityConstraint);
            parkingLotsMap.remove(givenCapacityConstraint);
            readytoLoadParkingLots.delete(givenCapacityConstraint);
            readytoWaitingParkingLots.delete(givenCapacityConstraint);
            addTrucktoWaitingParkingLots.delete(givenCapacityConstraint);
        }
    }

    /**
     * Adds a truck to the waiting list of the appropriate parking lot.
     * If no suitable parking lot is found, returns -1.
     *
     * @param givenID      the ID of the truck to be added
     * @param givenCapacity the capacity of the truck to be added
     * @return the capacity constraint of the parking lot where the truck was added, or -1 if unsuccessful
     */
    public int addingTruck(int givenID, int givenCapacity) {
        Truck newTruck = new Truck(givenID, givenCapacity);
        int ansCapacity = givenCapacity;
        if (!parkingLotsMap.containsKey(ansCapacity)) {
            ansCapacity = addTrucktoWaitingParkingLots.findLargestSmallerThan(ansCapacity);
        }
        if (ansCapacity == -1) {
            return -1;
        }
        while (!parkingLotsMap.get(ansCapacity).addTruckToWaiting(newTruck)) {
            ansCapacity = parkingLotsTree.findLargestSmallerThan(ansCapacity);
            if (ansCapacity == -1) {
                return -1;
            }
        }
        if (!readytoWaitingParkingLots.search(ansCapacity)) {
            readytoWaitingParkingLots.insert(ansCapacity);
        }
        return ansCapacity;
    }

    /**
     * Moves a truck to the ready state for loading from a parking lot with the specified capacity constraint.
     * If no suitable parking lot is found, returns "-1".
     *
     * @param givenCapacity the capacity constraint of the parking lot
     * @return the ID of the truck and the capacity constraint, or "-1" if no suitable truck is found
     */
    public String readyCommand(int givenCapacity) {
        int anscapacity = givenCapacity;
        if (!parkingLotsMap.containsKey(anscapacity)) {
            anscapacity = readytoWaitingParkingLots.findSmallestGreaterThan(anscapacity);
        }
        if (anscapacity == -1) {
            return "-1";
        }
        Truck tempTruck = parkingLotsMap.get(anscapacity).moveTruckToReady();

        while (tempTruck == null) {
            readytoWaitingParkingLots.delete(anscapacity);
            anscapacity = readytoWaitingParkingLots.findSmallestGreaterThan(anscapacity);
            if (anscapacity == -1) {
                return "-1";
            }
            tempTruck = parkingLotsMap.get(anscapacity).moveTruckToReady();
        }

        if (!readytoLoadParkingLots.search(anscapacity)) {
            readytoLoadParkingLots.insert(anscapacity);
        }
        if (parkingLotsMap.get(anscapacity).getWaitingSection().isEmpty()) {
            readytoWaitingParkingLots.delete(anscapacity);
        }
        return tempTruck.getId() + " " + anscapacity;
    }

    /**
     * Distributes a load to trucks in ready parking lots, starting with the given capacity.
     * If the load cannot be fully distributed, it returns partial or no results.
     *
     * @param givenCapacity the initial capacity constraint to start loading
     * @param givenLoadAmount the amount of load to distribute
     * @return a string representing truck IDs and their assigned capacities, or "-1" if unsuccessful
     */
    public String receivingLoad(int givenCapacity, int givenLoadAmount) {
        if (!readytoLoadParkingLots.search(givenCapacity)) {
            givenCapacity = readytoLoadParkingLots.findSmallestGreaterThan(givenCapacity);
        }
        boolean loadDistributed = false;
        StringBuilder ans = new StringBuilder();

        while (givenLoadAmount > 0 && givenCapacity != -1) {
            ParkingLot currentLot = parkingLotsMap.get(givenCapacity);
            if (currentLot == null || currentLot.getReadySection().isEmpty()) {
                readytoLoadParkingLots.delete(givenCapacity);
                givenCapacity = readytoLoadParkingLots.findSmallestGreaterThan(givenCapacity);
                continue;
            }

            Queue<Truck> readyQueue = currentLot.getReadySection();

            while (!readyQueue.isEmpty() && givenLoadAmount > 0) {
                Truck truck = readyQueue.poll();
                if (parkingLotsMap.get(givenCapacity).getReadySection().isEmpty()) {
                    readytoLoadParkingLots.delete(givenCapacity);
                }
                int truckRemainingCapacity = truck.getRemainingCapacity();
                int loadToAssign = Math.min(truckRemainingCapacity, Math.min(givenLoadAmount, givenCapacity));

                truck.setLoad(truck.getLoad() + loadToAssign);
                givenLoadAmount -= loadToAssign;

                int newCapacityConstraint;
                boolean isTruckFullyLoaded = truck.getLoad() == truck.getMaxCapacity();

                if (isTruckFullyLoaded) {
                    truck.setLoad(0);
                    newCapacityConstraint = fullyLoadedTruck(truck, truck.getMaxCapacity());
                } else {
                    newCapacityConstraint = fullyLoadedTruck(truck, truck.getRemainingCapacity());
                }

                if (ans.length() > 0) {
                    ans.append(" - ");
                }
                ans.append(truck.getId()).append(" ");

                if (newCapacityConstraint == -1) {
                    ans.append("-1");
                } else {
                    parkingLotsMap.get(newCapacityConstraint).addTruckToWaiting(truck);
                    if (!readytoWaitingParkingLots.search(newCapacityConstraint)) {
                        readytoWaitingParkingLots.insert(newCapacityConstraint);
                    }
                    ans.append(newCapacityConstraint);
                }

                loadDistributed = true;
            }

            if (currentLot.getReadySection().isEmpty()) {
                readytoLoadParkingLots.delete(givenCapacity);
            }

            if (givenLoadAmount > 0) {
                givenCapacity = readytoLoadParkingLots.findSmallestGreaterThan(givenCapacity);
            }
        }

        return loadDistributed ? ans.toString() : "-1";
    }

    /**
     * Finds a suitable parking lot for a fully loaded truck and returns the new capacity constraint.
     * If no suitable parking lot is found, returns -1.
     *
     * @param givenTruck the truck that is fully loaded
     * @param targetCapacity the target capacity for the truck
     * @return the capacity constraint of the suitable parking lot, or -1 if none found
     */
    private int fullyLoadedTruck(Truck givenTruck, int targetCapacity) {
        int capacityConstraint = targetCapacity;

        if (!parkingLotsMap.containsKey(capacityConstraint)) {
            capacityConstraint = addTrucktoWaitingParkingLots.findLargestSmallerThan(capacityConstraint);
        }

        while (capacityConstraint != -1) {
            ParkingLot lot = parkingLotsMap.get(capacityConstraint);
            if (lot.canAddTruck()) {
                return capacityConstraint;
            }
            capacityConstraint = addTrucktoWaitingParkingLots.findLargestSmallerThan(capacityConstraint);
        }

        return -1;
    }

    /**
     * Counts the number of trucks in parking lots with capacity constraints greater than the given value.
     *
     * @param givenCapacity the capacity constraint to start counting from
     * @return the total number of trucks
     */
    public int countingTruck(int givenCapacity) {
        int ans = 0;

        for (Integer capacity : parkingLotsTree.tailSet(givenCapacity + 1, true)) {
            ans += parkingLotsMap.get(capacity).getReadySection().size() + parkingLotsMap.get(capacity).getWaitingSection().size();
        }

        return ans;
    }
}
