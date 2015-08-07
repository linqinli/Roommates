package com.netease.match.service;

public interface IMatchSqlService {
	
	public String generateSqlStrByCondition(int id, int xb, int f, int gs, int cy, int cw,
			int zx, int ws, int xg, int fk);
	public String generateXbSql(int xb);
	public String generateGsSql(int gs);
	public String generateCySql(int cy);
	public String generateCwSql(int cw);
	public String generateZxSql(int zx);
	public String generateWsSql(int ws);
	public String generateXgSql(int xg);
	public String generateFkSql(int fk);
}
