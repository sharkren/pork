package com.mmhouse.pork.vo;

import java.util.List;

public class ContentsVO {
	
	private String contId;
	private String contType;
	private String email;
	private String contTitle;
	
	private String contMemo;
	private String secretYn;
	private String address;
	private String latitude;
	
	private String longitude;
	private String instDt;
	private String updtDt;
	private String delYn;
	
	private List<ContentsMediaVO> contentsMedia;
	
	public String getContId() {
		return contId;
	}

	public void setContId(String contId) {
		this.contId = contId;
	}

	public String getContType() {
		return contType;
	}

	public void setContType(String contType) {
		this.contType = contType;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContTitle() {
		return contTitle;
	}

	public void setContTitle(String contTitle) {
		this.contTitle = contTitle;
	}

	public String getContMemo() {
		return contMemo;
	}

	public void setContMemo(String contMemo) {
		this.contMemo = contMemo;
	}

	public String getSecretYn() {
		return secretYn;
	}

	public void setSecretYn(String secretYn) {
		this.secretYn = secretYn;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getInstDt() {
		return instDt;
	}

	public void setInstDt(String instDt) {
		this.instDt = instDt;
	}

	public String getUpdtDt() {
		return updtDt;
	}

	public void setUpdtDt(String updtDt) {
		this.updtDt = updtDt;
	}

	public String getDelYn() {
		return delYn;
	}

	public void setDelYn(String delYn) {
		this.delYn = delYn;
	}

	public List<ContentsMediaVO> getContentsMedia() {
		return contentsMedia;
	}

	public void setContentsMedia(List<ContentsMediaVO> contentsMedia) {
		this.contentsMedia = contentsMedia;
	}

	
	
} 
