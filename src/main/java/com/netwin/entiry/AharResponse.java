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
@Table(name="ADHRESMAS")
public class AharResponse {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ADHRESMAS_SEQ")
	@SequenceGenerator(name = "ADHRESMAS_SEQ", sequenceName = "ADHRESMAS_SEQ", allocationSize = 1)
	@Column(name = "ADHRESMASSRNO",length=10,nullable = false)
	private long adhResMasSrNo;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ENTDATETM")
	private Date entDateTM;
	@Column(name="CALLINGIPADR",length=45)
	private String callingIpAdr;
	@Lob
	@Column(name = "ACTREQSTR", columnDefinition = "TEXT")
	private String reqEncrypt;
	@Lob
	@Column(name = "REQSTR", columnDefinition = "TEXT")
	private String reqDecrypt;
	@Column(name="ADHREMASSRNO")
	private long ahaReqMasSrNo;
	
	@Column(name="REQESTFOR",length=1)
	private String reqFor;

	public long getAdhResMasSrNo() {
		return adhResMasSrNo;
	}

	public void setAdhResMasSrNo(long adhResMasSrNo) {
		this.adhResMasSrNo = adhResMasSrNo;
	}

	public Date getEntDateTM() {
		return entDateTM;
	}

	public void setEntDateTM(Date entDateTM) {
		this.entDateTM = entDateTM;
	}

	public String getCallingIpAdr() {
		return callingIpAdr;
	}

	public void setCallingIpAdr(String callingIpAdr) {
		this.callingIpAdr = callingIpAdr;
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

	public long getAhaReqMasSrNo() {
		return ahaReqMasSrNo;
	}

	public void setAhaReqMasSrNo(long ahaReqMasSrNo) {
		this.ahaReqMasSrNo = ahaReqMasSrNo;
	}

	public String getReqFor() {
		return reqFor;
	}

	public void setReqFor(String reqFor) {
		this.reqFor = reqFor;
	}
	
	
}