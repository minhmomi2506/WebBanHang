$(document).ready(function() {
	
	
	$("#phoneNumber").on("input", function() {
		checkDuplicatePhoneNumber();
	});

	$("#password").on("input", function() {
		checkLengthPassword();
	});
	
	$("#password").on("input", function() {
		checkLengthPassword();
	});
	
	$("#username").on("input", function() {
		checkDuplicateUsername();
	});
});

function checkLengthPassword() {
	if ($("#password").val().length < 4) {
		document.getElementById("passwordAlert").style.display = "block";
		document.getElementById("passwordAlert").innerHTML = "Mật khẩu phải dài ít nhất 4 kí tự";
		document.getElementById("signUp").disabled = true;
	}
	else {
		document.getElementById("passwordAlert").style.display = "none";
		document.getElementById("signUp").disabled = false;
		/*$.ajax({
			type: "GET",
			url: contextPath + "listUsers"
		}).done(function(result) {
			$.each(result, function(index, user) {
				alert(user.id);
			});
		});*/
	}
}

function checkDuplicateUsername(){
	$.ajax({
		type: "GET",
		url: contextPath + "listUsers"
	}).done(function(result) {
		let count = 0;
		$.each(result, function(index, user) {
			if (user.username == $("#username").val()) {
				document.getElementById("usernameAlert").style.display = "block";
				document.getElementById("usernameAlert").innerHTML = "Có người đăng kí tên này rồi";
				document.getElementById("signUp").disabled = true;
				count++;
			}
		});
		if (count == 0) {
			document.getElementById("usernameAlert").style.display = "none";
			document.getElementById("signUp").disabled = false;
		}
	});
}

function checkDuplicatePhoneNumber() {
	$.ajax({
		type: "GET",
		url: contextPath + "listUsers"
	}).done(function(result) {
		let count = 0;
		$.each(result, function(index, user) {
			if (user.phoneNumber == $("#phoneNumber").val()) {
				document.getElementById("duplicatePhoneNumberAlert").style.display = "block";
				document.getElementById("duplicatePhoneNumberAlert").innerHTML = "Có người đăng kí SĐT này rồi";
				document.getElementById("signUp").disabled = true;
				count++;
			}
		});
		if (count == 0) {
			document.getElementById("duplicatePhoneNumberAlert").style.display = "none";
			document.getElementById("signUp").disabled = false;
		}
	});
}
