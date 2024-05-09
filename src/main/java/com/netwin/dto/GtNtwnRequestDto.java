package com.netwin.dto;

import java.util.Date;

public class GtNtwnRequestDto {
	private long gtReqMasSrNo;
	private String reqEncrypt;
	private String reqDecrypt;
	private Date entryDate;
	private String callingIpAdr;
	
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
	
	
}
