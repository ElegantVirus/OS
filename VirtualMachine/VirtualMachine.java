/**
*Operaciniu sistemu projektas
*Autores :
*Evelina Bujyte
*Anastasija Kiseliova
*Matematine informatika
*3 kursas
*2017
**/
package VirtualMachine;

import Registers.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import os.Logger;

public class VirtualMachine {

	public Sf_Register sf = new Sf_Register();
	public IC_Register ic = new IC_Register();
	public CommonUseRegisters r1 = new CommonUseRegisters();
	public CommonUseRegisters r2 = new CommonUseRegisters();
	PageTable pageTable;
	int index;
	//id is used to identificate which vm it is

	ModeRegister mode = new ModeRegister();
	int ptr;
	private short lastAddress;

	public short getLastAddress() {
		return lastAddress;
	}

	public VirtualMachine(ModeRegister mode, int ptr, int id) {

		this.mode = mode;
		this.ptr = ptr;
		ModeRegister.setMode_0();
		this.pageTable = new PageTable(ptr);
		this.pageTable.increaseCs();
		this.ic.setIc((short) 129);
		this.lastAddress = 240;
		this.index = id;

	}

	public int getId() {
		return index;
	}

	private VirtualMachine() {

	}

	public String vmMemoryToString() {
		char[] command = new char[4];
		String memory = "";
		for (short i = 0; i < 256; i++) {
			command = pageTable.getCharArrayAtAddress(i);
			memory += String.valueOf(command) + " ";
			if (((i % 16) == 0) && (i != 0)) {
				memory += "\n";
			}

		}
		return memory;
	}

	@Override
	public String toString() {
		return "VirtualMachine{"
				+ "r1=" + r1.getR()
				+ ", r2=" + r2.getR()
				+ ", ic=" + ic.getIc()
				+ ", sf=" + sf.toString()
				+ '}';
	}

	public void work() {
		boolean breaker = true;
		char[] command = new char[4];

		while (breaker) {
			command = pageTable.getCharArrayAtAddress(ic.getIc());

			String cmd = String.valueOf(command);
			System.out.println(cmd + " " + ic.getIc());
			if ((cmd.charAt(0) == 'H') && (cmd.charAt(1) == 'A')) {//HALT
				halt();
				// RealMachine.Test.test(getId());
				breaker = false;
				break;
			} else {
				interpretACommand(cmd);
			}

		}
	}

	public String[] workInDebug() {

		String[] ar = new String[2];

		String command = String.valueOf(pageTable.getCharArrayAtAddress(ic.getIc()));
		interpretACommand(command);

		String nextCommand = String.valueOf(pageTable.getCharArrayAtAddress(ic.getIc()));

		ar[0] = command;
		ar[1] = nextCommand;

		return ar;
	}

	//command interpretation
	public void interpretACommand(String command) {

		if ((command.charAt(0) == 'H') && (command.charAt(1) == 'A')) {//HALT
			halt();
			//RealMachine.Test.test(getId());
		} else {
			int x1 = charToInt(command.charAt(2));
			int x2 = charToInt(command.charAt(3));
			short address = (short) (x1 * 16 + x2);
			String tempCommand = new StringBuilder().append(command.charAt(0)).append(command.charAt(1)).toString();//should simplify it ðŸ˜€
			switch (tempCommand) {
				case "LR": {//load register
					lr(address);
					break;
				}
				case "SR": {//save register
					sr(address);
					break;
				}
				case "RR": {// swaps R1 with R2
					rr();
					break;
				}
				case "AD": {//add
					ad(address);
					break;
				}
				case "SB": {//subtract
					sb(address);
					break;
				}
				case "MU": {//multi
					mu(address);
					break;
				}
				case "DI": {//div
					di(address);
					break;
				}
				case "CR": {//compare
					cr(address);
					break;
				}
				case "AN": {//and
					and();
					break;
				}
				case "XO": {//xor
					xor();
					break;
				}
				case "OR": {//or
					or();
					break;
				}
				case "NO": {//not
					not();
					break;
				}
				case "JU": {//jump
					ju(address);
					break;
				}
				case "JG": {//jump if more
					jg(address);
					break;
				}
				case "JE": {//jump if equal
					je(address);
					break;
				}
				case "JL": {//jump if lower
					jl(address);
					break;
				}
				case "JN": {//jump if not equal
					jn(address);
					break;
				}
				case "FR": {//file read
					fr(address);
					break;
				}
				case "FW": {//file write
					fw(address);
					break;
				}
				case "FD": {//file write
					fd();
					break;
				}
				case "FC": {//file write
					fc();
					break;
				}
				case "FO": {//file write
					fo(address);
					break;
				}

				case "GD": {//get data
					gd(address);

					break;
				}
				case "PD": {//put data
					pd(address);
					break;
				}
				default:
					//ismest koki exeptiona kad komada nerasta
					RealMachine.RealMachine.toConsole("command not found");
					pageTable.increaseCs();
					//   System.out.println("command not found");
					break;
			}
			//  RealMachine.Test.test(getId());
		}
	}

