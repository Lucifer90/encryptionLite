package it.fanciullini.crypting;

import it.fanciullini.crypting.pojo.PairKeys;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;

import static it.fanciullini.crypting.service.Cryptography.decrypt;
import static it.fanciullini.crypting.service.Cryptography.encrypt;


public class UnitTest {

    private String filename;
    private static PairKeys pk;

    /*@Test
    public void testEncryptionAndDecryptionSmallFile()
            throws GeneralSecurityException, IOException
    {
        filename = "witcherSet.zip";
        PairKeys pk = testEncrypt();
        testDecrypt(pk);
    }*/

    @Test
    public void testEncryptionAndDecryptionBigFile()
            throws GeneralSecurityException, IOException
    {
        filename = "testFile.zip";
        PairKeys pk = testEncrypt();
        testDecrypt(pk);
    }

    public PairKeys testEncrypt()
        throws GeneralSecurityException, IOException
    {
        System.out.println("0 - Inizio Test");
        String pathToUnecrypted = getClass().getClassLoader().getResource(filename).getPath();
        return encrypt(pathToUnecrypted);
    }

    public void testDecrypt(PairKeys pk)
            throws GeneralSecurityException, IOException
    {
        String pathToUnecrypted = getClass().getClassLoader().getResource(filename).getPath();
        String toDecrypt = pathToUnecrypted.replace(".", "_encrypted.");
        decrypt(pk.getSafeWordEncrypted(), pk.getPublicKey(), new File(toDecrypt));
    }
}
