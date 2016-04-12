<?php
   if ($_POST) {
      //Procesar los datos recibidos del formulario
      $usu=$_POST['usuario'];
      $pass=$_POST['pass'];
      if ($usu=="Admin" && $pass=="pepito") {
         $_SESSION['tipoUsuario']="Admin";
         echo "Bienvenido: ",$usu;
      } elseif ($usu=="Pepe" && md5($pass)=="c69dc337a48b4392e2ccfb49b46a043d") {
         $_SESSION['tipoUsuario']="UsuarioReg";
         echo "Bienvenido: ",$usu;
      } else {
         echo "Nombre de usuario o contraseña erróneo. Sigues como usuario no registrado.";
      }
   
      
   } else {
?>            
  <form method="post" action="?op=5">
     <label for="usuario">Nombre: </label>
     <input type="text" name="usuario" id="usuario" maxlength="10" /><br />
     <label for="pass">Contrase&ntilde;a:</label>
     <input type="password" name="pass" id="pass" maxlength="10" /><br />
     <input type="submit" value="Identificarse" />
   </form>
<?php     
   }
?>