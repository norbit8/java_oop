package orders;

import java.io.File;

/**
 * In this class I implement a compare method which decides how to compare between two files.
 * The comparing method here is by the size of the file.
 */
public class SizeOrder extends Order {

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
        if (file1.length() < file2.length()) return LESS;
        if (file1.length() > file2.length()) return GREATER;
        Order orderByAbs = new AbsOrder();
        return orderByAbs.compare(file1, file2);
    }
}