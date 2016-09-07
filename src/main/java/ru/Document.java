package ru;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Kamil Salahiev on 06/09/16
 */
public class Document {
    private static AtomicInteger counter;
    {
        counter = new AtomicInteger(0);
    }

    private int id;
    public File file;

    public Document(File file) {
        this.file = file;
        this.id = file.hashCode();
    }

    public String[] getContent(){
        try {
            return readFile(file).split("[\\s\"\n.]");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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

    @Override
    public String toString() {
        return Integer.toString(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Document document = (Document) o;

        if (id != document.id) return false;
        return file != null ? file.equals(document.file) : document.file == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (file != null ? file.hashCode() : 0);
        return result;
    }
}
