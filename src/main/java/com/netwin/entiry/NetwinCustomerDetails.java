package com.netwin.entiry;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "NETWCUSTMAS")
public class NetwinCustomerDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "NETWCUSTSRNO_SEQ")
	@SequenceGenerator(name = "NETWCUSTSRNO_SEQ", sequenceName = "NETWCUSTSRNO_SEQ", allocationSize = 1)
	@Column(name = "NETWCUSTSRNO", length = 10, nullable = false)
	private int netwCustSrNo;
	@Column(name = "NETWCUSTID", length = 50, nullable = true)
	private String netwCustId;
	@Column(name = "NETWCUSTNAME", length = 50, nullable = true)
	private String netwCustName;

	public int getNetwCustSrNo() {
		return netwCustSrNo;
	}

	public void setNetwCustSrNo(int netwCustSrNo) {
		this.netwCustSrNo = netwCustSrNo;
	}

	public String getNetwCustId() {
		return netwCustId;
	}

	public void setNetwCustId(String netwCustId) {
		this.netwCustId = netwCustId;
	}

	public String getNetwCustName() {
		return netwCustName;
	}

	public void setNetwCustName(String netwCustName) {
		this.netwCustName = netwCustName;
	}

}
