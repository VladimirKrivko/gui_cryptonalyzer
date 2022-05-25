package com.example.logics;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

public class IOTextFile {
    private final String inputPathFile;
    private final String outputPathFile;
    private final String text;        //мож быть final?


    public IOTextFile(String inputPathFile) throws IOException {
        this.inputPathFile = inputPathFile;

        text = initializeText();

        outputPathFile = inputPathFile.substring(0, inputPathFile.lastIndexOf(".")) +
                "_CRYPT" + inputPathFile.substring(inputPathFile.lastIndexOf("."));
    }


    public String getInputPathFile() {
        return inputPathFile;
    }

    public String getOutputPathFile() {
        return outputPathFile;
    }

    private String initializeText() throws IOException {
        try (FileInputStream fis = new FileInputStream(inputPathFile);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            int b;
            while ((b = fis.read()) != -1) {
                baos.write(b);
            }
            return baos.toString();
        }
    }

    public String getText() throws IOException {
        return this.text;
    }

    public void pushTextToFile(String text) throws IOException {
        try (FileWriter fw = new FileWriter(outputPathFile)) {
            fw.write(text);
        }
    }
}
