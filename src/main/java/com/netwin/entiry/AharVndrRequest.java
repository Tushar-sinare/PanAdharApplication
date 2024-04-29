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
@Table(name="VNDRAHARREQMAS")
public class AharVndrRequest {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "VNDRAHARREQMAS_SEQ")
	@SequenceGenerator(name = "VNDRAHARREQMAS_SEQ", sequenceName = "VNDRAHARREQMAS_SEQ", allocationSize = 1)
	@Column(name="VNDRPNREQMASSRNO",length=10,nullable = false)
	private int vndrAharReqMasSrNo;
	@Lob
	@Column(name = "ACTREQSTR", columnDefinition = "TEXT")
	private String reqEncrypt;
	@Lob
	@Column(name = "REQSTR", columnDefinition = "TEXT")
	private String reqDecrypt;
	@Column(name = "ENTDATETM")
	@Temporal(TemporalType.TIMESTAMP)
	private Date entryDate;
	@Column(name="CALLINGIPADR",length=45,nullable=true)
	private String callingIpAdr;
	@Column(name = "ADHREMASSRNO",nullable = true)
	private long aharReqMasSrNo;
	@Column(name="REQESTFOR",length=1)
	private String reqFor;
	public int getVndrPnReqMasSrNo() {
		return vndrAharReqMasSrNo;
	}
	public void setVndrPnReqMasSrNo(int vndrPnReqMasSrNo) {
		this.vndrAharReqMasSrNo = vndrPnReqMasSrNo;
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
	public int getVndrAharReqMasSrNo() {
		return vndrAharReqMasSrNo;
	}
	public void setVndrAharReqMasSrNo(int vndrAharReqMasSrNo) {
		this.vndrAharReqMasSrNo = vndrAharReqMasSrNo;
	}
	
	public long getAharReqMasSrNo() {
		return aharReqMasSrNo;
	}
	public void setAharReqMasSrNo(long aharReqMasSrNo) {
		this.aharReqMasSrNo = aharReqMasSrNo;
	}
	public String getReqFor() {
		return reqFor;
	}
	public void setReqFor(String reqFor) {
		this.reqFor = reqFor;
	}
	
	
}
