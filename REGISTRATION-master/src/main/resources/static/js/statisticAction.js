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
		$.each(result , function(i , bill) {
			$("#tbody").append("<tr><th><img src='data:image/jpeg;base64,"+bill.product.image+"' style='width: 100px; height: 100px;'></th><td>"+bill.product.name+"</td><td>"+bill.quantity+"</td><td>"+bill.product.price+"</td><td>"+bill.howToPay+"</td><td>"+bill.buyDate+"</td><td>"+bill.user.phoneNumber+"</td><td>"+bill.user.fullName+"</td><td>"+bill.address+"</td><td>"+bill.status.statusName+"</td></tr>");
		});
	});
}