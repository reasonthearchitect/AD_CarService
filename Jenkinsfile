node {
    stage 'Checkout'
    git url: 'https://github.com/reasonthearchitect/AD_CarStore.git'

    stage 'Build'
    sh "./gradlew build"
    
    stage 'Sonar'
    sh "./gradlew sonarqube"
}