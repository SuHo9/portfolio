function imageChoose(obj) {
	let f = obj.files[0];

	if (!f.type.match("image.*")) {
		alert("이미지를 등록해야 합니다.");
		return;
	}

	let reader = new FileReader();
	reader.onload = (e) => {
		$("#imageUploadPreview").attr("src", e.target.result);
	}
	reader.readAsDataURL(f);
}



function upload(event){

	event.preventDefault();
	let formData = new FormData();

	if(!$('#file')[0].files[0]){
		alert("사진을 등록해주세요");
		return false;
	}

	formData.append("caption",$('#caption').val());
	formData.append("file",$('#file')[0].files[0]);

	$.ajax({
			type: "post",
			url: `/api/post`,
			data: formData,
			contentType: false,
			processData: false,
			enctype: "multipart/form-data",
			dataType: "text"
		}).done(res=>{
			alert(res);
			location.href = `/main`
		}).fail(error=>{
			console.log(error);
			alert("게시글 등록에 실패하였습니다");
		});

}