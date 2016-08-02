node {
    stage 'Checkout'
    git url: 'https://github.com/reasonthearchitect/AD_CarStore.git'

    echo 'Starting the build process'

    stage 'Build'
    sh "./gradlew build"
    //step([$class: 'JUnitResultArchiver', testResults: '**/target/surefire-reports/TEST-*.xml'])
    echo 'Build process complete.'
}