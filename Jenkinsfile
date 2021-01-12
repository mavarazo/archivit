pipeline {
    agent any

    triggers {
        pollSCM '* * * * *'
    }

    environment {
        SPRING_PROFILES_ACTIVE = 'integrationtest'
        DOCKER_HUB_LOGIN = credentials('dockerhub')
        DOCKER_HUB_REPOSITORY = 'http://localhost:10130'
        DOCKER_HUB_IMAGE = 'archivit'
        DOCKER_HUB_TAG = "${DOCKER_HUB_REPOSITORY}/${DOCKER_HUB_IMAGE}:${env.BUILD_NUMBER}"
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
                    script {
                        image = docker.build("${DOCKER_HUB_IMAGE}:${env.BUILD_NUMBER}")
                    }
                }
            }
        }
        stage('Backend: Publish Docker Image') {
            steps {
                script {
                    docker.withRegistry("${DOCKER_HUB_REPOSITORY}") {
                        image.push()
                    }
                }
            }
        }
    }
}