	private int charToInt(char ch) {
		//TO DO: prideti apribojimus kad ne raide n shit
		int temp = (char) (ch - '0');
		return temp;
	}

	private int getIntFromAddress(short address) {
		char[] digits = pageTable.getCharArrayAtAddress(address);
		int res = Integer.parseInt(new String(digits));
		RealMachine.RealMachine.toConsole("get int from memory : " + res);
		//    System.out.println("get int from array : " + res);
		return res;
	}
	public byte [] getFromAddress(short address) 
	{
		return pageTable.getAtAdress(address);
	}

	public void putToAddress(byte[] bytes, short address) {
		int value = 1234;
		char[] chars = String.valueOf(value).toCharArray();
		pageTable.setBytesAtAddress(address, bytes);

	}

	private void putToAddress(String str, short address) {
		if (str != null) {
			if (str.equals("")) {
				str = "    ";

			} else if (str.length() == 1) {
				str += "   ";

			} else if (str.length() == 2) {
				str += "  ";

			} else if (str.length() == 3) {
				str += "  ";

			} else {
				str = str.substring(0, 4);
			}
		} else {
			str = "    ";
		}
		int value = 1234;
		byte[] bytes = str.getBytes();
		pageTable.setBytesAtAddress(address, bytes);

	}

	private byte[] intToBytes(final int i) {
		ByteBuffer bb = ByteBuffer.allocate(4);
		bb.putInt(i);
		return bb.array();
	}

	private void putToAddress(int number, short address) {
		byte[] temp = new byte[4];
		temp = intToBytes(number);
		putToAddress(temp, address);
	}

	/**
	 ******************************************************************************************VIRTUAL
	 * MACHINE COMMANDS
	 */
	//commands
	//LR â€“ Load Register â€“ iÅ¡ atminties baito x1x2 persiunÄia Ä¯ registrÄ… R1:
	//LR x1x2  => R1:=[x1x2];
	private void lr(short address) {
		r1.setR(getIntFromAddress(address));

		short a = ic.getIc();
		ic.setIc(++a);
		//pageTable.increaseCs();
		Logger.writeToLog("LR" + address + " ");
		TI_Register.decreaseTi();
	}

	//SR â€“ Save Register â€“ iÅ¡ registro R1 persiunÄia Ä¯ atminties baitÄ… x1x2:
	//SR x1x2  => [x1x2]:=R1;
	private void sr(short address) {

		putToAddress(r1.getRInt(), address);

		short a = ic.getIc();
		ic.setIc(++a);
		//pageTable.increaseCs();
		Logger.writeToLog("SR" + address + " ");
		TI_Register.decreaseTi();

	}

	//RR â€“ sukeiÄia registro R1 ir R2 reikÅ¡mes:
	//RR ðŸ˜Š R:=R1+R2, R2=R1-R2, R1=R1-R2;
	private void rr() {
		int temp;
		temp = r1.getRInt();
		r1.setR(r2.getR());
		r2.setR(temp);

		short a = ic.getIc();
		ic.setIc(++a);
		//pageTable.increaseCs();
		Logger.writeToLog("RR" + " ");
		TI_Register.decreaseTi();
	}

