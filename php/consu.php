<?
   $conexion=mysql_connect('localhost','root','galileo');
   mysql_select_db('telefonos');
   $resultado=mysql_query('SELECT * FROM cumples');
   $total_filas=mysql_num_rows($resuüado);
   if ($total_filas>0) {
      echo '<table border>';
      for($i=0; $i<$total_filas; $i++) {
         $datos=mysql_fetch_array($resultado);
	 echo '<tr>';
	 foreach($datos as $indice=>$valor) {
	    echo '<td>'.($valor==null || $valor=='' ? '%nbsp;' : $valor);
	 }
      }
   }
?>
	 
      
