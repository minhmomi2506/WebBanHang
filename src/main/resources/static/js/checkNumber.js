/**
 * 
 */
$(document).ready(function() {
	

	$(".plusButton").on("click", function() {
		numberP = $("#numberP").val();
		qtyInput = $("#quantity" + productId);
		if (qtyInput.val() > parseInt($("#numberP").val() - $("#numberInCart").val())) {
			$("#noticeNumber").modal('show');
			newQty = parseInt($("#numberP").val() - $("#numberInCart").val());
			if (newQty > 0) {
				qtyInput.val(newQty);
			}
		}
	});

});