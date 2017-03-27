#!/usr/bin/python
'''
Created on 18/10/2009

@author: usuario
'''
import sys


def valorHex(ch):
    if ('A' <= ch and ch <='F'):
        return ord(ch)-ord('A')+10
    elif ('a' <=ch and ch <='f'):
        return ord(ch)-ord('a')+10
    elif ('0' <= ch and ch <= '9'):
        return ord(ch)-ord('0')
    else:
        return -1 #error

    

def hex2asc(hex):
    if (len(hex) % 2 != 0):
        return "Error la cadena no tiene un numero par de caracteres"
    res=""
    i=0
    while (i<len(hex)):
        d1=valorHex(hex[i])
        if (d1<0):
            return "Error en caracter "+str(i)+": "+hex[i]
        d2=valorHex(hex[i+1])
        if (d2<0):
            return "Error en caracter "+str(i+1)+": "+hex[i+1]
        res+=chr(d1*16+d2)
        i+=2;
    return res

def asc2hex(asc):
    res=""
    i=0
    while(i<len(asc)):
        digito=ord(asc[i]) / 16;
        if (digito<10):
            res+=chr(ord('0')+digito)
        else:
            res+=chr(ord('A')+digito-10)
        digito=ord(asc[i]) % 16
        if (digito<10):
            res+=chr(ord('0')+digito)
        else:
            res+=chr(ord('A')+digito-10)
        i+=1
    return res

def hex2aschex(hex):
    res=""
    i=0
    while(i<len(hex)):
       res+="%"+hex[i]+hex[i+1]
       i+=2
    return res    

conv=""
for arg in sys.argv:
   if (arg == "-h"):
       conv="hex"
   elif (arg == "-a"):
       conv="asc"
   elif (arg == "-hh"):
       conv="hex2aschex"

if conv=="":
    print "Uso: "+sys.argv[0]+" {-h|-a|-hh} <cadena-a-convertir>"
    sys.exit()
           
#print hex2asc("E001D20A3EA5")
if sys.argv[2]=="-":
    for linea in sys.stdin:
        if (linea.find("\n")):
            linea=linea[:len(linea)-1]
        if (conv == "hex" ):
            print asc2hex(linea)
        elif (conv == "asc"):
            print hex2asc(linea)
        elif (conv == "hex2aschex"):
            print hex2aschex(linea)    
else:
    if (conv == "hex" ):
        print asc2hex(sys.argv[2])
    elif (conv == "asc"):
        print hex2asc(sys.argv[2])
    elif (conv == "hex2aschex"):
        print hex2aschex(sys.argv[2])    
    
        
    
    
