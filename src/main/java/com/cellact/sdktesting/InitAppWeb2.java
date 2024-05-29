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

        // Scanner scanner = new Scanner(System.in);

        // open URL Here-
        // 

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

        // // generate_commitment(nullifier,secret)
        // // Web3Service.prepareForProof();

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

        // // Web3Service.send_mediator(url);

        // scanner.nextLine();
        // scanner.close();
        
        // String fcm_token = "Cl2fX1UQt-R7vY0NxmVV9:APA91bHZdgZ4XvSvq5FDTBI7SCMY1794XQEHpjYE9I_tUIgb5nqoyqt4xoSut_Il7wqW1pFOwV75I80-CvwkQ7NgCqTU9HWGUhp_TZbsbZS3NvxjNcYUTjpKghVxRijafkrE-Wn3hIRH";
        // Web3Service.sendFCM(fcm_token);

        // String domainOfCallee = Web3Service.getCalleeDomain("WcF94LY7UTmZ59cm.cellact");
        // logger.debug("Domain of callee: " + domainOfCallee);
 
        // String XData = Web3Service.getXData();
        // logger.debug("XData: " + XData);
        // String XSign = Web3Service.getXSign(XData);
        // logger.debug("XSign: " + XSign);


        String password = "secure123";
        String ciphertextHex = "e931dc0e0099b76a97312f7a9122517a29c725e2c96782abb80d2a9517b00eb7c2dfc490171d9dd1cb85fe05a9ef7af0707a068ebd9e9ac9e383b5f43b56f2257ee9a3a722cc829c41bab4d5c31e78e4b820522a915267c3b7a57a6830011fdfdf0eef9ab613ef016aed68270c6ca696";
        String decryptedHex = Web3Service.updateNewProduct(password, ciphertextHex);
        System.out.println("Decrypted hex: " + decryptedHex);

    }
}
