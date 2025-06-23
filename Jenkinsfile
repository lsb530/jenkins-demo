pipeline {
    agent any

    environment {
        JAVA_HOME = tool 'JDK21'
        PATH      = "${JAVA_HOME}/bin:${env.PATH}"
    }

    stages {
        stage('Checkout') {
            steps {
                echo '[1/6] Checking out source…'
                checkout scm
            }
        }

        stage('Build') {
            steps {
                echo '[2/6] Cleaning & building…'
                sh './gradlew clean build -x test'
            }
        }

        stage('Test') {
            steps {
                echo '[3/6] Running tests…'
                sh './gradlew test'
            }
            post {
                always {
                    echo '[3/6] Publishing JUnit results…'
                    junit '**/build/test-results/test/*.xml'
                }
            }
        }

        stage('Package') {
            steps {
                echo '[4/6] Packaging application…'
                sh './gradlew bootJar'
                archiveArtifacts artifacts: 'build/libs/*.jar', fingerprint: true
            }
        }

        stage('Docker Build') {
            when {
                branch 'main'
            }
            steps {
                echo '[5/6] Building Docker image…'
                script {
                    def version = sh(
                        script: "./gradlew properties -q | grep '^version:' | awk '{print \$2}'",
                        returnStdout: true
                    ).trim()
                    def imageName = "jenkins-demo:${version}"
                    sh "docker build -t ${imageName} ."
                }
            }
        }

        stage('Deploy') {
            when {
                branch 'main'
            }
            steps {
                echo '[6/6] Deploying application…'
                // 배포 스크립트 추가
            }
        }
    }

    post {
        always {
            echo '==> Pipeline completed, cleaning workspace…'
            cleanWs()
        }
        success {
            echo '✅ Pipeline succeeded!'
        }
        failure {
            echo '❌ Pipeline failed!'
        }
    }
}