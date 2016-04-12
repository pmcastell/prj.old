<?php
   include_once("funcionesAuxiliares.php");
   if (isset($_POST['dni']) && isset($_POST['clave'])) {
      abreBase('videoclub','videoclub','videoclub2008');
      $consulta="SELECT * FROM socios WHERE dni='".$_POST['dni']."'";
      $resultado=mysql_query($consulta);
      if ($resultado) {
         $datos=mysql_fetch_array($resultado);
         if (mysql_num_rows($resultado)==1 && $_POST['clave']==$datos['clave']) {
            session_start();
            $_SESSION['dni']=$_POST['dni'];
            if ($datos['nombre']=="admin") {
               $_SESSION['tipoUsuario']="admin";
            } else {
               $_SESSION['tipoUsuario']="socio";
            }
            echo "<SCRIPT> document.location='index.php'; </SCRIPT>";
         } else {
            error("<br><b>Error: Usuario inexistente o contraseña errónea.<b><br>");
            echo '<form name="reenviodni" action="index.php?pag=ident" method="POST">',"\n";
            echo '<input name="dni" type="hidden" value="',$_POST['dni'],'">',"\n";
            echo '<a id="volverIntentar" href="javascript:document.reenviodni.submit();">Volver a Intentar</a>',"\n";
            echo '</form>',"\n";
            echo '<script> document.getElementById("volverIntentar").focus();</script>';
            //echo '<a href="javascript:history.back();">Volver a Intentar</a>';
         }                                      
      } else {
         echo "<h2>En estos momentos no podemos atenderle. Por favor vuelva a intentarlo pasados unos minutos.";
         echo "<br>Gracias</h2>";
      }
   } else {
      echo '
     <script language="javascript">';
     escribeFuncionValidarDni();
     echo '
         function validar() {
            return validarDni(document.ident.dni);
         }
     </script>';
?>
            
     <br><br><br><br>
     <form name="ident" action="index.php?pag=ident" method="post" onSubmit="return validar();">
     <table width="50%" align="center" border>
        <tr>
          <th colspan=2>
            Introduzca su D.N.I. y contraseña
        <tr>
          <td>
            D.N.I.:
          <td>
            <input type="text" name="dni" size="9"
            <?php if (isset($_POST['dni'])) { 
                  echo 'value="',$_POST['dni'],'"'; 
            } ?>
            >
        <tr>
          <td>
            Contraseña:
          <td>
            <input type="password" name="clave" size="32">
        <tr>
          <td colspan=2 align=center>
            <input type="submit" value="Enviar">&nbsp;&nbsp;<input type="reset" value="Borrar Datos">
     </table>
     <SCRIPT>
        <?php if (!isset($_POST['dni'])) { ?>
       document.ident.dni.focus();
       <?php } else { ?>
       document.ident.clave.focus();
       <?php } ?>
    </SCRIPT>

  </body>
</html>

<?php 
   } 
?>
