package io.gkuhn.userbroker.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="users")
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="`userid`")
	private int userid;
	
	@Column(name="`name`")
	private String name;
	
	@Column(name="`familyname`")
	private String familyname;
	
	@Column(name="`birthdate`")
	private Date birthdate;
	
	
	public User(String name, String familyname, Date birthdate) {
		this.name = name;
		this.familyname = familyname;
		this.birthdate = birthdate;
	}
	
	public User() { }

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFamilyname() {
		return familyname;
	}

	public void setFamilyname(String familyname) {
		this.familyname = familyname;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}
}
