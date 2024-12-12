package main;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utility {

    public void printTitle() {
        System.out.println("Welcome to the Assignment File Generator Tool!");
        System.out.println("You can press Ctrl + C anytime to terminate the program.\n");
        System.out.println(
                "The output file will be saved using the following naming format: `Assignment XX_Your-ID_Your-Name`");
        System.out.println("(e.g., Assignment 03_24100000_Joe Brooks.txt)\n");
    }

    public void printError() {
        System.out.println("An error occurred while trying to write the output file.");
        System.out.println("Possible reasons:");
        System.out.println("- The program does not have permission to write in the specified directory.");
        System.out.println("- There may not be enough disk space available.");
        System.out.println("- The file path might be invalid or corrupted.");
    }

    public void terminate(Scanner sc, boolean intentional) {
        if (intentional) {
            System.out.println("\n\nProgram terminated! Thank you for exploring this tool.");
        } else {
            System.out.println("\n\nProgram terminated due to an unexpected error!");
        }

        sc.close();
        System.exit(0);
    }

    public void detectSequence(List<String> fileNames) {
        String[] sequencedFileNames = new String[fileNames.size()];
        String[] remainingFileNames = new String[fileNames.size()];

        boolean sequenceExists = true;
        boolean atLeastOneNumeric = false;

        for (int i = 0, j = 0; i < fileNames.size(); i++) {
            String fileName = fileNames.get(i);
            String pattern = "\\d+";
            boolean found = false;

            Pattern regex = Pattern.compile(pattern);
            Matcher matcher = regex.matcher(fileName);

            while (matcher.find()) {
                String numberStr = matcher.group();
                int number = Integer.parseInt(numberStr);

                if (number >= 1 && number <= fileNames.size()) {
                    sequencedFileNames[number - 1] = fileName;
                    found = true;
                    atLeastOneNumeric = true;
                    break;
                }
            }

            if (!found) {
                sequenceExists = false;
                remainingFileNames[j++] = fileName;
            }
        }

        if (!atLeastOneNumeric) {
            sequencedFileNames = null;

            System.out.println("\nNo sequence detected.");

            if (remainingFileNames.length > 0) {
                System.out.println("\nFiles found:");

                for (String name : remainingFileNames) {
                    if (name != null) {
                        System.out.println(name);
                    }
                }
            }

            System.out.println("\nPlease review the files manually:");

        } else if (!sequenceExists) {
            System.out.println("\nNot all files follow the sequence.");

            if (sequencedFileNames.length > 0) {
                System.out.println("\nSequenced files detected:");

                for (String name : sequencedFileNames) {
                    if (name != null) {
                        System.out.println(name);
                    }
                }
            }

            if (remainingFileNames.length > 0) {
                System.out.println("\nRemaining jumbled files:");

                for (String name : remainingFileNames) {
                    if (name != null) {
                        System.out.println(name);
                    }
                }
            }

            System.out.println("\nPlease review the files manually:");

        } else {
            if (sequencedFileNames.length > 0) {
                System.out.println("\nWe have found the following sequence:");

                for (String name : sequencedFileNames) {
                    if (name != null) {
                        System.out.println(name);
                    }
                }
            }
        }
    }

    public String formatName(String name) {
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
}