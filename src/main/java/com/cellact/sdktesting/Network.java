package com.cellact.sdktesting;

import org.json.JSONObject;

import com.cellact.Config.ANetwork;
import com.cellact.Config.ALogger;


public class Network implements ANetwork{
    
    private String ENTRY_POINT_URL;
    private int CHAIN_ID;
    public String networkName;

    public Network(ALogger logger) {
        this(logger, "amoy");
    }
    
    public Network(ALogger logger, String _networkName) {
        try {
            this.networkName = _networkName;
            JSONObject networkConfig = Utils.getCloudFunctions(logger).getNetwork(networkName);
            this.ENTRY_POINT_URL = networkConfig.getString("entry_point_url");
            this.CHAIN_ID = networkConfig.getInt("chain_id");

        } catch (Exception e) {
            e.printStackTrace();
            this.ENTRY_POINT_URL = "https://polygon-amoy-bor-rpc.publicnode.com";
            this.CHAIN_ID = 80002;
        }
    }

    public String getRPC() {
        return ENTRY_POINT_URL;
    }

    public int getChainID() {
        return CHAIN_ID;
    }

}
