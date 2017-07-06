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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;

public final class ExternalMemory {

    public RandomAccessFile file;
    byte[] word;
    public HashMap<String, Long[]> fileMap = new HashMap<>();
    int pointer = 21105;

    public ExternalMemory() {
        initializeMemory("hdd1.txt");
    }

    public void initializeMemory(String name) {
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
     * @param pos position at which to start (reachable through WriteToDisc
     * method or searchInHDD, if you want to rewrite
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

            if (i >= bblock.length) {
                break;
            }
        }

    }

    /**
     * Memory block's,or program's, name is written in the first 16 bytes of our
     * HDD file. Method finds the name, which is 4 bytes long, and it's number
     * (1...16), then, accordingly, finds it's memory block
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
        if (blockPosition == -1) {
            System.out.println("toks failas neegzistuoja");
        }
        return blockPosition;
    }

    /**
     * Method to output specific memory block
     *
     * @param pos position of the block we want to output, get it from
     * searchInHdd method
     * @throws IOException
     */
    public String readBlock(long pos) throws IOException {
        file.seek(pos);
        String chunk = "";
        /**
         * TODO skip first program's enter
         */
        //  if(pos == 0)

        for (int i = 0; i < 16; i++) {
            chunk = chunk + file.readLine() + '\n';
            //  System.out.println(file.readLine());
        }
        return chunk;
    }

    public byte[][][] fillArray(long pos) throws IOException {
        /**
         * TODO fix putting data to array, there are empty places now, something
         * fishy. FIXED
         */
        file.seek(pos);
        //   String all = readBlock(pos);

        byte[][][] memoryArray = new byte[16][16][4];
        byte[] temp = new byte[5];

        byte c = 0;
        int i = 0, j = 0, y = 0;

        while (i < 16) {
            while (j < 16) {
                while (y < 5) {
                    //if ((c != 32) && 
                    c = file.readByte();
                    if ((c != '\n') && (c != '\r') && (c != 0)) {
                        //    System.out.print((char) memoryArray[i][j][y]);
                        temp[y] = c;
                        y++;
                    }
                }
                memoryArray[i][j][0] = temp[0];
                memoryArray[i][j][1] = temp[1];
                memoryArray[i][j][2] = temp[2];
                memoryArray[i][j][3] = temp[3];
                y = 0;
                j++;
            }
            j = 0;
            i++;
        }

        return memoryArray;
    }

    public long getBlockPosition() throws IOException {
        long pos = file.getFilePointer();
        return ((pos / 5) * 1314) + 81;
    }

    /**
     *
     * @param name name - key of fileMap
     * @param text later converted to bytes
     * @throws IOException
     */
    public void addFile(byte[] name) throws IOException {

        file.seek(pointer);
        //rašome failą toje vietoje,kur esame (prie pabaigos)  
        //  file.writeBytes("\r\n");
        String n = new String(name);
        Long addr[] = new Long[3];
        addr[0] = addr[1] = addr[2] = file.getFilePointer();

        fileMap.put(n, addr);
        cleanBlock(fileMap.get(n)[0]);

        //  writeToBlock(text + "EOF", fileMap.get(n)[0]);
        pointer = pointer + 1312;//text.getBytes().length;
    }

    /**
     *
     * @param pos position at which to start cleaning
     * @throws IOException
     */
    public void cleanBlock(long pos) throws IOException {
        file.seek(pos);

        byte[] bytes = {48, 48, 48, 48};

        for (int y = 0; y < 16; y++) {
            file.writeBytes("\r\n");
            for (int j = 0; j < 16; j++) {
                file.write(bytes);
                file.write(32);
            }
        }
    }

    /**
     * Fills given file's space 16x16x4 with zeroes
     *
     * @param name file name - fileMap key
     * @throws IOException
     */
    public void deleteFile(byte[] name) throws IOException {
        String n = new String(name);
        cleanBlock(fileMap.get(n)[0]);
    }

