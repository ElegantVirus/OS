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
import java.util.HashMap;
import os.Logger;

public class Resources {

    // public static HashMap<String, Boolean> dynamicRes = new HashMap<>();
    public static ArrayList<String> dynamicRes = new ArrayList<String>();
    //value true if available
    public static HashMap<String, Boolean> staticRes = new HashMap<>();

    /**
     * Inicializuoti pradines busenas
     */
    public static void initStatic() {
        staticRes.put("supervisor memory", true);
        staticRes.put("user memory", true);
        staticRes.put("external memory", true);
        staticRes.put("1chan", true);
        staticRes.put("2chan", true);
        staticRes.put("3chan", true);
        staticRes.put("interrupt", true);
        dynamicRes.add("kaka");
    }

    public static void addDynamic(String name) {
        Logger.writeToLog("Resource " + name + " added");
        dynamicRes.add(name);
        Logger.writeToLog("Resource " + name + " adde");
    }

    public static void deleteDynamic(String name) {
        Logger.writeToLog("Resource " + name + " deleted");
        dynamicRes.remove(name);
    }

    public static void setStatic(String name, Boolean state) {
        staticRes.replace(name, state);
        Logger.writeToLog("Resource " + name + " set to " + state);
    }

    public static Boolean check(String name) {

        if (checkDynamic(name) || checkStatic(name)) {
            return true;
        } else {
            return false;
        }
    }

    public static Boolean checkDynamic(String name) {

        if (dynamicRes.contains(name)) {
            return true;
        } else {
            return false;
        }
    }

    public static Boolean checkStatic(String name) {

        try {
            if (staticRes.get(name)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public static void takeAResource(String name) {
        if (checkStatic(name) == true) {
            setStatic(name, false);
        }
        if (checkDynamic(name) == true) {
            deleteDynamic(name);
        }
    }

}
