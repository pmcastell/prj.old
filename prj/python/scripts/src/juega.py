#!/usr/bin/python
# -*- coding: utf-8 -*-

# Creado: 18 de mar. de 2017
# autor: usuario

import sys

minimo=0
maximo=100
intento=50
while True:
    resp=raw_input()
    print resp
    if ("menor" in resp):
        maximo=intento
        intento=int((maximo+minimo)/2)
    elif ("mayor" in resp):
        minimo=intento
        intento=int((maximo+minimo)/2)
    elif ("Intento n" in resp):
        print intento
    elif ("Lo siento" in resp):
        pass
    elif ("Muy Bien!!!" in resp):
        print "Ya lo sabía, soy muy bueno jugando a esto"
    elif ("Quieres jugar" in resp):
        print "n"
        break
    else:
        sys.stderr.write('Error Desconocido. Fin.\n')
        sys.exit(1)
      
