#!/bin/bash
# This is a jenkins detection script
# Created by chen

JENKINS=`systemctl status jenkins | grep Active | awk '{print $3}' | cut -d "(" -f2 | cut -d ")" -f1`

if [ "$JENKINS" == "running" ]
        then
            echo "`date '+%Y/%m/%d %H:%M'` Jenkins服务正在运行！" >> jenkins.log
        else
            systemctl restart jenkins
            echo "`date '+%Y/%m/%d %H:%M'` Jenkins服务已重新启动！" >> jenkins.log
fi