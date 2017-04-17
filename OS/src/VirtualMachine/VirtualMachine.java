package VirtualMachine;

import Registers.*;
import java.nio.ByteBuffer;

public class VirtualMachine {

    public static Sf_Register sf = new Sf_Register();
    public static IC_Register ic = new IC_Register();
    public static CommonUseRegisters r1 = new CommonUseRegisters();
    public static CommonUseRegisters r2 = new CommonUseRegisters();
    PageTable pageTable;

    ModeRegister mode = new ModeRegister();
    TI_Register ti = new TI_Register();
    int ptr;

    public VirtualMachine(ModeRegister mode, TI_Register ti, int ptr) {

        this.mode = mode;
        this.ptr = ptr;
        this.ti = ti;
        ModeRegister.setMode_0();
        this.pageTable = new PageTable(ptr);

    }

    private VirtualMachine() {

    }

    public String vmMemoryToString() {
        char[] command = new char[4];
        String memory = "";
        for (short i = 0; i < 256; i++) {
            command = pageTable.getCharArrayAtAddress(i);
            memory += String.valueOf(command) + " ";
            if (((i % 16) == 0) && (i != 0)) {
                memory += "\n";
            }

        }
        return memory;
    }

    @Override
    public String toString() {
        return "VirtualMachine{"
                + "r1=" + r1.getR()
                + ", r2=" + r2.getR()
                + ", ic=" + ic.getIc()
                + ", sf=" + sf.toString()
                + '}';
    }

    public void work() {

        pageTable.increaseCs();
        char[] command = new char[4];
        /**
         * eit tolyn per datasegmenta ir vykdyt po viena kol nesutiksim halt
         */
        for (; pageTable.getCs() < 256; pageTable.increaseCs()) {
            command = pageTable.getCharArrayAtAddress(pageTable.getCs());
            String cmd = String.valueOf(command);
            if ((cmd.charAt(0) == 'H') && (cmd.charAt(1) == 'A')) {//HALT

                /**
                 * todo halt stuff
                 */
                halt();
                break;
            } else {
                interpretACommand(cmd);
            }
        }
    }

    //command interpretation
    public void interpretACommand(String command) {

        if ((command.charAt(0) == 'H') && (command.charAt(1) == 'A')) {//HALT

        } else {
            int x1 = charToInt(command.charAt(2));
            int x2 = charToInt(command.charAt(3));
            short address = (short) (x1 * 16 + x2);
            String tempCommand = new StringBuilder().append(command.charAt(0)).append(command.charAt(1)).toString();//should simplify it :D
            switch (tempCommand) {
                case "LR": {//load register
                    lr(address);
                    break;
                }
                case "SR": {//save register
                    sr(address);
                    break;
                }
                case "RR": {// swaps R1 with R2
                    rr();
                    break;
                }
                case "AD": {//add
                    ad(address);
                    break;
                }
                case "SB": {//subtract
                    sb(address);
                    break;
                }
                case "MU": {//multi
                    mu(address);
                    break;
                }
                case "DI": {//div
                    di(address);
                    break;
                }
                case "CR": {//compare
                    cr(address);
                    break;
                }
                case "AN": {//and
                    and();
                    break;
                }
                case "XO": {//xor
                    xor();
                    break;
                }
                case "OR": {//or
                    or();
                    break;
                }
                case "NO": {//not
                    not();
                    break;
                }
                case "JU": {//jump
                    ju(address);
                    break;
                }
                case "JM": {//jump if more
                    jm(address);
                    break;
                }
                case "JE": {//jump if equal
                    je(address);
                    break;
                }
                case "JL": {//jump if lower
                    jl(address);
                    break;
                }
                case "SM": {//save to memory
                    sm(x1, x2);
                    break;
                }
                case "LM": {//load from memory
                    lm(x1, x2);
                    break;
                }
                case "FR": {//file read

                    break;
                }
                case "FW": {//file write

                    break;
                }
                case "GD": {//get data
                    gd(address);

                    break;
                }
                case "PD": {//put data
                    pd(address);
                    break;
                }
                default:
                    //ismest koki exeptiona kad komada nerasta
                    RealMachine.RealMachine.toConsole("command not found");
                 //   System.out.println("command not found");
                    break;
            }
        }
    }

    private int charToInt(char ch) {
        //TO DO: prideti apribojimus kad ne raide n shit
        int temp = (char) (ch - '0');
        return temp;
    }

    private int getIntFromAddress(short address) {//<-----------------------------------------------------------NEEDS EDIT
        char[] digits = pageTable.getCharArrayAtAddress(address);
        int res = Integer.parseInt(new String(digits));
        RealMachine.RealMachine.toConsole("get int from memory : " + res);
    //    System.out.println("get int from array : " + res);
        return res;
    }

