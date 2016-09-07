package ru;

import java.io.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

/**
 * @author Kamil Salahiev on 06/09/16
 */
public class Uploader implements Runnable
{

    File file;
    InvertedIndex index;
    public Uploader(File file, InvertedIndex index){
        this.file = file;
        this.index = index;
    }

    @Override
    public void run() {
        Scanner sc2 = null;
        Document doc = new Document(file);
        System.out.println(doc.file.getName());
        try {
            String content = readFile(file);
            Arrays.stream(content.split(" "))
                .forEach(word -> index.addPair(word.toLowerCase().trim(), doc));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private String readFile(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String         line = null;
        StringBuilder  stringBuilder = new StringBuilder();
        String         ls = System.getProperty("line.separator");

        try {
            while((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(ls);
            }

            return stringBuilder.toString();
        } finally {
            reader.close();
        }
    }
}
