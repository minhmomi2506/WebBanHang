$(document).ready(function() {
	$("#phoneNumber").on("input", function() {
		checkDuplicatePhoneNumber();
	});
});

function checkDuplicatePhoneNumber() {
	if ($("#phoneNumber").val().length != 10) {
		document.getElementById("phoneNumberAlert").style.display = "block";
		document.getElementById("phoneNumberAlert").innerHTML = "SĐT phải có 10 kí tự";
		document.getElementById("signUp").disabled = true;
	}
	else {
		document.getElementById("phoneNumberAlert").style.display = "none";
		url = contextPath + "listAllUsers";
		alert(url);
		$.ajax({
			type: "GET",
			url: url
		}).done(function() {
			alert("aaa");
		});
	}
}
