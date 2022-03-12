/**
 * 
 */
$(document).ready(function() {
	$(".link-cancel").on("click", function(evt) {
		evt.preventDefault();
		cancelBill($(this));
	});

	$(".link-received").on("click", function(evt) {
		evt.preventDefault();
		receivedBill($(this));
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

function receivedBill(link) {
	rowNumber = link.attr("rowNumber");
	url = contextPath + "receiveBill/" + rowNumber;
	result = confirm("Đã nhận được đơn?");
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
			alert("Đã nhận đơn thành công");
			$("#customerBillReceived" + rowNumber).hide();
			$("#customerBillStatus" + rowNumber).hide();
			$("#status" + rowNumber).text("Giao hàng thành công");
		});
	}
}

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
			var row = "<tr><th><img src='data:image/jpeg;base64,"+billDetail.product.image+"' style='width: 100px; height: 100px;'></th><td>" + billDetail.product.name + "</td><td>" + billDetail.productPrice + "</td><td>" + billDetail.quantity + "</td><td>" + billDetail.money + "</td></tr>";
			$("#billDetailTable tbody").append(row);
		});
	});
}