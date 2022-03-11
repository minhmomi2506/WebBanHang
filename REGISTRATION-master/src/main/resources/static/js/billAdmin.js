/**
 * 
 */
$(document).ready(function() {
	$(".editBill").submit(function(evt) {
		evt.preventDefault();
		ajaxEditBillStatus($(this));
	});

	$(".link-detail").on("click", function(evt) {
		evt.preventDefault();
		getListBillDetail($(this));
	});
});

function ajaxEditBillStatus(link) {
	billId = link.attr("rowNumber");
	var formData = {
		status: {
			id: $("#editBillStatus" + billId).val(),
			statusName: $("#editBillStatus" + billId + " option:selected").text()
		}
	}
	$.ajax({
		type: "PUT",
		contentType: "application/json",
		url: contextPath + "editBill/" + billId,
		data: JSON.stringify(formData),
		dataType: 'json',
		beforeSend: function(xhr) {
			xhr.setRequestHeader(csrfHeader, csrfToken);
		}
	}).done(function() {
		alert("Sửa trạng thái đơn hàng thành công");
		$("#statusAdmin" + billId).text($("#editBillStatus" + billId + " option:selected").text());
		$("#editBillStatus" + billId).text($("#editBillStatus" + billId + " option:selected").text());
	});
}

function getListBillDetail(link) {
	rowNumber = link.attr("rowNumber");
	$("#billDetailTable tbody").empty();
	$.ajax({
		type: "GET",
		url: contextPath + "listBillDetail/" + rowNumber
	}).done(function(result) {
		$.each(result, function(index, billDetail) {
			var row = "<tr><td>" + billDetail.product.name + "</td><td>" + billDetail.productPrice + "</td><td>" + billDetail.quantity + "</td><td>" + billDetail.money + "</td></tr>";
			$("#billDetailTable tbody").append(row);
		});
	});
}