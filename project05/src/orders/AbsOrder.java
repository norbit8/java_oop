package orders;

import java.io.File;

/**
 * In this class I implement a compare method which decides how to compare between two files.
 * The comparing method here is by the absolute name of the file.
 */
public class AbsOrder extends Order {

    /**
     * Compares its two arguments for order. Returns a negative integer, zero,
     * or a positive integer as the first argument is less than,
     * equal to, or greater than the second.
     * @param file1 the first object to be compared.
     * @param file2 the second object to be compared.
     * @return a negative integer, zero, or a positive integer as
     * the first argument is less than, equal to, or greater than the second.
     * */
    @Override
    public int compare(File file1, File file2) {
        String file1Abs = file1.getAbsolutePath();
        String file2Abs = file2.getAbsolutePath();
        return file1Abs.compareTo(file2Abs);
    }
}