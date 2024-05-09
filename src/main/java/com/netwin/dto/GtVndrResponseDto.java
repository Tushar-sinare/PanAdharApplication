package com.netwin.dto;

import java.util.Date;

public class GtVndrResponseDto {
	private int vndrGtResMassNo;
	private String reqDecrypt;
	private String reqEncrypt;
	private Date entryDate;
private String userGstNo;
private long gtReqMasSrNo;
public int getVndrGtResMassNo() {
	return vndrGtResMassNo;
}
public void setVndrGtResMassNo(int vndrGtResMassNo) {
	this.vndrGtResMassNo = vndrGtResMassNo;
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
public String getUserGstNo() {
	return userGstNo;
}
public void setUserGstNo(String userGstNo) {
	this.userGstNo = userGstNo;
}
public long getGtReqMasSrNo() {
	return gtReqMasSrNo;
}
public void setGtReqMasSrNo(long gtReqMasSrNo) {
	this.gtReqMasSrNo = gtReqMasSrNo;
}


}
