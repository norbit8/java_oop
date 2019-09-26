import oop.ex3.spaceship.*;
import org.junit.*;
import org.junit.internal.runners.statements.Fail;

import static org.junit.Assert.*;

/**
 * This is the test class which tests lockers functionality.
 */
public class LockerTest {

    /** This is a constant for the locker */
    private static final int LOCKER_100_CAPACITY = 100;

    /** engine item */
    private static final Item ENGINE = ItemFactory.createSingleItem("spores engine");

    /** baseball bat */
    private static final Item BASEBALL_BAT = ItemFactory.createSingleItem("baseball bat");

    /** baseball bat */
    private static final Item FOOTBALL = ItemFactory.createSingleItem("football");


    /**
     * resets the long term storage for every test.
     */
    @Before
    public void resetLongTermStorage() {
        Locker.longTerm.resetInventory();
    }

    /**
     * This test the insertion of a regular item it should work and return 0.
     * (assuming I have enough space and not having more than 50% of the locker occupied by the item's volume
     */
    @Test
    public void testRegularInsertion() {
        Locker locker = new Locker(LOCKER_100_CAPACITY);
        assertEquals("Failed test: testRegularInsertion()", 0,
                locker.addItem(ENGINE, 1));
    }

    /**
     * This test tests adding a baseball bat when a football is already resides in the locker
     * and vise versa.
     */
    @Test
    public void testAddBatWithFootball() {
        Locker locker = new Locker(LOCKER_100_CAPACITY);
        locker.addItem(BASEBALL_BAT, 1);
        assertEquals("Failed test: testAddBatWithFootball()",
                -2, locker.addItem(FOOTBALL, 1));
    }

    /**
     * This test tests adding a baseball bat when a football is already resides in the locker
     * and vise versa.
     */
    @Test
    public void testAddFootballWithBat() {
        Locker locker = new Locker(LOCKER_100_CAPACITY);
        Item baseballBat = ItemFactory.createSingleItem("baseball bat");
        Item football = ItemFactory.createSingleItem("football");
        locker.addItem(football, 1);
        assertEquals("Failed test: testAddBatWithFootball()",
                -2, locker.addItem(baseballBat, 1));
    }

    /**
     * This test tests adding items to the locker and It should move some to the LTS
     */
    @Test
    public void testAddItemMoveToLTS() {
        Locker locker = new Locker(LOCKER_100_CAPACITY);
        // spores engine has a volume of 10
        assertEquals("Failed test: testAddItemMoveToLTS()", 1,
                locker.addItem(ENGINE, 6));
    }

    /**
     * Testing if adding more items than the capacity of the locker
     * fails.
     */
    @Test
    public void testAddingAboveTheCapacity() {
        Locker locker = new Locker(1);
        // Baseball bat volume is 2 units.
        Item baseballBat = ItemFactory.createSingleItem("baseball bat");
        assertEquals("Failed test: testAddingAboveTheCapacity()",
                -1, locker.addItem(baseballBat, 1));
    }

    /**
     * This test checks that if an item which is not in the locker is
     * requested then the method will return 0 .
     */
    @Test
    public void testGetItemCount() {
        Locker locker = new Locker(1);
        assertEquals("Failed test: testGetItemCount()", 0,
                locker.getItemCount("nothing"));
    }

    /**
     * This tests the removeItem function when you try to remove
     * a none existing item from the map.
     */
    @Test
    public void testRemoveANoneExistingItem() {
        Locker locker = new Locker(2);
        Item baseballBat = ItemFactory.createSingleItem("baseball bat");
        assertEquals("Failed test: testRemoveANoneExistingItem()", -1,
                locker.removeItem(baseballBat, 1));
    }

    /**
     * Test the get capacity function, by verifying I actually have 13 capacity.
     */
    @Test
    public void testGetCapacity() {
        Locker locker = new Locker(13);
        assertEquals("Failed test: testGetCapacity()", 13,
                locker.getCapacity());
    }

    /**
     * Tests the getAvailableCapacity() function.
     * after adding one baseball bat we should have a current capacity of 8.
     */
    @Test
    public void testGetAvailableCapacity() {
        Locker locker = new Locker(10);
        Item baseballBat = ItemFactory.createSingleItem("baseball bat");
        locker.addItem(baseballBat, 1);
        assertEquals("Failed test: testGetAvailableCapacity()", 8,
                locker.getAvailableCapacity());
    }

    /**
     * This test checks the getInventory() function by adding two items
     * and then calling the getInventory() in order to count them.
     */
    @Test
    public void testGetInventory() {
        Locker locker = new Locker(LOCKER_100_CAPACITY);
        Item helmet3 = ItemFactory.createSingleItem("helmet, size 3");
        locker.addItem(ENGINE, 2);
        locker.addItem(helmet3, 1);
        int counter = locker.getInventory().get("spores engine");
        counter += locker.getInventory().get("helmet, size 3");
        assertEquals("Failed test: testGetInventory()", 3, counter);
    }

