package ru;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Kamil Salahiev on 06/09/16
 */
public class MultithreadUploader {
    private List<File> listOfFiles;
    private InvertedIndex index;
    private final boolean concurrent;

    private final int THREAD_POOL_SIZE = 8;

    public MultithreadUploader(String folderPath, boolean concurrent){
        File folder = new File(folderPath);
        listOfFiles = Arrays.asList(folder.listFiles());
        this.concurrent = concurrent;
        if (!concurrent)
            index = new NotSafeInvertedIndex();
        else
            index = new SafeInvertedIndex();
    }

    public InvertedIndex parallelUpload(){

        ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

        listOfFiles
            .forEach(f -> executor.execute(() -> {
                Document doc = new Document(f);
                Arrays.stream(doc.getContent())
                    .forEach(word -> index.addPair(word.toLowerCase().trim(), doc));
            }));
        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        List<Thread> threads = new ArrayList<>();
//        listOfFiles
//            .forEach(f -> threads.add(new Thread(new Uploader(f, index))));
//        threads.forEach(Thread::start);
//        threads.forEach(t -> {
//            try {
//                t.join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        });

        return index;
    }

    public boolean isConcurrent() {
        return concurrent;
    }
}
