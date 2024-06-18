package com.cellact.sdktesting;

import java.util.Scanner;

import com.cellact.Config.ADataSaveHelper;
import com.cellact.Config.ALogger;


public class InitAppWeb2 {

    Web3AJ Web3Service;

    public InitAppWeb2() throws Exception {

        ADataSaveHelper dataSaveHelper = new SharedPreferencesHelper();
        dataSaveHelper.resetPreferences();

        ALogger logger = new Logger();

        Web3Service = new Web3AJ(dataSaveHelper, logger);



        logger.debug("Public key: " + Web3Service.wallet.getPublicKey());  
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();


        logger.debug(Web3Service.getCalleDomainDirect("9LJx5uElxhvhnfMW.cellact"));
        scanner.nextLine();

        long startTime = System.currentTimeMillis(); // Add this line
        logger.debug(Web3Service.getCalleDomainDirect("9LJx5uElxhvhnfMW.cellact"));
        long endTime = System.currentTimeMillis(); // Add this line
        long executionTime = endTime - startTime; // Calculate the execution time
        logger.debug("Execution time of local call to contract: " + executionTime + " milliseconds"); // Log the execution time

        scanner.nextLine();

        startTime = System.currentTimeMillis(); // Add this line

        logger.debug(Web3Service.getCalleeDomain("9LJx5uElxhvhnfMW.cellact"));

        endTime = System.currentTimeMillis(); // Add this line
        executionTime = endTime - startTime; // Calculate the execution time
        logger.debug("Execution time for the google cloud function: " + executionTime + " milliseconds"); // Log the execution time


        // Rest of the code.
        // String[] serviceProviders = Web3Service.getServiceProviderList(); // Change for a generic URL for the service provider 
        // int counter = 1;
        // for (String provider : serviceProviders) {
        //     logger.debug("Service provider: " + counter + "." + provider);
        //     counter++;
        // }
        // logger.debug("Enter service provider you want:");
        // String serviceProvider = scanner.nextLine();
        // Web3Service.setServiceProvider(serviceProviders[Integer.parseInt(serviceProvider) - 1]);

        // // Fetch store
        // String ipfsContent = Web3Service.fetchStore();
        // logger.debug("Store: " + ipfsContent);

        // // // Choose a product
        // logger.debug("Enter package you want:");
        // String packageNum = scanner.nextLine();

        // String successURL = "https://www.cellact.com";
        // String cancelURL = "https://www.cellact.com";

        // // Payment URL
        // String url = Web3Service.getPaymentURL(
        //     packageNum,
        //     successURL,
        //     cancelURL
        //     // commitment
        // );

        // // window.href(url);
        // logger.debug("Payment URL: " + url);

        // scanner.nextLine();
        // scanner.close();
        
        // String fcm_token = "Cl2fX1UQt-R7vY0NxmVV9:APA91bHZdgZ4XvSvq5FDTBI7SCMY1794XQEHpjYE9I_tUIgb5nqoyqt4xoSut_Il7wqW1pFOwV75I80-CvwkQ7NgCqTU9HWGUhp_TZbsbZS3NvxjNcYUTjpKghVxRijafkrE-Wn3hIRH";
        // Web3Service.sendDirectFCM(fcm_token);

        // logger.debug(Web3Service.getENS("cus_QHksy4VLT6WoNP"));

        // Web3Service.saveProduct("ANONYMOUS");
        // Web3Service.saveProduct("83745837458");
        // Web3Service.saveProduct("83745837458123");


        // Web3Service.getFreeProduct();


        // String password = "123123";
        // String ciphertextHex = "b035d38ea08b1fcce1a47ca476734c805dec5c36582257c047f6ce4ead5043541c06d17e5d6cdd85278ce8552dafa97cc2b790e308bcd406d1237c569e929f6571f6c04129ce2dd9e8d31c6a95a9216505be20e350588e1e631e23c8b577054512c694e427028b45482e694fcd76d74b36beaa6d30d6ec06ffbbd97db601bbd5";
        // String decryptedHex = Web3Service.updateNewProduct(password, ciphertextHex);
        // System.out.println("Decrypted hex: " + decryptedHex);

        // String domainOfCallee = Web3Service.getCalleeDomain("WcF94LY7UTmZ59cm.cellact");
        // logger.debug("Domain of callee: " + domainOfCallee);
 
        // String XData = Web3Service.getXData();
        // logger.debug("XData: " + XData);
        // String XSign = Web3Service.getXSign(XData);
        // logger.debug("XSign: " + XSign);

    }
}
