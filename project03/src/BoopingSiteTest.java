import oop.ex3.searchengine.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.lang.Math;

public class BoopingSiteTest {

    /** BoopingSite constant */
    private static final BoopingSite SITE = new BoopingSite("hotels_dataset.txt");

    /** Latitude constant */
    private static final double MY_LAT = 66;

    /** Longitude constant */
    private static final double MY_LONG = 55;

    /** wrong latitude coordinate constant for test */
    private static final double WRONG_LAT = 10000000;

    /** wrong latitude coordinate constant for test */
    private static final double WRONG_LONG = 10000000;

    /** A city constant manali */
    private static final String MANALI = "manali";

    /** A fake city constant manali */
    private static final String NARNIA = "Narnia";

    /**
     * This test invokes the getHotelsInCityByRating(...) method
     * by sending a fake city.
     * The function should return an empty array.
     */
    @Test
    public void testNotAValidCity() {
        assertEquals("Test failed: testNotAValidCity()",
                0, SITE.getHotelsInCityByRating(NARNIA).length);
    }

    /**
     * This function test that the getHotelsInCityByRating(...) method
     * returns an array of hotels sorted by rating from the top rated hotel
     * to the lowest one.
     */
    @Test
    public void testStarRatingDescending() {
        boolean starDescendingOrder = true;
        // choosing some city e.g. "Manali"
        Hotel[] hotels = SITE.getHotelsInCityByRating(MANALI);
        int biggerStar = hotels[0].getStarRating();
        for (int i = 1; i < hotels.length; i++) {
            if (biggerStar < hotels[i].getStarRating()) {
                starDescendingOrder = false;
                break;
            }
            biggerStar = hotels[i].getStarRating();
        }
        assertTrue("Test failed: testStarRatingDescending()", starDescendingOrder);
    }

    /**
     * This test verifies that for every same rated hotels in the array,
     * the alphabetical order kept.
     */
    @Test
    public void testABOrderForSameRating() {
        boolean alphabeticalOrdered = true;
        Hotel[] hotels = SITE.getHotelsInCityByRating(MANALI);
        Hotel prevHotel = hotels[0];
        for (int i = 1; i < hotels.length; i++) {
            if (prevHotel.getStarRating() == hotels[i].getStarRating()) {
                if (prevHotel.getPropertyName().compareTo(hotels[i].getPropertyName()) > 0) {
                    alphabeticalOrdered = false;
                    break;
                }
            }
            prevHotel = hotels[i];
        }
        assertTrue("Test failed: testABOrderForSameRating()", alphabeticalOrdered);
    }

    /**
     * This test verifies that all the hotels in the array,
     * are from the same city as provided (as parameter).
     */
    @Test
    public void testCity() {
        Hotel[] hotels = SITE.getHotelsInCityByRating(MANALI);
        boolean sameCity = isSameCity(hotels, MANALI);
        assertTrue("Test failed: testCity()", sameCity);
    }

    /**
     * Helper function, helps detects if the array has different cities in it.
     * @param hotels Array of hotel objects
     * @param city String represents a city.
     * @return true if all the hotels are from the same city, false otherwise.
     */
    private boolean isSameCity(Hotel[] hotels, String city) {
        boolean sameCity = true;
        for (Hotel hotel : hotels) {
            if (!hotel.getCity().equals(city)) {
                sameCity = false;
                break;
            }
        }
        return sameCity;
    }

    /**
     * This test, tests the getHotelsByProximity(...) method, specifically it tests it's ability to
     * sort the hotel by their distance from a given geographic location (ascending order).
     */
    @Test
    public void testProximity() {
        Hotel[] hotels = SITE.getHotelsByProximity(MY_LAT, MY_LONG);
        boolean greaterDistance = proximityHelper(hotels, MY_LAT, MY_LONG);
        assertTrue("Test failed: testProximity()", greaterDistance);
    }

    /**
     * This helper function detects if the given array has hotels with ascenging ored distance
     * from the given point.
     * @param hotels An array of Hotel objects.
     * @param pointLat The latitude point which we take the distance to/from.
     * @param pointLong The longitude point which we take the distance to/from.
     * @return true if the hotels are sorted in ascending order, false otherwise.
     */
    private boolean proximityHelper(Hotel[] hotels, double pointLat, double pointLong) {
        boolean greaterDistance = true;
        Hotel prevHotel = hotels[0];
        for (int i = 1; i < hotels.length; i++) {
            double hotel1Lat = prevHotel.getLatitude();
            double hotel1Long = prevHotel.getLongitude();
            double hotel2Lat = hotels[i].getLatitude();
            double hotel2Long = hotels[i].getLongitude();
            double firstDistance = getDistance(hotel1Lat, hotel1Long , pointLat, pointLong);
            double secondDistance = getDistance(hotel2Lat, hotel2Long , pointLat, pointLong);
            if (firstDistance > secondDistance) {
                // This is not ascending order.
                greaterDistance = false;
                break;
            }
        }
        return  greaterDistance;
    }

