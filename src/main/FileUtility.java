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
    private InputHandler input;
    private Utility utils;
    private File[] fileList;
    private String filePath;
    private String fileName;
    private String fileExtension;
    private AtomicInteger fileCount;
    private StringBuilder content;

    public FileUtility(
            InputHandler input,
            Utility utils,
            File[] fileList,
            String filePath,
            String fileName,
            String fileExtension,
            AtomicInteger fileCount) {
        this.input = input;
        this.utils = utils;
        this.fileList = fileList;
        this.filePath = filePath;
        this.fileName = fileName;
        this.fileExtension = fileExtension;
        this.fileCount = fileCount;
        this.content = new StringBuilder();
    }

    private FileUtility(File[] fileList) {
        this(null, null, fileList, null, null, null, null);
    }

    private FileUtility(File[] fileList, String fileExtension) {
        this(null, null, fileList, null, null, fileExtension, null);
    }

    public List<String> getFileNames() {
        List<String> fileNames = new ArrayList<>();

        if (fileList != null) {
            for (File file : fileList) {
                if (file != null) {
                    if (file.isDirectory()) {
                        // Recursively add files from subdirectories
                        FileUtility subDirectory = new FileUtility(file.listFiles());

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

    public void setFileList(String[][] categorizedFileNames) {
        if (categorizedFileNames == null || categorizedFileNames.length < 2) {
            throw new IllegalArgumentException("categorizedFileNames must have at least two non-null arrays.");
        }

        File[] sequencedFiles = new File[0];
        File[] nonSequencedFiles = new File[0];

        if (categorizedFileNames[0] != null) {
            sequencedFiles = new File[categorizedFileNames[0].length];
            traverseAndMatchFiles(this.fileList, categorizedFileNames[0], sequencedFiles);
        }

        if (categorizedFileNames[1] != null) {
            nonSequencedFiles = new File[categorizedFileNames[1].length];
            traverseAndMatchFiles(this.fileList, categorizedFileNames[1], nonSequencedFiles);
        }

        File[] combinedFiles = new File[sequencedFiles.length + nonSequencedFiles.length];

        System.arraycopy(sequencedFiles, 0, combinedFiles, 0, sequencedFiles.length);
        System.arraycopy(nonSequencedFiles, 0, combinedFiles, sequencedFiles.length, nonSequencedFiles.length);

        this.fileList = combinedFiles;
    }

    private void traverseAndMatchFiles(File[] fileList, String[] fileNames, File[] container) {
        if (fileList == null) {
            return;
        }

        int index = 0;

        for (String fileName : fileNames) {
            if (fileName == null) {
                break;
            } else {
                for (File file : fileList) {
                    if (file.isDirectory()) {
                        traverseAndMatchFiles(file.listFiles(), fileNames, container);
                    } else {
                        if (file.getName().equals(fileName)) {
                            container[index++] = file;
                            break;
                        }
                    }
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    private List<File>[] filterFileExt() {
        List<File> fileList = new ArrayList<>();
        List<File> unsupportedFileList = new ArrayList<>();
        List<File>[] separatedFiles = new List[2];

        for (File file : this.fileList) {
            if (file != null) {
                if (file.isDirectory()) {
                    FileUtility subDirectory = new FileUtility(file.listFiles(), this.fileExtension);

                    List<File>[] subDirectoryFiles = subDirectory.filterFileExt();
                    fileList.addAll(subDirectoryFiles[0]);
                    unsupportedFileList.addAll(subDirectoryFiles[1]);
                } else {
                    if (file.getName().endsWith(fileExtension)) {
                        fileList.add(file);
                    } else {
                        unsupportedFileList.add(file);
                    }
                }
            }
        }

        separatedFiles[0] = fileList;
        separatedFiles[1] = unsupportedFileList;

        return separatedFiles;
    }

    public void validateFileExt() {
        List<File>[] validFiles = filterFileExt();

        this.fileList = validFiles[0].toArray(new File[0]);
        File[] unsupportedFiles = validFiles[1].toArray(new File[0]);
        int fileCount = 0;

        System.out.println("\nFiles with Unsupported extensions:");

        for (File file : unsupportedFiles) {
            System.out.printf("   %s. %s\n",
                    String.format("%02d", ++fileCount),
                    file.getName(),
                    file.getName());
        }
    }

    public void readFiles() {
        System.out.println("\nReading files: ");
        int fileCount = 0;

        for (File file : this.fileList) {
            if (file != null) {
                if (file.isDirectory()) {
                    System.out.println("\nEntering directory: " + file.getName());

                    FileUtility subDirectoryUtility = new FileUtility(file.listFiles());
                    subDirectoryUtility.readFiles();
                    content.append(subDirectoryUtility.content);

                } else {
                    try (Scanner fileScanner = new Scanner(file)) {
                        System.out.printf("   %s. Added content from: '%s'\n",
                                String.format("%02d", ++fileCount),
                                file.getName());

                        int taskNumber = this.fileCount.incrementAndGet();
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
                        fileScanner.close();

                    } catch (FileNotFoundException err) {
                        System.out.println("Error: The file '" + file.getName()
                                + "' could not be found/accessed!\n");

                    }
                }
            }
        }
    }

    public void writeFiles() {
        String outputPath = filePath + File.separator + this.fileName + ".txt";
        File outputFile = new File(outputPath);

        if (outputFile.exists()) {
            String prompt1 = "Error: A file with the name '" + this.fileName + ".txt' already exists in the directory.\n";
            String prompt2 = "Choose and Option:";

            int choice = Integer
                    .parseInt(input.getUserChoice(prompt1, prompt2, 0, null, "Overwrite", "Create New Version", "Skip"));

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
                System.out.println("\n\nCreated new output file in input directory.");
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
            System.out.println("File written successfully to: " + outputFile.getAbsolutePath());
            System.out.println("\nThank you for exploring this tool.");
        } catch (IOException err) {
            utils.printError();
        }
    }
}
