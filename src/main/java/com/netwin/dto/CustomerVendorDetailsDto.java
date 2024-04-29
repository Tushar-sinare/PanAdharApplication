package com.netwin.dto;



public class CustomerVendorDetailsDto {
	
	private String adharNo;
	private String branchId;
	private String prodId;
	private String custId;
	private int vendorId;
	private String panNo;
private long ahaReqMasSrNo;
private long pnReqMasSrNo;
	public int getVendorId() {
		return vendorId;
	}
	public void setVendorId(int vendorId) {
		this.vendorId = vendorId;
	}
	public String getAdharNo() {
		return adharNo;
	}
	public void setAdharNo(String adharNo) {
		this.adharNo = adharNo;
	}
	public String getBranchId() {
		return branchId;
	}
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
	public String getProdId() {
		return prodId;
	}
	public void setProdId(String prodId) {
		this.prodId = prodId;
	}
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	

	public String getPanNo() {
		return panNo;
	}
	public void setPanNo(String panNo) {
		this.panNo = panNo;
	}



	public long getAhaReqMasSrNo() {
		return ahaReqMasSrNo;
	}
	public void setAhaReqMasSrNo(long ahaReqMasSrNo) {
		this.ahaReqMasSrNo = ahaReqMasSrNo;
	}
	public long getPnReqMasSrNo() {
		return pnReqMasSrNo;
	}
	public void setPnReqMasSrNo(long pnReqMasSrNo) {
		this.pnReqMasSrNo = pnReqMasSrNo;
	}
	public CustomerVendorDetailsDto() {
		super();
		
	}

	
}
