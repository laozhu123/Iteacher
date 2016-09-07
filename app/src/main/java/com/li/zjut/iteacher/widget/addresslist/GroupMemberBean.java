package com.li.zjut.iteacher.widget.addresslist;

public class GroupMemberBean {

	private String name;   //显示的数据
	private String sortLetters;  //显示数据拼音的首字母
	private String phone;
	private String pic;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSortLetters() {
		return sortLetters;
	}
	public void setSortLetters(String sortLetters) {
		this.sortLetters = sortLetters;
	}
}
