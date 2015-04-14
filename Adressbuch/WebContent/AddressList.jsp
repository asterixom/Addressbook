<%@page import="de.addressbuch.Liste"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.Map" %>
<% 
	Map<String, String[]> map = request.getParameterMap();
	if(map==null || map.isEmpty()){
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Adressbuch</title>
<script src="https://code.jquery.com/jquery-1.10.2.js"></script>
<script type="text/javascript">

function suche(){
	var suchbegriffe = document.getElementsByName('suchbegriff');
	var values[suchbegriffe.length][2];
	values[0][0]=suchbegriffe[0].firstChild.value;
	values[0][1]=suchbegriffe[0].lastChild.value;
}



</script>
</head>
<body>
<div id="header"><h1>Adressbuch</h1></div>
<div id="body">
<div id="suche">
<div name="suchbegriff"><select name="schluessel">
	<option value="name">Name</option>
	<option value="christianname">Vorname</option>
</select><input name="wert" type="text"/></div>
<input type="button" value="Suchen" onClick="suche();">
</div>
<div id="Liste">
<table><thead><tr><th>Name</th><th>Vorname</th></tr></thead><tbody id="werte"></tbody></table>
</div>
</div>
</body>
</html>
<%
	}else{
		ArrayList<String[]> adressen = Liste.gebeListe(map);
%>
<tr><td>Testkunde</td><td>Testvorname</td></tr>
<%
	}
%>