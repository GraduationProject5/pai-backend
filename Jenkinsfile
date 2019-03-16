// Created by chen.
//#!groovy
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
				//拉取代码
				git "${REPOSITORY}"
				script {
                    time = sh(returnStdout: true, script: 'date "+%Y%m%d%H%M"').trim()
                    git_version = sh(returnStdout: true, script: 'git log -1 --pretty=format:"%h"').trim()
                    build_tag = time+git_version
                }
                //echo "${build_tag}"
			}
		}

		stage('代码静态检查'){
			steps {
				//伪代码检查
				echo "${build_tag}"
				//echo "start code check"
			}
		}

		stage('编译+单元测试'){
			steps {
				echo "start compile"
                sh "ls -l"
                sh "mvn -U -am clean package"
			}
		}

		stage('构建镜像') {
            steps {
                echo "start build image"
                echo "image tag : ${build_tag}"
                sh "ls -l"
                sh "docker build -t ${DOCKER_REGISTRY}:${build_tag} ."

            }
        }

        stage('推送镜像') {
            steps {
                echo "start push image"

                  sh "ls -l"
                  withCredentials([usernamePassword(credentialsId: 'docker_registry', passwordVariable: 'docker_registryPassword', usernameVariable: 'docker_registryUsername')]) {
                      sh "docker login -u ${docker_registryUsername} -p ${docker_registryPassword} ${DOCKER_REGISTRY_HOST}"
                      sh "docker push ${DOCKER_REGISTRY}:${build_tag}"

                }
            }
        }

        stage('更新yml镜像版本') {
            steps{
                echo "start change yaml image tag"
                sh "ls -l"
                sh "sed -i 's/<BUILD_TAG>/${build_tag}/' docker-compose.yml"
                sh "cat docker-compose.yml"
            }
        }

        stage('启动服务') {
            steps {
                echo "start deploy"
                sh "ls -l"
                sh "docker-compose up -d"
            }
       }
    }

    post{
        always {
            emailext(
                subject: '${ENV, var="JOB_NAME"}-第${BUILD_NUMBER}次构建日志',
                body: '${FILE,path="email.html"}',
                to: '1156489606@qq.com'
            )
        }
    }
}