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

import Registers.TI_Register;


public class Test {

	// pi - program interrupt. 1 if memory protection,2 if some exception command not found or smth
	// si - system interrupt. 1 if get data, 2 if put data and  31 file read, 32 file write, 33 file open, 34 file delete, 35 file close,  4 if halt
	// tint - 10 timer interrupt
	private static int vmId = 0;

	public static int test(int vmIndex) {
		vmId = vmIndex;
		return test();
	}

	public static int test() {
		if ((RealMachine.si.getReg() != 0) || (RealMachine.tint.getReg() != 0) || (RealMachine.pi.getReg() != 0)) {
			return testNow();

		} else {
			return 0;
		}
	}

	public static int testNow() {

		int tint = RealMachine.tint.getReg();
		int pi = RealMachine.pi.getReg();
		int si = RealMachine.si.getReg();
		switch (si) {
			case 1: {//getdata
				//System.out.println("GET DATA INTERRUPT");
				RealMachine.toConsole("GET DATA INTERRUPT");
				RealMachine.si.set_0();
			//	RealMachine.getVmByIndex(vmId).getData();
				break;
			}
			case 2: {//putdata
				//System.out.println("PUT DATA INTERRUPT");
				RealMachine.toConsole("PUT DATA INTERRUPT");
				RealMachine.si.set_0();
				//RealMachine.getVmByIndex(vmId).putData();
				break;
			}
			case 31: {//fileread
				//System.out.println("FILES INTERRUPT");
				RealMachine.toConsole("FILE READ INTERRUPT");
				RealMachine.si.set_0();
			//	RealMachine.getVmByIndex(vmId).useFiles();
				break;
			}
			case 32: {//filewrite
				//System.out.println("FILES INTERRUPT");
				RealMachine.toConsole("FILE WRITE INTERRUPT");
				RealMachine.si.set_0();
			//	RealMachine.getVmByIndex(vmId).useFiles();
				break;
			}
			case 33: {//fileopen
				//System.out.println("FILES INTERRUPT");
				RealMachine.toConsole("FILE OPEN INTERRUPT");
				RealMachine.si.set_0();
			//	RealMachine.getVmByIndex(vmId).useFiles();
				break;
			}
			case 34: {//filedelete
				//System.out.println("FILES INTERRUPT");
				RealMachine.toConsole("FILE DELETE INTERRUPT");
				RealMachine.si.set_0();
			//	RealMachine.getVmByIndex(vmId).useFiles();
				break;
			}
			case 35: {//fileclose
				//System.out.println("FILES INTERRUPT");
				RealMachine.toConsole("FILE CLOSE INTERRUPT");
				RealMachine.si.set_0();
			//	RealMachine.getVmByIndex(vmId).useFiles();
				break;
			}
			case 4: {//halt
				//System.out.println("HALT INTERRUPT");
				RealMachine.toConsole("HALT INTERRUPT");
				RealMachine.si.set_0();
				RealMachine.removeProcess(vmId);
				break;
			}
			default:
				break;
		}
		switch (pi) {
			case 1: {//memory protection
				//System.out.println("MEMORY PROTECTION INTERRUPT");
				RealMachine.toConsole("MEMORY PROTECTION INTERRUPT");

				RealMachine.pi.set_0();
				break;
			}
			case 2: {//program code doesn exist
				//System.out.println("BAD INTERRUPT");
				RealMachine.toConsole("BAD INTERRUPT");

				RealMachine.pi.set_0();
				break;
			}
			default:
				break;
		}
		switch (tint) {
			case 10: {
				//System.out.println("TIMER INTERRUPT");
				RealMachine.toConsole("TIMER INTERRUPT");
				TI_Register.setTi((short) 10);
				RealMachine.tint.set_0();
				break;

			}
			default:
				break;

		}
		return si;
	}

}
