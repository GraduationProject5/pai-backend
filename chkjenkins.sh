#!/bin/bash
# This is a jenkins detection script
# Created by chen

JENKINS=`systemctl status jenkins | grep Active | awk '{print $3}' | cut -d "(" -f2 | cut -d ")" -f1`

# 解决 Can't connect to local MySQL server through socket '/var/lib/mysql/mysql.sock' (111)
MysqlD=`systemctl status mysqld | grep Active | awk '{print $3}' | cut -d "(" -f2 | cut -d ")" -f1`

if [ "$JENKINS" == "running" ]
        then
            echo "`date '+%Y/%m/%d %H:%M:%S'` Jenkins服务正在运行！" >> jenkins.log
        else
            systemctl restart jenkins
            echo "`date '+%Y/%m/%d %H:%M:%S'` Jenkins服务已重新启动！" >> jenkins.log
fi

if [ "$MysqlD" == "running" ]
        then
            echo "`date '+%Y/%m/%d %H:%M:%S'` Mysqld服务运行正常！" >> mysql.log
        else
            echo "`date '+%Y/%m/%d %H:%M:%S'` Mysqld服务已重新启动！" >> mysql.log

fi