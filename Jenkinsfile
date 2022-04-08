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
        	sh 'java -version'
    	}
	}
}