package Section;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * this class is a factory to produce ArrayList of Sections.
 */
public class SectionFactory {
    /**
     * an error messages
     */
    private static final String COMMAND_LINE_NOT_IN_FORMAT = "ERROR: Argument File Not in format";
    private static final String FILE_NOT_FOUND = "ERROR: Argument File Not in format, corpus " +
                                                 "file / directory wasn't found";
    private static final String INDEXER_BAD_ARGUMENT = "Error, Indexer Argument is not valid";
    private static final String PARSER_RULER_BAD_ARGUMENT = "Error, Parser Ruler Argument is not valid";
    private static final String QUERY_BAD_ARGUMENT = "Error, Query Argument is not valid";
    private static final String BAD_LINES = "Error, lines after the query should be empty";


    /**
     * CORPUS indicator
     */
    private static final String CORPUS_INDICATOR = "CORPUS";
    /**
     * INDEXER indciator
     */
    private static final String INDEXER_INDICATOR = "INDEXER";
    /**
     * parse rule indicator
     */
    private static final String PARSE_RULE_INDICATOR = "PARSE_RULE";
    /**
     * query indicator
     */
    private static final String QUERY_INDICATOR = "QUERY";
    /**
     * enum for all the indexer valid arguments
     */
    public enum Indexer {
        DICT, NAIVE, NAIVE_RK, SUFFIX_TREE
    }
    /**
     * enum for all the pasrse ruler valid arguments
     */
    public enum ParseRuler { SIMPLE, ST_MOVIE, ST_TV}


    /**
     * this function build an array list of sections. if the command file in not as format it will
     * return null
     * @param path - the path of the command file
     * @return - list of sections
     */
    public static Section build(String path)  {
        Section sec;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(path)));
            String line = reader.readLine();

            //check CORPUS title
            checkTitles(line, CORPUS_INDICATOR);

            //advance one line down <> FROM CORPUS title -> CORPUS argument
            line = reader.readLine();

            //check if file exists
            File corpus = new File(line);
            checkFile(corpus);

            //advance one line down <> FROM CORPUS argument -> INDEXER title
            line = reader.readLine();

            //check INDEXER title
            checkTitles(line, INDEXER_INDICATOR);

            //advance one line down <> FROM INDEXER title -> INDEXER arguments
            line = reader.readLine();

            //check if indexer argument is valid
            Indexer indexer = checkIndexerArgument(line);

            //advance one line down <> FROM INDEXER arguments -> PARSER_RULE title
            line = reader.readLine();

            //check parser title
            checkTitles(line, PARSE_RULE_INDICATOR);

            //advance one line down <> FROM PARSER_RULE title -> PARSER_RULE arguments
            line = reader.readLine();

            //check if parser ruler argument is valid
            ParseRuler ruler = checkParseRulerArgument(line);

            //advance one line down <> FROM PARSER_RULE arguments -> QUERY titile
            line = reader.readLine();
            if(line == null) {
                sec = new Section(corpus,indexer,ruler);
                return sec;
            }
            //check query title
            checkTitles(line, QUERY_INDICATOR);

            //advance one line down last one in section
            line = reader.readLine();

            //check query arguments
            Query query = checkQuery(line);
            sec = new Section(corpus,indexer,ruler,query);


            line = reader.readLine();

            // check if the next lines are empty lines
            while ((line) != null) {
                if (!line.equals("\n"))  {
                    System.out.println(BAD_LINES);
                    return null;
                }
                line = reader.readLine();
            }
    }
        // when dealing with reader I.O Errors may occur
        catch (ArgumentError d) {
            return null;
        }
        catch (IOException|NullPointerException e) {
            System.out.println(COMMAND_LINE_NOT_IN_FORMAT);
            return null;
    }

        return sec;
    }

    private static Indexer checkIndexerArgument(String line) throws ArgumentError {
        switch (line) {
            case "DICT": return Indexer.DICT;
            case "NAIVE": return Indexer.NAIVE;
            case "NAIVE RK": return Indexer.NAIVE_RK;
            case "SUFFIX TREE":return Indexer.SUFFIX_TREE;
            default:
                System.out.println(INDEXER_BAD_ARGUMENT);
                throw new ArgumentError();
        }
    }

    private static ParseRuler checkParseRulerArgument(String line) throws ArgumentError {
        switch (line) {
            case "SIMPLE": return ParseRuler.SIMPLE;
            case "ST_MOVIE": return ParseRuler.ST_MOVIE;
            case "ST_TV": return ParseRuler.ST_TV;
            default:
                System.out.println(PARSER_RULER_BAD_ARGUMENT);
                throw new ArgumentError();
        }
    }

    private static void checkTitles(String title, String indicator) throws ArgumentError{
        if (!title.equals(indicator)) {
            System.out.println(COMMAND_LINE_NOT_IN_FORMAT);
            throw new ArgumentError();
        }
    }

    private static void checkFile(File file) throws ArgumentError{
        if (!file.exists()) {
            System.out.println(FILE_NOT_FOUND);
            throw new ArgumentError();
        }
    }

    private static Query checkQuery(String line) throws ArgumentError {
        if ((line == null) || line.equals(CORPUS_INDICATOR)) {
            return new Query(false, false , "");
        }
        String[] args = line.split("#");
        String query = args[0];
        // QUESTION : what is the definition of word? is " "(space) is a word? what about "?/!"
        if (query.equals("")) {
            System.out.println(QUERY_BAD_ARGUMENT);
            throw new ArgumentError();
        }
        boolean quick = false, sensitive = false;
        if (args.length > 1 && args.length < 4) {
            for (int i = 1; i < args.length ; i++) {
                if (args[i].equals("QUICK") && !quick ) {
                    quick = true;
                }
                else if (args[i].equals("CASE") && !sensitive ) {
                    sensitive = true;
                }
                else {
                    System.out.println(QUERY_BAD_ARGUMENT);
                    throw new ArgumentError();
                }
            }
        }
        return new Query(quick, sensitive, query);
    }
}
