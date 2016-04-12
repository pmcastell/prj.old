<%@ page language="java" import="java.sql.*"%>
<html>
<head><title>Read from mySQL Database</title>
</head>
<body>

<p align="center"><b>Listado de Pel&iacute;culas</b><br>&nbsp;</p>

<div align="center" width="85%">
<center>
<table border="1" borderColor="#ffe9bf" cellPadding="0" cellSpacing="0" width="658" height="63">
<tbody>
<th bgColor="#008080" width="47" align="center" height="19"><font color="#ffffff"><b>C&oacute;digo</b></font></th>
<th bgColor="#008080" width="107" height="19"><font color="#ffffff"><b>T&iacute;tulo</b></font></th>
<th bgColor="#008080" width="224" height="19"><font color="#ffffff"><b>Sinopsis</b></font></th>
<th bgColor="#008080" width="270" height="19"><font color="#ffffff"><b>Director</b></font></th>

<%
String DRIVER = "org.gjt.mm.mysql.Driver";
Class.forName(DRIVER).newInstance();


Connection con=null;
ResultSet rst=null;
Statement stmt=null;


try{
String url="jdbc:mysql://localhost/videoclub?user=videoclub&password=videoclub2011";

int i=1;
con=DriverManager.getConnection(url);
stmt=con.createStatement();
rst=stmt.executeQuery("select * from PELICULAS ");
while(rst.next()){

if (i==(i/2)*2){
%>
<tr>
<td bgColor="#ffff98" vAlign="top" width="47" align="center" height="19"><%=i%>.</td>
<td bgColor="#ffff98" vAlign="top" width="107" height="19"><%=rst.getString(2)%></td>
<td bgColor="#ffff98" vAlign="top" width="224" height="19"><a href="<%=rst.getString(3)%>"><%=rst.getString(3)%></a>&nbsp;</td>
<td bgColor="#ffff98" vAlign="top" width="270" height="19"><%=rst.getString(4)%></td>
</tr>
<%
}else{
%>
<tr>
<td bgColor="#ffcc68" vAlign="top" width="47" align="center" height="19"><%=i%>.</td>
<td bgColor="#ffcc68" vAlign="top" width="107" height="19"><%=rst.getString(2)%></td>
<td bgColor="#ffcc68" vAlign="top" width="224" height="19"><a href="<%=rst.getString(3)%>"><%=rst.getString(3)%></a>&nbsp;</td>
<td bgColor="#ffcc68" vAlign="top" width="270" height="19"><%=rst.getString(4)%></td>
</tr>
<% }

i++;
}
rst.close();
stmt.close();
con.close();
}catch(Exception e){
System.out.println(e.getMessage());
}
%>

</tbody>
</table>
</center>
</div>


</body>
</html>
