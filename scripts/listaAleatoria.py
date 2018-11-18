#!/usr/bin/python
#-*- coding: utf-8 -*-

import random, sys
def listaAleatoria(limInf=5,limSup=16,nItems=5):
   lista=[]
   while (len(lista)<nItems):
      n=random.randint(limInf,limSup)
      if (not n in lista):
         lista.append(n)
   lista.sort()
   return lista


if __name__ == '__main__':
   if (len(sys.argv)>3):
      limInf=int(sys.argv[1])
      limSup=int(sys.argv[2])
      nItems=int(sys.argv[3])
      print(listaAleatoria(limInf,limSup,nItems))
      sys.exit(0)
   if (len(sys.argv)>2): 
      limInf=int(sys.argv[1])
      limSup=int(sys.argv[2]) 
      print(listaAleatoria(limInf,limSup))
      sys.exit(0)
   if (len(sys.argv)>1):
      limInf=int(sys.argv[1])
      print(listaAleatoria(limInf))
      sys.exit(0)
   print(listaAleatoria())

