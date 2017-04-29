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

    public static void setIc(short ic1) {
        os.Logger.writeToLog("is register has been set to "+ic1+"\n");
        ic = ic1;
    }

    @Override
    public String toString() {
        return "IC_Register:" + ic;
    }
    
    
}
