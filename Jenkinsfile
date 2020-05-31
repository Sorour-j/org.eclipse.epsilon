@NonCPS
def getSlackMessage() {
    def duration = currentBuild.durationString.minus(" and counting")
    def message = "Build <${env.BUILD_URL}|#${env.BUILD_NUMBER}> of <https://git.eclipse.org/c/epsilon/org.eclipse.epsilon.git/log/?h=${env.BRANCH_NAME}|${currentBuild.fullProjectName}> "
    
    message += (currentBuild.currentResult == "SUCCESS" ? "passed" : "failed") + " in ${duration}\n\n"

    for (changeSet in currentBuild.changeSets) {
      for (entry in changeSet.items) {
        message += "`${entry.commitId.take(7)}` ${entry.msg} - ${entry.author}\n"
      }
    }
    return message
}

def baseTriggers = '(pom\\.xml)|(Jenkinsfile)|(plugins\\/.*)|(pom[-]plain[.]xml)'
def updateTriggers = "${baseTriggers}|(standalone\\/.*)|(features\\/.*)|(releng\\/.*(target|updatesite)\\/.*)"

pipeline {
    agent any
    options {
      disableConcurrentBuilds()
      buildDiscarder(logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '2', daysToKeepStr: '14', numToKeepStr: ''))
    }
    tools {
        maven 'apache-maven-latest'
        jdk 'adoptopenjdk-hotspot-jdk8-latest'
    }
    triggers {
        pollSCM('H/5 * * * *')
    }
    stages {
      stage('Main') {
        agent {
          kubernetes {
            label 'migration'
          }
        }
        stages {
          stage('Build') {
            when {
              anyOf {
                changeset comparator: 'REGEXP', pattern: "${updateTriggers}|(tests\\/.*)"
                expression { return currentBuild.number == 1 }
              }
            } 
            steps {
              sh 'mvn -B -T 1C clean install -P eclipse-sign'
            }
          }
          stage('Test') {
            when {
              anyOf {
                changeset comparator: 'REGEXP', pattern: "${baseTriggers}|(tests\\/.*)"
                expression { return currentBuild.number == 1 }
              }
            }
            steps {
              wrap([$class: 'Xvnc', takeScreenshot: false, useXauthority: false]) {
                sh 'mvn -B -f tests/org.eclipse.epsilon.test verify -P plugged'
              }
              sh 'mvn -B -f tests/org.eclipse.epsilon.test surefire:test -P ci'
            }
          }
          stage('Javadocs') {
            when {
              anyOf {
                changeset comparator: 'REGEXP', pattern: "${baseTriggers}"
                expression { return currentBuild.number == 1 }
              }
            }
            steps {
              sh 'mvn -B javadoc:aggregate'
            }
          }
          stage('Plain Maven build') {
            when {
              anyOf {
                changeset comparator: 'REGEXP', pattern: "${updateTriggers}"
                expression { return currentBuild.number == 1 }
              }
            }
            steps {
              sh 'mvn -B -T 1C -f pom-plain.xml compile'
            }
          }
          stage('Update site') {
            when {
              allOf {
                branch 'master'
                anyOf {
                  changeset comparator: 'REGEXP', pattern: "${updateTriggers}"
                  expression { return currentBuild.number == 1 }
                }
              }
            }
            steps {
              sh 'mvn -f releng install -P updatesite'
              lock('download-area') {
                sshagent (['projects-storage.eclipse.org-bot-ssh']) {
                  sh '''
                    INTERIM=/home/data/httpd/download.eclipse.org/epsilon/interim
                    LATEST=/home/data/httpd/download.eclipse.org/epsilon/latest
                    SITEDIR="$WORKSPACE/releng/org.eclipse.epsilon.updatesite/target"
                    if [ -d "$SITEDIR" ]; then
                      ssh genie.epsilon@projects-storage.eclipse.org rm -rf $INTERIM
                      scp -r "$SITEDIR/repository" genie.epsilon@projects-storage.eclipse.org:${INTERIM}
                      scp "$SITEDIR"/*.zip genie.epsilon@projects-storage.eclipse.org:${INTERIM}/site.zip
                    fi
                    JARSDIR="$WORKSPACE/standalone/org.eclipse.epsilon.standalone/target"
                    if [ -d "$JARSDIR" ]; then
                      ssh genie.epsilon@projects-storage.eclipse.org "rm -rf $LATEST/jars && mkdir -p $LATEST/jars"
                      scp "$JARSDIR"/epsilon-* genie.epsilon@projects-storage.eclipse.org:${LATEST}/jars
                    fi
                    JAVADOCDIR="$WORKSPACE/target/site/apidocs"
                    if [ -d "$JAVADOCDIR" ]; then
                      ssh genie.epsilon@projects-storage.eclipse.org "rm -rf ${LATEST}/javadoc"
                      scp -r "$JAVADOCDIR" genie.epsilon@projects-storage.eclipse.org:${LATEST}/javadoc
                    fi
                  '''
                }
              }
            }
          }
          stage('Deploy to OSSRH') {
            when {
              allOf {
                branch 'master'
                anyOf {
                  changeset comparator: 'REGEXP', pattern: "${updateTriggers}"
                  expression { return currentBuild.number == 1 }
                }
              }
            }
            environment {
              KEYRING = credentials('secret-subkeys.asc')
            }
            steps {
              sh '''
                gpg --batch --import "${KEYRING}"
                for fpr in $(gpg --list-keys --with-colons  | awk -F: '/fpr:/ {print $10}' | sort -u);
                do
                  echo -e "5\ny\n" |  gpg --batch --command-fd 0 --expert --edit-key $fpr trust;
                done
              '''
              lock('ossrh') {
                sh 'mvn -B -f pom-plain.xml -P ossrh,eclipse-sign deploy'
              }
            }
          }
        }
      }
      /*stage('NEW VERSION') { 
        // This stage should only be uncommented when creating a new release.
        steps {
          lock('download-area') {
            sshagent (['projects-storage.eclipse.org-bot-ssh']) {
              sh 'cat "$WORKSPACE/releng/org.eclipse.epsilon.releng/new_version_tasks.sh" | ssh genie.epsilon@projects-storage.eclipse.org'
            }
          }
        }
      }*/
    }
    post {
      success {
        slackSend (channel: '#ci-notifications', botUser: true, color: '#00FF00', message: getSlackMessage())
      }
      failure {
        slackSend (channel: '#ci-notifications', botUser: true, color: '#FF0000', message: getSlackMessage())
        mail to: 'epsilon-dev@eclipse.org',
        subject: 'Epsilon Interim build failed!',
        body: "${env.BUILD_TAG}. More info at ${env.BUILD_URL}",
        charset: 'UTF-8',
        mimeType: 'text/html'
      }
    }
}
