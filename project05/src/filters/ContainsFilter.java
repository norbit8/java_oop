package filters;

import java.io.File;

/**
 * Contains filter matches files that contains the given str in their file name
 * excluding path.
 */
public class ContainsFilter implements Filter {

    /** The String to be contained in the file's name*/
    private String containStr;

    /**
     * Constructor of the contains filter
     * @param containStr A str to search inside the file's name.
     */
    ContainsFilter(String containStr){
        this.containStr = containStr;
    }

    /**
     * This function returns true when the file adheres to the conditions,
     * false otherwise.
     * Contains filter matches files that contains the given str in their file name
     * excluding path.
     * @param file the given file.
     * @return returns true when the file adheres to the conditions, false otherwise.
     */
    @Override
    public boolean isPass(File file) {
        return (file.getName().contains(this.containStr));
    }
}