    private void putToAddress(byte[] bytes, short address) {//<--------------------------------------------------NEEDS EDIT
        int value = 1234;
        char[] chars = String.valueOf(value).toCharArray();
        pageTable.setBytesAtAddress(address, bytes);

    }

private byte[] intToBytes( final int i ) {
        ByteBuffer bb = ByteBuffer.allocate(4);
        bb.putInt(i);
        return bb.array();
    }

    private void putIntToAddress(int number, short address) {//<--------------------------------------------------NEEDS EDIT
        byte[] temp = new byte [4];
        temp = intToBytes(number);
        putToAddress(temp, address);
    }

    //commands
    //LR – Load Register – iš atminties baito x1x2 persiunčia į registrą R1:
    //LR x1x2  => R1:=[x1x2];
    private void lr(short address) {
        r1.setR(getIntFromAddress(address));

        short a = IC_Register.getIc();
        IC_Register.setIc(++a);
    }

    //SR – Save Register – iš registro R1 persiunčia į atminties baitą x1x2:
    //SR x1x2  => [x1x2]:=R1;
    private void sr(short address) {

        putIntToAddress(r1.getR(), address);

        short a = IC_Register.getIc();
        IC_Register.setIc(++a);
    }

    //RR – sukeičia registro R1 ir R2 reikšmes:
    //RR =) R:=R1+R2, R2=R1-R2, R1=R1-R2;
    private void rr() {
        int temp;
        temp = r1.getR();
        r1.setR(r2.getR());
        r2.setR(temp);

        short a = IC_Register.getIc();
        IC_Register.setIc(++a);
    }
    //TO DO: aritmetinese sf sutvarkysiu sian

    //ARITMETINES
    //AD – suma – prie esamos registro R1 reikšmės prideda reikšmę esančią x1x2 atminties baite, rezultatas
    //patalpinamas registre R1:      AD x1x2 => R1:=R1+[x1x2];
    private void ad(short address) {
        int temp = getIntFromAddress(address);
        if ((r1.getR() + temp) > Integer.MAX_VALUE) {
            sf.setCf(true);
        }
        r1.setR(r1.getR() + temp);
        if (r1.getR() == 0) {
            sf.setZf(true);
        }

        short a = IC_Register.getIc();
        IC_Register.setIc(++a);
    }

    //SB – atimtis – iš esamos registro R1 reikšmės atimama reikšmė esanti x1x2 atminties baite, rezultatas
    //patalpinamas registre R1:      SB x1x2 => R1:=R1-[x1x2];
    private void sb(short address) {
        int temp = getIntFromAddress(address);
        if (temp > r1.getR()) {
            sf.setCf(true);
        }
        if (temp == r1.getR()) {
            sf.setZf(true);
        }
        //status flag
        r1.setR(r1.getR() - 1);

        short a = IC_Register.getIc();
        IC_Register.setIc(++a);
    }

    //MU -multiplication R1:=R1 *[x1x2];
    private void mu(short address) {
        int temp = getIntFromAddress(address);
        //status flag
        if ((r1.getR() * temp) > Integer.MAX_VALUE) {
            sf.setCf(true);
        }
        r1.setR(r1.getR() * temp);

        short a = IC_Register.getIc();
        IC_Register.setIc(++a);
    }

    //DI - division   R2:=R1 % [x1x2];-liekana R1:=R1 *[x1x2];
    private void di(short address) {
        int temp = getIntFromAddress(address);
        //status flag
        r2.setR(r1.getR() % temp);
        r1.setR(r1.getR() / temp);

        short a = IC_Register.getIc();
        IC_Register.setIc(++a);
    }

    //PALYGINIMO
    //CR – palyginimas – esamą registro R1 reikšmė yra lyginama su reikšme esančią x1x2 atminties baite,
    //rezultatas patalpinamas registre C:       CR x1x2 =>
    //          if R>[x1x2] then CF:=FALSE, ZF:= FALSE;
    //          if R=[x1x2] then ZF:=TRUE;
    //          if R<[x1x2] then CF:=TRUE;
    private void cr(short address) {
        int temp = getIntFromAddress(address);
        if (r1.getR() == temp) {
            sf.setZf(true);
            sf.setCf(false);
        } else if (r1.getR() > temp) {
            sf.setZf(false);
            sf.setCf(false);
        } else { //r1<temp
            sf.setZf(false);
            sf.setCf(true);
        }

        short a = IC_Register.getIc();
        IC_Register.setIc(++a);
    }

    //LOGINES
    //AND
    private void and() {
        r1.setR(r1.getR() & r2.getR());
        if (r1.getR() == 0) {
            sf.setZf(true);
        }

        short a = IC_Register.getIc();
        IC_Register.setIc(++a);
    }

