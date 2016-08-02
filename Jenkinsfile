node {
    stage 'Checkout'
    git url: 'https://github.com/reasonthearchitect/AD_CarStore.git'

    stage 'Build'
    //sh "./gradlew build"
    //step([$class: 'JUnitResultArchiver', testResults: '**/target/surefire-reports/TEST-*.xml'])
    echo 'Building...'

    //stage 'Integration'
    //sh "./gradlew integration"
    
    stage 'Docker'
    sh "docker ps"
}