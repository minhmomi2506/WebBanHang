/**
 * 
 */
$(document).ready(function() {
	$(".link-cancel").on("click", function(evt) {
		evt.preventDefault();
		cancelBill($(this));
	});

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
		$("#editBillStatus" + billId + " option:selected").text($("#editBillStatus" + billId + " option:selected").text());
		var rIndex, billTable = document.getElementById("billTable");
		for (var i = 0; i < billTable.rows.length; i++) {
			billTable.rows[i].onclick = function() {
				rIndex = this.rowIndex;
				billTable.rows[rIndex].cells[6].innerHTML = $("#editBillStatus" + billId + " option:selected").text();
			}
		}
	});
}

function cancelBill(link) {
	rowNumber = link.attr("rowNumber");
	url = contextPath + "cancelBill/" + rowNumber;
	result = confirm("Hủy đơn?");
	if (result) {
		$.ajax({
			type: "PUT",
			url: url,
			contentType: "application/json",
			dataType: 'json',
			beforeSend: function(xhr) {
				xhr.setRequestHeader(csrfHeader, csrfToken);
			}
		}).done(function(data) {
			alert("Đã hủy đơn");
			$("#customerBillStatus" + rowNumber).hide();
			$("#status" + rowNumber).text("Hủy đơn");
		});
	}
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