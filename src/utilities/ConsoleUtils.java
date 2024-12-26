package utilities;

import java.util.Random;

public final class ConsoleUtils extends BaseUtils {
    // Method to print title
    public static void printTitle() throws InterruptedException {
        String title1 = "Welcome to the '";
        String title2 = "Assignment File Generator";
        String title3 = "' Automate, Organize, & Submit with Ease!";

        clearConsole();
        typewriter(title1, title2, title3, 6);

        Thread.sleep(1000);
        System.out.println("\n");

        System.out.println("YYou can press Ctrl + C anytime to terminate the program.\n");
        Thread.sleep(3000);
        clearConsole();

        System.out.println(
                "The output file will be saved using the following naming format: `Assignment XX_Your-ID_Your-Name`");
        System.out.println("(e.g., Assignment 03_24100000_Joe Brooks.txt)\n");
    }

    // Prints user prompts to guide the user
    public static void printNotes() {
        System.out.println("Note:");
        System.out.println(" - Type 'Skip' to exclude this file.");
        System.out.println(" - Type 'Previous' to go back to the previous file.");
        System.out.println(" - Type 'Restart' to restart the sequencing process.");
        System.out.println(" - Type 'Merge' to combine this file with the previous file.");
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
    public static void printAndReset(String message, long waitTime) throws InterruptedException {
        ConsoleUtils.pressEnter();
        ConsoleUtils.clearConsole();
        System.out.println(message);
        Thread.sleep(waitTime); // Wait for 'waitTime' seconds
        ConsoleUtils.clearConsole();
    }

    // Simulates Typewriter effect
    private static void typewriter(
            String introText,
            String mainContent,
            String outroText,
            int milliseconds)
            throws InterruptedException {

        String letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

        // Prepare the full title (intro + main + outro)
        String fullTitle = introText + " " + mainContent + " " + outroText;

        // Build the underline according to the string length
        String underline1 = "_".repeat(fullTitle.length());
        String underline2 = "-".repeat(fullTitle.length());

        Thread.sleep(550);

        // Print First underline
        System.out.println(underline1);
        System.out.println(underline2);

        // Print Empty line for spacing
        System.out.println("");

        // Print Second underline
        System.out.println(underline1);
        System.out.println(underline2);

        // Move cursor up
        System.out.print("\033[A");
        System.out.print("\033[A");
        System.out.print("\033[A");

        Thread.sleep(550);

        // Typing simulation for the title (intro + main + outro)
        StringBuilder titleBuilder = new StringBuilder();
        // Type intro text
        if (!introText.isEmpty()) {
            for (String word : introText.split(" ")) {
                System.out.print(word + " ");
                Thread.sleep(500); // 1-second interval between words
            }
        }

        // Type main content with random characters (typewriter effect)
        for (char letter : mainContent.toCharArray()) {
            while (true) {
                char randomChar = letters.charAt(new Random().nextInt(letters.length()));
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

                Thread.sleep(milliseconds); // Adjust typing speed
            }
        }

        // Type outro text
        if (!outroText.isEmpty()) {
            for (String word : outroText.split(" ")) {
                System.out.print(word + " ");
                Thread.sleep(500); // 1-second interval between words
            }
        }

        // Move cursor down
        System.out.print("\033");
        System.out.print("\033");
        System.out.print("\033 ");
    }

    // Method to halt the program until user enters something
    public static void pressEnter() {
        System.out.println("\nPress Enter to continue....");
        InputUtils.sc.nextLine();
    }

    // Method to clear out the console
    public static void clearConsole() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception err) {
            terminate(false);
        }
    }

    // Method to remove previous lines and reset cursor
    public static void clearPreviousLines(int lines) {
        for (int i = 0; i < lines; i++) {
            System.out.print("\033[A"); // Move cursor up
            System.out.print("\033[2K"); // Clear line
        }
        System.out.flush();
    }

    // Trims unnecessary white spaces and applies proper capitalization to username
    public static String formatName(String name) {
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

    // Method to gracefully handle unexpected errors & termination of the program
    public static void terminate(boolean intentional) {
        if (intentional) {
            // For Intentional termination of the program
            System.out.println("\n\nProgram terminated! Thank you for exploring this tool.");
        } else {
            // For other errors
            System.out.println("\n\nProgram terminated due to an unexpected error!");
        }

        // Closer scanner if it's still open
        if (InputUtils.sc != null)
            InputUtils.sc.close();

        System.exit(0); // Exit program
    }
}