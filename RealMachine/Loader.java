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

import static RealMachine.RealMachine.memory;
import static RealMachine.RealMachine.mode;
import static RealMachine.RealMachine.vm;
import VirtualMachine.VirtualMachine;
import java.util.ArrayList;
import os.Logger;

public class Loader extends Process {

    public static final String [] resources = new String[]{"VM memory"}; 
    
    public static int getLastVmIndex() {
        return lastVmIndex;
    }
    private static int lastVmIndex;

    public static Loader loader = new Loader(96, "Loader", "BLOCKED", 0);

    public Loader(int priority, String id, String status, int pointer) {
        super(priority, id, status, pointer, resources);
    }

    public static void loadMemory() {
        int vmIndex = memory.loadFromSupervisoryToVm();
        lastVmIndex = vmIndex;
        //vm.add(new VirtualMachine(mode, memory.getVmPointerByIndex(vmIndex), vmIndex));
        /**/

    }

    @Override
    public void run(int pointer) {
        RealMachine.toConsole("Loader process is running");
        Logger.writeToLog("Loader process is running");
        //Blokavimasis, laukiant „vm atmintis“ resurso
        loadMemory();
        Resources.addDynamic("Loader end");
        Planner.blocked.add(loader);
        Planner.lineUp();
    }

}
