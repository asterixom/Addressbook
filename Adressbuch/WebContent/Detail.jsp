<%@page import="de.addressbuch.Address"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Detail</title>
<script src="https://code.jquery.com/jquery-1.10.2.js"></script>
<script type="text/javascript">

function toText(){
	$(document.getElementsByClassName('field')).each(function(id,element){
		element.type="hidden";
	});
	document.getElementById('button').value='Bearbeiten';
}

</script>
</head>
<body vlink="#0000FF" alink="#0000FF">
<%
	request.setCharacterEncoding("UTF-8");
	String id = request.getParameter("id");
	try{
		int idint = Integer.parseInt(id);
		Address address = Address.read(idint);
		String[][] field = {
				{"Addressform","Anrede"},
				{"Firstname","Vorname"},
				{"Name","Name"},
				{"Street","Straße"},
				//{"Number","Hausnummer"},
				{"Postcode","PLZ"},
				{"City","Ort"},
				{"Country","Land"},
				{"Email","eMail"},
				{"Phone","Telefon"},
				{"Mobile","Handy"},
				//{"Birthday","Geburtstag"}
				};
		boolean diff=false;
		for(int i=0;i<field.length;i++){
			String value = request.getParameter(field[i][0]);
			if(value!=null){
				try{
					Address.class.getMethod("set"+field[i][0], String.class).invoke(address, value);
					diff=true;
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		if(diff) address.save();
%>
<form method="POST" enctype="application/x-www-form-urlencoded">
<input type="hidden" name="id" value="<%=id%>">
<table>
<% for(String[] werte : field){ %>
<tr><td><%=werte[1]%></td><td><input type="text" class="field" name="<%=werte[0]%>" value="<%= Address.class.getMethod("get"+werte[0]).invoke(address) %>"></td></tr>
<% } %>
</table>
<input type="reset"/><input type="submit"/>
</form>
<br><a href="AddressList.jsp">Schließen</a>
<%
	}catch(NumberFormatException e){
		response.sendRedirect("AddressList.jsp");
	}
%>
</body>
</html>