    /**
     * This test adds more items than the locker can accept
     * which should result in failure (-1 return error number)
     */
    @Test
    public void testAddMoreThanTheCapacity() {
        Locker locker = new Locker(50);
        assertEquals("Failed test: testAddMoreThanTheCapacity()", -1,
                locker.addItem(ENGINE, 6));
    }

    /**
     * Inserting Items to the locker while the volume is not 50% and than
     * adding more items which will result in LOCKER_100_CAPACITY%
     */
    @Test
    public void testAddingItemsToLockerAndLTS() {
        Locker locker = new Locker(LOCKER_100_CAPACITY);
        locker.addItem(ENGINE, 4);
        assertEquals("Failed test: testGetInventory()", 1,
                locker.addItem(ENGINE, 6));
    }

    /**
     * Adding more than 50% of the locker volume from some item when the LTS is full
     * should fail.
     */
    @Test
    public void testAddingFullLTS() {
        Locker locker1 = new Locker(12500);
        Locker locker2 = new Locker(LOCKER_100_CAPACITY);
        locker1.addItem(ENGINE, 1250);
        assertEquals("Failed test: testAddingFullLTS()", -1,
                locker2.addItem(ENGINE, 6));
    }

    /**
     * When adding 0 of an item it should be successful.
     */
    @Test
    public void testAddItemZero(){
        Locker locker = new Locker(LOCKER_100_CAPACITY);
        assertEquals("Failed test: testAddItemZero()", -1,
                locker.addItem(ENGINE, 0));
    }

    /**
     * In this test I check if it's ok to add negative number of items.
     */
    @Test
    public void testAddNegativeItems(){
        Locker locker = new Locker(LOCKER_100_CAPACITY);
        assertEquals("Failed test: testAddNegativeItems()", -1,
                locker.addItem(ENGINE, -1));
    }

    /**
     * In this test I check if the removal of zero Items is permitted.
     */
    @Test
    public void testRemoveZeroItems(){
        Locker locker = new Locker(LOCKER_100_CAPACITY);
        locker.addItem(ENGINE, 1);
        assertEquals("Failed test: testRemoveZeroItems()", 0,
                locker.removeItem(ENGINE, 0));
    }

    /**
     * In this test I check if I can remove a negative numbers of items
     * which should result in a failure.
     */
    @Test
    public void testRemoveNegativeItems(){
        Locker locker = new Locker(LOCKER_100_CAPACITY);
        locker.addItem(ENGINE, 1);
        assertEquals("Failed test: testRemoveZeroItems()", -1,
                locker.removeItem(ENGINE, -1));
    }

    /**
     * In this test I add a football to the locker
     * and than I add zero baseball bats.
     * and I should get the specific error of adding football and baseball together is not
     * allowed, because It's specified in the pdf that this check should be first before any other checks
     * that the addItem function does before adding an Item.
     */
    @Test
    public void testInsertFootBallAndZeroBaseBall(){
        Locker locker = new Locker(LOCKER_100_CAPACITY);
        Item football = ItemFactory.createSingleItem("football");
        Item baseballBat = ItemFactory.createSingleItem("baseball bat");
        locker.addItem(football, 1);
        assertEquals("Failed test: testInsertFootBallAndZeroBaseBall()", -2,
                locker.addItem(baseballBat, 0));
    }

    /**
     * In this test I verify that removing an Item actually changes the capacity.
     * !! I assume that the getCapacity() function is working.
     */
    @Test
    public void testRemovingItemCapacityChange(){
        Locker locker = new Locker(LOCKER_100_CAPACITY);
        locker.addItem(ENGINE, 3);
        locker.removeItem(ENGINE, 1);
        assertEquals("Failed test: testRemovingItemCapacityChange()",
                80, locker.getAvailableCapacity());
    }

    /**
     * When I add more than 50% of the max capacity from some Item,
     * I should have less and equal 20% of the same item left in the locker.
     * This test checks the term above.
     */
    @Test
    public void testAtLeast20Percent(){
        Locker locker = new Locker(LOCKER_100_CAPACITY);
        locker.addItem(ENGINE, 10);
        // All the engines volume in the locker
        double enginesVolume = locker.getInventory().get("spores engine") * ENGINE.getVolume();
        assertTrue("Failed test: testAtLeast20Percent()",
                enginesVolume <= (double)locker.getCapacity()/5);
    }

    @Test
    public void testFootballZeroBaseBall(){
        Locker locker = new Locker(LOCKER_100_CAPACITY);
        locker.addItem(FOOTBALL, 1);
        locker.removeItem(FOOTBALL, 1);
        assertEquals("Failed test: testFootballZeroBaseBall() adding baseball bat when there is " +
                "0 footballs in the locker should be premitted",
                0, locker.addItem(BASEBALL_BAT, 1));
    }
}