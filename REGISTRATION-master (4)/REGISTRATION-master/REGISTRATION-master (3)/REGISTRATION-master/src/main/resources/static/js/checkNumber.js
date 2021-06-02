/**
 * 
 */
$(document).ready(function() {
	$(".plusButton").on("click", function() {
		numberP = $("#numberP").val();
		qtyInput = $("#quantity" + productId);
		if (qtyInput.val() > numberP) {
			$("#noticeNumber").modal('show');
			newQty = parseInt(qtyInput.val()) - 1;
			if (newQty > 0) {
				qtyInput.val(newQty);
			}
		}
	});

});