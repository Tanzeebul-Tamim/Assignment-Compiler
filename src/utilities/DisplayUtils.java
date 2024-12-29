package utilities;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public final class DisplayUtils extends BaseUtils {
    private static int charInterval;
    private static int wordInterval;

    static {
        charInterval = 17;
        wordInterval = 380;
    }

    // Print title
    public static void printTitle() throws InterruptedException {
        String title1 = "Welcome to the '";
        String title2 = "Assignment File Generator";
        String title3 = "' Automate, Organize, & Submit with Ease!";

        ConsoleUtils.clearConsole();
        typewriter(title1, title2, title3, true, false);

        Thread.sleep(interval);
        System.out.print("\n");

        System.out.println("You can press Ctrl + C anytime to terminate the program.\n");
        Thread.sleep(errorInterval);
        ConsoleUtils.clearConsole();
    }

    // Print outro message
    public static void printOutro() throws InterruptedException {
        String title1 = "Thank you for exploring this tool.\n";
        String title2 = "Developed by: Tanzeebul Tamim".toUpperCase();

        typewriter(title1, title2, "", false, true);
        ConsoleUtils.clearPreviousLines(2);
    }

    // Print assignment file naming format
    public static void printNamingFormat(String assignmentNo, String id, String name, String extension) {
        ConsoleUtils.clearConsole();
        System.out.printf(
                "The output file will be saved using the following naming format: \"Assignment %s_%s_%s.txt\"\n",
                assignmentNo.isEmpty() ? "XX" : assignmentNo,
                id.isEmpty() ? "Your-ID" : id,
                name.isEmpty() ? "Your-Name" : name);
        System.out.println(" - (e.g., Assignment 03_24100000_Joe Brooks.txt)\n");

        System.out.printf("Assignment No: %s\n", assignmentNo);
        System.out.printf("Your ID: %s\n", id);
        System.out.printf("Your Name: %s\n", name);
        System.out.printf("File Extension: %s\n\n", extension);
    }

    // Print the filenames being processed
    public static void printFileNames(String[] fileNames, String[] inputHistory) {
        for (int i = 0; i < fileNames.length; i++) {
            String fileName = fileNames[i];
            String userInput = inputHistory[i];

            if (fileName != null) {
                if (userInput == null) {
                    System.out.printf("   %s. %s\n",
                            String.format("%02d", i + 1),
                            fileName);
                } else {
                    try {
                        int choiceInt = Integer.parseInt(userInput);

                        System.out.printf("   %s. %s -> Assigned: %s\n",
                                String.format("%02d", i + 1),
                                fileName,
                                String.format("%02d", choiceInt));
                    } catch (NumberFormatException err) {
                        System.out.printf("   %s. %s -> Skipped\n",
                                String.format("%02d", i + 1),
                                fileName);
                    }
                }
            }
        }
    }

    // Print user prompts to guide the user
    public static void printOptions(int optionCount) {
        String range;

        if (optionCount > 4) {
            range = "(1-" + optionCount + ")";

        } else {
            range = "(";

            for (int i = 1; i <= optionCount; i++) {
                if (i == optionCount) {
                    range += "or " + i + ")";
                } else {
                    range += i + ", ";
                }
            }
        }

        System.out.println("Options:");
        System.out.printf(" - Enter a sequence number %s to assign to this file.\n", range);
        System.out.println(" - Enter \"Skip\" to exclude this file.");
        System.out.println(" - Enter \"Previous\" to go back to the previous file and reassign its sequence number.");
        System.out.println(" - Enter \"Restart\" to restart the sequencing process.");
        System.out.println(" - Enter \"Merge\" to combine this file with the previous file.");
        System.out.println();
    }

    // Print guidelines for organizing files
    public static void printGuidelines() {
        System.out.println(
                "Important Note:\nPlease ensure that the selected directory contains only the assignment files.\n\nFor best results:\n"
                        +
                        " - Organize your assignment files in a dedicated folder.\n" +
                        " - Verify that all files are relevant before proceeding.\n" +
                        " - If possible, name the files using a sequence (e.g., `Task-01`, `Task-02`, or `Task1`, `Task2`, etc.).\n"
                        +
                        "   (If you're unable or not permitted to rename them, you will be prompted to manually arrange them in the correct order.)\n");
    }

    // Print probable reasons of write-errors
    public static void printError() {
        System.out.println("An error occurred while trying to write the output file.");
        System.out.println("Possible reasons:");
        System.out.println("- The program does not have permission to write in the specified directory.");
        System.out.println("- There may not be enough disk space available.");
        System.out.println("- The file path might be invalid or corrupted.");
    }

    // Print user input related error messages
    public static void printError(String message) throws InterruptedException {
        printError(message, 0, 0, 0);
    }

    // V2 - customizable line parameter, interval time & prints multiple choices
    public static void printError(String message, int interval, int choices, int clearLines)
            throws InterruptedException {
        clearLines = clearLines == 0 ? 3 : clearLines;
        interval = interval == 0 ? errorInterval : interval;

        ConsoleUtils.moveCursor(1, 1); // Move cursor up
        System.out.printf("\rError: %s", message);

        if (choices > 0)
            choiceCountLoop(choices);

        Thread.sleep(interval);
        ConsoleUtils.clearPreviousLines(clearLines);
        System.out.println("\r");
    }

    // Display a message to the user & resets the console
    public static void printAndReset(String message, boolean pressEnter) throws InterruptedException {
        if (pressEnter)
            ConsoleUtils.pressEnter();

        ConsoleUtils.clearConsole();
        System.out.println(message);
        Thread.sleep(interval); // Wait for 'waitTime' seconds
        ConsoleUtils.clearConsole();
    }

    // Print a user prompt for selecting multiple input options
    public static void choiceCountLoop(int choiceCount) {
        choiceCountLoop(choiceCount, true);
    }

    // Print a prompt for selecting multiple input options, also prints new line
    public static void choiceCountLoop(int choiceCount, boolean newLine) {
        if (choiceCount > 4) {
            System.out.printf("(1-%d): ", choiceCount);

            if (newLine)
                System.out.println();

        } else {
            for (int i = 1; i <= choiceCount; i++) {
                if (i == 1) {
                    System.out.print("(");
                }

                if (i == choiceCount) {
                    System.out.printf("or %d): ", i);

                    if (newLine)
                        System.out.println();
                } else {
                    System.out.printf("%d, ", i);
                }
            }
        }
    }

    // Simulate Typewriter effect
    private static void typewriter(
            String introText,
            String mainContent,
            String outroText,
            boolean underline,
            boolean newline)
            throws InterruptedException {

        // Prepare the full title (intro + main + outro)
        String fullTitle = introText + " " + mainContent + " " + outroText;

        // Build the underline according to the string length
        String underline1 = "_".repeat(fullTitle.length());
        String underline2 = "-".repeat(fullTitle.length());

        Thread.sleep(interval);

        if (underline) {
            // Print First underline
            System.out.println(underline1);
            System.out.println(underline2);

            // Print Empty line for spacing
            System.out.println();

            // Print Second underline
            System.out.println(underline1);
            System.out.println(underline2);

            // Move cursor up
            ConsoleUtils.moveCursor(1, 3);
            Thread.sleep(interval);
        }

        // Typing simulation for the title (intro + main + outro)
        StringBuilder titleBuilder = new StringBuilder();

        // Type intro text
        if (!introText.isEmpty()) {
            for (String word : introText.split(" ")) {
                System.out.print(word + " ");
                Thread.sleep(wordInterval); // Sets a specified interval between words
            }
        }

        // Other necessary characters that need to be checked
        List<Character> checkChars = Arrays.asList(' ', '-', ',', '\'', '!', ':');

        // Type main content with random characters (typewriter effect)
        for (char letter : mainContent.toCharArray()) {
            while (true) {
                char randomChar = mainContent.charAt(new Random().nextInt(mainContent.length()));

                if (newline) {
                    System.out.print("\r" + titleBuilder + randomChar);
                } else {
                    System.out.print("\r" + introText + titleBuilder + randomChar);
                }

                if (letter == randomChar || checkChars.contains(letter)) {
                    // Appending the letters & other necessary characters
                    titleBuilder.append(letter);
                    break;
                }

                Thread.sleep(charInterval); // Adjust typing speed
            }
        }

        // Type outro text
        if (!outroText.isEmpty()) {
            for (String word : outroText.split(" ")) {
                System.out.print(word + " ");
                Thread.sleep(wordInterval); // Sets a specified interval between words
            }
        }

        // Move cursor down
        ConsoleUtils.moveCursor(0, 3);
    }
}
