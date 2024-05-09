package com.netwin.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;


@NamedQuery(name="NetwinCustomerDetails.findByNetwCustId", query="SELECT NEW com.netwin.dto.NetwnCustomerDto((a.netwCustSrNo)as netwCustSrNo,(a.netwCustId)as netwCustId,(a.netwCustName) as netwCustName,(a.netwVndrs) as netwVndrs) FROM NetwinCustomerDetails a WHERE a.netwCustId = :netwCustId")

@Entity
@Table(name = "NETWCUSTMAS")
public class NetwinCustomerDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "NETWCUSTSRNO_SEQ")
	@SequenceGenerator(name = "NETWCUSTSRNO_SEQ", sequenceName = "NETWCUSTSRNO_SEQ", allocationSize = 1)
	@Column(name = "NETWCUSTSRNO",length=10,nullable = false)
	private int netwCustSrNo;
	@Column(name = "NETWCUSTID",length=50,nullable = true)
	private String netwCustId;
	@Column(name = "NETWCUSTNAME",length=50,nullable = true)
	private String netwCustName;
	@Column(name = "NETWVNDRS",length=50,nullable = true)
	private int netwVndrs;
	
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
	public int getNetwVndrs() {
		return netwVndrs;
	}
	public void setNetwVndrs(int netwVndrs) {
		this.netwVndrs = netwVndrs;
	}
	

}
