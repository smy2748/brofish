import java.math.BigInteger;
import java.lang.Long;
import java.security.SecureRandom;

/**
 * An implementation of the BCrypt algorithm
 * @author Stephen Yingling
 */
public class BCrypt {
    //Default string as described in the specification
    protected static final byte[] OBSD =
            ("OrpheanBeholderScryDoubt").getBytes();

    /**
     * Creates a new instance of BCrypt
     */
    public BCrypt(){
    }

    /**
     * Runs the bcrypt hashing algorithm
     * @param cost The cost of the algorithm.  More specifically the value of n such that the number of
     *             operations to hash the input is 2^n
     * @param salt The salt to use for the hash.  Likely a random bunch of bits.
     * @param input The input to hash.
     * @return The hashed input as a byte array
     */
    public byte[] bCrypt(int cost, byte[] salt, byte[] input){
        BCryptFish b = EksBlowfishSetup(cost,salt,input);
        byte[] test = OBSD;
        byte[] ctext = OBSD.clone();
        for(int i=0; i<64;i++){
           ctext =  b.EncryptECB(ctext);
        }

        return ByteUtils.concat(salt,ctext);
    }

    /**
     * Perform the setup operation of the BCrypt algorithm with BLowfish
     * @param cost The cost of the algorithm.
     * @param salt The salt to use.
     * @param key The key to use for the underlying block cipher.  In the
     *            BCrypt specification it is listed as the input
     * @return The BCryptfish object with the proper initilization.
     */
    protected BCryptFish EksBlowfishSetup(int cost, byte[] salt, byte[] key){
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

    /**
     * Creates and returns the initial state of a BCryptFish object
     * @return A BCryptFish object in its initial state
     */
    protected BCryptFish InitState(){
        BCryptFish b = new BCryptFish();
        return b;
    }

    /**
     * Generates and returns a random salt
     * @return An array of 16 random bytes
     */
    public static byte[] generateSalt(){
        SecureRandom sr = new SecureRandom();
        byte[] res = new byte[16];
        sr.nextBytes(res);
        return res;
    }

    /**
     * Expand the key as described in the BCrypt specification
     * @param b The instance holding the current configuration
     * @param salt The "salt" used in key expansion
     * @param key The "key" used in key expansion
     * @return The configuration from applying the key expansion
     */
    protected BCryptFish expandKey(BCryptFish b, byte[] salt, byte[] key){
        long first = ByteUtils.bytesToLong(salt);
        byte[] lower = {salt[8], salt[9],salt[10],salt[11],
                        salt[12],salt[13],salt[14],salt[15]};
        long last = ByteUtils.bytesToLong(lower);

        b.expandKey(first,last,key);
        return b;
    }

}