    /**
     * Reads from the file's address up until the EOF string comes up
     *
     * @param name to find the file
     * @return String program's text
     * @throws IOException
     */
    public String fileReadFull(byte[] name) throws IOException {

        String temp = "";
        String n = new String(name);

        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                for (int y = 0; y < 4; y++) {
                    temp = temp + (char) (fillArray(fileMap.get(n)[0])[i][j][y]);
                }
            }
        }
        String[] words = temp.split("EOF");
        return words[0];
    }

    /**
     * FIXED - reads 4 bytes in given address
     *
     * @param name to find the file
     * @return symbol at 'pos' position
     * @throws IOException
     */
    public byte[] fileReadAtPos(byte[] name) throws IOException {

        /*    byte[] signs = fileReadFull(name).getBytes();
        byte[] retval = new byte[4];
        retval[0] = signs[pos];
        retval[1] = signs[pos + 1];
        retval[2] = signs[pos + 2];
        retval[3] = signs[pos + 3];
         */
        String n = new String(name);
        file.seek(fileMap.get(n)[2]);
        byte[] retval = new byte[4];
        //file.read(retval, 0, 5);
        retval[0] = file.readByte();
        retval[1] = file.readByte();
        retval[2] = file.readByte();
        retval[3] = file.readByte();
        file.readByte();
        fileMap.get(n)[2] = file.getFilePointer();
        return retval;
    }

    /**
     * Repeats the addFile method except this time address isn't acquired
     * automatically
     *
     * @param name to find the address
     * @param text what to write
     * @throws IOException
     */
    public void fileRewriteFull(byte[] name, String text) throws IOException {
        String n = new String(name);
        file.seek(fileMap.get(n)[0]);
        Long addr[] = new Long[2];
        addr[0] = addr[1] = addr[2] = file.getFilePointer();
        fileMap.put(n, addr);
        cleanBlock(fileMap.get(n)[0]);

        writeToBlock(text + "EOF", fileMap.get(n)[0]);
        pointer = pointer + 1312;//text.getBytes().length;
    }

    /**
     * Changes 4 bytes at given address
     *
     * @param name to find file
     * @param value char to write
     * @throws IOException
     */
    public void fileRewriteAtPos(byte[] name, byte[] value) throws IOException {
        /*
        byte[] signs = fileReadFull(name).getBytes();
        signs[pos] = value[0];
        signs[pos + 1] = value[1];
        signs[pos + 2] = value[2];
        signs[pos + 3] = value[3];

        System.out.println(new String(value));
        String s = new String(signs);
        System.out.println(s);
        fileRewriteFull(name, s);*/
        String n = new String(name);
        file.seek(fileMap.get(n)[1]);
        file.write(value);
        file.write(' ');
        fileMap.get(n)[1] = file.getFilePointer();
    }

    public boolean fileOpen(byte[] name) {
        String n = new String(name);
        if (fileMap.get(n)[0] == 0) {
            RealMachine.toConsole("The file named " + n + " doesn't exist");
            return false;
        } else {
            fileMap.get(n)[1] = fileMap.get(n)[1] = fileMap.get(n)[0];
            RealMachine.toConsole("The file named " + n + " has been successfully opened");
            return true;
        }

    }

    public boolean fileClose(byte[] name) {
        String n = new String(name);
        if (fileMap.get(n)[0] == 0) {
            RealMachine.toConsole("The file named " + n + " doesn't exist");
            return false;
        } else {
            fileMap.get(n)[1] = fileMap.get(n)[1] = fileMap.get(n)[0];
            RealMachine.pi.set_0();
            RealMachine.toConsole("The file named " + n + " has been successfully closed");
            return true;
        }
    }

    public String[] getAllFiles() {
        String[] keys = new String[fileMap.size()];

        int index = 0;
        for (HashMap.Entry<String, Long[]> mapEntry : fileMap.entrySet()) {
            keys[index] = mapEntry.getKey();

            System.out.println(mapEntry.getKey());

            //   values[index] = mapEntry.getValue();
            index++;
        }
        return keys;
    }
}
