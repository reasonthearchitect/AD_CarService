node {
    stage 'Checkout'
    git url: 'https://github.com/reasonthearchitect/AD_CarStore.git'

    stage 'Build'
    sh "./gradlew clean build sonarqube"

    stage 'BuildRunDocker'
    sh 'docker kill carstore'
    sh 'docker rm carstore'
    sh 'docker build -t reasonthearchitect/carstore .'
    sh 'docker run -d --name carstore -p 8200:8200 reasonthearchitect/carstore'
}