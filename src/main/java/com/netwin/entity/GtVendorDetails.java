package com.netwin.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name="GTVENDORMAS")
public class GtVendorDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GTVNDRSRNO_SEQ")
	@SequenceGenerator(name = "GTVNDRSRNO_SEQ", sequenceName = "GTVNDRSRNO_SEQ", allocationSize = 1)
	@Column(name = "GTVNDRSRNO",length=10,nullable = false)
	private int gtVnDrSrNo;
	@Column(name = "GTVNDRNAME",length=50,nullable = false)
	private String gtVndrName;
	@Column(name = "GTVNDRAPIPSW",length=50,nullable = false)
	private String gtVndrApiPsw;
	@Column(name = "GTVNDRAPIUSER",length=50,nullable = false)
	private String gtVnDrApiUser;
	@Column(name = "GTVRFYURL",length=100,nullable = false)
	private String gtVrfyURL;
	@Column(name = "GTVRFYOTPURL",length=100,nullable = true)
	private String gtVrfyOtpURL;
	@Column(name = "GTVNDROPTID",length=50,nullable = true)
	private String gtVndrOptId;
	@Column(name = "ENCREQ",length=50,nullable = true)
	private int encReq;
	@Column(name = "REQPERDAY",length=50,nullable = true)
	private int reqPerDay;
	@Column(name = "REQPERHR",length=50,nullable = true)
	private int reqPerHr;
	public int getGtVnDrSrNo() {
		return gtVnDrSrNo;
	}
		public void setGtVnDrSrNo(int gtVnDrSrNo) {
		this.gtVnDrSrNo = gtVnDrSrNo;
	}
	public String getGtVndrName() {
		return gtVndrName;
	}
	public void setGtVndrName(String gtVndrName) {
		this.gtVndrName = gtVndrName;
	}
	public String getGtVndrApiPsw() {
		return gtVndrApiPsw;
	}
	public void setGtVndrApiPsw(String gtVndrApiPsw) {
		this.gtVndrApiPsw = gtVndrApiPsw;
	}
	public String getGtVnDrApiUser() {
		return gtVnDrApiUser;
	}
	public void setGtVnDrApiUser(String gtVnDrApiUser) {
		this.gtVnDrApiUser = gtVnDrApiUser;
	}
	public String getGtVrfyURL() {
		return gtVrfyURL;
	}
	public void setGtVrfyURL(String gtVrfyURL) {
		this.gtVrfyURL = gtVrfyURL;
	}
	public String getGtVrfyOtpURL() {
		return gtVrfyOtpURL;
	}
	public void setGtVrfyOtpURL(String gtVrfyOtpURL) {
		this.gtVrfyOtpURL = gtVrfyOtpURL;
	}
	public String getGtVndrOptId() {
		return gtVndrOptId;
	}
	public void setGtVndrOptId(String gtVndrOptId) {
		this.gtVndrOptId = gtVndrOptId;
	}
	public int getEncReq() {
		return encReq;
	}
	public void setEncReq(int encReq) {
		this.encReq = encReq;
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
