package com.finlabs.finexa.resources.model;
import java.util.Date;
public class Client {
    private int clientId;
    private String salutation;
    private String firstName;
    private String middleName;
    private String lastName;
    private Date bDate;
    private String gender;
    private String pan;
    private String aadhar;
    private String maritalStatus;
    private int edQualification;
    private String otherEdQualification;
    private String emailId;
    private String countryCode;
    private long mobile;
    private int employmentType;
    private String otherEmploymentType;
    private String orgName;
    private String currDesignation;
    private int residentType;
    private String otherResidentType;
    private String countryOfResidence;
    private String retired;
    
    public Client(){
    	salutation ="";
    	firstName = "";
    	middleName = "";
    	lastName = "";
    	bDate = new Date();
    	gender = "";
    	pan = "";
    	aadhar = "";
    	maritalStatus = "";
    	edQualification = 0;
    	otherEdQualification = "";
    	emailId = "";
    	countryCode = "";
    	mobile = 0;
    	employmentType = 0;
    	otherEmploymentType = "";
    	orgName = "";
    	currDesignation = "";
    	residentType = 0;
    	otherResidentType = "";
    	countryOfResidence = "";
    	retired = "";
    }

    public Client(String salutation,
    			  String firstName, 
    		      String middleName, 
    			  String lastName,
    			  Date bDate,
    			  String gender,
    			  String pan,
    			  String aadhar,
    			  String maritalStatus,
    			  int edQualification,
    			  String otherEdQualification,
    			  String emailId,
    			  String countryCode,
    			  long mobile,
    			  int employmentType,
    			  String otherEmploymentType,
    			  String orgName,
    			  String currDesignation,
    			  int residentType,
    			  String otherResidentType,
    			  String countryOfResidence,
    			  String retired)
    {
    	salutation = this.salutation;
    	firstName = this.firstName;
    	middleName = this.middleName;
    	lastName = this.lastName;
    	bDate = this.bDate;
    	gender = this.gender;
    	pan = this.pan;
    	aadhar = this.aadhar;
    	maritalStatus = this.maritalStatus;
    	edQualification = this.edQualification;
    	otherEdQualification = this.otherEdQualification;
    	emailId = this.emailId;
    	countryCode = this.countryCode;
    	mobile = this.mobile;
    	employmentType = this.employmentType;
    	otherEmploymentType = this.otherEmploymentType;
    	orgName = this.orgName;
    	currDesignation = this.currDesignation;
    	residentType = this.residentType;
    	otherResidentType = this.otherResidentType;
    	countryOfResidence = this.countryOfResidence;
    	retired = this.retired;
    }

    public int getClientId() {
        return clientId;
    }
    public void setClientId(int clientId) {
        this.clientId = clientId;
    }
    public String getSalutation() {
        return salutation;
    }
    public void setSalutation(String salutation) {
        this.salutation = salutation;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getMiddleName() {
        return middleName;
    }
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }    
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public Date getBdate() {
        return bDate;
    }
    public void setBdate(Date bDate) {
        this.bDate = bDate;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getPan() {
        return pan;
    }
    public void setPan(String pan) {
        this.pan = pan;
    }
    public String getAadhar() {
        return aadhar;
    }
    public void setAadhar(String aadhar) {
        this.aadhar = aadhar;
    }
    public String getEmailId() {
        return emailId;
    }
    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }
    public String getOrgName() {
        return orgName;
    }
    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
    public String getCurrDesignation() {
        return currDesignation;
    }
    public void setCurrDesignation(String currDesignation) {
        this.currDesignation = currDesignation;
    }
    public String getMaritalStatus() {
        return maritalStatus;
    }
    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }
    public int getEdQualification() {
        return edQualification;
    }
    public void setEdQualification(int edQualification) {
        this.edQualification = edQualification;
    }
    public String getOtherEdQualification() {
        return otherEdQualification;
    }
    public void setOtherEdQualification(String otherEdQualification) {
        this.otherEdQualification = otherEdQualification;
    }
    public String getCountryCode() {
        return countryCode;
    }
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
    public long getMobile() {
        return mobile;
    }
    public void setMobile(long mobile) {
        this.mobile = mobile;
    }
    public int getEmploymentType() {
        return employmentType;
    }
    public void setEmploymentType(int employmentType) {
        this.employmentType = employmentType;
    }
    public String getOtherEmploymentType() {
        return otherEmploymentType;
    }
    public void setOtherEmploymentType(String otherEmploymentType) {
        this.otherEmploymentType = otherEmploymentType;
    }
    public int getResidentType() {
        return residentType;
    }
    public void setResidentType(int residentType) {
        this.residentType = residentType;
    }
    public String getOtherResidentType() {
        return otherResidentType;
    }
    public void setOtherResidentType(String otherResidentType) {
        this.otherResidentType = otherResidentType;
    }
    public String getCountryOfResidence() {
        return countryOfResidence;
    }
    public void setCountryOfResidence(String countryOfResidence) {
        this.countryOfResidence = countryOfResidence;
    }
    public String getRetired() {
        return retired;
    }
    public void setRetired(String retired) {
        this.retired = retired;
    }

    
    @Override
    public String toString() {
        return "Client [clientId=" + clientId + ", bDate=" + bDate + ", firstName=" + firstName +
                ", middleName=" + middleName + ", lastName=" + lastName + 
                ", gender=" + gender + ", pan=" + pan + ", aadhar=" + aadhar + ", maritalStatus=" + maritalStatus + 
                ", edQualification=" + edQualification + ", emailId=" + emailId + 
                ", mobile=" + mobile + ", employmentType=" + employmentType + ", orgName=" + orgName +
                ", currDesignation=" + currDesignation + ", residentType=" + residentType + "]";
    }    
}
