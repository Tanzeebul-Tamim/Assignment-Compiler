package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileUtility {
    private File directory;
    private String filePath;

    public FileUtility(File directory, String filePath) {
        this.directory = directory;
        this.filePath = filePath;
    }

    public String[] getFileNames() {
        File[] files = this.directory.listFiles();
        String[] fileNames = new String[files.length];
        int sequence = 0;

        if (files != null) {
            for (File file : files) {
                fileNames[sequence] = file.getName();
                sequence++;
            }
        }

        return fileNames;
    }

    public boolean readFiles() {
        File[] files = this.directory.listFiles();

        if (files == null || files.length == 0) {
            System.out.println("\nError: No files in the directory!");
            System.out.println("Press Ctrl + C to stop\n");
            return false;
        }

        for (File file : files) {
            if (file.isDirectory()) {
                System.out.println("Entering directory: " + file.getName());

                FileUtility subDirectoryUtility = new FileUtility(file, file.getAbsolutePath());
                subDirectoryUtility.readFiles();
            } else {
                try (Scanner fileScanner = new Scanner(file)) {
                    System.out.println("\nReading file: " + file.getName());

                    // while (fileScanner.hasNextLine()) {
                    //     String line = fileScanner.nextLine();
                    //     System.out.println(line);
                    // }
                } catch (FileNotFoundException error) {
                    System.out.println("Error: The file '" + file.getName()
                            + "' could not be found/accessed!\n");
                }
            }
        }

        return true;
    }
}
