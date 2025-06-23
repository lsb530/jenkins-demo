pipeline {
  agent any

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
        script {
          docker.image('openjdk:21-jdk').inside('-v /var/run/docker.sock:/var/run/docker.sock') {
            sh './gradlew clean build -x test'
          }
        }
      }
    }

    stage('Test') {
      steps {
        echo '[3/6] Running tests…'
        script {
          docker.image('openjdk:21-jdk').inside('-v /var/run/docker.sock:/var/run/docker.sock') {
            sh './gradlew test'
          }
        }
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
        script {
          docker.image('openjdk:21-jdk').inside('-v /var/run/docker.sock:/var/run/docker.sock') {
            sh './gradlew bootJar'
          }
        }
        archiveArtifacts artifacts: 'build/libs/*.jar', fingerprint: true
      }
    }

    stage('Docker Build') {
      when { branch 'main' }
      steps {
        echo '[5/6] Building Docker image…'
        script {
          // pull a light docker CLI container
          docker.image('docker:24.0.5').inside(
            '-v /var/run/docker.sock:/var/run/docker.sock ' +
            '-v $WORKSPACE:$WORKSPACE -w $WORKSPACE'
          ) {
            def version = sh(
              script: "./gradlew properties -q | grep '^version:' | awk '{print \$2}'",
              returnStdout: true
            ).trim()
            sh "docker build -t jenkins-demo:${version} ."
          }
        }
      }
    }

    stage('Deploy') {
      when { branch 'main' }
      steps {
        echo '[6/6] Deploying application…'
        // your deploy steps
      }
    }
  }

  post {
    always {
      echo '==> Pipeline completed, cleaning workspace…'
      cleanWs()
    }
    success { echo '✅ Pipeline succeeded!' }
    failure { echo '❌ Pipeline failed!' }
  }
}