package Registers;


public class IntRegister {
    /**
     * si - system interrupt. 1 if get data, 2 if put data and 3 if halt
     */
    private short pi = 0,si = 0;

    public IntRegister() {
        this.pi = 0;
        this.si = 0;
    }

    public short getPi() {
        return pi;
    }

    /**
     * pi - program interrupt. 1 if memory protection
     */
    public void setPi_1() {
        pi = 1;
    }

    /**
     * pi - program interrupt. 2 if operation code doesn't exist
     */
    public void setPi_2() {
        pi = 2;
    }

    public short getSi() {
        return si;
    }

    /**
     * si - system interrupt.1 if get data
     */
    public void setSi_1() {
        si = 1;
    }

    /**
     *  si - system interrupt.2 if put data
     */
    public void setSi_2() {
        si = 2;
    }

    /**
     * si - system interrupt.3 if halt
     */
    public  void setSi_3() {
        si = 3;
    }
    public  void clearSi(){
        si = 0;
    }
    public  void clearPi(){
        pi = 0;
    }
}
