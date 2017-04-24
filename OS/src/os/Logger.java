package os;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;

public class Logger {

    public static BufferedWriter log = null;

    public static void init(String filename){
        if (log == null) {
            FileWriter temp;
            try {
                temp = new FileWriter(filename);
                log = new BufferedWriter(temp);
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(Logger.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void writeToLog(String msg) {
        if (log != null) {
            try {
                log.write(msg);
                log.newLine();
                log.flush();
            } catch (IOException e) {
                System.out.println("Unable to write to file");
            }
        } else {
            System.out.println("Logger is not open");
        }
    }

    public static void close() throws IOException {
        if (log != null) {
            log.close();
        }
    }
}
