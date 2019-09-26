package filters;

import java.io.File;

/**
 * The suffix filter, is a filter which return true for every file which ends
 * with the given (user input given) suffix.
 */
public class SuffixFilter implements Filter {

    /** The given prefix */
    private String suffix;

    /**
     * SuffixFilter constructor.
     * @param suffix The given prefix (String).
     */
    SuffixFilter(String suffix){
        this.suffix = suffix;
    }

    /**
     * This function returns true when the file adheres to the conditions,
     * false otherwise.
     * The condition is if the file has the given suffix.
     * @param file the given file.
     * @return returns true when the file adheres to the conditions, false otherwise.
     */
    @Override
    public boolean isPass(File file) {
        return (file.getName().endsWith(this.suffix));
    }
}