package it.fanciullini.crypting.utils;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class PublicKeyConverter {

    public static String publicKeyToString(PublicKey publicKey){
        return Base64.getEncoder().encodeToString(publicKey.getEncoded());
    }

    public static PublicKey stringToPublicKey(String publicKeyString)
            throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        byte[] publicBytes = org.apache.commons.codec.binary.Base64.decodeBase64(publicKeyString);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(Costanti.encrypting);
        return keyFactory.generatePublic(keySpec);
    }

}
