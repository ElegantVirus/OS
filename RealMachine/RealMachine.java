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

import Registers.*;
import VirtualMachine.VirtualMachine;
import java.awt.Color;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import os.Main;

public class RealMachine {

	public static ModeRegister mode = new ModeRegister();
	public static TI_Register ti = new TI_Register();
	public static IOI_Register ioi = new IOI_Register();
	// pi - program interrupt. 1 if memory protection,2 if operation code doesn't exist
	// si - system interrupt. 1 if get data, 2 if put data and 3 if files, 4 if halt
	// tint - 10 timer interrupt
	public static IntRegister tint;
	public static IntRegister pi;
	public static IntRegister si;

	public static List<VirtualMachine> vm;

	//shows which part in userMemory belongs to VirtualMachine
	static int ptr;

	public static ExternalMemory externalMemory;
	public static UserMemory memory;

	public RealMachine() {
		//generating a singleton object
		os.Logger.init("Session.txt");
		pi = new IntRegister();
		si = new IntRegister();
		tint = new IntRegister();
		externalMemory = new ExternalMemory();
		ptr = UserMemory.getPtr();
		memory = new UserMemory();
		vm = new ArrayList<>();
		
	}


	public static void removeProcess(int vmIndex) {
		for (int i = 0; (i < vm.size()) && (!vm.isEmpty()); i++) {
			if (vm.get(i).getId() == vmIndex) {
				vm.remove(i);
			}
		}
		memory.freeVmMemory(vmIndex);

	}
	// memory.supervisorMemory.fillTable();

	public static void toConsole(String message) {
		Main.console.setForeground(Color.red);
		//  Main.console.setText(null);
		Main.console.append(message + '\n');
		Main.console.setForeground(Color.black);
		/*  JFrame frame = new JFrame(message);
        frame.setSize(200, 100);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JLabel label = new JLabel(message, SwingConstants.CENTER);
        frame.setResizable(false);
        frame.add(label);
        frame.setVisible(true);*/
	}

	public static ExternalMemory getExternalMemory() {
		return externalMemory;
	}

	public static VirtualMachine getVmByIndex(int index) {
		for (VirtualMachine result : vm) {
			if (result.getId() == index) {
				return result;
			}

		}
		return null;

	}
         public static void addProcess(String name) {

        byte[][][] process = new byte[16][16][4];

        try {
            process = externalMemory.fillArray(externalMemory.searchInHDD(name));  

        } catch (IOException ex) {
            Logger.getLogger(RealMachine.class.getName()).log(Level.SEVERE, null, ex);
        }

        memory.loadToSupervisory(process);
        int vmIndex = memory.loadFromSupervisoryToVm();
        if (vmIndex == -2) {
            toConsole("Wrong commands were found");
        } else if (vmIndex == -1) {
            toConsole("Not enough memory");
        } else {
            toConsole("Program has been successfully loaded");
            //  memory.printMemoryContents();
            vm.add(new VirtualMachine(mode, memory.getVmPointerByIndex(vmIndex), vmIndex));

            //vm.get(memory.getVmCount() - 1).work();
            //  System.out.println(vm.get(memory.getVmCount() - 1).toString());
        }
        // memory.supervisorMemory.fillTable();

    }

}
