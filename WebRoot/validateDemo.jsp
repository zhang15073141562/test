<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="jquery.min.js"></script>
<script type="text/javascript">
	// 刷新图片  
	function changeImg() {  
	    var imgSrc = $("#imgObj");  
	    var src = imgSrc.attr("src");  
	    imgSrc.attr("src", changeUrl(src));  
	}  
	//为了使每次生成图片不一致，即不让浏览器读缓存，所以需要加上时间戳  
	function changeUrl(url) {  
 	    var timestamp = (new Date()).valueOf();  
	    var index = url.indexOf("?",url);
	    if (index > 0) {  
	        url = url.substring(0, index);  
	    }
	    var index1=url.indexOf("?",url);
        if(index1<0){  
		     url = url + "?timestamp=" + timestamp;  
		  } 	    
	    return url;  
	}
	
	//验证是否正确
	function validate(){		
		$.ajax({
            type: "GET",
            url: "/test/validateDemoServlet",
            data: {"code":$("#validate").val()},
            dataType: "json",
            success: function(data){
                      $("#msg").show().text(data.data.message);  
                      $("#validate").focus(function(){
                    	  $("#msg").hide();
                      });
                    }
        });
	}
	
	//当获得焦点
	
</script>
</head>
<body>
       验证码：<input type="text" id="validate" name="validate" onblur="validate()"/>
         <span style="color:red;display:none;" id="msg"></span>
       <!--字母数字混合验证  -->
       <img src="test.images" id="imgObj" name="${sessionScope.code }" onclick="changeImg()"/>
       
       <!-- 汉字验证 -->
       <!-- <img src="/test/drawImageServlet" id="imgObj" onclick="changeImg()"/> -->  
       
       <a href="javascript:void(0)" onclick="changeImg()">换一张</a>
</body>
</html>




