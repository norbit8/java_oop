package filters;

import toolbox.ToolBox;
import java.io.File;

/**
 * Filter that checks the condition where the file size (in bytes) is in the given range.
 */
public class BetweenFilter implements Filter{

    /** The first value in the between filter. */
    private double val1;
    /** The second value in the between filter. */
    private double val2;

    /**
     * Constructor
     * @param val1 The first value in the between filter.
     * @param val2 The second value in the between filter.
     */
    BetweenFilter(double val1, double val2) throws WarningException{
        this.val1 = ToolBox.convertKBytesToBytes(val1);
        this.val2 = ToolBox.convertKBytesToBytes(val2);
        WarningException warningException = new WarningException();
        if (this.val1 > this.val2) throw warningException;
        // check if the values are non-negative
        if (!ToolBox.checkNonNegative(val1) ||
                !ToolBox.checkNonNegative(val2)) throw warningException;
    }

    /**
     * This function returns true when the file adheres to the conditions,
     * false otherwise.
     * The condition is if the file size (in k-bytes) is in the given range.
     * @param file the given file.
     * @return returns true when the file adheres to the conditions, false otherwise.
     */
    @Override
    public boolean isPass(File file) {
        return (file.length() >= this.val1 && file.length() <= this.val2);
    }
}