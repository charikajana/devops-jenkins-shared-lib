pipeline {
    agent any

    parameters {
        string(name: 'BRANCH_NAME', defaultValue: 'develop', description: 'Branch to checkout')
        string(name: 'RELEASE_VERSION', defaultValue: 'rel_25.12-01.00', description: 'Release Version')
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: "${params.BRANCH_NAME}",
                        url: 'git@github.com:charikajana/NimbusHotelBooker.git',
                        credentialsId: 'ChariToken'
            }
        }

        stage('Update POM Version') {
            steps {
                sh 'chmod +x scripts/update_pom.sh'
                sh "./scripts/update_pom.sh ${params.RELEASE_VERSION}"
            }
        }

        stage('Maven Build') {
            steps {
                sh "mvn clean install -DskipTests"
            }
        }
    }

    post {
        success {
            archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true
        }
    }
}
