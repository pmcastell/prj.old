#!/usr/bin/python
# -*- coding: utf-8 -*-

# Fecha creación: 19 mar. 2017
# autor: usuario
import sys
#expresamos todas las cantidades en céntimos
def cantidadACentimos(cant):
    res=""; j=-1
    for i in range(len(cant)):
        if (cant[i]!="."):
            res+=cant[i]
        else:
            j=i
    if (j!=-1): # es que hay un punto decimal
        if (len(cant)-1-j>2): print "Error sólo se admiten 2 decimalex como mucho."; sys.exit(1)
        for i in range(2-(len(cant)-1-j)): res+="0"
    else:
        res+="00"
    return int(res)
        
billetes=[20000,10000,5000,2000,1000,500]
monedas=[200,100,50,20,10,5,2,1]
devBilletes=[0,0,0,0,0,0]
devMonedas=[0,0,0,0,0,0,0,0]
#sys.argv=['ejercicio17.py','33.55','100']
try:
    float(sys.argv[1]); float(sys.argv[2]) # para que dé una excepción si el formato de los num. no está bien
    aPagar=cantidadACentimos(sys.argv[1]); entrega=cantidadACentimos(sys.argv[2])
except:
    print "Debe expresar 2 cantidades de hasta 2 decimales. Las cantidades son incorrectas. Inténtelo de nuevo"
    sys.exit(2)

dev=entrega-aPagar
if (dev<0): print "Error: la cantidad entregada no es suficiente para pagar"; sys.exit(3)
for i in range(len(billetes)):
    if (dev>=billetes[i]):
        devBilletes[i]=dev/billetes[i] #división entera
        dev-=devBilletes[i]*billetes[i]
for i in range(len(monedas)):
    if (dev>=monedas[i]):
        devMonedas[i]=dev/monedas[i] #división entera
        dev-=devMonedas[i]*monedas[i]

print "A devolver:"
for i in range(len(billetes)):
    if (devBilletes[i]>0):
        tipoMoneda=" billete"
        if (devBilletes[i]>1):
            tipoMoneda+="s"
        print str(devBilletes[i])+tipoMoneda+" de "+str(billetes[i]/100)+" euros."
        
for i in range(len(monedas)):
    if (devMonedas[i]>0):
        tipoMoneda=" moneda"
        if (devMonedas[i]>1):
            tipoMoneda+="s"
        cantidad=monedas[i]
        if (monedas[i]==100):
            unidad=" euro."
            cantidad/=100
        elif (monedas[i]>100):
            unidad=" euros."
            cantidad/=100
        elif (monedas[i]==1):
            unidad=" céntimo."
        else: # (monedas[i]<100):
            unidad=" céntimos."
        print str(devMonedas[i])+tipoMoneda+" de "+str(cantidad)+unidad
        
