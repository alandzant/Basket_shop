import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

public class ClientLog {
    //private List<String> saveLog = new ArrayList<>();
    private String saveLog = "";

    public void log(int productNum, int amount) {
        saveLog = saveLog + String.format("%d,%d%n", productNum, amount);

    }

    public void exportAsCSV(File txtFile) {
        if (!txtFile.exists()) {
           saveLog = "productsNum,amount\n" + saveLog;
        }
        try(FileWriter writer = new FileWriter(txtFile, true)) {
            writer.write(saveLog);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
