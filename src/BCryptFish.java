/**
 * Created by Stephen Yingling on 4/18/14.
 */
public class BCryptFish extends Blowfish{

    public BCryptFish(){}

    public void expandKey(long uSalt, long lSalt, byte[] key){
        setSalt(uSalt,lSalt);
        xorPArray(key);
        encryptPAndS();
    }

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
