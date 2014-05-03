
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.junit.Test;

import java.nio.charset.Charset;

import static org.junit.Assert.assertArrayEquals;

/**
 * Created by Stephen Yingling on 4/24/14.
 */
public class BCryptTest {


    @Test
    public void testBcrypt(){
        BCrypt bc = new BCrypt();
        byte[] salt = BCrypt.generateSalt();

        byte[] input = new String("Comme je descendais des Fleuves impassibles," +
                "Je ne me sentis plus guide par les haleurs :" +
                "Des Peaux-Rouges criards les avaient pris pour cibles" +
                "Les ayant cloues nus aux poteaux de couleurs").getBytes();

        byte[] res = bc.bCrypt(8,salt,input);

        String hashed = ByteUtils.byteArToStr(res);

        System.out.println(hashed);

        System.out.println(ByteUtils.byteArToHexStr(res));


        res = bc.bCrypt(8,salt,input);

        hashed = ByteUtils.byteArToStr(res);

        System.out.println(hashed);

        System.out.println(ByteUtils.byteArToHexStr(res));

        String pass = "password";
        System.out.println(Base64.encode(pass.getBytes()));
        input = Base64.encode(pass.getBytes()).getBytes();
        res = bc.bCrypt(8,salt,input);

        System.out.println();
        String enc;

        enc = new String(res, Charset.defaultCharset());

        

        hashed = new String(Base64.decode(enc), Charset.defaultCharset());

        System.out.println(hashed);

        System.out.println(ByteUtils.byteArToStr(salt));

        //System.out.println(ByteUtils.byteArToHexStr(res));



    }
}
