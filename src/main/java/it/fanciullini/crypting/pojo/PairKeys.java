package it.fanciullini.crypting.pojo;
import it.fanciullini.crypting.utils.Costanti;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;

import static it.fanciullini.crypting.utils.PublicKeyConverter.publicKeyToString;
import static it.fanciullini.crypting.utils.PublicKeyConverter.stringToPublicKey;

public class PairKeys {

    private String safeWordEncrypted;
    private PublicKey publicKey;

    public String getSafeWordEncrypted() {
        return safeWordEncrypted;
    }

    public void setSafeWordEncrypted(String safeWordEncrypted) {
        this.safeWordEncrypted = safeWordEncrypted;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public void setPublicKey(String pubKey)
            throws NoSuchAlgorithmException, InvalidKeySpecException
    {
        publicKey = stringToPublicKey(pubKey);
    }

    @Override
    public String toString(){
        return Costanti.safeWordPlaceholder+": "+safeWordEncrypted
                +"\n"+Costanti.publicKeyPlaceholder+": "+publicKeyToString(publicKey);
    }

    @SuppressWarnings("unchecked")
    public void toFile(String path)
            throws IOException
    {
        JSONObject outputMap = new JSONObject();
        outputMap.put(Costanti.safeWordPlaceholder, safeWordEncrypted);
        outputMap.put(Costanti.publicKeyPlaceholder, publicKeyToString(publicKey));

        try (FileWriter file = new FileWriter(Paths.get(path, Costanti.keysFilename).toString())) {
            file.write(outputMap.toJSONString());
            file.flush();
        }
    }

    public void fromFile(String path)
        throws IOException
    {
        JSONParser parser = new JSONParser();

        try {
            FileReader fileReader = new FileReader(Paths.get(path).toString());
            Object obj = parser.parse(fileReader);
            fileReader.close();
            JSONObject jsonObject = (JSONObject) obj;
            String safe = (String) jsonObject.get("name");

            safeWordEncrypted = (String) jsonObject.get(Costanti.safeWordPlaceholder);
            publicKey = stringToPublicKey((String) jsonObject.get(Costanti.publicKeyPlaceholder));

        } catch (Exception ex) {
            throw new IOException();
        }
    }

}
