package com.li.zjut.iteacher.bean.update;

import java.io.Serializable;

public class ReturnAppUpdate implements Serializable{
	String appname;
	String versionname;
	int versioncode;
	//下载地址
	String downloadaddress;
	//发布时间
	String releasetime;
	String id;
	String updateinfo;
	public String getAppname() {
		return appname;
	}
	public void setAppname(String appname) {
		this.appname = appname;
	}
	public String getVersionname() {
		return versionname;
	}
	public void setVersionname(String versionname) {
		this.versionname = versionname;
	}
	public int getVersioncode() {
		return versioncode;
	}
	public void setVersioncode(int versioncode) {
		this.versioncode = versioncode;
	}

	public String getReleasetime() {
		return releasetime;
	}
	public void setReleasetime(String releasetime) {
		this.releasetime = releasetime;
	}

	public String getUpdateinfo() {
		return updateinfo;
	}
	public void setUpdateinfo(String updateinfo) {
		this.updateinfo = updateinfo;
	}
	
	public String getDownloadaddress() {
		return downloadaddress;
	}
	public void setDownloadaddress(String downloadaddress) {
		this.downloadaddress = downloadaddress;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
}
