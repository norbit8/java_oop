package orders;

import toolbox.ToolBox;

import java.io.File;

/**
 * In this class I implement a compare method which decides how to compare between two files.
 * The comparing method here is by the type of the file.
 */
public class TypeOrder extends Order {


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
        String file1Type = ToolBox.getExtension(file1);
        String file2Type = ToolBox.getExtension(file2);
        if (file1Type.compareTo(file2Type) == 0) {
            Order orderByAbs = new AbsOrder();
            return orderByAbs.compare(file1, file2);
        }
        return file1Type.compareTo(file2Type);
    }
}
