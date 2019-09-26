/**
 * A hash-set based on closed-hashing with quadratic probing.
 * Extends SimpleHashSet.
 */
public class ClosedHashSet extends SimpleHashSet {

    /** The closed hash table*/
    SpecialString[] table;
    /** Constant representing adding one item */
    public static final int ADD_ONE = 1;
    /** Constant representing removing one item */
    public static final int REMOVE_ONE = 1;

    /**
     * A default constructor. Constructs a new, empty table with default initial capacity (16),
     * upper load factor (0.75) and lower load factor (0.25).
     */
    public ClosedHashSet() {
        super();
        table = new SpecialString[this.capacity()];
    }

    /**
     * Constructs a new, empty table with the specified load factors,
     * and the default initial capacity (16).
     * @param upperLoadFactor The upper load factor of the hash table.
     * @param lowerLoadFactor The lower load factor of the hash table.
     */
    public ClosedHashSet(float upperLoadFactor, float lowerLoadFactor) {
        super(upperLoadFactor, lowerLoadFactor);
        table = new SpecialString[this.capacity()];

    }

    /**
     * Data constructor - builds the hash set by adding the elements one by one.
     * Duplicate values should be ignored. The new table has the default values of initial capacity (16),
     * upper load factor (0.75), and lower load factor (0.25).
     * @param data Values to add to the set.
     */
    public ClosedHashSet(String[] data) {
        super();
        table = new SpecialString[this.capacity()];
        for (int index = 0; index < data.length; index++) {
            add(data[index]);
        }
    }

    /**
     * Add a specified element to the set if it's not already in it.
     * @param newValue New value to add to the set
     * @return False iff newValue already exists in the set
     */
    @Override
    public boolean add(String newValue) {
        if (contains(newValue)) return false;
        // I should add the new string
        // so first check if it should change the size of the table
        if (checkLoadFactorAdd(size() + ADD_ONE)) refreshTable();
        for (int i = 0; i < this.capacity(); i++) {
            int maybeIndex = clamp((newValue.hashCode() + ((i + (i * i)) / 2)));
            if (table[maybeIndex] == null || table[maybeIndex].isDeleted()) {
                setNumItems(size() + ADD_ONE);
                table[maybeIndex] = new SpecialString(newValue);
                return true;
            }
        }
        return true;
    }

    /**
    * Look for a specified value in the set.
    * @param searchVal Value to search for
    * @return True iff searchVal is found in the set
    */
    @Override
    public boolean contains(String searchVal) {
        for (int i = 0; i < this.capacity(); i++) {
            int maybeIndex = clamp((searchVal.hashCode() + ((i + (i * i)) / 2)));
            if (table[maybeIndex] == null) return false;
            if (table[maybeIndex].getString().equals(searchVal) && !table[maybeIndex].isDeleted() )
                return true;
        }
        return false;
    }

    /**
     * Remove the input element from the set.
     * @param toDelete Value to delete
     * @return True iff toDelete is found and deleted
     */
    @Override
    public boolean delete(String toDelete) {
        if (!contains(toDelete)) return false;
        for (int i = 0; i < this.capacity(); i++) {
            int maybeIndex = clamp((toDelete.hashCode() + ((i + (i * i)) / 2)));
            if (table[maybeIndex].getString().equals(toDelete)) {
                table[maybeIndex].deleteString();
                setNumItems(size() - REMOVE_ONE);
                if (checkLoadFactorDelete()) refreshTable();
                return true;
            }
        }
        return false;
    }

    /**
     * After changing the size of the hash table
     * We need to add all the elements back to the table and REHASH them.
     */
    private void refreshTable() {
        SpecialString[] temp = table.clone();
        table = new SpecialString[this.capacity()];
        this.setNumItems(0);
        for (int i = 0; i < temp.length; i++) {
            if (temp[i] == null) continue;
            if (!temp[i].isDeleted()) {
                this.add(temp[i].getString());
            }
        }
    }
}