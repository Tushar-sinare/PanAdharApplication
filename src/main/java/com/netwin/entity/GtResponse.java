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

@Table(name="GTRESMAS")
public class GtResponse {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GTRESMAS_SEQ")
	@SequenceGenerator(name = "GTRESMAS_SEQ", sequenceName = "GTRESMAS_SEQ", allocationSize = 1)
	@Column(name = "GTRESMASSRNO",length=10,nullable = false)
	private long gtResMasSrNo;
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
	@Column(name="GTREQMASSRNO")
	private long gtReqMasSrNo;
	public long getGtResMasSrNo() {
		return gtResMasSrNo;
	}
	public void setGtResMasSrNo(long gtResMasSrNo) {
		this.gtResMasSrNo = gtResMasSrNo;
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
	public long getGtReqMasSrNo() {
		return gtReqMasSrNo;
	}
	public void setGtReqMasSrNo(long gtReqMasSrNo) {
		this.gtReqMasSrNo = gtReqMasSrNo;
	}
	@Override
	public String toString() {
		return "GtResponse [gtResMasSrNo=" + gtResMasSrNo + ", entDateTM=" + entDateTM + ", callingIpAdr="
				+ callingIpAdr + ", reqEncrypt=" + reqEncrypt + ", reqDecrypt=" + reqDecrypt + ", gtReqMasSrNo="
				+ gtReqMasSrNo + "]";
	}
	
}


