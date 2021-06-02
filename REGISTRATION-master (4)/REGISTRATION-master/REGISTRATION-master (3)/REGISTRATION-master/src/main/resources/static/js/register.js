var check = function(){
	if(document.getElementById('password').value == document.getElementById('retype').value){
		document.getElementById('message').style.color = 'green';
		document.getElementById('message').innerHTML = 'matched';
	}
	else{
		document.getElementById('message').style.color = 'red';
		document.getElementById('message').innerHTML = 'not matched';
	}
}