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

pipeline {
    agent {
      kubernetes {
        label 'ui-tests'
      }
    }
    options {
      disableConcurrentBuilds()
      buildDiscarder(logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '14', numToKeepStr: ''))
    }
    environment {
      KEYRING = credentials('secret-subkeys.asc')
    }
    tools {
        maven 'apache-maven-latest'
        jdk 'adoptopenjdk-hotspot-jdk8-latest'
    }
    triggers {
        pollSCM('H/5 * * * *')
    }
    stages {
        stage('Build') {
          when { allOf { branch 'master'; changeset comparator: 'REGEXP', pattern: '(Jenkinsfile)|(pom\\.xml)|(features\\/.*)|(plugins\\/.*)|(tests\\/.*)|(releng\\/.*target.*)' } }
          steps {
            sh 'mvn -T 1C -B clean install javadoc:aggregate -P eclipse-sign'
          }
        }
        stage('Test') {
          when { allOf { branch 'master'; changeset comparator: 'REGEXP', pattern: '(Jenkinsfile)|(pom\\.xml)|(plugins\\/.*)|(tests\\/.*)' } }
          steps {
            wrap([$class: 'Xvnc', takeScreenshot: false, useXauthority: false]) {
              sh 'mvn -B -f tests/org.eclipse.epsilon.test install -P plugged'
            }
            sh 'mvn -B -f tests/org.eclipse.epsilon.test surefire:test -P ci'
          }
        }
        stage('Standalone JARs') {
          when { allOf { branch 'master'; changeset comparator: 'REGEXP', pattern: '(Jenkinsfile)|(pom\\.xml)|(plugins\\/.*)|(standalone\\/.*)' } }
          steps {
            sh 'mvn -B -f standalone install'
            sh 'cd standalone/org.eclipse.epsilon.standalone && bash build-javadoc-jar.sh'
          }
        }
        stage('Update website') {
          when { allOf { branch 'master'; changeset comparator: 'REGEXP', pattern: '(Jenkinsfile)|(pom\\.xml)|(features\\/.*)|(plugins\\/.*)|(releng\\/.*interim.*)|(standalone\\/.*)' } }
          steps {
            lock('download-area') {
              sshagent (['projects-storage.eclipse.org-bot-ssh']) {
                sh '''
                  INTERIM=/home/data/httpd/download.eclipse.org/epsilon/interim
                  INTERIMWS="$WORKSPACE/releng/org.eclipse.epsilon.updatesite.interim"
                  SITEDIR="$INTERIMWS/target"
                  if [ -d "$SITEDIR" ]; then
                    ssh genie.epsilon@projects-storage.eclipse.org rm -rf $INTERIM
                    scp -r "$SITEDIR/repository" genie.epsilon@projects-storage.eclipse.org:${INTERIM}
                    scp "$SITEDIR"/*.zip genie.epsilon@projects-storage.eclipse.org:${INTERIM}/site.zip
                  fi
                  if [ -e "$INTERIMWS/index.html" ]; then
                    scp "$INTERIMWS/index.html" genie.epsilon@projects-storage.eclipse.org:${INTERIM}/index.html
                  fi
                  JARSDIR="$WORKSPACE/standalone/org.eclipse.epsilon.standalone/target"
                  if [ -d "$JARSDIR" ]; then
                    ssh genie.epsilon@projects-storage.eclipse.org "rm -rf $INTERIM/jars; mkdir -p $INTERIM/jars"
                    scp "$JARSDIR"/epsilon-* genie.epsilon@projects-storage.eclipse.org:${INTERIM}/jars
                  fi
                  JAVADOCDIR="$WORKSPACE/target/site/apidocs"
                  if [ -d "$JAVADOCDIR" ]; then
                    ssh genie.epsilon@projects-storage.eclipse.org rm -rf ${INTERIM}/javadoc
                    scp -r "$JAVADOCDIR" genie.epsilon@projects-storage.eclipse.org:${INTERIM}/javadoc
                  fi
                '''
              }
            }
          }
        }
        stage('Deploy to OSSRH') {
          when { allOf { branch 'master'; changeset comparator: 'REGEXP', pattern: '(features\\/.*)|(plugins\\/.*)|(standalone\\/.*)' } }
          steps {
            sh '''
              gpg --batch --import "${KEYRING}"
              for fpr in $(gpg --list-keys --with-colons  | awk -F: '/fpr:/ {print $10}' | sort -u);
              do
                echo -e "5\ny\n" |  gpg --batch --command-fd 0 --expert --edit-key $fpr trust;
              done
            '''
            lock('ossrh') {
              sh 'mvn -B -f standalone/org.eclipse.epsilon.standalone -P ossrh org.eclipse.epsilon:eutils-maven-plugin:deploy'
            }
          }
        }
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
