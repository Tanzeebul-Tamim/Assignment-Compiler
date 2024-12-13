import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;
import main.*;

public class Main {
    public static void main(String[] args) {
        FileUtility fileUtil;
        Utility utils = new Utility();

        InputHandler input = new InputHandler(utils);
        AtomicInteger taskSequenceTracker = new AtomicInteger(0);

        utils.setInputHandler(input);

        try {
            utils.printTitle();
            input.collectInputs();

            fileUtil = new FileUtility(
                    input,
                    utils,
                    input.fileList,
                    input.folderPath,
                    input.getFileName(),
                    input.fileExtension,
                    taskSequenceTracker);

            fileUtil.validateFileExt();
            fileUtil.setFileList(utils.sequenceFiles(fileUtil.getFileNames()));

            fileUtil.readFiles();
            fileUtil.writeFiles();

        } catch (Exception err) {
            if (err instanceof NoSuchElementException) {
                // Intentional Termination by pressing Ctrl+C
                utils.terminate(input.sc, true);
            } else {
                // Unintentional Errors
                utils.terminate(input.sc, false);
            }

        } finally {
            input.sc.close();
        }
    }
}
