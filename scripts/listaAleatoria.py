#!/usr/bin/python
#-*- coding: utf-8 -*-
import random
def listaAleatoria(limInf=5,limSup=16,nItems=5):
   lista=[]
   while (len(lista)<nItems):
      n=random.randint(limInf,limSup)
      if (not n in lista):
         lista.append(n)
   lista.sort()
   return lista


if __name__ == '__main__':
   print listaAleatoria()   

