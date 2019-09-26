package filters;

/**
 * Filter factory is the class where one can create thier filter according to the user input.
 * this class processes the user's input and has a function which returns the
 */
public class FilterFactory {

    /** hidden filter */
    private static final String HIDDEN = "hidden";
    /** executable filter */
    private static final String EXECUTABLE = "executable";
    /** writable filter */
    private static final String WRITABLE = "writable";
    /** suffix filter */
    private static final String SUFFIX = "suffix";
    /** prefix filter */
    private static final String PREFIX = "prefix";
    /** contains filter */
    private static final String CONTAINS = "contains";
    /** file filter */
    private static final String FILE = "file";
    /** smaller than filter */
    private static final String SMALLER_THAN = "smaller_than";
    /** between filter */
    private static final String BETWEEN = "between";
    /** greater_than filter */
    private static final String GREATER_THAN = "greater_than";
    /** all filter */
    private static final String ALL = "all";
    /** NAME and VALUE separator */
    private static final String SPLICER = "#";
    /** The filter name location in the array after spliting by the splicer (#) */
    private static final int FILTER_NAME = 0;
    /** Negates the filter */
    private static final String NEGATE = "NOT";
    /** after splitting the string, The filter value is in the index 1 */
    private static final int FILTER_VALUE = 1;
    /** after splitting the string, The filter Negate option is in the index 2 */
    private static final int FILTER_NEGATE = 2;
    /** after splitting the string, The filter length should be max 3 */
    private static final int NEGATE_LENGTH = 3;
    /** after splitting the string, The filter between length should be max 4 */
    private static final int NEGATE_LENGTH_BETWEEN = 4;
    /** after splitting the string, The filter value2 of between filter is in the index 2 */
    private static final int FILTER_VALUE2 = 2;

    /**
     * This is the main factory function, which generates a filter according to the
     * user input.
     * @param filter The user input filter.
     * @return A new filter according to the user input.
     * @throws WarningException when the filter is not a valid one.
     */
    public Filter getFilter(String filter) throws WarningException {
        String[] processedFilter = filter.split(SPLICER);
        // if something goes wrong throw this exception
        WarningException warningException = new WarningException();
        switch (processedFilter[FILTER_NAME]){
            case ALL:
                Filter allFilter = new AllFilter();
                if(processedFilter.length > FILTER_NEGATE) throw warningException;
                if (processedFilter.length == FILTER_NEGATE) {
                    if (processedFilter[FILTER_VALUE].equals(NEGATE)) {
                        return new NotFilterDecorator(allFilter);
                    }
                }
                return allFilter;

            case GREATER_THAN:
                // check if there is a value, or NOT NOT more than once
                validateInput(filter);
                // Notes 4.1.2 section 5
                double value = Double.parseDouble(processedFilter[FILTER_VALUE]);
                Filter gt = new GreaterThanFilter(value);
                if (processedFilter.length == NEGATE_LENGTH) {
                    if (processedFilter[FILTER_NEGATE].equals(NEGATE)){
                        return (new NotFilterDecorator(gt));
                    }
                    else throw warningException;
                }
                return gt;

            case BETWEEN:
                if (processedFilter.length > NEGATE_LENGTH_BETWEEN) throw warningException;
                double value1 = Double.parseDouble(processedFilter[FILTER_VALUE]);
                double value2 = Double.parseDouble(processedFilter[FILTER_VALUE2]);
                Filter bf = new BetweenFilter(value1, value2);
                if (processedFilter.length == NEGATE_LENGTH_BETWEEN) {
                    if (processedFilter[NEGATE_LENGTH].equals(NEGATE)) {
                        return (new NotFilterDecorator(bf));
                    }
                }
                return bf;

            case SMALLER_THAN:
                // check if there is a value, or NOT NOT more than once
                validateInput(filter);
                // Notes 4.1.2 section 5
                double val = Double.parseDouble(processedFilter[FILTER_VALUE]);
                Filter lt = new SmallerThanFilter(val);
                if (processedFilter.length == NEGATE_LENGTH) {
                    if (processedFilter[FILTER_NEGATE].equals(NEGATE)){
                        return (new NotFilterDecorator(lt));
                    }
                    else throw warningException;
                }
                return lt;

            case FILE:
                validateInput(filter);
                Filter ff = new FileFilter(processedFilter[FILTER_VALUE]);
                // negate the value
                if (processedFilter.length == NEGATE_LENGTH) {
                    if (processedFilter[FILTER_NEGATE].equals(NEGATE)) {
                        return (new NotFilterDecorator(ff));
                    }
                }
                // return normal result
                return ff;

            case CONTAINS:
                validateInput(filter);
                Filter containsFilter = new ContainsFilter(processedFilter[FILTER_VALUE]);
                // negate the value
                if (processedFilter.length == NEGATE_LENGTH) {
                    if (processedFilter[FILTER_NEGATE].equals(NEGATE)) {
                        return (new NotFilterDecorator(containsFilter));
                    }
                }
                return containsFilter;

            case PREFIX:
                validateInput(filter);
                Filter prefixFilter = new PrefixFilter(processedFilter[FILTER_VALUE]);
                // negate the value
                if (processedFilter.length == NEGATE_LENGTH) {
                    if (processedFilter[FILTER_NEGATE].equals(NEGATE)) {
                        return (new NotFilterDecorator(prefixFilter));
                    }
                }
                return prefixFilter;

            case SUFFIX:
                validateInput(filter);
                Filter suffixFilter = new SuffixFilter(processedFilter[FILTER_VALUE]);
                // negate the value
                if (processedFilter.length == NEGATE_LENGTH) {
                    if (processedFilter[FILTER_NEGATE].equals(NEGATE)) {
                        return (new NotFilterDecorator(suffixFilter));
                    }
                }
                return suffixFilter;

            case WRITABLE:
                validateInput(filter);
                Filter writableFilter = new WritableFilter(processedFilter[FILTER_VALUE]);
                // negate the value
                if (processedFilter.length == NEGATE_LENGTH) {
                    if (processedFilter[FILTER_NEGATE].equals(NEGATE)) {
                        return (new NotFilterDecorator(writableFilter));
                    }
                }
                return writableFilter;

            case EXECUTABLE:
                validateInput(filter);
                Filter executableFilter = new ExecutableFilter(processedFilter[FILTER_VALUE]);
                // negate the value
                if (processedFilter.length == NEGATE_LENGTH) {
                    if (processedFilter[FILTER_NEGATE].equals(NEGATE)) {
                        return (new NotFilterDecorator(executableFilter));
                    }
                }
                return executableFilter;

            case HIDDEN:
                validateInput(filter);
                Filter hiddenFilter = new HiddenFilter(processedFilter[FILTER_VALUE]);
                // negate the value
                if (processedFilter.length == NEGATE_LENGTH) {
                    if (processedFilter[FILTER_NEGATE].equals(NEGATE)) {
                        return (new NotFilterDecorator(hiddenFilter));
                    }
                }
                return hiddenFilter;

            default:
                throw warningException;
        }
    }

    /**
     * Validates the input is ok.
     * @param filter The user input filter.
     * @throws WarningException when the filter is not a valid one.
     */
    private void validateInput(String filter) throws WarningException {
        String[] processedFilter = filter.split(SPLICER);
        // if something goes wrong throw this exception
        WarningException warningException = new WarningException();
        if (processedFilter.length == FILTER_VALUE ||
            processedFilter.length > NEGATE_LENGTH) throw warningException;
    }
}