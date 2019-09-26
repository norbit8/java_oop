package filters;

import java.io.File;

/**
 * The all filter will not filter any file.
 */
public class AllFilter implements Filter {

    /**
     * This function returns true when the file adheres to the conditions,
     * false otherwise.
     * @param file the given file.
     * @return returns true when the file adheres to the conditions, false otherwise.
     */
    @Override
    public boolean isPass(File file) {
        return true;
    }
}
