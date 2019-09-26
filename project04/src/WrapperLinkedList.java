import java.util.LinkedList;

/**
 * Wrapper-class that has a LinkedList
 * and delegates methods to it
 */
public class WrapperLinkedList {

    /** Delegated string list */
    private LinkedList<String> list;

    /**
     * The constructor which initiates the wrapper linked list class.
     */
    public WrapperLinkedList(){
        this.list = new LinkedList<String>();
    }

    /**
     * Inserts the specified element at the specified position in this list.
     * @param element String element.
     */
    public void push(String element) {
        this.list.push(element);
    }

    /**
     * pops an element from the LinkedList.
     * @return The first element.
     */
    public String pop() {
        return this.list.pop();
    }

    /**
     * @return true if the list is empty, false otherwise.
     */
    public boolean isEmpty(){
        return this.list.isEmpty();
    }

    /**
     * @param str A string to search for.
     * @return Returns true if this list contains the specified element.
     */
    public boolean contains(String str) {
        return this.list.contains(str);
    }

    /**
     * Removes the first occurrence of the specified element from this list if it present
     * @param str The string which is going to be removed
     * @return true if we successfully removed it, false otherwise.
     */
    public boolean remove(String str) {
        return this.list.remove(str);
    }
}