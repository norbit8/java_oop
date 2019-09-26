package filesprocessing;

import manager.InvalidUsageException;
import manager.Manager;

/**
 * DirectoryProcessor is the class with the main function.
 * the user run this class with two parameters (A directory with all the files to use, and a file
 * with sections).
 */
public class DirectoryProcessor {
    /** Constants for the source dir location in the user input */
    private static final int SRC = 0;
    /** Constants for the commands in the user input */
    private static final int CMD = 1;
    /** length of the user input */
    private static final int USER_INPUT_LENGTH = 2;
    /** Wrong Usage ERR */
    private static final String USAGE_ERR = "ERROR: Wrong usage. Should receive a valid dir and file";
    /** < 2 ARGS USAGE ERR */
    private static final String ARGS_ERR = "ERROR: Wrong usage. Should receive 2 arguments";

    /**
     * The main function which init the manager and prints
     * the desired output.
     * @param args The users input
     */
    public static void main(String[] args) {
        if(args.length == USER_INPUT_LENGTH) {
            String srcDir = args[SRC];
            String command = args[CMD];
            Manager manager;
            try {
                manager = new Manager(srcDir, command);
            } catch (InvalidUsageException e) {
                System.err.println(USAGE_ERR);
                return;
            }
            manager.printResults();
        }
        else {
            System.err.println(ARGS_ERR);
        }
    }
}