pipeline {
    agent any

    tools {
        nodejs 'NodeJS-15.5.1'
    }

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
        stage('Frontend Install') {
            steps { 
                dir('frontend') {
                    sh 'npm install --cache npm_cache'
                } 
            }
        }
        stage('Lint') {
            steps { 
                dir('frontend') {
                    // sh 'ng lint'
                    echo 'Lint'
                }
            }
        }
        stage('Frontend Build') {
            steps { 
                dir('frontend') {
                    sh 'npm run-script build --prod --aot --sm --progress=false'
                }
            }
        }
        stage('Frontend Test') {
            steps {
                dir('frontend') {
                    echo 'Test'
                }
            }
        }
    }
}
