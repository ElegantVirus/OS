package VirtualMachine;

import RealMachine.UserMemory;


public class PageTable {
    /**
     * table [vmAddress] [realAddresses]:
     * vmAddress : { rmx1,rmx2}
     */

    private int[][] table;
    private short cs;
    private short ds;

    public PageTable(int vmPtr) {
        this.table = new int[256][2];
        this.fillTable(vmPtr);
        this.setCsDs();
    }
    private PageTable(){

    }

    public short getCs() {
        return cs;
    }
    public short getDs() {
        return ds;
    }
    public void increaseDs(){
        this.ds +=1;
    }
    public void setDs (short ds){
        this.ds = ds;
    }

    private void fillTable(int vmPtr) {
        for (int i = 0; i < 16; i++) {
            int block = RealMachine.UserMemory.getIntAtAddress(vmPtr, i);
            for (int j = 0; j < 16; j++) {
                table[i * 16 + j][0] = block;
                table[i * 16 + j][1] = j;
            }
        }
    }
    private void setCsDs(){
        this.cs = 128;
        this.ds = 0;
        /**
         * todo implement
         * perbegti per visus ir radus DATA ir CODE priskitri adresa atitinkamai;
         * dar galima idet patikrinima  i setinima ir gettinima
         * ar tikrai nevirsija datasegmento reziu
         */

    }

    public byte[] getAtAdress(short address) {
        int x1 = this.table[address][0];
        int x2 = this.table[address][1];
        return UserMemory.getByteAtAddress(x1, x2);
    }

    public int getIntAtAddress(short address) {
        int x1 = this.table[address][0];
        int x2 = this.table[address][1];
        return UserMemory.getIntAtAddress(x1, x2);
    }

    public char [] getCharArrayAtAdress(short address) {
        int x1 = this.table[address][0];
        int x2 = this.table[address][1];
        return UserMemory.getCharArrayAtAddress(x1, x2);
    }
    public void setBytesAtAddress(short address, byte [] bytes){
        int x1 = this.table[address][0];
        int x2 = this.table[address][1];
        UserMemory.setBytesAtAddress(x1,x2,bytes);
    }

}