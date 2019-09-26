/**
 * This class represents a patron that has a name and values of different literary aspects.
 */
public class Patron {

    /** The first name of the patron. */
    String patronFirstName;

    /** The last name of the patron.*/
    String patronLastName;

    /** The weight the patron assigns to the comic aspects of books. */
    int comicTendency;

    /** The weight the patron assigns to the dramatic aspects of books. */
    int dramaticTendency;

    /** The weight the patron assigns to the educational aspects of books. */
    int educationalTendency;

    /** The minimal literary value a book must have for this patron to enjoy it. */
    int patronEnjoymentThreshold;

    /**
     * Creates a new patron with the given characteristics.
     * @param patronFirstName The first name of the patron.
     * @param patronLastName The last name of the patron.
     * @param comicTendency The weight the patron assigns to the comic aspects of books.
     * @param dramaticTendency The weight the patron assigns to the dramatic aspects of books.
     * @param educationalTendency The weight the patron assigns to the educational aspects of books.
     * @param patronEnjoymentThreshold The minimal literary value a book must have for this patron to enjoy.
     */
    Patron(String patronFirstName, String patronLastName,
           int comicTendency, int dramaticTendency,
           int educationalTendency, int patronEnjoymentThreshold) {
        this.patronFirstName = patronFirstName;
        this.patronLastName = patronLastName;
        this.comicTendency = comicTendency;
        this.dramaticTendency = dramaticTendency;
        this.educationalTendency = educationalTendency;
        this.patronEnjoymentThreshold = patronEnjoymentThreshold;
    }

    /**
     * stringRepresentation function:
     * @return A string representation of the patron. which is a sequence of its first and last name,
     * separated by a single white space.
     */
    String stringRepresentation() {
        return this.patronFirstName + " " + this.patronLastName;
    }

    /**
     * Gets the book score.
     * @param book A given book instance.
     * @return The literary value this patron assigns to the given book.
     */
    int getBookScore(Book book) {
        int comicScore = book.comicValue * this.comicTendency;
        int dramaticScore = book.educationalValue * this.educationalTendency;
        int educationalScore = book.dramaticValue * this.dramaticTendency;
        return comicScore + dramaticScore + educationalScore;
    }

    /**
     * @param book A given book instance.
     * @return True of this patron will enjoy the given book false otherwise.
     */
    boolean willEnjoyBook(Book book) {
        return getBookScore(book) >= this.patronEnjoymentThreshold;
    }
}