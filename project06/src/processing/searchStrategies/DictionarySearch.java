package processing.searchStrategies;

import processing.textStructure.Block;
import processing.textStructure.MultiWordResult;
import processing.textStructure.Word;
import processing.textStructure.WordResult;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import utils.Stemmer;

public class DictionarySearch implements IsearchStrategy {

	private static final Stemmer STEMMER = new Stemmer();

	private static HashMap<Integer, List<Word>> dict;

	public DictionarySearch(HashMap<Integer, List<Word>> dict) {
		this.dict = dict;

	}

	@Override
	public List<? extends WordResult> search(String query) {
		String[] arr = query.split(" ");
		ArrayList<ArrayList<Word>> wordListArray = new ArrayList<>();
		for (int i = 0; i < arr.length; i++) {
			wordListArray.add((ArrayList<Word>) dict.get(STEMMER.stem(arr[i]).hashCode()));
		}
		// need to check if one of the words is not found means not good results'
		ArrayList<MultiWordResult> multiWordResults = new ArrayList();
		helper(wordListArray, arr.length -1 , new ArrayList<Word>(), multiWordResults, arr);
		System.out.println(multiWordResults);
		return null;
	}

	private void helper(ArrayList<ArrayList<Word>> wordListArray, int depth, ArrayList<Word> collect,
						ArrayList<MultiWordResult> multiWordResults, String[] arr) {
		if (depth < 0) {
			checkForWordResult(collect, multiWordResults, arr);
			return;
		}
		for (Word word: wordListArray.get(depth)) {
			ArrayList<Word> col =  new ArrayList<Word>();
			col.addAll(collect);
			col.add(word);
			helper(wordListArray, depth -1, col, multiWordResults, arr);
		}
	}

	private void checkForWordResult(ArrayList<Word> collect, ArrayList<MultiWordResult> multiWordResults,
									String[] arr) {
		boolean flag;
		Block block = collect.get(0).getSrcBlk();
		long[] longArr = new long[collect.size()];
		int i = 0;
		for (Word word: collect) {
			if (block != word.getSrcBlk()) {
				longArr[i] = word.getStartIndex();
				return;
			}
		}
		multiWordResults.add(new MultiWordResult(arr, block,longArr));
	}
}