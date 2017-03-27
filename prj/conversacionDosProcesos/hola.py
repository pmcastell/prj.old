#!/usr/bin/python
# -*- coding: utf-8 -*-

import sys

def imprime(cad):
   print cad
   sys.stdout.flush()
   

imprime("hola")

while True:
   resp=raw_input()
   sys.stderr.write( resp+"\n")
   if (resp == "que tal"):
      imprime("bien")
   elif (resp == "me alegro"):
      imprime("y tu")
   elif (resp == "bien, gracias. Hasta luego"):
      imprime("hasta luego")
      break
   
   
