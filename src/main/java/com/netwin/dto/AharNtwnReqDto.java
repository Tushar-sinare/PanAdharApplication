package com.netwin.dto;

import java.util.Date;

public class AharNtwnReqDto {
	private long ahaReMasSrNo;
	private String reqEncrypt;
	private String reqDecrypt;
	private Date entryDate;
	private String callingIpAdr;
	private String reqFor;

	
	public long getAhaReMasSrNo() {
		return ahaReMasSrNo;
	}
	public void setAhaReMasSrNo(long ahaReMasSrNo) {
		this.ahaReMasSrNo = ahaReMasSrNo;
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
