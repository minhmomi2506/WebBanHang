/**
 * 
 */

function showPreview(event) {
	if (event.files && event.files[0]) {
		var reader = new FileReader();
		reader.onload = function(e) {
			$(".previewProductImage").attr('src', e.target.result);
		}
		reader.readAsDataURL(event.files[0]);
	}
}