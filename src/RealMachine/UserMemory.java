package RealMachine;


import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class UserMemory {
    private static final int MEMORY_SIZE = 272;
    private static final int PTR = 0;
    /**
     * memory [i]: i=
     * 0 - ptr
     * 1-16 supervisor memory
     * 17- (MEMORY_SIZE-1) - free
     */
    private static byte[][][] memory = new byte[MEMORY_SIZE][16][4];;
    private static boolean isEmpty[] = new boolean[MEMORY_SIZE];
    private static int vmCount = 0;
    /**
     * vmCount kol kas naudoju, kad suzinociau kiek vm buvo sukurta
     * tiksliau i kuri rm ptr zodi irasyt vmptr ty:
     * jei vmCount =0
     * ptr [0] = vmPtr; vmCount++
     *
     * jei vmCount = 5
     *  ptr [5] = vmPtr; vmCount++
     *
     *  reiktu sutvarkyt veliau, nes tai leis sukurti max 16 vm per os gyvavima ty jei bus daugiau
     *  mes array bound exeptiona
     */


   // public SupervisorMemory supervisorMemory;

    /**
     * nenaudoju atskiros Supervisor memory :(
     *
     * vietoj jos parasiau tris metodus:
     * loadToSupervisory - ikelia proceso bloka i ja
     * isSupervisoryCommandsOk -patikrina ar geros komandos, ją iskviecia loadFromSupervisoryToVm
     * loadFromSupervisoryToVm - grazina vm ptr, o jei netelpa  -1 arba negeros komandos -2
     *
     * getRandomFreeBlock (size); size kiek bloku norime gauti; rezultatas bloku numeriu masyvas,
     * jei netelpa masyve pirmas elementas -1
     */

    UserMemory() {
        vmCount = 0;
        memory = new byte[MEMORY_SIZE][16][4];
        isEmpty = new boolean[MEMORY_SIZE];

        for (int i = 17; i < MEMORY_SIZE; i++) {
            isEmpty[i] = true;
        }
        //supervisorMemory = new SupervisorMemory();
    }

    public static int getPtr() {
        return PTR;
    }

    public int getVmCount() {
        return vmCount;
    }

    public void loadToSupervisory(byte[][][] process) {
        for (int i = 1; i <=  16; i++) {
            memory[i] = process[i-1];
        }

        System.out.println("supervisory:");
        for (int i =1; i <= 16; i++) {
            System.out.print(i+"  ***");
            for (int j = 0; j < 16; j++)
                for (int y = 0; y < 4; y++)
                    System.out.print(((char) memory[i][j][y]));
            System.out.print("***\n");
        }
    }
    private int charToInt(char ch) {
        //TODO: prideti apribojimus kad ne raide n shit
        int temp = (char) (ch - '0');
        return temp;
    }
    private boolean isSupervisoryCommandsOk(){
        /**
         * TODO check data segment
         * patikrinti ne tik komandas bet ir skaicius ir tt
         */

        boolean codeSegmentIsFound = false;
        for (int i = 1 ; i<=16; i++){
            for (int j = 0; j < 16; j++){
                char [] command = getCharArrayAtAddress(i,j);
                if((command [0]== 'C')&&(command [1]== 'O')&&(command [2]== 'D')&&(command [3]== 'E')){
                    codeSegmentIsFound = true;
                }
               if (codeSegmentIsFound){
                   String tempCommand = new StringBuilder().append(command[0]).append(command[1]).toString();//should simplify it :D
                   switch (tempCommand) {
                       case"CO": {
                           if ((command[2] == 'D') && (command[3] == 'E')) {
                               break;
                           } else
                               return false;
                       }
                       case "LR":
                       case "SR":
                       case "RR":
                       case "AD":
                       case "SB":
                       case "MU":
                       case "DI":
                       case "CR":
                       case "AN":
                       case "XO":
                       case "OR":
                       case "NO":
                       case "JU":
                       case "JM":
                       case "JE":
                       case "JL":
                       case "SM":
                       case "LM": {
                           if ((Character.isDigit(command[2]))&&(Character.isDigit(command[3]))){
                               int x1 = charToInt(command[2]);
                               int x2 = charToInt(command[3]);
                               if ((x1*16+x2)<256 &&((x1*16+x2)>0)){
                                   break;
                               }
                               else{
                                   return false;
                               }
                           }
                           else {
                               return false;
                           }
                       }

                       case "FR": //file read papildomo patikrinimo reikia ten kur komentarai
                       case "FW": //file write

                       case "GD": //get data

                       case "PD": //put data
                       {
                           /**
                            * todo papildomi patikrimai siom 4 komandom nes jos turi kviest interuptus
                            */
                           break;
                       }
                       case "HA": {
                           if ((command[2] == 'L') && (command[3] == 'T')) {
                               return true;
                           } else
                               return false;
                       }
                       default: {

                           System.out.println("Wrong command found in supervisory memory's code segment");
                           return false;
                       }
                   }
                   return true;
               }
            }
        }
        return codeSegmentIsFound; // fill be false if there was no 'CODE' tag
    }
    public int loadFromSupervisoryToVm(){
        if (!isSupervisoryCommandsOk())
            return -2;//commands were wrong
        int [] vmAddresses = new int [17];
        vmAddresses =getRandomFreeBlock(17);
        if(vmAddresses[0] == -1){
            return -1;
            //not enough memory
        }
        else {
            int vmPtr = vmAddresses[0];
            System.out.print(" "+vmAddresses[0] );
            for(int i = 1; i<17 ; i++){
                System.out.print(" "+vmAddresses[i] );
                memory[vmPtr][i-1] = ByteBuffer.allocate(4).putInt(vmAddresses[i]).array();
                memory[vmAddresses[i]] = memory [i];
            }
            memory[0][vmCount] = ByteBuffer.allocate(4).putInt(vmPtr).array();
            vmCount ++;
            return vmPtr;
        }
    }

    private int [] getRandomFreeBlock(int size){ // how many lines
        Random rand = new Random();
        List<Integer> free = new ArrayList<Integer>();
        int [] result;
        for(int i = 17; i< MEMORY_SIZE; i++){
            if (isEmpty[i])
                free.add(i);
        }
        int howManyFrees = free.size();
        if (howManyFrees<size){
            result = new int [1];
            result[0] = -1;
            return result;
            //not enough space
        }
        else{
            result = new int [size];
            for (int j= 0; j< size; j++){
                int temp =rand.nextInt(howManyFrees);
                result[j] = free.get(temp);
                free.remove(temp);
                isEmpty[temp] = false;
                howManyFrees --;
            }
            return result;
        }
    }
    void freeVmMemory (int vmPtr){
        /**
         * TODO: Implement
         * isEmpty true uzzymet, o ar pacioj atminti nulius reik perrasyt nezinau
         * gal tiesiog palikt siuksles ??
         *
         */
    }
   /* public void loadToMemory(byte[][][] process) {
        int z = 0;
        for (int i = ptr; i < ptr + 16; i++) {
            memory[i] = process[z];
            z++;
        }

        for (int i = 0; i < 16; i++)
            for (int j = 0; j < 16; j++)
                for (int y = 0; y < 4; y++)
                    System.out.print(((char) memory[i][j][y]));

    }*/

    public int vmAddressTableAddress(int num) {
        return (num * 16) + num;

    }

    /**
     * @param num   Unique process number identifying where it was assigned, ptr.
     * @param which Which block you want to access
     * @return
     */
    public byte[] vmBlockAddress(int num, int which) {
        int blockAdress = 0;
        return memory[vmAddressTableAddress(num)][which];
    }

    public static byte [] getByteAtAddress(int x1, int x2){
        return memory[x1][x2];
    }

    public static int getIntAtAddress(int x1, int x2){
        return ByteBuffer.wrap( memory[x1][x2]).getInt();
    }
    public static char [] getCharArrayAtAddress(int x1, int x2){
        char [] temp = new char [4];
        temp[0]= (char )memory[x1][x2][0];
        temp[1]= (char )memory[x1][x2][1];
        temp[2]= (char )memory[x1][x2][2];
        temp[3]= (char )memory[x1][x2][3];
        return temp;
    }
    public static void setBytesAtAddress(int x1, int x2, byte [] bytes){
        memory[x1][x2]= bytes;
    }

    public void printMemoryContents () {
        System.out.println("\nMEMORY CONTENTS:");
        for (int i = 0; i < MEMORY_SIZE; i++) {
            System.out.print(i+"  ***");
            for (int j = 0; j < 16; j++)
                System.out.print(" "+ Arrays.toString(getCharArrayAtAddress(i, j))+" |");
                //System.out.print(" "+(ByteBuffer.wrap( memory[i][j]).getInt())+" |");
            System.out.print("***\n");
        }
    }
}
