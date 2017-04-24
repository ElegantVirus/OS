package Registers;


public class CommonUseRegisters {
    private int r;

    public int getR() {
        return r;
    }

    public void setR(int r1) {
        os.Logger.writeToLog("common use register has been set to "+r1+"\n");
        this.r = r1;
    }

    @Override
    public String toString() {
        return ": "+r ;
    }

}
