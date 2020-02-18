package main.java.ws.skyscanner.usersservice.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User {
	
	@Id @GeneratedValue
	private int id;
	private String username;
	private String name;
	private String surname;
	private String password;
	private String mail;
	private String airport;

	public User() {
		super();
	}

	public User(String username, String name, String surname, String password, String mail, String airport) {
		super();
		this.username = username;
		this.name = name;
		this.surname = surname;
		this.password = password;
		this.mail = mail;
		this.airport = airport;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getAirport() {
		return airport;
	}

	public void setAirport(String airport) {
		this.airport = airport;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", name=" + name + ", surname=" + surname + ", password="
				+ password + ", mail=" + mail + ", airport=" + airport + "]";
	}
	
}
