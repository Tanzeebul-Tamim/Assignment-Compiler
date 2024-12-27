package utilities;

import java.io.File;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public final class InputUtils extends BaseUtils {
    public static String name;
    public static int id;
    public static int assignmentNo;
    public static String fileExtension;
    public static String folderPath;

    public static File directory;
    public static File[] fileList; // Stores all the files found in the provided directory
    public static Scanner sc = new Scanner(System.in);

    // Valid file extensions
    private static String[] validExtensions = {
            "java", "txt", "py", "cpp", "c", "cs", "js", "ts", "html", "css", "xml", "json"
    };

    // Method to generate output file name using user data
    public static String getFileName() {
        String studentIdentity = String.format("%02d", assignmentNo) + "_" + id + "_" + name;
        String fileName = "Assignment " + studentIdentity;

        return fileName;
    }

    // Method to collect all user information
    public static void collectInputs() throws NoSuchElementException, InputMismatchException {
        name();
        id();
        assignmentNo();
        fileExtension();
        ConsoleUtils.clearConsole();
        directoryPath();
    }

    // Method to collect user input from multiple input options
    public static String getUserChoice(
            String prompt1,
            String prompt2,
            int fileCount,
            String[] keywords,
            String... choices)
            throws InputMismatchException,
            NoSuchElementException {

        int choice = 0;
        String input = "";

        if (prompt1 != null) {
            System.out.printf("\n%s\n", prompt1);
        }

        if (prompt2 != null) {
            System.out.printf("%s\n", prompt2);
        }

        if (choices.length > 0) {
            for (int i = 0; i < choices.length; i++) {
                System.out.printf("(%d) %s", i + 1, choices[i]);

                if (i != choices.length - 1) {
                    System.out.println();
                }
            }
        }

        while (choice == 0) {
            try {
                if (choices.length > 0) {
                    System.out.print("\nEnter your choice ");
                    DisplayUtils.choiceCountLoop(choices.length);
                } else {
                    System.out.print("Enter a number within the valid range ");
                    DisplayUtils.choiceCountLoop(fileCount);
                }

                input = sc.nextLine().trim();

                if (input.isEmpty()) {
                    System.out.println("Error: You must pick one of the options.");
                    continue;
                }

                // Checking for the specified Keywords for specified actions
                if (keywords != null) {
                    for (int i = 0; i < keywords.length; i++) {
                        String keyword = keywords[i];

                        if (input.equalsIgnoreCase(keyword)) {
                            System.out.println();
                            return keyword;
                        }
                    }
                }

                if (!input.matches("\\d+")) {
                    System.out
                            .print("\nError: Invalid Input! Please enter a numeric value.\n");
                    DisplayUtils.choiceCountLoop(choices.length);
                    continue;
                }

                choice = Integer.parseInt(input);
                int upperBound = (choices.length > 0) ? choices.length : fileCount;

                if (choice < 1 || choice > upperBound) {
                    System.out.print(
                            "\nError: Invalid Input! Please enter a numeric value corresponding to one of the options ");
                    DisplayUtils.choiceCountLoop(upperBound);
                    choice = 0;
                    continue;
                }

                System.out.println();

            } catch (InputMismatchException | IllegalArgumentException err) {
                System.out.printf("\nError: Input out of range! Enter a number between 1 to %d\n", choices.length);
            }

        }

        return input;
    }

    // Collects user name
    private static void name() {
        while (name == null) {
            System.out.println("Please enter your full name:"); // Formats name
            String input = ConsoleUtils.formatName(sc.nextLine());

            if (input.isEmpty()) {
                System.out.println("Error: Name cannot be left empty.\n");
                continue;
            }

            name = input;
        }
    }

    // Collects user id
    private static void id() {
        while (id == 0) {
            try {
                System.out.println("\nPlease enter your 8-digit ID (e.g., 24100000):");
                String input = sc.nextLine().trim();

                if (input.isEmpty()) {
                    System.out.println("Error: ID cannot be left empty.");
                    continue;
                }

                if (!input.matches("\\d+")) {
                    System.out.println("\nError: Invalid Input! ID must contain only numeric characters.");
                    continue;
                }

                if (input.length() != 8) {
                    System.out.println("\nError: Invalid Input! ID must be exactly 8 digits.");
                    continue;
                }

                id = Integer.parseInt(input);

            } catch (NumberFormatException err) {
                System.out.println("\nError: Invalid Input! ID must be a numeric value.");

            }
        }
    }

    // Collects assignment no
    private static void assignmentNo() {
        while (assignmentNo == 0) {
            try {
                System.out.println("\nPlease enter the assignment-no (1-15):");
                String input = sc.nextLine().trim();

                if (input.isEmpty()) {
                    System.out.println("Error: Assignment-No cannot be left empty.");
                    continue;
                }

                if (!input.matches("\\d+")) {
                    System.out
                            .println("\nError: Invalid Input! Assignment-No must contain only numeric characters.");
                    continue;
                }

                assignmentNo = Integer.parseInt(input);

                if (assignmentNo < 1 || assignmentNo > 15) {
                    System.out.println("\nError: Invalid Input! Assignment-No must be between 1 and 15.");
                    assignmentNo = 0;
                }

            } catch (NumberFormatException err) {
                System.out.println("\nError: Invalid Input! Assignment-No must be a numeric value.");

            }
        }
    }

    // Collects the required file extension
    private static void fileExtension() {
        while (fileExtension == null) {
            System.out.println(
                    "\nPlease enter the required file extension for your assignment (e.g., java, py, cpp):");
            String input = sc.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("Error: File extension cannot be left empty.");
                continue;
            }

            boolean isValid = false;
            for (String ext : validExtensions) {
                if (input.equals(ext)) {
                    isValid = true;
                    break;
                }
            }

            if (isValid) {
                fileExtension = "." + input;

            } else {
                System.out.println("\nError: Invalid Input! Allowed extensions are:");
                System.out.println("       " + String.join(", ", validExtensions));
            }
        }
    }

    // Collects the directory path where the assignment files are located
    private static void directoryPath() {
        System.out.println(
                "Important Note:\nPlease ensure that the selected directory contains only the assignment files.\n\nFor best results:\n"
                        +
                        " - Organize your assignment files in a dedicated folder.\n" +
                        " - Verify that all files are relevant before proceeding.\n" +
                        " - If possible, name the files using a sequence (e.g., `Task-01`, `Task-02`, or `Task1`, `Task2`, etc.).\n"
                        +
                        "   (If you're unable or not permitted to rename them, you will be prompted to manually arrange them in the correct order.)\n");

        ConsoleUtils.pressEnter();
        ConsoleUtils.clearPreviousLines(3);

        while (true) {
            System.out.println("\nPlease copy the path to your assignment folder and paste here:");
            String input = sc.nextLine().trim();
            ConsoleUtils.clearConsole();

            if (input.isEmpty()) {
                System.out.println("Error: Directory path cannot be left empty.");
                continue;
            }

            if (input.startsWith("\"") && input.endsWith("\"")) {
                input = input.substring(1, input.length() - 1);
            }

            directory = new File(input);

            if (directory.exists() && directory.isDirectory()) {
                File[] fileList = directory.listFiles();

                if (fileList == null || fileList.length == 0) {
                    System.out.println(
                            "\nError: No files found in the directory. Ensure the directory contains valid assignment files and try again.");

                } else {
                    folderPath = directory.getAbsolutePath();
                    InputUtils.fileList = fileList;
                    return;
                }

            } else {
                if (!directory.exists()) {
                    System.out.println("Error: The provided path does not exist.");
                } else {
                    System.out.println("Error: The provided path is not a valid directory.");
                }

            }
        }
    }

}
