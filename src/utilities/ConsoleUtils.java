package utilities;

public final class ConsoleUtils extends BaseUtils {
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