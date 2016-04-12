@echo off
i:
cd i:\Backup
cd java
call java.cmd
cd ..
cd mios
call mios.cmd
cd ..
cd ple
call ple.cmd
cd ..
cd prjc++
call prj.cmd
rem echo Introduce disco personal en UNIDAD F:
rem echo ¿Copiar a la Unidad F: (S/N)?
rem tecla sSnN
rem if errorlevel 2 goto :fin
xcopy i:\Backup\java\javaMios.rar I:\Mios\Personal\Backup /y
xcopy i:\Backup\mios\windowsMios.rar I:\Mios\Personal\Backup /y
xcopy i:\Backup\ple\ple.rar I:\Mios\Personal\Backup /y
xcopy i:\Backup\prjc++\prjc++.rar I:\Mios\Personal\Backup /y
:fin
