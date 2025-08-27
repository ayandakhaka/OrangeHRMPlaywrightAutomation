pipeline {
    agent any
    tools {
        maven 'Maven3'   // Name configured in Jenkins Global Tool Config
        jdk 'Java17'     // Name configured in Jenkins Global Tool Config
    }
    environment {
        CI = 'true'
        ORANGE_HRM_URL = 'https://opensource-demo.orangehrmlive.com/' // can override in Jenkins job
    }
    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/ayandakhaka/OrangeHRMPlaywrightAutomation.git'
            }
        }

        stage('Install Dependencies') {
            steps {
                sh 'mvn clean install -DskipTests'
                sh 'mvn exec:java -Dexec.mainClass="com.microsoft.playwright.CLI" -Dexec.args="install --with-deps"'
            }
        }

        stage('Run Playwright Tests') {
            steps {
                sh 'mvn test -Dsurefire.reportFormat=xml'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                    archiveArtifacts artifacts: 'target/surefire-reports/**', fingerprint: true
                }
            }
        }
    }
}
