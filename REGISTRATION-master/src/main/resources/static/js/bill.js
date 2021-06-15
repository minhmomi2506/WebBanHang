var addProduct;
$(document).ready(function() {
	$(".link-remove").on("click", function(evt) {
		evt.preventDefault();
		deleteBill($(this));
		$("#deleteBillModal").modal('hide');
	});
});

function deleteBill(link) {
	url = link.attr("href");
	$("#deleteBillModal #delRef").attr("href", url);
	$("#deleteBillModal #delRef").on("click", function() {
		$.ajax({
			type: "POST",
			url: $("#deleteBillModal #delRef").attr("href"),
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
	$("#billTable #" + rowId).remove();
}