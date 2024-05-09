package com.netwin.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "VNDRADHRESMAS")
public class AharVndrResponse {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "VNDRADHRESMAS_SEQ")
	@SequenceGenerator(name = "VNDRADHRESMAS_SEQ", sequenceName = "VNDRADHRESMAS_SEQ", allocationSize = 1)
	@Column(name = "VNDRADHRESMASSRNO", length = 50, nullable = false)
	private int vndrAdhResMassNo;
	@Lob
	@Column(name = "REQSTR", columnDefinition = "TEXT")
	private String reqDecrypt;
	@Lob
	@Column(name = "ACTREQSTR", columnDefinition = "TEXT")
	private String reqEncrypt;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ENTRYDTTM")
	private Date entryDate;

	@Column(name = "ADHARNO", length = 12, nullable = true)
	private String adharNo;
	@Column(name = "ADHREMASSRNO",nullable = true)
	private long aharReqMasSrNo;
	
	@Column(name="REQESTFOR",length=1)
	private String reqFor;



	public long getAharReqMasSrNo() {
		return aharReqMasSrNo;
	}

	public String getReqDecrypt() {
		return reqDecrypt;
	}

	public void setReqDecrypt(String reqDecrypt) {
		this.reqDecrypt = reqDecrypt;
	}

	public String getReqEncrypt() {
		return reqEncrypt;
	}

	public void setReqEncrypt(String reqEncrypt) {
		this.reqEncrypt = reqEncrypt;
	}

	public Date getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	public int getVndrAdhResMassNo() {
		return vndrAdhResMassNo;
	}

	public void setVndrAdhResMassNo(int vndrAdhResMassNo) {
		this.vndrAdhResMassNo = vndrAdhResMassNo;
	}

	public String getAdharNo() {
		return adharNo;
	}

	public void setAdharNo(String adharNo) {
		this.adharNo = adharNo;
	}

	public String getReqFor() {
		return reqFor;
	}

	public void setReqFor(String reqFor) {
		this.reqFor = reqFor;
	}

	public void setAharReqMasSrNo(long aharReqMasSrNo) {
		this.aharReqMasSrNo = aharReqMasSrNo;
	}
	

}
