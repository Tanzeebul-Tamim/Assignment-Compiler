package main;

import java.io.File;
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
        name();
        id();
        assignmentNo();
        fileExtension();
        directoryPath();
    }

    public Scanner getScanner() {
        return sc;
    }

    private void name() {
        while (name == null) {
            try {
                System.out.println("Please enter your full name:");
                String input = utils.formatName(sc.nextLine());

                if (input.isEmpty()) {
                    System.out.println("Error: Name cannot be left empty.\n");
                    continue;
                }

                name = input;

            } catch (NoSuchElementException err) {
                utils.terminate(sc, true);
            }
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
                    System.out.println("\nError: Invalid input. ID must contain only numeric characters.");
                    continue;
                }

                if (input.length() != 8) {
                    System.out.println("\nError: Invalid input. ID must be exactly 8 digits.");
                    continue;
                }

                id = Integer.parseInt(input);

            } catch (NumberFormatException err) {
                System.out.println("\nError: Invalid input. ID must be a numeric value.");

            } catch (NoSuchElementException err) {
                utils.terminate(sc, true);
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
                            .println("\nError: Invalid input. Assignment-No must contain only numeric characters.");
                    continue;
                }

                assignmentNo = Integer.parseInt(input);

                if (assignmentNo < 1 || assignmentNo > 15) {
                    System.out.println("\nError: Invalid input. Assignment-No must be between 1 and 15.");
                    assignmentNo = 0;
                }

            } catch (NumberFormatException err) {
                System.out.println("\nError: Invalid input. Assignment-No must be a numeric value.");

            } catch (NoSuchElementException err) {
                utils.terminate(sc, true);
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
                    System.out.println("\nError: Invalid input. Allowed extensions are:");
                    System.out.println("       " + String.join(", ", validExtensions));
                }

            } catch (NumberFormatException err) {
                System.out.println("\nError: Invalid input. Enter a valid extension.");

            } catch (NoSuchElementException err) {
                utils.terminate(sc, true);
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
                        .println("\nError: Invalid input. Please double-check and enter a valid path.");

            } catch (NoSuchElementException err) {
                utils.terminate(sc, true);
            }
        }
    }
}
