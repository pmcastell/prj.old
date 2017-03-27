<html>
   <head>
      <style>
          th, td {
            padding: 10px;
            }
         
      </style>
   </head>
   <body>
	   <table border="1">
	   <caption>Variables POST: </caption>
	   <tr><th>Variable</th><th>&nbsp;&nbsp;&nbsp;&nbsp;</th><th>Valor</th><th>Tipo</th></tr>
	   <?php
	   foreach($_POST as $i=>$val) {
	   	if (getType($val)=='array') {
	   	   echo "<tr><td>$i</td><td>==></td><td>";
	   	   print_r($val);
	   	   echo "</td><td>",getType($val),"</td></tr>";
	   	} else {
		   	echo "<tr><td>$i</td><td>==></td><td>$val</td><td>",getType($val),"</td></tr>";
	   	}
	   }
	   
	   
	   ?>
	   </table>
	   <br /><br />
      <table border="1">
      <caption>Varibles GET: </caption>
	   <tr><th>Variable</th><th>&nbsp;&nbsp;&nbsp;&nbsp;</th><th>Valor</th><th>Tipo</th></tr>
	   <?php
	   foreach($_GET as $i=>$val) {
	   	if (getType($val)=='array') {
	   	   echo "<tr><td>$i</td><td>==></td><td>";
	   	   print_r($val);
	   	   echo "</td><td>",getType($val),"</td></tr>";
	   	} else {
		   	echo "<tr><td>$i</td><td>==></td><td>$val</td><td>",getType($val),"</td></tr>";
	   	}
	   }
	   //$fecha=$_POST['fecha'];
	   //print_r($fecha);
	   ?>
	   
	   </table>
	   	   <br /><br />
      <table border="1">
      <caption>Ficheros: </caption>
	   <tr><th>Variable</th><th>&nbsp;&nbsp;&nbsp;&nbsp;</th><th>Valor</th><th>Tipo</th></tr>
	   <?php
	   foreach($_FILES as $i=>$val) {
	   	if (getType($val)=='array') {
	   	   echo "<tr><td>$i</td><td>==></td><td>";
	   	   print_r($val);
	   	   echo "</td><td>",getType($val),"</td></tr>";
	   	} else {
		   	echo "<tr><td>$i</td><td>==></td><td>$val</td><td>",getType($val),"</td></tr>";
	   	}
	   }
	   //$fecha=$_POST['fecha'];
	   //print_r($fecha);
	   ?>
	   
	   </table>
</html>
