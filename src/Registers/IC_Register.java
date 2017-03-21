package Registers;

//instruction counter
public class IC_Register {
    private static short ic;

    public IC_Register() {
        ic = 0000;
    }

    /**
     * Command counter
     *
     * @return count of commands
     */

    public static short getIc() {
        return ic;
    }

    public static void setIc(short ic) {
        ic = ic;
    }
}
