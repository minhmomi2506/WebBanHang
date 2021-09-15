/**
 * 
 */

$(document).ready(function() {
	$(".editProductImage").on("hide.bs.modal", function() {
		hideModal();
	});
});

function showPreview(event) {
	if (event.files && event.files[0]) {
		var reader = new FileReader();
		reader.onload = function(e) {
			$(".previewProductImage").attr('src', e.target.result);
		}
		reader.readAsDataURL(event.files[0]);
	}
}

function hideModal(){
	document.getElementsByClassName("editProductImageInput").value= null;
	$(".previewProductImage").attr("src", "");
	alert("aaa");
}