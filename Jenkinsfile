node {
    stage 'Checkout'
    git url: 'https://github.com/reasonthearchitect/AD_CarStore.git'

    stage 'Build'
    sh "./gradlew clean build"
    //step([$class: 'JUnitResultArchiver', testResults: '**/build/test-results/TEST-*.xml'])

    stage 'BuildRunDocker'
    sh 'docker kill carstore'
    sh 'docker rm carstore'
    sh 'docker build -t reasonthearchitect/carstore .'
    //sh 'docker run -d --name carstore -p 8200:8200 reasonthearchitect/carstore'
}