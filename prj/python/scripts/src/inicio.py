#!/usr/bin/python
#'''
#Created on 09/07/2012
#
#@author: usuario
#'''

import os,sys

try:
    fin=open("inicio.txt","r")
except:
    print "error abriendo fichero inicio.txt"    
    sys.exit(2)
for linea in fin:
    if (linea!="" and linea[0]!='#'):
        print "ejecutando: ",linea
        os.system(linea)
fin.close()

