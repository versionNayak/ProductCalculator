package com.finlabs.finexa.resources.model;

public class Country {
	    private int countryId;
	    private String iso;
	    private String name;
	    private String niceName;
	    private String iso3;
	    private int numCode;
	    private int phoneCode;
	    
	    public Country(){
	    	iso ="";
	    	name = "";
	    	niceName = "";
	    	iso3 = "";
	    	numCode = 0;
	    	phoneCode = 0;
	    }

	    public Country(String iso,
	    			  String name, 
	    		      String niceName, 
	    			  String iso3,
	    			  int numCode,
	    			  int phoneCode)
	    {
	    	iso = this.iso;
	    	name = this.name;
	    	niceName = this.niceName;
	    	iso3 = this.iso3;
	    	numCode = this.numCode;
	    	phoneCode = this.phoneCode;
	    }

	    public int getCountryId() {
	        return countryId;
	    }
	    public void setCountryId(int countryId) {
	        this.countryId = countryId;
	    }
	    public String getIso() {
	        return iso;
	    }
	    public void setIso(String iso) {
	        this.iso = iso;
	    }
	    public String getName() {
	        return name;
	    }
	    public void setName(String name) {
	        this.name = name;
	    }
	    public String getNiceName() {
	        return niceName;
	    }
	    public void setNiceName(String niceName) {
	        this.niceName = niceName;
	    }    
	    public String getIso3() {
	        return iso3;
	    }
	    public void setIso3(String iso3) {
	        this.iso3 = iso3;
	    }
	    public int getNumCode() {
	        return numCode;
	    }
	    public void setNumCode(int numCode) {
	        this.numCode = numCode;
	    }
	    public int getPhoneCode() {
	        return phoneCode;
	    }
	    public void setPhoneCode(int phoneCode) {
	        this.phoneCode = phoneCode;
	    }
	}

