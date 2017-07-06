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
        os.Logger.writeToLog("sf is set to"+sf+'\n');
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
        os.Logger.writeToLog("of is set to"+of+'\n');
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
        os.Logger.writeToLog("zf is set to"+zf+'\n');
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