package filters;

import java.io.File;

/**
 * The prefix filter, is a filter which return true for every file which starts
 * with the given (user input given) prefix.
 */
public class PrefixFilter implements Filter {

    /** The given prefix */
    private String prefix;

    /**
     * PrefixFilter constructor.
     * @param prefix The given prefix (String).
     */
    PrefixFilter(String prefix){
        this.prefix = prefix;
    }

    /**
     * This function returns true when the file adheres to the conditions,
     * false otherwise.
     * The condition is if the file has the given prefix.
     * @param file the given file.
     * @return returns true when the file adheres to the conditions, false otherwise.
     */
    @Override
    public boolean isPass(File file) {
        return (file.getName().startsWith(this.prefix));
    }
}