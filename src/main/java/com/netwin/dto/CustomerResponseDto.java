package com.netwin.dto;

import java.util.Date;


public class CustomerResponseDto {
	private long ahaResMasSrNo;
	private String reqEncrypt;
	private String reqDecrypt;
	private Date entDateTM;
	private long pnReqMasSrNo;
	private long ahaReqMasSrNo;
	private String reqFor;
	public long getAhaResMasSrNo() {
		return ahaResMasSrNo;
	}
	public void setAhaResMasSrNo(long ahaResMasSrNo) {
		this.ahaResMasSrNo = ahaResMasSrNo;
	}
	public String getReqFor() {
		return reqFor;
	}
	public void setReqFor(String reqFor) {
		this.reqFor = reqFor;
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
	
	public Date getEntDateTM() {
		return entDateTM;
	}
	public void setEntDateTM(Date entDateTM) {
		this.entDateTM = entDateTM;
	}
	public long getPnReqMasSrNo() {
		return pnReqMasSrNo;
	}
	public void setPnReqMasSrNo(long pnReqMasSrNo) {
		this.pnReqMasSrNo = pnReqMasSrNo;
	}
	public long getAhaReqMasSrNo() {
		return ahaReqMasSrNo;
	}
	public void setAhaReqMasSrNo(long ahaReqMasSrNo) {
		this.ahaReqMasSrNo = ahaReqMasSrNo;
	}


	
	
}
