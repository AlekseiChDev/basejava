package ru.javawebinar.basejava;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MainFile {
    public static void main(String[] args) {
        String filePath = ".\\.gitignore";

        File file = new File(filePath);
        try {
            System.out.println(file.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException("Error", e);
        }

        try (FileInputStream fis = new FileInputStream(filePath)) {
            System.out.println(fis.read());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        File dir = new File("./src/ru/javawebinar/basejava");
        outNameFiles(dir, "");
    }

    private static void outNameFiles(File dir, String shift) {
        File[] listFiles = dir.listFiles();
        if (listFiles != null) {
            for (File file : listFiles) {
                if (file.isDirectory()) {
                    System.out.println(shift + "[" +file.getName() + "]");
                    outNameFiles(file, shift + " ");
                } else {
                    System.out.println(shift + file.getName());
                }
            }
        }
    }
}