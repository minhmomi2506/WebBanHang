/**
 * 
 */
$(document).ready(function() {
	$(".link-remove-category").on("click", function(evt) {
		evt.preventDefault();
		deleteCategory($(this));
	});

	$("#addCategoryForm").submit(function(evt) {
		evt.preventDefault();
		ajaxPostCategory();
	});

	/*	$("#aaa").on("click",function() {
			getAll();
		});*/
	/*getAll();*/
});

function getAll() {
	$.ajax({
		method: "GET",
		url: contextPath + "getAllCategories"
	}).done(function(responseJson) {
		/*alert(responseJson);*/
		$.each(responseJson, function(index, category) {
			alert(category.categoryName);
		});
	}).fail(function() {
		alert("Fail");
	});
}

/*INSERT CATEGORY*/
function ajaxPostCategory() {
	var formData = {
		categoryName: $("#addCategoryName").val()
	}

	$.ajax({
		type: "POST",
		contentType: "application/json",
		url: contextPath + "addCategory",
		data: JSON.stringify(formData),
		dataType: 'json',
		beforeSend: function(xhr) {
			xhr.setRequestHeader(csrfHeader, csrfToken);
		}
	}).done(function(category) {
		alert("Thêm hạng mục thành công");
		/*var categoryTable = document.getElementById("categoryTable");
		var newRow = categoryTable.insertRow(categoryTable.length),
			cell1 = newRow.insertCell(0),
			cell2 = newRow.insertCell(1);
		cell1.innerHTML = $("#addCategoryName").val();
		cell2.innerHTML = $("#deleteCategoryAction").html();*/
		$("#categoryTable").append("<tr><td>"+$("#addCategoryName").val()+"</td><td>more data</td></tr>");
	});
}

/*DELETE CATEGORY*/
function deleteCategory(link) {
	rowNumber = link.attr("rowNumber");
	result = confirm("Delete?");
	if (result) {
		$.ajax({
			type: "DELETE",
			url: contextPath + "deleteCategory/" + rowNumber,
			beforeSend: function(xhr) {
				xhr.setRequestHeader(csrfHeader, csrfToken);
			}
		}).done(function() {
			removeCategory(rowNumber);
		}).fail(function() {
			alert("Xóa thất bại");
		});
	}
}

function removeCategory(rowNumber) {
	rowId = "row" + rowNumber;
	$("#categoryTable #" + rowId).remove();
}

