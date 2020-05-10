<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="http://code.jquery.com/jquery-latest.js"></script>
<script>
	var fileIndex = 0; 
	var totalFileSize = 0;
	var fileList = new Array();
	var fileSizeList = new Array();
	var uploadSize = 50; //5mb
	var fileMax = 500;

	$(function (){
        // 파일 드롭 다운
        fileDropDown();
    });
	//https://htmlcolorcodes.com/color-chart/
	function fileDropDown(){
		var dropZone = $("#dropZone");
		
		dropZone.on('dragneter',function(e){
			e.stopPropagation();
			e.preventDefault();
			dropZone.css('background-color','#EAFAF1') //녹색인듯 녹색아닌 녹색
		});
		dropZone.on('dragleave',function(e){
			e.stopPropagation();
			e.preventDefault();
			dropZone.css('background-color','#F8F9F9 ') //회색인듯 회색아닌 회색
		});
		dropZone.on('dragover',function(e){
			e.stopPropagation();
			e.preventDefault();
			dropZone.css('background-color','#F7DC6F') //노랑인듯 노랑아닌 노랑
		});
		dropZone.on('drop',function(e){
			e.stopPropagation();
			e.preventDefault();
			dropZone.css('background-color','#EBDEF0') //보라인듯 보라아닌 보라
			
			var files = e.originalEvent.dataTransfer.files;
			if(files != null){
				if(files.length <1){
					alert("사진을 올려주세요");
					return;
				}
				selectFile(files)
			}else{
				alert("등록 오류입니다");
			}
			
			
		});
	}

	function selectFile(files){
		if(files != null){
			for(var i = 0; i < files.length; i++){
				var fileName = files[i].name;
				var fileNameArr = fileName.split("\.");
				var jpg = fileNameArr[fileNameArr.length - 1];
				var fileSize = files[i].size /1024;
				
				if(fileSize > uploadSize){
					alert("파일이 너무 큽니다.");	
					break;
				}else{
					totalFileSize += fileSize;
					fileList[fileIndex] = files[i];
					fileSizeList[fileIndex] = fileSize;
					addFileList(fileIndex, fileName,fileSize);
					fileIndex ++;
				}
			}
		}else{
			("등록 중 오류가 발생하였습니다.");
		}
	}

	function addFileList(fileindex,fileName,fileSize){
		var html = "";
			html +="<tr id ='fileTr_"+fileindex+"'>";
			html +="<td class = 'left'>";
			html +=fileName + ": " + fileSize + "MB" + "<a href='#' onclick='deleteFile(" + fileindex + "); return false;'>삭제</a>";
			html +="</td>"
			html +="</tr>"
			$('#fileTable').append(html);
	}
	
	function deleteFile(fileindex){
		totalFileSize -= fileSizeList[fileindex];
		delete fileList[fileindex];
		delete fileSizeList[fileindex];
		$("#fileTr_" + fileindex).remove();
	}
	
	function uploadFile(){
		var uploadFileList = Object.keys(fileList);
		
		if(uploadFileList.length == 0){
			alert("등록할 상품이 없습니다.");
			return;
		}else if(totalFileSize > fileMax){
			alert("등록 가능한 상품 용량을 초과하였습니다.");
			return;
		}
		
		if(confirm("해당 상품을 등록하시겠습니까?")){
			var form = $('#uploadForm')[0];
			var formData = new FormData(form);
			
			for(var i = 0; i < uploadFileList.length; i++){
                formData.append('files', fileList[uploadFileList[i]]);
            }
            
			
			$.ajax({ //json정보가 들어있는 formData전송 방식
				url:'/upload',
				data:formData,
				type:'POST',
				enctype:"multipart/form-data",
				dataType : 'json',
				contentType:false, //json보내는 것 아님
				processData:false, //json보내는 것 아님
				cache: false,
				success:function(result){
					if(result){
						alert('성공적으로 등록하였습니다.' + result);
						location.replace("/");
					}else{
						alert('상품을 등록하지 못했습니다.');
						location.reload();
					}
				}
			});
			
		}
	}
	

</script>



<title>상품등록</title>
</head>
<body>

<form id="uploadForm" action = "/upload" enctype="multipart/form-data" method="post">
        <table class="table" width="50%" border="1px">
            <tbody id="fileTable">
            <tr>
                    <td id="product_name">
                    <label>상품명: </label>
                        <input type = "text" id = "product_name" name= "product_name">
                    </td>
            </tr>
            <tr>
                    <td id="product_price">
                    <label>상품가격: </label>
                        <input type = "text" id = "product_price" name= "product_price">
                    </td>
            </tr>
            <tr>
                    <td id="product_category">
                    <label>상품카테고리: </label>
                        <input type = "text" id = "product_ category" name= "product_category">
                    </td>
            </tr>
                <tr>
                    <td id="dropZone">
                        이곳에 상품을 가져다 놓으세요.
                    </td>
                </tr>
            </tbody>
        </table>
        <button type="button" onclick="uploadFile();">파일 업로드</button>
</form>
</body>
</html>