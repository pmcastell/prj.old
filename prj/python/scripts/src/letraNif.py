#!/usr/bin/python

import sys

if (len(sys.argv)<2):
   print "Uso: ",sys.argv[0]," <dni>."
else:   
   print "introduce dni: "
   dni= sys.argv[1]
   dni=int(dni)
   #letras="TRWAGMYFPDXBNJZSQVHLCKE"
   #r=dni % 23
   print dni,"TRWAGMYFPDXBNJZSQVHLCKE"[dni % 23]

#print dni,letras[dni % 23]

#if (r==0):
#   LetraNIF = "T"
#elif (r==1):  
#   LetraNIF = "R"
#elif (r==2):     
#   LetraNIF = "W"
#elif (r==3):        
#   LetraNIF = "A"
#elif (r==4):        
#   LetraNIF = "G"
#elif (r==5):        
#   LetraNIF = "M"
#elif (r==6):        
#   LetraNIF = "Y"
#elif (r==7):        
#   LetraNIF = "F"
#elif (r==8):        
#   LetraNIF = "P"
#elif (r==9):        
#   LetraNIF = "D"
#elif (r==10):        
#   LetraNIF = "X"
#elif (r==11):        
#   LetraNIF = "B"
#elif (r==12):        
#   LetraNIF = "N"
#elif (r==13):        
#   LetraNIF = "J"
#elif (r==14):        
#   LetraNIF = "Z"
#elif (r==15):        
#   LetraNIF = "S"
#elif (r==16):        
#   LetraNIF = "Q"
#elif (r==17):        
#   LetraNIF = "V"
#elif (r==18):        
#   LetraNIF = "H"
#elif (r==19):        
#   LetraNIF = "L"
#elif (r==20):        
#   LetraNIF = "C"
#elif (r==21):        
#   LetraNIF = "K"
#elif (r==22):        
#   LetraNIF = "E"
#else: 
#   LetraNIF = "Error"
#
#if (LetraNIF!="Error"):
#   print "NIF: ",dni,LetraNIF
#else:
#   print "Error en dni"
   
