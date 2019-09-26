package processing.searchStrategies;

import processing.textStructure.*;

import java.util.*;

public class NaiveSearch implements IsearchStrategy {
	protected Corpus origin;
	private static final int QUERY_SIZE = 1;
	public NaiveSearch(Corpus origin) {
		this.origin = origin;
	}

	/**
	 * This function return a list of WordResults from a specific block.
	 * @param block A block object.
	 * @param query A query from the user.
	 * @return A list of WordResults from the given block.
	 */
	private List<WordResult> getWordResultFromBlock(Block block, String query) {
		List<WordResult> resLst = new ArrayList<>();
		String[] searched = new String[QUERY_SIZE];
		searched[0] = query;
		String blockStringified = block.toString();
		boolean found = true;
		for (int i = 0; i < blockStringified.length() - query.length(); i++) {
			// iterating over the block char by char and searching for the query
			for (int j = 0; j < query.length(); j++) {
				if (query.charAt(j) != blockStringified.charAt(i + j)) {
					found = false;
					break;
				}
			}
			if (found) {
				WordResult wordRes = new WordResult(block, searched, i);
				resLst.add(wordRes);
			}
			found = true;
		}
		return resLst;
	}

	/**
	 * The main search method to comply with the IsearchStrategy interface
	 * @param query The query string to search for.
	 * @return  A list of wordResults
	 */
	@Override
	public List<WordResult> search(String query) {
		List<WordResult> results = new ArrayList<>();
		Iterator corpusIter = origin.iterator();
		while(corpusIter.hasNext()) {
			Entry entry = (Entry) corpusIter.next();
			Iterator entryIter = entry.iterator();
			while(entryIter.hasNext()) {
				results.addAll(getWordResultFromBlock((Block) entryIter.next(), query));
			}
		}
		Collections.sort(results);
		return results;
	}

}
