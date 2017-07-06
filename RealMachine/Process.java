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

/**
 *
 * @author ElDiablo
 */
abstract public class Process {

    private final static String READY = "READY";
    //private final static String STOPPED="STOPPED";
    private final static String RUNNING = "RUNNING";
    private final static String BLOCKED = "BLOCKED";
    private int priority;
    //isorinis proceso vardas
    private String id;
    private int pointer;
    private String status = READY;
private String [] resources;

    public int getPointer() {
        return pointer;
    }

    public void setPointer(int pointer) {
        this.pointer = pointer;
    }
    

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        Logger.writeToLog("Process "+this.id+" is set to "+ status);
        this.status = status;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Process(int priority, String id, String status, int pointer, String [] list) {
        RealMachine.toConsole("Process created, name: "+id+" priority :"
                +priority+" status :"+status+" pointer :"+pointer);
        
        this.priority = priority;
        this.id = id;
        this.status = status;
        this.pointer = pointer;
	this.resources = new String [list.length];
	System.arraycopy(list, 0, this.resources, 0, list.length);
    }
 public String getAResource(){
	 System.out.println("pointer "+ pointer);
  return this.resources[this.pointer];
 }

    /**
     * "Kai procesui nereikalingi visi arba dalis duotos klasės resursų,
     * iškviečiama procedūra "Atlaisvinti resursą" su dviem parametrais, RSEM -
     * resurso vardas, ir D - atlaisvinamo resurso aprašymas"
     */
    public void freeResource(String name, short param) {
        switch (this.id) {
            case RUNNING: {

                break;
            }
            case READY: {

                break;
            }
            case BLOCKED: {

                break;
            }
            //	case STOPPED:{

            //	break;
            //}		
        }
        
    }

    public int requestResource(String name, short param) {
        switch (this.id) {
            case RUNNING: {

                break;
            }
            case READY: {

                break;
            }
            case BLOCKED: {

                break;
            }
        
        }
       
        return 1;
    }

    abstract public void run(int pointer);
}
