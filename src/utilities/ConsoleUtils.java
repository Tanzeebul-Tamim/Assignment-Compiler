package utilities;

public final class ConsoleUtils extends BaseUtils {
    // Method to print title
    public static void printTitle() {
        clearConsole();
        System.out.println("Welcome to the Assignment File Generator Tool!");
        System.out.println("You can press Ctrl + C anytime to terminate the program.\n");
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