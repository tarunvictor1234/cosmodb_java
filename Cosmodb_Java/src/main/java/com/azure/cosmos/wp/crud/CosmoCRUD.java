package com.azure.cosmos.wp.crud;

import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.azure.cosmos.implementation.Utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CosmoCRUD {

	protected static Logger logger = LoggerFactory.getLogger(CosmoAsyncAPI.class);
	private static final ObjectMapper OBJECT_MAPPER = Utils.getSimpleObjectMapper();

	public static void main(String[] args) {
		CosmoAsyncAPI api = new CosmoAsyncAPI();

		try {
			logger.info("Starting ASYNC main");
			
			//create(api);
			//getIDDetails(api);
			list(api);
			
        	
		
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(String.format("Cosmos getStarted failed with %s", e));
		} finally {
			logger.info("Closing the client");
			api.close();

		}
		
		
		
	}
	
	static final void create(CosmoAsyncAPI api) throws Exception {
		Table1POJO pojo = new Table1POJO();
		pojo.setId(RandomStringUtils.random(10, true, true));
		pojo.setType1(RandomStringUtils.random(10, true, true));
		pojo.setType2(RandomStringUtils.random(10, true, true));
		pojo.setType3(RandomStringUtils.random(10, true, true));
		pojo.setEntityType("table1");
		System.out.println(pojo.toString());
		api.createItem(pojo);
	}
	
	
	static final void getIDDetails(CosmoAsyncAPI api) throws Exception{
		JsonNode node =  api.getDocumentById("0QtkIojFt3");
    	System.out.println(node);
	}

	
	static final void list(CosmoAsyncAPI api) throws Exception{
		List<JsonNode> list = api.readtems("table1");
		for (JsonNode node : list)
			try {
				System.out.println(OBJECT_MAPPER.treeToValue(node, Table1POJO.class));
			} catch (JsonProcessingException e) {

				e.printStackTrace();

			}
	}

}
