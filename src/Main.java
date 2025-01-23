import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;

import utilities.*;

public class Main {
    public static void main(String[] args) throws Exception {
        /*
         * Tracks the task sequence-numbers
         * Ensures unique and consistent numbering in the output file.
         * (e.g. // TASK 1, // TASK 2)
         */
        AtomicInteger taskSequenceTracker = new AtomicInteger(0);
        FileUtils fileUtil;

        try {
            // Todo Uncomment these methods
            // DisplayUtils.printTitle();
            InputUtils.collectInputs();

            fileUtil = new FileUtils(
                    InputUtils.fileList,
                    InputUtils.folderPath,ls
                    InputUtils.getFileName(),
                    InputUtils.fileExtension,
                    taskSequenceTracker);

            // Ask the user if they want to sequence the files or keep their original order.
            String prompt = "Would you like the files to be sequenced by adding unique serial numbers?";
            String[] choices = { "Files will be sequenced.", "Files will remain unsequenced." };
            int choice = InputUtils.getPreference(prompt, choices, choices);

            fileUtil.filterFiles(); // Ensures that the file extensions are valid before processing

            if (choice == 1) {
                // Sets file list sequentially
                fileUtil.setFileList(SequenceUtils.sequenceFiles(fileUtil.getFileNames()));
            } else {
                // Prints the detected sequenced files
                System.out.println("\nFiles detected:");
                int fileCount = 0;

                for (String fileName : fileUtil.getFileNames()) {
                    if (fileName != null) {
                        System.out.printf("   %s. %s\n",
                                String.format("%02d", ++fileCount),
                                fileName);
                    }
                }

                ConsoleUtils.pressEnter();
            }

            fileUtil.readFiles(); // Reads file contents from the files located in the provided path
            fileUtil.writeFiles(); // Generates the output file and writes the content in it
            DisplayUtils.printOutro();

        } catch (NoSuchElementException | InterruptedException err) {
            // Catching NoSuchElementException when user presses `Ctrl+C` to terminate the
            // program
            ConsoleUtils.terminate(true);

        } catch (Exception err) {
            // Catching other unintentional & unexpected exceptions and terminating the
            // program
            ConsoleUtils.terminate(false, err);
            // Todo ConsoleUtils.terminate(false);

        } finally {
            // Ensure scanner is closed regardless of termination
            if (InputUtils.sc != null) {
                InputUtils.sc.close();
            }
        }
    }
}
