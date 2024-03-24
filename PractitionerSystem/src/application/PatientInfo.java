package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class PatientInfo 
{
	BufferedReader br;
	BufferedWriter bw;
	
	String firstName, lastName, addressLine1, addressLine2, city, state, 
    username, password, birthMonth, zipCode, birthYear;
	
	public PatientInfo(String path)
	{
		File f = new File(path); //Create a file 
		
		try {
			br = new BufferedReader(new FileReader(path));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			firstName = br.readLine().replaceAll("\n", "");;
			lastName = br.readLine().replaceAll("\n", "");;
			addressLine1 = br.readLine().replaceAll("\n", "");;
			addressLine2 = br.readLine().replaceAll("\n", "");;
			city = br.readLine().replaceAll("\n", "");;
			state = br.readLine().replaceAll("\n", "");;
			zipCode = br.readLine().replaceAll("\n", "");;
			birthMonth = br.readLine().replaceAll("\n", "");;
			birthYear = br.readLine().replaceAll("\n", "");;
			username = br.readLine().replaceAll("\n", "");;
			password = br.readLine().replaceAll("\n", "");;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getBirthMonth() {
		return birthMonth;
	}

	public void setBirthMonth(String birthMonth) {
		this.birthMonth = birthMonth;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getBirthYear() {
		return birthYear;
	}

	public void setBirthYear(String birthYear) {
		this.birthYear = birthYear;
	}
	
	
	
	
}

