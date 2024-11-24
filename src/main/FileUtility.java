package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class FileUtility {
    private File directory;
    private File[] fileList;
    private String filePath;
    private String fileName;
    private String fileExtension;
    private AtomicInteger fileCount;
    private StringBuilder content;

    public FileUtility(
            File directory,
            File[] fileList,
            String filePath,
            String fileName,
            String fileExtension,
            AtomicInteger fileCount) {
        this.directory = directory;
        this.fileList = fileList;
        this.filePath = filePath;
        this.fileName = fileName;
        this.fileExtension = fileExtension;
        this.fileCount = fileCount;
        this.content = new StringBuilder();
    }

    public String[] getFileNames() {
        File[] files = this.directory.listFiles();
        String[] fileNames = new String[files.length];
        int sequence = 0;

        if (files != null) {
            for (File file : files) {
                if (file != null) {
                    fileNames[sequence] = file.getName();
                    sequence++;
                }
            }
        }

        return fileNames;
    }

    public void readFiles() {
        for (File file : fileList) {
            if (file.isDirectory()) {
                System.out.println("\nEntering directory: " + file.getName());

                FileUtility subDirectoryUtility = new FileUtility(
                        file,
                        file.listFiles(),
                        file.getAbsolutePath(),
                        fileName,
                        fileExtension,
                        fileCount);

                subDirectoryUtility.readFiles();
                this.content.append(subDirectoryUtility.content);

            } else {
                if (file.getName().endsWith(fileExtension)) {
                    try (Scanner fileScanner = new Scanner(file)) {
                        System.out.println("\nReading file: " + file.getName());

                        int taskNumber = fileCount.incrementAndGet();
                        String taskNo = "// Task " + taskNumber + "\n\n";

                        StringBuilder fileContent = new StringBuilder();
                        fileContent.append(taskNo);

                        while (fileScanner.hasNextLine()) {
                            String line = fileScanner.nextLine();

                            fileContent
                                    .append(line)
                                    .append(System.lineSeparator());
                        }

                        fileContent.append("\n\n");
                        this.content.append(fileContent);
                        System.out.println("Added content from: " + file.getName());

                    } catch (FileNotFoundException err) {
                        System.out.println("Error: The file '" + file.getName()
                                + "' could not be found/accessed!\n");

                    }
                } else {
                    System.out.println("\nUnsupported file extension: " + file.getName());
                }
            }
        }
    }

    public void writeFiles() {
        String outputPath = filePath + File.separator + fileName + ".txt";
        File outputFile = new File(outputPath);

        if (!outputFile.exists()) {
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
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            writer.write(content.toString());
            writer.newLine();
            System.out.println("\nFile written successfully to: " + outputFile.getAbsolutePath());
            System.out.println("\nThank you for exploring this tool.");

        } catch (IOException err) {
            System.out.println("An error occurred while trying to write the output file.");
            System.out.println("Possible reasons:");
            System.out.println("- The program does not have permission to write in the specified directory.");
            System.out.println("- There may not be enough disk space available.");
            System.out.println("- The file path might be invalid or corrupted.");
        }
    }

}
