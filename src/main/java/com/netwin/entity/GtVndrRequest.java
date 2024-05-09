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
@Table(name = "VNDRGTREQMAS")
public class GtVndrRequest {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "VNDRGTREQMAS_SEQ")
	@SequenceGenerator(name = "VNDRGTREQMAS_SEQ", sequenceName = "VNDRGTREQMAS_SEQ", allocationSize = 1)
	@Column(name = "VNDRGTREQMASSRNO", length = 10, nullable = false)
	private int vndrGtReqMasSrNo;
	@Lob
	@Column(name = "ACTREQSTR", columnDefinition = "TEXT")
	private String reqEncrypt;
	@Lob
	@Column(name = "REQSTR", columnDefinition = "TEXT")
	private String reqDecrypt;
	@Column(name = "ENTDATETM")
	@Temporal(TemporalType.TIMESTAMP)
	private Date entryDate;
	@Column(name = "CALLINGIPADR", length = 45, nullable = true)
	private String callingIpAdr;
	@Column(name = "GTREQMASSRNO", nullable = true)
	private long gtReqMasSrNo;
	public int getVndrGtReqMasSrNo() {
		return vndrGtReqMasSrNo;
	}
	public void setVndrGtReqMasSrNo(int vndrGtReqMasSrNo) {
		this.vndrGtReqMasSrNo = vndrGtReqMasSrNo;
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
	public long getGtReqMasSrNo() {
		return gtReqMasSrNo;
	}
	public void setGtReqMasSrNo(long gtReqMasSrNo) {
		this.gtReqMasSrNo = gtReqMasSrNo;
	}
	@Override
	public String toString() {
		return "GtVndrRequest [vndrGtReqMasSrNo=" + vndrGtReqMasSrNo + ", reqEncrypt=" + reqEncrypt + ", reqDecrypt="
				+ reqDecrypt + ", entryDate=" + entryDate + ", callingIpAdr=" + callingIpAdr + ", gtReqMasSrNo="
				+ gtReqMasSrNo + "]";
	}

}
