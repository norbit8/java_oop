package processing.textStructure;

import processing.parsingRules.IparsingRule;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * This class represents a single file within a Corpus
 */
public class Entry implements Iterable<Block>, Serializable {
	/** Serial version uid */
	public static final long serialVersionUID = 1L;
	/** Field of blocks */
	private ArrayList<Block> blocks = new ArrayList<>();
	private String filePath;
	private IparsingRule parseRule;
	private String fileName;

	/**
	 * Constructor
	 * @param filePath this is the file path.
	 * @param parseRule any parsing rule.
	 */
	public Entry(String filePath, IparsingRule parseRule) {
			this.filePath = filePath;
			this.parseRule = parseRule;
			File file = new File(this.filePath);
			this.fileName = file.getName();
	}

	/**
	 * Iterate over Block objects in the Entry
	 * @return a block iterator
	 */
	@Override
    public Iterator<Block> iterator() {
		return blocks.iterator();
    }

    // --- setters ---

	/**
	 * This method activates the blocks.
	 * @param allBlocks An array list of all the blocks in the entry.
	 */
	void activateBlocks(ArrayList<Block> allBlocks){
		this.blocks = allBlocks;
	}

	void setRafs(){
		try {
			RandomAccessFile file = new RandomAccessFile(this.filePath, "r");
			for (Block block : this.blocks){
				block.setRaf(file);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
    // --- getters ---

	/**
	 * getter for the file path
	 * @return a string represents the fle path.
	 */
	String getFilePath(){
		return this.filePath;
	}

	/**
	 * getter for the parsing rule
	 * @return parse rule
	 */
	IparsingRule getParseRule(){
		return this.parseRule;
	}

	/**
	 * returns the file's name.
	 * @return the file name.
	 */
	String getFileName() {
		return this.fileName;
	}
}