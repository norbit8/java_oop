package parser;

import filters.Filter;
import orders.Order;

/**
 * Wrapper class which has two fields: filter and order.
 */
public class WrapperFilterOrder {

    /** some filter object */
    private Filter filter;
    /** some order object */
    private Order order;

    /**
     * Constructor for the wrapper class
     * @param filter some filter object.
     * @param order some order object.
     */
    public WrapperFilterOrder(Filter filter, Order order){
        this.filter = filter;
        this.order = order;
    }

    /**
     * getter for filter.
     * @return return the filter.
     */
    public Filter getFilter() {
        return filter;
    }

    /**
     * getter for the order.
     * @return return the order.
     */
    public Order getOrder(){
        return order;
    }
}