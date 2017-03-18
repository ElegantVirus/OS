package VirtualMachine;

import RealMachine.RealMachine;



public class PageTable {
    int PageTableNumber;

    public PageTable() {
        this.PageTableNumber = RealMachine.PTR.getPageTable();
        for(int i=0;i<10;i++) {
            RealMachine.memory.set(this.PageTableNumber, i, ""+3+i);
        }
        //RM.memory.set(this.PageTableNumber, 7, ""+0);
    }
    public int getRealBlockNumber(int VirtualBlock) {
        String RBN = RealMachine.memory.getWord(RealMachine.PTR.getPageTable(), VirtualBlock);
        return Integer.parseInt(RBN);
    }
}