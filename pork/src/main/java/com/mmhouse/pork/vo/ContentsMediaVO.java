package com.mmhouse.pork.vo;

import org.apache.ibatis.type.Alias;

@Alias("com.mmhouse.pork.vo.ContentsMediaVO")
public class ContentsMediaVO {
	
	private String contId;
	private String contType;
	private String mediaId;
	private String mediaName;
	private String mediaSize;
	private String address;
	private String latitude;
	private String longitude;
	private String delYn;
	
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
	public String getMediaId() {
		return mediaId;
	}
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	public String getMediaName() {
		return mediaName;
	}
	public void setMediaName(String mediaName) {
		this.mediaName = mediaName;
	}
	public String getMediaSize() {
		return mediaSize;
	}
	public void setMediaSize(String mediaSize) {
		this.mediaSize = mediaSize;
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
	public String getDelYn() {
		return delYn;
	}
	public void setDelYn(String delYn) {
		this.delYn = delYn;
	}
	
}
