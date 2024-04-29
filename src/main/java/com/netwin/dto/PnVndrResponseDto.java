package com.netwin.dto;

import java.util.Date;

public class PnVndrResponseDto {
	private int vndrPnResMassNo;
	private String reqDecrypt;
	private String reqEncrypt;
	private Date entryDate;
private String panNo;
private long pnReqMasSrNo;
public int getVndrPnResMassNo() {
	return vndrPnResMassNo;
}
public void setVndrPnResMassNo(int vndrPnResMassNo) {
	this.vndrPnResMassNo = vndrPnResMassNo;
}
public String getReqDecrypt() {
	return reqDecrypt;
}
public void setReqDecrypt(String reqDecrypt) {
	this.reqDecrypt = reqDecrypt;
}
public String getReqEncrypt() {
	return reqEncrypt;
}
public void setReqEncrypt(String reqEncrypt) {
	this.reqEncrypt = reqEncrypt;
}
public Date getEntryDate() {
	return entryDate;
}
public void setEntryDate(Date entryDate) {
	this.entryDate = entryDate;
}
public String getPanNo() {
	return panNo;
}
public void setPanNo(String panNo) {
	this.panNo = panNo;
}
public long getPnReqMasSrNo() {
	return pnReqMasSrNo;
}
public void setPnReqMasSrNo(long pnReqMasSrNo) {
	this.pnReqMasSrNo = pnReqMasSrNo;
}

}
