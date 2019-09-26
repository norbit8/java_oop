package manager;

import filters.Filter;
import orders.*;
import parser.*;

import java.io.BufferedReader;
import java.io.*;
import java.util.*;

/**
 * The module that runs it all,
 * It calls the parsing module to parse the file,
 * It iterate the different sections.
 * And it has a function which prints the result of all the work.
 */
public class Manager {

    /** The source directory */
    private File src;
    /** The commands file */
    private File cmd;
    /** The commands file location */
    private String commands;
    /** Error with the io, unable to open/read from file */
    private static final String ERR_IO = "ERROR: Failed reading form the file";
    /** Error with the subsections */
    private static final String BAD_SUBSECTION = "ERROR: Bad subsection name";
    /** Filter String constant */
    private static final String FILTER = "FILTER";
    /** Order String constant */
    private static final String ORDER = "ORDER";
    /** Minimum rows in the file */
    private static final int MINIMUM_ROWS = 3;
    /** The default order */
    private static final String ORDER_DEFAULT = "abs";
    /** Added lines */
    private static final int ADD_LINES = 2;
    /** Order Line */
    private static final int ORDER_LINE = 4;

    /**
     * Manager constructor, it initialize the manager object.
     * @param srcDir the source directory location (String).
     * @param command the command file location (String).
     * @throws InvalidUsageException This is an exception where the user uses the program incorrectly.
     */
    public Manager(String srcDir, String command) throws InvalidUsageException{
        this.src = new File(srcDir);
        this.cmd = new File(command);
        this.commands = command;
        checkValidInput();
    }

    /**
     * This function checks if the user input is valid.
     * @throws InvalidUsageException if the user input is not valid throws this exception
     */
    private void checkValidInput() throws InvalidUsageException{
        if (!this.src.exists() || !this.cmd.exists()) {
            throw new InvalidUsageException();
        }
        if (!this.cmd.isFile() || !this.src.isDirectory()){
            throw new InvalidUsageException();
        }
    }

    /**
     * readFile is the function which reads the file and break down each line to a string which
     * will be inserted into an ArrayList.
     * @return ArrayList of Strings, where each string represent a line in the given file.
     */
    private ArrayList<String> readFile() {
        ArrayList<String> lines = new ArrayList<>();
        // closes the input automatically when inside the try
        try (InputStream input = new FileInputStream(this.commands)) {
            // buffered
            BufferedReader br = new BufferedReader(new InputStreamReader(input));
            String line;
            while((line = br.readLine()) != null) lines.add(line);
            br.close();
        }
        catch (IOException ioe) {
            System.err.println(ERR_IO);
        }
        return lines;
    }

    /**
     * This function iterates over the srcDir and prints the result
     * according to the sections.
     */
    public void printResults(){
        ArrayList<String> lines = this.readFile();
        String filter;
        String order;
        int lineNumber = 1;
        int filterLine = 1;
        int orderLine = ORDER_LINE;
        // validate
        if (lines.size() == 0) return;
        if (lines.size() < MINIMUM_ROWS) {
            System.err.println(BAD_SUBSECTION);
            return;
        }
        try{
            firstScan();
        }
        catch (Exception e) {
            System.err.println(BAD_SUBSECTION);
            return;
        }
        // after knowing everything is valid:
        ListIterator<String> it = lines.listIterator();
        while (it.hasNext()) {
            // should be FILTER
            if (!it.next().equals(FILTER)){
                System.err.println(BAD_SUBSECTION);
                return;
            }
            if (!it.hasNext()) {
                System.err.println(BAD_SUBSECTION);
                return;
            }
            filter = it.next();
            filterLine++;
            // should be ORDER
            if(!it.hasNext() || !it.next().equals(ORDER)){
                System.err.println(BAD_SUBSECTION);
                return;
            }
            if (it.hasNext()) {
                order = it.next();
                if (!order.equals(FILTER)) {
                    lineNumber++;
                }
                else {
                    order = ORDER_DEFAULT;
                    it.previous();
                }
            }
            else order = ORDER_DEFAULT;
            lineNumber += ADD_LINES;
            Parser parser = new Parser(filter, order, filterLine, orderLine);
            WrapperFilterOrder filterAndOrder = parser.getFilterOrder();
            printAccordingly(filterAndOrder.getFilter(), filterAndOrder.getOrder());
            lineNumber++;
            filterLine = lineNumber;
            orderLine = lineNumber + 3;
        }
    }

    /**
     * This function verifies that the command file given from the user is valid.
     * @throws BadSubsectionException if the command file is not valid throw this exception.
     */
    private void firstScan() throws BadSubsectionException {
        ArrayList<String> lines = this.readFile();
        BadSubsectionException e = new BadSubsectionException();
        ListIterator<String> it = lines.listIterator();
        String item;
        while (it.hasNext()) {
            // should be FILTER
            if (!it.next().equals(FILTER)) throw e;
            if (!it.hasNext()) throw e;
            it.next();
            // should be ORDER
            if(!it.hasNext() || !it.next().equals(ORDER))throw e;
            if (it.hasNext()) {
                item = it.next();
                if (item.equals(FILTER)) it.previous();
            }
        }
    }

    /**
     * This function prints the files after sorting and filtering
     * @param filter The selected filter.
     * @param order The selected order.
     */
    private void printAccordingly(Filter filter, Order order) {
        ArrayList<File> processedFiles = new ArrayList<>();
        File files[] = this.src.listFiles();
        for (File dirFile : files) {
            if (filter.isPass(dirFile) && dirFile.isFile()) {
                processedFiles.add(dirFile);
            }
        }
        File[] arr = processedFiles.toArray(new File[0]);
        arr = order.sort(arr, 0, processedFiles.size() - 1);
        for(File file: arr) {
            System.out.println(file.getName());
        }
    }
}