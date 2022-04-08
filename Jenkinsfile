pipeline {
	agent any
	stages {
		stage('Clone') {
			steps {
				echo 'clone from git step'
				git 'https://github.com/minhmomi2506/WebBanHang.git'
			}
		}
		
		stage('check out'){
			steps {
				checkout scm
			}
		}
		
		stage('check java') {
        	steps {
        		sh 'java -version'
        	}
    	}
    	
    	stage('deploy') {
			steps {
				sh "cd .."
				sh "ls"
			}	
		}
	}
}