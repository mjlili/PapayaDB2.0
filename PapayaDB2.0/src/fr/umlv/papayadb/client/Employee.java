package fr.umlv.papayadb.client;

import java.util.Arrays;
import java.util.List;

public class Employee {
	
	private int id;
	private String name;
	private Address address;
	private String role;
	private List<String> cities;
	
	public Employee() {
		
	}
	public Employee(int id, String name, Address address, String role, List<String> cities) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.role = role;
		this.cities = cities;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public List<String> getCities() {
		return cities;
	}
	public void setCities(List<String> cities) {
		this.cities = cities;
	}
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("***** Employee Details *****\n");
		sb.append("ID="+getId()+"\n");
		sb.append("Name="+getName()+"\n");
		sb.append("Role="+getRole()+"\n");
		sb.append("Address="+getAddress()+"\n");
		sb.append("Cities="+Arrays.toString(getCities().toArray())+"\n");
		sb.append("*****************************");
		return sb.toString();
	}
	
}
