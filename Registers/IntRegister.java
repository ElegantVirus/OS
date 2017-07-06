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

public class IntRegister {

    /**
     * pi - program interrupt. 1 if memory protection,2 if operation code si -
     * system interrupt. 1 if get data, 2 if put data and 31 file read, 32 file
     * write, 33 file open, 34 file delete, 35 file close, 4 if halt 10-timer
     * interrupt
     */
    private short reg;

    public IntRegister() {
        this.reg = 0;
    }

    public void set_0() {
        os.Logger.writeToLog("INTERRUPT UNTRIGGERED - 0" + '\n');
        reg = 0;
    }

    public void set_1() {
        os.Logger.writeToLog("INTERRUPT TRIGGERED - 1" + '\n');
        reg = 1;
    }

    public void set_2() {
        os.Logger.writeToLog("INTERRUPT TRIGGERED - 2" + '\n');
        reg = 2;
    }

    public void set_31() {
        os.Logger.writeToLog("INTERRUPT TRIGGERED - 31" + '\n');
        reg = 31;
    }

    public void set_32() {
        os.Logger.writeToLog("INTERRUPT TRIGGERED - 32" + '\n');
        reg = 32;
    }

    public void set_33() {
        os.Logger.writeToLog("INTERRUPT TRIGGERED - 33" + '\n');
        reg = 33;
    }

    public void set_34() {
        os.Logger.writeToLog("INTERRUPT TRIGGERED - 34" + '\n');
        reg = 34;
    }

    public void set_35() {
        os.Logger.writeToLog("INTERRUPT TRIGGERED - 35" + '\n');
        reg = 35;
    }

    public void set_4() {
        os.Logger.writeToLog("INTERRUPT TRIGGERED - 4" + '\n');
        reg = 4;
    }

    public void set_10() {
        os.Logger.writeToLog("INTERRUPT TRIGGERED - 10" + '\n');
        reg = 10;
    }

    public short getReg() {
        return reg;
    }

    public void clearReg() {
        os.Logger.writeToLog("INTERRUPT REGISTER CLEARED" + '\n');
        reg = 0;
    }

    @Override
    public String toString() {
        return ": " + reg;
    }

}
