数据库系统暂时为Derby，后期需要切换为其它数据库系统。
需要通过jdk根目录下的db\bin\ij.bat启动数据库，connect 'jdbc:derby:e:\roommates;create=true';
该命令在e:\roommates下建立了一个数据库，同时我们需要建一个user_info表，
create table user_info (
  id int primary key,
  name varchar(30)
);

下面就可以导入项目到eclipse运行了。