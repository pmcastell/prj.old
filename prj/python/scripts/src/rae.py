#!/usr/bin/python
# -*- coding: utf-8 -*-
# Author : Leo Gutiérrez Ramirez, leorocko13@hotmail.com
# html2txt by Francois Granger, <fgranger@altern.org>
import sys
import re
import urllib
 
def Usage():
   print "\nRealiza busquedas en el diccionario de la [Real Academia Española]\n";
   print "\t" + sys.argv[0] + " Palabra\n";
   sys.exit(1);

sys.argv.append("casa")
if len(sys.argv) <= 1:
   Usage();
 
url = "http://lema.rae.es/drae/srv/search?val=" + sys.argv[1]
url="https://www.iesinclan.tk/franc/"
print("url: "+url);
f = urllib.urlopen(url);
texto = f.read();
f.close();
 
# Convierte el código HMTL a texto para ser mostrado correctamente en la consola:
 
p = re.compile('(<p.*?>)|(<tr.*?>)', re.I)
t = re.compile('<td.*?>', re.I)
comm = re.compile('<!--.*?-->', re.M)
tags = re.compile('<.*?>', re.M)
 
def html2txt(texto, hint = 'entity', code = 'ISO-8859-1'):
   texto = texto.replace('\n', '') # remove returns time this compare to split filter join
   texto = p.sub('\n', texto) # replace p and tr by \n
   texto = t.sub('\t', texto) # replace td by \t
   texto = comm.sub('', texto) # remove comments
   texto = tags.sub('', texto) # remove all remaining tags
   texto = re.sub(' +', ' ', texto) # remove running spaces this remove the \n and \t
   # Handling of entities
   result = texto;
   pass;
   return result;
print html2txt(texto);
sys.exit(0);