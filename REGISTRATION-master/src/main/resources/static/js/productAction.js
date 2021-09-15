
$(document).ready(function() {
	$(".link-remove").on("click", function(evt) {
		evt.preventDefault();
		deleteProduct($(this));
	});

	/*	$("#addProductForm").submit(function(evt) {
				evt.preventDefault();
				ajaxAddProduct($(this));
			});*/

	$(".editP").submit(function(evt) {
		evt.preventDefault();
		ajaxEditProduct($(this));
	});
});


/*ADD PRODUCT*/
/*function ajaxAddProduct(link) {
	var formData = {
		name: $("#productName").val(),
		description: $("#productDescription").val(),
		price: $("#productPrice").val(),
		number: $("#productNumber").val(),
		image : document.getElementById("proImage").files[0],
		category: {
			id: $("#productCategory").val(),
			categoryName: $("#productCategory option:selected").text()
		}
	}
	
	$.ajax({
		type: "POST",
		contentType: "application/json",
		url: contextPath + "addP",
		beforeSend: function(xhr) {
			xhr.setRequestHeader(csrfHeader, csrfToken);
		}
	}).done(function() {
		alert("Thêm sản phẩm thành công");
	});
/*	alert("abc");*/


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
	rowNumber = link.attr("rowNumber");
	result = confirm("Delete?");
	if (result) {
		$.ajax({
			type: "DELETE",
			url: contextPath + "deleteProductById/" + rowNumber,
			beforeSend: function(xhr) {
				xhr.setRequestHeader(csrfHeader, csrfToken);
			}
		}).done(function() {
			rowNumber = link.attr("rowNumber");
			removeProduct(rowNumber);
		});
	}
}

function removeProduct(rowNumber) {
	rowId = "row" + rowNumber;
	$("#productTable #" + rowId).remove();
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

