package com.finlabs.finexa.resources.model;

public class EducationalQualification {
	
    private int edQualificationId;
    private int edQualificationCode;
    private String educationalQualification;
    
    public EducationalQualification(){
    	edQualificationCode = 0;
    	educationalQualification = "";
    }

    public EducationalQualification(int edQualificationCode,
    			  String educationalQualification)
    {
    	edQualificationCode = this.edQualificationCode;
    	educationalQualification = this.educationalQualification;
    }

    public int getEdQualificationId() {
        return edQualificationId;
    }
    public void setEdQualificationId(int edQualificationId) {
        this.edQualificationId = edQualificationId;
    }

    public int getEdQualificationCode() {
        return edQualificationCode;
    }
    public void setEdQualificationCode(int edQualificationCode) {
        this.edQualificationCode = edQualificationCode;
    }
    
    public String getEducationalQualification() {
        return educationalQualification;
    }
    public void setEducationalQualification(String educationalQualification) {
        this.educationalQualification = educationalQualification;
    }

}
