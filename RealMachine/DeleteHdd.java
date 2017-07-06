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

public class DeleteHdd extends Process {

    public static final String[] resources = new String[]{"File delete", "3chan"};
    public static DeleteHdd deleteHdd = new DeleteHdd(90, "DeleteHdd", "BLOCKED", 0);

    public DeleteHdd(int priority, String id, String status, int pointer) {
        super(priority, id, status, pointer, resources);
    }

    @Override
    public void run(int pointer) {

        RealMachine.toConsole("DeleteHdd process is running");
        Logger.writeToLog("DeleteHdd process is running");
        //Blokavimas, laukiant 3iojo kanalo resurso
        //Istrinami duomenys
        byte[] temp = UserMemory.getByteAtAddress(15, 0);
        try {
            RealMachine.externalMemory.deleteFile(temp);
        } catch (IOException ex) {
            RealMachine.pi.set_2();
        }
        //atlaisvinti trecia kanala
        Resources.setStatic("3chan", true);
        //atlaisvinti external memory
        Resources.setStatic("external memory", true);
        //sukuria dinamini pabaigos pranesima
        Resources.addDynamic("Delete_hdd end");
        deleteHdd.setPointer(0);
        Planner.blocked.add(deleteHdd);
        deleteHdd.setStatus("BLOCKED");
        Planner.lineUp();

    }

}
