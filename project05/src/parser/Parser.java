package parser;

import filters.*;
import orders.*;

/**
 * Parser class will handle illegal input, and has a method
 * which returns and order and filter object merged together in a wrapper class.
 */
public class Parser {

    /** some user input filter (String) */
    private String filter;
    /** some user input order (String)*/
    private String order;
    /** The number of the line of the filter. */
    private int filterLine;
    /** The number of the line of the order. */
    private int orderLine;
    /** The beginning of every warning */
    private static final String WARNING = "Warning in line ";

    /**
     * Parser constructor, takes the user's input of the filter and order types
     * and parse it.
     * @param filter Some user input filter (String).
     * @param order Some user input order (String).
     * @param filterLine The number of the line of the filter.
     * @param orderLine The number of the line of the order
     */
    public Parser(String filter, String order, int filterLine, int orderLine) {
        this.filter = filter;
        this.order = order;
        this.filterLine = filterLine;
        this.orderLine = orderLine;
    }

    /**
     * This function should parse the users filter and order input.
     * @return WrapperFilterOrder class which contains two fields of filter and order.
     */
    public WrapperFilterOrder getFilterOrder() {
        FilterFactory filterFactory = new FilterFactory();
        OrderFactory orderFactory = new OrderFactory();
        Filter currFilter = new AllFilter();
        Order currOrder = new AbsOrder();
        try {
            currFilter = filterFactory.getFilter(this.filter);
        }
        catch (WarningException e) {
            System.err.println(WARNING + (this.filterLine));
        }
        try {
            currOrder = orderFactory.getOrder(this.order);
        }
        catch (WarningExceptionOrder e) {
            System.err.println(WARNING + (this.orderLine));
        }
        return (new WrapperFilterOrder(currFilter, currOrder));
    }
}