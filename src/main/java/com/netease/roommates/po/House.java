package com.netease.roommates.po;

public class House {
	private String houseId;
	private String title;
	private String area;
	private String community;
	private String publishTime;
	private String jjr;
	private String jjrname;
	private String jjrFrom;
	private String jjrFromDetail;
	
	private Integer price;
	private String roomType;
	private String imgUrl;
//	private Date updateTime;
	
//	public House(String houseId, String title,String area,String community,
//			String publishTime,String jjr,String jjrname,String jjrFrom,
//			String jjrFromDetail,Integer price,String roomType,Timestamp updateTime) {
//		this.houseId = houseId;
//		this.title = title;
//		this.area = area;
//		this.community = community;
//		this.publishTime = publishTime;
//		this.jjr = jjr;
//		this.jjrname = jjrname;
//		this.price = price;
//		this.roomType = roomType;
//	}
	
	public House(String houseId, String title,String area,String community,
			String publishTime,String jjr,String jjrname,Integer price,String roomType,String imgUrl) {
		this.houseId = houseId;
		this.title = title;
		this.area = area;
		this.community = community;
		this.publishTime = publishTime;
		this.jjr = jjr;
		this.jjrname = jjrname;
		this.price = price;
		this.roomType = roomType;
		this.imgUrl = imgUrl;
	}
	
	public House(String houseId,String title){
		setHouseId(houseId);
		setTitle(title);
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getCommunity() {
		return community;
	}

	public void setCommunity(String community) {
		this.community = community;
	}

	public String getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}

	public String getJjr() {
		return jjr;
	}

	public void setJjr(String jjr) {
		this.jjr = jjr;
	}

	public String getJjrname() {
		return jjrname;
	}

	public void setJjrname(String jjrname) {
		this.jjrname = jjrname;
	}

	public String getJjrFrom() {
		return jjrFrom;
	}

	public void setJjrFrom(String jjrFrom) {
		this.jjrFrom = jjrFrom;
	}

	public String getJjrFromDetail() {
		return jjrFromDetail;
	}

	public void setJjrFromDetail(String jjrFromDetail) {
		this.jjrFromDetail = jjrFromDetail;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public String getHouseId() {
		return houseId;
	}

	public void setHouseId(String houseId) {
		this.houseId = houseId;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
}