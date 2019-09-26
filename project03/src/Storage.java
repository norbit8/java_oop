import oop.ex3.spaceship.*;
import java.util.HashMap;
import java.util.Map;

/**
 * On board of the spaceship USS Discovery there are multiple lockers to keep items
 * the crew might need at any point in time.
 * This class represents some general storage in the spaceship.
 */
public abstract class Storage {

    /** When any function fails to continue return -1 */
    public static final int FAILED = -1;

    /** when succeeded to add an item to the locker */
    public static final int SUCCESS = 0;

    /** The amount of storage unit this locker holds. */
    protected int maxCapacity;

    /** The Hash table which stores all the lockers items and numbers */
    protected Map<String, Integer> storage = new HashMap<String, Integer>();

    /** when zero items were found */
    private final int noItems = 0;

    /** A general error constant */
    public static final String GENERAL_ERROR = "Error: Your request cannot be completed at this time.";

    /** PROBLEM NEGATIVE NUMBER OF ITEMS CONSTANT MESSAGE */
    public static final String PROBLEM_NEGATIVE = " Problem: cannot remove a negative" +
                                                  " number of items of type ";

    /** problem the locker doesnt contain n numbers of item ... */
    public static final String PROBLEM_DOES_NOT_CONTAIN = " Problem: the locker does not contain ";

    /** problem the locker cannot contain ... */
    public static final String PROBLEM_CANNOT_CONTAIN = " Problem: the locker cannot" +
                                                         " contain items of type ";

    /** When a problem of contradiction is happening drop this message: (constant)*/
    public static final String PROBLEM_CONTRADICTION = ", as it contains a contradicting item";

    /** A items type error constant */
    public static final String ITEMS_ERROR = " items of type ";

    /** No room error constant */
    public static final String NO_ROOM_ERROR = " Problem: no room for ";

    /** Warning items moved constant */
    public static final String WARNING = "Warning: Action successful," +
                                         " but has caused items to be moved to storage";
    /**
     * Adds n items of the given type to the locker.
     */
    public abstract int addItem(Item item, int n);

    /**
     * This method returns the number of Items of type *type*.
     * @param type An item type (i.e: football , baseball bat ...)
     * @return The number of Items of the type type.
     */
    public int getItemCount(String type) {
        if (!storage.containsKey(type)) {
            return noItems;
        }
        return storage.get(type);
    }

    /**
     * This method returns a map of all the items types contained in the locker,
     * and their respective quantities
     * @return A copy for the storage object (SO THE USER CANT CHANGE IT).
     */
    public Map<String, Integer> getInventory() {
        Map<String, Integer> copy = new HashMap<>(this.storage);
        return copy;
    }

    /**
     * This method returns the locker's total capacity
     * @return The locker's total capacity
     */
    public int getCapacity() {
        return this.maxCapacity;
    }

    /**
     * This method returns the locker's available capacity
     * @return should return the available capacity.
     */
    public abstract int getAvailableCapacity();

    /**
     * This is a helper function to verify that the item which is trying to be added to the locker
     * is (a) Legal
     * and (b) The amount of it is not negative
     * @param item The item you wish to add.
     * @param n The amount of the item you wish to add.
     * @return True if the item is not legal or the amount is negative.
     *         False if it can be added (Potentially).
     */
    protected boolean checkSideOptions(Item item, int n){
        if (n <= 0) {
            System.out.println(GENERAL_ERROR);
            return true;
        }
        if (ItemFactory.createSingleItem(item.getType()) == null) {
            System.out.println(GENERAL_ERROR);
            return true;
        }
        return false;
    }
}
