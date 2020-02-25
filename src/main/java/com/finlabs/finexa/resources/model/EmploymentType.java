package com.finlabs.finexa.resources.model;

public class EmploymentType {

	
    private int employmentTypeId;
    private int employmentTypeCode;
    private String employmentType;
    
    public EmploymentType(){
    	employmentTypeCode = 0;
    	employmentType = "";
    }

    public EmploymentType(int employmentTypeCode,
    			  String employmentType)
    {
    	employmentTypeCode = this.employmentTypeCode;
    	employmentType = this.employmentType;
    }

    public int getEmploymentTypeId() {
        return employmentTypeId;
    }
    public void setEmploymentTypeId(int employmentTypeId) {
        this.employmentTypeId = employmentTypeId;
    }

    public int getEmploymentTypeCode() {
        return employmentTypeCode;
    }
    public void setEmploymentTypeCode(int employmentTypeCode) {
        this.employmentTypeCode = employmentTypeCode;
    }
    
    public String getEmploymentType() {
        return employmentType;
    }
    public void setEmploymentType(String employmentType) {
        this.employmentType = employmentType;
    }

}
