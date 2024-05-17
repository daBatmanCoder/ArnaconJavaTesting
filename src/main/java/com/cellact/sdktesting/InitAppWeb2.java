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

        String[] serviceProviders = Web3Service.getServiceProviderList();
        Web3Service.setServiceProvider(serviceProviders[0]);

            //hhg
        // Fetch store
        String ipfsContent = Web3Service.fetchStore();
        logger.debug("Store: " + ipfsContent);

        // // Choose a product
        Scanner scanner = new Scanner(System.in);
        logger.debug("Enter package you want:");
        String packageNum = scanner.nextLine();

        String successURL = "https://www.cellact.com";
        String cancelURL = "https://www.cellact.com";

        // Payment URL
        String url = Web3Service.getPaymentURL(
            packageNum,
            successURL,
            cancelURL
        );
        logger.debug("Payment URL: " + url);

        scanner.nextLine();
        scanner.close();
        
        String fcm_token = "Cl2fX1UQt-R7vY0NxmVV9:APA91bHZdgZ4XvSvq5FDTBI7SCMY1794XQEHpjYE9I_tUIgb5nqoyqt4xoSut_Il7wqW1pFOwV75I80-CvwkQ7NgCqTU9HWGUhp_TZbsbZS3NvxjNcYUTjpKghVxRijafkrE-Wn3hIRH";
        Web3Service.sendFCM(fcm_token);

        // String domainOfCallee = Web3Service.getCalleeDomain("dIamIORAGOdNDrDv");
        // logger.debug("Domain of callee: " + domainOfCallee);

        String XData = Web3Service.getXData();
        logger.debug("XData: " + XData);
        String XSign = Web3Service.getXSign(XData);
        logger.debug("XSign: " + XSign);


    }
}
