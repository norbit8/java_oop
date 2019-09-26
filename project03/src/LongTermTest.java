import oop.ex3.spaceship.*;
import org.junit.*;
import static org.junit.Assert.*;

/**
 * This test class tests the long term storage functionality.
 */
public class LongTermTest {

    // CONSTANTS FOR FAILING AND SUCCESSION:
    private static final int FAILURE = -1;
    private static final int SUCCESSION = 0;

    /** engine item */
    private final Item engine = ItemFactory.createSingleItem("spores engine");

    /** The amount of storage unit LongTerm. */
    private static final int FULL_CAPACITY = 10000;

    /**
     * Adding 1000 spores engine to the LTS
     */
    @Test
    public void testAddItemToLTS(){
        LongTermStorage lts = new LongTermStorage();
        assertEquals("Test failed: testAddItemToLTS()",
                SUCCESSION ,lts.addItem(engine, 1000));
    }

    /**
     * Adding negative items to the lts.
     */
    @Test
    public void testAddNegativeToAddItem(){
        LongTermStorage lts = new LongTermStorage();
        assertEquals("Test failed: testAddNegativeToAddItem()",
                FAILURE ,lts.addItem(engine, -1));
    }

    /**
     * Adding 0 items to the lts
     */
    @Test
    public void testAddZeroItemsToLTS(){
        LongTermStorage lts = new LongTermStorage();
        assertEquals("Test failed: testAddZeroItemsToLTS()",
                SUCCESSION ,lts.addItem(engine, 0));
    }

    /**
     * Adding above the limit to the lts
     */
    @Test
    public void testAddAboveLimitToLTS(){
        LongTermStorage lts = new LongTermStorage();
        assertEquals("Test failed: testAddZeroItemsToLTS()",
                FAILURE ,lts.addItem(engine, 99999));
    }

    /**
     * Testing the getCapacity function which should return 10000 storage units.
     */
    @Test
    public void testGetCapacityLTS(){
        LongTermStorage lts = new LongTermStorage();
        assertEquals("Test failed: testGetCapacityLTS()", FULL_CAPACITY ,lts.getCapacity());
    }

    /**
     * Testing the effect of addItem function which should result in less capacity.
     * I assume that the getAvailableCapacity() works well.
     * NOTE: THIS TEST ALSO PROVIDE A TEST FOR THE getAvailableCapacity() FUNCTION IF I ASSUME THAT THE
     * addItem FUNCTION WORKS WELL.
     */
    @Test
    public void testAddItemEffect(){
        LongTermStorage lts = new LongTermStorage();
        lts.addItem(engine, 2);
        assertEquals("Test failed: testGetCapacityLTS()",
                9980  ,lts.getAvailableCapacity());
    }

    /**
     * This tests the resetInventory() function, first I add some items to the long-term storage
     * then I use the function to reset the storage,
     * and then I use getAvailableCapacity() function to verify
     * that the first function actually worked.
     * I assume that the getAvailableCapacity() works well.
     */
    @Test
    public void testResetInventory(){
        LongTermStorage lts = new LongTermStorage();
        lts.addItem(engine, 2);
        lts.resetInventory();
        assertEquals("Test failed: testResetInventory()",
                FULL_CAPACITY, lts.getAvailableCapacity());
    }
}