import oop.ex3.searchengine.Hotel;
import java.lang.Math;
import java.util.Comparator;

/**
 * The sorting logic for the getHotelsByProximity(...) method is implemented in this very class.
 */

public class SortHotelsByProximity implements Comparator<Hotel> {

    /** if hotel1 should be before hotel2 */
    private static final int GT = -1;

    /** if hotel1 should be after hotel2 */
    private static final int LT = 1;

    /** The user input latitude */
    private double latitude;

    /** The user input longitude */
    private double longitude;

    /**
     * Constructor for the sorting class logic.
     * @param latitude A user input double latitude.
     * @param longitude A user input double longitude.
     */
    public SortHotelsByProximity(double latitude, double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * The compare function which implement the logic for comparing between two hotels
     * @param hotel1 The first Hotel object
     * @param hotel2 The second Hotel object
     * @return -1 if the first hotel should be before the second
     *          1 if the second hotel should be before the first
     */
    @Override
    public int compare(Hotel hotel1, Hotel hotel2) {
        // first I find the distance between each hotel and the given geographic point.
        // and because root is a monotone function then I got rid of it when calculating the dist.
        double distHotel1 = (Math.pow((hotel1.getLatitude() - latitude), 2) +
                                 Math.pow((hotel1.getLongitude() - longitude), 2));
        double distHotel2 = (Math.pow((hotel2.getLatitude() - latitude), 2) +
                Math.pow((hotel2.getLongitude() - longitude), 2));
        // then I create the sorting logic.
        if (distHotel1 < distHotel2){
            return GT;
        }
        if (distHotel1 > distHotel2) {
            return LT;
        }
        if (hotel1.getNumPOI() > hotel2.getNumPOI()){
           return GT;
        }
        // In any other case just return LT
        return LT;
    }
}
