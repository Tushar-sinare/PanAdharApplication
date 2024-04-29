package com.netwin.dto;

import java.util.Date;

public class PnVndrRequestDto {
	private int vndrPnReqMasSrNo;

	private String reqEncrypt;
	private String reqDecrypt;
	private Date entryDate;
	private String callingIpAdr;
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
