<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>스마트에디터</title>

<script src="https://code.jquery.com/jquery-latest.js"></script>
<script type="text/javascript" src="./resources/editor/js/HuskyEZCreator.js" charset="utf-8"></script>

</head>
<script type="text/javascript">
	$(function(){
	    //전역변수, 배열
	    var obj = [];              
	    //스마트에디터 프레임생성
	    nhn.husky.EZCreator.createInIFrame({
	        oAppRef: obj,
	        elPlaceHolder: "editor",
	        sSkinURI: "./resources/editor/SmartEditor2Skin.html",
	        htParams : {
	            // 툴바 사용 여부
	            bUseToolbar : true,            
	            // 입력창 크기 조절바 사용 여부
	            bUseVerticalResizer : true,    
	            // 모드 탭(Editor | HTML | TEXT) 사용 여부
	            bUseModeChanger : true,
	        }
	    });
	    //전송버튼 누르면
	    $("#insertBoard").click(function(){
	        //에디터의 내용을 id가 editor인 textarea에 대입
	        obj.getById["editor"].exec("UPDATE_CONTENTS_FIELD", []);
	        //폼 submit				
	        $("#insertBoardFrm").submit();
	    });
	});

</script>
<body>

	<!-- 서버로 텍스트 전송 -->
	<form action="sendText" method="post" id="insertBoardFrm" enctype="multipart/form-data">
		<textarea name="editor" id="editor" style="width: 700px; height: 400px;"></textarea>
		<input type="button" id="insertBoard" value="등록" />
	</form>

</body>
</html>
