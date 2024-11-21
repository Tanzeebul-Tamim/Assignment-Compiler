import java.util.NoSuchElementException;
import java.util.Scanner;
import java.io.File;
import main.*;

// Todo - ask file extension, can use both (sequenced, not sequenced), try implementing a method for name, id, assNo, extension input and move them to utility file

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean terminated = false;

        int id = -1;
        int assignmentNo = -1;
        String name = "";

        try {
            System.out.println(
                    "The output file will be saved using the following naming format: `Assignment XX_YourID_YourName`");
            System.out.println("(e.g., Assignment 01_12345678_Joe Brooks)\n");

            System.out.println("Please enter your full name:");
            name = sc.nextLine();

            while (id == -1) {
                try {
                    System.out.println("\nPlease enter your 8-digit ID (e.g., 12345678):");

                    if (!sc.hasNextInt()) {
                        if (!sc.hasNext()) {
                            throw new NoSuchElementException("User terminated input.");
                        }

                        System.out.println("\nInvalid input. ID must be a numeric value.");
                        System.out.println("Press Ctrl + C to stop\n");
                        sc.next();
                    } else {
                        id = sc.nextInt();

                        if (String.valueOf(id).length() != 8) {
                            System.out.println("\nInvalid ID. It must be exactly 8 digits.");
                            System.out.println("Press Ctrl + C to stop\n");
                            id = -1;
                        }
                    }
                } catch (NoSuchElementException error) {
                    System.out.println("\n\nProgram terminated! Thank you for exploring this tool.");
                    terminated = true;
                    sc.close();
                    System.exit(0);
                    break;
                }
            }

            while (assignmentNo == -1) {
                try {
                    System.out.println("\nPlease enter the assignment number (1-15):");

                    if (!sc.hasNextInt()) {
                        if (!sc.hasNext()) {
                            throw new NoSuchElementException("User terminated input.");
                        }

                        System.out.println("\nInvalid input. Assignment number must be a numeric value.");
                        System.out.println("Press Ctrl + C to stop\n");
                        sc.next();
                    } else {
                        assignmentNo = sc.nextInt();

                        if (assignmentNo < 1 || assignmentNo > 15) {
                            System.out.println("\nInvalid assignment number. It must be between 1 and 15.");
                            System.out.println("Press Ctrl + C to stop\n");
                            assignmentNo = -1;
                        }
                    }
                } catch (NoSuchElementException error) {
                    System.out.println("\n\nProgram terminated! Thank you for exploring this tool.");
                    terminated = true;
                    sc.close();
                    System.exit(0);
                    break;
                }
            }

            System.out.println();

            if (terminated) {
                return;
            }
        } catch (NoSuchElementException error) {
            System.out.println("\n\nProgram terminated! Thank you for exploring this tool.");
            sc.close();
            System.exit(0);
        } catch (Exception error) {
            System.out.println("An unexpected error occurred. Please try again.");
            terminated = true;
        } finally {
            if (terminated) {
                System.out.println("The program was terminated before completion.");
            } else {
                String fileName = "Assignment " + String.format("%02d", assignmentNo) + "_" + id + "_" + name;
                Utility utils = new Utility(fileName);

                File directory;
                FileUtility fileUtil;
                do {
                    directory = utils.validateDirectoryPath();
                    fileUtil = utils.validateDirectoryPath(directory);
                } while (fileUtil == null);

                if (fileUtil != null) {
                    fileUtil.writeFiles();
                }
            }

            sc.close();
        }
    }
}
