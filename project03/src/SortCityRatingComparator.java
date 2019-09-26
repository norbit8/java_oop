import oop.ex3.searchengine.*;
import java.util.Comparator;

/**
 * The sorting logic for the SortByCityRating(...) method is implemented in this very class.
 */
public class SortCityRatingComparator implements Comparator<Hotel> {

    /** if hotel1 should be before hotel2 */
    private static final int GT = -1;

    /** if hotel1 should be after hotel2 */
    private static final int LT = 1;

    /**
     * The compare function which implement the logic for comparing between two hotels
     * @param hotel1 The first Hotel object
     * @param hotel2 The second Hotel object
     * @return -1 if the first hotel should be before the second
     *          1 if the second hotel should be before the first
     */
    @Override
    public int compare(Hotel hotel1, Hotel hotel2) {
        if (hotel1.getStarRating() > hotel2.getStarRating()){
            return GT;
        }
        if (hotel1.getStarRating() < hotel2.getStarRating()) {
            return LT;
        }
        return (hotel1.getPropertyName().compareTo(hotel2.getPropertyName()));
    }
}