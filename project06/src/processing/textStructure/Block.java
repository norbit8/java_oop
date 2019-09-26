package processing.textStructure;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * This class represents an arbitrary block of text within a file
 */
public class Block implements Serializable {
	public static final long serialVersionUID = 1L;

	private long endIdx;
	transient RandomAccessFile inputFile;
	private long startIdx;
	private String entryName;
	private List<String> metaData;

	/**
	 * Constructor
	 * @param inputFile     the RAF object backing this block
	 * @param startIdx      start index of the block within the file
	 * @param endIdx        end index of the block within the file
	 */
	public Block(RandomAccessFile inputFile, long startIdx, long endIdx) {
		this.inputFile = inputFile;
		this.startIdx = startIdx;
		this.endIdx = endIdx;

	}
	
	/**
	 * The filename from which this block was extracted
	 * @return  filename
	 */
	public String getEntryName(){
		return this.entryName;
	}


///////// getters //////////
	/**
	 * @return start index
	 */
	public long getStartIndex() {
		return startIdx;
	}
	
	/**
	 * @return  end index
	 */
	public long getEndIndex() {
		return endIdx;
	}
	
	/**
	 * Convert an abstract block into a string
	 * @return  string representation of the block (the entire text of the block from start to end indices)
	 */
	@Override
	public String toString() {
		byte[] blockInBytes = new byte[(int) (endIdx - startIdx)];
		try {
			inputFile.seek(startIdx);
			inputFile.read(blockInBytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new String(blockInBytes, StandardCharsets.UTF_8);
	}

	/**
	 * @return the RAF object for this block
	 */
	public RandomAccessFile getRAF() {
		return this.inputFile;
	}
	
	/**
	 * Get the metadata of the block, if applicable for the parsing rule used
	 * @return  String of all metadata.
	 */
	public List<String> getMetadata() {
		return this.metaData;
	}

///////// setters //////////

	/**
	 * Adds metadata to the block
	 * @param metaData A list containing metadata entries related to this block
	 */
	public void setMetadata(List<String> metaData) {
		this.metaData = metaData;
	}

	/**
	 * Sets the entry name
	 * @param fileName the raf.
	 */
	void setEntryName(String fileName) {
		this.entryName = fileName;
	}

	/**
	 * set the random access file
	 * @param raf the random access file
	 */
	public void setRaf(RandomAccessFile raf) {
		this.inputFile = raf;
	}
}