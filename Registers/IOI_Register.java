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

public class IOI_Register {

    private byte ioi;

    public IOI_Register() {
        this.ioi = 0;
    }

    public byte getIoi() {
        return ioi;
    }

    /**
     * Sets input - output interrupt register, depending on which channel is
     * sending the signal
     *
     * @param a which channel is busy
     */
    public void setIoi(int a) {
        if (a == 1) {
            this.ioi = (byte) (ioi | ((byte) 0));
            os.Logger.writeToLog("channel 1 has been set to busy"+'\n');
        }
        if (a == 2) {
            this.ioi = (byte) (ioi | ((byte) 2));
            os.Logger.writeToLog("channel 2 has been set to busy"+'\n');
        }
        if (a == 3) {
            this.ioi = (byte) (ioi | ((byte) 4));
            os.Logger.writeToLog("channel 3 has been set to busy "+'\n');
        }

    }

    /**
     * Logical and should clean a bit specified
     *
     * @param a
     */
    public void cleanIoi(int a) {
        if (a == 1) {
            this.ioi = (byte) (ioi & (1110));
            os.Logger.writeToLog("channel 1 has been set to free"+'\n');
        }
        if (a == 2) {
            this.ioi = (byte) (ioi & (1101));
            os.Logger.writeToLog("channel 2 has been set to free"+'\n');
        }
        if (a == 3) {
            this.ioi = (byte) (ioi & (1011));
            os.Logger.writeToLog("channel 3 has been set to free "+'\n');
        }

    }

    /**
     * Setting to bits at once
     *
     * @param a
     * @param b
     */
    public void setIoi(int a, int b) {
        setIoi(a);
        setIoi(b);
    }

    /**
     * Setting three bits at once
     *
     * @param a
     * @param b
     * @param c
     */
    public void setIoi(int a, int b, int c) {
        setIoi(a);
        setIoi(b);
        setIoi(c);
    }

    /**
     * If u want to clean two bits - set two channels to 0
     *
     * @param a
     * @param b
     */
    public void cleanIoi(int a, int b) {
        cleanIoi(a);
        cleanIoi(b);
    }

    /**
     * If u want to clean 3 bits - set 3 channels to 0
     *
     * @param a
     * @param b
     * @param c
     */
    public void cleanIoi(int a, int b, int c) {
        cleanIoi(a);
        cleanIoi(b);
        cleanIoi(c);
    }

    public int checkBitPos(int position) {
        return (ioi >> position) & 1;
    }

    @Override
    public String toString() {
        String bc = "busy channels :";
        if (checkBitPos(1) == 1) {
            bc = bc + "1 ";
        }
        if (checkBitPos(2) == 1) {
            bc = bc + "2 ";
        }
        if (checkBitPos(3) == 1) {
            bc = bc + "3 ";
        }

        return bc;
    }

}
