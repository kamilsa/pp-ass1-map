package ru;

import java.util.Map;
import java.util.Set;

/**
 * @author Kamil Salahiev on 06/09/16
 */
public interface InvertedIndex {

    void addPair(String key, Document value);

    boolean checkIfDocExist(String word, Document value);

    Set<Document> getDocumentsByWord(String word);

    Map<String, Set<Document>> getMap();

}
