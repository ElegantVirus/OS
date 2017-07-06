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

public class Start_Stop extends Process {

    public static final String[] resources = new String[]{" ", "OS end"};
    public static Start_Stop ss = new Start_Stop(100, "Start_Stop", "BLOCKED", 0);

    public static ProgramChooser programChooser = ProgramChooser.programChooser;
    public static JCL jcl = JCL.jcl;
    public static MainProc mainProc = MainProc.mainProc;
    public static WriteBytes writeBytes = WriteBytes.writeBytes;
    public static ReadBytes readBytes = ReadBytes.readBytes;
    public static GetHdd getHdd = GetHdd.getHdd;
    public static PutHdd putHdd = PutHdd.putHdd;
    public static OpenHdd openHdd = OpenHdd.openHdd;
    public static CloseHdd closeHdd = CloseHdd.closeHdd;
    public static DeleteHdd deleteHdd = DeleteHdd.deleteHdd;
    public static Loader loader = Loader.loader;

    @Override
    public void run(int pointer) {
        RealMachine.toConsole("Start_Stop process is running");
        Logger.writeToLog("Start_Stop process is running");
        switch (pointer) {
            case 0:
                Resources.initStatic();

                Planner.blocked.add(jcl);
                Planner.blocked.add(programChooser);
                Planner.blocked.add(mainProc);
                Planner.blocked.add(writeBytes);
                Planner.blocked.add(readBytes);
                Planner.blocked.add(getHdd);
                Planner.blocked.add(putHdd);
                Planner.blocked.add(openHdd);
                Planner.blocked.add(closeHdd);
                Planner.blocked.add(deleteHdd);
                Planner.blocked.add(loader);
                Planner.blocked.add(ss);

                ss.setPointer(ss.getPointer() + 1);
                //    Planner.lineUp();
                break;
            case 1:
                RealMachine.toConsole("Pabaiga");
                System.exit(1);
                break;
        }

    }

    public Start_Stop(int priority, String id, String status, int pointer) {
        super(priority, id, status, pointer, resources);
    }

}
