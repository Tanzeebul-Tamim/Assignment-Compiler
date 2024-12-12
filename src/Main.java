import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;
import main.*;

// Todo - can use both (sequenced, not sequenced), can merge files to be considered as a single task

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
                    input,
                    utils,
                    input.fileList,
                    input.folderPath,
                    input.getFileName(),
                    input.fileExtension,
                    taskSequenceTracker);

            fileUtil.validateFileExt();
            fileUtil.setFileList(utils.detectSequence(fileUtil.getFileNames()));

            fileUtil.readFiles();
            fileUtil.writeFiles();

            input.sc.close();
        }
    }
}
