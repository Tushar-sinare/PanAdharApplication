package com.netwin.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

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
	@Column(name = "LINENUMBER",length=10,nullable = false)
	private int lineNumber;

	@Column(name = "CLASSNAME",length=100,nullable = false)
	private String className;
	@Column(name = "METHODNAME",length=50,nullable = false)
	private String methoName;
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
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getMethoName() {
		return methoName;
	}
	public void setMethoName(String methoName) {
		this.methoName = methoName;
	}
	public int getLineNumber() {
		return lineNumber;
	}
	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}

	
}
