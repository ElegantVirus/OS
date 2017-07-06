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

public class ReadBytes extends Process {

    public static final String[] resources = new String[]{"Read word", "1chan"};
    public static ReadBytes readBytes = new ReadBytes(80, "ReadBytes", "BLOCKED", 0);

    public ReadBytes(int priority, String id, String status, int pointer) {
        super(priority, id, status, pointer, resources);
    }

    @Override
    public void run(int pointer) {
        RealMachine.toConsole("ReadBytes process is running");
        Logger.writeToLog("ReadBytes process is running");
        switch (pointer) {
            case 0:
                //Blokavimas, laukiant zodis skaitymui
                readBytes.setPointer(readBytes.getPointer() + 1);
                Planner.blocked.add(readBytes);
                readBytes.setStatus("BLOCKED");
                Planner.lineUp();
                break;
            case 1:
                //Blokavimas, laukiant 1ojo kanalo resurso
                //readBytes.setPointer(readBytes.getPointer() + 1);
                //Planner.blocked.add(readBytes);
                //readBytes.setStatus("BLOCKED");
                //break;
                // case 2:
                //eilutes kopijavimas i supervizorine
                UserMemory.setBytesAtAddress(16, 0, os.Main.getText().getBytes());
                //Atlaisvinama supervizorine
                //  Resources.setStatic("supervisor memory", true);
                //Atlaisvinamas 1 kanalas
                Resources.setStatic("1chan", true);
                Resources.addDynamic("Read_Bytes end");
                readBytes.setPointer(0);
                Planner.blocked.add(readBytes);
                readBytes.setPriority(readBytes.getPriority() - 1);
                readBytes.setStatus("BLOCKED");
                Planner.lineUp();
                break;
        }
    }

}
