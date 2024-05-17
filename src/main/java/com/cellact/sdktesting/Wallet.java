package com.cellact.sdktesting;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.MGF1ParameterSpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

import java.security.KeyFactory;
import javax.crypto.Cipher;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;

import org.web3j.crypto.Bip32ECKeyPair;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.MnemonicUtils;
import org.web3j.utils.Numeric;

import com.cellact.Config.ALogger;

public class Wallet {

    String mnemonic;
    String privateKey;
    String publicKeyRSA;
    String privateKeyRSA;
    ALogger logger;

    private static final int[] DERIVATION_PATH = {44 | Bip32ECKeyPair.HARDENED_BIT, 60 | Bip32ECKeyPair.HARDENED_BIT, 0 | Bip32ECKeyPair.HARDENED_BIT, 0, 0};

    // Constructor with no private key
    public Wallet(ALogger logger) {
        this.logger = logger;
        this.mnemonic = generateMnemonic();
        generateKeyPair(logger);
        this.privateKey = getPrivateKeyFromMnemonic(this.mnemonic);
    }

    // Constructor with private key - for wallet import
    public Wallet(String privateKey){
        this.mnemonic = generateMnemonic(); // That's not right... 
        this.privateKey = privateKey;
    }

    // Returns the mnemonic 
    public String getPrivateKey() {
        return this.privateKey;
    }

    // Generates a private key  
    static String generatePrivateKey(){
        return getPrivateKeyFromMnemonic(generateMnemonic());
    }

    // Checks if a mnemonic is valid
    static boolean isValidMnemonic(String mnemonic) {
        return MnemonicUtils.validateMnemonic(mnemonic);
    }

    // Generates a mnemonic
    static String generateMnemonic() {
        byte[] entropy = new byte[16]; // 128 bits
        new SecureRandom().nextBytes(entropy);
        return MnemonicUtils.generateMnemonic(entropy);
    }

    // Returns the private key from the mnemonic
    static String getPrivateKeyFromMnemonic(String mnemonic) {
        byte[] seed = MnemonicUtils.generateSeed(mnemonic, null);
        Bip32ECKeyPair masterKeypair = Bip32ECKeyPair.generateKeyPair(seed);
        Bip32ECKeyPair derivedKeyPair = Bip32ECKeyPair.deriveKeyPair(masterKeypair, DERIVATION_PATH);
        return Numeric.toHexStringNoPrefix(derivedKeyPair.getPrivateKey());
    }

    // Returns the public key from the private key
    public String getPublicKey() {
        Credentials credentials = Credentials.create(this.privateKey);
        return credentials.getAddress();
    }

    // Returns the wallet credentials
    public Credentials getCredentials() {
        return Credentials.create(this.privateKey);
    }

    public void generateKeyPair(ALogger logger) {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);  // You can use 1024 or 2048 for RSA key size
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            this.privateKeyRSA = convertToPemFormat(keyPair.getPrivate());
            this.privateKeyRSA = convertToPemFormat(keyPair.getPublic());
            keyPair.getPrivate();
        } catch (NoSuchAlgorithmException e) {
            logger.error("Failed to generate key pair: ", e);
        }
    }

    public String getPublicKeyRSA() {
        return this.publicKeyRSA;
    }

    public String decrypt(String encryptedData, ALogger logger) {
        try {
            PrivateKey privateKey = loadPrivateKey(this.privateKeyRSA);
            return decryptData(privateKey, encryptedData);
        } catch (Exception e) {
            logger.error("Failed to decrypt data: ", e);
            return null;
        }
    }

    private static String decryptData(PrivateKey privateKey, String encryptedData) throws Exception {
        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedData);
        Cipher decryptCipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
        decryptCipher.init(Cipher.DECRYPT_MODE, privateKey, new OAEPParameterSpec("SHA-256", "MGF1", MGF1ParameterSpec.SHA256, PSource.PSpecified.DEFAULT));
        return new String(decryptCipher.doFinal(encryptedBytes));

    }
    
    /**
     * Converts a PEM formatted private key to a PrivateKey object.
     *
     * @param privateKeyPEM The private key in PEM format.
     * @return A PrivateKey object.
     * @throws Exception If any error occurs during key conversion.
     */
    private static PrivateKey loadPrivateKey(String privateKeyPEM) throws Exception {
        String privateKeyContent = cleanPemKey(privateKeyPEM);
        byte[] pkcs8EncodedBytes = Base64.getDecoder().decode(privateKeyContent);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(pkcs8EncodedBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }

    public static String cleanPemKey(String pemkey){
        // Remove the first and last lines of the PEM string (header and footer)
        return pemkey
                .replaceAll("-----BEGIN RSA PUBLIC KEY-----", "")
                .replaceAll("-----END RSA PUBLIC KEY-----", "")
                .replaceAll("-----BEGIN RCA PRIVATE KEY-----", "")
                .replaceAll("-----END RCA PRIVATE KEY-----", "")
                .replaceAll("\\s", ""); // Remove whitespace characters
    }

    private static String convertToPemFormat(Object key) {
        String base64Encoded = "";

        if (key instanceof RSAPublicKey) {
            base64Encoded = Base64.getEncoder().encodeToString(((RSAPublicKey) key).getEncoded());
        } else if (key instanceof RSAPrivateKey) {
            base64Encoded = Base64.getEncoder().encodeToString(((RSAPrivateKey) key).getEncoded());
        }

        return base64Encoded; //formatPEMString(base64Encoded)
    }


    
}
