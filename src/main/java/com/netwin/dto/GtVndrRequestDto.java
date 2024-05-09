package com.netwin.dto;

import java.util.Date;

public class GtVndrRequestDto {
	private int vndrGtReqMasSrNo;

	private String reqEncrypt;
	private String reqDecrypt;
	private Date entryDate;
	private String callingIpAdr;
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

	
}
