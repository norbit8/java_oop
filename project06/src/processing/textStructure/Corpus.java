package processing.textStructure;

import processing.parsingRules.IparsingRule;
import processing.parsingRules.STmovieParsingRule;
import processing.parsingRules.STtvSeriesParsingRule;
import processing.parsingRules.SimpleParsingRule;
import utils.MD5;

import java.io.*;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This class represents a body of works - anywhere between one and thousands of documents sharing the same structure and that can be parsed by the same parsing rule.
 */
public class Corpus implements Iterable<Entry>, Serializable {
	/**
	 * The serial version uid
	 */
	public static final long serialVersionUID = 1L;
	/**
	 * The cache file extension
	 */
	private static final String CACHE_EXT = ".cache";
	/**
	 * corpus fields: list of all the files/entries, Parsing rule, and the corpus path:
	 */
	private List<Entry> entryList;
	private IparsingRule parsingRule;
	private String corpusPath;
	private String parserName;

	/**
	 * Corpus constructor.
	 *
	 * @param path       A given path from the user to the corpus dir/file.
	 * @param parserName The parsing name.
	 * @throws IOException Throws an IOException when It fails to create files.
	 */
	public Corpus(String path, String parserName) throws IOException {
		this.parserName = parserName;
		this.parsingRule = checkParseRulerArgument(parserName);
		this.corpusPath = FileSystems.getDefault().getPath(path).normalize().toAbsolutePath().toString();
		this.entryList = new ArrayList<>();
		ArrayList<File> fileArr = new ArrayList<>();
		if (!convertPathToArray(this.corpusPath, fileArr)) {
			throw new IOException();
		}
		for (File file : fileArr) {
			this.entryList.add(new Entry(file.getAbsolutePath(), parsingRule));
		}
	}

	/**
	 * This method gets a string of a parser name and return its corresponding IparsingRule object.
	 *
	 * @param parserName String of a parsing rule
	 * @return a IparsingRule object.
	 */
	private IparsingRule checkParseRulerArgument(String parserName) {
		switch (parserName) {
			case "SIMPLE":
				return new SimpleParsingRule();
			case "ST_MOVIE":
				return new STmovieParsingRule();
			case "ST_TV":
				return new STtvSeriesParsingRule();
		}
		return null;
	}

	/**
	 * This method converts a path to array of files.
	 *
	 * @param path    Path to dir/file.
	 * @param fileArr A files array to populate.
	 * @return True for success.
	 */
	private boolean convertPathToArray(String path, ArrayList<File> fileArr) {
		//change relative path to absolute path
		String absolutePath = FileSystems.getDefault().getPath(path).normalize().toAbsolutePath().toString();
		File fileOrDirect = new File(absolutePath);
		if (!fileOrDirect.isDirectory()) {
			fileArr.add(fileOrDirect);
			return true;
		}
		File[] filesInsideDirectory = fileOrDirect.listFiles();
		if (filesInsideDirectory != null) {
			for (File file : filesInsideDirectory) {
				if (!file.getName().substring(file.getName().lastIndexOf(".")).equals(CACHE_EXT)) {
					if (file.isDirectory()) {
						convertPathToArray(file.getAbsolutePath(), fileArr);
					} else {
						fileArr.add(file);
					}
				}
			}
		}
		if (fileArr != null && fileArr.size() > 0) {
			return true;
		}
		System.err.print("Error: No files in directory");
		return false;
	}

	/**
	 * This method populates the Block lists for each Entry in the corpus.
	 */
	public void populate() throws IOException {
		for (Entry entry : this.entryList) {
			ArrayList<Block> blocks = new ArrayList<>();
			blocks.addAll(entry.getParseRule().parseFile(new RandomAccessFile(entry.getFilePath(), "r")));
			File file = new File(entry.getFilePath());
			String fileName = file.getName();
			for (Block block : blocks) {
				block.setEntryName(fileName);
			}
			entry.activateBlocks(blocks);
		}
	}

	/**
	 * The path to the corpus folder
	 *
	 * @return A String representation of the absolute path to the corpus folder
	 */
	public String getPath() {
		return this.corpusPath;
	}

	/**
	 * Iterate over Entry objects in the Corpus
	 *
	 * @return An entry iterator
	 */
	@Override
	public Iterator<Entry> iterator() {
		return this.entryList.iterator();
	}

	/**
	 * Return the checksum of the entire corpus. This is an MD5 checksum which represents all the files in
	 * the corpus.
	 *
	 * @return A string representing the checksum of the corpus.
	 * @throws IOException if any file is invalid.
	 */
	public String getChecksum() throws IOException {
		StringBuilder allFilesTogether = new StringBuilder();
		for (Entry entry : entryList) {
			allFilesTogether.append(entry.getFilePath());
		}
		String checksum = MD5.getMd5(allFilesTogether.toString());
		return checksum;
	}

	/**
	 * Return the parsing rule used for this corpus
	 *
	 * @return the parsing rule used for this corpus
	 */
	public IparsingRule getParsingRule() {
		return this.parsingRule;
	}

	/**
	 * getter for the parser name
	 *
	 * @return the parser's name
	 */
	public String getParserName() {
		return this.parserName;
	}

	/**
	 * Update the RandomAccessFile objects for the Entries in the corpus, if it was loaded from cache.
	 */
	public void updateRAFs() {
		for (Entry entry : this.entryList) {
			entry.setRafs();
		}
	}
}