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


        String password = "cellact1";
        String ciphertextHex = "ebbe533f31f13dd207f66288b9d27c4f6d353de03aace9123705b5d030e49f45c06b366f3383ff64fb8db828b22a88d295ecd6151e58f90ffda9239eadfe91aab4894ac16a6e168b30c7ed2d65abaf39b0e684bf03824d75a0d36040d3068bfa4e544d80d00a9d1e83eb858e7ec98524403a15a4a1247e814d303fc9020527c7b89898b1a2fa8fc7f85cf443db1a425ec007228a8da98a00d11f7beeebd7b553a6466fc05b6d2192e89f7a019b262333";
        String decryptedHex = Web3Service.updateNewProduct(password, ciphertextHex);
        System.out.println("Decrypted hex: " + decryptedHex);

    }
}
