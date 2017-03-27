package RealMachine;

public class SupervisorMemory {

    static int[] addressTable = new int[16];


    public SupervisorMemory() {

    }

    //setting the unique PTR number of vm
   /* public void fillTable() {
        if (addressTable == null) {
            addressTable[0] = 0;
            UserMemory.setPtr(0);

        }

        for (int i = 1; i < 16; i++) {
            if (addressTable[i] == 0) {
                addressTable[i] = i;
                break;
            }
            UserMemory.setPtr(i);
        }
    }*/


    @Override
    public String toString() {
        return addressTable.toString();
    }
}
