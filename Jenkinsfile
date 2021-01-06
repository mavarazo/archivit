pipeline {
    agent any

    environment {
        SPRING_PROFILES_ACTIVE = 'integrationtest'
    }

    stages {
        stage('Spring Boot Build') {
            steps {
                dir('backend') {
                    sh './gradlew assemble'
                }
            }
        }
        stage('Spring Boot Test') {
            steps {
                dir('backend') {
                    sh './gradlew test'
                }
            }
        }
    }
}