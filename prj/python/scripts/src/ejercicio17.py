#!/usr/bin/python
# -*- coding: utf-8 -*-

# Fecha creación: 19 mar. 2017
# autor: usuario
import sys
#expresamos todas las cantidades en céntimos
billetes=[200,100,50,20,10,5]
monedas=[2,1,0.50,0.20,0.10,0.02,0.01]
devBilletes=[0,0,0,0,0,0]
devMonedas=[0,0,0,0,0,0,0]
#sys.argv=['ejercicio17_bis.py',]
try:
    aPagar=float(sys.argv[1])
    entrega=float(sys.argv[2])
except:
    print "Debe expresar 2 cantidades de hasta 2 decimales. Las cantidades son incorrectas. Inténtelo de nuevo"
    sys.exit(1)

#aPg=int(aPagar*100)
#ent=int(entrega*100)
dev=entrega-aPagar
if (dev<0): print "Error: la cantidad entregada no es suficiente para pagar"; sys.exit(2)
for i in range(len(billetes)):
    if (dev>=billetes[i]):
        devBilletes[i]=int(dev/billetes[i]) #división entera
        dev-=devBilletes[i]*billetes[i]
for i in range(len(monedas)):
    if (dev>=monedas[i]):
        devMonedas[i]=int(dev/monedas[i]) #división entera
        dev-=devMonedas[i]*monedas[i]

print "A devolver:"
for i in range(len(billetes)):
    if (devBilletes[i]>0):
        tipoMoneda=" billete"
        if (devBilletes[i]>1):
            tipoMoneda+="s"
        print str(devBilletes[i])+tipoMoneda+" de "+str(billetes[i])+" euros."
        
for i in range(len(monedas)):
    if (devMonedas[i]>0):
        tipoMoneda=" moneda"
        if (devMonedas[i]>1):
            tipoMoneda+="s"
        cantidad=monedas[i]
        if (monedas[i]==1):
            unidad=" euro."
        elif (monedas[i]>100):
            unidad=" euros."
        elif (monedas[i]==1):
            unidad=" céntimo."
            cantidad=int(cantidad*100)
        elif (monedas[i]<100):
            unidad=" céntimos."
            cantidad=int(cantidad*100)
        print str(devMonedas[i])+tipoMoneda+" de "+str(cantidad)+unidad
        
