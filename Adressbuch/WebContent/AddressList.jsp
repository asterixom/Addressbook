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

function suche(prefix,suffix,event){
	var prefix = prefix || "";
	var suffix = suffix || "";
	var event = event || null;
	if(event!=null){
		if(event.which==13){
			return false;
		}
	}
	var suchbegriffe = document.getElementsByName('suchbegriff');
	var data="";
	$(suchbegriffe).each(function(index,element){
		var value = prefix+element.children[1].value+suffix;
		if(value=="") value="%";
		data+=encodeURIComponent(element.firstChild.value)+"="+encodeURIComponent(value)+"&";
	});
	var xmlhttp=new XMLHttpRequest();
	xmlhttp.onreadystatechange=function(){
		if(xmlhttp.readyState==4){
			document.getElementById('werte').innerHTML=xmlhttp.responseText;
		}
	}
	xmlhttp.open("POST","AddressList.jsp?"+data,true);
	xmlhttp.send();
}

function add(){
	document.getElementById('suchen').innerHTML+='<div name="suchbegriff"><select name="schluessel">\
	<option value="name">Name</option>\
	<option value="christianname">Vorname</option>\
	<option value="email">eMail</option>\
	<option value="addressform">Anrede</option>\
	<option value="phone">Telefon</option>\
	<option value="mobile">Handy</option>\
	<option value="street">Straße</option>\
	<option value="number">Hausnummer</option>\
	<option value="city">Ort</option>\
	<option value="postcode">PLZ</option>\
	<option value="country">Land</option>\
	<option value="birthday">Geburtstag</option>\
</select><input name="wert" type="text" onkeyup="suche(\'%\',\'%\',event)" autocomplete="off"/>\
<input type="button" value="-" onClick="removeDiv(this.parentNode);"></div>';
}

function removeDiv(element){
	element.parentNode.removeChild(element);
	suche();
}

</script>
</head>
<body onLoad="suche()" link="#0000FF" vlink="#0000FF" alink="#0000FF">
<div id="header"><h1>Adressbuch</h1></div>
<div id="body">
<div id="suche">
<form onSubmit="suche();return false;"><div id="suchen">
</div><input type="button" value="+" onClick="add();"><input type="submit" value="Suchen">
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