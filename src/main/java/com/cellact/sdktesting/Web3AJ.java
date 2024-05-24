package com.cellact.sdktesting;

import com.cellact.Config.ANetwork;
import com.cellact.Config.ADataSaveHelper;
import com.cellact.Config.ALogger;
import com.cellact.Config.AWeb3AJ;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.math.BigDecimal;
import java.math.BigInteger;

import java.net.HttpURLConnection;
import java.net.URL;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Arrays;
import java.util.UUID;


import java.time.Instant;

import org.web3j.crypto.Credentials;
import org.web3j.crypto.Sign;
import org.web3j.utils.Numeric;

import org.json.JSONArray;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.crypto.RawTransaction;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthEstimateGas;
import org.web3j.protocol.core.methods.response.EthGasPrice;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.response.PollingTransactionReceiptProcessor;


public class Web3AJ extends AWeb3AJ{

    Web3j web3j;
    Wallet wallet;
    ANetwork network; // Ethereum / Polygon / Binance Smart Chain

    // Constructor (with network specified)
    public Web3AJ(
        ADataSaveHelper dataSaveHelper, 
        ALogger logger
    ) {
        super(dataSaveHelper, logger); // Saves the logger and datahelper and init the cloudfunctions class with logger

        String privateKey = dataSaveHelper.getPreference("privateKey", null);
        if (privateKey != null){
            this.wallet = new Wallet(privateKey);
        }
        else{
            this.wallet = new Wallet(logger);
            dataSaveHelper.setPreference("privateKey", this.wallet.getPrivateKey());
        }

    }


    public String getXData(){
        // String xdata = dataSaveHelper.getPreference("xdata", null);

        // if (xdata != null){
        //     return xdata;
        // }

        String uuid = UUID.randomUUID().toString();
        long timestamp = Instant.now().toEpochMilli();
        String xdata = uuid + ":" + timestamp;
        // dataSaveHelper.setPreference("xdata", xdata);
        return xdata;
    }

    public String getXSign(String data){
        // String xsign = dataSaveHelper.getPreference("xsign", null);
        // if (xdata != null){
        //     return xdata;
        // }
        return signMessage(data);
    }
    
    // Takes a message and signs it with the private key of the current wallet
    public String signMessage(
        String Message
    ){

        String prefix = "\u0019Ethereum Signed Message:\n" + Message.length();
        String prefixedMessage = prefix + Message;

        Credentials credentials = wallet.getCredentials();

        byte[] messageBytes = prefixedMessage.getBytes();

        Sign.SignatureData signature = Sign.signMessage(messageBytes, credentials.getEcKeyPair());
        // logger.debug("Signature: " + Numeric.toHexString(signature.getR()));
        String sigHex = Numeric.toHexString(signature.getR()) 
                + Numeric.toHexStringNoPrefix(signature.getS()) 
                + Numeric.toHexStringNoPrefix(new byte[]{signature.getV()[0]});

        return sigHex;
    }

    // Encodes a function call to be used in a transaction
    private String encodeFunction(
        String functionName,
        @SuppressWarnings("rawtypes") List<Type> inputParameters,
        List<TypeReference<?>> outputParameters
    ) {
        
        Function function = new Function(
                functionName,
                inputParameters,
                outputParameters
        );

        return FunctionEncoder.encode(function);
        
    }

    // Mint an NFT - for the user wallet and ENS
    String mintNFT(
        String _userENS
    ) throws IOException {

        // Load your Ethereum wallet credentials
        Credentials credentials = Credentials.create(this.wallet.getPrivateKey());

        // Raw Transaction 
        TransactionManager transactionManager = new RawTransactionManager(
                web3j,
                credentials,
                network.getChainID(), // Chain ID for Polygon Mumbai Testnet
                new PollingTransactionReceiptProcessor(web3j, 1000, 60)
        );

        @SuppressWarnings("rawtypes")
        List<Type> inputParameters = Arrays.asList(new Utf8String(this.wallet.getPublicKey()), new Utf8String(_userENS));
        
        String encodedFunction = encodeFunction(
            "safeMint", // Function name
            inputParameters, // Function input parameters
            Collections.emptyList() // Function return types (empty for a transaction)
        );

        EthGasPrice ethGasPrice = web3j.ethGasPrice().send();
        BigInteger gasPrice = ethGasPrice.getGasPrice();
        BigInteger valueInWei = new BigInteger("0"); 
        BigInteger estimatedGasLimit = getEstimatedGasForStateChanging(
            credentials, 
            encodedFunction, 
            valueInWei, 
            gasPrice, 
            Utils.getContracts(logger).NAME_HASH_ADDRESS
        );

        EthSendTransaction response = transactionManager.sendTransaction(
                gasPrice,
                estimatedGasLimit,
                Utils.getContracts(logger).NAME_HASH_ADDRESS,
                encodedFunction,
                valueInWei
        );
        
        return response.getTransactionHash();
    }

