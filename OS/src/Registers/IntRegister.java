package Registers;

public class IntRegister {

    /**
     * pi - program interrupt. 1 if memory protection,2 if operation code
     * doesn't exist si - system interrupt. 1 if get data, 2 if put data and 3
     * if files, 4 if halt
     */
    private short reg;

    public IntRegister() {
        this.reg = 0;
    }

    public void set_1() {
        os.Logger.writeToLog("INTERRUPT TRIGGERED - 1"+'\n');
        reg = 1;
    }

    public void set_2() {
        os.Logger.writeToLog("INTERRUPT TRIGGERED - 2"+'\n');
        reg = 2;
    }

    public void set_3() {
        os.Logger.writeToLog("INTERRUPT TRIGGERED - 3"+'\n');
        reg = 3;
    }

    public void set_4() {
        os.Logger.writeToLog("INTERRUPT TRIGGERED - 4"+'\n');
        reg = 4;
    }

    public short getReg() {
        return reg;
    }

    public void clearReg() {
        os.Logger.writeToLog("INTERRUPT REGISTER CLEARED"+'\n');
        reg = 0;
    }

    @Override
    public String toString() {
        return ": "+ reg;
    }

}
