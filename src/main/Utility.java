package main;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utility {
    private InputHandler input; // InputHandler class reference

    // Method to set InputHandler class reference
    public void setInputHandler(InputHandler input) {
        this.input = input;
    }

    // Method to print title
    public void printTitle() {
        System.out.println("Welcome to the Assignment File Generator Tool!");
        System.out.println("You can press Ctrl + C anytime to terminate the program.\n");
        System.out.println(
                "The output file will be saved using the following naming format: `Assignment XX_Your-ID_Your-Name`");
        System.out.println("(e.g., Assignment 03_24100000_Joe Brooks.txt)\n");
    }

    // Method to print error
    public void printError() {
        System.out.println("An error occurred while trying to write the output file.");
        System.out.println("Possible reasons:");
        System.out.println("- The program does not have permission to write in the specified directory.");
        System.out.println("- There may not be enough disk space available.");
        System.out.println("- The file path might be invalid or corrupted.");
    }

    // Method to gracefully handle errors in case of any unexpected errors or sudden
    // termination of the program
    public void terminate(Scanner sc, boolean intentional) {
        if (intentional) {
            // For Intentional termination of the program
            System.out.println("\n\nProgram terminated! Thank you for exploring this tool.");
        } else {
            // For other errors
            System.out.println("\n\nProgram terminated due to an unexpected error!");
        }

        sc.close();
        System.exit(0);
    }

    // Prints user prompts to guide the user
    private void printUserPrompt() {
        System.out.println("\nNote:");
        System.out.println(" - Type 'Skip' to exclude this file.");
        System.out.println(" - Type 'Previous' to go back to the previous file.");
        System.out.println(" - Type 'Restart' to restart the sequencing process.");
        System.out.println(" - Type 'Merge' to combine this file with the previous file.");
        System.out.println();
    }

    // Handles the manual reordering of the unordered file names
    public String[] sequenceManually(String[] fileNames) {
        System.out.println("\nPlease review the files manually:");
        this.printUserPrompt();

        String[] sequencedFileNames = new String[fileNames.length];
        String[] keywords = { "Skip", "Previous", "Restart", "Merge" }; // Specified keywords for specified actions

        int count = 0;

        traverseFileNames: for (int i = 0; i < fileNames.length; i++) {
            String fileName = fileNames[i];

            if (fileName != null) {
                if (count == fileNames.length - 1) {
                    // Automatically place the last file in the last remaining slot
                    for (int j = 0; j < sequencedFileNames.length; j++) {
                        if (sequencedFileNames[j] == null) {
                            sequencedFileNames[j] = fileName; // Assign the last file to the empty slot
                            break traverseFileNames;
                        }
                    }
                }

                String prompt = "Please assign a sequence number to the file '" + fileName + "':";

                // Get user input
                String choice = input.getUserChoice(prompt, null, fileNames.length, keywords);
                int choiceInt = 0;

                try {
                    // Tries to convert the input into an integer, then using that integer, sets the
                    // file in the specified index to maintain a sequence
                    choiceInt = Integer.parseInt(choice);
                    sequencedFileNames[choiceInt - 1] = fileName;
                    count++;

                } catch (NumberFormatException err) {
                    choice = choice.toLowerCase();

                    // Logics for different specified actions
                    switch (choice) {
                        case "skip" -> { // Skips the current file
                            count++;
                            continue;
                        }
                        case "previous" -> { // Goes back to the previous file
                            if (i > 0) {
                                i -= 2;
                            } else { // Prevents the attempt to going back to the previous file when user's already on the first file
                                System.out.println("\nAlready at the first file.");
                                i -= 1;
                            }
                        }
                        case "restart" -> { // Resets the whole reordering process
                            count = 0;
                            return sequenceManually(fileNames);
                        }
                        case "merge" -> { // Merges multiple files together

                        }
                        default -> System.out.println("Error: Invalid Input!");
                    }
                }
            }
        }

        return sequencedFileNames;
    }

    // Method to detect sequence among file names
    public String[][] sequenceFiles(List<String> fileNames) {
        // Storing ordered and unordered files in separate arrays
        String[] sequencedFileNames = new String[fileNames.size()];
        String[] remainingFileNames = new String[fileNames.size()];

        // arrays to store all file names
        String[] allFileNames = fileNames.toArray(new String[0]);
        String[][] filteredFileName = new String[2][fileNames.size()];

        boolean sequenceExists = true;
        boolean atLeastOneNumeric = false;
        int fileCount = 0;

        // Checking and Identifying probable existing sequences
        for (int i = 0, j = 0; i < fileNames.size(); i++) {
            String fileName = fileNames.get(i);
            String pattern = "\\d+";
            boolean found = false;

            Pattern regex = Pattern.compile(pattern);
            Matcher matcher = regex.matcher(fileName);

            while (matcher.find()) {
                String numberStr = matcher.group();
                int number = Integer.parseInt(numberStr);

                if (number >= 1 && number <= fileNames.size()) {
                    sequencedFileNames[number - 1] = fileName;
                    found = true;
                    atLeastOneNumeric = true;
                    break;
                }
            }

            if (!found) {
                sequenceExists = false;
                remainingFileNames[j++] = fileName;
            }
        }

        if (!atLeastOneNumeric) { // Case 1: No files maintain sequence
            sequencedFileNames = null;

            System.out.println("\nNo sequence detected!");

            if (remainingFileNames.length > 0) {
                System.out.println("\nFiles found:");

                for (String fileName : remainingFileNames) {
                    if (fileName != null) {
                        System.out.printf("   %s. %s\n",
                                String.format("%02d", ++fileCount),
                                fileName);
                    }
                }
            }

            remainingFileNames = this.sequenceManually(remainingFileNames);

        } else if (!sequenceExists) { // Case 2: Some files maintain sequence and some doesn't
            System.out.println("\nNot all files follow the sequence.");

            if (sequencedFileNames.length > 0) {
                System.out.println("\nSequenced files detected:");

                for (String fileName : sequencedFileNames) {
                    if (fileName != null) {
                        System.out.printf("   %s. %s\n",
                                String.format("%02d", ++fileCount),
                                fileName);
                    }
                }
            }

            fileCount = 0;

            if (remainingFileNames.length > 0) {
                System.out.println("\nRemaining jumbled files:");

                for (String fileName : remainingFileNames) {
                    if (fileName != null) {
                        System.out.printf("   %s. %s\n",
                                String.format("%02d", ++fileCount),
                                fileName);
                    }
                }
            }

            remainingFileNames = null;
            sequencedFileNames = this.sequenceManually(allFileNames);

        } else { // Case 3: All files maintain sequence
            if (sequencedFileNames.length > 0) {
                System.out.println("\nThe following sequence has been detected:");

                for (String fileName : sequencedFileNames) {
                    if (fileName != null) {
                        System.out.printf("   %s. %s\n",
                                String.format("%02d", ++fileCount),
                                fileName);
                    }
                }
            }
        }

        // Building the final array containing both arrays of sequenced and non sequenced file names
        filteredFileName[0] = sequencedFileNames;
        filteredFileName[1] = remainingFileNames;

        return filteredFileName;
    }

    // Formats the user name by trimming unnecessary white spaces and applying proper capitalization
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