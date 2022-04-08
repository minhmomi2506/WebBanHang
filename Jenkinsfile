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
    			sh "kill \$(lsof -t -i:8080) > /dev/null 2> /dev/null || : "
        		sh "cd ./target/ && java -jar REGISTRATION-0.0.1-SNAPSHOT.war --spring.profiles.active=prod"
    		}	
    	}
	}
}