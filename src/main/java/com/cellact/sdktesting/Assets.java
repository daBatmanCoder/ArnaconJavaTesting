package com.cellact.sdktesting;

public class Assets {
    
    // Need to generate here something more generic to save the parameter for the Service provider to be used in the SDK
    // That's not related to the dataSaver of the client
    private static String serviceProviderName;

    public static String getServiceProviderName(){
        if ( serviceProviderName != null ) {
            return serviceProviderName;
        } 
        
        return "";
    }

    public static void setServiceProviderName(String _serviceProviderName){
        serviceProviderName = _serviceProviderName;
    }


}
