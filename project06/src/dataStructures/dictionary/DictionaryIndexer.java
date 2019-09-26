package dataStructures.dictionary;

import dataStructures.Aindexer;
import processing.parsingRules.IparsingRule;
import processing.searchStrategies.DictionarySearch;
import processing.textStructure.Block;
import processing.textStructure.Corpus;
import processing.textStructure.Entry;
import processing.textStructure.Word;
import utils.Stemmer;
import utils.Stopwords;
import utils.WrongMD5ChecksumException;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.zip.Adler32;
import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * An implementation of the abstract Aindexer class, backed by a simple hashmap to store words and their
 * locations within the files.
 */
public class DictionaryIndexer extends Aindexer<DictionarySearch> {

	private static final int ADJUST_ONE = 1;
	private static final Stemmer STEMMER = new Stemmer();
	public static final IndexTypes TYPE_NAME = IndexTypes.DICT;
	/** one of the fields in the cache file */
	private static final String DICT = "DICT";
	/** Under score for the cache file*/
	private static final Object UNDERSCORE = "_";
	/** HashMap field*/
    private HashMap<Integer, List<Word>> dict = new HashMap<>();
	/** The dir slash */
	private static final String DIR_SLASH = "\\";
	/** The name of the cache */
	private String fullName;
	/** The corpus's path */
	private String corpusPath;
	/** the cachedFilePath */
	private String cachedFileName;
	private String corpusName;
	private String parserRule;

	/**
	 * Basic constructor, sets origin Corpus and initializes backing hashmap
	 * @param origin    the Corpus to be indexed by this DS.
	 */
	public DictionaryIndexer(Corpus origin) {
		super(origin);
		this.corpusPath = this.origin.getPath();
		File fname = new File(this.corpusPath);
		this.corpusName = fname.getName();
		this.parserRule = this.origin.getParserName();
		this.fullName = DICT + UNDERSCORE + parserRule +
				UNDERSCORE + corpusName + ".cache";

		File corpus = new File(this.corpusPath);
		if (corpus.isDirectory()){
			this.cachedFileName= this.corpusPath + DIR_SLASH + this.fullName;
		}
		else{
			this.cachedFileName = corpus.getParent() + DIR_SLASH + this.fullName;
		}

	}

	/**
	 * This function checks if there is a cached file, if so it loads it,
	 * else it will throw an exception.
	 * @throws WrongMD5ChecksumException if the check sum is not the same.
	 * @throws FileNotFoundException if the file is not even found.
	 */
	@Override
	protected void readIndexedFile() throws WrongMD5ChecksumException, FileNotFoundException {
		try {
			File file = new File(this.cachedFileName);
			if (!file.exists()) {
				throw new FileNotFoundException();
			}
			FileInputStream input = new FileInputStream(file);
			ObjectInputStream objects = new ObjectInputStream(input);
			// read the checksum
			String md5 = (String) objects.readObject();
			// if the checksum is not the same
			if (!md5.equals(this.origin.getChecksum())) {
				throw new WrongMD5ChecksumException();
			}
			// read the corpus
			this.origin = (Corpus) objects.readObject();
			// read the indexer
			this.dict = (HashMap<Integer, List<Word>>) objects.readObject();
		} catch (IOException e) {
			// when the file is not found.
			throw new FileNotFoundException();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void writeIndexFile() {
		File newFile = new File(cachedFileName);
		try (FileOutputStream file = new FileOutputStream(newFile)){
			ObjectOutputStream objectInserter = new ObjectOutputStream(file);

			// hashing them:
			String checksum = origin.getChecksum();

			// Writing the checksum
			objectInserter.writeObject(checksum);
			// Writing the corpus
			objectInserter.writeObject(origin);
			// Writing the Indexer
			objectInserter.writeObject(dict);

			objectInserter.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void indexCorpus() {
		for (Entry entry: origin) {
			for (Block block: entry) {
				String blk = block.toString();
				indiceBlock(blk, block);
			}
		}
	}

	/**
	 * this function indices each block and call putWordInDictionary for adding the word to dictionary
	 * @param blk - a string of the block
	 * @param block - a block object
	 */
	private void indiceBlock(String blk, Block block) {
		int pStart = 0;
		int pEnd = 0;
		while (pEnd < blk.length() - ADJUST_ONE) {
			if (isValid(blk.charAt(pEnd))) {
				pEnd++;
				while (isValid(blk.charAt(pEnd))) {
					if (pEnd < blk.length() - ADJUST_ONE) {
						pEnd++;
					}
					else {
						String word = blk.substring(pStart, pEnd + ADJUST_ONE);
						putWordInDictionary(word, block, pStart, pEnd + ADJUST_ONE);
						break;
					}
				}
			}
			else {
				pEnd ++;
				pStart = pEnd;
			}
			String word = blk.substring(pStart, pEnd);
			putWordInDictionary(word, block, pStart, pEnd);
		}
	}

	/**
	 * this function stem the word and insert it to the dictionary
	 * @param word - string representation of the word
	 * @param block - a block object
	 * @param pStart - the start pointer of the word in the block
	 * @param pEnd - the end pointer of the word in the block
	 */
	private void putWordInDictionary(String word, Block block, int pStart, int pEnd) {
		if (!Stopwords.isStopword(word)) {
			word = STEMMER.stem(word);
			if (!Stopwords.isStemmedStopword(word)) {
				List<Word> words1 = dict.get(word.hashCode());
				Word wordObj = new Word(block, block.getStartIndex() + pStart, block.getStartIndex() +pEnd);
				if (words1 != null) {
					words1.add(wordObj);
				}
				else {
					words1 = new ArrayList<>();
					words1.add(wordObj);

				}
				dict.put(word.hashCode(), words1);
			}
		}
	}

	/**
	 * checks if the char is digit letter it ' for valid word.
	 * @param c a char.
	 * @return true if its valid, false otherwise.
	 */
	private boolean isValid(char c) {
		return Character.isDigit(c) || Character.isLetter(c) || c== '\'';
	}

	@Override
	public IparsingRule getParseRule() {
		return this.origin.getParsingRule();
	}

	@Override
	public DictionarySearch asSearchInterface() {
		return new DictionarySearch(dict);
	}
}