    // Buys our ENS (for web2 users)
    String buyENS(
        String _userENS
    ) throws IOException {

            // Load your Ethereum wallet credentials
            Credentials credentials = Credentials.create(this.wallet.getPrivateKey());

            // Raw Transaction 
            TransactionManager transactionManager = new RawTransactionManager(
                    web3j,
                    credentials,
                    network.getChainID(), // Chain ID for Polygon Mumbai Testnet
                    new PollingTransactionReceiptProcessor(web3j, 1000, 60)
            );

            @SuppressWarnings("rawtypes")
            List<Type> inputParameters = Arrays.asList(new Address(this.wallet.getPublicKey()), new Utf8String(_userENS));
            
            String encodedFunction = encodeFunction(
                "safeMint", // Function name
                inputParameters, // Function input parameters
                Collections.emptyList() // Function return types (empty for a transaction)
            );

            EthGasPrice ethGasPrice = web3j.ethGasPrice().send();
            BigInteger gasPrice = ethGasPrice.getGasPrice();

            // Need to change this to MATIC (network currency let's say.)
            BigInteger valueInWei = new BigInteger("0"); 
            
            BigInteger estimatedGasLimit = getEstimatedGasForStateChanging(
                credentials,
                encodedFunction, 
                valueInWei, 
                gasPrice,
                Utils.getContracts(logger).W_ENS_ADDRESS
            );

            EthSendTransaction response = transactionManager.sendTransaction(
                    gasPrice,
                    estimatedGasLimit,
                    Utils.getContracts(logger).W_ENS_ADDRESS,
                    encodedFunction,
                    valueInWei 
            );
            
            return response.getTransactionHash();
        }
    
    // Helper function to estimate the gas limit for a state-changing function
    BigInteger getEstimatedGasForStateChanging(
        Credentials credentials,
        String encodedFunction, 
        BigInteger valueInWei,
        BigInteger gasPrice,
        String contractAddress
    ) throws IOException {

        BigInteger nonce = web3j.ethGetTransactionCount(
            credentials.getAddress(), 
            DefaultBlockParameterName.LATEST
        ).send().getTransactionCount();

        //  BigInteger gasPrice = web3j.ethGasPrice().send().getGasPrice();
        BigInteger gasLimitEstimation = BigInteger.valueOf(20000000);        

        // Create the transaction object for the state-changing function
        RawTransaction rawTransaction = RawTransaction.createTransaction(
                nonce,
                gasPrice,
                gasLimitEstimation,
                contractAddress,
                valueInWei,//BigInteger.ZERO, // Value in wei to send with the transaction
                encodedFunction
        );

        Transaction transaction = Transaction.createFunctionCallTransaction(
                credentials.getAddress(),
                rawTransaction.getNonce(),
                rawTransaction.getGasPrice(),
                rawTransaction.getGasLimit(),
                rawTransaction.getTo(),
                rawTransaction.getValue(),
                rawTransaction.getData()
        );


        // Estimate the gas limit for the transaction
        EthEstimateGas ethEstimateGas = web3j.ethEstimateGas(transaction).send();

        BigInteger estimatedGasLimit;

        if (ethEstimateGas.hasError()) {
            estimatedGasLimit = BigInteger.valueOf(2000000); // Fallback gas limit
        } else {
            estimatedGasLimit = ethEstimateGas.getAmountUsed();
        }
        
        return estimatedGasLimit;
    }

    public BigDecimal checkBalance(
        String publicKey
    ) throws IOException {
        
        EthGetBalance balance = web3j.ethGetBalance(publicKey, DefaultBlockParameterName.LATEST).send();
        BigInteger big_wei = balance.getBalance();
        // Optionally, convert the balance to Ether
        BigDecimal wei = new BigDecimal(big_wei);
        BigDecimal precise_balance = wei.divide(new BigDecimal("1000000000000000000")); 
        
        return precise_balance;
    }

