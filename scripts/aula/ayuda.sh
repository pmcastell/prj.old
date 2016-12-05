#!/bin/bash

echo -e "
-------------------------------------------------------------------------------------
$(echoColor Orange Estos son los comandos \(scripts\) disponibles:)
$(echoColor Yellow sudo bloquear) <on-off> [lista-máquinas-a-bloquear] ---> bloquea/desbloquea acceso a youtube y la lista de máquinas a bloquear
   ejemplo: bloquear on www.marca.com www.as.com ---> (bloquea youtube, marca y as)
            bloquear off  ---> desbloquea todos los sitios que estuvieran bloqueados
$(echoColor Yellow h) <texto-a-hablar> ---> habla con voz masculina
$(echoColor Yellow hf) <texto-a-hablar> ---> habla con voz femenina
$(echoColor Yellow hs) <texto-a-hablar> ---> hablan los dos a la vez
$(echoColor Yellow sudo encenderAula) [equipos] ---> enciende el aula o los equipos que se le digan
   ejemplo: encenderAula  ---> enciende todos los equipos del aula (casi todos)
            encenderAula 101 102 103  ---> enciende los equipos pc101, pc102 y pc103
$(echoColor Yellow sudo apagarAula)  ---> apaga todos los equipos del aula
$(echoColor Yellow pantalla)  ---> nos conecta por vnc al equipo que le digamos
   ejemplo: pantalla 101  ---> nos conecta con el pc101 (por si falla el epoptes o el equipo no sale en la pantalla del epoptes)
$(echoColor Yellow sudo srvOn)  ---> enciende remotamente un servidor (los que tienen actividado el wake on lan)
   ejemplo: srvOn ALL ---> enciende todos los servidores aula1srv, aula2srv, tecnologia, Salaprofes2, biblioSrv1
            srvOn aula2srv   ---> enciende el servidor del aula 2
$(echoColor Yellow sudo activarTor) <on-off> ---> hace que el squid salga a través de la red tor (para ver sitios bloqueados por la consellería)
$(echoColor Yellow sudo vaciarArp) ---> vacía la cache arp
$(echoColor Yellow cerrarSesion)  ---> cierra sesión de un usuario en un pc
   ejemplo: cerrarSesion 101 pepito  ---> cierra la sesión de pepito en pc101 (no hace falta se puede hacer con epoptes)
$(echoColor Yellow imprime)  ---> manda directamente ficheros a la fotocopiadora de la sala de profesores SÓLO FUNCIONA CON PDFS
   ejemplo: imprime *.pdf /tmp/*.pdf  ---> imprime todos los ficheros pdf de la carpeta actual y de la /tmp
$(echoColor Yellow sudo enrutamiento) <on-off>  ---> activa/desactiva el enrutamiento del aula
$(echoColor Yellow echoColor) <color> <texto> ---> imprime texto en el color indicado que puede ser:    
      Black, Red, Green, Orange, Blue , Purple , Cyan, LightGray, DarkGray, LightRed, LightGreen, 
      Yellow, LightBlue, LightPurple, LightCyan, White
$(echoColor Yellow ayuda)  ---> imprime esta ayuda
\n-------------------------------------------------------------------------------------
Esto es to, eso es to, eso es todo amigos!!!
$(echoColor LightCyan @F.J.Criado)
Pulsa q para salir" | less -R
