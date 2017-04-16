package RealMachine;

import Registers.*;
import VirtualMachine.VirtualMachine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class RealMachine {

    CommonUseRegisters r1 = new CommonUseRegisters();
    CommonUseRegisters r2 = new CommonUseRegisters();
    Ch_Register ch1 = new Ch_Register();
    Ch_Register ch2 = new Ch_Register();
    Ch_Register ch3 = new Ch_Register();
    Sf_Register sf = new Sf_Register();
    ModeRegister mode = new ModeRegister();
    TI_Register ti = new TI_Register();
    IOI_Register ioi = new IOI_Register();
    IntRegister pi = new IntRegister();
    IntRegister si = new IntRegister();

    List<VirtualMachine> vm;

    //shows which part in userMemory belongs to VirtualMachine
    static int ptr;
    /**
     * ptr nesikeičia !!! nuėmiau ptr iš atminties
     */

    public static ExternalMemory externalMemory;
    UserMemory memory;

    public RealMachine() {
        //generating a singleton object
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
        
        byte[][][] process= new byte[16][16][4];;
        try {
            process = externalMemory.fillArray(externalMemory.searchInHDD(name));           // System.out.print(externalMemory.readBlock(externalMemory.searchInHDD(name)));

            for (int i = 1; i < 16; i++) {
                for (int j = 0; j < 16; j++) {
                    for (int y = 0; y < 4; y++) {
                        System.out.print((char)process[i][j][y]);
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(RealMachine.class.getName()).log(Level.SEVERE, null, ex);
        }

        

            
        memory.loadToSupervisory(process);
        int vmPtr = memory.loadFromSupervisoryToVm();
        if (vmPtr == -2) {
            errorMsg("Wrong commands were found");
            // System.out.println("Wrong commands were found");
        } else if (vmPtr == -1) {
            errorMsg("Not enough memory");
            //System.out.println("Not enough memory");
        } else {
            memory.printMemoryContents();
            vm.add(memory.getVmCount() - 1, new VirtualMachine(mode, ti, vmPtr));
            vm.get(memory.getVmCount() - 1).work();
            System.out.println(vm.get(memory.getVmCount() - 1).toString());

        }
        // memory.supervisorMemory.fillTable();

    }

    public void errorMsg(String message) {
        JFrame frame = new JFrame(message);
        frame.setSize(100, 200);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JLabel label = new JLabel(message, SwingConstants.CENTER);
        frame.setResizable(false);
        frame.add(label);
        frame.setVisible(true);
    }

}
