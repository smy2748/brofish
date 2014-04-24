import java.math.BigInteger;
import java.lang.Long;
import java.security.SecureRandom;

/**
 * Created by Stephen Yingling on 4/18/14.
 */
public class BCrypt {
    protected static final byte[] OBSD =
            stringToBArray("OrpheanBeholderScryDoubt");

    public BCrypt(){

    }

    public byte[] bCrypt(int cost, byte[] salt, byte[] input){
        BCryptFish b = EksBlowfishSetup(cost,salt,input);
        byte[] test = OBSD;
        byte[] ctext = OBSD.clone();
        for(int i=0; i<64;i++){
           ctext =  b.EncryptECB(ctext);
        }

        return ByteUtils.concat(salt,ctext);
    }


    private BCryptFish EksBlowfishSetup(int cost, byte[] salt, byte[] key){
        byte[] zero = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
        BCryptFish b = InitState();
        b = expandKey(b,salt,key);

        BigInteger times = BigInteger.valueOf(2).pow(cost);

        for(BigInteger cur = BigInteger.ZERO;
            cur.compareTo(times) < 0;
            cur = cur.add(BigInteger.ONE)){
            b = expandKey(b,zero,key);
            b = expandKey(b,zero,salt);
        }

        return b;
    }

    private BCryptFish InitState(){
        BCryptFish b = new BCryptFish();
        return b;
    }

    public static byte[] getSalt(){
        SecureRandom sr = new SecureRandom();
        byte[] res = new byte[16];
        sr.nextBytes(res);
        return res;
    }


    private BCryptFish expandKey(BCryptFish b, byte[] salt, byte[] key){
        long first = bytesToLong(salt);
        byte[] lower = {salt[8], salt[9],salt[10],salt[11],
                        salt[12],salt[13],salt[14],salt[15]};
        long last = bytesToLong(lower);

        b.expandKey(first,last,key);
        return b;
    }


    private long bytesToLong(byte[] bytes){
        long res = 0;
        int bInL = Long.SIZE/8;
        for(int i=0; i < bInL; i++){
            res <<=8;
            res |= bytes[i];
        }
        return res;
    }

    public static byte[] stringToBArray(String s){
        if(s.length()<1){
            return null;
        }
        byte[] res = new byte[s.length()];
        char[] chars = s.trim().toCharArray();

        for(int i=0; i<s.length(); i++){
            res[i] = (byte) chars[i];
        }

        return res;
    }
}
