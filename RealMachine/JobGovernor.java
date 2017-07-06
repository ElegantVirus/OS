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
import static RealMachine.RealMachine.toConsole;
import static RealMachine.RealMachine.vm;
import VirtualMachine.VirtualMachine;
import java.util.ArrayList;

public class JobGovernor extends Process {

    public int vmIndex = -1;
	public static final String[] TEMP = new String[]{" ", "Loader end", "Run program", "supervisor memory", "Read_Bytes end", "supervisor memory", "Write_Bytes end", "supervisor memory",  "Get_hdd end", "supervisor memory",  "Put_hdd end", "supervisor memory", "Open_hdd end", "supervisor memory", "Delete_hdd end", "supervisor memory", "Close_hdd end"};
	

    public JobGovernor(int priority, String id, String status, int pointer) {
        super(priority, id, status, pointer, TEMP);
    }

    private void checkInterrupt(int interrupt) {
        //system interrupt. 1 if get data, 2 if put data and  31 file read, 32 file write, 33 file open, 34 file delete, 35 file close,  4 if halt
        switch (interrupt) {
            case 1:

                this.setPointer(3);
                Planner.blocked.add(this);
                this.setStatus("BLOCKED");
                break;
            case 2:
                this.setPointer(5);
                Planner.blocked.add(this);
                this.setStatus("BLOCKED");
                break;
            case 31:
                this.setPointer(7);
                Planner.blocked.add(this);
                this.setStatus("BLOCKED");
                break;
            case 32:
                this.setPointer(9);
                Planner.blocked.add(this);
                this.setStatus("BLOCKED");
                break;
            case 33:
                this.setPointer(11);
                Planner.blocked.add(this);
                this.setStatus("BLOCKED");
                break;
            case 34://no address
                this.setPointer(13);
                Planner.blocked.add(this);
                this.setStatus("BLOCKED");
                break;
            case 35://no address
                this.setPointer(15);
                Planner.blocked.add(this);
                this.setStatus("BLOCKED");
                break;
            case 4:
                //HALT
                Planner.lineUp();
                break;
            default:
                this.setPointer(2);
                Planner.ready.add(this);
                this.setStatus("READY");
        }

    }

