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
@Table(name = "VNDRGTRESMAS")
public class GtVndrResponse {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "VNDRGTRESMAS_SEQ")
	@SequenceGenerator(name = "VNDRGTRESMAS_SEQ", sequenceName = "VNDRGTRESMAS_SEQ", allocationSize = 1)
	@Column(name = "VNDRGTRESMASSRNO",length=50,nullable = false)
	private int vndrGtResMassNo;
	@Lob
	@Column(name = "REQSTR", columnDefinition = "TEXT")
	private String reqDecrypt;
	@Lob
	@Column(name = "ACTREQSTR", columnDefinition = "TEXT")
	private String reqEncrypt;
	
@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ENTRYDTTM")
	private Date entryDate;

	@Column(name = "GSTNO",length=15,nullable = true)
	private String userGstNo;
	
	@Column(name = "GTREQMASSRNO",nullable = true)
	private long gtReqMasSrNo;

	public int getVndrGtResMassNo() {
		return vndrGtResMassNo;
	}

	public void setVndrGtResMassNo(int vndrGtResMassNo) {
		this.vndrGtResMassNo = vndrGtResMassNo;
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


	public String getUserGstNo() {
		return userGstNo;
	}

	public void setUserGstNo(String userGstNo) {
		this.userGstNo = userGstNo;
	}

	public long getGtReqMasSrNo() {
		return gtReqMasSrNo;
	}

	public void setGtReqMasSrNo(long gtReqMasSrNo) {
		this.gtReqMasSrNo = gtReqMasSrNo;
	}
	
	
	}

