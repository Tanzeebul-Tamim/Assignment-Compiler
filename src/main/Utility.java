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

    public void terminate(Scanner sc, boolean intentional) {
        if (intentional) {
            System.out.println("\n\nProgram terminated! Thank you for exploring this tool.");
        } else {
            System.out.println("\n\nProgram terminated due to an unexpected error!");
        }

        sc.close();
        System.exit(0);
    }

    // Todo
    public void detectSequence(List<String> fileNames) {
        boolean sequenceExists = true;
        String[] sequencedFileNames = new String[fileNames.size()];

        for (int i = 0; i < fileNames.size(); i++) {
            String fileName = fileNames.get(i);
            String pattern = "\\d+";
            boolean found = false;

            Pattern regex = Pattern.compile(pattern);
            Matcher matcher = regex.matcher(fileName);

            while (matcher.find()) {
                String numberStr = matcher.group();
                int number = Integer.parseInt(numberStr);

                if (number >= 1 && number <= 20) {
                    sequencedFileNames[number - 1] = fileName;
                    found = true;
                    break;
                }
            }

            if (!found) {
                sequenceExists = false;
                sequencedFileNames = null;
                break;
            }
        }

        if (sequenceExists) {
            System.out.println("\nWe have found the following sequence:");

            for (String fileName: sequencedFileNames) {
                System.out.println(fileName);
            }

            System.out.println("\nType 'yes' to proceed with the detected sequence or 'no' to manually enter a new sequence.");
        } else {
            System.out.println("\nNo sequence detected. Falling back to manual sequencing...");
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