package filters;

import java.io.File;

/**
 * File filter, filters all the files which is not in the name of the specified String.
 */
public class FileFilter implements Filter {

    /** A name of a file to be filtered*/
    private String fileName;

    FileFilter(String fileName){
        this.fileName = fileName;
    }

    /**
     * This function returns true when the file adheres to the conditions,
     * false otherwise.
     * The condition is if the file name is equal to the given string.
     * @param file the given file.
     * @return returns true when the file adheres to the conditions, false otherwise.
     */
    @Override
    public boolean isPass(File file) {
        return (file.getName().equals(fileName));
    }
}
