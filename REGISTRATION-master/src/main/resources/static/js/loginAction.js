$(document).ready(function() {
	$("#phoneNumber").on("input", function() {
		checkLengthPhoneNumber();
	});
	
	$("#password").on("input", function() {
		checkLengthPassword();
	});
});

function checkLengthPhoneNumber() {
	if ($("#phoneNumber").val().length != 10) {
		document.getElementById("phoneNumberAlert").style.display = "block";
		document.getElementById("phoneNumberAlert").innerHTML = "SĐT phải có 10 kí tự";
		document.getElementById("signUp").disabled = true;
	}
	else {
		document.getElementById("phoneNumberAlert").style.display = "none";
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

function checkLengthPassword() {
	if ($("#password").val().length <= 4) {
		document.getElementById("passwordAlert").style.display = "block";
		document.getElementById("passwordAlert").innerHTML = "Mật khẩu phải dài ít nhất 5 kí tự";
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

