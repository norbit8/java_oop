package Section;

import java.io.File;
import java.util.function.Predicate;

/**
 * this class creates section objects which arguemnt parameters
 */
public class Section {
    /**
     * this holds the line of corpus.
     */
    private File corpus;
    /**
     * this hold the line of indexer
     */
    private SectionFactory.Indexer indexer;
    /**
     * this hold the line of parseRule
     */
    private SectionFactory.ParseRuler parseRule;
    /**
     * this hold the line of query
     */
    private Query query;


    /**
     * simple constrictor
     * @param corpus - holds the corpus  from the factory
     * @param indexer - holds the indexer from the factory
     * @param parseRule - holds the parseRule from the factory
     * @param query - holds the query from the factory
     */
    Section(File corpus, SectionFactory.Indexer indexer, SectionFactory.ParseRuler parseRule,
            Query query) {
        this.corpus = corpus;
        this.indexer = indexer;
        this.parseRule = parseRule;
        this.query = query;
    }

    /**
     * simple constrictor
     * @param corpus - holds the corpus  from the factory
     * @param indexer - holds the indexer from the factory
     * @param parseRule - holds the parseRule from the factory
     */
    Section(File corpus, SectionFactory.Indexer indexer, SectionFactory.ParseRuler parseRule) {
        this.corpus = corpus;
        this.indexer = indexer;
        this.parseRule = parseRule;
        this.query = null;
    }

    /**
     * a corpus getter function
     * @return - the Filter indicator as String
     */
    public File getCorpus() {
        return corpus;
    }

    /**
     * a indexer getter function
     * @return - the order indicator as string
     */
    public SectionFactory.Indexer getIndexer() throws ArgumentError {
        return indexer;
    }

    /**
     * a parseRule getter function
     * @return the parsing rule enum
     */
    public SectionFactory.ParseRuler getParseRule() {
        return parseRule;
    }

    /**
     * a query getter function
     * @return could return null if empty, else it will return the query
     */
    public Query getQuery() {
        return query;
    }
}
