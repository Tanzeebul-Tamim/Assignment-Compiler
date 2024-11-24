package main;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utility {

    public void printTitle() {
        System.out.println("Welcome to the Assignment File Generator Tool!");
        System.out.println("You can press Ctrl + C anytime to terminate the program.\n");
        System.out.println(
                "The output file will be saved using the following naming format: `Assignment XX_Your-ID_Your-Name`");
        System.out.println("(e.g., Assignment 01_12345678_Joe Brooks)\n");
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

    public void detectSequence(String[] fileNames) {
        Pattern pattern = Pattern.compile("\\d+");

        for (String fileName : fileNames) {
            Matcher matcher = pattern.matcher(fileName);

            if (matcher.find()) {
                System.out.println("Found " + fileName);
            } else {
                System.out.println("Not found " + fileName);
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