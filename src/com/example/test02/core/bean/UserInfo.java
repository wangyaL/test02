package com.example.test02.core.bean;

public class UserInfo {
	private int userId;
	private String username;
	private String shopName;
	private String good;
	private String normal;
	private String bad;
	private String pic;
	private String phone;
	private String address;
	private String email;
	private String area;
	private String minPrice;
	private String courierFees;
	private String info;
	public String getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(String minPrice) {
		this.minPrice = minPrice;
	}

	public String getCourierFees() {
		return courierFees;
	}

	public void setCourierFees(String courierFees) {
		this.courierFees = courierFees;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getGood() {
		return good;
	}

	public void setGood(String good) {
		this.good = good;
	}

	public String getNormal() {
		return normal;
	}

	public void setNormal(String normal) {
		this.normal = normal;
	}

	public String getBad() {
		return bad;
	}

	public void setBad(String bad) {
		this.bad = bad;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	@Override
	public String toString() {
		return "UserInfo [userId=" + userId + ", username=" + username
				+ ", shopName=" + shopName + ", good=" + good + ", normal="
				+ normal + ", bad=" + bad + ", pic=" + pic + ", phone=" + phone
				+ ", address=" + address + ", email=" + email + ", area="
				+ area + ", minPrice=" + minPrice + ", courierFees="
				+ courierFees + ", info=" + info + "]";
	}

}
