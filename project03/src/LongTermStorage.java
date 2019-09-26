import oop.ex3.spaceship.*;

/**
 * On board of the spaceship USS Discovery there are multiple lockers to keep items
 * the crew might need at any point in time.
 * This class represents the Long-Term Storage in the spaceship.
 */
public class LongTermStorage extends Storage{

    /** The amount of storage unit this locker currently holds. */
    private int capacity;

    /** The amount of storage unit LongTerm. */
    private static final int FULL_CAPACITY = 10000;

    /**
     *  Constructor - initializes a Long-Term Storage object.
     */
    public LongTermStorage() {
        this.maxCapacity = FULL_CAPACITY;
        this.capacity = FULL_CAPACITY; // initial current capacity
    }

    /**
     * Adds n items of the given type to the locker.
     * @param item The item you wish to add.
     * @param n The amount of the item you wish to add.
     * @return 0 => If the addition is successful.
     *         -1 => If n Items cannot be added to the Long-term storage at this time, no Items should be added.
     */
    public int addItem(Item item, int n){
        int capacityToBeAdded = item.getVolume() * n;
        if (n == 0) return SUCCESS;
        if (checkSideOptions(item, n)) return FAILED;
        if (capacityToBeAdded > this.capacity) {
            System.out.println("Error: Your request cannot be completed at this time." +
                               " Problem: no room for " + n + "items of type " + item.getType());
            return FAILED;
        }
        else {
            this.storage.put(item.getType(), n);
            this.capacity -= capacityToBeAdded;
            return SUCCESS;
        }
    }

    /**
     * This method returns the locker's available capacity
     * @return available capacity.
     */
    public int getAvailableCapacity() {
        return this.capacity;
    }

    /**
     * This method resets the long-term storage's inventory.
     */
    public void resetInventory() {
        this.storage.clear();
        this.capacity = this.maxCapacity;
    }
}
