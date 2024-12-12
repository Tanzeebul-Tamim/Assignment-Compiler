package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class FileUtility {
    private Scanner scanner;
    private Utility utils;
    private File[] fileList;
    private String filePath;
    private String fileName;
    private String fileExtension;
    private AtomicInteger fileCount;
    private StringBuilder content;

    public FileUtility(
            Scanner scanner,
            Utility utils,
            File[] fileList,
            String filePath,
            String fileName,
            String fileExtension,
            AtomicInteger fileCount) {
        this.scanner = scanner;
        this.utils = utils;
        this.fileList = fileList;
        this.filePath = filePath;
        this.fileName = fileName;
        this.fileExtension = fileExtension;
        this.fileCount = fileCount;
        this.content = new StringBuilder();
    }

    public List<String> getFileNames() {
        List<String> fileNames = new ArrayList<>();

        if (fileList != null) {
            for (File file : fileList) {
                if (file != null) {
                    if (file.isDirectory()) {
                        // Recursively add files from subdirectories
                        FileUtility subDirectory = new FileUtility(
                                this.scanner,
                                this.utils,
                                file.listFiles(),
                                file.getAbsolutePath(),
                                fileName,
                                fileExtension,
                                fileCount);

                        // Add the file names found in the subdirectory
                        fileNames.addAll(subDirectory.getFileNames());
                    } else {
                        // Add file name if it's a file
                        fileNames.add(file.getName());
                    }
                }
            }
        }

        return fileNames;
    }

    public void setFileList(String[] fileNames) {
        List<File> sequencedFiles = new ArrayList<>(); // Store files in the correct sequence
        traverseAndMatchFiles(this.fileList, fileNames, sequencedFiles);
        this.fileList = sequencedFiles.toArray(new File[0]); // Update the fileList with the correctly sequenced files
    }

    private void traverseAndMatchFiles(File[] files, String[] fileNames, List<File> sequencedFiles) {
        if (files == null)
            return;

        for (File file : files) {
            if (file != null) {
                if (file.isDirectory()) {
                    // Recursive traversal for subdirectories
                    traverseAndMatchFiles(file.listFiles(), fileNames, sequencedFiles);
                } else {
                    // Match file name with the sequenced array
                    for (String fileName : fileNames) {
                        if (file.getName().equals(fileName)) {
                            sequencedFiles.add(file);
                            break;
                        }
                    }
                }
            }
        }
    }

    public void validateExtension() {
        File[] fileList = new File[this.fileList.length];
        int count = 0;

        for (File file : this.fileList) {
            if (file.getName().endsWith(fileExtension)) {
                fileList[count++] = file;
            } else {
                System.out.println("Skipping file:" + file.getName() + ": unsupported extension.");
            }
        }

        this.fileList = fileList;
    }

    public void readFiles() {
        for (File file : this.fileList) {
            if (file != null) {
                if (file.isDirectory()) {
                    System.out.println("\nEntering directory: " + file.getName());

                    FileUtility subDirectoryUtility = new FileUtility(
                            this.scanner,
                            this.utils,
                            file.listFiles(),
                            file.getAbsolutePath(),
                            fileName,
                            fileExtension,
                            fileCount);

                    subDirectoryUtility.readFiles();
                    content.append(subDirectoryUtility.content);

                } else {
                    try (Scanner fileScanner = new Scanner(file)) {
                        System.out.println("\nReading file: " + file.getName());

                        int taskNumber = fileCount.incrementAndGet();
                        String taskNo = "// TASK " + taskNumber + "\n\n";

                        StringBuilder fileContent = new StringBuilder();
                        fileContent.append(taskNo);

                        while (fileScanner.hasNextLine()) {
                            String line = fileScanner.nextLine();

                            fileContent
                                    .append(line)
                                    .append(System.lineSeparator());
                        }

                        fileContent.append("\n\n");
                        content.append(fileContent);
                        System.out.println("Added content from: " + file.getName());

                    } catch (FileNotFoundException err) {
                        System.out.println("Error: The file '" + file.getName()
                                + "' could not be found/accessed!\n");

                    }
                }
            }
        }
    }

    public void writeFiles() {
        String outputPath = filePath + File.separator + fileName + ".txt";
        File outputFile = new File(outputPath);

        if (outputFile.exists()) {
            System.out.println("\nA file with the name '" + fileName + ".txt' already exists in the directory.\n");
            System.out.println("Options: [1] Overwrite, [2] Create New Version, [3] Skip");
            int choice = -1;

            while (choice < 1 || choice > 3) {
                System.out.print("Enter your choice: ");

                if (this.scanner.hasNextInt()) {
                    choice = this.scanner.nextInt();
                    System.out.println();
                } else {
                    System.out.println();
                    System.out.println("Please enter a valid option (1, 2, or 3).");
                    this.scanner.nextLine(); // Clear invalid input
                }
            }

            if (choice == 1) {
                System.out.println("Overwriting the existing file...");

            } else if (choice == 2) {
                int version = 0;

                do {
                    String finalPath = filePath + File.separator + fileName + "(" + (++version) + ")" + ".txt";
                    outputFile = new File(finalPath);

                } while (outputFile.exists());

                System.out.println("Creating a new version of the file: " + outputFile.getName());

            } else {
                System.out.println("Skipping the file writing operation.");
                return;
            }
        }

        try {
            if (outputFile.createNewFile()) {
                System.out.println("\nCreated new output file in input directory.");

            }
        } catch (IOException err) {
            System.out.println(
                    "\nError: Failed to create file in the input directory. Writing to the current directory...");

            outputPath = System.getProperty("user.dir") + File.separator + "output.txt";
            outputFile = new File(outputPath);
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            writer.write(content.toString());
            writer.newLine();
            System.out.println("\nFile written successfully to: " + outputFile.getAbsolutePath());
            System.out.println("\nThank you for exploring this tool.");
        } catch (IOException err) {
            utils.printError();
        }
    }
}
