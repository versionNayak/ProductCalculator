package com.finlabs.finexa.resources.model;

public class Relation {

	
    private int relationId;
    private int relationCode;
    private String relation;
    
    public Relation(){
    	relationCode = 0;
    	relation = "";
    }

    public Relation(int relationCode,
    			  String relation)
    {
    	relationCode = this.relationCode;
    	relation = this.relation;
    }

    public int getRelationId() {
        return relationId;
    }
    public void setRelationId(int relationId) {
        this.relationId = relationId;
    }

    public int getRelationCode() {
        return relationCode;
    }
    public void setRelationCode(int relationCode) {
        this.relationCode = relationCode;
    }
    
    public String getRelation() {
        return relation;
    }
    public void setRelation(String relation) {
        this.relation = relation;
    }


	
}
