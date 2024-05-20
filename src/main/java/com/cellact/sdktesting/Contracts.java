package com.cellact.sdktesting;

import com.cellact.Config.AContract;


public class Contracts implements AContract{
    
    String NAME_HASH_ADDRESS;
    String W_ENS_ADDRESS;

    public Contracts() {
        try {
            this.NAME_HASH_ADDRESS = Utils.getCloudFunctions(null).getContractAddress("NAME_HASH_ADDRESS");
            this.W_ENS_ADDRESS = Utils.getCloudFunctions(null).getContractAddress("W_ENS_ADDRESS");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getNameHashAddress() {
        return this.NAME_HASH_ADDRESS;
    }

    public String getWENSAddress() {
        return this.W_ENS_ADDRESS;
    }

}
