<?php 

 
   //echo $_SERVER['PHP_SELF'],"<br />";
   //echo basename($_SERVER['PHP_SELF']),"<br />";
   //exit;
 //setcookie('lenguajePreferido',$_POST['lenguajePreferido'],0);
 //setcookie('idePreferido',$_POST['idePreferido'],0);
 //exit;
 include_once("funcionesAuxiliares.php");
 if (!(isset($_COOKIE['lenguajePreferido']) && isset($_COOKIE['idePreferido']) ) && $_POST) {
     setcookie('lenguajePreferido',$_POST['lenguajePreferido'],time()+86400*365);
     setcookie('idePreferido',$_POST['idePreferido'],time()+86400*365);
     echo "en 2 segundos se recargará esta página";
     recargar("?",2);
     //$_COOKIE['lenguajePreferido']=$_POST['lenguajePreferido'];
     //$_COOKIE['idePreferido']=$_POST['idePreferido'];
 }?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<!-- Fecha de Creación: 16/03/2011 -->   
<html xmlns ="http://www.w3.org/1999/xhtml" xml:lang="es" lang ="es">
   <head>
      <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
      <title>Título de la web</title>
   </head>
   <body>
     <?php
        if (isset($_COOKIE['lenguajePreferido']) && isset($_COOKIE['idePreferido'])) {
           echo "Tu lenguaje favorito es: ",$_COOKIE['lenguajePreferido'],"<br />\n";
           echo "Tu compilador favorito es: ",$_COOKIE['idePreferido'],"<br />\n";
        } else { // no nos ha visitado anteriormente
           if (!$_POST) {
              // mostrar formulario
              ?>
              <form action="?" method="post">
                 <label for="lenguajePreferido">Elige tu lenguaje favorito:</label>
                 <select name="lenguajePreferido" id="lenguajePreferido">
                    <option>C/C++</option>
                    <option>PHP</option>
                    <option>Java</option>
                    <option>Javascript</option>
                    <option>Cobol</option>
                 </select><br />
                 <label for="idePreferido">Elige tu compilador favorito:</label>
                 <select name="idePreferido" id="idePreferido">
                    <option>Eclipse</option>
                    <option>Netbeans</option>
                    <option>Visual C++</option>
                    <option>Turbo C/C++</option>
                    <option>Code Blocks</option>
                 </select><br />
                 <input type="submit" value="Enviar Preferencias" />
              </form>
              <?php 
           } else { // nos están enviado datos => establecer las cookies
              //echo "Tu lenguaje favorito es: ",$_POST['lenguajePreferido'],"<br />\n";
              //echo "Tu compilador favorito es: ",$_POST['idePreferido'],"<br />\n";
           }
        } 
     ?>
   </body>
</html>