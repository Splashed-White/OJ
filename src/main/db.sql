
create database if not exists servlet_oj;
use servlet_oj;
drop table if exists oj_table;
create table oj_table(
    id int primary key auto_increment,
    title varchar(50),
    level varchar(50),
    description varchar(4096),
    templateCode varchar(4096),
    testCode varchar(4096)
);

--题目序号 题目标题 题目难度 题目的描述(题干) 题目的代码模板(给用户展示的初始代码) 测试用例代码