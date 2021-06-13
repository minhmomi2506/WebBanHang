/**
 * 
 *//**
* 
*/
/*DELETE USER*/
$(document).ready(function() {
	$(".link-remove").on("click", function(evt) {
		evt.preventDefault();
		deleteUser($(this));
	})
});

function deleteUser(link) {
	url = link.attr("href");
	$("#deleteUserModal #delRef").attr("href", url);
	$("#deleteUserModal #delRef").on("click", function() {
		$.ajax({
			type: "POST",
			url: $("#deleteUserModal #delRef").attr("href"),
			beforeSend: function(xhr) {
				xhr.setRequestHeader(csrfHeader, csrfToken);
			}
		}).done(function() {
			rowNumber = link.attr("rowNumber");
			removeUser(rowNumber);
			$("#deleteUserModal").modal('hide');
		});
	});
}

function removeUser(rowNumber) {
	rowId = "row" + rowNumber;
	$("#userTable #" + rowId).remove();
}
