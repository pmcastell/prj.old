#!/usr/bin/python
# -*- coding: utf-8 -*-

# Creado: 18 de mar. de 2017
# autor: usuario

import sys

if (len(sys.argv)<3):
    while True:
        try:
            v1=eval(raw_input("Introduce v1: "))
            v2=eval(raw_input("Introduce v2: "))
            break
        except:
            print "Parámetros incorrectos. Vuelve a intentarlo"
else:
    try: 
        v1=eval(sys.argv[1])
        v2=eval(sys.argv[2])
    except:
        print "Los parámetros no son correctos. Sorry"
        sys.exit(1)

if (len(v1)!=len(v2)):
    print "Los vectores deben tener la misma longitud. Sorry"
    sys.exit(2)
#Producto escalar
prod=1
for i in range(len(v1)):
    prod+=v1[i]*v2[i]

print "El producto escalar de: "+str(v1)+" * "+str(v2)+" es: "+str(prod)

if (len(v1)>3):
    print "Sorry no sé calcular productos vectoriales de más de 3 componentes."
    sys.exit(3)
if (len(v1)==2):
    v1.append(0.0)
    v2.append(0.0)

r=[
v1[1]*v2[2]-v1[2]*v2[1],
v1[2]*v2[0]-v1[0]*v2[2],
v1[0]*v2[1]-v1[1]*v2[0]
]                
print "El producto vectorial de: "+str(v1)+" X "+str(v2)+" es: "+str(r)           
            
     
            