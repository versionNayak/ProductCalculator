package com.finlabs.finexa.resources.model;


public class ClientAddress {
	private int addressId;
    private int clientId;
    private String addressTypeOffice;
    private String addressTypePermanent;
    private String addressTypeCorrespondence;
    private String address;
    private String city;
    private String state;
    private long pincode;
    private String country;
    private String countryCode;
    private long phone;
    private String alternateEmail;
    private long emergencyContact;
    
    public ClientAddress(){
    	clientId=0;
    	addressTypeOffice ="N";
    	addressTypePermanent ="N";
    	addressTypeCorrespondence ="N";
    	address ="";
    	city = "";
    	state = "";
    	country = "";
    	pincode = 0;
    	countryCode = "";
    	phone = 0;
    	alternateEmail = "";
    	emergencyContact = 0;
    }

    public ClientAddress(int clientId,
    			  String addressTypeOffice,
    			  String addressTypePermanent,
    			  String addressTypeCorrespondence,
    			  String address,
    			  String city, 
    		      String state,
    		      long pincode,
    			  String country,
    			  String countryCode,
    			  long phone,
    			  String alternateEmail,
    			  long emergencyContact)    
    {
    	clientId=this.clientId;
    	addressTypeOffice =this.addressTypeOffice;
    	addressTypePermanent =this.addressTypePermanent;
    	addressTypeCorrespondence =this.addressTypeCorrespondence;
    	address =this.address;
    	city =this.city;
    	state = this.state;
    	country = "";
    	pincode = this.pincode;
    	countryCode = this.countryCode;
    	phone = this.phone;
    	alternateEmail = this.alternateEmail;
    	emergencyContact = this.emergencyContact;
    }

    public int getAddressId() {
        return addressId;
    }
    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }
    public int getClientId() {
        return clientId;
    }
    public void setClientId(int clientId) {
        this.clientId = clientId;
    }
    public String getAddressTypeOffice() {
        return addressTypeOffice;
    }
    public void setAddressTypeOffice(String addressTypeOffice) {
        this.addressTypeOffice = addressTypeOffice;
    }
    public String getAddressTypePermanent() {
        return addressTypePermanent;
    }
    public void setAddressTypePermanent(String addressTypePermanent) {
        this.addressTypePermanent = addressTypePermanent;
    }
    public String getAddressTypeCorrespondence() {
        return addressTypeCorrespondence;
    }
    public void setAddressTypeCorrespondence(String addressTypeCorrespondence) {
        this.addressTypeCorrespondence = addressTypeCorrespondence;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }    
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public long getPincode() {
        return pincode;
    }
    public void setPincode(long pincode) {
        this.pincode = pincode;
    }
    public String getCountryCode() {
        return countryCode;
    }
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
    public long getPhone() {
        return phone;
    }
    public void setPhone(long phone) {
        this.phone = phone;
    }
   public String getAlternateEmail() {
        return alternateEmail;
    }
    public void setAlternateEmail(String alternateEmail) {
        this.alternateEmail = alternateEmail;
    }
    public long getEmergencyContact() {
        return emergencyContact;
    }
    public void setEmergencyContact(long emergencyContact) {
        this.emergencyContact = emergencyContact;
    }
}
