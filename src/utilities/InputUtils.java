package utilities;

import java.io.File;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public final class InputUtils extends BaseUtils {
    public static String name;
    public static String id;
    public static String assignmentNo;

    public static String fileExtension;
    public static String folderPath;
    public static File directory;
    public static File[] fileList; // Stores all the files found in the provided directory

    public static Scanner sc = new Scanner(System.in);
    private static String error;

    // Valid file extensions
    private static String[] validExtensions = {
            "java", "txt", "py", "cpp", "c", "cs", "js", "ts", "html", "css", "xml", "json"
    };

    // Method to generate output file name using user data
    public static String getFileName() {
        String studentIdentity = assignmentNo + "_" + id + "_" + name;
        String fileName = "Assignment " + studentIdentity;

        return fileName;
    }

    // Method to collect all user information
    public static void collectInputs()
            throws NoSuchElementException,
            InputMismatchException,
            InterruptedException {

        DisplayUtils.printNamingFormat("", "", "", "");
        assignmentNo();
        id();
        name();
        fileExtension();
        ConsoleUtils.clearConsole();
        directoryPath();
    }

    // Collect assignment no
    private static void assignmentNo() throws InterruptedException {
        int assignmentNo = 0;

        while (assignmentNo == 0) {
            try {
                System.out.println("Please enter the Assignment-no (1-15):");
                String input = sc.nextLine().trim();

                if (input.isEmpty()) {
                    error = "Assignment-no cannot be left empty.";
                    DisplayUtils.printError(error, errorInterval);

                    continue;
                }

                if (input.startsWith("-")) {
                    error = "Invalid Input! Assignment-no must be a positive numeric value.";
                    DisplayUtils.printError(error, errorInterval);

                    continue;
                }

                if (!input.matches("\\d+")) {
                    error = "Invalid Input! Assignment-no must contain only numeric characters.";
                    DisplayUtils.printError(error, errorInterval);

                    continue;
                }

                assignmentNo = Integer.parseInt(input);

                if (assignmentNo < 1 || assignmentNo > 15) {
                    error = "Invalid Input! Assignment-no must be between 1 and 15.";
                    DisplayUtils.printError(error, errorInterval);

                    assignmentNo = 0;
                }

            } catch (NumberFormatException err) {
                error = "Invalid Input! Assignment-no must be a numeric value.";
                DisplayUtils.printError(error, errorInterval);

            }
        }

        InputUtils.assignmentNo = String.format("%02d", assignmentNo);
        DisplayUtils.printNamingFormat(InputUtils.assignmentNo, "", "", "");
    }

    // Collect user id
    private static void id() throws InterruptedException {
        while (id == null) {
            try {
                System.out.println("Please enter your 8-digit ID (e.g., 24100000):");
                String input = sc.nextLine().trim();

                if (input.isEmpty()) {
                    error = "ID cannot be left empty.";
                    DisplayUtils.printError(error, errorInterval);

                    continue;
                }

                if (input.startsWith("-")) {
                    error = "Invalid Input! ID must contain only positive numeric characters.";
                    DisplayUtils.printError(error, errorInterval);

                    continue;
                }

                if (!input.matches("\\d+")) {
                    error = "Invalid Input! ID must contain only numeric characters.";
                    DisplayUtils.printError(error, errorInterval);

                    continue;
                }

                if (input.length() != 8) {
                    error = "Invalid Input! ID must be exactly 8 digits.";
                    DisplayUtils.printError(error, errorInterval);

                    continue;
                }

                id = input;

            } catch (NumberFormatException err) {
                error = "Invalid Input! ID must be a numeric value.";
                DisplayUtils.printError(error, errorInterval);

            }
        }

        DisplayUtils.printNamingFormat(assignmentNo, id, "", "");
    }

    // Collect user name
    private static void name() throws InterruptedException {
        while (name == null) {
            System.out.println("Please enter your full name:");
            String input = ConsoleUtils.formatName(sc.nextLine()); // Formats name

            if (input.isEmpty()) {
                error = "Name cannot be left empty.";
                DisplayUtils.printError(error, errorInterval);

                continue;
            }

            name = input;
        }

        DisplayUtils.printNamingFormat(assignmentNo, id, name, "");
    }

    // Collect the required file extension
    private static void fileExtension() throws InterruptedException {
        while (fileExtension == null) {
            System.out.println(
                    "Please enter the required file extension for your assignment (e.g., java, py, cpp):");
            String input = sc.nextLine().trim();

            if (input.isEmpty()) {
                error = "File extension cannot be left empty.";
                DisplayUtils.printError(error, errorInterval);

                continue;
            }

            boolean isValid = false;
            for (String ext : validExtensions) {
                if (input.equalsIgnoreCase(ext)) {
                    input = input.toLowerCase();
                    isValid = true;
                    break;
                }
            }

            if (isValid) {
                fileExtension = "." + input;
                input = input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
                DisplayUtils.printNamingFormat(assignmentNo, id, name, input);
                Thread.sleep(interval);
            } else {
                error = "Invalid Input! Allowed extensions are: " + String.join(", ", validExtensions);
                DisplayUtils.printError(error, errorInterval + 1200);
            }
        }
    }

    // Collect the directory path where the assignment files are located
    private static void directoryPath() throws InterruptedException {
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
            System.out.println("Please copy the path to your assignment folder and enter here:");
            String input = sc.nextLine().trim();
            ConsoleUtils.clearConsole();

            if (input.isEmpty()) {
                error = "Directory path cannot be left empty.";
                DisplayUtils.printError(error, errorInterval);

                continue;
            }

            if (input.startsWith("\"") && input.endsWith("\"")) {
                input = input.substring(1, input.length() - 1);
            }

            directory = new File(input);

            if (directory.exists() && directory.isDirectory()) {
                File[] fileList = directory.listFiles();

                if (fileList == null || fileList.length == 0) {
                    error = "File extension cannot be left empty.";
                    DisplayUtils.printError(error, errorInterval);

                } else {
                    folderPath = directory.getAbsolutePath();
                    InputUtils.fileList = fileList;
                    return;
                }

            } else {
                if (!directory.exists()) {
                    error = "The provided path does not exist.";
                } else {
                    error = "The provided path is not a valid directory.";
                }

                DisplayUtils.printError(error, errorInterval);
            }
        }
    }

    // Collect user input among multiple input options
    public static String getUserChoice(
            String prompt1,
            String prompt2,
            int fileCount,
            String[] keywords,
            String... choices)
            throws InputMismatchException,
            NoSuchElementException,
            InterruptedException {

        int choice = 0;
        String input = "";

        if (prompt1 != null) {
            System.out.printf("\n%s\n", prompt1);
        }

        if (prompt2 != null) {
            System.out.printf("\n%s\n", prompt2);
        }

        if (choices.length > 0) {
            for (int i = 0; i < choices.length; i++) {
                String serial = String.format("%02d", i + 1);
                System.out.printf("   %s. %s", serial, choices[i]);

                if (i != choices.length - 1) {
                    System.out.println();
                }
            }
        }

        while (choice == 0) {
            try {
                if (choices.length > 0) {
                    System.out.print("\n\nEnter your choice ");
                    DisplayUtils.choiceCountLoop(choices.length);
                } else {
                    System.out.print("Enter a number within the valid range ");
                    DisplayUtils.choiceCountLoop(fileCount);
                }

                input = sc.nextLine().trim();

                if (input.isEmpty()) {
                    error = "You must pick one of the options.";
                    DisplayUtils.printError(error, errorInterval);

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
                    error = "Invalid Input! Please enter a numeric value.";
                    DisplayUtils.printError(error, errorInterval, choices.length);

                    continue;
                }

                choice = Integer.parseInt(input);
                int upperBound = (choices.length > 0) ? choices.length : fileCount;

                if (choice < 1 || choice > upperBound) {
                    error = "Invalid Input! Enter a number from the options ";
                    DisplayUtils.printError(error, errorInterval, upperBound);

                    choice = 0;
                    continue;
                }

            } catch (InputMismatchException | IllegalArgumentException err) {
                error = "Input out of range! Enter a number between 1 to " + choices.length;
                DisplayUtils.printError(error, errorInterval);

            }

        }

        return input;
    }
}
