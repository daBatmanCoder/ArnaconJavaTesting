package com.cellact.sdktesting;

import java.util.Scanner;

import com.cellact.Config.ADataSaveHelper;
import com.cellact.Config.ALogger;


public class InitAppWeb2 {

    Web3AJ Web3Service;

    public InitAppWeb2() throws Exception {

        ADataSaveHelper dataSaveHelper = new SharedPreferencesHelper();
        ALogger logger = new Logger();

        Web3Service = new Web3AJ(dataSaveHelper, logger);
        System.out.println(Web3Service.wallet.getPublicKey());
        System.out.println(Web3Service.signMessage("hey"));
        // String[] serviceProviders = Web3Service.getServiceProviderList();
        // Web3Service.setServiceProvider(serviceProviders[0]);

        // // Fetch store
        // String ipfsContent = Web3Service.fetchStore();
        // System.out.println("Store: " + ipfsContent);

        // // // Choose a product
        // Scanner scanner = new Scanner(System.in);
        // System.out.println("Enter package you want: ");
        // String packageNum = scanner.nextLine();

        // String successURL = "https://www.cellact.com";
        // String cancelURL = "https://www.cellact.com";

        // // Payment URL
        // String url = Web3Service.getPaymentURL(
        //     packageNum,
        //     successURL,
        //     cancelURL
        // );
        // System.out.println("Payment URL: " + url);

        // scanner.nextLine();
        // scanner.close();
        // String fcm_token = "9c71ab46-370b-40f6-8235-bf1b03da1867";
        // Web3Service.sendFCM(fcm_token);

    }
}
