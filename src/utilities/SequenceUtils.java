package utilities;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class SequenceUtils extends BaseUtils {
    // Detects sequence among file names
    public static String[][] sequenceFiles(List<String> fileNames) throws InterruptedException {
        // Storing ordered and unordered files in separate arrays
        String[] sequencedFileNames = new String[fileNames.size()];
        String[] nonSequencedFileNames = new String[fileNames.size()];

        // Arrays to store all file names
        String[] combinedFileNames = fileNames.toArray(new String[0]);
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
                nonSequencedFileNames[j++] = fileName;
            }
        }

        if (!atLeastOneNumeric) { // Case 1: No files maintain sequence
            sequencedFileNames = null;

            System.out.println("No sequence detected!");
            Thread.sleep(interval);

            // Prints the non sequenced files
            if (nonSequencedFileNames.length > 0) {
                System.out.println("\nFiles found:");

                for (String fileName : nonSequencedFileNames) {
                    if (fileName != null) {
                        System.out.printf("   %s. %s\n",
                                String.format("%02d", ++fileCount),
                                fileName);
                    }
                }
            }

            DisplayUtils.printAndReset("Falling back to manual sequencing...", true);
            nonSequencedFileNames = sequenceManually(nonSequencedFileNames, true);

        } else if (!sequenceExists) { // Case 2: Some files maintain sequence and some don't
            int idx = 0;

            System.out.println("Not all files follow the sequence.");
            Thread.sleep(interval);

            // Prints the detected sequenced files
            if (sequencedFileNames.length > 0) {
                System.out.println("\nSequenced files detected:");

                for (String fileName : sequencedFileNames) {
                    if (fileName != null) {
                        combinedFileNames[idx++] = fileName;

                        System.out.printf("   %s. %s\n",
                                String.format("%02d", ++fileCount),
                                fileName);
                    }
                }
            }

            fileCount = 0;
            Thread.sleep(interval);

            // Prints the non sequenced files
            if (nonSequencedFileNames.length > 0) {
                System.out.println("\nRemaining unsequenced files:");

                for (String fileName : nonSequencedFileNames) {
                    if (fileName != null) {
                        combinedFileNames[idx++] = fileName;

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
                // Setting custom boundaries for manual sequencing
                // Because the sequenced files will be listed first
                int upperBound = fileNames.size() - fileCount;
                int[] bounds = { upperBound, fileNames.size() };

                nonSequencedFileNames = sequenceManually(nonSequencedFileNames, true, bounds);
            } else {
                sequencedFileNames = sequenceManually(combinedFileNames, true);
                nonSequencedFileNames = null;
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
        filteredFileName[1] = nonSequencedFileNames;

        return filteredFileName;
    }

    // Handles the manual reordering of the unordered file names
    private static String[] sequenceManually(String[] fileNames, boolean clearConsole) throws InterruptedException {
        return sequenceManually(fileNames, clearConsole, null);
    }

    // V2 - for custom numbering of the filenames
    private static String[] sequenceManually(
            String[] fileNames,
            boolean clearConsole,
            int[] bounds)
            throws InterruptedException {

        // Calculating how many files will undergo the process
        int length = bounds == null ? fileNames.length : bounds[1] - bounds[0];

        String[] sequencedFileNames = new String[length]; // Stores the final sequenced array of filenames
        String[] keywords = { "Skip", "Previous", "Reset", "Merge" }; // Specified keywords for specified actions

        String[] inputHistory = new String[fileNames.length]; // Stores user input history
        String prompt = "Please manually review the following files and assign sequence numbers to organize them:";
        boolean firstTime = true;

        traverseFileNames: for (int i = 0; i < length; i++) {
            ConsoleUtils.clearConsole();

            if (firstTime && clearConsole)
                Thread.sleep(interval);

            System.out.println(prompt);
            DisplayUtils.printFileNames(fileNames, inputHistory);
            System.out.println("....\n");

            String fileName = fileNames[i];

            if (fileName != null) {
                if (firstTime && clearConsole)
                    Thread.sleep(interval);

                firstTime = false;
                DisplayUtils.printOptions(length); // Displays the available actions & options

                String fileNumber = String.format("%02d", i + 1);
                String totalFiles = String.format("%02d", length);
                String current = "> Current File: \"" + fileName + "\" (File no:" + fileNumber + " of " + totalFiles
                        + ")";
                System.out.println(current + "\n"); // Displays the current iterative file name and number

                // Gets user input
                String choice = InputUtils.getUserChoice(
                        null,
                        null,
                        length,
                        true,
                        false,
                        4,
                        keywords,
                        bounds);

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
                                // Skips the iteration if the entry is a specified keyword
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
                        case "reset" -> { // Resets the whole reordering process
                            return sequenceManually(fileNames, false);
                        }
                        case "merge" -> { // Merges multiple files together

                        }
                        default -> System.out.println("Error: Invalid Input!");
                    }
                }
            }
        }

        ConsoleUtils.clearConsole();
        System.out.println(prompt);
        DisplayUtils.printFileNames(fileNames, inputHistory);
        System.out.println("....\n");

        DisplayUtils.printOptions(length); // Displays the available actions & options
        System.out.print('\n');

        // Ask the user if they want to proceed with the sequence
        prompt = "Are you sure you want to proceed with the current sequencing?";
        String[] choices = { "Yes, proceed.", "No, restart the sequencing process." };
        String[] results = { "Proceeding with the current sequencing.", "Restarting manual sequencing." };
        int choice = InputUtils.getPreference(prompt, choices, results);

        if (choice == 1) {
            return sequencedFileNames;
        } else {
            return sequenceManually(fileNames, clearConsole, bounds);
        }

    }
}
