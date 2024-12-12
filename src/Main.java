import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;
import main.*;

// Todo - can use both (sequenced, not sequenced), can merge files to be considered as a single task
// Todo - handle extension validation before sequence detection
// Todo - handle same file name case error
// Todo - test new sequenced file array in file utility

public class Main {
    public static void main(String[] args) {
        FileUtility fileUtil;
        Utility utils = new Utility();
        InputHandler input = new InputHandler(utils);
        AtomicInteger taskSequenceTracker = new AtomicInteger(0);

        try {
            utils.printTitle();
            input.collectInputs();

        } catch (NoSuchElementException err) {
            utils.terminate(input.sc, true);

        } catch (Exception err) {
            utils.terminate(input.sc, false);

        } finally {
            fileUtil = new FileUtility(
                    input.getScanner(),
                    utils,
                    input.fileList,
                    input.folderPath,
                    input.getFileName(),
                    input.fileExtension,
                    taskSequenceTracker);

            utils.detectSequence(fileUtil.getFileNames());

            // ! for (String name : fileUtil.getFileNames()) {
            // System.out.println(name);
            // }

            fileUtil.readFiles();
            fileUtil.writeFiles();

            // input.sc.close();
        }
    }
}