	//ARITMETINES
	//AD â€“ suma â€“ prie esamos registro R1 reikÅ¡mÄ—s prideda reikÅ¡mÄ™ esanÄiÄ… x1x2 atminties baite, rezultatas
	//patalpinamas registre R1:      AD x1x2 => R1:=R1+[x1x2];
	private void ad(short address) {
		int temp = getIntFromAddress(address);
		if ((r1.getRInt() + temp) > Integer.MAX_VALUE) {
			sf.setCf(true);
		}
		r1.setR(r1.getRInt() + temp);
		if (r1.getRInt() == 0) {
			sf.setZf(true);
		}

		short a = ic.getIc();
		ic.setIc(++a);
		//pageTable.increaseCs();
		Logger.writeToLog("AD" + address + " ");
		TI_Register.decreaseTi();
	}

	//SB â€“ atimtis â€“ iÅ¡ esamos registro R1 reikÅ¡mÄ—s atimama reikÅ¡mÄ— esanti x1x2 atminties baite, rezultatas
	//patalpinamas registre R1:      SB x1x2 => R1:=R1-[x1x2];
	private void sb(short address) {
		int temp = getIntFromAddress(address);
		if (temp > r1.getRInt()) {
			sf.setCf(true);
		}
		if (temp == r1.getRInt()) {
			sf.setZf(true);
		}
		//status flag
		r1.setR(r1.getRInt() - 1);

		short a = ic.getIc();
		ic.setIc(++a);
		//pageTable.increaseCs();
		Logger.writeToLog("SB" + address + " ");
		TI_Register.decreaseTi();
	}

	//MU -multiplication R1:=R1 *[x1x2];
	private void mu(short address) {
		int temp = getIntFromAddress(address);
		//status flag
		if ((r1.getRInt() * temp) > Integer.MAX_VALUE) {
			sf.setCf(true);
		}
		r1.setR(r1.getRInt() * temp);

		short a = ic.getIc();
		ic.setIc(++a);
		//pageTable.increaseCs();
		Logger.writeToLog("MU" + address + " ");
		TI_Register.decreaseTi();
	}

	//DI - division   R2:=R1 % [x1x2];-liekana R1:=R1 *[x1x2];
	private void di(short address) {
		int temp = getIntFromAddress(address);
		//status flag
		r2.setR(r1.getRInt() % temp);
		r1.setR(r1.getRInt() / temp);

		short a = ic.getIc();
		ic.setIc(++a);
		//pageTable.increaseCs();
		Logger.writeToLog("DI" + address + " ");
		TI_Register.decreaseTi();
	}

	//PALYGINIMO
	//CR â€“ palyginimas â€“ esamÄ… registro R1 reikÅ¡mÄ— yra lyginama su reikÅ¡me esanÄiÄ… x1x2 atminties baite,
	//rezultatas patalpinamas registre C:       CR x1x2 =>
	//          if R>[x1x2] then CF:=FALSE, ZF:= FALSE;
	//          if R=[x1x2] then ZF:=TRUE;
	//          if R<[x1x2] then CF:=TRUE;
	private void cr(short address) {
		int temp = getIntFromAddress(address);
		if (r1.getRInt() == temp) {
			sf.setZf(true);
			sf.setCf(false);
		} else if (r1.getRInt() > temp) {
			sf.setZf(false);
			sf.setCf(false);
		} else { //r1<temp
			sf.setZf(false);
			sf.setCf(true);
		}

		short a = ic.getIc();
		ic.setIc(++a);
		//pageTable.increaseCs();
		Logger.writeToLog("CR" + address + " ");
		TI_Register.decreaseTi();
	}

	//LOGINES
	//AND
	private void and() {
		r1.setR(r1.getRInt() & r2.getRInt());
		if (r1.getRInt() == 0) {
			sf.setZf(true);
		}

		short a = ic.getIc();
		ic.setIc(++a);
		//pageTable.increaseCs();
		Logger.writeToLog("AND" + " ");
		TI_Register.decreaseTi();
	}

	//XOR
	private void xor() {
		r1.setR(r1.getRInt() ^ r2.getRInt());
		if (r1.getRInt() == 0) {
			sf.setZf(true);
		}

		short a = ic.getIc();
		ic.setIc(++a);
		//pageTable.increaseCs();
		Logger.writeToLog("XOR" + " ");
		TI_Register.decreaseTi();
	}

	//OR
	private void or() {
		r1.setR(r1.getRInt() | r2.getRInt());
		if (r1.getRInt() == 0) {
			sf.setZf(true);
		}

		short a = ic.getIc();
		ic.setIc(++a);
		//pageTable.increaseCs();
		Logger.writeToLog("OR" + " ");
		TI_Register.decreaseTi();
	}

