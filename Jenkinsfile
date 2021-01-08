pipeline {
    agent any

    triggers {
        pollSCM '* * * * *'
    }

    environment {
        SPRING_PROFILES_ACTIVE = 'integrationtest'
    }

    stages {
        stage('Backend: Build') {
            steps {
                dir('backend') {
                    sh './gradlew assemble'
                }
            }
        }
        stage('Backend: Test') {
            steps {
                dir('backend') {
                    sh './gradlew test'
                }
            }
        }
        stage('Backend: Build Docker Image') {
            steps {
                dir('backend') {
                    sh './gradlew docker'
                }
            }
        }
        stage('Backend: Push Docker Image') {
            environment {
                DOCKER_HUB_LOGIN = credentials('dockerhub')
            }
            steps {
                dir('backend') {
                    sh 'docker login --username=$DOCKER_HUB_LOGIN_USR --password=$DOCKER_HUB_LOGIN_PSW'
                    sh './gradlew dockerPush'
                }
            }
        }
    }
}
