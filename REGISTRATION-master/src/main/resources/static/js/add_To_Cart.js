var addToCart;
var fieldProductId;
var checkOut;
$(document).ready(function() {
	fieldProductId = $("#fieldProductId");
	$("#addToCart").on("click", function(evt) {
		evt.preventDefault();
		addProductToCart($(this));
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

	checkCartMiniNumber();
});

/*DELETE FROM CART*/
function deleteProductFromCart(link) {
	productId = link.attr("rowNumber");
	url = contextPath + "deleteFromCart/"+productId;
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


/*ADD PRODUCT TO CART*/
function addProductToCart(link) {
	productId = link.attr("pid");
	quantity = $("#quantity" + productId).val();
	url = contextPath + "addProductToCart/" + productId + "/" + quantity;
	if (parseInt($("#numberP").text()) - parseInt($("#numberInCart").val()) == 0) {
		alert("Số lượng sản phẩm có thể thêm đã đầy!");
	}
	else {
		$.ajax({
			type: "POST",
			url: url,
			beforeSend: function(xhr) {
				xhr.setRequestHeader(csrfHeader, csrfToken);
			}
		}).done(function(addedQuantity) {
			alert(" Sản phẩm đã được thêm vào giỏ hàng thành công");
			checkCartMiniNumber();
		}).fail(function() {
			alert("Fail");
		})
	}
}

function checkCartMiniNumber() {
	url = contextPath + "countNumberIncart";
	$.ajax({
		type: "GET",
		url: url
	}).done(function(result) {
		$("#cartMiniNumber").text(result);
		if(parseInt($("#cartMiniNumber").text()) == 0){
			document.getElementById("cartMiniNumber").style.display = "none";
		}
	});
}

/*DECREASE QUANTITY*/
function decreaseQuantity(link) {
	productId = link.attr("pid");
	qtyInput = $("#quantity" + productId);
	newQty = parseInt(qtyInput.val()) - 1;
	if (newQty > 0) {
		qtyInput.val(newQty);
		updateQuantity(productId, newQty);
	}
}

/*INCREASE QUANTITY*/
function increaseQuantity(link) {
	productId = link.attr("pid");
	qtyInput = $("#quantity" + productId);
	newQty = parseInt(qtyInput.val()) + 1;
	if (newQty > 0 && newQty <= parseInt($("#numberP").text())) {
		qtyInput.val(newQty);
		updateQuantity(productId, newQty);
	}
	else {
		alert("Số lượng sản phẩm có thể thêm vào giỏ đã đầy!");
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