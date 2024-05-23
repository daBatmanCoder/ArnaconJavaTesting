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

        // open URL Here-
        // 

        String[] serviceProviders = Web3Service.getServiceProviderList(); // Change for a generic URL for the service provider 
        int counter = 1;
        for (String provider : serviceProviders) {
            logger.debug("Service provider: " + counter + "." + provider);
            counter++;
        }
        logger.debug("Enter service provider you want:");
        String serviceProvider = scanner.nextLine();
        Web3Service.setServiceProvider(serviceProviders[Integer.parseInt(serviceProvider) - 1]);

        // Fetch store
        String ipfsContent = Web3Service.fetchStore();
        logger.debug("Store: " + ipfsContent);

        // generate_commitment(nullifier,secret)

        // // Choose a product
        logger.debug("Enter package you want:");
        String packageNum = scanner.nextLine();

        String successURL = "https://www.cellact.com";
        String cancelURL = "https://www.cellact.com";

        // Payment URL
        String url = Web3Service.getPaymentURL(
            packageNum,
            successURL,
            cancelURL
            // commitment
        );

        // window.href(url);
        logger.debug("Payment URL: " + url);

        // send_mediator(url,commitment)

        scanner.nextLine();
        scanner.close();
        
        String fcm_token = "Cl2fX1UQt-R7vY0NxmVV9:APA91bHZdgZ4XvSvq5FDTBI7SCMY1794XQEHpjYE9I_tUIgb5nqoyqt4xoSut_Il7wqW1pFOwV75I80-CvwkQ7NgCqTU9HWGUhp_TZbsbZS3NvxjNcYUTjpKghVxRijafkrE-Wn3hIRH";
        Web3Service.sendFCM(fcm_token);

        String domainOfCallee = Web3Service.getCalleeDomain("dIamIORAGOdNDrDv");
        logger.debug("Domain of callee: " + domainOfCallee);
        long startTime = System.nanoTime();
 
        String XData = Web3Service.getXData();
        logger.debug("XData: " + XData);
        String XSign = Web3Service.getXSign(XData);
        logger.debug("XSign: " + XSign);

        // End time
        long endTime = System.nanoTime();

        // Calculate the duration
        long duration = (endTime - startTime);  // Duration in nanoseconds

        // Convert nanoseconds to milliseconds
        System.out.println("Execution time of yourFunction: " + duration / 1_000_000 + " milliseconds");

    }
}
