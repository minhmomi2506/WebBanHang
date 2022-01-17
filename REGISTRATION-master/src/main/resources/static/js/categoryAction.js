$(document).ready(function() {
	$(".link-remove-category").on("click", function(evt) {
		evt.preventDefault();
		deleteCategory($(this));
	});
	
	getAllCategories();

	/*	$("#addCategoryForm").submit(function(evt) {
			evt.preventDefault();
			ajaxPostCategory();
		});*/

	/*	$("#aaa").on("click",function() {
			getAll();
		});*/
});

function getAllCategories(){
	url = "/getAllCategories";
	$.ajax({
		type: "GET",
		url: url
	}).done(function(result) {
		$.each(result, function(index, category) {
		/*	alert(product.price);*/
			/*$("#tbodyProductTable")
			.append("<tr><th><img src='data:image/jpeg;base64," + product.image + "' style='width: 100px; height: 100px;'></th><td>" + product.name + "</td><td>" + product.category.categoryName + "</td><td>" + product.description + "</td><td>" + product.price + "</td><td>" + product.number + "</td><td><a class = 'fas fa-trash link-remove' id = '" + product.id + "' href = ''> &nbsp; </td></tr>");*/
			$("#listCategories").append("<li class='nav-item'><a href = 'taiNghe' class = 'nav-link'>Tai nghe</a></li>")
		});
	});
}


/*INSERT CATEGORY*/
/*function ajaxPostCategory() {
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
		var categoryTable = document.getElementById("categoryTable");
		var newRow = categoryTable.insertRow(categoryTable.length),
			cell1 = newRow.insertCell(0),
			cell2 = newRow.insertCell(1);
		cell1.innerHTML = $("#addCategoryName").val();
		cell2.innerHTML = $("#deleteCategoryAction").html();
	});
}*/

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