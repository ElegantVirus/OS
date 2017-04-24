package Registers;


public class CommonUseRegisters {
    private int r;

    public int getR() {
        return r;
    }

    public void setR(int r1) {
        this.r = r1;
    }

    @Override
    public String toString() {
        return ": "+r ;
    }

}
