pipeline {
    agent any

    tools {
        jdk 'JDK17'
        maven 'Maven3'
    }

    parameters {
        choice(name: 'ENVIRONMENT', choices: ['qa', 'dev', 'uat', 'prod'], description: 'Target configuration')
    }

    stages {
        stage('Test') {
            steps {
                sh "mvn -B clean test -P${params.ENVIRONMENT}"
            }
        }
    }

    post {
        always {
            junit testResults: 'target/surefire-reports/*.xml', allowEmptyResults: true
            archiveArtifacts artifacts: 'target/extent-reports/extent-report.html,target/logs/api-tests.log',
                    allowEmptyArchive: true
        }
    }
}
