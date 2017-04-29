package Registers;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;

public class CommonUseRegisters {

    private byte[] r = new byte[4];

    public byte[] getR() {
        return r;
    }

    public int getRInt() {

        return ByteBuffer.wrap(r).getInt();
    }

    public char[] getRChar() {
        char[] temp = new char[4];
        temp[0] = (char) r[0];
        temp[1] = (char) r[1];
        temp[2] = (char) r[2];
        temp[3] = (char) r[3];
        return temp;
    }

    public void setR(int r1) {
        this.r = toBytes(r1);
    }

    public void setR(char[] r1) {

        this.r = toBytes(r1);
    }

    public void setR(byte[] r1) {
        this.r = r1;
    }

    public void setR(String r1) {
        this.r = toBytes(r1.substring(0, 3).toCharArray());
    }

    private byte[] toBytes(char[] chars) {
        CharBuffer charBuffer = CharBuffer.wrap(chars);
        ByteBuffer byteBuffer = Charset.forName("UTF-8").encode(charBuffer);
        byte[] bytes = Arrays.copyOfRange(byteBuffer.array(),
                byteBuffer.position(), byteBuffer.limit());
        Arrays.fill(charBuffer.array(), '\u0000'); // clear sensitive data
        Arrays.fill(byteBuffer.array(), (byte) 0); // clear sensitive data
        return bytes;
    }

    byte[] toBytes(int i) {
        byte[] result = new byte[4];

        result[0] = (byte) (i >> 24);
        result[1] = (byte) (i >> 16);
        result[2] = (byte) (i >> 8);
        result[3] = (byte) (i /*>> 0*/);

        return result;
    }

    @Override
    public String toString() {
        return ": " + getRInt();
    }

}
