package processing.parsingRules;

import processing.textStructure.Block;
import processing.textStructure.WordResult;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class STmovieParsingRule implements IparsingRule, Serializable {
	public static final long serialVersionUID = 1L;

	@Override
	public Block parseRawBlock(RandomAccessFile inputFile, long startPos, long endPos) {
		return new Block(inputFile, startPos, endPos);
	}

	@Override
	public List<Block> parseFile(RandomAccessFile inputFile) {
		ParsingHelper helper = new ParsingHelper();
		ArrayList<Long> arr = new ArrayList<>();
		Pattern p1 = Pattern.compile("(\\w*) by:");
		Pattern p2 = Pattern.compile("\\s{42}([A-Z]|[a-z]| )*\\s*");
		//creates an array of pointer for each scene
		createPointerArray(inputFile, arr);
		return helper.parseFileHelper(inputFile, arr, p1 ,p2);
	}

	/**
	 * creates an array of pointers which hold the start of each block
	 * @param inputFile - the raf file
	 * @param arr - an array of pointers
	 */
	private void createPointerArray(RandomAccessFile inputFile, ArrayList<Long> arr) {
		try {
			Pattern p = Pattern.compile("(\\d)(.*)(\\d)");
			Matcher m = p.matcher("");
			Long pos = inputFile.getFilePointer();
			String line = inputFile.readLine();
			while (line != null) {
				line = line.trim();
				m = p.matcher(line);
				if (m.matches()) {
					arr.add(pos);
				}
				pos = inputFile.getFilePointer();
				line = inputFile.readLine();
			}
			arr.add(inputFile.getFilePointer() -1);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void printResult(WordResult wordResult)  {
		System.out.println(wordResult.resultToString());
	}
}