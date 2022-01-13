$(document).ready(function() {


	$("#phoneNumber").on("input", function() {
		checkPhoneNumber();
	});

	$("#userId").on("input", function() {
		checkID();
	});

	$("#password").on("input", function() {
		checkPassword();
	});

	$("#email").on("input", function() {
		checkEmail();
	});
});

function checkEmail() {
	let count = 0;
	for (var i = 0; i < $("#email").val().length; i++) {
		if ($("#userId").val().charAt(i) == "@") {
			alert($("#userId").val().substring(i, $("#email").val().length));
		}
	}
}

function checkID() {
	let uppercaseCount = 0;
	let lowercaseCount = 0;
	let numberCount = 0;
	for (var i = 0; i < $("#userId").val().length; i++) {
		if (!isNaN($("#userId").val().charAt(i) * 1)) {
			numberCount++;
		} else {
			if ($("#userId").val().charAt(i) == $("#userId").val().charAt(i).toUpperCase()) {
				uppercaseCount++;
			}
			if ($("#userId").val().charAt(i) == $("#userId").val().charAt(i).toLowerCase()) {
				lowercaseCount++;
			}
		}
	}
	if (uppercaseCount == 0 || lowercaseCount == 0 || numberCount == 0) {
		document.getElementById("idAlert").style.display = "block";
		document.getElementById("idAlert").innerHTML = "ID phải chứa ít nhất 1 kí tự hoa, 1 kí tự thường, 1 chữ số";
		document.getElementById("signUp").disabled = true;
	}
	else {
		document.getElementById("idAlert").style.display = "none";
		document.getElementById("signUp").disabled = false;
	}
}

function checkPhoneNumber() {
	if ($("#phoneNumber").val().charAt(3) != "-" || $("#phoneNumber").val().charAt(7) != "-") {
		document.getElementById("phoneNumberAlert").style.display = "block";
		document.getElementById("phoneNumberAlert").innerHTML = "SĐT phải có dạng xxx-xxx-xxxx";
		document.getElementById("signUp").disabled = true;
	}
	else {
		document.getElementById("phoneNumberAlert").style.display = "none";
		document.getElementById("signUp").disabled = false;
	}
}

function checkPassword() {
	let uppercaseCount = 0;
	let lowercaseCount = 0;
	let numberCount = 0;
	for (var i = 0; i < $("#password").val().length; i++) {
		if (!isNaN($("#password").val().charAt(i) * 1)) {
			numberCount++;
		} else {
			if ($("#password").val().charAt(i) == $("#password").val().charAt(i).toUpperCase()) {
				uppercaseCount++;
			}
			if ($("#password").val().charAt(i) == $("#password").val().charAt(i).toLowerCase()) {
				lowercaseCount++;
			}
		}
	}
	if (uppercaseCount == 0 || lowercaseCount == 0 || numberCount == 0) {
		document.getElementById("passwordAlert").style.display = "block";
		document.getElementById("passwordAlert").innerHTML = "Mật khẩu phải chứa ít nhất 1 kí tự hoa, 1 kí tự thường, 1 chữ số";
		document.getElementById("signUp").disabled = true;
	}
	else{
		document.getElementById("passwordAlert").style.display = "none";
		document.getElementById("signUp").disabled = false;
	}
}

