package com.netease.roommates.vo;

import com.netease.roommates.po.Personality;

public class QuestionnaireVO {
	private int yf;
	private int zx;
	private int cy;
	private int cw;
	private int ws;
	private int fk;
	private int xg;
	
	public QuestionnaireVO() {}

	public int getYf() {
		return yf;
	}

	public void setYf(int yf) {
		this.yf = yf;
	}

	public int getZx() {
		return zx;
	}

	public void setZx(int zx) {
		this.zx = zx;
	}

	public int getCy() {
		return cy;
	}

	public void setCy(int cy) {
		this.cy = cy;
	}

	public int getCw() {
		return cw;
	}

	public void setCw(int cw) {
		this.cw = cw;
	}

	public int getWs() {
		return ws;
	}

	public void setWs(int ws) {
		this.ws = ws;
	}

	public int getFk() {
		return fk;
	}

	public void setFk(int fk) {
		this.fk = fk;
	}

	public int getXg() {
		return xg;
	}

	public void setXg(int xg) {
		this.xg = xg;
	}
	
	public void populatePersonality(Personality personality) {
		personality.setHasHouse(yf);
		personality.setDailySchedule(zx);
		personality.setSmoking(cy);
		personality.setPet(cw);
		personality.setCleanliness(ws);
		personality.setVisitor(fk);
		personality.setPersonCharacter(xg);
	}
}
