def call(Map config) {
    pipeline {
        agent any

        stages {
            stage('Detect Java Home') {
                steps {
                    script {
                        def detectedJavaHome = sh(
                                script: '''dirname $(dirname $(readlink -f $(which java)))''',
                                returnStdout: true
                        ).trim()

                        echo "JAVA_HOME: ${detectedJavaHome}"
                        env.JAVA_HOME = detectedJavaHome
                    }
                }
            }

            stage('Test') {
                steps {
                    echo "JAVA_HOME = ${env.JAVA_HOME}"
                }
            }
        }
    }
}