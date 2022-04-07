
$(document).ready(function() {
	$(".link-remove").on("click", function(evt) {
		evt.preventDefault();
		deleteProduct($(this));
	});

	$("#addCategoryForm").submit(function(evt) {
		evt.preventDefault();
		ajaxPostCategory();
	});
});

/*function ajaxPostCategory() {
	var formData = {
		category_name: $("#addCategoryName").val()
	}

	$.ajax({
		type: "POST",
		contentType: "application/json",
		url : contextPath + "addCategory",
		data: JSON.stringify(formData),
		dataType : 'json',
		beforeSend: function(xhr) {
			xhr.setRequestHeader(csrfHeader, csrfToken);
		}
	}).done(function() {
		alert("aaaa");
	});
}*/

function deleteProduct(link) {
	url = link.attr("href");
	$("#deleteProductModal #delRef").attr("href", url);
	$("#deleteProductModal #delRef").on("click", function() {
		$.ajax({
			type: "DELETE",
			url: $("#deleteProductModal #delRef").attr("href"),
			beforeSend: function(xhr) {
				xhr.setRequestHeader(csrfHeader, csrfToken);
			}
		}).done(function() {
			rowNumber = link.attr("rowNumber");
			removeProduct(rowNumber);
		});
	});
}

function removeProduct(rowNumber) {
	rowId = "row" + rowNumber;
	$("#productTable #" + rowId).remove();
}

