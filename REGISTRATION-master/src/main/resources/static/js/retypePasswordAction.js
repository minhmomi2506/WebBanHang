function retypePasswordCheck() {
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
