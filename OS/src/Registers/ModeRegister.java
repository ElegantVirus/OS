package Registers;


public class ModeRegister {

    private static byte mode;

    public ModeRegister() {
    }

    public static byte getMode() {
        return mode;
    }

    /**
     * If 1, then supervisor mode
     */
    public static void setMode_1() {
        ModeRegister.mode = 1;
    }
    /**
     * If 0, then user mode
     */
    public static void setMode_0() {
        ModeRegister.mode = 0;
    }

    @Override
    public String toString() {
        String temp = String.format("%8s", Integer.toBinaryString(mode & 0xFF)).replace(' ', '0');
        return "ModeRegister : " + temp;
    }

}
