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
    private File[] fileList;
    private String filePath;
    private String fileName;
    private String fileExtension;
    private AtomicInteger fileCount;
    private StringBuilder content;

    public FileUtility(
            File[] fileList,
            String filePath,
            String fileName,
            String fileExtension,
            AtomicInteger fileCount) {
        this.fileList = fileList;
        this.filePath = filePath;
        this.fileName = fileName;
        this.fileExtension = fileExtension;
        this.fileCount = fileCount;
        this.content = new StringBuilder();
    }

    // Overloaded constructors, used for recursive traversing
    private FileUtility(File[] fileList) {
        this(fileList, null, null, null, null);
    }

    private FileUtility(File[] fileList, String fileExtension) {
        this(fileList, null, null, fileExtension, null);
    }

    // Gets all file names from a specific path including nested directories
    public List<String> getFileNames() {
        List<String> fileNames = new ArrayList<>();

        if (fileList != null) {
            for (File file : fileList) {
                if (file != null) {
                    if (file.isDirectory()) {
                        // Recursively add files from subdirectories
                        FileUtility subDirectory = new FileUtility(file.listFiles());

                        // Add the file names in the main arraylist, found in the subdirectory
                        fileNames.addAll(subDirectory.getFileNames());
                    } else {
                        // Add file name to the arraylist if it's a file
                        fileNames.add(file.getName());
                    }
                }
            }
        }

        return fileNames;
    }

    // Combines the received sequenced & non-sequenced file-lists sequentially
    public void setFileList(String[][] categorizedFileNames) {

        // Checks for invalid arguments
        if (categorizedFileNames == null || categorizedFileNames.length < 2) {
            throw new IllegalArgumentException("categorizedFileNames must have at least two non-null arrays.");
        }

        // Stores the files separately in 2 new arrays
        File[] sequencedFiles = new File[0];
        File[] nonSequencedFiles = new File[0];

        // Matches the filenames and creates a sequential array of files
        if (categorizedFileNames[0] != null) {
            sequencedFiles = new File[categorizedFileNames[0].length];
            traverseAndMatchFiles(this.fileList, categorizedFileNames[0], sequencedFiles);
        }

        // Matches the filenames and combines the non-sequential files in the same array
        if (categorizedFileNames[1] != null) {
            nonSequencedFiles = new File[categorizedFileNames[1].length];
            traverseAndMatchFiles(this.fileList, categorizedFileNames[1], nonSequencedFiles);
        }

        // Stores the final combined file list
        File[] combinedFiles = new File[sequencedFiles.length + nonSequencedFiles.length];

        // Combines both sequenced and non-sequenced arrays sequentially
        System.arraycopy(sequencedFiles, 0, combinedFiles, 0, sequencedFiles.length);
        System.arraycopy(nonSequencedFiles, 0, combinedFiles, sequencedFiles.length, nonSequencedFiles.length);

        this.fileList = combinedFiles;
    }

    /*
     * Iterates through all the file names
     * Matches the names with all the existing filenames
     * Sequentially sets the file names
     */
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
                        // Recursive traversing for sub directories
                        traverseAndMatchFiles(file.listFiles(), fileNames, container);
                    } else {
                        // Adds the file sequentially to the final (container) array if name matches
                        if (file.getName().equals(fileName)) {
                            container[index++] = file;
                            break;
                        }
                    }
                }
            }
        }
    }

    // Method to validate files with the valid extension
    @SuppressWarnings("unchecked")
    private List<File>[] validateFileExtension() {
        List<File> fileList = new ArrayList<>();
        List<File> unsupportedFileList = new ArrayList<>();
        List<File>[] separatedFiles = new List[2];

        for (File file : this.fileList) {
            if (file != null) {
                if (file.isDirectory()) {
                    // Recursively process subdirectories
                    FileUtility subDirectory = new FileUtility(file.listFiles(), this.fileExtension);
                    List<File>[] subDirectoryFiles = subDirectory.validateFileExtension();

                    fileList.addAll(subDirectoryFiles[0]);
                    unsupportedFileList.addAll(subDirectoryFiles[1]);
                } else {
                    // Files with only the valid extension gets selected
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

    // Validates file extensions, retains supported files, and logs unsupported ones
    public void filterFiles() {
        List<File>[] validFiles = validateFileExtension();

        this.fileList = validFiles[0].toArray(new File[0]);
        File[] unsupportedFiles = validFiles[1].toArray(new File[0]);
        int fileCount = 0;

        // Prints the name of files that have unsupported extensions
        System.out.println("\nFiles with Unsupported extensions:");

        for (File file : unsupportedFiles) {
            System.out.printf("   %s. %s\n",
                    String.format("%02d", ++fileCount),
                    file.getName(),
                    file.getName());
        }
    }

    /*
     * Reads all the files (files with valid extension) and builds a string
     * containing the content and stores them for further processing.
     */
    public void readFiles() {
        System.out.println("\nReading files: ");
        int fileCount = 0;

        for (File file : this.fileList) {
            if (file != null) {
                if (file.isDirectory()) {
                    // Recursive traversing for sub directories
                    System.out.println("\nEntering directory: " + file.getName());

                    FileUtility subDirectoryUtility = new FileUtility(file.listFiles());
                    subDirectoryUtility.readFiles();
                    content.append(subDirectoryUtility.content);

                } else {
                    // Reading the files with a Scanner object and Building the output file content
                    try (Scanner fileScanner = new Scanner(file)) {
                        System.out.printf("   %s. Added content from: '%s'\n",
                                String.format("%02d", ++fileCount),
                                file.getName());

                        // Setting task no for each file
                        int taskNumber = this.fileCount.incrementAndGet();
                        String taskNo = "// TASK " + taskNumber + "\n\n";

                        // Building the string that contains the whole content
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

    // Writes the content read by readFiles() method and generates a .txt file
    public void writeFiles() {
        String outputPath = filePath + File.separator + this.fileName + ".txt";
        File outputFile = new File(outputPath);

        // Checks for existing file with same name to avoid unwanted file overwriting
        if (outputFile.exists()) {
            String prompt1 = "Error: A file with the name '" + this.fileName
                    + ".txt' already exists in the directory.\n";
            String prompt2 = "Choose and Option:";

            int choice = Integer
                    .parseInt(
                            InputHandler.getUserChoice(prompt1, prompt2, 0, null, "Overwrite", "Create New Version",
                                    "Skip"));

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

        // Creates a new .txt file
        try {
            if (outputFile.createNewFile()) {
                System.out.println("\n\nCreated new output file in input directory.");
            }

        } catch (IOException err) {
            /*
             * In case of any interrupted operations, the file is written in the directory
             * from where the program is executed
             */
            System.out.println(
                    "\nError: Failed to create file in the input directory. Writing to the current directory...");

            outputPath = System.getProperty("user.dir") + File.separator + "output.txt";
            outputFile = new File(outputPath);
        }

        // Writing the content in the created .txt file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            writer.write(content.toString());
            writer.newLine();
            System.out.println("File written successfully to: " + outputFile.getAbsolutePath());
            System.out.println("\nThank you for exploring this tool.");
        } catch (IOException err) {
            Utility.printError();
        }
    }
}
