VERSION 5.00
Begin VB.Form Form1 
   Caption         =   "Form1"
   ClientHeight    =   3216
   ClientLeft      =   48
   ClientTop       =   336
   ClientWidth     =   5736
   LinkTopic       =   "Form1"
   ScaleHeight     =   3216
   ScaleWidth      =   5736
   StartUpPosition =   3  'Windows Default
   Begin VB.TextBox cuenta 
      Alignment       =   1  'Right Justify
      Height          =   372
      Left            =   3000
      TabIndex        =   11
      Top             =   1560
      Width           =   1572
   End
   Begin VB.TextBox dc 
      Alignment       =   1  'Right Justify
      Enabled         =   0   'False
      Height          =   372
      Left            =   2280
      TabIndex        =   9
      Top             =   1560
      Width           =   732
   End
   Begin VB.TextBox sucursal 
      Alignment       =   1  'Right Justify
      Height          =   372
      Left            =   1560
      TabIndex        =   7
      Top             =   1560
      Width           =   732
   End
   Begin VB.TextBox entidad 
      Alignment       =   1  'Right Justify
      Height          =   372
      Left            =   840
      TabIndex        =   5
      Top             =   1560
      Width           =   732
   End
   Begin VB.CommandButton Command2 
      Caption         =   "&D.C."
      Height          =   372
      Left            =   2160
      TabIndex        =   4
      Top             =   2040
      Width           =   972
   End
   Begin VB.TextBox letra 
      Alignment       =   1  'Right Justify
      Height          =   372
      Left            =   4080
      TabIndex        =   3
      Top             =   480
      Width           =   492
   End
   Begin VB.CommandButton Command1 
      Caption         =   "&Letra"
      Height          =   372
      Left            =   2880
      TabIndex        =   1
      Top             =   480
      Width           =   972
   End
   Begin VB.TextBox nif 
      Alignment       =   1  'Right Justify
      Height          =   372
      Left            =   1200
      TabIndex        =   0
      Top             =   480
      Width           =   1572
   End
   Begin VB.Label Label5 
      Alignment       =   2  'Center
      Caption         =   "Nº Cuenta"
      Height          =   252
      Left            =   3000
      TabIndex        =   12
      Top             =   1200
      Width           =   1572
   End
   Begin VB.Label Label4 
      Alignment       =   2  'Center
      Caption         =   "D.C."
      Height          =   252
      Left            =   2280
      TabIndex        =   10
      Top             =   1200
      Width           =   732
   End
   Begin VB.Label Label3 
      Alignment       =   2  'Center
      Caption         =   "Sucursal"
      Height          =   252
      Left            =   1560
      TabIndex        =   8
      Top             =   1200
      Width           =   732
   End
   Begin VB.Label Label2 
      Alignment       =   2  'Center
      Caption         =   "Entidad"
      Height          =   252
      Left            =   840
      TabIndex        =   6
      Top             =   1200
      Width           =   732
   End
   Begin VB.Label Label1 
      Caption         =   "D.N.I."
      Height          =   252
      Left            =   240
      TabIndex        =   2
      Top             =   600
      Width           =   732
   End
End
Attribute VB_Name = "Form1"
Attribute VB_GlobalNameSpace = False
Attribute VB_Creatable = False
Attribute VB_PredeclaredId = True
Attribute VB_Exposed = False
Option Explicit
Option Base 0
Private Sub Command1_Click()
    Dim letras
    If (Not IsNull(nif.Text) And nif.Text <> "") Then
       letras = Array("T", "R", "W", "A", "G", "M", "Y", "F", "P", "D", "X", "B", "N", "J", "Z", "S", "Q", "V", "H", "L", "C", "K", "E")
       letra = letras(CLng(nif.Text) Mod 23)
    End If
End Sub
Function rellenaCeros(cadena, tam)
   Dim dif As Long, i As Long
   cadena = CStr(cadena)
   dif = tam - Len(cadena)
   For i = 1 To dif
      cadena = "0" + cadena
   Next
   rellenaCeros = cadena
End Function
Function digitControl(banco, sucursal, cuenta)
   Dim a, b, c, d, e, f, g, h, i, j, p1, p2, d1, d2 As Integer
   If (IsNull(banco) Or IsNull(sucursal) Or IsNull(cuenta)) Then
      MsgBox ("Error en parámetros")
      Exit Function
   End If
   banco = rellenaCeros(banco, 4)
   sucursal = rellenaCeros(sucursal, 4)
   cuenta = rellenaCeros(cuenta, 10)
   a = CInt(Mid(banco, 1, 1))
   b = CInt(Mid(banco, 2, 1))
   c = CInt(Mid(banco, 3, 1))
   d = CInt(Mid(banco, 4, 1))
   e = CInt(Mid(sucursal, 1, 1))
   f = CInt(Mid(sucursal, 2, 1))
   g = CInt(Mid(sucursal, 3, 1))
   h = CInt(Mid(sucursal, 4, 1))
   
   p1 = 4 * a + 8 * b + 5 * c + 10 * d + 9 * e + 7 * f + 3 * g + 6 * h
   d1 = 11 - p1 Mod 11
   If (d1 = 10) Then
      d1 = 1
   ElseIf (d1 = 11) Then
      d1 = 0
   End If
   a = CInt(Mid(cuenta, 1, 1))
   b = CInt(Mid(cuenta, 2, 1))
   c = CInt(Mid(cuenta, 3, 1))
   d = CInt(Mid(cuenta, 4, 1))
   e = CInt(Mid(cuenta, 5, 1))
   f = CInt(Mid(cuenta, 6, 1))
   g = CInt(Mid(cuenta, 7, 1))
   h = CInt(Mid(cuenta, 8, 1))
   i = CInt(Mid(cuenta, 9, 1))
   j = CInt(Mid(cuenta, 10, 1))
   p2 = a + 2 * b + 4 * c + 8 * d + 5 * e + 10 * f + 9 * g + 7 * h + 3 * i + 6 * j
   d2 = 11 - p2 Mod 11
   If (d2 = 10) Then
      d2 = 1
   ElseIf (d2 = 11) Then
      d2 = 0
   End If
   digitControl = CStr(d1) + CStr(d2)

End Function

Private Sub Command2_Click()
  dc.Text = digitControl(entidad, sucursal, cuenta)
End Sub
