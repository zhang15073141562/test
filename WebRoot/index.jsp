<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>左框移右框</title>
<script type="text/javascript" src="jquery.min.js"></script>
<script type="text/javascript">
	//把一个select 中的项移到另一个select中
	function moveOptions(from,to){
	  var oldname=$("#"+from+"  option:selected");
	  if(oldname.length==0){
	      return;
	  }
	  var valueOb = {};
	  $("#" + to).find("option").each(function(){
	      valueOb[String($(this).val())] = $(this);
	  });
	  
	  for( var i =0;i< oldname.length; i++){
	     if(valueOb[String($(oldname[i]).val())] == undefined){
	             $(oldname[i]).clone().appendTo($("#"+to))
	             $(oldname[i]).remove();
	     }        
	  }
	  
	}
</script>
</head>
<body>
   <SELECT id="role_list"  multiple="true" style="width:200px;height:300px;">
    <option value="1">aaa</option>
    <option value="2">bbb</option>
    <option value="3">ccc</option>
    <option value="4">ddd</option>
   </SELECT>
	<input type="button"  value="<<<<" onclick="moveOptions('role_list_to','role_list')"/>
	<input type="button"  value=">>>>" onclick="moveOptions('role_list','role_list_to')"/>
	<SELECT id="role_list_to"  multiple="true" style="width:200px;height:300px;">
	    <option value="5">eee</option>
	    <option value="6">fff</option>
    </SELECT>
</body>
</html>