package RealMachine;

import Registers.*;
import VirtualMachine.VirtualMachine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
     * ptr nesikeičia !!!
     * nuėmiau ptr iš atminties
     */

    ExternalMemory externalMemory;
    UserMemory memory ;

    public RealMachine() {
        //generating a singleton object
        externalMemory = new ExternalMemory();
        ptr = UserMemory.getPtr();
        memory = new UserMemory();
        vm = new ArrayList<VirtualMachine>();



        try {
            //if you want it to work with no program previously loaded, u gotta erase memory first -> fill blocks with zeros

            externalMemory.eraseMemory();
            String program = "DATA-10000200080REZULTATASYRA:00CODELR01AD02HALT";
            externalMemory.writeToDisk("Pr10", program);
            //Vm size

            //process = externalMemory.fillArray(externalMemory.searchInHDD("Pr10"));
            //perkeliau i add process

        } catch (IOException e) {
            e.printStackTrace();
        }


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

    public void addProcess(String name){
        byte[][][] process = new byte[16][16][4];
        try {
            process = externalMemory.fillArray(externalMemory.searchInHDD(name));
        }
        catch (IOException e){
            e.printStackTrace();
        }
        memory.loadToSupervisory(process);
        int vmPtr = memory.loadFromSupervisoryToVm();
        if (vmPtr == -2){
            System.out.println("Wrong commands were found");
        }
        else if (vmPtr == -1){
            System.out.println("Not enough memory");
        }
        else{
            memory.printMemoryContents();
            vm.add (memory.getVmCount()-1,new VirtualMachine(mode, ti, vmPtr));
            vm.get(memory.getVmCount()-1).work();
            System.out.println(vm.get(memory.getVmCount()-1).toString());


        }
       // memory.supervisorMemory.fillTable();


    }



}

