package lab.contract.encryption;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class Aes256Utils {
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/CBC/PKCS5PADDING";
    static final Decoder DECODER = Base64.getDecoder();
    static final Encoder ENCODER = Base64.getEncoder();
    private final SecretKeySpec keySpec;
    private final byte[] iv;
    private final String encodedIv;

    //@Value("${aes256.secret-key}")
    //private String secretKey;

    public Aes256Utils() {
        byte[] key = DECODER.decode("a1s2d3f4g5h6j7k8l9z0xcvbnmqwerty");
        keySpec = new SecretKeySpec(key, ALGORITHM);
        SecureRandom random = new SecureRandom();
        iv = new byte[16];
        random.nextBytes(iv);
        this.encodedIv = ENCODER.encodeToString(iv);
    }

    @SneakyThrows
    String encrypt(String plainText) {
        Cipher cipher = getCipher(encodedIv, Cipher.ENCRYPT_MODE);
        byte[] encrypted = cipher.doFinal(plainText.getBytes());
        return ENCODER.encodeToString(encrypted);
    }

    @SneakyThrows
    String decrypt(String cipherText) {
        Cipher cipher = getCipher(encodedIv, Cipher.DECRYPT_MODE);
        byte[] encrypted = DECODER.decode(cipherText);
        byte[] decrypted = cipher.doFinal(encrypted);
        return new String(decrypted);
    }

    private Cipher getCipher(String encodedIv, int decryptMode)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        IvParameterSpec ivSpec = new IvParameterSpec(DECODER.decode(encodedIv));
        cipher.init(decryptMode, keySpec, ivSpec);
        return cipher;
    }
}

