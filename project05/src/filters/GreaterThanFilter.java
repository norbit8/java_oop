package filters;

import toolbox.ToolBox;

import java.io.File;

/**
 * The greater than filter class checks if the file size is greater than some value.
 */
public class GreaterThanFilter implements Filter {

    /** Value in Bytes that the file should be greater than */
    private double value;

    /**
     * Greater than filter constructor.
     * @param value The value to be greater than. (In bytes)
     */
    GreaterThanFilter(double value) throws WarningException{
        this.value = ToolBox.convertKBytesToBytes(value);
        if (!ToolBox.checkNonNegative(value)) throw new WarningException();
    }

    /**
     * This function returns true when the file adheres to the conditions,
     * false otherwise.
     * The condition is if the file size (in k-bytes) is greater than the given value.
     * @param file the given file.
     * @return returns true when the file adheres to the conditions, false otherwise.
     */
    @Override
    public boolean isPass(File file) {
        return (file.length() > this.value);
    }
}