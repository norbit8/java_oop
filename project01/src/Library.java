/**
 * This class represents a library, which hold a collection of books.
 * Patrons can register at the library to be able to check out books,
 * if a copy of the requested book is available.
 */
public class Library {

    /** The maximum number of books a single patron can borrow. */
    final int maxBorrowedBooks;

    /** The length of the lib array (the list of the books) */
    int booksLength;

    /** The length of the patrons array */
    int patronsLength;

    /** Array of all the available books in the library */
    Book[] lib;

    /** Array of all the patrons which registered to the library */
    Patron[] patrons;

    /** A constant representing that the current book is not borrowed */
    final int NOT_BORROWED = -1;

    /** A constant representing that the patrons id is not registered to this library */
    final int NOT_PATRON = -1;

    final int BOOK_NOT_AVAILABLE = -1;
    /*----=  Constructors  =-----*/

    /**
     * Creates a new library with the given characteristic.
     * @param maxBookCapacity The maximal number of books this library can hold.
     * @param maxBorrowedBooks The maximal number of books this library allows
     * a single patron to borrow at the same time.
     * @param maxPatronCapacity The maximal number of registered patrons
     * this library can handle.
     */
    Library(int maxBookCapacity, int maxBorrowedBooks,
            int maxPatronCapacity) {
        this.maxBorrowedBooks = maxBorrowedBooks;
        this.patrons = new Patron[maxPatronCapacity];
        this.lib = new Book[maxBookCapacity];
        this.patronsLength = 0;
        this.booksLength = 0;
    }

    /**
     * Adds the given book to this library,
     * if there is place available,
     * and it isn't already in the library.
     * @param book The book to add to this library.
     * @return a non-negative id number for the book if there was a spot
     * and the book was successfully added,
     * or if the book was already in the library; a negative number otherwise.
     */
    int addBookToLibrary(Book book) {
        // The book is already in the library.
        if(getBookId(book) != -1) return getBookId(book);
        // The current library reached it's maximum capacity.
        if (this.lib.length <= this.booksLength) return -1;
        // new book, add it to the library.
        lib[this.booksLength] = book;
        return this.booksLength++;
    }

    /**
     * Checks if the book id exists in the library.
     * @param bookId Book id number. (int)
     * @return True if the book is in the library, otherwise false.
     */
    boolean isBookIdValid(int bookId) {
        return (this.booksLength > bookId && bookId >= 0);
    }

    /**
     * Gets the book id from the library array.
     * @param book A book instance.
     * @return The books id if its in the library, otherwise -1.
     */
    int getBookId(Book book) {
        for (int index = 0; index < this.booksLength; index++) {
            if (lib[index].equals(book)) return index;
        }
        return BOOK_NOT_AVAILABLE;
    }

    /**
     * Checks if the book is available in the library.
     * @param bookId id of a book (int)
     * @return true if the book is available, false otherwise.
     */
    boolean isBookAvailable(int bookId) {
        return (isBookIdValid(bookId) && lib[bookId].getCurrentBorrowerId() == NOT_BORROWED);
    }

    /**
     *  Registers the given Patron to this library,
     *  if there is a spot available.
     * @param patron An instance of a patron.
     * @return The patron id number if successfully registered, otherwise -1.
     */
    int registerPatronToLibrary(Patron patron) {
        // search if the patron is already in registered to the library.
        if (getPatronId(patron) != -1) return getPatronId(patron);
        // If the registration fails (return -1)
        if (this.patronsLength >= this.patrons.length ||
            isPatronIdValid(patronsLength)) return NOT_PATRON;
        // else just add the patron
        patrons[this.patronsLength] = patron;
        return this.patronsLength++;
    }

    /**
     * Returns true if the given number is an id of a patron in the library,
     * false otherwise.
     * @param patronId Patron id number (int)
     * @return True if the patron is registered, otherwise returns false.
     */
    boolean isPatronIdValid(int patronId) {
        return (this.patronsLength > patronId && 0 <= patronId);
    }

    /**
     * Gets the patron id from the patrons array.
     * @param patron An instance of a patron.
     * @return Non-negative id number of the given patron if he is registered to this library, -1 otherwise.
     */
    int getPatronId(Patron patron) {
        for (int index = 0; index < this.patronsLength; index++) {
            if (patrons[index].equals(patron)) {
                return index;
            }
        }
        return NOT_PATRON;
    }

    /**
     * Marks the book with the given id number as borrowed
     * by the patron with the given patron id.
     * @param bookId Book id number (int)
     * @param patronId Patron id number (int)
     * @return True if successfully borrowed, False otherwise.
     */
    boolean borrowBook(int bookId,
                       int patronId) {
        if (isBookAvailable(bookId) && isPatronIdValid(patronId)) {
            Book book = getBook(bookId);
            if(patronBooksNumber(patronId) < this.maxBorrowedBooks &&
               getPatron(patronId).willEnjoyBook(book)) {
                // its ok to borrow the book thus:
                book.setBorrowerId(patronId);
                return true;
            }
        }
        return false;
    }

    /**
     * Return the given book.
     * @param bookId book id (int).
     */
    void returnBook(int bookId) {
        if (!isBookIdValid(bookId)) {
            // Book id is not valid in this library
        } else {
            Book book = getBook(bookId);
            book.returnBook();
        }
    }

    /**
     * Suggest the patron with the given id
     * the book he will enjoy the most,
     * out of all available books he will enjoy,
     * if any such exist.
     * @param patronId An Id of a patron. (int)
     * @return Null if no book is suggested, the suggested book otherwise.
     */
    Book suggestBookToPatron(int patronId) {
        Book suggestedBook = null;
        int maxEnjoyment = 0;
        if (!isPatronIdValid(patronId)) {
            return null;
        }
        Patron patron = getPatron(patronId);
        for (int index = 0; index < this.booksLength; index++) {
            int booksId = index;
            Book book = lib[index];
            if (isBookAvailable(booksId) && (maxEnjoyment < patron.getBookScore(book)) &&
                patron.willEnjoyBook(book)){
                maxEnjoyment = patron.getBookScore(lib[index]);
                suggestedBook = lib[index];
            }
        }
        if (suggestedBook != null) return suggestedBook;
        return null;
    }

    /**
     * @param bookId A id number (int)
     * @return returns the book instancec (if exists else null)
     */
    Book getBook(int bookId) {
        if (isBookIdValid(bookId)) return lib[bookId];
        return null;
    }

    /**
     * Internal function returns the patron instance from
     * the patrons array.
     * NOTICE: use this function only after verifying that the
     * patrons id is valid (with the method isPatronIdValid.
     * @param patronId A patrons id number (int).
     * @return A patrons instance.
     */
    Patron getPatron(int patronId) {
        for (int index = 0; index < this.patronsLength; index++) {
            if (index == patronId) {
                return patrons[index];
            }
        }
        return null;
    }

    /**
     * Counts the number of books for a given patron.
     * @param patronId patron's id number (int).
     * @return Returns how many books the patron has.
     */
    int patronBooksNumber(int patronId) {
        int counter = 0;
        for (int index = 0; index < this.booksLength; index++) {
            if (lib[index].getCurrentBorrowerId() == patronId) {
                counter++;
            }
        }
        return counter;
    }
}