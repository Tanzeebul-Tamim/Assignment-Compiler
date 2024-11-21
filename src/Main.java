import java.io.File;
import main.*;

// Todo - input name, id, assignment no, multiple file extension, disclaimer message to organize sequentially using naming format

public class Main {
    public static void main(String[] args) {
        Utility utils = new Utility();

        File directory;
        FileUtility fileUtil;

        do {
            directory = utils.validateDirectoryPath();
            fileUtil = utils.validateDirectoryPath(directory);
        } while (fileUtil == null);

        if (fileUtil != null) {
            fileUtil.writeFiles();
        }

        utils.sc.close();
    }
}