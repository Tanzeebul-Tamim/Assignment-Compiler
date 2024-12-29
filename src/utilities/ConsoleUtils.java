package utilities;

public final class ConsoleUtils extends BaseUtils {
    // Utility method to print ANSI escape sequences
    private static void executeAnsiCommand(String command, int times) {
        for (int i = 0; i < times; i++) {
            System.out.print(command);
        }

        System.out.flush();
    }

    // Method to clear the console
    public static void clearConsole() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                executeAnsiCommand("\033[H\033[2J", 1); // Clear screen and move cursor to home
            }
        } catch (Exception err) {
            terminate(false);
        }
    }

    // Method to remove current line/previous lines and reset cursor
    public static void clearPreviousLines(int lines) {
        // int lines: 0 -> clear current line
        // int lines: x -> clear previous `x` lines

        for (int i = 0; i < lines; i++) {
            executeAnsiCommand("\033[2K", 1); // Clear current line

            if (i < lines - 1)
                executeAnsiCommand("\033[A", 1); // Move cursor up
        }

    }

    // Method to move cursor position
    public static void moveCursor(int direction, int lineCount) {
        // int direction: 1 -> Move up, 0 -> Move down
        if (direction != 1 && direction != 0) {
            throw new IllegalArgumentException("Direction must be 1 (up) or 0 (down)!");
        }

        // Determine the escape sequence based on direction
        String escapeSequence = (direction == 1) ? "\033[A" : "\033[B";

        // Use utility method to move the cursor
        executeAnsiCommand(escapeSequence, lineCount);
    }

    // Method to halt the program until user enters something
    public static void pressEnter() {
        System.out.println("\nPress Enter to continue....");
        InputUtils.sc.nextLine();
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
        // clearConsole();

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

    // Todo: Remove this method
    // Method to gracefully handle unexpected errors & termination of the program
    public static void terminate(boolean intentional, Exception err) throws Exception {
        throw err;
    }
}