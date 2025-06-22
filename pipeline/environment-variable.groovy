pipeline {
    agent any

    environment {
        NAME = 'handsome'
        LASTNAME = 'boki'
    }

    stages {
        stage('Build') {
            steps {
                retry(3) {
                    sh 'echo $NAME $LASTNAME'
                }
            }
        }
    }
}