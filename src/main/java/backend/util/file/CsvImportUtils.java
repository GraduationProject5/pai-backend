package backend.util.file;

public class CsvImportUtils {

    public static boolean isCsv(String fileName) {
        if (fileName.endsWith(".csv")) {
            return true;
        } else {
            return false;
        }

    }

}
