package Composant;
 
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class Licence
{

static final byte k[] = "lixiante".getBytes();
static SecretKeySpec myDesKey = new SecretKeySpec(k,"DES");
static Cipher desCipher;

public String DESencryption(byte [] text){
    try{

    // Create the cipher
    desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
    // Initialize the cipher for encryption
    desCipher.init(Cipher.ENCRYPT_MODE, myDesKey);
    byte[] textEncrypted = desCipher.doFinal(text);

    return new sun.misc.BASE64Encoder().encode(textEncrypted);
    }catch(NoSuchAlgorithmException e){
    e.printStackTrace();
    }catch(NoSuchPaddingException e){
    e.printStackTrace();
    }catch(InvalidKeyException e){
    e.printStackTrace();
    }catch(Exception e){
    }
    
    return null;
}

public String DESdecrypt(String str){
    try {

    byte[] textencrypted = new sun.misc.BASE64Decoder().decodeBuffer(str);

    // Create the cipher
    desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
    // Initialize the same cipher for decryption
    desCipher.init(Cipher.DECRYPT_MODE, myDesKey);
    // Decrypt the text
    byte[] textDecrypted = desCipher.doFinal(textencrypted);

    return new String(textDecrypted,"UTF-8");
    }catch(NoSuchAlgorithmException e){
    e.printStackTrace();
    }catch(NoSuchPaddingException e){
    e.printStackTrace();
    }catch(InvalidKeyException e){
    e.printStackTrace();
    }catch(Exception e){
    }
    return null;
    }

public boolean check(String key, String encryptedData){
    if (key.equals(encryptedData))
        return true;
    else
        return false;    

}

public void genLicence(String input){
    byte[] dataEncrypted;
    String dataDecrypted = "";
    
try{
dataEncrypted = input.getBytes("UTF-8");

//System.out.println("encrypted information :"+DESencryption(dataEncrypted));


}catch(Exception e){

}

}

public boolean checkKeys(String input, String licence, String username, String password){

    
    String dataDecrypted = "";
    
    
    try{
        dataDecrypted = this.DESdecrypt(licence);
        //System.out.println("decrypted information :"+dataDecrypted);

        if (dataDecrypted.equals(input))
            return true;
        else
            return false;    

    }catch(Exception e){
        
    }    
    return false;

}



}