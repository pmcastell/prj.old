Option Compare Database
Option Explicit

Sub procesosServicioMacros(Optional macro As String = "")
    Dim linea As String
    Dim db As Database
    Dim doc As Document
    Dim fichero, consulta As String


    fichero = "C:\temp\macroTemp.txt"
    Set db = CurrentDb
    For Each doc In db.Containers("Scripts").Documents
        If (macro = "" Or macro = doc.Name) Then
            SaveAsText acMacro, doc.Name, fichero
            consulta = "INSERT INTO [PROCESOS] VALUES ('" & doc.Name & "')"
            db.Execute (consulta)
            Open fichero For Input Access Read As #1
            Line Input #1, linea
            While Not EOF(1)
                While (Not EOF(1) And linea <> "Begin")
                   Line Input #1, linea
                Wend
                While (Not EOF(1) And InStr(1, linea, "Argument") = 0)
                   Line Input #1, linea
                Wend
                If (Not EOF(1)) Then
                   consulta = "INSERT INTO [SUBPROCESOS] VALUES ('" & doc.Name
& "','" & Mid(linea, InStr(1, linea, """") + 1)
                   consulta = Mid(consulta, 1, Len(consulta) - 1) & "')"
                   db.Execute (consulta)
                End If

                'LUpdate , dbFailOnError


                While (Not EOF(1) And InStr(1, linea, "End") = 0)
                   Line Input #1, linea
                Wend

            Wend
            Close #1
        End If
    Next

End Sub


