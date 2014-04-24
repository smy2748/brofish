import org.junit.Test;

import java.security.SecureRandom;

/**
 * Created by Stephen Yingling on 4/24/14.
 */
public class BCryptTest {


    @Test
    public void testBcrypt(){
        BCrypt bc = new BCrypt();
        byte[] salt = BCrypt.getSalt();

        byte[] input = BCrypt.stringToBArray("p@ss word1!");

        byte[] res = bc.bCrypt(8,salt,input);

        String hashed = ByteUtils.byteArToStr(res);

        System.out.println(hashed);

        System.out.println(ByteUtils.byteArToHexStr(res));
    }
}
