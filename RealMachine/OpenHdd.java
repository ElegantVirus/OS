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

import java.io.IOException;
import java.util.ArrayList;
import os.Logger;

public class OpenHdd extends Process {

    public static final String[] resources = new String[]{"File open", "3chan"};
    public static OpenHdd openHdd = new OpenHdd(90, "OpenHdd", "BLOCKED", 0);

    public OpenHdd(int priority, String id, String status, int pointer) {
        super(priority, id, status, pointer, resources);
    }

    @Override
    public void run(int pointer) {
        RealMachine.toConsole("OpenHdd process is running");
        Logger.writeToLog("OpenHdd process is running");
        //Blokavimas, laukiant 3iojo kanalo resurso
        //Irasomi duomenys
        byte[] temp = UserMemory.getByteAtAddress(15, 0);

        if (!RealMachine.externalMemory.fileOpen(temp)) {
            RealMachine.pi.set_2();
        }
        UserMemory.setBytesAtAddress(15, 1, temp);

        //atlaisvinti trecia kanala
        Resources.setStatic("3chan", true);
        //atlaisvinti external memory
        Resources.setStatic("external memory", true);
        //sukuria dinamini pabaigos pranesima
        Resources.addDynamic("Open_hdd end");
        openHdd.setPointer(0);
        Planner.blocked.add(openHdd);
        openHdd.setStatus("BLOCKED");
        Planner.lineUp();

    }

}
