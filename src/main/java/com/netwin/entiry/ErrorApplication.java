package com.netwin.entiry;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="ERRORSREQANDRES")
public class ErrorApplication {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ERRORSREQANDRES_SEQ")
	@SequenceGenerator(name = "ERRORSREQANDRES_SEQ", sequenceName = "ERRORSREQANDRES_SEQ", allocationSize = 1)
	@Column(name = "ERRORID",length=10,nullable = false)
	private int errorId;
	@Column(name = "ERRORCODE",length=10,nullable = false)
	private int errorCode;
	@Lob
	@Column(name = "ErrorDescription",columnDefinition = "TEXT",nullable = false)
	private String erroDesc;
	public int getErrorId() {
		return errorId;
	}
	public void setErrorId(int errorId) {
		this.errorId = errorId;
	}
	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	public String getErroDesc() {
		return erroDesc;
	}
	public void setErroDesc(String erroDesc) {
		this.erroDesc = erroDesc;
	}

	
}
