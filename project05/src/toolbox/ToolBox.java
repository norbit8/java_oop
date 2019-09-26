package toolbox;

import java.io.File;
import java.util.Arrays;

/**
 * This Class has useful tools (functions), like converters and verifiers.
 */
public class ToolBox {

    /** Final to check that the VALUE is NON NEGATIVE */
    private static final int NON_NEGATIVE = 0;
    /** Final to check that the VALUE is NON NEGATIVE */
    private static final int BYTES_TO_KB = 1024;

    /**
     * This method verifies that the value is not negative.
     * @param value Value to be checked.
     * @return True if the value is not negative, false otherwise.
     */
    public static boolean checkNonNegative(double value) {
        return (value >= NON_NEGATIVE);
    }

    /**
     * Converts bytes to k-bytes
     * @param value Value in bytes.
     * @return Value in k-bytes. (Double)
     */
    public static double convertKBytesToBytes(double value) {
        return (value * BYTES_TO_KB);
    }

    /**
     * This function gets the extension of any file.
     * if there is no extension it will return an empty string.
     * @param file A file.
     * @return The extension of the given file, if there isn't such thing
     * it will return empty string.
     */
    public static String getExtension(File file) {
        String fileName = file.getName();
        if (!fileName.contains(".")) return "";
        if (fileName.startsWith(".")) return "";
        String[] processedFile = fileName.split("\\.");
        if (processedFile.length == 0) return "";
        return processedFile[processedFile.length - 1];
    }
}
