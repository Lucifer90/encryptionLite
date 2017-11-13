package it.fanciullini.crypting.service;

import it.fanciullini.crypting.pojo.PairKeys;
import it.fanciullini.crypting.utils.Costanti;
import org.encryptor4j.util.FileEncryptor;

import javax.crypto.*;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;

import static it.fanciullini.crypting.utils.PathConverter.convertPath;

public class Cryptography
{
    private Cipher cipher;
    private String safeWord;
    private String encryptedSafeWord;
    private PublicKey publicKey;


    public Cryptography()
            throws NoSuchAlgorithmException, NoSuchPaddingException, NoSuchProviderException, IllegalBlockSizeException,
            UnsupportedEncodingException, BadPaddingException, InvalidKeyException
    {
        this.cipher = Cipher.getInstance(Costanti.encrypting);
    }

    public String encryptText(String msg, PrivateKey key)
            throws NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException,
            BadPaddingException, InvalidKeyException
    {
        this.cipher.init(Cipher.ENCRYPT_MODE, key);
        return org.apache.commons.codec.binary.Base64.encodeBase64String(cipher.doFinal(msg.getBytes(Costanti.encoding)));
    }

    public String decryptText(String msg, PublicKey key)
            throws InvalidKeyException, UnsupportedEncodingException,
            IllegalBlockSizeException, BadPaddingException
    {
        this.cipher.init(Cipher.DECRYPT_MODE, key);
        return new String(cipher.doFinal(org.apache.commons.codec.binary.Base64.decodeBase64(msg)), Costanti.encoding);
    }

    private String safeWordGenerator()
    {
        // Genero la password
        PasswordGenerator passwordGenerator = new PasswordGenerator(52);
        return passwordGenerator.nextString();
    }

    @Deprecated
    private void encryptSafeWord(String safeWorld)
            throws NoSuchAlgorithmException, NoSuchPaddingException, NoSuchProviderException, IllegalBlockSizeException,
            UnsupportedEncodingException, BadPaddingException, InvalidKeyException
    {
        // Cripto la chiave
        GenerateKeys gk = new GenerateKeys(512);
        encryptedSafeWord = encryptText(safeWorld, gk.getPrivateKey());
        publicKey = gk.getPublicKey();
    }

    public static void encryptAndDecrypt(String toEncrypt){
        //separa toencrypt in path-file, replace solo file e rimergia
        String encrypted = convertPath(toEncrypt, Costanti.dot, Costanti.encryptedSpecial);
        PairKeys pk = justEncrypt(toEncrypt);
        justDecrypt(encrypted, pk.getSafeWordEncrypted(), java.util.Base64.getEncoder().encodeToString(pk.getPublicKey().getEncoded()));
    }

    public static PairKeys justEncrypt(String toEncrypt){
        PairKeys pk = null;
        String parentFolder = new File(toEncrypt).getParent();
        try {
            pk = encrypt(toEncrypt);
            pk.toFile(parentFolder);
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }
        return pk;
    }

    public static void justDecrypt(String toDecrypt, String safeWordEncrypted, String publicKey){
        PairKeys pk = new PairKeys();
        try {
            pk.setSafeWordEncrypted(safeWordEncrypted);
            pk.setPublicKey(publicKey);
            decrypt(pk.getSafeWordEncrypted(), pk.getPublicKey(), new File(toDecrypt));
            System.out.println(pk.toString());
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void justDecrypt(String toDecrypt){
        PairKeys pk = new PairKeys();
        File decrypted = new File(toDecrypt);
        String parentFolder = decrypted.getParent();
        try {
            pk.fromFile(parentFolder);
            decrypt(pk.getSafeWordEncrypted(), pk.getPublicKey(), decrypted);
            System.out.println(pk.toString());
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }
    }


    public static PairKeys encrypt(String pathToFile)
            throws GeneralSecurityException, IOException
    {

        Cryptography crypt = new Cryptography();

        String encr = convertPath(pathToFile, Costanti.dot, Costanti.encryptedSpecial);
        String password = crypt.safeWordGenerator();

        File srcFile = new File(pathToFile);
        File destFile = new File(encr);

        FileEncryptor fe = new FileEncryptor(password);
        fe.encrypt(srcFile, destFile);
        GenerateKeys gk = new GenerateKeys(512);
        String encryptedPassword = encryptKey(password, crypt, gk);

        PairKeys pk = new PairKeys();
        pk.setPublicKey(gk.getPublicKey());
        pk.setSafeWordEncrypted(encryptedPassword);
        return pk;
    }

    public static String encryptKey(String password, Cryptography crypt, GenerateKeys gk)
            throws NoSuchPaddingException, BadPaddingException,
            NoSuchAlgorithmException, IllegalBlockSizeException,
            UnsupportedEncodingException, InvalidKeyException,
            NoSuchProviderException {
        String encryptedPassword = crypt.encryptText(password, gk.getPrivateKey());
        return encryptedPassword;
    }

    public static void decrypt(String encryptedSafeWord, PublicKey publicKey, File encryptedFilePath)
            throws GeneralSecurityException,
            IOException
    {
        // Decripto il file
        Cryptography crypt = new Cryptography();
        String decryptedPassword = crypt.decryptText(encryptedSafeWord, publicKey);
        FileEncryptor fe = new FileEncryptor(decryptedPassword);
        String selector;
        if(encryptedFilePath.getPath().contains(Costanti.encryptedSpecial))
            selector = Costanti.encryptedSpecial;
        else
            selector = Costanti.dot;
        String decryptedFilePath = convertPath(encryptedFilePath.getPath(), selector, Costanti.decryptedSpecial);
        fe.decrypt(encryptedFilePath, new File(decryptedFilePath));
    }

}
