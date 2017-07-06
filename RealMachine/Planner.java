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
import java.util.Collections;
import java.util.Comparator;


public class Planner {

    private final static String READY = "READY";
    private final static String RUNNING = "RUNNING";
    private final static String BLOCKED = "BLOCKED";
    public static ArrayList<Process> ready = new ArrayList<Process>();
    public static Process running;
    public static ArrayList<Process> blocked = new ArrayList<Process>();


    /**
     * Procesai surikiuojami pagal prioritetą
     */
    public static void lineUp() {

        for (int i = 0; i < blocked.size() && !blocked.isEmpty(); i++) {
            if (canBeSetToReady(blocked.get(i))) {
                readyProcess(blocked.get(i));
            }
        }
        {
            Collections.sort(ready, new Comparator<Process>() {
                @Override
                public int compare(Process a, Process b) {
                    return a.getPriority() - b.getPriority();
                }
            });
        }
        if (!ready.isEmpty()) {
            runProcess(ready.get(0));
            running.run(running.getPointer());
        }

    }

    public static void sort() {

    }

    /**
     *
     * @param process
     * @return if the requested resources are available takes them and returns
     * true
     */
    private static boolean canBeSetToReady(Process process) {
      
	String resource = process.getAResource();
         if(Resources.check(resource)){
		 Resources.takeAResource(resource);
		 return true;
	 }
	 return false;
            
    }

    public static void blockProcess(Process process) {

        if (process.equals(running)) {
            process.setStatus("BLOCKED");
            blocked.add(process);
        }

    }

    public static void runProcess(Process process) {
        ready.remove(process);
        process.setStatus("RUNNING");
        running = process;
    }

    public static void readyProcess(Process process) {
        blocked.remove(process);
        process.setStatus(READY);
        ready.add(process);
    }
}
