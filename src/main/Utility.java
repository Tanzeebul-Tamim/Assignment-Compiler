package main;

import java.io.File;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class Utility {
    private String fileName;
    private String extension;
    AtomicInteger fileCount = new AtomicInteger(0);

    public void setFileNameAndExt(String fileName, String extension) {
        this.fileName = fileName;
        this.extension = extension;
    }

    public File validateDirectoryPath() {
        Scanner sc = new Scanner(System.in);
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
                    sc.close();
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
            sc.close();
            return null;
        }
    }

    public FileUtility validateDirectoryPath(File directory) {
        FileUtility fileUtil = new FileUtility(directory, directory.getAbsolutePath(), fileName, extension, fileCount);
        boolean isValid = fileUtil.readFiles();

        if (isValid) {
            return fileUtil;
        } else {
            return null;
        }
    }

    public String formatName(String name) {
        name = name.trim();
        String[] words = name.split("\\s+");
        StringBuilder formattedName = new StringBuilder();

        for (String word : words) {
            if (!word.isEmpty()) {
                formattedName
                        .append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1).toLowerCase())
                        .append(" ");
            }
        }

        return formattedName.toString().trim();
    }
}