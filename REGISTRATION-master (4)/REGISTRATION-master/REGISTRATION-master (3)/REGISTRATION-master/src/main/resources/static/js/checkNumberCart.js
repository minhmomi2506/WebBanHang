/**
 * 
 */
/*$(document).ready(function() {
	$(".plusButton").on("click", function(evt) {
		evt.preventDefault();
		productId = $(this).attr("pid");
		qtyInput = $("#quantity" + productId);
		number = $("#number" + productId);
		if (qtyInput.val() > number.val()) {
			$("#noticeNumber").modal('show');
			newQty = parseInt(number.val());
			qtyInput.val(newQty);
			updateQuantity(productId, newQty);
		}
	});

});

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
}*/