package com.netwin.entiry;



import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity

@Table(name="pnreqdetails")
public class PnRequest {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PNREQDETSRNO_SEQ")
	@SequenceGenerator(name = "PNREQDETSRNO_SEQ", sequenceName = "PNREQDETSRNO_SEQ", allocationSize = 1)
	@Column(name = "PNREQDETSRNO",length=10,nullable = false)
	private long PnReqDetSrNo;
	@OneToOne 
	@JoinColumn(name = "PNREMASSRNO",nullable = true)
	private PnNetwinRequest pnNetwinRequest;
	@OneToOne 
	@JoinColumn(name = "PNVNDRSRNO",nullable = true)
	private PnVendorDetails pnVendorDetails;
	@OneToOne 
	@JoinColumn(name = "NETWCUSTSRNO",nullable = true)
	private NetwinCustomerDetails netwinCustomerDetails;
	@OneToOne 
	@JoinColumn(name = "NETWPRODSRNO",nullable = true)
	private NetwinProductionDetails netwinProductionDetails;
	 @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "APPDATE",nullable = false)
	private Date appDate;
	@Column(name = "REQBY",length=50,nullable = true)
	private String reqBy;
	@Column(name = "PAGEID",length=50,nullable = true)
	private String pageId;
	@Column(name = "USERREQNO",length=50,nullable = true)
	private String userReqSrNo;
	@Column(name = "PANNO",length=10,nullable = true)
	private String panNo;
	@Column(name = "BRANCH",length=50,nullable = true)
	private String branchId;
	@Column(name = "PRODID",length=50,nullable = true)
	private String prodId;
	@Column(name = "CUSTID",length=50,nullable = true)
	private String custId;
	public long getPnReqDetSrNo() {
		return pnReqDetSrNo;
	}
	public void setPnReqDetSrNo(long pnReqDetSrNo) {
		this.pnReqDetSrNo = pnReqDetSrNo;
	}
	public PnNetwinRequest getPnNetwinRequest() {
		return pnNetwinRequest;
	}
	public void setPnNetwinRequest(PnNetwinRequest pnNetwinRequest) {
		this.pnNetwinRequest = pnNetwinRequest;
	}
	public PnVendorDetails getPnVendorDetails() {
		return pnVendorDetails;
	}
	public void setPnVendorDetails(PnVendorDetails pnVendorDetails) {
		this.pnVendorDetails = pnVendorDetails;
	}
	public NetwinCustomerDetails getNetwinCustomerDetails() {
		return netwinCustomerDetails;
	}
	public void setNetwinCustomerDetails(NetwinCustomerDetails netwinCustomerDetails) {
		this.netwinCustomerDetails = netwinCustomerDetails;
	}
	public NetwinProductionDetails getNetwinProductionDetails() {
		return netwinProductionDetails;
	}
	public void setNetwinProductionDetails(NetwinProductionDetails netwinProductionDetails) {
		this.netwinProductionDetails = netwinProductionDetails;
	}
	public Date getAppDate() {
		return appDate;
	}
	public void setAppDate(Date appDate) {
		this.appDate = appDate;
	}
	public String getReqBy() {
		return reqBy;
	}
	public void setReqBy(String reqBy) {
		this.reqBy = reqBy;
	}
	public String getPageId() {
		return pageId;
	}
	public void setPageId(String pageId) {
		this.pageId = pageId;
	}
	public String getUserReqSrNo() {
		return userReqSrNo;
	}
	public void setUserReqSrNo(String userReqSrNo) {
		this.userReqSrNo = userReqSrNo;
	}
	public String getPanNo() {
		return panNo;
	}
	public void setPanNo(String panNo) {
		this.panNo = panNo;
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
	
	
}


