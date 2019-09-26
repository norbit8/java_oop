package orders;

import java.io.File;
import java.util.Comparator;

/**
 * An abstract class for order
 */
public abstract class Order implements Comparator<File> {

    /** if the first argument is less than the second argument */
    static final int LESS = -1;
    /** if the first argument is greater than the second argument */
    static final int GREATER = 1;
    /** adding one to the index */
    static final int ADDER = 1;

    /**
     * Compares its two arguments for order. Returns a negative integer, zero,
     * or a positive integer as the first argument is less than,
     * equal to, or greater than the second.
     * @param file1 the first object to be compared.
     * @param file2 the second object to be compared.
     * @return a negative integer, zero, or a positive integer as
     * the first argument is less than, equal to, or greater than the second.
     */
    public abstract int compare(File file1, File file2);

    /**
     * dividing the fileArray into two partitions and merging them according to a selected element
     * (pivot),
     * where the logic is: all the elements before the pivot are smaller than him, and all the
     * elements to the right of the pivot are greater then him.
     * @param fileArray the fileArray of files to sort.
     * @param min the lowest number in the fileArray.
     * @param max the highest file in the fileArray.
     * @return an index to where
     */
    int parti(File[] fileArray, int min, int max)
    {
        int index = (min - ADDER);
        File pivot = fileArray[max];
        for (int jindex = min; jindex < max; jindex++)
        {
            // swapping elements if they are less then the pivot.
            if (compare(fileArray[jindex], pivot) <= 0)
            {
                index++;
                File temp = fileArray[index];
                fileArray[index] = fileArray[jindex];
                fileArray[jindex] = temp;
            }
        }
        // swapping between the elements:
        File temp = fileArray[index + ADDER];
        fileArray[index + ADDER] = fileArray[max];
        fileArray[max] = temp;
        return index + ADDER;
    }

    /**
     * The function generates random pivot
     * and swaps between the max element in the fileArray
     * and uses the parti function at last.
     * @param fileArray the fileArray of files to sort.
     * @param min the lowest number in the fileArray.
     * @param max the highest file in the fileArray.
     * @return randomize pivot.
     */
    int randomize_parti(File[] fileArray, int min, int max){
        // creating the random pivot:
        int randomPivot = min + (int)(Math.random() *((max - min) + ADDER));
        File temp = fileArray[max];
        fileArray[max] = fileArray[randomPivot];
        fileArray[randomPivot]= temp;
        return parti(fileArray, min, max);
    }

    /**
     * QuickSort algorithm implementation. (With random pivot selection)
     * @param fileArray the fileArray of files to sort.
     * @param min the lowest number in the fileArray.
     * @param max the highest file in the fileArray.
     * @return A sorted fileArray.
     */
    public File[] sort(File[] fileArray, int min, int max) {
        if (min < max)
        {
            int par = randomize_parti(fileArray, min, max);
            sort(fileArray, min, par - ADDER);
            sort(fileArray, par + ADDER, max);
        }
        return fileArray;
    }
}