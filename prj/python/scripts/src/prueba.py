'''
Created on 13/04/2009

@author: usuario
'''

import os, sys, time
print "hola"

cumples=[
      ["PAPA","12-11-2006",'C'],
      ["MAMA","14-01-2007",'C'],
      ["PAQUI","01-11,1958",'C'],
      ["ANTONIO","22-03-1960",'C'],
      ["JUAN","06-06-1963",'C'],
      ["FRANC","06-08-1965",'C'],
      ["MARI","21-10-1969",'C'],
      ["JORGE","30-09-1975",'C'],

      ["RAFA","21-09-1987",'C'],
      ["ALBERTO","09-08-2006",'C'],
      ["DAVID","13-05-2006",'C'],
      ["LAURA","20-04-1996",'C'],
      ["ANTONIN","26-04-1994",'C'],

      ["ESTHER","20-02-2007",'C'],
      ["CRISTINA","07-03-2007",'C'],
      
      ["PAPA, ANTONIO, CHICO RICO","13-06-2006",'S'],
      ["MAMA, MARI","12-09-2006",'S'],
      ["PAQUI","04-10-2006",'S'],
      ["JUAN","24-06-2006",'S'],
      ["FRANC","03-12-2006",'S'],
      ["JORGE","23-04-2006",'S'],

      ["RAFA","24-10-2006",'S'],

      ["DIA DEL PADRE","19-03-2007",'S'],
      ["DIA DE LA MADRE","06-05-2006",'S'],


      ["ESTHER","01-07-2006",'S'],
      ["CRISTINA","24-07-2006",'S'],

      ["JUAN JOSE","05-07-1958",'C'],
      ["MARIA","08-09-2006",'S'],
      ["FATIMA","17-12-2006",'C'],
      ["JUAN JOSE","19-03-2007",'S'],

      ["RENOVACION CERTIFICADO FNMT","25-04-2006",'C']
      
   ]

hoy=time.strftime("%d-%m-%Y", time.localtime())
for i in range(0, len(cumples)):
    cumples[0][1][0:5]
    if (cumples[i][1][0:5]==hoy[0:5]):
        if (cumples[i][2]=="C"):
            print "Hoy es el Cumple de: ",cumples[i][0]," y cumple ",int(hoy[6:])-int(cumples[i][1][6:])," anios. "
        else:
            print "Hoy es el Santo de: ",cumples[i][0]
                 
