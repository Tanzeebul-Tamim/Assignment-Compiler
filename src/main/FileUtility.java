package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;
import java.util.Scanner;

public class FileUtility {
    private File directory;
    private String filePath;
    private String fileName;
    private StringBuilder content;

    public FileUtility(File directory, String filePath, String fileName) {
        this.directory = directory;
        this.filePath = filePath;
        this.fileName = fileName;
        this.content = new StringBuilder();
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
                System.out.println("\nEntering directory: " + file.getName());

                FileUtility subDirectoryUtility = new FileUtility(file, file.getAbsolutePath(), fileName);
                subDirectoryUtility.readFiles();
            } else {
                try (Scanner fileScanner = new Scanner(file)) {
                    if (file.getName().endsWith(".java")) {
                        System.out.println("\nReading file: " + file.getName());

                        StringBuilder fileContent = new StringBuilder();
                        while (fileScanner.hasNextLine()) {
                            String line = fileScanner.nextLine();
                            fileContent.append(line).append(System.lineSeparator());
                        }
                        content.append(fileContent);
                    } else {
                        System.out.println("\nUnsupported file extension: " + file.getName());
                    }
                } catch (FileNotFoundException error) {
                    System.out.println("Error: The file '" + file.getName()
                            + "' could not be found/accessed!\n");
                }
            }
        }

        return true;
    }

    public void writeFiles() {
        String outputPath = filePath + File.separator + fileName + ".txt";
        File outputFile = new File(outputPath);

        if (!outputFile.exists()) {
            try {
                if (outputFile.createNewFile()) {
                    System.out.println("\nCreated new output file in input directory.");
                }
            } catch (IOException error) {
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
        } catch (IOException error) {
            System.out.println("An error occurred while trying to write the output file.");
            System.out.println("Possible reasons:");
            System.out.println("- The program does not have permission to write in the specified directory.");
            System.out.println("- There may not be enough disk space available.");
            System.out.println("- The file path might be invalid or corrupted.");
        }
    }

}
