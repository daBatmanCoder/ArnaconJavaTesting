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

        System.out.println(Web3Service.wallet.getPublicKey());
        System.out.println(Web3Service.wallet.getPrivateKey());
        
        String[] serviceProviders = Web3Service.getServiceProviderList();
        Web3Service.setServiceProvider(serviceProviders[0]);


        // Fetch store
        String ipfsContent = Web3Service.fetchStore();
        System.out.println("Store: " + ipfsContent);

        // // Choose a product
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter package you want: ");
        String packageNum = scanner.nextLine();

        String successURL = "https://www.cellact.com";
        String cancelURL = "https://www.cellact.com";

        // Payment URL
        String url = Web3Service.getPaymentURL(
            packageNum,
            successURL,
            cancelURL
        );
        System.out.println("Payment URL: " + url);

        scanner.nextLine();
        scanner.close();
        
        String fcm_token = "Cl2fX1UQt-R7vY0NxmVV9:APA91bHZdgZ4XvSvq5FDTBI7SCMY1794XQEHpjYE9I_tUIgb5nqoyqt4xoSut_Il7wqW1pFOwV75I80-CvwkQ7NgCqTU9HWGUhp_TZbsbZS3NvxjNcYUTjpKghVxRijafkrE-Wn3hIRH";
        Web3Service.sendFCM(fcm_token);

        String domainOfCallee = Web3Service.getCalleeDomain("dIamIORAGOdNDrDv");
        System.out.println("Domain of callee: " + domainOfCallee);

        // String XData = Web3Service.getXData();
        // System.out.println("XData: " + XData);
        // String XSign = Web3Service.getXSign(XData);
        // System.out.println("XSign: " + XSign);

    }
}
