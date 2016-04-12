<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<!-- Fecha de Creación: 21/10/2011 -->   
<html xmlns ="http://www.w3.org/1999/xhtml" xml:lang="es" lang ="es">
   <head>
      <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
      <title>dig.php</title>
   </head>
   <body>
    <table border="1" cellpadding="8" cellspacing="2">
    <tr style="vertical-align: top;"><td>
     <form method="post" action="?">
     <table border="0" style="text-align: left;">
     	<tr><th><label for="dominio">Dominio a consultar:</label></th>
         <td><input type="text" name="dominio" value="<? echo $_POST['dominio']; ?>" /></td>
		</tr>
		<tr><th><label for="server">Servidor DNS:</label></th>
		    <td><input type="text" name="server" value="<? echo $_POST['server']; ?>" /></td>
		</tr>
		<tr><th><label for="tipo">Tipo de consulta:</label></th>
		    <td><select name="tipo" id="tipo">
		    	<?php $values=array("A","AAAA","ANY","CNAME","NS","MX","PTR","SOA","TXT","LOC","RP","AXFR");
		    	      $opciones=array("A (IPv4 dir)"," AAAA (IPv6 dir)","ANY (cualquier tipo)","CNAME (Canonical NAME)",
		    	                      "NS (NameServer)","MX (Mail eXchange)","PTR (domain PoinTeR)","SOA (Start Of Authority)",
		    	                      "TXT (TeXT)","LOC (LOCation)","RP (Reponsible Person)","AXFR (zone transFeR)");
		    	      for($i=0; $i<count($values);$i++) {
		    	         echo '<option value="',$values[$i],'"';
		    	         if ($_POST['tipo']==$values[$i]) {
		    	            echo ' selected="selected"';
		    	         }
		    	         echo ">",$opciones[$i],"</option>";
		    	      }
		    	  ?>
		        </select></td>
		  </tr>
		    
		
		<tr><th colspan="2"><input type="submit" value="Consultar" /></th></tr>
		</table>
	</form>
	</td>
	<td>
	<div style="width:900px; height:550px; overflow: auto;"> 

   <? 
      if (!$_POST) {
      	exit;
      }
      	
      $dominio=$_POST['dominio'];
      $server=$_POST['server'];
      $tipo=$_POST['tipo'];
      $comando="dig @$server $dominio $tipo";
      if (strpos($comando,";")>0 || strpos($comando,"<")>0 || strpos($comando,">")>0 || strpos($comando,"&")>0) {
         echo "Error: había caracteres extraños en alguno de los campos.";
         exit;
      }
      $dig1=shell_exec($comando);
      //`dig @asystem $dominio any`;
      
      print "<h2>Respuesta de $server:</h2><pre>"; 
      print $dig1;
      print "</pre>";
      flush();
      
      /*$dig1=`dig @eden $domain any`;
      
      print "<h2>Eden (Primary)</h2><BR><pre>";
      print $dig1;
      print "</pre><P>";
      flush();
      
      $dig1=`dig @seth $domain any`;
      
      print "<h2>Seth (secondary)</h2><BR><pre>";
      print $dig1;
      print "</pre><P>";*/
      ?>
      </div>
      </td>
      </tr>
      </table>
   </body>
</html>