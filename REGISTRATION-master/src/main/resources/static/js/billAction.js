/**
 * 
 */$(document).ready(function() {
	 $(".link-edit").on("click", function(evt) {
		 evt.preventDefault();
		 editBill($(this));
	 });
 });

function editBill(link) {
	billNumber = link.attr("billNumber");
	count = link.attr("count");
	status = $("#status"+billNumber+count).text();
	url = contextPath + "editBill/" + billNumber+"/"+status;
	$("#editBillButton"+billNumber).on("click", function() {
		alert(url);
		$.ajax({
			type: "POST",
			url: url,
			beforeSend: function(xhr) {
				xhr.setRequestHeader(csrfHeader, csrfToken);
			}
		}).done(function(data) {
			alert("Cập nhật thành công!");
			alert(data);
		}).fail(function() {
			alert("Fail");
		});
	});
}