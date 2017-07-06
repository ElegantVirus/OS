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

//instruction counter

public class IC_Register {
    private short ic;

    public IC_Register() {
        ic = 0000;
    }

    /**
     * Command counter
     *
     * @return count of commands
     */

    public short getIc() {
        return ic;
    }

    public void setIc(short ic1) {
        os.Logger.writeToLog("ic register has been set to "+ic1+"\n");
        ic = ic1;
    }

    @Override
    public String toString() {
        return " " + ic;
    }
    
    
}
