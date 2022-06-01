package com.azure.cosmos.wp.crud;


import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.azure.cosmos.CosmosClient;
import com.azure.cosmos.CosmosContainer;
import com.azure.cosmos.CosmosDatabase;
import com.azure.cosmos.implementation.Utils;
import com.azure.cosmos.models.CosmosQueryRequestOptions;
import com.azure.cosmos.models.FeedResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
public class CosmoAsyncAPI {

    
   
    private static final String DATABASE_ID = "tdatase";
    private static final String CONTAINER_ID = "tcontainer";

    
    
    private static CosmosContainer cosmosContainer = null;
    private static CosmosClient cosmosClient = CosmosClientFactory.getCosmosClient();
    private static final ObjectMapper OBJECT_MAPPER = Utils.getSimpleObjectMapper();
    private static CosmosDatabase cosmosDatabase = null;
    
    protected static Logger logger = LoggerFactory.getLogger(CosmoAsyncAPI.class);
    

    public void close() {
    	cosmosClient.close();
    }

   
    public void createItem(DDEPInterace pojo) throws Exception {
    	
    	JsonNode itemJson = OBJECT_MAPPER.valueToTree(pojo);

        ((ObjectNode) itemJson).put("entityType", pojo.getEntityType());

      
            // Persist the document using the DocumentClient.
        	itemJson =
                getContainerCreateResourcesIfNotExist()
                    .createItem(itemJson)
                    .getItem();
        
        System.out.println("done...");
    }

    

    private CosmosContainer getContainerCreateResourcesIfNotExist() throws Exception{

        
            if (cosmosDatabase == null) {
                cosmosDatabase = cosmosClient.getDatabase(DATABASE_ID);
            }

            if (cosmosContainer == null) {
                cosmosContainer = cosmosDatabase.getContainer(CONTAINER_ID);
            }


        return cosmosContainer;
    }
    
    
    public List<JsonNode> readtems(String entity) throws Exception{

        List<JsonNode> todoItems = new ArrayList<JsonNode>();

        String sql = "SELECT * FROM root r WHERE r.entityType = '"+entity+"'";
        int maxItemCount = 1000;
        int maxDegreeOfParallelism = 1000;
        int maxBufferedItemCount = 100;

        CosmosQueryRequestOptions options = new CosmosQueryRequestOptions();
        options.setMaxBufferedItemCount(maxBufferedItemCount);
        options.setMaxDegreeOfParallelism(maxDegreeOfParallelism);
        options.setQueryMetricsEnabled(false);

     
        String continuationToken = null;
        do {

            for (FeedResponse<JsonNode> pageResponse :
                getContainerCreateResourcesIfNotExist()
                    .queryItems(sql, options, JsonNode.class)
                    .iterableByPage(continuationToken, maxItemCount)) {

                continuationToken = pageResponse.getContinuationToken();

                for (JsonNode item : pageResponse.getElements()) {

                    
                        todoItems.add(item);
                    

                }
            }

        } while (continuationToken != null);
        System.out.println(todoItems);
        return todoItems;
    }
    
    public JsonNode getDocumentById(String id) throws Exception{

        String sql = "SELECT * FROM root r WHERE r.id='" + id + "'";
        int maxItemCount = 1000;
        int maxDegreeOfParallelism = 1000;
        int maxBufferedItemCount = 100;

        CosmosQueryRequestOptions options = new CosmosQueryRequestOptions();
        options.setMaxBufferedItemCount(maxBufferedItemCount);
        options.setMaxDegreeOfParallelism(maxDegreeOfParallelism);
        options.setQueryMetricsEnabled(false);

        List<JsonNode> itemList = new ArrayList();

        String continuationToken = null;
        do {
            for (FeedResponse<JsonNode> pageResponse :
                getContainerCreateResourcesIfNotExist()
                    .queryItems(sql, options, JsonNode.class)
                    .iterableByPage(continuationToken, maxItemCount)) {

                continuationToken = pageResponse.getContinuationToken();

                for (JsonNode item : pageResponse.getElements()) {
                    itemList.add(item);
                }
            }

        } while (continuationToken != null);

        if (itemList.size() > 0) {
            return itemList.get(0);
        } else {
            return null;
        }
    }
}

