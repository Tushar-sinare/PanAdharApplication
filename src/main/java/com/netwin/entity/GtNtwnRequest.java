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

//@NamedQuery(name="AharNtwnRequest.findByGtReqMasSrNo", query="SELECT NEW com.netwin.dto.CustomerVendorDetailsDto(JSON_EXTRACT(a.reqDecrypt, '$.pageId') AS pageId, JSON_EXTRACT(a.reqDecrypt, '$.userReqSrNo') AS userReqSrNo, JSON_EXTRACT(a.reqDecrypt, '$.adharNo') AS adharNo, JSON_EXTRACT(a.reqDecrypt, '$.branchId') AS branchId, JSON_EXTRACT(a.reqDecrypt, '$.prodId') AS prodId, JSON_EXTRACT(a.reqDecrypt, '$.custId') AS custId) FROM AharNtwnRequest a WHERE a.ahaReMasSrNo = 90")
@Entity
@Table(name="GTREQMAS")
public class GtNtwnRequest {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PNADHGTREQMAS_SEQ")
	@SequenceGenerator(name = "PNADHGTREQMAS_SEQ", sequenceName = "PNADHGTREQMAS_SEQ", allocationSize = 1)
	@Column(name = "GTREQMASSRNO",length=10,nullable = false)
	private long gtReqMasSrNo;
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
	@Column(name="REQESTFOR",length=1)
	private String reqFor;
	public long getGtReqMasSrNo() {
		return gtReqMasSrNo;
	}
	public void setGtReqMasSrNo(long gtReqMasSrNo) {
		this.gtReqMasSrNo = gtReqMasSrNo;
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
	public String getReqFor() {
		return reqFor;
	}
	public void setReqFor(String reqFor) {
		this.reqFor = reqFor;
	}
	
	
	
	}


