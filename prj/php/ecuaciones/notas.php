<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns ="http://www.w3.org/1999/xhtml" xml:lang="es" lang ="es">
   <head>
      <!-- Fecha de Creación: 14/03/2012 -->   
      <!-- Autor: F. Criado -->
      <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
      <title>Título de la web</title>
    </head>
   <body>
     <?php
        $nota=$_POST['nota'];
        if ($nota>10 || $nota<0) {
           echo 'La nota es incorrecta';
        } else {
           if ($nota>=9) {
              echo 'Sobresaliente<br/>';
           } elseif ($nota>=7) {
              echo 'Notable';
           } elseif ($nota>=6) {
              echo 'Bien';
           } elseif ($nota>=5) {
              echo 'Suficiente';
           } else {
              echo 'Suspenso';
           }
        }
        
     ?>
   </body>
</html>