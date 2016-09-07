package ru;

/**
 * @author Kamil Salahiev on 06/09/16
 */
public class Main {

    public static void main(String[] args){
        System.out.println("hi");
        MultithreadUploader uploader = new MultithreadUploader("./src/main/resources", true);
        uploader.parallelUpload();
    }
}
