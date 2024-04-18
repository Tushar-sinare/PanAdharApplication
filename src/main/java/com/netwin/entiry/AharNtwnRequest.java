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
@Table(name="ADHREQMAS")
public class AharNtwnRequest {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ADHREQMAS_SEQ")
	@SequenceGenerator(name = "ADHREQMAS_SEQ", sequenceName = "ADHREQMAS_SEQ", allocationSize = 1)
	@Column(name = "ADHREMASSRNO",length=10,nullable = false)
	private long adhReMasSrNo;
	@Lob
	@Column(name = "ACTREQSTR", columnDefinition = "TEXT")
	private String reqEncrypt;
	@Lob
	@Column(name = "REQSTR", columnDefinition = "TEXT")
	private String reqDecrypt;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ENTDATETM")
	private Date entryDate;
	@Column(name="CALLINGIPADR",length=45)
	private String callingIpAdr;
	
	
	public long getAdhReMasSrNo() {
		return adhReMasSrNo;
	}
	public void setAdhReMasSrNo(long adhReMasSrNo) {
		this.adhReMasSrNo = adhReMasSrNo;
	}
	public String getReqEncrypt() {
		return reqEncrypt;
	}
	public void setReqEncrypt(String reqEncrypt) {
		this.reqEncrypt = reqEncrypt;
	}
	public String getReqDecrypt() {
		return reqDecrypt;
	}
	public void setReqDecrypt(String reqDecrypt) {
		this.reqDecrypt = reqDecrypt;
	}
	public Date getEntryDate() {
		return entryDate;
	}
	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}
	public String getCallingIpAdr() {
		return callingIpAdr;
	}
	public void setCallingIpAdr(String callingIpAdr) {
		this.callingIpAdr = callingIpAdr;
	}
	@Override
	public String toString() {
		return "AharNtwnRequest [adhReMasSrNo=" + adhReMasSrNo + ", reqEncrypt=" + reqEncrypt + ", reqDecrypt="
				+ reqDecrypt + ", entryDate=" + entryDate + ", callingIpAdr=" + callingIpAdr + "]";
	}
	
	
	}


