import java.util.HashSet;
import java.util.LinkedList;
import java.util.TreeSet;

/**
 * This is the testers class where we test all of the data-structures we created against each other
 * and against some selected java's native data structures
 * NOTE: if you want to disable or enable some test, you can do so by changing the state of the final
 * boolean variables TEST1,...,TEST6.
 */
public class SimpleSetPerformanceAnalyzer {

    /** The number of data-structures we test */
    public static final int NUM_DATA_STRUCTURES = 5;
    /** Difference between nano second and millisecond */
    public static final int NANO_TO_MILI = 1000000;
    /** "hi" string as constant to use by some tests */
    public static final String WORD_TO_SEARCH = "hi";
    /** FINAL int representing the linked list place in the array */
    public static final int LINKED_LIST = 3;
    /** Constant iteration number to warm up */
    public static final int WARM_UP = 70000;
    /** Constant iteration number to warm up for the linked list */
    public static final int WARM_UP_LINKED_LIST = 7000;
    /** Constant iteration number to test */
    public static final int ITERATIONS_TEST = 70000;
    /** Constant iteration number to test for the linked list */
    public static final int ITERATION_TEST_LL = 7000;
    /** TEST 4 the number "-13170890158" */
    public static final String NUM_TO_SEARCH = "-13170890158";
    /** TEST 5 the number "23" */
    public static final String NUM_TO_SEARCH_FOR_TEST5 = "23";
    /** Constant to enable test1 */
    public static final boolean TEST1 = true;
    /** Constant to enable test2 */
    public static final boolean TEST2 = true;
    /** Constant to enable test3 */
    public static final boolean TEST3 = true;
    /** Constant to enable test4 */
    public static final boolean TEST4 = true;
    /** Constant to enable test5 */
    public static final boolean TEST5 = true;
    /** Constant to enable test6 */
    public static final boolean TEST6 = true;

    /**
     * Adding all the words in data1.txt or data2.txt one by one, to each of the data structures.
     * @param data data1.txt or data2.txt converted to a Strings Array.
     * @param dataStructures all the five data structures.
     */
    private void testAddData(String[] data, SimpleSet[] dataStructures) {
        long timeBefore;
        long difference;
        System.out.println("INSERTING DATA...");
        for (int i = 0 ; i < dataStructures.length; i++){
            timeBefore = System.nanoTime() / NANO_TO_MILI;
            for (int j = 0; j < data.length; j++) {
                dataStructures[i].add(data[j]);
            }
            difference = (System.nanoTime() / NANO_TO_MILI) - timeBefore;
            System.out.println("DATA STRUCTURE: "+i+" TIME = "+ difference + " Miliseconds.");
        }
    }

    /**
     * performs contains("hi") for each data structure when its initialized with data1.txt or data2.txt
     * @param dataStructures The data structures.
     */
    private void testContains(SimpleSet[] dataStructures, String word) {
        long timeBefore;
        long difference;
        System.out.println("Searching \"" + word + "\" in all the data structures test...");
        for (int i = 0 ; i < dataStructures.length; i++) {
            int warmUpTime;
            int iterationsRealTest;
            if (i != LINKED_LIST) {
                warmUpTime = WARM_UP;
                iterationsRealTest = ITERATIONS_TEST;
            }
            else {
                warmUpTime = WARM_UP_LINKED_LIST;
                iterationsRealTest = ITERATION_TEST_LL;
            }
            // WARM UP:
            for (int j = 0; j < warmUpTime; j++) {
                dataStructures[i].contains(word);
            }
            // the actual test after warm up
            timeBefore = System.nanoTime();
            for (int j = 0; j < iterationsRealTest; j++) dataStructures[i].contains(word);
            difference = (System.nanoTime() - timeBefore) / iterationsRealTest;
            System.out.println("DATA STRUCTURE: "+ i +" TIME = "+ difference + " Nano seconds.");
        }
    }

    /**
     * Main method to run the performanceAnalyzer
     * @param args arguments
     */
    public static void main(String[] args){
        SimpleSetPerformanceAnalyzer test = new SimpleSetPerformanceAnalyzer();
        String[] data1 = Ex4Utils.file2array("out/production/ex4/data1.txt");
        String[] data2 = Ex4Utils.file2array("out/production/ex4/data2.txt");
        SimpleSet[] dataStructures = new SimpleSet[NUM_DATA_STRUCTURES];
        dataStructures[0] = new OpenHashSet();
        dataStructures[1] = new ClosedHashSet();
        dataStructures[2] = new CollectionFacadeSet(new TreeSet<String>());
        dataStructures[3] = new CollectionFacadeSet(new LinkedList<String>());
        dataStructures[4] = new CollectionFacadeSet(new HashSet<String>());
        // TEST 1
        if (TEST1) test.testAddData(data1, dataStructures);
        // TEST 2
        if (TEST2) test.testAddData(data2, dataStructures);
        // TEST 3 OR 4
        if (TEST3 || TEST4) {
            test.testAddData(data1, dataStructures);
            if (TEST3) test.testContains(dataStructures, WORD_TO_SEARCH);
            if (TEST4) test.testContains(dataStructures, NUM_TO_SEARCH);
        }
        // TEST 5 OR 6
        if (TEST5 || TEST6) {
            test.testAddData(data2, dataStructures);
            if (TEST5) test.testContains(dataStructures, NUM_TO_SEARCH_FOR_TEST5);
            if (TEST6) test.testContains(dataStructures, WORD_TO_SEARCH);
        }
    }

}