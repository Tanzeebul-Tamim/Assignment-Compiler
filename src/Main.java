import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;
import main.*;

// Todo: press enter, clear console, merge files
public class Main {
    public static void main(String[] args) {
        FileUtility fileUtil;
        InputHandler input = new InputHandler();

        /*
         * Tracks the task sequence-numbers to ensure unique and consistent numbering in
         * the output file.
         * e.g. // TASK 1, // TASK 2
         */
        AtomicInteger taskSequenceTracker = new AtomicInteger(0);

        try {
            Utility.printTitle();
            input.collectInputs();

            fileUtil = new FileUtility(
                    input.fileList,
                    input.folderPath,
                    input.getFileName(),
                    input.fileExtension,
                    taskSequenceTracker);

            fileUtil.filterFiles(); // Ensures that the file extensions are valid before processing
            fileUtil.setFileList(Utility.sequenceFiles(fileUtil.getFileNames())); // Sets file list sequentially

            fileUtil.readFiles(); // Reads file contents from the files located in the provided path
            fileUtil.writeFiles(); // Generates the output file and writes the content in it

        } catch (NoSuchElementException err) {
            // Catching NoSuchElementException when user presses Ctrl+C to intentionally
            // terminate the program
            Utility.terminate(InputHandler.sc, true);

        } catch (Exception err) {
            // Catching other unintentional & unexpected exceptions and terminating the
            // program
            Utility.terminate(InputHandler.sc, false);

        } finally {
            // Ensure scanner is closed regardless of termination
            if (InputHandler.sc != null) {
                InputHandler.sc.close();
            }

        }
    }
}
