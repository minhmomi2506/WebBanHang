pipeline {
	agent any
	stages {
		stage('Clone') {
			steps {
				echo 'clone from git step'
				git 'https://github.com/minhmomi2506/WebBanHang.git'
			}
		}
		
		stage('check java') {
        	steps {
        		sh 'java -version'
        	}
    	}
    	
    	stage('deploy') {
    		steps {
        		sh "cd target/ && java -jar REGISTRATION-0.0.1-SNAPSHOT.war"
    		}	
    	}
	}
}