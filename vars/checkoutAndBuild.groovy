def call(Map config) {
    pipeline {
        environment {
            JAVAHOME = """${sh()}""".trim()
            JAVA_HOME = "$JAVAHOME"
            JAVA_TOOL_OPTIONS = " "
        }
        stages {
            stage('test') {
                steps {
                    script {
                        echo "JAVA_HOME " + evn.JAVA_HOME
                    }
                }
            }
        }
    }
}