/**
 * 
 */

function showPreview(event) {
	productId = event.attr("productNumber");
	if(event.target.files.length > 0){
		var src = URL.createObjectURL(event.target.files[0]);
		var previewImage = $("#previewImage"+productId);
		previewImage.src = src;
		previewImage.style.display = "block";
	}
}

function ShowImagePreview(imageUploader, previewImage) {
	if (imageUploader.files && imageUploader.files[0]) {
		var reader = new FileReader();
		reader.onload = function(e) {
			$(previewImage).attr('src', e.target.result);
		}
		reader.readAsDataURL(imageUploader.files[0]);
	}
}