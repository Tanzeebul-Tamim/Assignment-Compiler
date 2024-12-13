import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;
import main.*;

public class Main {
    public static void main(String[] args) {
        FileUtility fileUtil;
        Utility utils = new Utility();
        InputHandler input = new InputHandler(utils);
        
        // Tracks the task sequence numbers to ensure unique and consistent numbering in the output file.
        AtomicInteger taskSequenceTracker = new AtomicInteger(0);
        utils.setInputHandler(input); // Sets InputHandler object reference in Utility class

        try {
            utils.printTitle(); // Prints title
            input.collectInputs(); // Collects all user information

            fileUtil = new FileUtility(
                    input,
                    utils,
                    input.fileList,
                    input.folderPath,
                    input.getFileName(),
                    input.fileExtension,
                    taskSequenceTracker);

            fileUtil.validateFileExt(); // Validates extension
            fileUtil.setFileList(utils.sequenceFiles(fileUtil.getFileNames())); // Sets file list sequentially

            fileUtil.readFiles(); // Reads file contents from the files located in the provided path
            fileUtil.writeFiles(); // Generates the output file and writes the content in it

        } catch (Exception err) {
            if (err instanceof NoSuchElementException) {
                // Intentional Termination by pressing Ctrl+C
                utils.terminate(input.sc, true);
            } else {
                // Unintentional Errors
                utils.terminate(input.sc, false);
            }

        } finally {
            input.sc.close(); // Closes the scanner object
        }
    }
}
