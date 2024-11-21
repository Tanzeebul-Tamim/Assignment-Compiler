package main;

import java.io.File;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Utility {
    public Scanner sc = new Scanner(System.in);

    public File validateDirectoryPath() {
        String filePath;
        File directory;

        try {
            System.out.println("Please enter the file path to your assignment folder:\n");

            while (true) {
                filePath = sc.nextLine();
                directory = new File(filePath);

                if (directory.exists() && directory.isDirectory()) {
                    return directory;
                } else {
                    System.out.println(
                            "\nError: Invalid file path! Please double-check and enter a valid path:");
                    System.out.println("Press Ctrl + C to stop\n");
                }
            }
        } catch (NoSuchElementException | IllegalStateException error) {
            System.out.println("\n\nProgram terminated! Thank you for exploring this tool.");
            sc.close();
            System.exit(0);
            
            return null;
        } catch (Exception error) {
            System.out.println("\n\nProgram terminated due to an unexpected error.");
            return null;
        }
    }

    public boolean validateDirectoryPath(File directory) {
        FileUtility utility = new FileUtility(directory, directory.getAbsolutePath());
        return utility.readFiles();
    }
}