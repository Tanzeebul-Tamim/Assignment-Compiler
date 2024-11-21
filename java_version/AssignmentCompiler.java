import java.io.File;
import main.Utility;

public class AssignmentCompiler {
    public static void main(String[] args) {
        Utility utils = new Utility();

        File directory;
        boolean isValid;

        do {
            directory = utils.validateDirectoryPath();
            isValid = utils.validateDirectoryPath(directory);
        } while (!isValid);

        utils.sc.close();
    }
}