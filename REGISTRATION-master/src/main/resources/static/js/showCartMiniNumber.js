/**
 * 
 */
$(document).ready(function() {
	checkCartMiniNumber();
});

function checkCartMiniNumber() {
	url = contextPath + "getAllFromCart";
	$.ajax({
		type: "GET",
		url: url
	}).done(function(result) {
		$.each(result, function(i, cartCount) {
			newQty = parseInt($("#cartMiniNumber").text()) + 1;
			$("#cartMiniNumber").text(newQty);
		});
		if (parseInt($("#cartMiniNumber").text()) == 0) {
			document.getElementById("cartMiniNumber").style.display = "none";
		}
	});
}