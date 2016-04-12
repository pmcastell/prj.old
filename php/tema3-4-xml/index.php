<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="es" lang="es">
<head>
<!-- Fecha de Creación: 06/01/2012 -->
<!-- Autor: F. Criado -->
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<title>Título de la web</title>
<script type="application/javascript">
         function prueba() {
       		a={ "poblaciones": [ 
       		                        	{"poblacion": { "id": "0", "text": "Alcobendas"}},
       		                        	{"poblacion": { "id": "1", "text": "Miraflores de la Sierra"}},
       		                        	{"poblacion": { "id": "2", "text": "San Fernando de Henares"}}
       		                        ]
       			   };
       		cadena='{ "poblaciones": [{"poblacion": { "id": "0", "text": "Alcobendas"}},{"poblacion": { "id": "1", "text": "Miraflores de la Sierra"}},{"poblacion": { "id": "2", "text": "San Fernando de Henares"}}]}';
       		a3=JSON.parse(cadena);
       		a2=eval("("+cadena+")");
       		
       		b=a.poblaciones;
       		p1=a.poblaciones[0];
       		p2=a.poblaciones[1];
       		p3=a.poblaciones[2];
       		for(i=0;i<a.poblaciones.length;i++) {
       			id=a.poblaciones[i].poblacion.id;
       			pueblo=a.poblaciones[i].poblacion.text;
       		}
       	}
   
//       	prueba();
        function cambiaPosicion(imagen) {
            //imagen=document.getElementById("informatica13");
            if (typeof imagen.contador == "undefined") {
                imagen.contador=0;
            } else {
                imagen.contador++;
            }
            if (imagen.contador<30000) {
                padre=imagen.parentElement;
                while(padre!=null && padre.nodeName!="DIV") {
                    padre=padre.parentElement;
                }
                imagen.left=Math.floor(Math.random()*(padre.clientWidth-imagen.clientWidth));
                if (imagen.left<0) {
                    imagen.left=0;
                }
                imagen.top=Math.floor(Math.random()*(padre.clientHeight-imagen.clientHeight));
                if (imagen.top<0) {
                    imagen.top=0;
                }
                imagen.style.left=imagen.left+"px";
                imagen.style.top=imagen.top+"px";
                //document.location="?"; //paranoia total
                
            } else {
                imagen.style.visibility="hidden";
            }
            
        }
      </script>
</head>
<body>
	<h1>Página de prueba</h1>
	
	
	
	
     <?php
        echo "HTTP_USER_AGENT: $HTTP_USER_AGENT";
        echo "HTTP_REFERER: $HTTP_REERER";
        echo '<br/>Variables globales:<br/>',"\n";
        echo "--------------------------------<br/>";
        foreach($GLOBALS as $in => $val) {
           echo "Variable: $in => $val\n<br/>";
        }
        echo '<br/>$SERVER:\n<br/>';
        echo '----------------------------<br/>';
        foreach($_SERVER as $in => $val) {
           echo "Variable: $in => $val\n<br/>";
        }
     ?>
     <form id='prueba' action="prueba.php" method="post">
        <label for="usuario">Usuario:</label>
        <input type="text" name="usuario" id="usuario" maxlength="12"/>
        <br/>
        <label for="pass">Contraseña:</label>
        <input type="text" name="pass" id="pass" maxlength="8"/>
        <br/>
        <input type="submit" value="Entrar al sistema"/>
     </form>
        </body>

</html>
