/**
 * 
 */
$(document).ready(function() {

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
		phoneNumber: $("#editUserPhoneNumber").val(),
		address: $("#editUserAddress").val()
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