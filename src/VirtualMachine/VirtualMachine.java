package VirtualMachine;


public class VirtualMachine {
    private int r1;
    private int r2;
    private short ic;
    private int ds;
    private int cs;
    private  SFRegister sf;
    //kaip mes sf updateinsim?

//***************************************************
    /**
     * Pozymiu registras
     */
 //   public static CRegister C;
    /**
     * Vartotojui isskiriama atmintis
     */
    static public PageTable PageTable;
    public static UserMemory memory;
    public String end = "HALT";
    //***************************************************


    public VirtualMachine() {
        sf = new SFRegister();
    }

    //Cs ir ds?
    //why not
    public VirtualMachine(int cs, int ds) {
        sf = new SFRegister();
        this.ds = ds;
        this.cs = cs;

    }

    public SFRegister getSf() {
        return sf;
    }

    public void setSf(SFRegister sf) {
        this.sf = sf;
    }

    public int getCs() {
        return cs;
    }

    public void setCs(int cs) {
        this.cs = cs;
    }

    public int getDs() {
        return ds;
    }

    public void setDs(int ds) {
        this.ds = ds;
    }

    public short getIc() {
        return ic;
    }

    public void setIc(short ic) {
        this.ic = ic;
    }

    public int getR1() {
        return r1;
    }

    public void setR1(int r1) {
        this.r1 = r1;
    }

    public int getR2() {
        return r2;
    }

    public void setR2(int r2) {
        this.r2 = r2;
    }

    @Override
    public String toString() {
        return "VirtualMachine{" +
                "r1=" + r1 +
                ", r2=" + r2 +
                ", ic=" + ic +
                ", sf=" + sf.toString() +
                '}';
    }


    //command interpretation
    public void interpretACommand(String command) {

        if ((command.charAt(0) == 'H') && (command.charAt(1) == 'A')) {//HALT

        } else {
            int x1 = charToInt(command.charAt(2));
            int x2 = charToInt(command.charAt(3));
            String tempCommand = new StringBuilder().append(command.charAt(0)).append(command.charAt(1)).toString();//should simplify it :D
            switch (tempCommand) {
                case "LR": {//load register
                    lr(x1, x2);
                    break;
                }
                case "SR": {//save register
                    sr(x1, x2);
                    break;
                }
                case "RR": {// swaps R1 with R2
                    rr();
                    break;
                }
                case "AD": {//add
                    ad(x1, x2);
                    break;
                }
                case "SB": {//subtract
                    sb(x1, x2);
                    break;
                }
                case "MU": {//multi
                    mu(x1, x2);
                    break;
                }
                case "DI": {//div
                    di(x1, x2);
                    break;
                }
                case "CR": {//compare
                    cr(x1, x2);
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
                    ju(x1, x2);
                    break;
                }
                case "JM": {//jump if more
                    jm(x1, x2);
                    break;
                }
                case "JE": {//jump if equal
                    je(x1, x2);
                    break;
                }
                case "JL": {//jump if lower
                    jl(x1, x2);
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
                    gd(x1, x2);

                    break;
                }
                case "PD": {//put data
                    pd(x1, x2);
                    break;
                }
                default:
                    //ismest koki exeptiona kad komada nerasta
                    System.out.println("command not found");
                    break;
            }
        }
    }

    private int charToInt(char ch) {
        //TO DO: prideti apribojimus kad ne raide n shit
        int temp = (char) (ch - '0');
        return temp;
    }

    private int getFromAddress(int x1, int x2) {//<-----------------------------------------------------------NEEDS EDIT
        //what data types should be used????
        return 5;
    }

    private void putToAddress(int data, int x1, int x2) {//<--------------------------------------------------NEEDS EDIT
        //what data types should be used????

    }


    //commands
    //LR – Load Register – iš atminties baito x1x2 persiunčia į registrą R1:
    //LR x1x2  => R1:=[x1x2];
    private void lr(int x1, int x2) {
        setR1(getFromAddress(x1, x2));

        ic++;
    }

    //SR – Save Register – iš registro R1 persiunčia į atminties baitą x1x2:
    //SR x1x2  => [x1x2]:=R1;
    private void sr(int x1, int x2) {
        putToAddress(this.getR1(), x1, x2);

        ic++;
    }

    //RR – sukeičia registro R1 ir R2 reikšmes:
    //RR =) R:=R1+R2, R2=R1-R2, R1=R1-R2;
    private void rr() {
        int temp;
        temp = this.getR1();
        this.setR1(this.getR2());
        this.setR2(temp);

        ic++;
    }
    //TO DO: aritmetinese sf sutvarkysiu sian

    //ARITMETINES
    //AD – suma – prie esamos registro R1 reikšmės prideda reikšmę esančią x1x2 atminties baite, rezultatas
    //patalpinamas registre R1:      AD x1x2 => R1:=R1+[x1x2];
    private void ad(int x1, int x2) {
        int temp = getFromAddress(x1, x2);
        if ((r1+temp)>Integer.MAX_VALUE){
            sf.setCf(true);
        }
        r1 += temp;
        if (r1 == 0)
            sf.setZf(true);
        ic++;
    }


