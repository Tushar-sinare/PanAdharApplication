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

@Table(name="PNRESMAS")
public class PnResponse {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PNRESMAS_SEQ")
	@SequenceGenerator(name = "PNRESMAS_SEQ", sequenceName = "PNRESMAS_SEQ", allocationSize = 1)
	@Column(name = "PNRESMASSRNO",length=10,nullable = false)
	private long pnResMasSrNo;
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
	@Column(name="PNREQMASSRNO")
	private long pnReqMasSrNo;
	public long getPnResMasSrNo() {
		return pnResMasSrNo;
	}
	public void setPnResMasSrNo(long pnResMasSrNo) {
		this.pnResMasSrNo = pnResMasSrNo;
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
	public long getPnReqMasSrNo() {
		return pnReqMasSrNo;
	}
	public void setPnReqMasSrNo(long pnReqMasSrNo) {
		this.pnReqMasSrNo = pnReqMasSrNo;
	}
	
}


