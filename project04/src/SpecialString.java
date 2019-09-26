/**
 * Special class representing a String with another feature
 * which is a parameter which omits true if the string should be
 * considered as deleted, false otherwise.
 */
public class SpecialString {
    /** The string */
    private String str;
    /** The state of the string */
    private boolean deleted = false;

    /**
     * Default constructor
     */
    public SpecialString(){
    }

    /**
     * Constructor
     * @param str A given string.
     */
    public SpecialString(String str){
        this.str = str;
    }

    /* ----- Getters ----- **/

    /**
     * @return The string itself.
     */
    public String getString() {
        return str;
    }

    /**
     * @return true if the String is deleted, false otherwise.
     */
    public boolean isDeleted() {
        return this.deleted;
    }

    /* ----- Setters ----- **/

    /**
     * Sets the string to a new value
     * and sets the string as not deleted (deleted = false).
     * @param str A given string to change to.
     */
    public void setStr(String str) {
        this.str = str;
        this.deleted = false;
    }

    /**
     * Marks the String as deleted
     */
    public void deleteString() {
        this.deleted = true;
    }
}