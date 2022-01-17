
$(document).ready(function() {

	/*	$("#addProductForm").submit(function(evt) {
				evt.preventDefault();
				ajaxAddProduct($(this));
			});*/

	$(".editP").submit(function(evt) {
		evt.preventDefault();
		ajaxEditProduct($(this));
	});

	getAllProducts();

});

function getAllProducts() {
	$("#tbodyProductTable").empty();
	url = "/getAllProducts";
	$.ajax({
		type: "GET",
		url: url
	}).done(function(result) {
		$.each(result, function(index, product) {
			/*	alert(product.price);*/
			$("#tbodyProductTable")
				.append("<tr><th><img src='data:image/jpeg;base64," + product.image
					+ "' style='width: 100px; height: 100px;'></th><td>" + product.name
					+ "</td><td>" + product.category.categoryName
					+ "</td><td>" + product.description
					+ "</td><td>" + product.price
					+ "</td><td>" + product.number
					+ "</td><td><a class = 'fas fa-trash link-remove' id = '"
					+ product.id + "' href = ''> &nbsp;<a class = 'fas fa-pencil link-edit' id = '"
					+ product.id + "' href = '' data-toggle='modal' data-target='#editProductModal" + product.id + "''></a><div class='modal' id='editProductModal"
					+ product.id + "'><div class='modal-dialog'><div class='modal-content'><div class='modal-header'><h4 class='modal-title'>SỬA THÔNG TIN SẢN PHẨM</h4></div><div class='modal-body'>" +
					"<form class='text left border border-light p-5 editP' rowNumber='"
					+ product.id + "'>" +
					"<input class = 'form-control mb-4' style = 'width: 420px;' type = 'text' id = 'editProductName" + product.id + "' value = '" + product.name + "'>" +
					"<input class = 'form-control mb-4' style = 'width: 420px;' type = 'text' id = 'editProductDescripton" + product.id + "' value = '" + product.description + "'>" +
					"</form></div></div></div></div></td></tr>");

		});
	});
	$(document).on("click", ".link-remove", function(evt) {
		evt.preventDefault();
		deleteProduct($(this));
	});
}

/*EDIT PRODUCT*/
function ajaxEditProduct(link) {
	productId = link.attr("productNumber");
	var formData = {
		name: $("#editProductName" + productId).val(),
		description: $("#editProductDescription" + productId).val(),
		price: $("#editProductPrice" + productId).val(),
		number: $("#editProductNumber" + productId).val(),
		category: {
			id: $("#editProductCategory" + productId).val(),
			categoryName: $("#editProductCategory" + productId + " option:selected").text()
		}
	}
	$.ajax({
		type: "PUT",
		contentType: "application/json",
		url: contextPath + "editProductInfo/" + productId,
		data: JSON.stringify(formData),
		dataType: 'json',
		beforeSend: function(xhr) {
			xhr.setRequestHeader(csrfHeader, csrfToken);
		}
	}).done(function() {
		alert("Sửa sản phẩm thành công");
		$("#editProductName" + productId).val($("#editProductName" + productId).val());
		$("#editProductCategory" + productId + " option:selected").text($("#editProductCategory" + productId + " option:selected").text());
		$("#editProductDescription" + productId).val($("#editProductDescription" + productId).val());
		$("#editProductPrice" + productId).val($("#editProductPrice" + productId).val());
		$("#editProductNumber" + productId).val($("#editProductNumber" + productId).val());
		var rIndex, productTable = document.getElementById("productTable");
		for (var i = 0; i < productTable.rows.length; i++) {
			productTable.rows[i].onclick = function() {
				rIndex = this.rowIndex;
				productTable.rows[rIndex].cells[1].innerHTML = $("#editProductName" + productId).val();
				productTable.rows[rIndex].cells[2].innerHTML = $("#editProductCategory" + productId + " option:selected").text();
				productTable.rows[rIndex].cells[3].innerHTML = $("#editProductDescription" + productId).val();
				productTable.rows[rIndex].cells[4].innerHTML = $("#editProductPrice" + productId).val();
				productTable.rows[rIndex].cells[5].innerHTML = $("#editProductNumber" + productId).val();
			}
		}
	});

}

/*DELETE PRODUCT*/
function deleteProduct(link) {
	id = link.attr("id");
	result = confirm("Delete?");
	if (result) {
		$.ajax({
			type: "DELETE",
			url: "/deleteProductById/" + id,
			beforeSend: function(xhr) {
				xhr.setRequestHeader(csrfHeader, csrfToken);
			}
		}).done(function() {
			getAllProducts();
		});
	}
}

function getAllFromCart() {
	number = $("#cartNumber");
	$.ajax({
		type: "GET",
		url: contextPath + "getAllFromCart"
	}).done(function(result) {
		$.each(result, function() {
			newQty = parseInt(number.text()) + 1;
			number.text(newQty);
		});
	});
}

