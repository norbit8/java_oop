import oop.ex3.searchengine.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Booping.com is a new hotel booking site, that allows for personalized search methodologies.
 * This is its main class.
 */
public class BoopingSite {

    /** The data set we use in order to fetch all the hotels data*/
    private String dataSet;

    /** All the hotels in the data set in an Array of Hotel objects */
    private Hotel[] hotels;

    /** The maximum value a latitude coordinate can be given. (also the opposite number) */
    private static final int MAX_LAT = 90;

    /** The maximum value a longitude coordinate can be given. (also the opposite number) */
    private static final int MAX_LONG = 180;


    /**
     * BoopingSite constructor.
     * @param name A String with the name of the data-set of all the hotels.
     */
    public BoopingSite(String name){
        this.dataSet = name;
        this.hotels = HotelDataset.getHotels(this.dataSet);
    }

    /**
     * This method returns an array of ho-tels located in the given city,
     * sorted from the highest star-rating to the lowest.
     * Hotels that have the same rating will be organized according to the
     * alphabet order of the hotel’s (property) name.
     * (In case there are no hotels in the given city, this method returns an empty array).
     * @param city The name of the city which the user wants to get hotels recommendation from.
     * @return A sorted hotel from the highest star-rating to the lowest.
     */
    public Hotel[] getHotelsInCityByRating(String city){
        ArrayList<Hotel> hotelsInCity = new ArrayList<>();
        for (Hotel hotel : this.hotels) {
            if (hotel.getCity().equals(city)) {
                hotelsInCity.add(hotel);
            }
        }
        Collections.sort(hotelsInCity, new SortCityRatingComparator());
        return arrayListToHotelArray(hotelsInCity);
    }

    /**
     * This is a helper method for the getHotelsInCityByRating(...) method to convert ArrayList to
     * a regular Array of type Hotel.
     * @param hotelsInCity An ArrayList<Hotel> object which holds hotels.
     * @return An array of Hotels.
     */
    private Hotel[] arrayListToHotelArray(ArrayList hotelsInCity){
        int len = hotelsInCity.toArray().length;
        Hotel[] sortedHotels = new Hotel[len];
        int i = 0;
        for (Object hotel: hotelsInCity) {
            sortedHotels[i] = (Hotel) hotel;
            i++;
        }
        return sortedHotels;
    }

    /**
     * This function gets the best rated hotels around a given geographic location.
     * @param latitude Double number representing the a geographic coordinate (north-south).
     * @param longitude Double number representing the a geographic coordinate (west-east).
     * @return array of hotels,  sorted according to their (euclidean) distance
     * from the given geographic location, in ascending order.
     * Hotels that are at the same distance from the given location are organized according
     * to the number of points-of-interest for which they are close to (in a decreasing order).
     * In case of illegal input, this method returns an empty array.
     */
    public Hotel[] getHotelsByProximity(double latitude, double longitude){
        Hotel[] hotelsSorted = new Hotel[0];
        if (!checkCoordinates(latitude, longitude)) {
           return hotelsSorted;
        }
        Arrays.sort(this.hotels, new SortHotelsByProximity(latitude, longitude));
        return hotels;
    }

    /**
     * This method returns an array of top rated hotels in the city which are the closest to the given
     * coordinates (in WGS-84)
     * @param city The name of the city which the user wants to get hotels recommendation from.
     * @param latitude Double number representing the a geographic coordinate (north-south).
     * @param longitude Double number representing the a geographic coordinate (west-east).
     * @return An array of hotels in the given city, sorted according to their (euclidean) distance
     * from the given geographic location, in ascending order.
     * Hotels that are at the same distance from the given location are organized according to the number
     * of POI for which they are close to (in decreasing order). In case of illegal input,
     * this method returns an empty array.
     */
    public Hotel[] getHotelsInCityByProximity(String city, double latitude, double longitude){
        Hotel[] hotelsSorted = new Hotel[0];
        if (!checkCoordinates(latitude, longitude)) {
            return hotelsSorted;
        }
        hotelsSorted = getHotelsInCityByRating(city);
        Arrays.sort(hotelsSorted, new SortHotelsByProximity(latitude, longitude));
        return hotelsSorted;
    }

    /**
     * This method checks that the given coordinates are legal in the WGS-84 Geo standard.
     * @param latitude Double number representing the a geographic coordinate (north-south).
     * @param longitude Double number representing the a geographic coordinate (west-east).
     * @return True if the given coordinates are legal, false otherwise.
     */
    private boolean checkCoordinates(double latitude, double longitude){
        if (latitude > MAX_LAT || latitude < -MAX_LAT) {
            return false;
        }
        return !(longitude > MAX_LONG) && !(longitude < -MAX_LONG);
    }
}
