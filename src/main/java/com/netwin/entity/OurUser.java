package com.netwin.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class OurUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(unique = true,name="USERNAME")
	private String userNames;
	@Column(unique = true,name="PASSWORD")
	private String password;
	@Column(name = "NETWCUSTSRNO")
	private int netwCustSrno;
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserNames() {
		return userNames;
	}

	public void setUserNames(String userName) {
		this.userNames = userName;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public int getNetwCustSrno() {
		return netwCustSrno;
	}

	public void setNetwCustSrno(int netwCustSrno) {
		this.netwCustSrno = netwCustSrno;
	}



	
}
