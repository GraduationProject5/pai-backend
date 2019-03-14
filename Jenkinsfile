// Created by chen.
#!groovy
pipeline{
	agent any
	//定义仓库地址
	environment {
		REPOSITORY="https://github.com/GraduationProject5/pai-backend.git"
		SERVICE_DIR="pai-backend"
		DOCKER_REGISTRY_HOST="registry.cn-shanghai.aliyuncs.com"
		DOCKER_REGISTRY="registry.cn-shanghai.aliyuncs.com/cbb_registry/pai-backend"
	}

	stages {

		stage('获取代码'){
			steps {
				echo "start fetch code from git:${REPOSITORY}"
				//清空当前目录
				deleteDir()
				//拉去代码
				git "${REPOSITORY}"
				script {
                    time = sh(returnStdout: true, script: 'date "+%Y%m%d%H%M"').trim()
                    git_version = sh(returnStdout: true, script: 'git log -1 --pretty=format:"%h"').trim()
                    build_tag = time+git_version
                }
			}
		}

		stage('代码静态检查'){
			steps {
				//伪代码检查
				echo "start code check"
			}
		}

		stage('编译+单元测试'){
			steps {
				echo "start compile"
				//切换目录
				dir(SERVICE_DIR) {
					//重新打包
					sh "ls -l"
					sh 'mvn -U -am clean install'
				}
			}
		}

		stage('构建镜像') {
            steps {
                echo "start build image"
                echo "image tag : ${build_tag}"
                dir(SERVICE_DIR){
                    sh "ls -l"
                    sh "docker build -t ${DOCKER_REGISTRY}:${build_tag} ."
                }
            }
        }

       stage('推送镜像') {
            steps {
                echo "start push image"
                dir(SERVICE_DIR){
                  sh "ls -l"
                  withCredentials([usernamePassword(credentialsId: 'docker_registry', passwordVariable: 'docker_registryPassword', usernameVariable: 'docker_registryUsername')]) {
                      sh "docker login -u ${docker_registryUsername} -p ${docker_registryPassword} ${DOCKER_REGISTRY_HOST}"
                      sh "docker push ${DOCKER_REGISTRY}:${build_tag}"
                  }
                }
            }
        }

	    stage('构建镜像'){
			steps {
				echo "start build image"
				dir('sso-client1') {
					//build镜像
					sh 'docker build -t hub.c.163.com/longfeizheng/sso-client1:1.0 .'
					//登录163云仓库
					sh 'docker login -u longfei_zheng@163.com -p password hub.c.163.com'
					//推送镜像到163仓库
					sh 'docker push hub.c.163.com/longfeizheng/sso-client1:1.0'
				}
			}
		}

        stage('启动服务'){
            steps {
                echo "start sso-merryyou"
                //重启服务
                bat 'docker-compose up -d --build'
            }
        }

	}
}