package com.netwin.entiry;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name="PNVENDORMAS")
public class PnVendorDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PNVNDRSRNO_SEQ")
	@SequenceGenerator(name = "PNVNDRSRNO_SEQ", sequenceName = "PNVNDRSRNO_SEQ", allocationSize = 1)
	@Column(name = "PNVNDRSRNO",length=10,nullable = false)
	private int pnVnDrSrNo;
	@Column(name = "PNVNDRNAME",length=50,nullable = false)
	private String pnVndrName;
	@Column(name = "PNVNDRAPIPSW",length=50,nullable = false)
	private String pnVndrApiPsw;
	@Column(name = "PNVNDRAPIUSER",length=50,nullable = false)
	private String pnVnDrApiUser;
	@Column(name = "PNVRFYURL",length=50,nullable = false)
	private String pnVrfyURL;
	@Column(name = "PNVRFYOTPURL",length=50,nullable = false)
	private String pnVrfyOtpURL;
	@Column(name = "PNVNDROPTID",length=50,nullable = false)
	private String pnVndrOptId;
	@Column(name = "ENCREQ",length=50,nullable = true)
	private int encReq;
	@Column(name = "REQPERDAY",length=50,nullable = true)
	private int reqPerDay;
	@Column(name = "REQPERHR",length=50,nullable = true)
	private int reqPerHr;
	public int getPnVnDrSrNo() {
		return pnVnDrSrNo;
	}
	public void setPnVnDrSrNo(int pnVnDrSrNo) {
		this.pnVnDrSrNo = pnVnDrSrNo;
	}
	public String getPnVndrName() {
		return pnVndrName;
	}
	public void setPnVndrName(String pnVndrName) {
		this.pnVndrName = pnVndrName;
	}
	public String getPnVndrApiPsw() {
		return pnVndrApiPsw;
	}
	public void setPnVndrApiPsw(String pnVndrApiPsw) {
		this.pnVndrApiPsw = pnVndrApiPsw;
	}
	public String getPnVnDrApiUser() {
		return pnVnDrApiUser;
	}
	public void setPnVnDrApiUser(String pnVnDrApiUser) {
		this.pnVnDrApiUser = pnVnDrApiUser;
	}
	public String getPnVrfyURL() {
		return pnVrfyURL;
	}
	public void setPnVrfyURL(String pnVrfyURL) {
		this.pnVrfyURL = pnVrfyURL;
	}
	public String getPnVrfyOtpURL() {
		return pnVrfyOtpURL;
	}
	public void setPnVrfyOtpURL(String pnVrfyOtpURL) {
		this.pnVrfyOtpURL = pnVrfyOtpURL;
	}
	public String getPnVndrOptId() {
		return pnVndrOptId;
	}
	public void setPnVndrOptId(String pnVndrOptId) {
		this.pnVndrOptId = pnVndrOptId;
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
