package com.cellact.sdktesting;

import java.util.Scanner;

import com.cellact.Config.ADataSaveHelper;
import com.cellact.Config.ALogger;


public class InitAppWeb2 {

    Web3AJ Web3Service;

    public InitAppWeb2() throws Exception {

        ADataSaveHelper dataSaveHelper = new SharedPreferencesHelper();
        // dataSaveHelper.resetPreferences();

        ALogger logger = new Logger();

        Web3Service = new Web3AJ(dataSaveHelper, logger);

        logger.debug("Public key: " + Web3Service.wallet.getPublicKey());  

        // Scanner scanner = new Scanner(System.in);


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

        logger.debug(Web3Service.getENS("cus_QHksy4VLT6WoNP"));


        // String password = "123123";
        // String ciphertextHex = "b035d38ea08b1fcce1a47ca476734c80ceaa02a6f15bc922ff91663116650ec837087c1c3d7c6f6b814b75e01c40dd4bda24b8d168041f0a34558f341fda55de2c8a8e0fd77a9a07dd8546c98f545cb8793efc20f8c8df4312d20122775c6ed0e7db02c0ef0b3942aac23310a27bf0f4152a8c88191b8938440485e62c48ae67";
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
