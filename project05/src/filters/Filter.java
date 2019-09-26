package filters;

import java.io.File;

/**
 * Filter interface has one abstract function which is "isPass" which is common to all
 * the filters, more about it in the README file.
 */
public interface Filter {

    /**
     * This function returns true when the file adheres to the conditions,
     * false otherwise.
     * @param file the given file.
     * @return returns true when the file adheres to the conditions, false otherwise.
     */
    boolean isPass(File file);
}
