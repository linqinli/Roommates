#屋檐下

## README
屋檐下App后端服务器代码

###Notice
数据库已经切换为云主机上的Mysql数据库系统

###Convention
程序源码放在  src/main/java目录下  
程序资源放在  src/main/resources目录下  

测试源码放在  src/test/java目录下  
测试资源放在  src/test/resources目录下  

程序调用方式为 Controller——>Service——>Mapper(DAO)  
	Controller:负责与前端交互  
	Service:负责业务层逻辑  
	Mapper:负责数据库访问，其实就是DAO  
	不存在跨层调用，也不存在逆向调用
###Requirement
	JDK1.7+
	Eclipse

###Using
  首先使用git clone项目到本地，再通过Eclipse导入项目。
  Maven运行参数：clean compile tomcat:run

###Reference
- [Spring](http://spring.io/)
- [Mybatis](http://mybatis.github.io/)