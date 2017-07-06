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

public class GetHdd extends Process {

    public static final String[] resources = new String[]{"File read", "3chan"};
    
    public static GetHdd getHdd = new GetHdd(90, "GetHdd", "BLOCKED", 0);

    public GetHdd(int priority, String id, String status, int pointer) {
        super(priority, id, status, pointer, resources);
    }

    @Override
    public void run(int pointer) {

        RealMachine.toConsole("GetHdd process is running");
        Logger.writeToLog("GetHdd process is running");
        //Blokavimas, laukiant 3iojo kanalo resurso
        //Irasomi duomenys
        byte[] temp = UserMemory.getByteAtAddress(15, 0);
        try {
            byte[] t = RealMachine.externalMemory.fileReadAtPos(temp);
            UserMemory.setBytesAtAddress(15, 1, t);
        } catch (IOException ex) {
            RealMachine.pi.set_2();
        }
        //atlaisvinti trecia kanala
        Resources.setStatic("3chan", true);
        //atlaisvinti external memory
        Resources.setStatic("external memory", true);
        //sukuria dinamini pabaigos pranesima
        Resources.addDynamic("Get_hdd end");
        getHdd.setPointer(0);
        Planner.blocked.add(getHdd);
        getHdd.setStatus("BLOCKED");
        Planner.lineUp();

    }

}
