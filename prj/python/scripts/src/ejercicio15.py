#!/usr/bin/python
# -*- coding: utf-8 -*-

# Creado: 18 de mar. de 2017
# autor: usuario
import sys

if (len(sys.argv)<4):
    print "Uso: "+sys.argv[0]+" <banco> <oficina> <numCuenta>"
    sys.exit(1)

banc=sys.argv[1]; ofic=sys.argv[2]; cuen=sys.argv[3]
#validación 
try:
    int(banc); int(ofic); int(cuen)
except:
    print "Datos erróneos. Debe inroducir:";print "Código de banco de 4 dígitos"
    print "Código de oficina de 4 dígitos";print "Código de cuenta de 10 dígitos"
    sys.exit(2)
               
pesos=[1,2,4,8,5,10,9,7,3,6]
bancOfic="00"+banc+ofic
d1=d2=0
for i in range(10):
    d1+=int(bancOfic[i])*pesos[i]
    d2+=int(cuen[i])*pesos[i]

d1=11-d1%11; d2=11-d2%11
if (d1==10): d1=1 
elif (d1==11): d1=0
if (d2==10): d2=1
elif (d2==11): d2=0

print banc+"-"+ofic+"-"+str(d1)+str(d2)+"-"+cuen  
