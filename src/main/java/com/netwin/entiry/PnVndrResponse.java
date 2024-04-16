package com.netwin.entiry;



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
@Table(name = "VNDRPNRESMAS")
public class PnVndrResponse {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "VNDRPNRESMAS_SEQ")
	@SequenceGenerator(name = "VNDRPNRESMAS_SEQ", sequenceName = "VNDRPNRESMAS_SEQ", allocationSize = 1)
	@Column(name = "VNDRPNRESMASSRNO",length=50,nullable = false)
	private int vndrPnResMassNo;
	@Lob
	@Column(name = "REQSTR", columnDefinition = "TEXT")
	private String reqDecrypt;
	@Lob
	@Column(name = "ACTREQSTR", columnDefinition = "TEXT")
	private String reqEncrypt;
	
@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ENTRYDTTM")
	private Date entryDate;

	@Column(name = "PANNO",length=10,nullable = true)
	private String panNo;

	public int getVndrPnResMassNo() {
		return vndrPnResMassNo;
	}

	public void setVndrPnResMassNo(int vndrPnResMassNo) {
		this.vndrPnResMassNo = vndrPnResMassNo;
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

	public String getPanNo() {
		return panNo;
	}

	public void setPanNo(String panNo) {
		this.panNo = panNo;
	}
	

}