	//NOT
	private void not() {
		r1.setR(~r1.getRInt());
		if (r1.getRInt() == 0) {
			sf.setZf(true);
		}

		short a = ic.getIc();
		ic.setIc(++a);
		//pageTable.increaseCs();
		Logger.writeToLog("NOT" + " ");
		TI_Register.decreaseTi();
	}

	//VALDYMO PERDAVIMO (JUMP'AI)//<--------------------------------------------------------------------------NEEDS EDIT
	//JU â€“ besÄ…lyginio valdymo perdavimas â€“ valdymas perduodamas adresu 16*x1+x2:
	//JU x1x2 => IC:=16*x1+x2;
	private void ju(short address) {

		//pageTable.setCs(address);
		ic.setIc(address);
		Logger.writeToLog("JU" + address + " ");
		TI_Register.decreaseTi();

	}

	// JM â€“ sÄ…lyginio valdymo perdavimas (jeigu daugiau) â€“ valdymas perduodamas jeigu C=0, valdymas perduodamas adresu 16*x1+x2:
	// Jm x1x2 ðŸ˜Š If C=0 then Ds:= 16*x1+x2;
	private void jg(short address) {
		//kaip su sf reikia apsibrezt
		if (sf.getCf() == false && sf.getZf() == false) {
			ic.setIc(address);
		}

		Logger.writeToLog("JM" + address + " ");
		TI_Register.decreaseTi();

	}

	// JE â€“ sÄ…lyginio valdymo perdavimas (jeigu lygu) â€“ valdymas perduodamas jeigu C=1, valdymas perduodamas adresu 16*x1+x2:
	//JE x1x2 ðŸ˜Š  If C=1 then IC:= 16*x1+x2;
	private void je(short address) {
		if (sf.getZf() == true) {
			ic.setIc(address);
		}

		Logger.writeToLog("JE" + address + " ");
		TI_Register.decreaseTi();
	}

	private void jn(short address) {
		if (sf.getZf() != true) {
			ic.setIc(address);
		}

		Logger.writeToLog("JE" + address + " ");
		TI_Register.decreaseTi();
	}

	//JL â€“ sÄ…lyginio valdymo perdavimas (jeigu maÅ¾iau) â€“ valdymas perduodamas jeigu C=2, valdymas perduodamas adresu 16*x1+x2:
	//JL x1x2 ðŸ˜Š  If C=2 then IC:= 16*x1+x2;
	private void jl(short address) {
		if (sf.getCf() == true) {
			ic.setIc(address);
		}

		Logger.writeToLog("JL" + address + " ");
		TI_Register.decreaseTi();

	}

	//PABAIGOS
	// HALT â€“ programos pabaigos komanda.
	private void halt() {
		RealMachine.RealMachine.si.set_4();
		//???

		//short a = IC_Register.getIc();///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		//IC_Register.setIc(++a);
		//ic.setIc((short) 129);
		Logger.writeToLog("HALT" + " ");
		TI_Register.decreaseTi();
	}

	private static final int MAX_FILES = 6;
	//IVEDIMO /ISVEDIMO
	//FR - file read
	//adress nurodo vieta i kuria rasysim vm'e, r2 is kur rasysim faile, r1 failo handleris

	private void fr(short address) {
		lastAddress = address;
		RealMachine.RealMachine.si.set_31();
		RealMachine.RealMachine.ioi.setIoi(3);
		short a = ic.getIc();
		ic.setIc(++a);
		Logger.writeToLog("FR" + address + " ");
		TI_Register.decreaseTi();
		/*	byte[] temp = r1.getR();
		try {
			RealMachine.RealMachine.externalMemory.fileReadAtPos(temp);
		} catch (IOException ex) {
			RealMachine.RealMachine.pi.set_2();
			java.util.logging.Logger.getLogger(VirtualMachine.class.getName()).log(Level.SEVERE, null, ex);
		}
		//pageTable.increaseCs();*/
	}
	//FW - file write
	//adress nurodo vieta is kurios rasysim, r2 kur rasysim faile, r1 failo handleris

