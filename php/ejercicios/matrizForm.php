<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns ="http://www.w3.org/1999/xhtml" xml:lang="es" lang ="es">
   <head>
      <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
      <title>Título de la web </title>
   </head>
   <body>
      <form id="matriz" action="matriz.php" method="post">
     
         
            <table border>
               <tr>
                  <td>
                     <fieldset>
			            <label>Matriz 1</label>
			            <br />
			            <input type="text" name="a00" id="a00" size="2" />
			            <input type="text" name="a01" id="a01" size="2" />
			            <br />
			            <input type="text" name="a10" id="a10" size="2" />
			            <input type="text" name="a11" id="a11" size="2" />
			            </fieldset>
		            </td>
                  <td>
                     <fieldset>
                     <label>Matriz 2</label>
                     <br />
                     <input type="text" name="b00" id="b00" size="2" />
                     <input type="text" name="b01" id="b01" size="2" />
                     <br />
                     <input type="text" name="b10" id="b10" size="2" />
                     <input type="text" name="b11" id="b11" size="2" />
                     </fieldset>
                  </td>
               </tr>
               <tr><td><label for="operacion">Selecciona op.:</label></td>
	               <td><select name="operacion">
	                           <option>Suma</option>
	                           <option>Resta</option>
	                           <option>Producto</option>
	                       </select>
	                </td>
	             </tr>
               <tr><td colspan="2"><input type="submit" value="Operar" /></td></tr>
		      </table>
    
      </form>
   </body>
</html>