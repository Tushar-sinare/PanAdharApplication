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

@Table(name="pnresdetails")
public class PnResponse {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pnresdetails_SEQ")
	@SequenceGenerator(name = "pnresdetails_SEQ", sequenceName = "pnresdetails_SEQ", allocationSize = 1)
	@Column(name = "PNRESDETSRNO",length=10,nullable = false)
	private long pnResDetSrNo;
	@OneToOne 
	@JoinColumn(name = "vndrpnresmassrno")
	private PnVndrResponse pnVndrResponse;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "APPDATE")
	private Date appDate;
	@Column(name = "PANNO",length=10,nullable = true)
	private String panNo;
	@Column(name = "CUSTID",length=50,nullable = true)
	private String custId;
	public long getPnResDetSrNo() {
		return pnResDetSrNo;
	}
	public void setPnResDetSrNo(long pnResDetSrNo) {
		this.pnResDetSrNo = pnResDetSrNo;
	}
	public PnVndrResponse getPnVndrResponse() {
		return pnVndrResponse;
	}
	public void setPnVndrResponse(PnVndrResponse pnVndrResponse) {
		this.pnVndrResponse = pnVndrResponse;
	}
	public Date getAppDate() {
		return appDate;
	}
	public void setAppDate(Date appDate) {
		this.appDate = appDate;
	}
	public String getPanNo() {
		return panNo;
	}
	public void setPanNo(String panNo) {
		this.panNo = panNo;
	}
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	
}


