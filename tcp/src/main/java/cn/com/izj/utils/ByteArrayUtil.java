package cn.com.izj.utils;

/**
 * Created by GouBo on 2018/7/11 23:19.
 */
public class ByteArrayUtil {
    public static byte[] getBytes(short data) {
        byte[] bytes = new byte[2];
        bytes[1] = (byte) (data & 0xff);
        bytes[0] = (byte) ((data & 0xff00) >> 8);
        return bytes;
    }

    public static byte[] getBytes(int data) {
        byte[] bytes = new byte[4];
        bytes[3] = (byte) (data & 0xff);
        bytes[2] = (byte) ((data & 0xff00) >> 8);
        bytes[1] = (byte) ((data & 0xff0000) >> 16);
        bytes[0] = (byte) ((data & 0xff000000) >> 24);
        return bytes;
    }

    public static byte[] getBytes(long data) {
        byte[] bytes = new byte[8];
        bytes[7] = (byte) (data & 0xff);
        bytes[6] = (byte) ((data >> 8) & 0xff);
        bytes[5] = (byte) ((data >> 16) & 0xff);
        bytes[4] = (byte) ((data >> 24) & 0xff);
        bytes[3] = (byte) ((data >> 32) & 0xff);
        bytes[2] = (byte) ((data >> 40) & 0xff);
        bytes[1] = (byte) ((data >> 48) & 0xff);
        bytes[0] = (byte) ((data >> 56) & 0xff);
        return bytes;
    }

    public static short getShort(byte[] bytes) {
        return (short) ((0xff00 & (bytes[0] << 8)) | (0xff & bytes[1]));
    }

    public static int getInt(byte[] bytes) {
        return (0xff000000 & (bytes[0] << 24)) |
                (0xff0000 & (bytes[1] << 16)) |
                (0xff00 & (bytes[2] << 8)) |
                (0xff & bytes[3]);
    }

    public static long getLong(byte[] bytes) {
        return (0xffL & (long) bytes[7]) |
                (0xff00L & ((long) bytes[6] << 8)) |
                (0xff0000L & ((long) bytes[5] << 16)) |
                (0xff000000L & ((long) bytes[4] << 24)) |
                (0xff00000000L & ((long) bytes[3] << 32)) |
                (0xff0000000000L & ((long) bytes[2] << 40)) |
                (0xff000000000000L & ((long) bytes[1] << 48)) |
                (0xff00000000000000L & ((long) bytes[0] << 56));
    }

    public static byte[] byteMergerAll(byte[]... values) {
        int length_byte = 0;
        for (byte[] value : values) {
            if (value != null) {
                length_byte += value.length;
            }
        }
        byte[] all_byte = new byte[length_byte];
        int countLength = 0;
        for (byte[] b : values) {
            if (b != null) {
                System.arraycopy(b, 0, all_byte, countLength, b.length);
                countLength += b.length;
            }
        }
        return all_byte;
    }
}
