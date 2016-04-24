set src=k:\mios\calculadora\src
del %src%\hs_err_pid*
javac visualizador.java
if errorlevel 1 goto :ERROR
del visualizador.jar
call deploy visualizador
echo borrando %src%\*.class
del %src%\*.class
echo borrando %src%\mieval\*.class
del %src%\mieval\*.class
echo borrando %src%\mieval\tipo\*.class
del %src%\mieval\tipo\*.class
echo borrando %src%\layout\*.class
del %src%\layout\*.class
echo borrando visualizador.jar
del ..\visualizador.jar
move visualizador.jar ..\
set src=
cat k:\mios\utils\sirena
cat k:\mios\utils\sirena
cat k:\mios\utils\sirena
goto :FIN
:ERROR
echo error compilando
pause
:FIN