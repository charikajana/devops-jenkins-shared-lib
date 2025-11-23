def call(Map config) {

    def lastResult = currentBuild.rawBuild.getPreviousBuild()?.getResult()

    pipeline {
        agent {
            node {
                label 'local-agent'
            }
        }
        parameters {
            booleanParam(name: 'BuildFlag', defaultValue: false, description: 'Enable Build Flag?')
            booleanParam(name: 'skipUnitTests', defaultValue: false, description: 'Skip Unit Tests?')
            booleanParam(name: 'promoteTrustedRepo', defaultValue: false, description: 'Promote Trusted Repo?')
        }

        stages {

            stage('Detect Java Home') {
                when {
                    expression { return lastResult == null || lastResult != "SUCCESS" }
                }
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
                    echo "BuildFlag: ${params.BuildFlag}"
                    echo "skipUnitTests: ${params.skipUnitTests}"
                    echo "promoteTrustedRepo: ${params.promoteTrustedRepo}"
                }
            }
        }
    }
}
