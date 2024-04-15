package com.netwin.entiry;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "NETWPRODMAS")
public class NetwinProductionDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "NETWPRODSRNO_SEQ")
	@SequenceGenerator(name = "NETWPRODSRNO_SEQ", sequenceName = "NETWPRODSRNO_SEQ", allocationSize = 1)
	@Column(name = "NETWPRODSRNO", length = 10, nullable = false)
	private int NETWPRODSRNO;
	@Column(name = "NETWPRODID", length = 50, nullable = true)
	private String netwProdId;
	@Column(name = "NETWPRODNAME", length = 50, nullable = true)
	private String netwProdName;
	@Column(name = "PANVRFYREQ", length = 50, nullable = true)
	private String pnVrfyReq;
	@Column(name = "ADHRVRFYREQ", length = 50, nullable = true)
	private String adhrVrfyReq;
	@Column(name = "NETWVNDRS", length = 50, nullable = true)
	private int netwVndrs;

	public int getNETWPRODSRNO() {
		return NETWPRODSRNO;
	}

	public void setNETWPRODSRNO(int nETWPRODSRNO) {
		NETWPRODSRNO = nETWPRODSRNO;
	}

	public String getNetwProdId() {
		return netwProdId;
	}

	public void setNetwProdId(String netwProdId) {
		this.netwProdId = netwProdId;
	}

	public String getNetwProdName() {
		return netwProdName;
	}

	public void setNetwProdName(String netwProdName) {
		this.netwProdName = netwProdName;
	}

	public String getPnVrfyReq() {
		return pnVrfyReq;
	}

	public void setPnVrfyReq(String pnVrfyReq) {
		this.pnVrfyReq = pnVrfyReq;
	}

	public String getAdhrVrfyReq() {
		return adhrVrfyReq;
	}

	public void setAdhrVrfyReq(String adhrVrfyReq) {
		this.adhrVrfyReq = adhrVrfyReq;
	}

	public int getNetwVndrs() {
		return netwVndrs;
	}

	public void setNetwVndrs(int netwVndrs) {
		this.netwVndrs = netwVndrs;
	}

}
