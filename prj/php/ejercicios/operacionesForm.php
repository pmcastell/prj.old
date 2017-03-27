<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns ="http://www.w3.org/1999/xhtml" xml:lang="es" lang ="es">
   <head>
      <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
      <title>Suma de dos números </title>
   </head>
   <body>
 
      <form method="post" action="operaciones.php" id="operaciones">
         <fieldset>
            <label for="n1">Número 1:</label>
            <input type="text" name="n1" id="n1" size="10" />
            <label for="n2">Número 2:</label>
            <input type="text" name="n2" id="n2" size="10" />
            <label for="operacion">Seleccion op:</label>
            <select name="operacion" id="operacion">
               <option>Suma</option>
               <option>Resta</option>
               <option>División</option>
               <option>Producto</option>
            </select>
            <input type="submit" value="Operar" />
         </fieldset>
      </form>

   </body>
</html>
