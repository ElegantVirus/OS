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

public class PutHdd extends Process {

    public static final String[] resources = new String[]{"File write", "3chan"};
    public static PutHdd putHdd = new PutHdd(90, "PutHdd", "BLOCKED", 0);

    public PutHdd(int priority, String id, String status, int pointer) {
        super(priority, id, status, pointer, resources);
    }

    @Override
    public void run(int pointer) {
        RealMachine.toConsole("PutHdd process is running");
        Logger.writeToLog("PutHdd process is running");
        switch (pointer) {
            case 0:
                //Blokavimas, laukiant isorines atminties resurso external memory
                putHdd.setPointer(putHdd.getPointer() + 1);
                Planner.blocked.add(putHdd);
                putHdd.setStatus("BLOCKED");
                Planner.lineUp();
                break;
            case 1:
                //Blokavimas, laukiant 3iojo kanalo resurso
                //Irasomi duomenys
                byte[] temp = UserMemory.getByteAtAddress(15, 0);
                byte[] t = UserMemory.getByteAtAddress(15, 1);
                try {
                    RealMachine.externalMemory.fileRewriteAtPos(temp, t);
                } catch (IOException ex) {
                    RealMachine.pi.set_2();
                }
                //atlaisvinti trecia kanala
                Resources.setStatic("3chan", true);
                //atlaisvinti external memory
                Resources.setStatic("external memory", true);
                //sukuria dinamini pabaigos pranesima
                Resources.addDynamic("Put_hdd end");
                putHdd.setPointer(0);
                Planner.blocked.add(putHdd);
                putHdd.setStatus("BLOCKED");
                Planner.lineUp();
                break;
        }
    }

}
