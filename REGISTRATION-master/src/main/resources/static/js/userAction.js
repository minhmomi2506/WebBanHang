$(document).ready(function() {
	$("#editUserPhoneNumber").on("input", function() {
		$.ajax({
			type: "GET",
			url: contextPath + "listUsers"
		}).done(function(result) {
			let count = 0;
			$.each(result, function(index, user) {
				if (user.phoneNumber == $("#editUserPhoneNumber").val()) {
					document.getElementById("duplicateEditPhoneNumberAlert").style.display = "block";
					document.getElementById("duplicateEditPhoneNumberAlert").innerHTML = "Có người đăng kí SĐT này rồi";
					document.getElementById("editUserInfoButton").disabled = true;
					count++;
				}
			});
			if (count == 0) {
				document.getElementById("duplicateEditPhoneNumberAlert").style.display = "none";
				document.getElementById("editUserInfoButton").disabled = false;
			}
		});
	});
	
	$("#editProfileForm").submit(function(evt) {
		evt.preventDefault();
		ajaxEditUserInfo($(this));
	});

	$("#editUserInfoForm").submit(function(evt) {
		evt.preventDefault();
		ajaxEditUserInfo($(this));
	});

	$("#editUserPasswordForm").submit(function(evt) {
		evt.preventDefault();
		ajaxEditUserPassword($(this));
	});
});

function ajaxEditUserInfo(link) {
	userId = $("#editUserId").val();
	
	var formData = {
		fullName: $("#editUserFullName").val(),
		phoneNumber: $("#editUserPhoneNumber").val()
	}
	$.ajax({
		type: "PUT",
		contentType: "application/json",
		url: contextPath + "editUser/"+userId,
		data: JSON.stringify(formData),
		dataType: 'json',
		beforeSend: function(xhr) {
			xhr.setRequestHeader(csrfHeader, csrfToken);
		}
	}).done(function() {
		alert("Sửa thông tin thành công");
		$("#userFullName").text($("#editUserFullName").val());
	});
}

function ajaxEditUserPassword(link) {
	userId = $("#editUserPasswordId").val();
	var formData = {
		password: $("#editUserPassword").val()
	}
	$.ajax({
		type: "PUT",
		contentType: "application/json",
		url: contextPath + "editUserPassword/" + userId,
		data: JSON.stringify(formData),
		dataType: 'json',
		beforeSend: function(xhr) {
			xhr.setRequestHeader(csrfHeader, csrfToken);
		}
	}).done(function() {
		alert("Sửa mật khẩu thành công");
		$("#retypePasswordSuccess").hide();
		$("#editUserPassword").val("");
		$("#retypePassword").val("");
	});
}