#!groovy
def Common = new odos.jenkins.Common()
def GIT_URL = "https://github.com/ODOS-II-code-challange/odos_crrs_service.git"

pipeline {
    agent any

    stages {
             
        stage('Build') {
            steps {
                script{
                  Common.slack 'Building...'
                  Common.jHipsterBuild()
                }
            }
        }
     
        stage('Sonar Scan') {
          steps {
            script{
            Common.slack 'Sonar Scan for build request...'
                       
             withCredentials([usernamePassword(credentialsId: 'sonar-jenkins', passwordVariable: 'SONAR_PASSWORD', usernameVariable: '')]) {
                  sh """
                        ./gradlew --full-stacktrace\
                        -Dsonar.host.url=http://localhost:9000 \
                        -Dsonar.github.endpoint=https://api.github.com \
                        -Dsonar.github.repository=ODOS-II-code-challange/odos_crrs_service  \
                        -Dsonar.projectKey=gov.dhs.uscis.odos:crrsvc \
                        -Dsonar.analysis.mode=preview \
                        -Dsonar.github.pullRequest=$ghprbPullId \
                        -Dsonar.github.repository=$ghprbGhRepository \
                        -Dsonar.github.login=odos-jenkins \
                        -Dsonar.github.oauth=c1d702b375ef96065d3e4fa6996681033fbbf1e2 \
                        -Dsonar.login=jenkins \
                        -Dsonar.password=$SONAR_PASSWORD \
                        -x test sonarqube
                    """
            }
               
            }
          }
        
        }
         stage(' Compare Code Coverage') {
            steps {
                script{
                   withEnv(["GIT_URL=${GIT_URL}"]){
                        currentBuild.result = 'SUCCESS'
                        step([$class: 'CompareCoverageAction'])
                        
                    }
                }
            }
        }

    }
}

