import java.nio.ByteBuffer;

/**
 * Created by Stephen Yingling on 4/18/14.
 */
public class ByteUtils {
    public static long bytesToLong(byte[] bytes){
        long res = 0;
        int bInL = Long.SIZE/8;
        for(int i=0; i < bInL; i++){
            res <<=8;
            res |= bytes[i];
        }
        return res;
    }

    public static byte[] extractBytes(byte[] bytes, int start, int end){
        byte[] res = new byte[end-start];

        for(int i=0; (i+start)<end; i++){
            res[i] = bytes[start+i];
        }

        return res;
    }

    public static byte[] longToBytes(long l){
        ByteBuffer bf = ByteBuffer.allocate(Long.SIZE/8);
        bf.putLong(l);
        return bf.array();
    }

    public static void copyTo(byte[] from, int fStartm, int fEnd,
                              byte[] to, int tStart){
        if(fEnd < from.length){
            fEnd = from.length;
        }
        for(int delta=0;
            ((delta+fStartm) < fEnd) && ((delta+tStart)<to.length);
             delta++){
            to[delta+tStart] = from[delta+fStartm];
        }
    }

    public static byte[] concat(byte[] ... arrays){
        int len = 0;
        for(byte[] b : arrays){
            len += b.length;
        }

        byte[] res = new byte[len];
        int index = 0;

        for(byte[] cur: arrays){
            copyTo(cur,0,cur.length,res,index);
            index += cur.length;
        }

        return res;
    }

    public static String byteArToStr(byte[] bytes){
        String res = "";
        for(byte b : bytes){
            res += (char) b;
        }

        return res;
    }

    public static String byteArToHexStr(byte[] bytes){
        String res = "";
        for(byte b : bytes){
            res+= byteToString(b);
        }

        return res;
    }

    public static String byteToString(byte b){
        String res= "";
        res += nibbleToChar((byte)(0x0F&(b>>>4)));
        res += nibbleToChar((byte)(0x0F&b));

        return res;
    }

    public static char nibbleToChar(byte b){
        if (b > 0x0F){
            return '!';
        }

        if(b < 10){
            return (char)(b+48);
        }

        b -= 10;

        return (char)(b+65);
    }

}
