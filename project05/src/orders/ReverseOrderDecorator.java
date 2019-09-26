package orders;

import java.io.File;

/**
 * ReverseOrderDecorator is a decorator which negates the logic of the given order.
 */
public class ReverseOrderDecorator extends Order {

    /** Some order to negate */
    private Order order;

    public ReverseOrderDecorator(Order order){
        this.order = order;
    }

    /**
     * Compares its two arguments for order. Returns a negative integer, zero,
     * or a positive integer as the first argument is less than,
     * equal to, or greater than the second.
     * @param file1 the first object to be compared.
     * @param file2 the second object to be compared.
     * @return a negative integer, zero, or a positive integer as
     * the first argument is less than, equal to, or greater than the second.
     * */
    @Override
    public int compare(File file1, File file2) {
        return (-order.compare(file1, file2));
    }
}