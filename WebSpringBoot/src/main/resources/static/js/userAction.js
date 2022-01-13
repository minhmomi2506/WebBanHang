/**
 * 
 */
$(document).ready(function() {
	getListUsers();

});

function getListUsers() {
	$("#tbodyUserListTable").empty();
	url = "/WebSpringBoot/" + "listUsers";
	$.ajax({
		type: "GET",
		url: url
	}).done(function(result) {
		$.each(result, function(index, user) {
			$("#tbodyUserListTable").append("<tr><th><img src='data:image/jpeg;base64," + user.image + "' style='width: 100px; height: 100px;'></th><td>" + user.userId + "</td><td>" + user.fullName + "</td><td>" + user.email + "</td><td>" + user.phoneNumber + "</td><td>" + user.address + "</td><td><a class = 'fas fa-trash link-remove' id = '" + user.id + "' href = ''></a><a class = 'fas fa-pencil link-edit' rowNumber = '" + user.id + "' href = ''></a><div class='modal' id='editUserModal"+user.id+"'><div class='modal-dialog'><div class='modal-content'><div class='modal-header'><h4 class='modal-title'>SỬA THÔNG TIN SẢN PHẨM</h4></div><div class='modal-body'><form class='text-center border border-light p-5 editP' rowNumber='"+user.id+"'>"+
			
			"<input type='text' id = 'editUserId"+user.id+"' name='id' class='form-control mb-4' value='"+user.id+"' hidden = 'hidden'>"+
			
			"<input type='text' id = 'editUserFullName"+user.id+"' name='fullName' class='form-control mb-4' value='"+user.fullName+"'>"+
			
			"<input type='text' id = 'editUserEmail"+user.id+"' name='email' class='form-control mb-4' value='"+user.email+"'>"+
			"<input type='text' id = 'editUserPhoneNumber"+user.id+"' name='phoneNumber' class='form-control mb-4' value='"+user.phoneNumber+"'>"+
			"<input type='text' id = 'editUserAddress"+user.id+"' name='address' class='form-control mb-4' value='"+user.address+"'><button type = 'submit'>Edit</button></form></div></div></div></div></td></tr>");
		});
	});
	$(document).on("click", ".link-remove", function(evt) {
		evt.preventDefault();
		deleteUser($(this));
	});
	
	$(document).on("click", ".editP", function(evt) {
		evt.preventDefault();
		ajaxEditProduct($(this));
	});
	
	$(document).on("click", ".link-edit", function(evt) {
		evt.preventDefault();
		editUser($(this));
	});
}

function editUser(link){
	rowNumber = link.attr("rowNumber");
	modal = document.getElementById("editUserModal"+rowNumber);
	modal.style.display = "block";
	
}

function ajaxEditProduct(link){
	id = link.attr("rowNumber");
	fullName = $("#editUserFullName"+id).val();
	alert(fullName);
}

function deleteUser(link) {
	id = link.attr("id");
	result = confirm("Delete?");
	if (result) {
		$.ajax({
			type: "DELETE",
			url: "/WebSpringBoot/" + "deleteById/" + id,
			beforeSend: function(xhr) {
				xhr.setRequestHeader(csrfHeader, csrfToken);
			}
		}).done(function() {
			getListUsers();
		}).fail(function() {
			alert("Xóa thất bại");
		});
	}
}

