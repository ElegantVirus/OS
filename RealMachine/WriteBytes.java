/**
*Operaciniu sistemu projektas
*Autores :
*Evelina Bujyte
*Anastasija Kiseliova
*Matematine informatika
*3 kursas
*2017
**/
package RealMachine;

import java.util.ArrayList;
import os.Logger;

public class WriteBytes extends Process {

    public static final String[] resources = new String[]{"Write word", "2chan"};
    public static WriteBytes writeBytes = new WriteBytes(75, "WriteBytes", "BLOCKED", 0);

    public WriteBytes(int priority, String id, String status, int pointer) {
        super(priority, id, status, pointer, resources);
    }

    @Override
    public void run(int pointer) {
        RealMachine.toConsole("WriteBytes process is running");
        Logger.writeToLog("WriteBytes process is running");
        switch (pointer) {
            case 0:
                //Blokavimas, laukiant zodis rasymui
                writeBytes.setPointer(writeBytes.getPointer() + 1);
                Planner.blocked.add(writeBytes);
                writeBytes.setStatus("BLOCKED");
                Planner.lineUp();
                break;
            case 1:
                //Blokavimas, laukiant 2ojo kanalo
                //writeBytes.setPointer(writeBytes.getPointer() + 1);
                //Planner.blocked.add(writeBytes);
                // writeBytes.setStatus("BLOCKED");
                //   break;
                // case 2:
                //eilutes kopijavimas is supervizorines i isvedimo
                RealMachine.toConsole(UserMemory.getStringAtAddress(16, 0));
                //Atlaisvinama supervizorine
                Resources.setStatic("supervisor memory", true);
                //Atlaisvinamas 2 kanalas
                Resources.setStatic("2chan", true);
                Resources.addDynamic("Write_Bytes end");
                writeBytes.setPointer(0);
                Planner.blocked.add(writeBytes);
                writeBytes.setPriority(writeBytes.getPriority() - 1);
                writeBytes.setStatus("BLOCKED");
                Planner.lineUp();
                break;
        }
    }

}
