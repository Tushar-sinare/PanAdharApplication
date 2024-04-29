package com.netwin.entiry;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;



@Entity
@Table(name="ADHVENDORMAS")
public class AharVndrDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ADHVENDORMAS_SEQ")
	@SequenceGenerator(name = "ADHVENDORMAS_SEQ", sequenceName = "ADHVENDORMAS_SEQ", allocationSize = 1)
	@Column(name = "ADHVNDRSRNO",length=10,nullable = false)
	private int aharVnDrSrNo;
	@Column(name = "ADHVNDRNAME",length=50,nullable = false)
	private String aharVdrName;
	@Column(name = "ADHVNDRAPIPSW",length=50,nullable = false)
	private String aharVdrApiPsw;
	@Column(name = "ADHVNDRAPIUSER",length=50,nullable = false)
	private String aharVdrApiUser;
	@Column(name = "ADHVRFYURL",length=50,nullable = false)
	private String aharVrfyURL;
	@Column(name = "ADHVRFYOTPURL",length=50,nullable = false)
	private String aharVrfyOtpURL;
	@Column(name = "ADHVNDROPTID",length=50,nullable = false)
	private String aharVdrOptId;
	@Column(name = "ENCREQ",length=50,nullable = true)
	private String encReqStr;
	@Column(name = "REQPERDAY",length=50,nullable = true)
	private int reqPerDay;
	@Column(name = "REQPERHR",length=50,nullable = true)
	private int reqPerHr;
	public int getAharVnDrSrNo() {
		return aharVnDrSrNo;
	}
	public void setAharVnDrSrNo(int aharVnDrSrNo) {
		this.aharVnDrSrNo = aharVnDrSrNo;
	}
	public String getAharVdrName() {
		return aharVdrName;
	}
	public void setAharVdrName(String aharVdrName) {
		this.aharVdrName = aharVdrName;
	}
	public String getAharVdrApiPsw() {
		return aharVdrApiPsw;
	}
	public void setAharVdrApiPsw(String aharVdrApiPsw) {
		this.aharVdrApiPsw = aharVdrApiPsw;
	}
	public String getAharVdrApiUser() {
		return aharVdrApiUser;
	}
	public void setAharVdrApiUser(String aharVdrApiUser) {
		this.aharVdrApiUser = aharVdrApiUser;
	}
	public String getAharVrfyURL() {
		return aharVrfyURL;
	}
	public void setAharVrfyURL(String aharVrfyURL) {
		this.aharVrfyURL = aharVrfyURL;
	}
	public String getAharVrfyOtpURL() {
		return aharVrfyOtpURL;
	}
	public void setAharVrfyOtpURL(String aharVrfyOtpURL) {
		this.aharVrfyOtpURL = aharVrfyOtpURL;
	}
	public String getAharVdrOptId() {
		return aharVdrOptId;
	}
	public void setAharVdrOptId(String aharVdrOptId) {
		this.aharVdrOptId = aharVdrOptId;
	}
	public String getEncReqStr() {
		return encReqStr;
	}
	public void setEncReqStr(String encReqStr) {
		this.encReqStr = encReqStr;
	}
	public int getReqPerDay() {
		return reqPerDay;
	}
	public void setReqPerDay(int reqPerDay) {
		this.reqPerDay = reqPerDay;
	}
	public int getReqPerHr() {
		return reqPerHr;
	}
	public void setReqPerHr(int reqPerHr) {
		this.reqPerHr = reqPerHr;
	}
	
	
}