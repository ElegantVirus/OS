package Registers;


public class Sf_Register {
    byte sf;
    //cf of x x   x x x zf


    public Sf_Register() {
        this.sf = (byte) 0;
    }

    public boolean getCf() {
        return (sf & 128) > 0;
    }

    public void setCf(boolean cf) {
        if (cf) {
            sf = (byte) (sf | 128);
        } else {
            sf = (byte) (sf & 127);
        }
    }

    public boolean getOf() {
        return (sf & 64) > 0;
    }

    public void setOf(boolean of) {
        if (of) {
            sf = (byte) (sf | 64);
        } else {
            sf = (byte) (sf & 191);
        }
    }

    public boolean getZf() {
        return (sf & 1) > 0;
    }

    public void setZf(boolean zf) {
        if (zf) {
            sf = (byte) (sf | 1);
        } else {
            sf = (byte) (sf & 255);
        }
    }

    @Override
    public String toString() {
        String temp = String.format("%8s", Integer.toBinaryString(sf & 0xFF)).replace(' ', '0');
        return "Sf register :"+temp;
    }

    /*public void pr() {
        String temp = "00000000" + Integer.toBinaryString(sf);
        System.out.println(temp.substring(temp.length() - 8));
    }*/


}