    @Override
    public void run(int pointer) {
        RealMachine.toConsole("vardas " + this.getId() + " pointer " + pointer+" vmindex "+vmIndex+"~~~~~~~~~~~~~~~~~~~~~");
        switch (pointer) {
            case 0:
                //Bloka􀇀i􀅵as, laukia􀅶t „vartotojo atminties“ resurso
                this.setPointer(1);
                Resources.addDynamic("VM memory");
                Planner.blocked.add(this);
                Planner.lineUp();

                break;
            case 1://laukiant loader pabaigos
                vmIndex = Loader.getLastVmIndex();
                if (vmIndex == -2) {
                    toConsole("Wrong commands were found");
                    /**
                     * todo go to halt
                     */
                    
                    break;
                } else if (vmIndex == -1) {
                    toConsole("Not enough memory");
                    break;
                } else {
                    toConsole("Program has been successfully loaded");

                    vm.add(new VirtualMachine(mode, memory.getVmPointerByIndex(vmIndex), vmIndex));
                    this.setPointer(2);

                    Planner.blocked.add(this);
                }
                break;
            case 2:

                RealMachine.getVmByIndex(vmIndex).workInDebug();
                int interrupt = Test.test(vmIndex);
                //system interrupt. 1 if get data, 2 if put data and  31 file read, 32 file write, 33 file open, 34 file delete, 35 file close,  4 if halt
                checkInterrupt(interrupt);
                Planner.lineUp();
                break;

            case 3://get data laukia supervizorines
                this.setPointer(4);
                Resources.addDynamic("Read word");
                Planner.blocked.add(this);
                this.setStatus("BLOCKED");
                Planner.lineUp();
                break;
            case 4://laukia read word end

                //pabaigia getdata;
                //irasau atsakyma i r1
                RealMachine.getVmByIndex(vmIndex).r1.setR(UserMemory.getByteAtAddress(16, 0));
                //atlaisvina supervizorine
                Resources.setStatic("supervisor memory", true);
                this.setPointer(2);
                Planner.ready.add(this);
                this.setStatus("READY");
                Planner.lineUp();
                break;
            case 5://put data laukia supervizorines
                UserMemory.setBytesAtAddress(16, 0, RealMachine.getVmByIndex(vmIndex).getFromAddress((short) RealMachine.getVmByIndex(vmIndex).getLastAddress()));
                this.setPointer(6);
                Resources.addDynamic("Write word");
                Planner.blocked.add(this);
                this.setStatus("BLOCKED");
                Planner.lineUp();
                break;
            case 6://laukia write word end

                //pabaigia putdata;
                //atlaisvina supervizorine
                Resources.setStatic("supervisor memory", true);
                this.setPointer(2);
                Planner.ready.add(this);
                this.setStatus("READY");
                Planner.lineUp();
                break;
            case 7://file read laukia supervizorines

                //irasau i supervizorine
                UserMemory.setBytesAtAddress(15, 0, RealMachine.getVmByIndex(vmIndex).r1.getR());
                Resources.addDynamic("File read");
                this.setPointer(8);
                Planner.blocked.add(this);
                this.setStatus("BLOCKED");
                Planner.lineUp();
                break;
            case 8://laukia gethdd end
                //irasau atsakyma i adresa
                RealMachine.getVmByIndex(vmIndex).putToAddress(UserMemory.getByteAtAddress(15, 1), RealMachine.getVmByIndex(vmIndex).getLastAddress());

                //atlaisvina supervizorine
                Resources.setStatic("supervisor memory", true);
                this.setPointer(2);
                Planner.ready.add(this);
                this.setStatus("READY");
                Planner.lineUp();
                break;
            case 9://file write laukia supervizorines
                //irasau i supervizorine
                UserMemory.setBytesAtAddress(15, 0, RealMachine.getVmByIndex(vmIndex).r1.getR());
                UserMemory.setBytesAtAddress(15, 1, RealMachine.getVmByIndex(vmIndex).getFromAddress((short) RealMachine.getVmByIndex(vmIndex).getLastAddress()));
                this.setPointer(10);
                Resources.addDynamic("File write");
                Planner.blocked.add(this);
                this.setStatus("BLOCKED");
                Planner.lineUp();
                break;
            case 10://laukia puthdd end
                //atlaisvina supervizorine
                Resources.setStatic("supervisor memory", true);
                this.setPointer(2);
                Planner.ready.add(this);
                this.setStatus("READY");
                Planner.lineUp();
                break;
            //open	
            case 11://file open laukia supervizorines
                //irasau i supervizorine
                UserMemory.setBytesAtAddress(15, 0, RealMachine.getVmByIndex(vmIndex).getFromAddress((short) RealMachine.getVmByIndex(vmIndex).getLastAddress()));
                this.setPointer(12);
                Resources.addDynamic("File open");
                Planner.blocked.add(this);
                this.setStatus("BLOCKED");
                Planner.lineUp();
                break;
            case 12://laukia puthdd end
                //irasau atsakyma i r1
                RealMachine.getVmByIndex(vmIndex).r1.setR(UserMemory.getByteAtAddress(15, 0));
                //atlaisvina supervizorine
                Resources.setStatic("supervisor memory", true);
                this.setPointer(2);
                Planner.ready.add(this);
                this.setStatus("READY");
                Planner.lineUp();
                break;

            //delete
            case 13://file delete laukia supervizorines
                //irasau i supervizorine
                UserMemory.setBytesAtAddress(15, 0, RealMachine.getVmByIndex(vmIndex).r1.getR());
                this.setPointer(14);
                Resources.addDynamic("File delete");
                Planner.blocked.add(this);
                this.setStatus("BLOCKED");
                Planner.lineUp();
                break;
            case 14://laukia deletehdd end
                //atlaisvina supervizorine
                Resources.setStatic("supervisor memory", true);
                this.setPointer(2);
                Planner.ready.add(this);
                this.setStatus("READY");
                Planner.lineUp();
                break;
            //close
            case 15://file closelaukia supervizorines
                //irasau i supervizorine
                UserMemory.setBytesAtAddress(15, 0, RealMachine.getVmByIndex(vmIndex).r1.getR());
                this.setPointer(16);
                Resources.addDynamic("File close");
                Planner.blocked.add(this);
                this.setStatus("BLOCKED");
                Planner.lineUp();
                break;
            case 16://laukia closehdd end
                //atlaisvina supervizorine
                Resources.setStatic("supervisor memory", true);
                this.setPointer(2);
                Planner.ready.add(this);
                this.setStatus("READY");
                Planner.lineUp();
                break;

        }
    }

}
