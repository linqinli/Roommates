#屋檐下

## README
屋檐下App后端服务器代码

###Database
系统数据库暂时为Derby，Derby是JDK自带数据库，在JDK根目录到db目录下。
运行bin\ij.bat(Windows系统), 使用Derby创建数据库的方法如下：

	ij> connect 'jdbc:derby:e:\roommates;create';
	ij> create table user_info (
			id int primary key,
			name varchar(30)
		);

###Requirement
	JDK1.7+
	Eclipse

###Using
  首先使用git clone项目到本地，再通过Eclipse导入项目。
  Maven运行参数：clean compile tomcat:run

###Reference
- [Spring](http://spring.io/)
- [Mybatis](http://mybatis.github.io/)