    public String fetchStore() throws Exception {

        String serviceProviderName = dataSaveHelper.getPreference("serviceProviderName", null);
        logger.debug("Service Provider: " + serviceProviderName);

        String cid = Utils.getCloudFunctions(logger).getShopCID(serviceProviderName);

        // Use a public IPFS gateway to fetch the content. You can also use a local IPFS node if you have one running.
        String ipfsGateway = "https://ipfs.io/ipfs/";
        String ipfsLink = ipfsGateway + cid;
        @SuppressWarnings("deprecation")
        URL url = new URL(ipfsLink);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        // Check for successful response
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            dataSaveHelper.setPreference("store", content.toString());

            return content.toString(); // Returns the content of the file
        } else {
            throw new Exception("Failed to fetch content from IPFS. Response code: " + responseCode);
        }
    }

    public void sendFCM(
        String fcm_token
    ) {
        try{
            String ensJsonString = "";
            String ens = "";
            
            while (ensJsonString.isEmpty()) {

                ensJsonString = getENS();
                if (ensJsonString == null){
                    ensJsonString = "";
                }
            }

            // Parse the JSON string to a JSONArray
            JSONArray ensArray = new JSONArray(ensJsonString);

            if (ensArray != null && ensArray.length() > 0) {
                // Select a random ENS from the array
                int randomIndex = new Random().nextInt(ensArray.length()); // Get a random index
                ens = ensArray.getString(randomIndex); // Use the random index to select an ENS
                dataSaveHelper.setPreference("randomENS", ens);
            }

            String fcmTokenJson = "{\"fcm_token\": \"" + fcm_token + "\"}";
            String fcm_signed = signMessage(fcmTokenJson);
            
            Utils.getCloudFunctions(logger).sendFCM(fcmTokenJson, fcm_signed, ens);
            registerAyala();
        }
        catch(Exception e) {
            throw new RuntimeException("Error: " + e);
        }
    }

    public void registerAyala() {
        try{
            
            String ens = dataSaveHelper.getPreference("randomENS", null);
            if (ens == null){
                throw new RuntimeException("Error: ENS not found");
            }
            logger.debug("ENS: " + ens);

            String someData = getXData();
            String signedData = getXSign(someData);
            
            Utils.getCloudFunctions(logger).registerAyala(someData, signedData, ens);
        }
        catch(Exception e) {
            throw new RuntimeException("Error: " + e);
        }
    }

    public String getPaymentURL(
        String packageNum,
        String successDP,
         String cancelDP
    ) {
        return Utils.getPaymentURL(
            this.wallet.getPublicKey(),
            packageNum,
            dataSaveHelper.getPreference("store", packageNum), 
            successDP, 
            cancelDP,
            dataSaveHelper,
            logger
        );
    }

    public String[] getServiceProviderList(){
        CloudFunctions cloudFunctions = Utils.getCloudFunctions(logger);
        return cloudFunctions.getServiceProviderList();
    }

    public String getENS() {

        String ensList = getSavedENSList();

        if ( ensList != null){
            return ensList;
        }

        String ens = Utils.getCloudFunctions(logger).getUserENS(this.wallet.getPublicKey(), null);
        if (ens.equals("Error")){
            return null;
        }
        else{
            dataSaveHelper.setPreference("ens", ens);
        }
        return ens;
    }

    public String getSavedENSList(){
        return dataSaveHelper.getPreference("ens", null);
    }

    public String getENS(String customerID) {
        
        String ens = Utils.getCloudFunctions(logger).getUserENS(this.wallet.getPublicKey(),customerID);
        if (ens.equals("Error")){
            return null;
        }
        return ens;
    }

    public void setServiceProvider(
        String serviceProviderName
    ) {
        dataSaveHelper.setPreference("serviceProviderName", serviceProviderName);
    }

    public String getCalleeDomain(String callee) {
        return Utils.getCloudFunctions(logger).getCalleeDomain(callee);
    }


    public void prepareForProof() {
        
        String nullifier = "8273492374982734987293479283749237498231231231234";// Generate here a byte 31 crypto bytes
        String Secret = "8273492374982734987293479283749237498234" ;// Generate here a byte 31 crypto bytes
        dataSaveHelper.setPreference("nullifier", nullifier);
        dataSaveHelper.setPreference("secret", Secret);

    }


    public void send_mediator(String url) {
        if (
            dataSaveHelper.getPreference("secret", null) == null && 
            dataSaveHelper.getPreference("nullifier", null) == null) {
                prepareForProof();
        }

        String secret = dataSaveHelper.getPreference("secret", null);
        String nullifier = dataSaveHelper.getPreference("nullifier", null );
        
        Utils.getCloudFunctions(logger).openMedianShop(url,secret,nullifier);
    }
}

