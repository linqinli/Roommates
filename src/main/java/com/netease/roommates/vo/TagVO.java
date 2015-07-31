package com.netease.roommates.vo;

import com.netease.roommates.po.Personality;
import com.netease.utils.MessageUtils;

public class TagVO {
	private String zx;
	private String cy;
	private String cw;
	private String ws;
	private String fk;
	private String xg;

	public TagVO() {

	}

	public TagVO(Personality personality) {
		zx = MessageUtils.getMessage("DAILI_SCHEDULE_" + personality.getDailySchedule());
		cy = MessageUtils.getMessage("SOMKING_" + personality.getSmoking());
		cw = MessageUtils.getMessage("PET_" + personality.getPet());
		ws = MessageUtils.getMessage("CLEAN_" + personality.getCleanliness());
		fk = MessageUtils.getMessage("VISITOR_" + personality.getVisitor());
		xg = MessageUtils.getMessage("CHARACTER_" + personality.getPersonCharacter());
	}

	public String getZx() {
		return zx;
	}

	public void setZx(String zx) {
		this.zx = zx;
	}

	public String getCy() {
		return cy;
	}

	public void setCy(String cy) {
		this.cy = cy;
	}

	public String getCw() {
		return cw;
	}

	public void setCw(String cw) {
		this.cw = cw;
	}

	public String getWs() {
		return ws;
	}

	public void setWs(String ws) {
		this.ws = ws;
	}

	public String getFk() {
		return fk;
	}

	public void setFk(String fk) {
		this.fk = fk;
	}

	public String getXg() {
		return xg;
	}

	public void setXg(String xg) {
		this.xg = xg;
	}
}
