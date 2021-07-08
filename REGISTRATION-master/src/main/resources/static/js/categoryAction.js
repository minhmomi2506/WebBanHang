/**
 * 
 */
$(document).ready(function() {
	$(".link-remove").on("click", function(evt) {
		evt.preventDefault();
		deleteProduct($(this));
	});
});

function deleteProduct(link) {
	url = link.attr("href");
	$("#deleteCategoryModal #delRef").attr("href", url);
	$("#deleteCategoryModal #delRef").on("click", function() {
		$.ajax({
			type: "POST",
			url: $("#deleteCategoryModal #delRef").attr("href"),
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
	$("#categoryTable #" + rowId).remove();
}

