package utilities;

import java.util.Random;

public final class DisplayUtils extends BaseUtils {
    // Method to print title
    public static void printTitle() throws InterruptedException {
        String title1 = "Welcome to the '";
        String title2 = "Assignment File Generator";
        String title3 = "' Automate, Organize, & Submit with Ease!";

        ConsoleUtils.clearConsole();
        typewriter(title1, title2, title3, 15, 380);

        Thread.sleep(sleep);
        System.out.println("\n");

        System.out.println("YYou can press Ctrl + C anytime to terminate the program.\n");
        Thread.sleep(sleep * 3);
        ConsoleUtils.clearConsole();

        System.out.println(
                "The output file will be saved using the following naming format: `Assignment XX_Your-ID_Your-Name`");
        System.out.println("(e.g., Assignment 03_24100000_Joe Brooks.txt)\n");
    }

    // Prints the filenames being processed
    public static void printFileNames(String[] fileNames, String[] inputHistory) {
        for (int i = 0; i < fileNames.length;) {
            String fileName = fileNames[i];
            String userInput = inputHistory[i];

            if (fileName != null) {
                if (userInput == null) {
                    System.out.printf("   %s. %s\n",
                            String.format("%02d", ++i),
                            fileName);
                } else {
                    try {
                        int choiceInt = Integer.parseInt(userInput);

                        System.out.printf("   %s. %s -> Assigned: %s\n",
                                String.format("%02d", ++i),
                                fileName,
                                String.format("%02d", choiceInt));
                    } catch (NumberFormatException err) {
                        System.out.printf("   %s. %s -> Skipped\n",
                                String.format("%02d", ++i),
                                fileName);
                    }
                }
            }
        }
    }

    // Prints user prompts to guide the user
    public static void printOptions(int optionCount) {
        System.out.println("Options:");
        System.out.printf(" - Enter a sequence number (1-%d) to assign to this file.\n", optionCount);
        System.out.println(" - Enter \"Skip\" to exclude this file.");
        System.out.println(" - Enter \"Previous\" to go back to the previous file.");
        System.out.println(" - Enter \"Restart\" to restart the sequencing process.");
        System.out.println(" - Enter \"Merge\" to combine this file with the previous file.");
        System.out.println();
    }

    // Method to print error
    public static void printError() {
        System.out.println("An error occurred while trying to write the output file.");
        System.out.println("Possible reasons:");
        System.out.println("- The program does not have permission to write in the specified directory.");
        System.out.println("- There may not be enough disk space available.");
        System.out.println("- The file path might be invalid or corrupted.");
    }

    // Displays a message to the user & resets the console
    public static void printAndReset(String message, boolean pressEnter) throws InterruptedException {
        if (pressEnter)
            ConsoleUtils.pressEnter();

        ConsoleUtils.clearConsole();
        System.out.println(message);
        Thread.sleep(sleep); // Wait for 'waitTime' seconds
        ConsoleUtils.clearConsole();
    }

    // Method to display a user prompt for selecting multiple input options
    public static void choiceCountLoop(int choiceCount) {
        if (choiceCount > 5) {
            System.out.printf("(1-%d):\n", choiceCount);

        } else {
            for (int i = 1; i <= choiceCount; i++) {
                if (i == 1) {
                    System.out.print("(");
                }

                if (i == choiceCount) {
                    System.out.printf("or %d):\n", i);
                } else {
                    System.out.printf("%d, ", i);
                }
            }
        }
    }

    // Simulates Typewriter effect
    private static void typewriter(
            String introText,
            String mainContent,
            String outroText,
            int charInterval,
            int wordInterval)
            throws InterruptedException {

        // Prepare the full title (intro + main + outro)
        String fullTitle = introText + " " + mainContent + " " + outroText;

        // Build the underline according to the string length
        String underline1 = "_".repeat(fullTitle.length());
        String underline2 = "-".repeat(fullTitle.length());

        Thread.sleep(sleep);

        // Print First underline
        System.out.println(underline1);
        System.out.println(underline2);

        // Print Empty line for spacing
        System.out.println();

        // Print Second underline
        System.out.println(underline1);
        System.out.println(underline2);

        // Move cursor up
        System.out.print("\033[A");
        System.out.print("\033[A");
        System.out.print("\033[A");

        Thread.sleep(sleep);

        // Typing simulation for the title (intro + main + outro)
        StringBuilder titleBuilder = new StringBuilder();

        // Type intro text
        if (!introText.isEmpty()) {
            for (String word : introText.split(" ")) {
                System.out.print(word + " ");
                Thread.sleep(wordInterval); // Sets a specified interval between words
            }
        }

        // Type main content with random characters (typewriter effect)
        for (char letter : mainContent.toCharArray()) {
            while (true) {
                char randomChar = mainContent.charAt(new Random().nextInt(mainContent.length()));
                System.out.print("\r" + introText + titleBuilder + randomChar);

                if (letter == randomChar) {
                    // Appending the letters
                    titleBuilder.append(letter);
                    break;
                } else if (letter == ' ' || letter == '-' || letter == ',' || letter == '\'' || letter == '!') {
                    // Appending other necessary characters
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
        System.out.print("\033");
        System.out.print("\033");
        System.out.print("\033 ");
    }
}
