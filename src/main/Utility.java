package main;

import java.io.File;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Utility {
    public Scanner sc = new Scanner(System.in);
    private String fileName;

    public Utility(String fileName) {
        this.fileName = fileName;
    }

    public File validateDirectoryPath() {
        String filePath;
        File directory;

        try {
            System.out.println(
                    "Important Note:\nPlease ensure that the selected directory contains only the assignment files. Keeping other files (e.g., unrelated documents, images, or executable files) in the directory may cause unexpected issues while processing the assignments.\n\nFor best results:\n"
                            +
                            " - Organize your assignment files in a dedicated folder.\n" +
                            " - Verify that all files are relevant before proceeding.\n");

            System.out.println("\nPlease copy the file path to your assignment folder and paste here:\n");

            while (true) {
                filePath = sc.nextLine();

                if (filePath.startsWith("\"") && filePath.endsWith("\"")) {
                    filePath = filePath.substring(1, filePath.length() - 1);
                }

                directory = new File(filePath);

                if (directory.exists() && directory.isDirectory()) {
                    return directory;
                } else {
                    System.out.println(
                            "\nError: Invalid file path! Please double-check and enter a valid path:");
                    System.out.println("Press Ctrl + C to stop\n");
                }
            }
        } catch (NoSuchElementException | IllegalStateException error) {
            System.out.println("\n\nProgram terminated! Thank you for exploring this tool.");
            sc.close();
            System.exit(0);

            return null;
        } catch (Exception error) {
            System.out.println("\n\nProgram terminated due to an unexpected error.");
            return null;
        }
    }

    public FileUtility validateDirectoryPath(File directory) {
        FileUtility fileUtil = new FileUtility(directory, directory.getAbsolutePath(), fileName);
        boolean isValid = fileUtil.readFiles();

        if (isValid) {
            return fileUtil;
        } else {
            return null;
        }
    }
}