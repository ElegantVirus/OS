/**
*Operaciniu sistemu projektas
*Autores :
*Evelina Bujyte
*Anastasija Kiseliova
*Matematine informatika
*3 kursas
*2017
**/
package RealMachine;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UserMemory {

	private static final int MEMORY_SIZE = 272;
	private static final int PTR = 0;
	/**
	 * memory [i]: i= 0 - ptr 1-16 supervisor memory 17- (MEMORY_SIZE-1) - free
	 */
	private static byte[][][] memory = new byte[MEMORY_SIZE][16][4];
	;
    private static boolean isEmpty[] = new boolean[MEMORY_SIZE];
	private static int vmCount = 0;
	public static boolean[] vmPointers;
	//vmPointers is used to know which vm slots are available; there can be max 16 vms indexes from 0 to 15 vm.id stores its index
	//false- available; true - there is vm at the index


	UserMemory() {
		vmCount = 0;
		memory = new byte[MEMORY_SIZE][16][4];
		isEmpty = new boolean[MEMORY_SIZE];
		vmPointers = new boolean[16];

		for (int i = 0; i < 16; i++) {
			vmPointers[i] = false;
		}
		for (int i = 17; i < MEMORY_SIZE; i++) {
			isEmpty[i] = true;
		}
		//supervisorMemory = new SupervisorMemory();
	}

	public static int getPtr() {
		return PTR;
	}

	public static int getVmCount() {
		return vmCount;
	}

	public void loadToSupervisory(byte[][][] process) {

		boolean codeWasFound = false;
		cleanSupervisory();
		int ii = 0, jj = 0;
		for (int i = 1; (i <= 16) && (ii <= 16); i++, ii++) {
			for (int j = 0; j < 16; j++, jj = (jj + 1) % 16) {
				char[] c = new char[4];
				c[0] = (char) process[i - 1][j][0];
				c[1] = (char) process[i - 1][j][1];
				c[2] = (char) process[i - 1][j][2];
				c[3] = (char) process[i - 1][j][3];
				// System.out.println(i+" "+j+"  :"+c[0]+c[1]+c[2]+c[3]);
				if ((c[0] == 'C') && (c[1] == 'O') && (c[2] == 'D') && (c[3] == 'E')) {
					codeWasFound = true;
					//cleanSupervisory(i,j);
					ii = 9;
					jj = 0;
				}
				if (codeWasFound) {
					// memory[i][j] = temp;
					memory[ii][jj] = process[i - 1][j];

				} else {
					memory[i][j] = process[i - 1][j];
				}
			}
		}
	}

	private int charToInt(char ch) {
		int temp = (char) (ch - '0');
		return temp;
	}

	private int getFreeVmSlot() {
		for (int i = 0; i < 16; i++) {
			if (vmPointers[i] == false) {

				return i;
			}

		}
		return -1;
	}

	public int loadFromSupervisoryToVm() {
		if (!JCL.isSupervisoryCommandsOk()) {
			return -2;//commands were wrong
		}
		int vmIndex = getFreeVmSlot();
		if (vmIndex == -1) {
			//max virtual machines are working
			//not enough memory
			return -1;
		}

		int[] vmAddresses = new int[17];
		vmAddresses = getRandomFreeBlock(17);
		if (vmAddresses[0] == -1) {
			return -1;
			//not enough memory
		} else {
			int vmPtr = vmAddresses[0];
			//System.out.print(" " + vmAddresses[0]);
			for (int i = 1; i < 17; i++) {
				//System.out.print(" " + vmAddresses[i]);
				memory[vmPtr][i - 1] = ByteBuffer.allocate(4).putInt(vmAddresses[i]).array();
				memory[vmAddresses[i]] = memory[i];
			}
			memory[0][vmIndex] = ByteBuffer.allocate(4).putInt(vmPtr).array();
			vmCount++;
			vmPointers[vmIndex] = true;
			return vmIndex;
		}
	}

	public int getVmPointerByIndex(int vmIndex) {
		return ByteBuffer.wrap(memory[0][vmIndex]).getInt();
	}

	public void freeVmMemory(int vmIndex) {
		int vmPointer = getVmPointerByIndex(vmIndex);
		memory[0][vmIndex] = ByteBuffer.allocate(4).putInt(-1).array();
		vmPointers[vmIndex] = false;
		vmCount--;

		for (int i = 0; i < 16; i++) {
			int index = ByteBuffer.wrap(memory[vmPointer][i]).getInt();
			isEmpty[index] = true;
		}
		isEmpty[vmPointer] = true;
	}

	private int[] getRandomFreeBlock(int size) { // how many lines
		Random rand = new Random();
		List<Integer> free = new ArrayList<Integer>();
		int[] result;
		for (int i = 17; i < MEMORY_SIZE; i++) {
			if (isEmpty[i]) {
				free.add(i);
			}
		}
		int howManyFrees = free.size();
		if (howManyFrees < size) {
			result = new int[1];
			result[0] = -1;
			return result;
			//not enough space
		} else {
			result = new int[size];
			for (int j = 0; j < size; j++) {
				int temp = rand.nextInt(howManyFrees);
				result[j] = free.get(temp);
				free.remove(temp);
				isEmpty[temp] = false;
				howManyFrees--;
			}
			return result;
		}
	}

	public void cleanSupervisory() {
		cleanSupervisory(1, 0);

	}

	private void cleanSupervisory(int x1, int x2) {
		byte[] temp = new byte[4];
		temp[0] = Byte.parseByte("0");
		temp[1] = Byte.parseByte("0");
		temp[2] = Byte.parseByte("0");
		temp[3] = Byte.parseByte("0");

		for (int j = x2; (x1 >= 1) && (j < 16); j++) {
			memory[x1][j] = temp;
		}
		for (int i = x1 + 1; (x1 >= 1) && (i <= 16); i++) {
			for (int j = 0; j < 16; j++) {
				memory[i][j] = temp;
			}
		}

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
	 * @param num Unique process number identifying where it was assigned, ptr.
	 * @param which Which block you want to access
	 * @return
	 */
	public byte[] vmBlockAddress(int num, int which) {
		int blockAdress = 0;
		return memory[vmAddressTableAddress(num)][which];
	}

	public static byte[] getByteAtAddress(int x1, int x2) {
		return memory[x1][x2];
	}

	public static int getIntAtAddress(int x1, int x2) {
		return ByteBuffer.wrap(memory[x1][x2]).getInt();
	}

	public static char[] getCharArrayAtAddress(int x1, int x2) {
		char[] temp = new char[4];
		temp[0] = (char) memory[x1][x2][0];
		temp[1] = (char) memory[x1][x2][1];
		temp[2] = (char) memory[x1][x2][2];
		temp[3] = (char) memory[x1][x2][3];
		return temp;
	}
	public static String getStringAtAddress(int x1, int x2) {
		String temp = "" + (char)memory[x1][x2][0]+(char)memory[x1][x2][1]+
                        (char)memory[x1][x2][2]+(char)memory[x1][x2][3];
                
		return temp;
	}
	public static void setBytesAtAddress(int x1, int x2, byte[] bytes) {
		memory[x1][x2] = bytes;
	}

	public static String printMemoryContents() {
		//       System.out.println("\nMEMORY CONTENTS:");
		/*    for (int i = 0; i < MEMORY_SIZE; i++) {
            System.out.print(i+"  ***");
            for (int j = 0; j < 16; j++)
                System.out.print(" "+ Arrays.toString(getCharArrayAtAddress(i, j))+" |");
            //System.out.print(" "+(ByteBuffer.wrap( memory[i][j]).getInt())+" |");
            System.out.print("***\n");
        }*/
		String mem = null;

		for (int i = 0; i < MEMORY_SIZE; i++) {
			//      System.out.print(i+"  ***");
			for (int j = 0; j < 16; j++) {
				for (int y = 0; y < 4; y++) {
					mem = mem + (char) memory[i][j][y];
				}
			}
			//System.out.print(((char) memory[i][j][y]));
			//System.out.print("***\n");
			mem = mem + '\n';
		}
		return mem;
	}
}
