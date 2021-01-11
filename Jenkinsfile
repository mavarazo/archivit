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
                    sh './gradlew bootBuildImage'
                }
            }
        }
    }
}
