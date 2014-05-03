/**
 * An extension of the Blowfish class to allow for the BCrypt algorithm to
 * be performed.
 * @author Stephen Yingling
 */
public class BCryptFish extends Blowfish{

    public BCryptFish(){}

    /**
     * Perform the expandKey function as detailed in the bCrypt spec
     * @param uSalt The upper 64 bits of the salt
     * @param lSalt The lower 64 bits of the salt
     * @param key The key being used at this step
     */
    public void expandKey(long uSalt, long lSalt, byte[] key){
        setSalt(uSalt,lSalt);
        xorPArray(key);
        encryptPAndS();
    }

    /**
     * Set the value of the salt for this algorithm
     * @param first The upper 64 bits of the salt
     * @param second The lower 64 bits of the salt
     */
    public void setSalt(long first, long second){
        uSalt = first;
        lSalt = second;
    }

    /**
     * Encrypt the given bytes using Electronic Codebook convention
     * @param in The bytes to encrypt
     * @return The encrypted bytes
     */
    public byte[] EncryptECB(byte[] in){
        byte[] res = new byte[in.length], cur;
        long plain;

        for(int i=0; (i+7)<in.length; i += 8){
            cur = ByteUtils.extractBytes(in, i, i + 8);
            plain = ByteUtils.bytesToLong(cur);
            plain = encrypt(plain);
            ByteUtils.copyTo(ByteUtils.longToBytes(plain),0,8,res,i);
        }

        return res;
    }
}