	private void fw(short address) {
		lastAddress = address;
		RealMachine.RealMachine.si.set_32();
		RealMachine.RealMachine.ioi.setIoi(3);
		short a = ic.getIc();
		ic.setIc(++a);
		//pageTable.increaseCs();
		Logger.writeToLog("FW" + address + " ");
		TI_Register.decreaseTi();
		/*
        byte[] temp = r1.getR();
        byte[] t = pageTable.getAtAdress(address);

        try {

            RealMachine.RealMachine.externalMemory.fileRewriteAtPos(temp, t);
        } catch (IOException ex) {
            RealMachine.RealMachine.pi.set_2();
            java.util.logging.Logger.getLogger(VirtualMachine.class.getName()).log(Level.SEVERE, null, ex);
        }
		 */
	}
	//FO - file open
	//adresas turi failo pavadinima failo handleris grazinamas i r1

	private void fo(short address) {
		lastAddress = address;
		RealMachine.RealMachine.si.set_33();
		RealMachine.RealMachine.ioi.setIoi(3);
		short a = ic.getIc();
		ic.setIc(++a);
		Logger.writeToLog("FO" + address + " ");
		TI_Register.decreaseTi();

		/*    byte[] temp = pageTable.getAtAdress(address);
        if (!RealMachine.RealMachine.externalMemory.fileOpen(temp)) {
            RealMachine.RealMachine.pi.set_2();
        }
        r1.setR(temp);
		 */
	}
	//FD file delete
	//istrina faila kurio handleris yra r1

	private void fd() {
		RealMachine.RealMachine.si.set_34();
		RealMachine.RealMachine.ioi.setIoi(3);
		short a = ic.getIc();
		ic.setIc(++a);
		Logger.writeToLog("FD" + " ");
		TI_Register.decreaseTi();
		/*    byte[] temp = r1.getR();
        try {
            RealMachine.RealMachine.externalMemory.deleteFile(temp);
        } catch (IOException ex) {
            RealMachine.RealMachine.pi.set_2();
            java.util.logging.Logger.getLogger(VirtualMachine.class.getName()).log(Level.SEVERE, null, ex);
        }
		 */
	}
	//FC file close
	//uzdaromas failas kurio handleris yra r1

	private void fc() {
		RealMachine.RealMachine.si.set_35();
		RealMachine.RealMachine.ioi.setIoi(3);
		short a = ic.getIc();
		ic.setIc(++a);
		Logger.writeToLog("FC" + " ");
		TI_Register.decreaseTi();
		/*   byte[] temp = r1.getR();
        RealMachine.RealMachine.si.set_3();

        if (!RealMachine.RealMachine.externalMemory.fileClose(temp)) {
            RealMachine.RealMachine.pi.set_2();
        }
		 */
	}

	// GD â€“ Ä¯vedimas â€“ iÅ¡ Ä¯vedimo srauto paima 1 Å¾odÅ¾io srautÄ… ir jÄ¯ Ä¯veda Ä¯ atmintÄ¯ pradedant atminties baitu 16*x1+x2:
	// GD x1x2
	private void gd(short address) {
		lastAddress = address;
		RealMachine.RealMachine.ioi.setIoi(2);

		// * TODO:	int x; read( x ); putIntToAddress(x, address);
		String str = "";
		os.Main.setVi();

		str = os.Main.getText();
		RealMachine.RealMachine.si.set_1();

		//System.out.println("PUSH :" + os.Main.getPush());
		short a = ic.getIc();
		ic.setIc(++a);
		putToAddress(str, address);
		Logger.writeToLog("GD" + address + " ");
		TI_Register.decreaseTi();

		os.Main.stopVi();
	}

	// PD â€“ iÅ¡vedimas â€“ iÅ¡ atminties, pradedant atminties baitu 16*x1+x2 paima 1 Å¾odÅ¾io srautÄ… ir jÄ¯ iÅ¡veda Ä¯ ekranÄ…:
	//PD x1x2
	private void pd(short address) {
		lastAddress = address;
		RealMachine.RealMachine.ioi.setIoi(1);
		// System.out.println(getIntFromAddress(address));
		RealMachine.RealMachine.toConsole(String.valueOf(pageTable.getCharArrayAtAddress(address)));
		RealMachine.RealMachine.si.set_2();

		short a = ic.getIc();
		ic.setIc(++a);
		//pageTable.increaseCs();
		Logger.writeToLog("PD" + address + " ");
		TI_Register.decreaseTi();
	}


}
