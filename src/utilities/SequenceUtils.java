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

    // Todo: Merge
    // // Handles file merging operation
    // private static String[] merge(
    //         String[] fileNames,
    //         String fileName,
    //         String fileNumber,
    //         String totalFiles,
    //         String[] inputHistory)
    //         throws InterruptedException {
    //     // Specified keywords for specified actions
    //     String[] keywords = { "Revert", "Reset", "Abort", "Finish" };
    //     String[] mergedFileNames = new String[fileNames.length];
    //     String[] mergeHistory = new String[fileNames.length]; // Stores user merge input history

    //     boolean firstTime = true;
    //     String prompt = "Please review the following files and assign sequence numbers to select the files you wish to merge into the current file:";
    //     String currentFile = "> File in Focus: \"" + fileName + "\" (File no:" + fileNumber + " of "
    //             + totalFiles + ")";

    //     System.out.println(currentFile + "\n"); // Displays the current iterative file name and number

    //     // Stores the separated files that hasn't been assigned a sequence number yet
    //     String[] remainingFiles = new String[fileNames.length];
    //     int remainingIndex = 0;

    //     // Separating remaining files that hasn't been assigned a sequence number yet
    //     // So that no files that has already been processed doesn't accidentally gets
    //     // merged
    //     for (String file_name : fileNames) {
    //         if (file_name != null) {
    //             boolean found = false;

    //             for (String assignedFileName : inputHistory) {
    //                 if (file_name.equals(assignedFileName)) {
    //                     found = true;
    //                     break;
    //                 }
    //             }

    //             if (!found) {
    //                 // Creating a new array with the remaining files
    //                 remainingFiles[remainingIndex++] = file_name;
    //             }
    //         }
    //     }

    //     traverseMerge: for (int i = 0; i < remainingFiles.length; i++) {
    //         ConsoleUtils.clearConsole();

    //         if (firstTime)
    //             Thread.sleep(interval); // Simulates intervals among output sections

    //         // Prints user prompt
    //         System.out.println(prompt);

    //         // Prints available file names
    //         DisplayUtils.printFileNames(remainingFiles, mergeHistory, true);
    //         System.out.println("....\n");

    //         String file_name = remainingFiles[i];

    //         if (file_name != null) {
    //             if (firstTime)
    //                 Thread.sleep(interval); // Simulates intervals among output sections

    //             firstTime = false;
    //             DisplayUtils.printOptions(remainingIndex, true); // Displays the available actions & options

    //             // Constructing a descriptive pointer for the current file being processed
    //             String file_number = String.format("%02d", i + 1);
    //             String total_files = String.format("%02d", remainingFiles.length);
    //             String current_file = "> Current File: \"" + file_name + "\" (File no:"
    //                     + file_number
    //                     + " of " + total_files
    //                     + ")";

    //             // Displays the current iterative file name and number
    //             System.out.println(current_file + "\n");

    //             // Gets user input
    //             String choice = InputUtils.getUserChoice(
    //                     null,
    //                     null,
    //                     remainingFiles.length,
    //                     true,
    //                     false,
    //                     4,
    //                     keywords);

    //             try {// Tries to convert the user input into an integer
    //                 int choiceInt = Integer.parseInt(choice);

    //                 // Checks for duplicate entries
    //                 for (String input : mergeHistory) {
    //                     if (input != null) {
    //                         try {
    //                             int inputInt = Integer.parseInt(input);

    //                             // Prints error & skips the current iteration if duplicate entry
    //                             // found
    //                             if (inputInt == choiceInt) {
    //                                 String error = "Duplicate Serial Number! Please enter an unique serial number.";
    //                                 DisplayUtils.printError(error);
    //                                 i--;
    //                                 continue traverseMerge;
    //                             }

    //                         } catch (NumberFormatException e) {
    //                             // Skips the iteration if the entry is a specified keyword
    //                             continue;
    //                         }
    //                     }
    //                 }

    //                 // Then sets the file in the specified index to maintain a sequence
    //                 mergedFileNames[choiceInt - 1] = file_name;
    //                 mergeHistory[i] = choice; // Also preserves the user input history

    //             } catch (NumberFormatException e) { // Executes, if fails to convert input into an
    //                                                 // integer
    //                 // Looks for specific keywords to perform specific actions
    //                 choice = choice.toLowerCase();

    //                 // Logics for different specified actions
    //                 switch (choice) {
    //                     case "revert" -> { // Goes back to the previous file
    //                         if (i > 0) {
    //                             i -= 2; // Reversing index
    //                             mergeHistory[i + 1] = null; // Resetting input history at the specific slot
    //                             ConsoleUtils.clearPreviousLines(10);
    //                         } else { // Prevents going back to the previous file when already on the
    //                             // first file
    //                             String error = "Already at the first file!";
    //                             DisplayUtils.printError(error);
    //                             i--; // Reversing index
    //                         }
    //                     }

    //                     case "reset" -> { // Resets the whole merging operation
    //                         return merge(
    //                                 fileNames,
    //                                 fileName,
    //                                 fileNumber,
    //                                 totalFiles,
    //                                 inputHistory);
    //                     }

    //                     case "abort" -> { // Aborts the whole merging operation
    //                         return new String[] { choice };
    //                     }

    //                     case "finish" -> { // Exits the merging operation
    //                         break;
    //                     }

    //                     default -> DisplayUtils.printError("Invalid Input!");
    //                 }
    //             }
    //         }
    //     }

    //     // Concatenates the merged filenames as a string separated by pipe(|)
    //     for (String mergedFile : mergedFileNames) {
    //         if (mergedFile != null)
    //             fileName += "|" + mergedFile;
    //     }

    //     // Returns the concatenated file name & the number of files merged
    //     return new String[] { fileName, String.valueOf(mergedFileNames.length) };
    // }

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
        // Todo: Merge - String[] keywords = { "Skip", "Previous", "Reset", "Merge" }; // Specified keywords for specified actions
        String[] keywords = { "Skip", "Previous", "Reset" }; // Specified keywords for specified actions

        String[] inputHistory = new String[fileNames.length]; // Stores user input history
        String prompt = "Please manually review the following files and assign sequence numbers to organize them:";
        boolean firstTime = true;

        traverseFileNames: for (int i = 0; i < length; i++) {
            ConsoleUtils.clearConsole();

            if (firstTime && clearConsole)
                Thread.sleep(interval); // Simulates intervals among output sections

            // Prints user prompt
            System.out.println(prompt);
            DisplayUtils.printFileNames(fileNames, inputHistory, false); // Prints available file names
            System.out.println("....\n");

            String fileName = fileNames[i];

            if (fileName != null) {
                if (firstTime && clearConsole)
                    Thread.sleep(interval); // Simulates intervals among output sections

                firstTime = false;
                DisplayUtils.printOptions(length, false); // Displays the available actions & options

                // Constructing a descriptive pointer for the current file being processed
                String fileNumber = String.format("%02d", i + 1);
                String totalFiles = String.format("%02d", length);
                String currentFile = "> Current File: \"" + fileName + "\" (File no:" + fileNumber + " of " + totalFiles
                        + ")";
                System.out.println(currentFile + "\n"); // Displays the current iterative file name and number

                // Gets sequence numbers
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
                                i -= 2; // Reversing index

                                // Todo: Merge - 
                                // if (inputHistory[i + 1].contains("|")) {
                                //     System.out.println("Previous Item was merged");
                                // }

                                inputHistory[i + 1] = null; // Resetting input history at the specific slot
                                ConsoleUtils.clearPreviousLines(10);
                            } else { // Prevents going back to the previous file when already on the first file
                                String error = "Already at the first file!";
                                DisplayUtils.printError(error);
                                i--; // Reversing index
                            }
                        }

                        case "reset" -> { // Resets the whole reordering process
                            return sequenceManually(fileNames, false);
                        }

                        // Todo: Merge - 
                        // case "merge" -> { // Selects multiple files to merge into one file
                        //     String[] merge = merge(
                        //             fileNames,
                        //             fileName,
                        //             fileNumber,
                        //             totalFiles,
                        //             inputHistory);

                        //     if (merge[0].equalsIgnoreCase("abort")) {
                        //         i--; // Reversing index in case of merging abortion
                        //     } else {
                        //         boolean duplicate = false;

                        //         do {
                        //             // Gets file numbers for merging
                        //             String mergeChoice = InputUtils.getUserChoice(
                        //                     null,
                        //                     null,
                        //                     length,
                        //                     true,
                        //                     false,
                        //                     4,
                        //                     null);

                        //             int mergeChoiceInt = Integer.parseInt(mergeChoice);

                        //             // Checks for duplicate entries
                        //             for (String input : inputHistory) {
                        //                 duplicate = false;

                        //                 if (input != null) {
                        //                     try {
                        //                         int inputInt = Integer.parseInt(input);

                        //                         // Prints error & skips the current iteration if duplicate entry found
                        //                         if (inputInt == mergeChoiceInt) {
                        //                             String error = "Duplicate Serial Number! Please enter an unique serial number.";
                        //                             DisplayUtils.printError(error);
                        //                             duplicate = true;
                        //                             break;
                        //                         }

                        //                     } catch (NumberFormatException e) {
                        //                         // Skips the iteration if the entry is a specified keyword
                        //                         continue;
                        //                     }
                        //                 }
                        //             }
                        //         } while (duplicate);

                        //         String mergePrompt = "Assign a sequence number to the merged file:";

                        //         // Get sequence number for merging
                        //         String mergeChoice = InputUtils.getUserChoice(
                        //                 mergePrompt,
                        //                 null,
                        //                 length,
                        //                 true,
                        //                 false,
                        //                 4,
                        //                 null);

                        //         int mergeChoiceInt = Integer.parseInt(mergeChoice);

                        //         sequencedFileNames[mergeChoiceInt - 1] = inputHistory[i] = merge[0];
                        //         i += Integer.parseInt(merge[1]);
                        //     }
                        // }

                        default -> DisplayUtils.printError("Invalid Input!");
                    }
                }
            }
        }

        ConsoleUtils.clearConsole();
        System.out.println(prompt);
        DisplayUtils.printFileNames(fileNames, inputHistory, false);
        System.out.println("....\n");

        DisplayUtils.printOptions(length, false); // Displays the available actions & options
        System.out.print('\n');

        // Ask the user if they want to proceed with the sequence
        prompt = "Are you sure you want to proceed with the current sequencing?";
        String[] choices = { "Yes, proceed.", "No, restart the sequencing process." };
        String[] results = { "Proceeding with the current sequencing.", "Restarting manual sequencing." };
        int choice = InputUtils.getPreference(prompt, choices, results);

        if (choice == 1) {
            return sequencedFileNames; // Proceeds
        } else {
            return sequenceManually(fileNames, clearConsole, bounds); // Restarts
        }
    }
}
