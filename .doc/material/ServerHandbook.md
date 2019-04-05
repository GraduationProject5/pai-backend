# 服务器维护指南
#### 编辑：陈步兵

> 查看数据库运行日志，登陆服务器之后 
> 
```
docker ps 
docker logs -f -t [docker ps 的镜像名称]
```
>删除本地的缓存目录
>
```
cd /var/lib/jenkins/workspace 
ls   //pai-backend list
rm -rf [dir name]
```


#### 1. 服务器数据库
`RROR 2002 (HY000): Can't connect to local MySQL server through socket '/var/lib/mysql/mysql.sock' (111)`

* 鉴于本服务器，建议直接命令行 `init 6`

#### 2. 数据库连接池
`com.mysql.cj.exceptions.CJCommunicationsException: Communications link failure`

`Caused by: java.net.ConnectException: Connection refused (Connection refused)`

* 由于长时间没有数据操作，建议重启应用

> ```
cd /var/lib/jenkins/workspace 
ls   //pai-backend list
cd pai-backend
ls //确认是否有docker-compose.ymal
cat docker-compose.ymal //查看确认应用版本号
docker-compose down -v 
docker-compose up -d
```

#### 3. 数据库验证错误
`Unable to load authentication plugin 'caching_sha2_password'`

* MySQL在8.0后验证方式由mysql_native_password变为caching_sha2_password

`solution : alter user 'root'@‘%’ identified with mysql_native_password by 'password'`

#### 4. 数据库导入文件错误

` "The used command is not allowed with this MySQL version"`

* 数据库localinfile设置

>```
sudo vi /etc/mysql/my.cnf
	[mysqld]
	local-infile 
	[mysql]
	local-infile 
	or
	local-infile=1
mysql --local-infile -uroot -pyourpwd yourdbname
```

#### 6. jenkins 构建错误

* 登陆Jenkins，查看 step 报错信息
	* 常见错误：
		* 端口占用（本应用port: 13100）关闭原来的应用
			* kill（我准备再写一个脚本，每次构建检查端口占用）
		* 编译错误（代码报错信息）修改代码
			* 审查修改代码
		* 网络问题（git代码pull不下来，images push不上去）
			* abort 当前构建任务，重新手动构建

#### 7. 服务器数据库安全设置

>```
1. local_infile: 
		loose-local-infile = 1
2. secure_priv:
		secure_file_priv=''
3. 远程多用户访问：
		%
4. 验证方式：
		MySQL在8.0后验证方式由mysql_native_password变为caching_sha2_password
```