    //XOR
    private void xor() {
        r1.setR(r1.getR() ^ r2.getR());
        if (r1.getR() == 0) {
            sf.setZf(true);
        }

        short a = IC_Register.getIc();
        IC_Register.setIc(++a);
    }

    //OR
    private void or() {
        r1.setR(r1.getR() | r2.getR());
        if (r1.getR() == 0) {
            sf.setZf(true);
        }

        short a = IC_Register.getIc();
        IC_Register.setIc(++a);
    }

    //NOT
    private void not() {
        r1.setR(~r1.getR());
        if (r1.getR() == 0) {
            sf.setZf(true);
        }

        short a = IC_Register.getIc();
        IC_Register.setIc(++a);
    }

    //VALDYMO PERDAVIMO (JUMP'AI)//<--------------------------------------------------------------------------NEEDS EDIT
    //JU – besąlyginio valdymo perdavimas – valdymas perduodamas adresu 16*x1+x2:
    //JU x1x2 => IC:=16*x1+x2;
    private void ju(short address) {
        //      IC_Register.setIc(short(16 * x1 + x2));
        //       ic = (short) (16);// manau, kad negerai

        /**
         * AR JUMPAI GALI TAIP ATRODYT????
         */
        pageTable.setCs(address);
        short a = IC_Register.getIc();
        IC_Register.setIc(++a);

    }

    // JM – sąlyginio valdymo perdavimas (jeigu daugiau) – valdymas perduodamas jeigu C=0, valdymas perduodamas adresu 16*x1+x2:
    // Jm x1x2 =) If C=0 then Ds:= 16*x1+x2;
    private void jm(short address) {
        //kaip su sf reikia apsibrezt
        if (sf.getCf() == false && sf.getZf() == false) {
            pageTable.setCs(address);
        }
        short a = IC_Register.getIc();
        IC_Register.setIc(++a);

    }

    // JE – sąlyginio valdymo perdavimas (jeigu lygu) – valdymas perduodamas jeigu C=1, valdymas perduodamas adresu 16*x1+x2:
    //JE x1x2 =)  If C=1 then IC:= 16*x1+x2;
    private void je(short address) {
        if (sf.getZf() == true) {
            pageTable.setCs(address);
        }
        short a = IC_Register.getIc();
        IC_Register.setIc(++a);
    }

    //JL – sąlyginio valdymo perdavimas (jeigu mažiau) – valdymas perduodamas jeigu C=2, valdymas perduodamas adresu 16*x1+x2:
    //JL x1x2 =)  If C=2 then IC:= 16*x1+x2;
    private void jl(short address) {
        if (sf.getCf() == true) {
            pageTable.setCs(address);
        }
        short a = IC_Register.getIc();
        IC_Register.setIc(++a);
    }

    //DARBO SU BENDRA ATMINTIES SRITIMI (prieinama visoms vartotojo programoms; komandos leidžia į ją rašyti ir skaityti; sritis apsaugoma semaforais):
    // SM – registro R įrašymas į bendrąją atmintį:
    //SM x1x2 =)[16*[16*(16*a2 a3)+x1]x2] :=R (pagal puslapiavimo mechanizmą);
    private void sm(int x1, int x2) {
        //???

        short a = IC_Register.getIc();
        IC_Register.setIc(a++);
    }

    // LM – iš bendrosios atminties įrašomas žodis į registrą R:
    //LM x1x2 =) R:= [16*[16*(16*a2 a3)+x1]x2] (pagal puslapiavimo mechanizmą);
    private void lm(int x1, int x2) {
        //???

        short a = IC_Register.getIc();
        IC_Register.setIc(++a);
    }

    //PABAIGOS
    // HALT – programos pabaigos komanda.
    private void halt() {
        //???

        short a = IC_Register.getIc();
        IC_Register.setIc(++a);
    }

    //IVEDIMO /ISVEDIMO
    //FR - file read
    //FW - file write
    // GD – įvedimas – iš įvedimo srauto paima 1 žodžio srautą ir jį įveda į atmintį pradedant atminties baitu 16*x1+x2:
    // GD x1x2
    private void gd(short address) {
        //int x;
        //read( x );
        //putToAddress(x, x1, x2);

        short a = IC_Register.getIc();
        IC_Register.setIc(++a);
    }

    // PD – išvedimas – iš atminties, pradedant atminties baitu 16*x1+x2 paima 1 žodžio srautą ir jį išveda į ekraną:
    //PD x1x2
    private void pd(short address) {
       // System.out.println(getIntFromAddress(address));
        RealMachine.RealMachine.toConsole(String.valueOf(getIntFromAddress(address)));

        short a = IC_Register.getIc();
        IC_Register.setIc(a++);
    }
}
