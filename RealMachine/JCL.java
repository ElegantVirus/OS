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

import static RealMachine.UserMemory.getCharArrayAtAddress;
import java.util.ArrayList;
import os.Logger;

public class JCL extends Process {

    public static final String[] resources = new String[]{"Check program"};
    public static JCL jcl = new JCL(65, "JCL", "BLOCKED", 0);
    public static String output = "";

    public static boolean isSupervisoryCommandsOk() {

        boolean codeSegmentIsFound = false;
        for (int i = 0; i <= 16; i++) {
            for (int j = 0; j < 16; j++) {
                char[] command = getCharArrayAtAddress(i, j);
                if ((command[0] == 'C') && (command[1] == 'O') && (command[2] == 'D') && (command[3] == 'E')) {
                    codeSegmentIsFound = true;
                }
                if (codeSegmentIsFound) {
                    String tempCommand = new StringBuilder().append(command[0]).append(command[1]).toString();//should simplify it :D
                    switch (tempCommand) {
                        case "CO": {
                            if ((command[2] == 'D') && (command[3] == 'E')) {
                                break;
                            } else {
                                return false;
                            }
                        }
                        case "LR":
                        case "SR":
                        case "RR":
                        case "AD":
                        case "SB":
                        case "MU":
                        case "DI":
                        case "CR":
                        case "AN":
                        case "XO":
                        case "OR":
                        case "NO": {
                            if ((Character.isDigit(command[2])) && (Character.isDigit(command[3]))) {
                                int x1 = charToInt(command[2]);
                                int x2 = charToInt(command[3]);
                                if ((x1 * 16 + x2) < 256 && ((x1 * 16 + x2) >= 0)) {
                                    break;
                                } else {
                                    return false;
                                }
                            } else {
                                return false;
                            }
                        }
                        case "JU":
                        case "JG":
                        case "JE":
                        case "JN":
                        case "JL": {
                            if ((Character.isDigit(command[2])) && (Character.isDigit(command[3]))) {
                                int x1 = charToInt(command[2]);
                                int x2 = charToInt(command[3]);
                                if ((x1 * 16 + x2) < 256 && ((x1 * 16 + x2) >= 129)) {
                                    break;
                                } else {
                                    return false;
                                }
                            } else {
                                return false;
                            }
                        }

                        case "FR": //skaityti faila
                        case "FW": //rasyti i faila
                        case "FD": //istrinti faila
                        case "FO": //atidaryti faila
                        case "FC": //uzdaryti faila
                            break;
                        case "GD": //get data
                        {
                            os.Main.setVi();
                        }
                        case "PD": //put data
                        {
                            break;
                        }
                        case "HA": {
                            if ((command[2] == 'L') && (command[3] == 'T')) {
                                return true;
                            } else {
                                return false;
                            }
                        }
                        default: {

                            System.out.println("Wrong command found in supervisory memory's code segment");
                            return false;
                        }
                    }
                    // return true;
                }
            }
        }
        return false; // fill be false if there was no 'CODE' tag
    }

    public static int charToInt(char ch) {
        int temp = (char) (ch - '0');
        return temp;
    }

    public JCL(int priority, String id, String status, int pointer) {
        super(priority, id, status, pointer, resources);
    }

    @Override
    public void run(int pointer) {
        RealMachine.toConsole("JCl process is running");
        Logger.writeToLog("JCL process is running");
        if (isSupervisoryCommandsOk()) {
            Resources.dynamicRes.add("Program ok");
            Planner.blocked.add(jcl);
            // jcl.setPointer(0);
            jcl.setStatus("BLOCKED");
            jcl.setPriority(jcl.getPriority() - 1);
            Planner.lineUp();
        } else {
            Resources.dynamicRes.add("Program not ok");
            Planner.blocked.add(jcl);
            // jcl.setPointer(0);
            jcl.setStatus("BLOCKED");
            jcl.setPriority(jcl.getPriority() - 1);
        }

    }
}
