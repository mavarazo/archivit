pipeline {
    agent any

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