/**
 * 
 */

$(document).ready(function() {
	$("#statistic").on("click", function(evt) {
		evt.preventDefault();
		watchStatistic($(this));
	});

	/*	$("#addProductForm").submit(function(evt) {
			evt.preventDefault();
			ajaxAddProduct($(this));
		});*/
});

function watchStatistic(link) {
	month = $("#month option:selected").text();
	year = $("#year option:selected").text();
	url = contextPath + "statistic/" + month + "/" + year;
	$.ajax({
		type: "GET",
		url: url
	}).done(function(result) {
		billsByMonthAndYear(month, year);
		document.getElementById("statisticResult").innerHTML = "Tổng doanh thu: " + result + " VND";
	});
}

function billsByMonthAndYear(month, year) {
	document.getElementById("billsByMonthAndYearTitle").innerHTML = "Bảng thống kê các hóa đơn tháng " + month + "/" + year;
	$("#tbody").empty();
	url = contextPath + "billsByMonthAndYear/" + month + "/" + year;
	$.ajax({
		type: "GET",
		url: url
	}).done(function(result) {
		$.each(result, function(i, bill) {
			$("#tbody").append("<tr><th><a href='' class='link-detail' rowNumber='" + bill.id + "'>Watch bill details</a>" +
				"<div class='modal' id='billDetailModal" + bill.id + "' style='width: 900px; margin: auto; padding: 0 !important; left: 50%; top: 50%; transform: translate(-50%, -50%); position: absolute; height: 700px; margin: auto;'><div class='modal-dialog'><div class='modal-content'>" +
				"<div class='modal-header'><h4 class='modal-title'>LIST BILL DETAILS</h4>" +
				"<div class='modal-body' style='width: 700px; height: 700px; margin: auto;'>" +
				"<table id='billDetailTable' style = 'width: 100%; margin: auto;'>" +
				"<thead class='thead-dark'>"+
				"<tr>"+
				"<th scope='col'>IMAGE</th>"+
				"<th scope='col'>NAME</th>"+
				"<th scope='col'>PRICE</th>"+
				"<th scope='col'>QUANTITY</th>"+
				"<th scope='col'>MONEY</th>"+
				"</tr></thead>"+
				"<tbody></tbody>"+
				"</table></div></div></div></div></div>" +
				"</th><td>" + bill.toTal + "</td><td>" + bill.howToPay + "</td><td>" + bill.buyDate + "</td><td>" + bill.user.phoneNumber + "</td><td>" + bill.user.fullName + "</td><td>" + bill.address + "</td><td>" + bill.status.statusName + "</td></tr>");
		});
	});

	$(document).on("click", ".link-detail", function(evt) {
		evt.preventDefault();
		getListBillDetail($(this));
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
			var row = "<tr><th><img src='data:image/jpeg;base64,"+billDetail.product.image+"' style='width: 100px; height: 100px;'></th><td>" + billDetail.product.name + "</td><td>" + billDetail.productPrice + "</td><td>" + billDetail.quantity + "</td><td>" + billDetail.money + "</td></tr>";
			$("#billDetailTable tbody").append(row);
		});
	});
	modal = document.getElementById("billDetailModal" + rowNumber);
	modal.style.display = "block";
}