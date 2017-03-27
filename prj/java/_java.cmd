xcopy i:\Mios\prj\java\*.java i:\Backup\java /s /y
xcopy i:\Mios\prj\java\*.txt i:\Backup\java /s /y
del i:\Backup\java\javaMios.rar
WinRAR  -r a i:\Backup\java\javaMios i:\Backup\java\*.*
