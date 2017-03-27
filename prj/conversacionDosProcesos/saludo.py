#!/usr/bin/python
# -*- coding: utf-8 -*-

import sys

def imprime(cad):
   print cad
   sys.stdout.flush()
   
   
while True:
   resp=raw_input()
   sys.stderr.write( resp+"\n")
   if (resp == "hola"):
      imprime("que tal")
   elif (resp == "bien"):
      imprime("me alegro")
   elif (resp == "y tu"):
      imprime("bien, gracias. Hasta luego")
      break
   
      
