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
@Table(name = "ADHREQDETAILS")
public class AharRequest {
	
		@Id
		@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ADHREQDETAILS_SEQ")
		@SequenceGenerator(name = "ADHREQDETAILS_SEQ", sequenceName = "ADHREQDETAILS_SEQ", allocationSize = 1)
		@Column(name = "ADHREQDETSRNO",length=10,nullable = false)
		private long adhReqDetSrNo;
		@OneToOne 
		@JoinColumn(name = "ADHREMASSRNO",nullable = true)
		private AharNtwnRequest aharNtwnRequest;
		@OneToOne 
		@JoinColumn(name = "ADHVNDRSRNO",nullable = true)
		private AharVendorDetails aharVndrDetails;
		@OneToOne 
		@JoinColumn(name = "NETWCUSTSRNO",nullable = true)
		private NetwinCustomerDetails ntwnCustomerDetails;
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
		@Column(name = "ADHARNO",length=12,nullable = true)
		private String adharNo;
		@Column(name = "BRANCH",length=50,nullable = true)
		private String branchId;
		@Column(name = "PRODID",length=50,nullable = true)
		private String prodId;
		@Column(name = "CUSTID",length=50,nullable = true)
		private String custId;
		public long getAdhReqDetSrNo() {
			return adhReqDetSrNo;
		}
		public void setAdhReqDetSrNo(long adhReqDetSrNo) {
			this.adhReqDetSrNo = adhReqDetSrNo;
		}
		public AharNtwnRequest getAharNtwnRequest() {
			return aharNtwnRequest;
		}
		public void setAharNtwnRequest(AharNtwnRequest aharNtwnRequest) {
			this.aharNtwnRequest = aharNtwnRequest;
		}
		public AharVendorDetails getAharVndrDetails() {
			return aharVndrDetails;
		}
		public void setAharVndrDetails(AharVendorDetails aharVndrDetails) {
			this.aharVndrDetails = aharVndrDetails;
		}
		public NetwinCustomerDetails getNtwnCustomerDetails() {
			return ntwnCustomerDetails;
		}
		public void setNtwnCustomerDetails(NetwinCustomerDetails ntwnCustomerDetails) {
			this.ntwnCustomerDetails = ntwnCustomerDetails;
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
		@Override
		public String toString() {
			return "AharRequest [adhReqDetSrNo=" + adhReqDetSrNo + ", aharNtwnRequest=" + aharNtwnRequest
					+ ", aharVndrDetails=" + aharVndrDetails + ", ntwnCustomerDetails=" + ntwnCustomerDetails
					+ ", netwinProductionDetails=" + netwinProductionDetails + ", appDate=" + appDate + ", reqBy="
					+ reqBy + ", pageId=" + pageId + ", userReqSrNo=" + userReqSrNo + ", adharNo=" + adharNo
					+ ", branchId=" + branchId + ", prodId=" + prodId + ", custId=" + custId + "]";
		}
		
		
	}


