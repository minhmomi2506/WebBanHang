/**
 * 
 */
$(document).ready(function() {
	getUserInformation();
	
	$("#phoneNumber").on("input", function() {
		checkPhoneNumber();
	});
	
	$("#userId").on("input", function() {
		checkID();
	});

	$("#password").on("input", function() {
		checkPassword();
	});
});

function checkID(){
	let uppercaseCount = 0;
	let lowercaseCount = 0;
	let numberCount = 0;
	for(var i = 0; i < $("#userId").val().length; i ++){
		if($("#userId").val().charAt(i) == $("#userId").val().charAt(i).toUpperCase()){
			uppercaseCount++;
		}
	}
	for(var i = 0; i < $("#userId").val().length; i ++){
		if($("#userId").val().charAt(i) == $("#userId").val().charAt(i).toLowerCase()){
			lowercaseCount++;
		}
	}
	for(var i = 0; i < $("#userId").val().length; i ++){
		if(parseInt($("#userId").val().charAt(i))){
			numberCount++;
		}
	}
	
	if(uppercaseCount == 0 || lowercaseCount == 0 || numberCount == 0){
		document.getElementById("idAlert").style.display = "block";
		document.getElementById("idAlert").innerHTML = "ID phải chứa ít nhất 1 kí tự hoa, 1 kí tự thường, 1 chữ số";
		document.getElementById("signUp").disabled = true;
	}
	else{
		document.getElementById("idAlert").style.display = "none";
		document.getElementById("signUp").disabled = false;
	}
}

function checkPassword() {
	let uppercaseCount = 0;
	let lowercaseCount = 0;
	let numberCount = 0;
	for(var i = 0; i < $("#password").val().length; i ++){
		if($("#password").val().charAt(i) == $("#password").val().charAt(i).toUpperCase()){
			uppercaseCount++;
		}
	}
	for(var i = 0; i < $("#password").val().length; i ++){
		if($("#password").val().charAt(i) == $("#password").val().charAt(i).toLowerCase()){
			lowercaseCount++;
		}
	}
	for(var i = 0; i < $("#password").val().length; i ++){
		if(parseInt($("#password").val().charAt(i))){
			numberCount++;
		}
	}
	
	if(uppercaseCount == 0 || lowercaseCount == 0 || numberCount == 0){
		document.getElementById("passwordAlert").style.display = "block";
		document.getElementById("passwordAlert").innerHTML = "Mật khẩu phải chứa ít nhất 1 kí tự hoa, 1 kí tự thường, 1 chữ số";
		document.getElementById("submitEditUser").disabled = true;
	}
	else{
		document.getElementById("passwordAlert").style.display = "none";
		document.getElementById("submitEditUser").disabled = false;
	}
}

function checkPhoneNumber() {
	if($("#phoneNumber").val().charAt(3) != "-" || $("#phoneNumber").val().charAt(7) != "-"){
		document.getElementById("phoneNumberAlert").style.display = "block";
		document.getElementById("phoneNumberAlert").innerHTML = "SĐT phải có dạng xxx-xxx-xxxx";
		document.getElementById("submitEditUser").disabled = true;
	}
	else{
		document.getElementById("phoneNumberAlert").style.display = "none";
		document.getElementById("submitEditUser").disabled = false;
	}
}

function getUserInformation() {
	url = "/WebSpringBoot/" + "userInformation";
	$.ajax({
		type: "GET",
		url: url
	}).done(function(result) {
		document.getElementById("userFullName").innerHTML = result.fullName;
		document.getElementById("myFullName").innerHTML = result.fullName;
		document.getElementById("userEmail").innerHTML = result.email;
		$("#fullName").val(result.fullName);
		$("#address").val(result.address);
		$("#phoneNumber").val(result.phoneNumber);
		$("#email").val(result.email);
		var image = document.createElement("img");
		image.src = "data:image/jpeg;base64," + result.image;
		document.getElementById("userImageDiv").innerHTML = "";
		document.getElementById("userImageDiv").appendChild(image);
		var editUserInfoForm = document.getElementById("editUserInfoForm");
		editUserInfoForm.id = result.id;
	});
	$("#editUserInfoForm").submit(function(evt) {
		evt.preventDefault();
		ajaxEditUserInfo($(this));
	});
}

function ajaxEditUserInfo(link) {
	userId = link.attr("id");
	var formData = {
		fullName: $("#fullName").val(),
		phoneNumber: $("#phoneNumber").val(),
		email:$("#email").val(),
		address:$("#address").val()
	}
	$.ajax({
		type: "PUT",
		contentType: "application/json",
		url: "/WebSpringBoot/" + "editUserInformation/"+userId,
		data: JSON.stringify(formData),
		dataType: 'json',
		beforeSend: function(xhr) {
			xhr.setRequestHeader(csrfHeader, csrfToken);
		}
	}).done(function() {
		alert("Sửa thông tin thành công");
		getUserInformation();
	}).fail(function(){
		alert("aaa");
	});
}