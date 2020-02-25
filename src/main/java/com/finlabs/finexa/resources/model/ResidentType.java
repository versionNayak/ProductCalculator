package com.finlabs.finexa.resources.model;

public class ResidentType {

	
    private int residentTypeId;
    private int residentTypeCode;
    private String residentType;
    
    public ResidentType(){
    	residentTypeCode = 0;
    	residentType = "";
    }

    public ResidentType(int residentTypeCode,
    			  String residentType)
    {
    	residentTypeCode = this.residentTypeCode;
    	residentType = this.residentType;
    }

    public int getResidentTypeId() {
        return residentTypeId;
    }
    public void setResidentTypeId(int residentTypeId) {
        this.residentTypeId = residentTypeId;
    }

    public int getResidentTypeCode() {
        return residentTypeCode;
    }
    public void setResidentTypeCode(int residentTypeCode) {
        this.residentTypeCode = residentTypeCode;
    }
    
    public String getResidentType() {
        return residentType;
    }
    public void setResidentType(String residentType) {
        this.residentType = residentType;
    }


}
