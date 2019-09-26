/**
 * an abstract class implementing SimpleSet. You may expand its API if you wish, keeping
 * in mind the minimal API principal.
 * You may implement methods fromSimpleSet or keep them abstract as you see fit
 */
public abstract class SimpleHashSet implements SimpleSet{

    /** The constant which initialize the hash table items */
    protected static final int ZERO_ITEMS = 0;
    /** Describes the higher load factor of a newly created hash set */
    protected static float DEFAULT_HIGHER_CAPACITY = 0.75f;
    /** Describes the lower load factor of a newly created hash set */
    protected static float DEFAULT_LOWER_CAPACITY = 0.25f;
    /** Describes the capacity of a newly created hash set */
    protected static int INITIAL_CAPACITY = 16;
    /** The current higher load */
    protected float upperLoad;
    /** The current lower load */
    protected float lowerLoad;
    /** The current capacity (number of cells) of the table */
    protected int tableSize;
    /** The current number of items in the table */
    protected int currentSize;
    /** Constant representing the increasing factor of the table size */
    public static final int INCREASE_LOAD = 2;
    /** Constant representing the decreasing factor of the table size */
    public static final int DECREASE_LOAD = 2;

    /**
     * Constructs a new hash set with the default capacities given in
     * DEFAULT_LOWER_CAPACITY and DEFAULT_HIGHER_CAPACITY.
     */
    public SimpleHashSet(){
        this.upperLoad = DEFAULT_HIGHER_CAPACITY;
        this.lowerLoad = DEFAULT_LOWER_CAPACITY;
        this.tableSize = INITIAL_CAPACITY;
        this.currentSize = ZERO_ITEMS;
    }

    /**
     * Constructs a new hash set with capacity INITIAL_CAPACITY
     * @param upperLoadFactor higher load factor
     * @param lowerLoadFactor lower load factor
     */
    public SimpleHashSet(float upperLoadFactor, float lowerLoadFactor) {
        this.upperLoad = upperLoadFactor;
        this.lowerLoad = lowerLoadFactor;
        this.tableSize = INITIAL_CAPACITY;
        this.currentSize = ZERO_ITEMS;
    }

    /**
     * Add a specified element to the set if it's not already in it.
     * @param newValue New value to add to the set
     * @return False iff newValue already exists in the set
     */
    @Override
    public abstract boolean add(String newValue);

    /**
     * Look for a specified value in the set.
     * @param searchVal Value to search for
     * @return True iff searchVal is found in the set
     */
    @Override
    public abstract boolean contains(String searchVal);

    /**
     * Remove the input element from the set.
     * @param toDelete Value to delete
     * @return True iff toDelete is found and deleted
     */
    @Override
    public abstract boolean delete(String toDelete);

    /**
     * @return The number of elements currently in the set
     */
    @Override
    public int size() {
        return this.currentSize;
    }

    /**
     * @return The current capacity (number of cells) of the table.
     */
    public int capacity(){
        return this.tableSize;
    }

    /**
     * Clamps hashing indices to fit within the current table capacity.
     * @param index the index before clamping
     * @return an index properly clamped
     */
    protected int clamp(int index) {
        return (index & (this.tableSize - 1));
    }

    /**
     * @return The lower load factor of the table.
     */
    protected float getLowerLoadFactor() {
        return this.lowerLoad;
    }

    /**
     * @return The upper load factor of the table.
     */
    protected float getUpperLoadFactor() {
        return this.upperLoad;
    }


    /**
     * Setter for the new table size. (When resized)
     * @param newSize The new table size. (INT)
     */
    protected void setTableSize(int newSize) {
        this.tableSize = newSize;
    }

    /**
     * Sets the new number of items in the table
     * @param newNum new number of items in the table
     */
    protected void setNumItems(int newNum) {
        this.currentSize = newNum;
    }

    /**
     * This function check if we surpass the upper load factor and if so acts appropriately
     * (When we above the upper load factor increase the size of the hash table
     * @param newSize the new number of items.
     * @return true if the table size should be changed else false.
     */
    protected boolean checkLoadFactorAdd(int newSize) {
        if ((double)newSize/this.capacity() > this.getUpperLoadFactor()) {
            int increasedCapacity = capacity() * INCREASE_LOAD;
            setTableSize(increasedCapacity);
            return true;
        }
        return false;
    }

    /**
     * This function check if we are
     * getting below the lower load factor, and if so acts appropriately
     * when we are below the lower load factor decrease the size of the hash table)
     * @return true when the load factor should change else false.
     */
    protected boolean checkLoadFactorDelete() {
        if ((double)this.size()/capacity() < this.getLowerLoadFactor()) {
            int decreasedCapacity = capacity() / DECREASE_LOAD;
            if (decreasedCapacity < 1) {
                return false;
            }
            setTableSize(decreasedCapacity);
            return true;
        }
        return false;
    }
}