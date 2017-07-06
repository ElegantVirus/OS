/**
*Operaciniu sistemu projektas
*Autores :
*Evelina Bujyte
*Anastasija Kiseliova
*Matematine informatika
*3 kursas
*2017
**/
package Registers;

public class TI_Register {

	private static short ti;
	private static short tiHalt;

	public TI_Register() {
		ti = 10;
		tiHalt =100;
	}

	public static short getTi() {
		return ti;
	}

	public static void setTi(short ti) {
		TI_Register.ti = ti;
		os.Logger.writeToLog("timer has been set to" + ti + '\n');
	}

	public static void decreaseTi() {
		TI_Register.ti = --ti;
		TI_Register.tiHalt= --tiHalt;
		os.Logger.writeToLog("timer has been set to" + ti + '\n');
		if (TI_Register.ti == 0) {
			RealMachine.RealMachine.tint.set_10();
		}
		if (TI_Register.tiHalt == 0) {
			RealMachine.RealMachine.si.set_4();
		}

	}

	@Override
	public String toString() {
		return " " + (int) ti;
	}

}
