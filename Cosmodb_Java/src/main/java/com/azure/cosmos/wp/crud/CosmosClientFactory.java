package com.azure.cosmos.wp.crud;



import com.azure.cosmos.ConsistencyLevel;
import com.azure.cosmos.CosmosClient;
import com.azure.cosmos.CosmosClientBuilder;

public class CosmosClientFactory {
  

    private static String MASTER_KEY =  "";
    private static String HOST = "";
/*
 * 
 */
    
    
    private static CosmosClient cosmosClient = new CosmosClientBuilder()
            .endpoint(HOST)
            .key(MASTER_KEY)
            .consistencyLevel(ConsistencyLevel.EVENTUAL)
            .buildClient();

    public static CosmosClient getCosmosClient() {
        return cosmosClient;
    }

}