import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;
import java.io.File;
import main.*;

// Todo - can use both (sequenced, not sequenced)

public class Main {
    public static void main(String[] args) {
        FileUtility fileUtil;
        Utility utils = new Utility();
        InputHandler input = new InputHandler(utils);

        File directory;
        AtomicInteger taskSequenceTracker = new AtomicInteger(0);

        try {
            utils.printTitle();
            input.name();
            input.id();
            input.assignmentNo();
            input.fileExtension();
            System.out.println();

        } catch (NoSuchElementException err) {
            utils.terminate(input.sc, true);

        } catch (Exception err) {
            utils.terminate(input.sc, false);

        } finally {
            directory = input.directoryPath();
            fileUtil = new FileUtility(
                    directory,
                    input.fileList,
                    input.folderPath,
                    input.getFileName(),
                    input.fileExtension,
                    taskSequenceTracker);

            utils.detectSequence(fileUtil.getFileNames());
            // fileUtil.readFiles();
            // fileUtil.writeFiles();

            input.sc.close();
        }
    }
}
