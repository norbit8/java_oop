package processing.parsingRules;

import processing.textStructure.Block;

import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParsingHelper {

    void setCrew(ArrayList<String> meta, Block block, Pattern p) {
        String[] lines = block.toString().split("\n");
        Matcher m = p.matcher("");
        String line;
        for (int i = 0; i < lines.length; i++) {
            line = lines[i].trim();
            m = p.matcher(line);
            if (m.matches()) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(line).append(" ");
                i++;
                line = lines[i];
                while (!line.trim().equals("")) {
                    stringBuilder.append(line.trim().replace("&", ",")).append(" ");
                    i++;
                    line = lines[i];
                }
                String temp = stringBuilder.toString();
                stringBuilder.setCharAt(temp.length() - 2, '.');
                meta.add(stringBuilder.toString());
            }
        }
    }

    void setSceneName(ArrayList<String> meta, Block block) {
        String[] lines = block.toString().split("\n");
        String line = lines[0];
        String[] lineArr = lines[0].trim().split(" ");
        String sceneNumber = lineArr[0];

        Pattern p1 = Pattern.compile("(\\D)");
        Matcher m1 = p1.matcher(line);
        StringBuilder sceneName = new StringBuilder();
        StringBuilder scenedetails = new StringBuilder();
        scenedetails.append("Appearing in scene ").append(sceneNumber).append(", titled ");
        while (m1.find()) {
            sceneName.append(m1.group(0));
        }
        scenedetails.append(sceneName.toString().trim());
        meta.add(0, scenedetails.toString());
    }

    void createBlockMeta(Block block, ArrayList<String> blockMeta, Pattern p) {
        String[] lines = block.toString().split("\n");
        Matcher m = p.matcher("");
        String line;
        HashSet<String> hash_Set = new HashSet<>();
        for (int i = 0; i < lines.length; i++) {
            line = lines[i];
            m = p.matcher(line);
            if (m.matches()) {
                hash_Set.add(line.trim());
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("With the characters: ");
        for (String name: hash_Set) {
            if (!name.equals("THE END")) {
                stringBuilder.append(name + " ");
            }
        }
        // if there is no charecteres in the scene
        if (hash_Set.size() > 0) {
            blockMeta.add(stringBuilder.toString());
        }
    }


    void createBlocks(ArrayList<Long> arr, ArrayList<Block> blocks, RandomAccessFile inputFile) {
        for (int i = 0; i < arr.size() - 1; i++) {
            blocks.add(parseBlock(inputFile, arr.get(i), arr.get(i + 1) - 1));
        }
    }


    Block parseBlock(RandomAccessFile inputFile, long startPos, long endPos) {
        return new Block(inputFile, startPos, endPos);
    }

    void printMeta(ArrayList<String> meta) {
        for (String line:meta) {
            System.out.println(line);
        }
        System.out.println("\n***********\n");
    }

    public List<Block> parseFileHelper(RandomAccessFile inputFile, ArrayList<Long> arr, Pattern p1,
                                       Pattern p2) {

        ParsingHelper helper = new ParsingHelper();
        ArrayList<String> meta = new ArrayList<>();
        ArrayList<Block> blocks = new ArrayList<>();

        //creates blocks form each scene
        helper.createBlocks(arr, blocks, inputFile);

        //add the first block for entry meta data
        blocks.add(0, parseRawBlock(inputFile, 0, arr.get(0)));
        //set crew
        helper.setCrew(meta, blocks.get(0), p1);
        //remove entry meta data block information
        blocks.remove(0);
        for (Block block: blocks) {
            ArrayList<String> blockMeta = new ArrayList<>();
            //find the names of the characters in the scene
            helper.createBlockMeta(block, blockMeta, p2);

            //add the entry data to meta list
            blockMeta.addAll(meta);

            //creates the scene name and number
            helper.setSceneName(blockMeta, block);

            block.setMetadata(blockMeta);
        }
        return blocks;
    }

    public Block parseRawBlock(RandomAccessFile inputFile, long startPos, long endPos) {
        return new Block(inputFile, startPos, endPos);

    }
}