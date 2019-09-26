/**
 * A hash-set based on chaining.
 */
public class OpenHashSet extends SimpleHashSet {

    /** The hash table */
    private WrapperLinkedList[] table;
    /** Constant representing removing one item */
    public static final int REMOVE_ONE = 1;
    /** Constant representing adding one item */
    public static final int ADD_ONE = 1;

    /**
     * A default constructor.
     */
    public OpenHashSet(){
        super();
        table = new WrapperLinkedList[this.capacity()];
        initTable();
    }

    /**
     * Constructs a new, empty table with the specified load
     * factors, and the default initial capacity (16).
     * @param upperLoadFactor higher load factor.
     * @param lowerLoadFactor lower load factor.
     */
    public OpenHashSet(float upperLoadFactor, float lowerLoadFactor){
        super(upperLoadFactor, lowerLoadFactor);
        table = new WrapperLinkedList[this.capacity()];
        initTable();
    }

    /**
     * Data constructor - builds the hash set by adding the elements one by one.
     * @param data An array of strings of all the data that should be in the hash table.
     */
    public OpenHashSet(java.lang.String[] data){
        super();
        table = new WrapperLinkedList[this.capacity()];
        initTable();
        for (int index = 0; index < data.length; index++) {
            add(data[index]);
        }
    }

    /**
     * This function initialize the table by creating new wrapperLinkedList objects
     */
    private void initTable(){
        for (int index = 0; index < table.length; index++) {
            this.table[index] = new WrapperLinkedList();
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
        else {
            if (checkLoadFactorAdd(size() + ADD_ONE)) refreshTable();
            int index = clamp(newValue.hashCode());
            this.table[index].push(newValue);
            setNumItems(size() + ADD_ONE);
            return true;
        }
    }

    /**
     * After changing the size of the hash table
     * We need to add all the elements back to the table and REHASH them.
     */
    private void refreshTable() {
        WrapperLinkedList[] tempTable = this.table.clone();
        // setting a new table with the new capacity
        this.table = new WrapperLinkedList[capacity()];
        this.initTable();
        this.setNumItems(0);
        // adding all the values
        for (int index = 0; index < tempTable.length; index++) {
            while(!tempTable[index].isEmpty()){
                add(tempTable[index].pop());
            }
        }
    }

    /**
     * Look for a specified value in the set.
     * @param searchVal Value to search for
     * @return True iff searchVal is found in the set
     */
    @Override
    public boolean contains(String searchVal) {
        int index = clamp(searchVal.hashCode());
        return table[index].contains(searchVal);
    }

    /**
     * Remove the input element from the set.
     * @param toDelete Value to delete
     * @return True iff toDelete is found and deleted
     */
    @Override
    public boolean delete(String toDelete) {
        if (!this.contains(toDelete)) return false;
        this.setNumItems(this.size() - REMOVE_ONE);
        int index = clamp(toDelete.hashCode());
        table[index].remove(toDelete);
        if (this.checkLoadFactorDelete()) refreshTable();
        return true;
    }
}