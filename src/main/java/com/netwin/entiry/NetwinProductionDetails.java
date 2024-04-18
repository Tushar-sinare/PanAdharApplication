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
	@Column(name = "NETWPRODSRNO",length=10,nullable = false)
	private int netwProdSrNo;
	@Column(name = "NETWPRODID",length=50,nullable = true)
	private String netwProdId;
	@Column(name = "NETWPRODNAME",length=50,nullable = true)
	private String netwProdName;
	@Column(name = "PANVRFYREQ",length=50,nullable = true)
	private String pnVrfyReq;
	@Column(name = "ADHRVRFYREQ",length=50,nullable = true)
	private String adhrVrfyReq;
		
		public int getNetwProdSrNo() {
			return netwProdSrNo;
		}
		public void setNetwProdSrNo(int netwProdSrNo) {
			this.netwProdSrNo = netwProdSrNo;
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
		@Override
		public String toString() {
			return "NetwinProductionDetails [netwProdSrNo=" + netwProdSrNo + ", netwProdId=" + netwProdId
					+ ", netwProdName=" + netwProdName + ", pnVrfyReq=" + pnVrfyReq + ", adhrVrfyReq=" + adhrVrfyReq
					+ "]";
		}
		

}
