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

public class CloseHdd extends Process {

    public static final String[] resources = new String[]{"File close","3chan"};
    
    public static CloseHdd closeHdd = new CloseHdd(90, "CloseHdd", "BLOCKED", 0);

    public CloseHdd(int priority, String id, String status, int pointer) {

        super(priority, id, status, pointer, resources);

    }

    @Override
    public void run(int pointer) {

        RealMachine.toConsole("CloseHdd process is running");
        Logger.writeToLog("CloseHdd process is running");
        //Blokavimas, laukiant 3iojo kanalo resurso
        //Istrinami duomenys
        byte[] temp = UserMemory.getByteAtAddress(15, 0);

        if (!RealMachine.externalMemory.fileClose(temp)) {
            RealMachine.pi.set_2();
        }
        //atlaisvinti trecia kanala
        Resources.setStatic("3chan", true);
        //atlaisvinti external memory
        Resources.setStatic("external memory", true);
        //sukuria dinamini pabaigos pranesima
        Resources.addDynamic("Close_hdd end");
        closeHdd.setPointer(0);
        Planner.blocked.add(closeHdd);
        closeHdd.setStatus("BLOCKED");
        Planner.lineUp();

    }

}
