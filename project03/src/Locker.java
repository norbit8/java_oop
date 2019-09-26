import oop.ex3.spaceship.*;
import java.lang.Math;

/**
 * On board of the spaceship USS Discovery there are multiple lockers to keep items
 * the crew might need at any point in time.
 * This class represents a general storage in the spaceship.
 */
public class Locker extends Storage {

    /** The amount of storage unit this locker currently holds. */
    private int capacity;

    /** A static field of the long term storage (There is one for all the lockers on the spaceship) */
    protected static LongTermStorage longTerm = new LongTermStorage();

    /** constant of a football*/
    private final String football = "football";

    /** constant of a baseball bat*/
    private final String baseballBat = "baseball bat";

    /** constant of failing adding football and baseball bat together */
    private final int footballFail = -2;

    /** constant of moving items to the LongTerm storage */
    private final int successMovedItems = 1;

    /**
     * The constructor of the locker class.
     * @param capacity The total amount of storage units the locker can hold.
     */
    public Locker(int capacity) {
        if (capacity < 0){
            this.maxCapacity = 0;
            this.capacity = 0;
            return;
        }
        this.maxCapacity = capacity;
        this.capacity = capacity;
    }

    /**
     * Adds n items of the given type to the locker.
     * @param item The item you wish to add.
     * @param n The amount of the item you wish to add.
     * @return 0 => If the addition is successful and doesn't cause Items to be moved to the long-term storage,
     *         -1 => If n Items cannot be added to the locker at this time, no Items should be added.
     *         1 => If it causes n* items to be moved to long-term storage and it can accommodate all n* items.
     *         -2 => When adding a football when a baseball bat is already in the locker then
     *         it fails and return -2 also vise versa (when trying to add a baseball bat when
     *         a football is already in the locker)
     */
    public int addItem(Item item, int n) {
        int totalCapacity = item.getVolume() * n; // The total capacity the addition will take.
        int currentItemVolume = 0, currentItemCount = 0;
        if (storage.containsKey(item.getType())) {
            currentItemCount = storage.get(item.getType());
            currentItemVolume = currentItemCount * item.getVolume(); // The volume the
            // item currently takes in the locker
        }
        double totalVolume = currentItemVolume + totalCapacity; // the total volume the item will
        // take after the addition
        int numberOfItems = (int) Math.floor((double)(this.maxCapacity/5)/item.getVolume()); // The number of
        // items that I need to have in order to have at most 20% of the locker from this item.
        if (checkBaseballFootball(item.getType())) return footballFail; // football and baseball check
        if (checkSideOptions(item, n)) return FAILED;
        if (this.capacity < totalCapacity) {
            // If n Items cannot be added to the locker at this time, no Items should be added
            System.out.println(GENERAL_ERROR + NO_ROOM_ERROR + n + ITEMS_ERROR + item.getType());
            return FAILED;
        }
        if (((2*(totalVolume)) > maxCapacity) && checkLongTerm(item, n, numberOfItems)){
            longTerm.addItem(item, currentItemCount + n - numberOfItems);
            addThemUp(item, (numberOfItems - currentItemCount));
            System.out.println(WARNING);
            return successMovedItems;
        }
        if (((2*(totalVolume)) > maxCapacity) && !checkLongTerm(item, n, numberOfItems)) {
            System.out.println(GENERAL_ERROR + NO_ROOM_ERROR +
                              (currentItemCount + n - numberOfItems) + ITEMS_ERROR + item.getType());
            return FAILED;
        }
        addThemUp(item, n); // if I have enough space in the locker and it wont be >50% then just store it.
        return SUCCESS;
    }

    /**
     * Helper function which adds the object to the map data structure.
     * @param item The item you wish to add.
     * @param n The amount of the item you wish to add.
     */
    private void addThemUp(Item item, int n){
        int currentItemCount = 0;
        if (storage.containsKey(item.getType())) {
            currentItemCount = storage.get(item.getType());
        }
        storage.put(item.getType(), currentItemCount + n);
        this.capacity -= (n * item.getVolume());
    }

    /**
     * This method checks that I have enough available capacity in the Long-Term storage for the
     * items I try to store.
     * @param item The item you wish to add.
     * @param n The amount of the item you wish to add.
     * @param numberOfItems The number of items to store in the locker (less than or equal 20% of the items )
     * @return True if I have enough storage in the LTS, false otherwise.
     */
    private boolean checkLongTerm(Item item,int n, int numberOfItems){
        int totalItems = n;
        if (storage.containsKey(item.getType())){
            totalItems = storage.get(item.getType()) + n;
        }
        int numberOfItemsToLTS = totalItems - numberOfItems;
        int capacityToBeStoredInLTS = numberOfItemsToLTS * item.getVolume();
        return (longTerm.getAvailableCapacity() >= capacityToBeStoredInLTS);
    }

    /**
     * This function helps removing items from the locker.
     * @param item The item you wish to add.
     * @param n The amount of the item you wish to add.
     * @return 0 for success, and -1 for failure.
     */
    public int removeItem(Item item, int n) {
        if (n < 0) {
            System.out.println(GENERAL_ERROR + PROBLEM_NEGATIVE + item.getType());
            return FAILED;
        }
        else if (!isAvailableForRemove(item, n)) {
            System.out.println(GENERAL_ERROR + PROBLEM_DOES_NOT_CONTAIN + n + ITEMS_ERROR + item.getType());
            return FAILED;
        }
        int itemCurrentAmount = storage.get(item.getType());
        storage.put(item.getType(), itemCurrentAmount - n);
        this.capacity += n * item.getVolume();
        return SUCCESS;
    }

    /**
     * Helper function to see if I can remove an Item from the map
     * which means it is first exists there and second that the amount which is
     * requested to be removed is actually available.
     * @param item The item you wish to add.
     * @param n The amount of the item you wish to add.
     * @return True if I can permit the removal of n items, false else-wise.
     */
    private boolean isAvailableForRemove(Item item, int n) {
        return storage.containsKey(item.getType()) && storage.get(item.getType()) >= n;
    }

    /**
     * This method returns the locker's available capacity
     * @return available capacity.
     */
    public int getAvailableCapacity() {
        return this.capacity;
    }

    /**
     * Checks whether a football is being added with a baseball bat or vise versa
     * @param type A string represents the name of the item.
     * @return True if there is conflict, false otherwise.
     */
    private boolean checkBaseballFootball(String type){
        if (type.equals(football) && storage.containsKey(baseballBat)) {
            if(checkNotEmpty(baseballBat, type)) {
                return true;
            }
        }
        if (type.equals(baseballBat) && storage.containsKey(football)) {
            return checkNotEmpty(football, type);
        }
        return false;
    }

    /**
     * This helper function helps detect whether the locker has more than 0 of an item
     * @param item The item you wish to add.
     * @param type A string represents the name of the item.
     * @return true if there is more than one, false otherwise.
     */
    private boolean checkNotEmpty(String item, String type){
        if (storage.get(item) > 0) {
            System.out.println(GENERAL_ERROR + PROBLEM_CANNOT_CONTAIN + type + PROBLEM_CONTRADICTION);
            return true;
        }
        return false;
    }
}