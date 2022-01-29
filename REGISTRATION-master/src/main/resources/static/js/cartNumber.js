/**
 * 
 */
$(document).ready(function() {
	$(".plusButton").on("click", function(evt) {
		evt.preventDefault();
		increaseQuantity($(this));
	});

	$(".minusButton").on("click", function(evt) {
		evt.preventDefault();
		decreaseQuantity($(this));
	});

	$(".link-remove").on("click", function(evt) {
		evt.preventDefault();
		deleteProductFromCart($(this));
	});

});

/*DELETE FROM CART*/
function deleteProductFromCart(link) {
	url = link.attr("href");
	$.ajax({
		type: "DELETE",
		url: url,
		beforeSend: function(xhr) {
			xhr.setRequestHeader(csrfHeader, csrfToken);
		}
	}).done(function(newSubtotal) {
		rowNumber = link.attr("rowNumber");
		removeProduct(rowNumber);
		updateTotal();
	});
}

function removeProduct(rowNumber) {
	rowId = "row" + rowNumber;
	$("#" + rowId).remove();
}

function increaseQuantity(link) {
	productId = link.attr("pid");
	qtyInput = $("#quantity" + productId);
	myNumber = $("#number" + productId);
	newQty = parseInt(qtyInput.val()) + 1;
	if (newQty > myNumber.val()) {
		$("#noticeNumber").modal('show');
		newQty = parseInt(myNumber.val());
		qtyInput.val(newQty);
		updateQuantity(productId, newQty);
	}
	else {
		qtyInput.val(newQty);
		updateQuantity(productId, newQty);
	}
}

/*DECREASE QUANTITY*/
function decreaseQuantity(link) {
	productId = link.attr("pid");
	qtyInput = $("#quantity" + productId);
	let newQty = parseInt(qtyInput.val()) - 1;
	if (newQty > 0) {
		qtyInput.val(newQty);
		updateQuantity(productId, newQty);
	}
}

function updateQuantity(productId, quantity) {
	url = contextPath + "updateQuantity/" + productId + "/" + quantity;
	$.ajax({
		type: "POST",
		url: url,
		beforeSend: function(xhr) {
			xhr.setRequestHeader(csrfHeader, csrfToken);
		}
	}).done(function(newSubtotal) {
		updateSubtotal(newSubtotal, productId);
		updateTotal();
	});
}

function updateSubtotal(newSubtotal, productId) {
	$("#subtotal" + productId).text(newSubtotal);
}

function updateTotal() {
	total = 0.0;

	$(".productSubtotal").each(function(index, element) {
		total = total + parseFloat(element.innerHTML);
	});

	$("#totalAmount").text(total + " VND");
}