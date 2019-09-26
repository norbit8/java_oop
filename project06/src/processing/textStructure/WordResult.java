package processing.textStructure;

import java.io.IOException;

/**
 * This class represents a result containing a single string (single word or multiple words treated as one)
 */
public class WordResult implements Comparable<WordResult>{
    private long idxInBlk;
    Block location;
    protected String[] content;

    /**
     * Simple constructor without index.
     * @param blk The block where this word was found
     * @param word  The word queried, represented as an array of size 1.
     */
    private WordResult(Block blk, String[] word){
        this.content = word;
        this.location = blk;
    }

    /**
     * Constructor containing index of word in block
     * @param blk The block where this word was found
     * @param word  The word queried, represented as an array of size 1.
     * @param idx   The index within the block where the word was found.
     */
    public WordResult(Block blk, String[] word, long idx) {
        this(blk, word);
        this.idxInBlk = idx;
    }

    /**
     * Getter for the result's block
     * @return  The block where this word was found.
     */
    public Block getBlock(){
        return this.location;
    }

	/**
	 * Getter for the queried word for this result
	 * @return The query word that generated this result
	 */
	public String[] getWord(){
        return this.content;
    }

    /**
     * This function returns the sentence from the word
     * @return all the sentence.
     */
    private String getSentence(){
        StringBuilder sentence = new StringBuilder();
        String blockText = this.location.toString();
        blockText = blockText.substring((int)this.idxInBlk);
        for (int i = 0; i <blockText.length(); i++){
            if (blockText.charAt(i) == '\n') {
                break;
            }
            sentence.append(blockText.charAt(i));
        }
        return sentence.toString();
    }

	/**
	 * Method for printing the result
	 * @return The result representation as defined by the "printing results"
     * requirement in the exercise instructions.
	 */
	public String resultToString(){
        StringBuilder meta = new StringBuilder();
        if (this.getBlock().getMetadata() != null) {
            meta.append("\n");
            for (String metaPart : this.getBlock().getMetadata()) {
                meta.append(metaPart).append("\n");
            }
        }
        return getSentence() + "\n" +"Taken out of the entry \"" + getBlock().getEntryName() +
                "\"" + meta.deleteCharAt(meta.lastIndexOf("\n"));
    }

    @Override
    public int compareTo(WordResult o) {
        return this.getBlock().getEntryName().compareTo(o.getBlock().getEntryName());
    }
}