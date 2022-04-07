$(document).ready(function() {
	$("#editUserPassword").on("input", function() {
		retypePasswordCheck();
	});
	$("#retypePassword").on("input", function() {
		retypePasswordCheck();
	});
});

function retypePasswordCheck() {
	if ($("#editUserPassword").val().length < 4) {
		document.getElementById("retypePasswordAlert").style.display = "block";
		document.getElementById("retypePasswordAlert").innerHTML = "Mật khẩu phải dài ít nhất 4 kí tự";
		document.getElementById("retypePasswordSuccess").style.display = "none";
		document.getElementById("editUserPasswordButton").disabled = true;
	}
	else {
		if (document.getElementById("editUserPassword").value != document.getElementById("retypePassword").value) {
			document.getElementById("retypePasswordAlert").style.display = "block";
			document.getElementById("retypePasswordAlert").innerHTML = "Nhập lại mật khẩu không chính xác"
			document.getElementById("retypePasswordSuccess").style.display = "none";
			document.getElementById("editUserPasswordButton").disabled = true;
		}
		else {
			document.getElementById("retypePasswordSuccess").style.display = "block";
			document.getElementById("retypePasswordSuccess").innerHTML = "Nhập lại mật khẩu chính xác"
			document.getElementById("retypePasswordAlert").style.display = "none";
			document.getElementById("editUserPasswordButton").disabled = false;
		}
	}
}
