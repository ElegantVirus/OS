package RealMachine;

import Registers.*;
import VirtualMachine.VirtualMachine;
import java.awt.Color;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import os.Main;

public class RealMachine {
    public static ModeRegister mode = new ModeRegister();
    public static TI_Register ti = new TI_Register();
    public static IOI_Register ioi = new IOI_Register();
     // pi - program interrupt. 1 if memory protection,2 if operation code doesn't exist
    // si - system interrupt. 1 if get data, 2 if put data and 3 if files, 4 if halt

    public static IntRegister pi = new IntRegister();
    public static IntRegister si = new IntRegister();

    public static List<VirtualMachine> vm;

    //shows which part in userMemory belongs to VirtualMachine
    static int ptr;
    /**
     * ptr nesikeičia !!! nuėmiau ptr iš atminties
     */

    public static ExternalMemory externalMemory;
    public static UserMemory memory;

    public RealMachine() {
        //generating a singleton object
        os.Logger.init("Session.txt");
        externalMemory = new ExternalMemory();
        ptr = UserMemory.getPtr();
        memory = new UserMemory();
        vm = new ArrayList<VirtualMachine>();
        /*
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                for (int y = 0; y < 4; y++) {
                    System.out.print((char) process[i][j][y]);

                }
                System.out.println();
            }
        }*/
    }

    public void addProcess(String name) {
        
        byte[][][] process= new byte[16][16][4];
        
        try {
            process = externalMemory.fillArray(externalMemory.searchInHDD(name));           // System.out.print(externalMemory.readBlock(externalMemory.searchInHDD(name)));

      /*      for (int i = 0; i < 16; i++) {
                for (int j = 0; j < 16; j++) {
                    for (int y = 0; y < 4; y++) {
                        System.out.print((char)process[i][j][y]);
                    }
                System.out.println();
                }
            }*/
        } catch (IOException ex) {
            Logger.getLogger(RealMachine.class.getName()).log(Level.SEVERE, null, ex);
        }

       
        memory.loadToSupervisory(process);
        int vmPtr = memory.loadFromSupervisoryToVm();
        if (vmPtr == -2) {
            toConsole("Wrong commands were found");
        } else if (vmPtr == -1) {
            toConsole("Not enough memory");
        } else {
            toConsole("Program has been successfully loaded");
          //  memory.printMemoryContents();
            vm.add(memory.getVmCount() - 1, new VirtualMachine(mode, ti, vmPtr));
            //vm.get(memory.getVmCount() - 1).work();
          //  System.out.println(vm.get(memory.getVmCount() - 1).toString());

        }
        // memory.supervisorMemory.fillTable();

    }

    public static void toConsole(String message) {
        Main.console.setForeground(Color.red);
      //  Main.console.setText(null);
        Main.console.append(message+'\n');
        Main.console.setForeground(Color.black);
      /*  JFrame frame = new JFrame(message);
        frame.setSize(200, 100);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JLabel label = new JLabel(message, SwingConstants.CENTER);
        frame.setResizable(false);
        frame.add(label);
        frame.setVisible(true);*/
    }

}
