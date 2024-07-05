pipeline {
    agent any
    environment {
        // define the environment variable
        NEW_VERSION = '1.2.0'
        SERVER_CREDENTIALS = credentials('server-credentials')
    }
    stages {
        stage('Build') {
            // expression {
            //         BRANCH_NAME == 'master' && CODE_CHANGES == true
            //     }
            steps {
                echo 'Building the application..'
                echo "building version ${NEW_VERSION}"
            }
        }
        stage('Test') {
            when {
                // expression {
                //     env.BRANCH_NAME == 'master' || BRANCH_NAME == 'network'
                // }
            }
            steps {
                echo 'Testing the application..'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying the application....'
                echo "deploying with ${SERVER_CREDENTIALS}"
                // sh "${SERVER_CREDENTIALS}"
                withCredentials([
                    usernamePassword(credentials: 'server-credentials', usernameVariable: USER, passwordVariable: PWD)
                ]){
                    sh "some script " 
                }
            }
        }
    }
}






