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
			$("#tbody").append("<tr><a href='' class='link-detail' data-toggle='modal' data-target='billDetailModal"+bill.id+"' rowNumber='"+bill.id+"'>Watch bill details</a><th></th><td>"+bill.toTal+"</td><td>"+bill.howToPay+"</td><td>"+bill.buyDate+"</td><td>"+bill.user.phoneNumber+"</td><td>"+bill.user.fullName+"</td><td>"+bill.address+"</td><td>"+bill.status.statusName+"</td></tr > ");
		});
	});			
/*			+"<div class='modal billDetailModal' th:id='billDetailModal"+bill.id+"' tabindex='-1' role='dialog' style='width: 900px; margin: auto; padding: 0 !important; left: 50%; top: 50%; transform: translate(-50%, -50%); position: absolute;'>"+*/
}