package main;

import java.io.File;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class InputHandler {
    public String name;
    public int id;
    public int assignmentNo;
    public String fileExtension;
    public String folderPath;

    public File directory;
    public File[] fileList;

    private Utility utils;
    public Scanner sc = new Scanner(System.in);

    public InputHandler(Utility utils) {
        this.utils = utils;
    }

    private String[] validExtensions = {
            "java", "txt", "py", "cpp", "c", "cs", "js", "ts", "html", "css", "xml", "json"
    };

    public String getFileName() {
        String studentIdentity = String.format("%02d", assignmentNo) + "_" + id + "_" + name;
        String fileName = "Assignment " + studentIdentity;

        return fileName;
    }

    public void collectInputs() {
        this.name();
        this.id();
        this.assignmentNo();
        this.fileExtension();
        this.directoryPath();
    }

    private void name() {
        while (name == null) {
            System.out.println("Please enter your full name:");
            String input = utils.formatName(sc.nextLine());

            if (input.isEmpty()) {
                System.out.println("Error: Name cannot be left empty.\n");
                continue;
            }

            name = input;
        }
    }

    private void id() {
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

    private void assignmentNo() {
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

    private void fileExtension() {
        while (fileExtension == null) {
            try {
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

            } catch (NumberFormatException err) {
                System.out.println("\nError: Invalid Input! Enter a valid extension.");

            }
        }

        System.out.println();
    }

    private void directoryPath() {
        System.out.println(
                "Important Note:\nPlease ensure that the selected directory contains only the assignment files. Including unrelated files (e.g., images, documents, or executables) or question-provided tester class files in the directory will result in an invalid assignment text file. Submitting such an incorrect file might cause issues with your university assignment submission.\n\nFor best results:\n"
                        +
                        " - Organize your assignment files in a dedicated folder.\n" +
                        " - Verify that all files are relevant before proceeding.\n" +
                        " - If possible, name the files using a sequence (e.g., `Task-01`, `Task-02`, or `Task1`, `Task2`, etc.).\n"
                        +
                        "   (If the files are not named sequentially, or if you are unable or not permitted to rename them, you will be prompted to manually arrange them in the correct order.)\n");

        while (true) {
            try {
                System.out.println("\nPlease copy the path to your assignment folder and paste here:");
                String input = sc.nextLine().trim();

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
                        this.fileList = fileList;
                        return;
                    }

                } else {
                    if (!directory.exists()) {
                        System.out.println("\nError: The provided path does not exist.");
                    } else {
                        System.out.println("\nError: The provided path is not a valid directory.");
                    }

                }
            } catch (NumberFormatException err) {
                System.out
                        .println("\nError: Invalid Input! Please double-check and enter a valid path.");

            }
        }
    }

    private void choiceCountLoop(int choiceCount) {
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

    public int getUserChoice(
            String prompt1,
            String prompt2,
            int choiceCount,
            String... choices)
            throws InputMismatchException,
            NoSuchElementException {

        int choice = 0;

        if (choices.length != choiceCount) {
            throw new IllegalArgumentException(
                    "The number of provided choices does not match the expected choice count.");
        }

        System.out.printf("\n%s\n\n", prompt1);

        if (prompt2 != null) {
            System.out.printf("%s\n", prompt2);
        }

        for (int i = 0; i < choiceCount; i++) {
            System.out.printf("(%d) %s", i + 1, choices[i]);

            if (i != choiceCount - 1) {
                System.out.println();
            }
        }

        while (choice == 0) {
            try {
                System.out.print("\n\nPlease enter your choice ");
                this.choiceCountLoop(choiceCount);

                String input = sc.nextLine().trim();

                if (input.isEmpty()) {
                    System.out.println("Error: You must pick one of the options.");
                    continue;
                }

                if (!input.matches("\\d+")) {
                    System.out
                            .print("\nError: Invalid Input! Please enter a numeric value corresponding to one of the options ");
                    this.choiceCountLoop(choiceCount);
                    continue;
                }

                choice = Integer.parseInt(input);

                if (choice < 1 || choice > choiceCount) {
                    System.out
                            .print("\nError: Invalid Input! Please enter a numeric value corresponding to one of the options ");
                    this.choiceCountLoop(choiceCount);
                    choice = 0;
                }

                System.out.println();

            } catch (NumberFormatException err) {
                System.out.printf("\nError: Invalid Input! Enter a number between 1 to %d\n", choiceCount);
            }
        }

        return choice;
    }

}
