var addProduct;
$(document).ready(function() {
	$(".link-remove").on("click", function(evt) {
		evt.preventDefault();
		deleteProduct($(this));
		$("#deleteProductModal").modal('hide');
	});
});

function deleteProduct(link) {
	url = link.attr("href");
	$("#deleteProductModal #delRef").attr("href", url);
	$("#deleteProductModal #delRef").on("click", function() {
		$.ajax({
			type: "POST",
			url: $("#deleteProductModal #delRef").attr("href"),
			beforeSend: function(xhr) {
				xhr.setRequestHeader(csrfHeader, csrfToken);
			}
		}).done(function() {
			rowNumber = link.attr("rowNumber");
			removeProduct(rowNumber);
		});
	});
}

function removeProduct(rowNumber) {
	rowId = "row" + rowNumber;
	$("#productTable #" + rowId).remove();
}