#!/usr/bin/python
#-*- coding: utf-8 -*-

import HTMLParser, os.path, sys, traceback
def set_output_encoding(encoding='utf-8'):
    import sys
    import codecs
    '''When piping to the terminal, python knows the encoding needed, and
       sets it automatically. But when piping to another program (for example,
       | less), python can not check the output encoding. In that case, it 
       is None. What I am doing here is to catch this situation for both 
       stdout and stderr and force the encoding'''
    current = sys.stdout.encoding
    if current is None :
        sys.stdout = codecs.getwriter(encoding)(sys.stdout)
    current = sys.stderr.encoding
    if current is None :
        sys.stderr = codecs.getwriter(encoding)(sys.stderr)




h=HTMLParser.HTMLParser()
if (len(sys.argv)>1):
   param1=sys.argv[1]
else:
   param1=None

set_output_encoding()   
   
try:
   if (param1!=None):
      fichero=open(param1,"r")
   else:
      fichero=sys.stdin
   for linea in fichero:
      try:
         if (linea[len(linea)-1]=="\n"):
            print h.unescape(linea[:len(linea)-1])
         else: 
            print h.unescape(linea)
      except:
         traceback.print_exc()
except:
   print h.unescape(param1)            



