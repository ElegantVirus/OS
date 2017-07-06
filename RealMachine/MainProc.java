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

public class MainProc extends Process {

    public static MainProc mainProc = new MainProc(99, "Main_Proc", "BLOCKED", 0);

	//public static final String[] TEMP = ;

    public MainProc(int priority, String id, String status, int pointer) {
        super(priority, id, status, pointer, new String[]{"Program ok"});
    }

    @Override
    public void run(int pointer) {//laukia programa ok
        RealMachine.toConsole("Main process is running");
        Logger.writeToLog("Main process is running");
        JobGovernor jg = new JobGovernor(98, "Job_Governor", "BLOCKED", 0);
        Planner.ready.add(jg);
        Planner.blocked.add(mainProc);
        Planner.lineUp();

    }

}
