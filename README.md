# springbootofandroid
安卓后台-高校签到管理系统
# 快速开始

##后端地址：114.55.38.79:3300

### API

/admin:

* /login          管理员登陆验证
* /save           添加一个管理员
* /getAll         查询所有学生
* /saveStu        添加一条学生信息
* /start          开启今日签到
* /detail/{uuid}  查看某一个学生具体信息
* /update         更新学生信息
* /delete         删除学生及其签到情况
* /byDate         筛选指定日期的签到情况

/student:


* /login         学生登陆验证
* /attendance    签到
* /update        更新学生信息

