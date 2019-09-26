import Section.*;
import dataStructures.Aindexer;
import dataStructures.dictionary.DictionaryIndexer;
import dataStructures.naive.NaiveIndexer;
import processing.searchStrategies.IsearchStrategy;
import processing.textStructure.Corpus;
import processing.textStructure.WordResult;

import java.io.IOException;
import java.util.List;

/**
 * The main program - A text searching module that indexes and queries large corpuses for strings or word groups
 */
public class TextSearcher {

    /** All the indexers*/
    private static final String DICT = "DICT";
    private static final String NAIVE = "NAIVE";
    private static final String NAIVE_RK = "NAIVE_RK";
    private static final String SUFFIX_TREE = "SUFFIX_TREE";
    private static final String CUSTOM = "CUSTOM";
    /** Maximum arguments of the program (only the route to the .conf file) */
    private static final int MAX_ARGS = 1;
    /** Usage Error constant */
    private static final String USAGE_ERR = "Usage error: please use only one file";
    /** helps print a separator */
    private static final String SEPARATOR = "=====================================================" +
            "=====================================================================================" +
            "=====================================================================================" +
            "=================================";
    /** helps print the top ten results */
    private static final int TOP_RES = 10;
    private static final String NO_RES = "Sorry, no matches found.";

    private static Aindexer createIndexer(SectionFactory.Indexer indexerName, Corpus corpus){
        switch (indexerName.name()){
            case DICT:
                return new DictionaryIndexer(corpus);
            case NAIVE:
                return new NaiveIndexer(corpus, false);
            case NAIVE_RK:
                return new NaiveIndexer(corpus, true);
            case SUFFIX_TREE:
                // TODO: fix this
                return null;
            case CUSTOM:
                // TODO: fix this
                return null;
            default:
                return null;
        }
    }

    /**
     * Main method. Reads and parses a command file and if a query exists, prints the results.
     * @param args
     */
    public static void main(String[] args) {
        // verifying that the user input is valid
        if (args.length != MAX_ARGS) {
            System.out.println(USAGE_ERR);
            return;
        }
        //creating the section
        Section section = SectionFactory.build(args[0]);
        SectionFactory.Indexer index;
        try {
            index = section.getIndexer();
        } catch (Exception e) {
            return;
        }
        SectionFactory.ParseRuler parserRule = section.getParseRule();
        // creating the corpus:
        Corpus corpus = null;
        try {
            corpus = new Corpus(section.getCorpus().getPath(), parserRule.name());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Aindexer indexer = createIndexer(index, corpus);
        try {
            indexer.index();
        } catch (IOException e) {
            e.printStackTrace();
        }
        IsearchStrategy searchEngine = indexer.asSearchInterface();
        // now if the query is not null then I should search!
        if (section.getQuery() != null) {
            String query = section.getQuery().getQuery();
            List<? extends WordResult> resLst = searchEngine.search(query);
            // iterating over the results:
            System.out.println(SEPARATOR);
            if (resLst != null) {
                for (int i = 0; i < TOP_RES; i++) {
                    // TODO: FIX HERE
                    try {
                        corpus.getParsingRule().printResult(resLst.get(i));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println(SEPARATOR);
                }
            } else {
                System.out.println(NO_RES);
            }
        }
    }
}