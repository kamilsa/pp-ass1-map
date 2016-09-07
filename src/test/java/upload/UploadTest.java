package upload;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import ru.*;

/**
 * @author Kamil Salahiev on 06/09/16
 */
public class UploadTest {

    String folderPath = "./src/main/resources";

    @Test
    public void nonConcurrentTest(){
        runExperiment(new MultithreadUploader(folderPath, false));
    }

    @Test
    public void concurrentTest(){
        runExperiment(new MultithreadUploader(folderPath, true));
    }

    public void runExperiment(MultithreadUploader uploader){
        int n = 20;
        Long total = 0L;
        for (int i = 0; i < n; i++) {
            Long t0 = System.currentTimeMillis();
            InvertedIndex index = uploader.parallelUpload();
            Long t1 = System.currentTimeMillis();
            total += t1-t0;
//            System.out.println("Inserted in " + (t1 - t0));


            File folder = new File(folderPath);
            List<File> listOfFiles = Arrays.asList(folder.listFiles());
//        index.getMap()
//            .forEach((k,v) -> System.out.println(k + ": " + Arrays.toString(v.toArray())));
            listOfFiles
                .forEach(f -> {
                    Document doc = new Document(f);
                    Arrays.stream(doc.getContent())
                        .forEach(word -> {
                            word = word.toLowerCase().trim();
                            if (index.checkIfDocExist(word, doc) == false) {
                                System.out.println(word + ": " + doc.file.hashCode());
                            }
                            Assert.assertTrue(index.checkIfDocExist(word, doc));
                        });
                });
        }
        System.out.println("Concurrent: " + uploader.isConcurrent() + " Avg time: " + total/n + " ms");
    }
}
