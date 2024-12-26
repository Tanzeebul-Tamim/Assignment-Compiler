package utilities;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SequenceUtils extends BaseUtils {
    // Detects sequence among file names
    public static String[][] sequenceFiles(List<String> fileNames) throws InterruptedException {
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
            Thread.sleep(1000);

            // Prints the non sequenced files
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

            ConsoleUtils.printAndReset("Falling back to manual sequencing...", 1500);
            remainingFileNames = sequenceManually(remainingFileNames);

        } else if (!sequenceExists) { // Case 2: Some files maintain sequence and some don't
            System.out.println("\nNot all files follow the sequence.");
            Thread.sleep(1000);

            // Prints the detected sequenced files
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
            Thread.sleep(1000);

            // Prints the non sequenced files
            if (remainingFileNames.length > 0) {
                System.out.println("\nRemaining non-sequenced files:");

                for (String fileName : remainingFileNames) {
                    if (fileName != null) {
                        System.out.printf("   %s. %s\n",
                                String.format("%02d", ++fileCount),
                                fileName);
                    }
                }
            }

            Thread.sleep(1000);
            ConsoleUtils.printAndReset("Falling back to manual sequencing...", 1500);
            
            remainingFileNames = null;
            sequencedFileNames = sequenceManually(allFileNames);

        } else { // Case 3: All files maintain sequence
            // Prints the detected sequenced files
            if (sequencedFileNames.length > 0) {
                System.out.println("\nThe following sequence has been detected:");
                Thread.sleep(1000);

                for (String fileName : sequencedFileNames) {
                    if (fileName != null) {
                        System.out.printf("   %s. %s\n",
                                String.format("%02d", ++fileCount),
                                fileName);
                    }
                }

                ConsoleUtils.printAndReset("Proceeding with automatic file arrangement...", 1500);
            }
        }

        /*
         * Builds the final array containing both arrays of sequenced and non
         * sequenced file names
         */
        filteredFileName[0] = sequencedFileNames;
        filteredFileName[1] = remainingFileNames;

        return filteredFileName;
    }

    // Handles the manual reordering of the unordered file names
    private static String[] sequenceManually(String[] fileNames) {
        ConsoleUtils.clearPreviousLines(1);
        String[] sequencedFileNames = new String[fileNames.length]; // Stores the final sequenced array of filenames
        String[] keywords = { "Skip", "Previous", "Restart", "Merge" }; // Specified keywords for specified actions
        String[] input_history = new String[fileNames.length]; // Stores user input history

        int count = 0;

        traverseFileNames: for (int i = 0; i < fileNames.length; i++) {
            String fileName = fileNames[i];

            if (fileName != null) {
                if (i % 5 == 0) {
                    ConsoleUtils.clearConsole();
                    System.out.printf("Please manually review the %d files that were found in the provided path:\n\n",
                            fileNames.length);
                    ConsoleUtils.printNotes();
                }

                if (count == fileNames.length - 1) {
                    // Automatically places the last file in the last remaining slot
                    for (int j = 0; j < sequencedFileNames.length; j++) {
                        if (sequencedFileNames[j] == null) {
                            sequencedFileNames[j] = fileName; // Assign the last file to the empty slot
                            break traverseFileNames;
                        }
                    }
                }

                String number = String.format("%02d", i + 1);
                String prompt = number + ". " + "Please assign a sequence number to the file '" + fileName + "':";

                // Gets user input
                String choice = InputUtils.getUserChoice(prompt, null, fileNames.length, keywords);

                if (!choice.equals("Previous") &&
                        !choice.equals("Restart") &&
                        !choice.equals("Merge")) {
                    input_history[i] = choice;
                }

                int choiceInt = 0;

                try { // Tries to convert the user input into an integer
                    choiceInt = Integer.parseInt(choice);

                    // Then sets the file in the specified index to maintain a sequence
                    sequencedFileNames[choiceInt - 1] = fileName;
                    count++;

                } catch (NumberFormatException err) { // Executes, if fails to convert input into an integer
                    // Looks for specific keywords to perform specific actions
                    choice = choice.toLowerCase();

                    // Logics for different specified actions
                    switch (choice) {
                        case "skip" -> { // Skips the current file
                            count++;
                            continue;
                        }
                        case "previous" -> { // Goes back to the previous file
                            if (i > 0) {
                                // Prints the previous files again if the file sequenced has been cleared
                                if (i % 5 == 0) {
                                    int startIdx = i - 5;

                                    ConsoleUtils.clearConsole();
                                    System.out.printf(
                                            "Please manually review the %d files that were found in the provided path:\n\n",
                                            fileNames.length);
                                    ConsoleUtils.printNotes();

                                    for (int j = startIdx; j < i; j++) {
                                        String fileName_history = fileNames[j];
                                        String number_history = String.format("%02d", j + 1);
                                        String prompt_history = number_history + ". "
                                                + "Please assign a sequence number to the file '"
                                                + fileName_history + "':";
                                        String input = input_history[j];

                                        System.out.println(prompt_history);
                                        System.out.print("Enter a number within the valid range ");
                                        InputUtils.choiceCountLoop(fileNames.length);
                                        System.out.println(input);
                                        System.out.println("\n");
                                    }
                                } else {
                                    ConsoleUtils.clearPreviousLines(10);
                                    i -= 2;
                                }
                            } else { // Prevents going back to the previous file when already on the first file
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
}
