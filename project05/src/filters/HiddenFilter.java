package filters;

import java.io.File;

/**
 * HiddenFilter is the filter which checks if the file is hidden or not according
 * to the users input.
 */
public class HiddenFilter implements Filter {

    /** Constant for the answer YES */
    private static final String YES = "YES";
    /** Constant for the answer NO */
    private static final String NO = "NO";
    /** The users choice */
    private String answer;

    /**
     * Constructor for the writable filter
     * @param answer should be YES or NO
     * @throws WarningException if the answer is not YES nor NO it should throw an exception.
     */
    HiddenFilter(String answer) throws WarningException{
        if (!answer.equals(YES) && !answer.equals(NO)) throw new WarningException();
        this.answer = answer;
    }

    /**
     * This function returns true when the file adheres to the conditions,
     * false otherwise.
     * The condition is if the file is hidden and the user input was YES so it should return it,
     * also if the file was not hidden and the user input was NO than it also should return it.
     * In any other case it should return False.
     * @param file the given file.
     * @return returns true when the file adheres to the conditions, false otherwise.
     */
    @Override
    public boolean isPass(File file) {
        if(file.isHidden()) {
            return this.answer.equals(YES);
        }
        else{
            return this.answer.equals(NO);
        }
    }
}
