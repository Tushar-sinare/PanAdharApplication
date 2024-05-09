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
@Table(name="VNDRPNREQMAS")
public class PnVndrRequest {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "VNDRPNREQMAS_SEQ")
	@SequenceGenerator(name = "VNDRPNREQMAS_SEQ", sequenceName = "VNDRPNREQMAS_SEQ", allocationSize = 1)
	@Column(name="VNDRPNREQMASSRNO",length=10,nullable = false)
	private int vndrPnReqMasSrNo;
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
	@Column(name = "PNREQMASSRNO",nullable = true)
	private long pnReqMasSrNo;
	public int getVndrPnReqMasSrNo() {
		return vndrPnReqMasSrNo;
	}
	public void setVndrPnReqMasSrNo(int vndrPnReqMasSrNo) {
		this.vndrPnReqMasSrNo = vndrPnReqMasSrNo;
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
	public long getPnReqMasSrNo() {
		return pnReqMasSrNo;
	}
	public void setPnReqMasSrNo(long pnReqMasSrNo) {
		this.pnReqMasSrNo = pnReqMasSrNo;
	}
	
}
