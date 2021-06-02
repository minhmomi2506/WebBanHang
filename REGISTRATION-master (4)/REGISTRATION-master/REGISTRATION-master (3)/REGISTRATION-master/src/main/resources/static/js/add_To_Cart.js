/**
 * 
 */
var addToCart;
var fieldProductId;
var checkOut;
$(document).ready(function() {
	addToCart = $("#addToCart");
	checkOut = $("#checkOut");
	fieldProductId = $("#fieldProductId");
	addToCart.click(function() {
		addProductToCart();
	});

	$(".minusButton").on("click", function(evt) {
		evt.preventDefault();
		decreaseQuantity($(this));
	});

	$(".plusButton").on("click", function(evt) {
		evt.preventDefault();
		increaseQuantity($(this))
	});

	$(".link-remove").on("click", function(evt) {
		evt.preventDefault();
		deleteProductFromCart($(this));
	});

	updateTotal();
	
	checkOut.click(function() {
		checkOut();
	});
});

/*DELETE FROM CART*/
function deleteProductFromCart(link) {
	url = link.attr("href");
	$.ajax({
		type: "POST",
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

function decreaseQuantity(link) {
	productId = link.attr("pid");
	qtyInput = $("#quantity" + productId);
	newQty = parseInt(qtyInput.val()) - 1;
	if (newQty > 0) {
		qtyInput.val(newQty);
		updateQuantity(productId, newQty);
	}
}

function increaseQuantity(link) {
	productId = link.attr("pid");
	qtyInput = $("#quantity" + productId);
	newQty = parseInt(qtyInput.val()) + 1;
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

/*ADD PRODUCT TO CART*/
function addProductToCart() {
	productId = fieldProductId.text();
	quantity = $("#quantity" + productId).val();
	url = contextPath + "addProductToCart/" + productId + "/" + quantity;
	$.ajax({
		type: "POST",
		url: url,
		beforeSend: function(xhr) {
			xhr.setRequestHeader(csrfHeader, csrfToken);
		}
	}).done(function() {
		$("#buyModal").modal('show');
	}).fail(function() {
		alert("Fail");
	})
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


