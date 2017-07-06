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

import static RealMachine.RealMachine.externalMemory;
import static RealMachine.RealMachine.memory;
import static RealMachine.RealMachine.mode;
import static RealMachine.RealMachine.toConsole;
import static RealMachine.RealMachine.vm;
import VirtualMachine.VirtualMachine;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProgramChooser extends Process {
//instance of an object to operate cuz have to make only ONE instance  
public static final String[] TEMP = new String[]{"From interface", "3chan", "supervisor memory"};

    public static ProgramChooser programChooser = new ProgramChooser(85, "ProgramChooser", "BLOCKED", 0);
    public static byte[][][] process = new byte[16][16][4];
    public static String name;

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        ProgramChooser.name = name;
    }

    public static void readProcess() {
        try {
            process = externalMemory.fillArray(externalMemory.searchInHDD(name));
        } catch (IOException ex) {
            Logger.getLogger(ProgramChooser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void addProcess() {

        memory.loadToSupervisory(process);
            }

    public ProgramChooser(int priority, String id, String status, int pointer) {
        super(priority, id, status, pointer,TEMP);
	
    }

    @Override
    public void run(int pointer) {
        RealMachine.toConsole("ProgramChooser process is running");
        os.Logger.writeToLog("ProgramChooser process is running");
        switch (pointer) {
            case 0:
                //Blokavimas, laukiant is vartotojo sasajos
                programChooser.setPointer(programChooser.getPointer() + 1);
                programChooser.setStatus("BLOCKED");
                Planner.blocked.add(programChooser);
                Planner.lineUp();
                break;
            case 1:
                //Blokavimas, laukiant 3iojo kanalo
                programChooser.setPointer(programChooser.getPointer() + 1);
                //Failo nuskaitymas
                readProcess();
                //3iojo kanalo atlaisvinimas
                Resources.setStatic("3chan", true);
                programChooser.setStatus("BLOCKED");
                Planner.blocked.add(programChooser);
                Planner.lineUp();

                break;
            case 2:
                //Laukia supervizorines atminties
                programChooser.setStatus("BLOCKED");

                //Kopijavimas i supervizorine
                addProcess();
                //Programos tikrinimas resurso atlaisvinimas
                Resources.setStatic("supervisor memory", true);
                Resources.dynamicRes.add("Check program");
                programChooser.setPointer(0);
                Planner.blocked.add(programChooser);
                programChooser.setPriority(programChooser.getPriority() - 1);
                Planner.lineUp();
                break;

        }
    }
}
