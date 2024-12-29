import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;

import utilities.*;

// Todo: Implement merge files feature
// Todo: Implement partial sequencing feature when partial sequence detecting
// Todo: Update readme, add about demo file (add a redirecting link in the doc) and update the prompt instructions
public class Main {
    public static void main(String[] args) {
        /*
         * Tracks the task sequence-numbers
         * Ensures unique and consistent numbering in the output file.
         * (e.g. // TASK 1, // TASK 2)
         */
        AtomicInteger taskSequenceTracker = new AtomicInteger(0);
        FileUtils fileUtil;

        try {
            // DisplayUtils.printTitle();
            InputUtils.collectInputs();

            fileUtil = new FileUtils(
                    InputUtils.fileList,
                    InputUtils.folderPath,
                    InputUtils.getFileName(),
                    InputUtils.fileExtension,
                    taskSequenceTracker);

            // Ask the user if they want to sequence the files or keep their original order.
            int choice = InputUtils.promptForFileSequencing();

            fileUtil.filterFiles(); // Ensures that the file extensions are valid before processing

            if (choice == 1) {
                // Sets file list sequentially
                fileUtil.setFileList(SequenceUtils.sequenceFiles(fileUtil.getFileNames()));
            }

            fileUtil.readFiles(); // Reads file contents from the files located in the provided path
            fileUtil.writeFiles(); // Generates the output file and writes the content in it

        } catch (NoSuchElementException | InterruptedException err) {
            // Catching NoSuchElementException when user presses Ctrl+C to terminate the
            // program
            ConsoleUtils.terminate(true);

        } catch (Exception err) {
            // Catching other unintentional & unexpected exceptions and terminating the
            // program
            ConsoleUtils.terminate(false);

        } finally {
            // Ensure scanner is closed regardless of termination
            if (InputUtils.sc != null) {
                InputUtils.sc.close();
            }
        }
    }
}
