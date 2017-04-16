package Registers;


public class TI_Register {

    private static short ti;

    public TI_Register() {
        ti = 0;
    }

    public static short getTi() {
        return ti;
    }

    public static void setTi(short ti) {
        TI_Register.ti = ti;
    }
}