    /**
     * Distance calculator method (A helper function).
     * @param x1 first coordinate of point 1
     * @param y1 second coordinate of point 1
     * @param x2 first coordinate of point 2
     * @param y2 second coordinate of point 2
     * @return The euclidean distance between point 1 and point 2.
     */
    private double getDistance(double x1, double y1, double x2, double y2){
        return Math.sqrt((Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2)));
    }

    /**
     * According to the getHotelsByProximity(...) method,
     * If two hotels are at the same distance from the given location,
     * then they are organized according to the number of points-of-interest for which they are close to
     * (in a decreasing order)
     * In this test I verify that.
     */
    @Test
    public void testPointsOfInterest(){
        Hotel[] hotels = SITE.getHotelsByProximity(MY_LAT, MY_LONG);
        assertTrue("Test failed: testPointsOfInterest()", checkPOI(hotels));
    }

    /**
     * This test tests the getHotelsByProximity(...) In case of providing illegal input.
     */
    @Test
    public void testWrongCoordinatesProximity(){
      assertEquals("Test failed: testWrongCoordinatesProximity()", 0,
                   SITE.getHotelsByProximity(WRONG_LAT, WRONG_LONG).length);
    }

    /**
     * This test tests the getHotelsInCityByProximity(...) In case of providing illegal city input.
     */
    @Test
    public void testWrongCityProximity(){
        assertEquals("Test failed: testWrongCityProximity()", 0,
                SITE.getHotelsInCityByProximity(NARNIA, MY_LAT, MY_LONG).length);
    }

    /**
     * This test tests the getHotelsInCityByProximity(...) In case of providing illegal coordinates but
     * a real city.
     */
    @Test
    public void testWrongCoordinatesCityProximity(){
        assertEquals("Test failed: testWrongCoordinatesCityProximity()", 0,
                SITE.getHotelsInCityByProximity(MANALI, WRONG_LAT, WRONG_LONG).length);
    }

    /**
     * This test, tests the getHotelsInCityByProximity(...) method, specifically it tests it's ability to
     * sort the hotel by their distance from a given geographic location (ascending order).
     */
    @Test
    public void testHICBP(){
        Hotel[] hotels = SITE.getHotelsInCityByProximity(MANALI, MY_LAT, MY_LONG);
        boolean greaterDistance = proximityHelper(hotels, MY_LAT, MY_LONG);
        assertTrue("Test failed: testHICBP()", greaterDistance);
    }

    /**
     * This test verifies that all the hotels in the array,
     * are from the same city as provided (as parameter).
     */
    @Test
    public void testCityHICBP() {
        Hotel[] hotels = SITE.getHotelsInCityByProximity(MANALI, MY_LAT, MY_LONG);
        boolean sameCity = isSameCity(hotels, MANALI);
        assertTrue("Test failed: testCityHICBP()", sameCity);
    }

    /**
     * According to the getHotelsInCityByProximity(...) method,
     * If two hotels are at the same distance from the given location,
     * then they are organized according to the number of points-of-interest for which they are close to
     * (in a decreasing order)
     * In this test I verify that.
    */
    @Test
    public void testPOIHotelsICBP(){
        Hotel[] hotels = SITE.getHotelsInCityByProximity(MANALI, MY_LAT, MY_LONG);
        assertTrue("Test failed: testPOIHotelsICBP()", checkPOI(hotels));
    }

    /**
     * This is a helper function for all the tests that checks the POI oreder
     * @param hotels An array of Hotel objects.
     * @return true if the hotels which has the same distance are ordered with decreasing order
     * of POI, false otherwise.
     */
    private boolean checkPOI(Hotel[] hotels){
        boolean decreasingPOI = true;
        Hotel prevHotel = hotels[0];
        for (int i = 1; i < hotels.length; i++) {
            double firstDistance = getDistance(prevHotel.getLatitude(), prevHotel.getLongitude() ,
                    MY_LAT, MY_LONG);
            double secondDistance = getDistance(hotels[i].getLatitude(), hotels[i].getLongitude() ,
                    MY_LAT, MY_LONG);
            if (firstDistance == secondDistance) {
                // The distances are the same from the given location
                if(prevHotel.getNumPOI() < hotels[i].getNumPOI()) {
                    decreasingPOI = false;
                    break;
                }
            }
        }
        return decreasingPOI;
    }
}