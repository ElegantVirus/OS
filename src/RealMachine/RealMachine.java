package RealMachine;

import Registers.*;
import VirtualMachine.VirtualMachine;

public class RealMachine {

    CommonUseRegisters r1 = new CommonUseRegisters();
    CommonUseRegisters r2 = new CommonUseRegisters();
    Ch_Register ch1 = new Ch_Register();
    Ch_Register ch2 = new Ch_Register();
    Ch_Register ch3 = new Ch_Register();
    Sf_Register sf = new Sf_Register();
    ModeRegister mode = new ModeRegister();
    TI_Register ti = new TI_Register();
    IOI_Register ioi = new IOI_Register();
    IntRegister pi = new IntRegister();
    IntRegister si = new IntRegister();

    public RealMachine() {
        VirtualMachine vm = new VirtualMachine(mode,ti);

    }

    /**
     * Page table block number
     * TAI YRA EILUTES ADRESAS, eilute yra lentele. SUkuri vm, vadinasi, i kazkuri indeksa irasai adresa eilutes
     * 16x16
     * Reikia isskirti adresa vm, kai ja sukuri ir isskiri jai adresa
     */



    ExternalMemory em = new ExternalMemory();
    SupervisorMemory sm = new SupervisorMemory();
    UserMemory um = new UserMemory();
}
