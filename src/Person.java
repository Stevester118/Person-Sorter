// Person class for Person objects. Holds all of the elements for a person in
// the ASCII file.
public class Person {
	String firstname, lastname, startdate, address, aptnum, city, state, country, zipcode;
	
	public String getFirstName(){return firstname;}
	public String getLastName(){return lastname;}
	public String getStartDate(){return startdate;}
	public String getAddress(){return address;}
	public String getAptNum(){return aptnum;}
	public String getCity(){return city;}
	public String getState(){return state;}
	public String getCountry(){return country;}
	public String getZipCode(){return zipcode;}
	
	public void setFirstName(String s){firstname = s;}
	public void setLastName(String s){lastname = s;}
	public void setStartDate(String s){startdate = s;}
	public void setAddress(String s){address = s;}
	public void setAptNum(String s){aptnum = s;}
	public void setCity(String s){city = s;}
	public void setState(String s){state = s;}
	public void setCountry(String s){country = s;}
	public void setZipCode(String s){zipcode = s;}
}
