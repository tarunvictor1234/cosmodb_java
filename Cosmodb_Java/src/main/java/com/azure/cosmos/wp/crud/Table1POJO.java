package com.azure.cosmos.wp.crud;

public class Table1POJO implements DDEPInterace{

	private String type1;
    private String type2;
    private String id;
    private String type3;
	private String entityType;
    
	
	public String getEntityType() {
		return entityType;
	}
	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}
	public String getType1() {
		return type1;
	}
	public void setType1(String type1) {
		this.type1 = type1;
	}
	public String getType2() {
		return type2;
	}
	public void setType2(String type2) {
		this.type2 = type2;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType3() {
		return type3;
	}
	public void setType3(String type3) {
		this.type3 = type3;
	}
	@Override
	public String toString() {
		return "Table1POJO [type1=" + type1 + ", type2=" + type2 + ", id=" + id + ", type3=" + type3 + ", entityType="
				+ entityType + "]";
	}
	
    
    
}
