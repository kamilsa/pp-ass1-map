package ru;

import com.google.common.collect.Sets;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Kamil Salahiev on 06/09/16
 */
public class SafeInvertedIndex implements InvertedIndex {

    private Map<String, Set<Document>> docMap;

    SafeInvertedIndex(){
        docMap = new ConcurrentHashMap<>();
    }

    public void addPair(String key, Document value) {
        docMap.putIfAbsent(key, Sets.newConcurrentHashSet());
        docMap.get(key).add(value);
    }

    public boolean checkIfDocExist(String key, Document value) {
        return docMap.containsKey(key) && docMap.get(key).contains(value);
    }

    @Override
    public Set<Document> getDocumentsByWord(String word) {
        return docMap.get(word);
    }

    @Override
    public Map<String, Set<Document>> getMap() {
        return docMap;
    }
}
