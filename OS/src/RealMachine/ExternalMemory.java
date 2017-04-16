package RealMachine;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class ExternalMemory {

    public RandomAccessFile file;
    byte[] word;

    public ExternalMemory() {
        initializeMemory("hdd1.txt");
    }


    public void initializeMemory(String name){
        word = new byte[5];

        try {
            file = new RandomAccessFile(name, "rwd");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void eraseMemory() throws IOException {

        byte[] bytes = {48, 48, 48, 48};
        for (int i = 0; i < 16; i++) {

            file.write(bytes);
            file.write(32);

        }
        file.writeBytes("\r\n");

        for (int i = 0; i < 16; i++) {
            for (int y = 0; y < 16; y++) {
                for (int j = 0; j < 16; j++) {
                    file.write(bytes);
                    file.write(32);
                }
                file.writeBytes("\r\n");

            }
            file.writeBytes("\r\n");
        }
    }

    public void writeToDisk(String name, String block) throws IOException {

        //   System.out.println(name+" "+block);
        file.seek(0);// always go to the beginning of file and read from there

        try {
            while (true) {// first we need to find where to put the file
                file.read(word, 0, 5);
                if (word[0] == 48 && word[1] == 48 && word[2] == 48 && word[3] == 48) {
                    file.seek(file.getFilePointer() - 5);
                    file.writeBytes(name);
                    writeToBlock(block, getBlockPosition());
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes data to block
     *
     * @param block data to write to memory
     * @param pos   position at which to start (reachable through WriteToDisc method or searchInHDD, if you want to rewrite
     * @throws IOException
     */
    private void writeToBlock(String block, long pos) throws IOException {
        //   System.out.println(pos);
        file.seek(pos);
        byte[] bblock;
        bblock = block.getBytes();

        int i = 0;

        while (true) {
            if (file.readByte() == 48) {
                file.seek(file.getFilePointer() - 1);
                file.writeByte(bblock[i]);
                i++;
            }

            if (i >= bblock.length)
                break;
        }

    }

    /**
     * Memory block's,or program's, name is written in the first 16 bytes of our HDD file.
     * Method finds the name, which is 4 bytes long, and it's number (1...16), then, accordingly, finds it's memory block
     *
     * @param name name of the program,memory block (4bytes)
     * @return the place where block's data starts
     * @throws IOException
     */
    public long searchInHDD(String name) throws IOException {
        file.seek(0);// got to file beginning
        byte[] bName;
        bName = name.getBytes();
        long blockPosition = 0;

        while (true) {// first we need to find where we've put the file
            file.read(word, 0, 5);
            if (bName[0] == word[0] && bName[1] == word[1] && bName[2] == word[2] && bName[3] == word[3]) {
                file.seek(file.getFilePointer() - 5);
                blockPosition = getBlockPosition();
                break;
            } else {
                blockPosition = -1;
                //     System.out.println("Toks failas neegzistuoja");
                //    break;
            }
        }
        if (blockPosition == -1)
            System.out.println("toks failas neegzistuoja");
        return blockPosition;
    }

    /**
     * Method to output specific memory block
     *
     * @param pos position of the block we want to output, get it from searchInHdd method
     * @throws IOException
     */
    public String readBlock(long pos) throws IOException {
        file.seek(pos);
        String chunk = null;
        /**
         * TODO skip first program's enter
         */
      //  if(pos == 0)

        for (int i = 0; i < 16; i++) {
            chunk = chunk + file.readLine()+'\n';
          //  System.out.println(file.readLine());
        }
        return chunk;
    }

    public byte[][][] fillArray(long pos) throws IOException {
/**
 * TODO fix putting data to array, there are empty places now, something fishy.
 */
        file.seek(pos);
        byte[][][] memoryArray = new byte[16][16][4];

        byte c = 0;

        for (int i = 0; i < 16; i++) {

            for (int j = 0; j < 16; j++) {

                for (int y = 0; y < 4; y++) {
                    c = file.readByte();
                    if (c != 32)
                        memoryArray[i][j][y] = c;
                    //    System.out.print((char) memoryArray[i][j][y]);
                }
            }
            //because there are 81 symbol in a row, 81st is a newline
            file.seek(file.getFilePointer() + 1);
        }
        return memoryArray;
    }

    public long getBlockPosition() throws IOException {
        long pos = file.getFilePointer();
        return ((pos / 5) * 1314) + 81;
    }

}