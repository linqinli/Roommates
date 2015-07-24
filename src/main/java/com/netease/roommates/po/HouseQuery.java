package com.netease.roommates.po;

public class HouseQuery {
	
	private String keyword;
	private String area;
	private int minPrice;
	private int maxPrice;
	private String room;
	private int startRow;
	private int pageSize;
	
	public HouseQuery(String kw,String area,int minPrice,int maxPrice,String room,int startRow,int pageSize){
		setKeyword(kw);
		setArea(area);
//		setPrice(price);
		
		setMinPrice(minPrice);
		setMaxPrice(maxPrice);
		
		setRoom(room);
		setStartRow(startRow);
		setPageSize(pageSize);
	}
	
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getRoom() {
		return room;
	}
	public void setRoom(String room) {
		this.room = room;
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(int minPrice) {
		this.minPrice = minPrice;
	}

	public int getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(int maxPrice) {
		this.maxPrice = maxPrice;
	}
}