    //SB – atimtis – iš esamos registro R1 reikšmės atimama reikšmė esanti x1x2 atminties baite, rezultatas
    //patalpinamas registre R1:      SB x1x2 => R1:=R1-[x1x2];
    private void sb(int x1, int x2) {
        int temp = getFromAddress(x1, x2);
        if (temp> r1)
            sf.setCf(true);
        if (temp == r1)
            sf.setZf(true);
        //status flag
        r1 -= temp;

        ic++;
    }

    //MU -multiplication R1:=R1 *[x1x2];
    private void mu(int x1, int x2) {
        int temp = getFromAddress(x1, x2);
        //status flag
        if ((r1*temp)>Integer.MAX_VALUE){
            sf.setCf(true);
        }
        r1 *= temp;

        ic++;
    }

    //DI - division   R2:=R1 % [x1x2];-liekana R1:=R1 *[x1x2];
    private void di(int x1, int x2) {
        int temp = getFromAddress(x1, x2);
        //status flag
        r2 = r1 % temp;
        r1 /= temp;

        ic++;
    }


    //PALYGINIMO
    //CR – palyginimas – esamą registro R1 reikšmė yra lyginama su reikšme esančią x1x2 atminties baite,
    //rezultatas patalpinamas registre C:       CR x1x2 =>
    //          if R>[x1x2] then CF:=FALSE, ZF:= FALSE;
    //          if R=[x1x2] then ZF:=TRUE;
    //          if R<[x1x2] then CF:=TRUE;
    private void cr(int x1, int x2) {
        int temp = getFromAddress(x1, x2);
        if (r1 == temp) {
            sf.setZf(true);
            sf.setCf(false);
        } else if (r1 > temp) {
            sf.setZf(false);
            sf.setCf(false);
        } else { //r1<temp
            sf.setZf(false);
            sf.setCf(true);
        }

        ic++;
    }

    //LOGINES
    //AND
    private void and() {
        r1 = r1 & r2;
        if(r1 == 0)
            sf.setZf(true);

        ic++;
    }

    //XOR
    private void xor() {
        r1 = r1 ^ r2;
        if(r1 == 0)
            sf.setZf(true);

        ic++;
    }

    //OR
    private void or() {
        r1 = r1 | r2;
        if(r1 == 0)
            sf.setZf(true);

        ic++;
    }

    //NOT
    private void not() {
        r1 = ~r1;
        if(r1 == 0)
            sf.setZf(true);

        ic++;
    }

    //VALDYMO PERDAVIMO (JUMP'AI)//<--------------------------------------------------------------------------NEEDS EDIT
    //JU – besąlyginio valdymo perdavimas – valdymas perduodamas adresu 16*x1+x2:
    //JU x1x2 => IC:=16*x1+x2;
    private void ju(int x1, int x2) {
        ic = (short) (16 * x1 + x2);// manau, kad negerai


    }

    // JM – sąlyginio valdymo perdavimas (jeigu daugiau) – valdymas perduodamas jeigu C=0, valdymas perduodamas adresu 16*x1+x2:
    // Jm x1x2 =) If C=0 then IC:= 16*x1+x2;
    private void jm(int x1, int x2) {
        //kaip su sf reikia apsibrezt

    }

    // JE – sąlyginio valdymo perdavimas (jeigu lygu) – valdymas perduodamas jeigu C=1, valdymas perduodamas adresu 16*x1+x2:
    //JE x1x2 =)  If C=1 then IC:= 16*x1+x2;
    private void je(int x1, int x2) {

    }

    //JL – sąlyginio valdymo perdavimas (jeigu mažiau) – valdymas perduodamas jeigu C=2, valdymas perduodamas adresu 16*x1+x2:
    //JL x1x2 =)  If C=2 then IC:= 16*x1+x2;
    private void jl(int x1, int x2) {

    }

    //DARBO SU BENDRA ATMINTIES SRITIMI (prieinama visoms vartotojo programoms; komandos leidžia į ją rašyti ir skaityti; sritis apsaugoma semaforais):
    // SM – registro R įrašymas į bendrąją atmintį:
    //SM x1x2 =)[16*[16*(16*a2 a3)+x1]x2] :=R (pagal puslapiavimo mechanizmą);
    private void sm(int x1, int x2) {
        //???

        ic++;
    }

    // LM – iš bendrosios atminties įrašomas žodis į registrą R:
    //LM x1x2 =) R:= [16*[16*(16*a2 a3)+x1]x2] (pagal puslapiavimo mechanizmą);
    private void lm(int x1, int x2) {
        //???

        ic++;
    }

    //PABAIGOS
    // HALT – programos pabaigos komanda.
    private void halt() {
        //???

        ic++;
    }

    //IVEDIMO /ISVEDIMO
    //FR - file read

    //FW - file write


    // GD – įvedimas – iš įvedimo srauto paima 1 žodžio srautą ir jį įveda į atmintį pradedant atminties baitu 16*x1+x2:
    // GD x1x2
    private void gd(int x1, int x2) {
        //int x;
        //read( x );
        //putToAddress(x, x1, x2);

        ic++;
    }

    // PD – išvedimas – iš atminties, pradedant atminties baitu 16*x1+x2 paima 1 žodžio srautą ir jį išveda į ekraną:
    //PD x1x2
    private void pd(int x1, int x2) {
        //print getFromAddress(x1, x2);

        ic++;
    }
}
