<html>
<head>
<%@ page 
	import = "java.io.*"
	import = "java.lang.*"
	import = "java.sql.*"
%>
<title>
JSP Example 2
</title>
</head>
<body>
<h1>JSP Example 3</h1>
<% 

	Connection dbconn;
	ResultSet results;
	PreparedStatement sql;
	try
	{
		Class.forName("org.gjt.mm.mysql.Driver");
		try
		{
			String telefono,nombre,direccion, fecha, cumple_santo,fechaNacimiento,
			        tabla=request.getParameter("tabla"); 
			if (tabla==null) {
			   tabla="telefonos";
			}
			boolean	doneheading = false;
			dbconn = DriverManager.getConnection("jdbc:mysql://localhost/telefonos","root","galileo");
			sql = dbconn.prepareStatement("SELECT * FROM "+tabla);
			results = sql.executeQuery();
			while(results.next())
			{
				if(! doneheading)
				{
					out.println("<table border=2>");
					doneheading = true;
				}
				if (tabla.equalsIgnoreCase("telefonos")) {
                   telefono = results.getString("telefono");
                   nombre = results.getString("nombre");
                   direccion = results.getString("direccion");
				   out.println("<tr><td>" + telefono);
				   out.println("<td>" + nombre);
				   out.println("<td>" + direccion);
				} else {
				   fecha=results.getString("fecha");
				   nombre=results.getString("nombre");
				   cumple_santo=results.getString("cumple_santo");
				   fechaNacimiento=results.getString("fechaNacimiento");
				   out.println("<tr><td>" + fecha);
				   out.println("<td>" + nombre);
				   out.println("<td>" + cumple_santo);
				   out.println("<td>" + fechaNacimiento);
				}
				   
			}
			if(doneheading)
			{
				out.println("</table>");
			}
			else
			{
				out.println("No hay registros que mostrar" );
			}
		}
		catch (SQLException s)
		{
			out.println("SQL Error<br>");
		}
	}
	catch (ClassNotFoundException err)
	{
		out.println("Class loading error");
        }
%>
</body> 
</html>