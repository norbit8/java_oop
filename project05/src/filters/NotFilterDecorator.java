package filters;

import java.io.File;

/**
 * Decorator filter, when we want to negate the filter we use it.
 */
public class NotFilterDecorator implements Filter {

    /** According the Decorator design pattern I should have an instance of the filter */
    Filter filter;

    /**
     * The decorator constructor.
     * @param filter the given filter.
     */
    public NotFilterDecorator(Filter filter) {
        this.filter = filter;
    }

    /**
     * This function returns true when the file adheres to the conditions,
     * false otherwise.
     * @param file the given file.
     * @return returns true when the file adheres to the conditions, false otherwise.
     */
    @Override
    public boolean isPass(File file) {
        return (!filter.isPass(file));
    }
}
