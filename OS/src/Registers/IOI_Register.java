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
     * Sets input - output interrupt register, depending on which channel is sending the signal
     * @param a which channel is busy
     */
    public void setIoi(int a) {
        if(a == 1)
            this.ioi=(byte)(ioi|((byte)1));
        if(a==2)
            this.ioi=(byte)(ioi|((byte)2));
        if(a==3)
            this.ioi=(byte)(ioi|((byte)4));

    }
    /**
     * If couple of channels are busy, it adds their values
     */
    public void setIoi(int a, int b) {
        setIoi(a);
        setIoi(b);
    }
    public void setIoi(int a, int b, int c) {
        setIoi(a);
        setIoi(b);
        setIoi(c);
    }
    public void valyk(){
        this.ioi = 0;
    }
}
