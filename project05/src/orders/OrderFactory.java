package orders;

/**
 * The order factory creates an order object in the getOrder function,
 * according to the users request.
 */
public class OrderFactory {

    /** order by abs */
    private static final String ABS_ORDER = "abs";
    /** order by size */
    private static final String SIZE_ORDER = "size";
    /** order by type */
    private static final String TYPE_ORDER = "type";
    /** NAME and VALUE separator */
    private static final String SPLICER = "#";
    /** The order name location in the array after splitting by the splicer (#) */
    private static final int ORDER_NAME = 0;
    /** The REVERSE location in the array */
    private static final int ORDER_REVERSE = 1;
    /** The length of the array after splitting when there is reverse argument */
    private static final int REVERSE_LENGTH = 2;
    /** The reverse keyword */
    private static final String REVERSE = "REVERSE";

    /**
     * The getter of the factory
     * @param order is the order requested by the user.
     * @return The right order according to the user input.
     * @throws WarningExceptionOrder Order exception warning is throws when the user
     * insert invalid input.
     */
    public Order getOrder(String order) throws WarningExceptionOrder{
        WarningExceptionOrder warningExceptionOrder = new WarningExceptionOrder();
        String[] precessedOrder = order.split(SPLICER);
        boolean reverse = false;
        if (precessedOrder.length == REVERSE_LENGTH){
            if (precessedOrder[ORDER_REVERSE].equals(REVERSE)) {
                reverse = true;
            }
            else throw warningExceptionOrder;
        }
        if (precessedOrder.length > REVERSE_LENGTH) throw warningExceptionOrder;
        switch (precessedOrder[ORDER_NAME]) {
            case ABS_ORDER:
                Order absOrder = new AbsOrder();
                if (reverse) {
                    return new ReverseOrderDecorator(absOrder);
                }
                return absOrder;

            case SIZE_ORDER:
                Order sizeOrder = new SizeOrder();
                if (reverse) {
                    return new ReverseOrderDecorator(sizeOrder);
                }
                return sizeOrder;

            case TYPE_ORDER:
                Order typeOrder = new TypeOrder();
                if (reverse) {
                    return new ReverseOrderDecorator(typeOrder);
                }
                return typeOrder;

            default:
                throw new WarningExceptionOrder();
        }
    }
}