def call(Map config) {
    pipeline {
        agent any

        environment {
            JAVA_TOOL_OPTIONS = ""
        }

        stages {
            stage('Set JAVA_HOME') {
                steps {
                    script {
                        // Get JAVA_HOME from system
                        def detectedJavaHome = sh(
                                script: "dirname $(dirname $(readlink -f $(which java)))",
                                returnStdout: true
                        ).trim()

                        echo "Detected JAVA_HOME: ${detectedJavaHome}"

                        env.JAVA_HOME = detectedJavaHome
                    }
                }
            }

            stage('Test') {
                steps {
                    script {
                        echo "JAVA_HOME = ${env.JAVA_HOME}"
                    }
                }
            }
        }
    }
}