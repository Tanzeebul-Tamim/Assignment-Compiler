package utilities;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class SequenceUtils extends BaseUtils {
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

            System.out.println("No sequence detected!");
            Thread.sleep(interval);

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

            DisplayUtils.printAndReset("Falling back to manual sequencing...", true);
            remainingFileNames = sequenceManually(remainingFileNames);

        } else if (!sequenceExists) { // Case 2: Some files maintain sequence and some don't
            System.out.println("Not all files follow the sequence.");
            Thread.sleep(interval);

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
            Thread.sleep(interval);

            // Prints the non sequenced files
            if (remainingFileNames.length > 0) {
                System.out.println("\nRemaining unsequenced files:");

                for (String fileName : remainingFileNames) {
                    if (fileName != null) {
                        System.out.printf("   %s. %s\n",
                                String.format("%02d", ++fileCount),
                                fileName);
                    }
                }
            }

            ConsoleUtils.pressEnter();
            ConsoleUtils.clearConsole();
            Thread.sleep(interval);

            String prompt1 = "Some files already have sequences.";
            String prompt2 = "Would you like to:";
            String[] choices = { "Keep them unchanged (process only unsequenced files)?", "Re-sequence all files?" };
            String[] results = { "Proceeding with the unsequenced files only.", "Re-sequencing all files." };

            int choice = Integer.parseInt(
                    InputUtils.getUserChoice(
                            prompt1,
                            prompt2,
                            0,
                            false,
                            true,
                            0,
                            null,
                            choices));

            Thread.sleep(interval);
            DisplayUtils.printAndReset(results[choice - 1] + "..", false);

            if (choice == 1) {

            } else {
                remainingFileNames = null;
                sequencedFileNames = sequenceManually(allFileNames);
            }

        } else { // Case 3: All files maintain sequence
            // Prints the detected sequenced files
            if (sequencedFileNames.length > 0) {
                System.out.println("\nThe following sequence has been detected:");
                Thread.sleep(interval);

                for (String fileName : sequencedFileNames) {
                    if (fileName != null) {
                        System.out.printf("   %s. %s\n",
                                String.format("%02d", ++fileCount),
                                fileName);
                    }
                }

                DisplayUtils.printAndReset("Proceeding with automatic file arrangement...", true);
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
    private static String[] sequenceManually(String[] fileNames) throws InterruptedException {
        String[] sequencedFileNames = new String[fileNames.length]; // Stores the final sequenced array of filenames
        String[] keywords = { "Skip", "Previous", "Restart", "Merge" }; // Specified keywords for specified actions

        String[] inputHistory = new String[fileNames.length]; // Stores user input history
        String prompt = "Please manually review the following files and assign sequence numbers to organize them:";
        boolean firstTime = true;

        traverseFileNames: for (int i = 0; i < fileNames.length; i++) {
            ConsoleUtils.clearConsole();

            if (firstTime)
                Thread.sleep(interval);

            System.out.println(prompt);
            DisplayUtils.printFileNames(fileNames, inputHistory);
            System.out.println("....\n");

            String fileName = fileNames[i];

            if (fileName != null) {
                if (firstTime)
                    Thread.sleep(interval);

                firstTime = false;
                DisplayUtils.printOptions(fileNames.length); // Displays the available actions & options

                String fileNumber = String.format("%02d", i + 1);
                String totalFiles = String.format("%02d", fileNames.length);
                String current = "> Current File: \"" + fileName + "\" (File no:" + fileNumber + " of " + totalFiles
                        + ")";
                System.out.println(current + "\n"); // Displays the current iterative file name and number

                // Gets user input
                String choice = InputUtils.getUserChoice(
                        null,
                        null,
                        fileNames.length,
                        true,
                        false,
                        4,
                        keywords);

                try { // Tries to convert the user input into an integer
                    int choiceInt = Integer.parseInt(choice);

                    // Checks for duplicate entries
                    for (String input : inputHistory) {
                        if (input != null) {
                            try {
                                int inputInt = Integer.parseInt(input);

                                // Prints error & skips the current iteration if duplicate entry found
                                if (inputInt == choiceInt) {
                                    String error = "Duplicate Serial Number! Please enter an unique serial number.";
                                    DisplayUtils.printError(error);
                                    i--;
                                    continue traverseFileNames;
                                }
                            } catch (NumberFormatException err) {
                                // Skips the validation process if the entry is a specified keyword
                                continue;
                            }
                        }
                    }

                    // Then sets the file in the specified index to maintain a sequence
                    sequencedFileNames[choiceInt - 1] = fileName;
                    inputHistory[i] = choice; // Also preserves the user input history

                } catch (NumberFormatException err) { // Executes, if fails to convert input into an integer
                    // Looks for specific keywords to perform specific actions
                    choice = choice.toLowerCase();

                    // Logics for different specified actions
                    switch (choice) {
                        case "skip" -> { // Skips the current file
                            inputHistory[i] = choice; // Preserves the user input history
                            continue;
                        }
                        case "previous" -> { // Goes back to the previous file
                            if (i > 0) {
                                i -= 2;
                                inputHistory[i + 1] = null;
                                ConsoleUtils.clearPreviousLines(10);
                            } else { // Prevents going back to the previous file when already on the first file
                                String error = "Already at the first file!";
                                DisplayUtils.printError(error);
                                i--;
                            }
                        }
                        case "restart" -> { // Resets the whole reordering process
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
