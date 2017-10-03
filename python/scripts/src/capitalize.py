#!/usr/bin/python
# -*- coding: utf-8 -*-
'''
Created on 20/12/2014

@author: usuario
'''
import sys, codecs

def upper(ch):
   if (ch in u"áéíóúñ"):
      if (ch==u"á"):
         return u"Á"
      elif (ch==u"é"):
         return u"É"
      elif (ch==u"í"):
         return u"Í"
      elif (ch==u"ó"):
         return u"Ó"
      elif (ch==u"ú"):
         return u"Ú"
      else:
         return u"Ñ"
   else:
      return ch.upper()
   
def lower(ch):
   if (ch in u"ÁÉÍÓÚÑ"):  
      if (ch==u"Á"):
         return u"á"
      elif (ch==u"É"):
         return u"é"
      elif (ch==u"Í"):
         return u"í"   
      elif (ch==u"Ó"):
         return u"ó"
      elif (ch==u"Ú"):
         return u"ú"
      else:
         return u"ñ"
   else:
      return ch.lower()
    
def capitalize():
   change=True
   UTF8Reader = codecs.getreader('utf-8')
   sys.stdin = UTF8Reader(sys.stdin)
   sys.stdout = codecs.getwriter('utf-8')(sys.stdout)
   for line in sys.stdin:
      for ch in line:
         if (change):
            sys.stdout.write(upper(ch))
         else:
            sys.stdout.write(lower(ch))
         if (ch=="."):
            change=True
         elif (ch==" " or ch=="\t" or ch=="\n" ):
            change=True 
         else:
            change=False

texto="""
 EL MUNDO ESTÁ AL REVÉS. LUEGO SE PUSO PEOR LA COSA.
 entonces aparecieron ellos. y luego NADA FUE igual.
 Después se vieron avocados al triunfo y LUEGO nada MÁS se SUPO de ELLOS.
""" 
capitalize()            
            
