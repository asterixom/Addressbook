<%@page import="de.addressbuch.Liste"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.Map" %>
<% 
	Map<String, String[]> map = request.getParameterMap();
	if(!request.getMethod().equalsIgnoreCase("POST")){
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Adressbuch</title>
<script src="https://code.jquery.com/jquery-1.10.2.js"></script>
<script type="text/javascript">

function suche(prefix,suffix){
	var prefix = prefix || "";
	var suffix = suffix || "";
	var suchbegriffe = document.getElementsByName('suchbegriff');
	var data="";
	var value = prefix+suchbegriffe[0].lastChild.value+suffix;
	if(value=="") value="%";
	data+=encodeURIComponent(suchbegriffe[0].firstChild.value)+"="+encodeURIComponent(value)+"&";
	var xmlhttp=new XMLHttpRequest();
	xmlhttp.onreadystatechange=function(){
		if(xmlhttp.readyState==4){
			document.getElementById('werte').innerHTML=xmlhttp.responseText;
		}
	}
	xmlhttp.open("POST","AddressList.jsp?"+data,true);
	xmlhttp.send();
}



</script>
</head>
<body onLoad="suche()">
<div id="header"><h1>Adressbuch</h1></div>
<div id="body">
<div id="suche">
<form onSubmit="suche();return false;">
<div name="suchbegriff"><select name="schluessel">
	<option value="name">Name</option>
	<option value="christianname">Vorname</option>
</select><input name="wert" type="text" onkeyup="suche('%','%')"/></div>
<input type="submit" value="Suchen">
</form>
</div>
<div id="Liste">
<table><thead><tr><th>Name</th><th>Vorname</th><th>EDIT</th></tr></thead><tbody id="werte"></tbody></table>
</div>
</div>
</body>
</html>
<%
	}else{
		ArrayList<String[]> adressen = Liste.gebeListe(map);
		for(String[] adresse : adressen){
			out.println("<tr><td>"+adresse[1]+"</td><td>"+adresse[2]+"</td><td><a href='Detail.jsp?id="+adresse[0]+"'>EDIT</a></td></tr>");
		}
	}
%>