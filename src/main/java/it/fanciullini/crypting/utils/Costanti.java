package it.fanciullini.crypting.utils;

public class Costanti {

    public static final String allMethod = "ALL";
    public static final String encryptMethod = "ENCRYPT";
    public static final String decryptMethod = "DECRYPT";

    public static final String encoding = "UTF-8";
    public static final String encrypting = "RSA";

    public static final String safeWordPlaceholder = "safeWordEncrypted";
    public static final String publicKeyPlaceholder = "publicKey";

    public static final String newLine = "\n";
    public static final String dot = ".";
    public static final String encryptedSpecial = "_encrypted.";
    public static final String decryptedSpecial = "_decrypted.";

    public static final String keysFilename = "keysContainer.json";

    public static final String selectedFile = "Selected File: %s";
    public static final String encryptSuccess = "Encryption successful";
    public static final String decryptSuccess = "Decryption successful";
    public static final String testSuccess = "Test successful";

    public static final String unplannedError = "Unplanned error";

    public static final String alertNoKeys = "Attention: In Manual mode please insert both public key and safe word.";
    public static final String alertNoPath = "Attention: Always insert path to file.";

    public static final String howTo_eng = "HOWTO" +
            "\nSTEP 1: Select a file" +
            "\nSTEP 2: Select operation:" +
            "\n\t-ENCRYPT: Encryption of a file, and create a JSON file containing keys for decryption" +
            "\n\t-DECRYPT: Decrypt a file, need json containing the correct keys" +
            "\n\t-TEST: Encrypt and Decrypt a file";

    public static final String howTo_ita = "Guida" +
            "\nSTEP 1: Seleziona un file" +
            "\nSTEP 2: Seleziona un'operazione:" +
            "\n\t-ENCRYPT: Cripta un file, e crea un JSON che contiene le chiavi di criptazione" +
            "\n\t-DECRYPT: Decripta un file, ha bisogno dell'inserimento manuale delle chiavi o di un file JSON,"
            + "\n\t\tdi default cerca keysContainer.json allo stesso livello del file" +
            "\n\t-TEST: Cripta e decripta un file.";


}
