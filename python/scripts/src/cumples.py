#!/usr/bin/python
# -*- coding: utf-8 -*-

'''
Created on 13/04/2009

@author: usuario
'''

#import os, sys, time, datetime
import os, sys
#date
from datetime import date
#mport datetime

cumples=[
      ["PAPA","12-11-1922",'C'],
      ["MAMA","14-01-1937",'C'],
      ["PAQUI","01-11-1958",'C'],
      ["ANTONIO","22-03-1960",'C'],
      ["JUAN","06-06-1963",'C'],
      ["FRANC","06-08-1965",'C'],
      ["MARI","21-10-1969",'C'],
      ["JORGE","30-09-1975",'C'],

      ["RAFA","21-09-1987",'C'],
      ["ALBERTO","09-08-1990",'C'],
      ["DAVID","13-05-1991",'C'],
      ["LAURA","20-04-1995",'C'],
      ["ANTONIO CHICO","26-04-1994",'C'],

      ["ESTHER","20-02-1965",'C'],
      ["CRISTINA","07-03-1985",'C'],
      
      ["PAPA, ANTONIO, ANTONIO CHICO","13-06-2006",'S'],
      ["MAMA, MARI","12-09-2006",'S'],
      ["PAQUI","04-10-1958",'S'],
      ["JUAN","24-06-1963",'S'],
      ["FRANC","03-12-1965",'S'],
      ["JORGE","23-04-1975",'S'],

      ["RAFA","24-10-1987",'S'],

      ["DIA DEL PADRE","19-03-2007",'S'],
      ["DIA DE LA MADRE","06-05-2018",'S'],

      ["CRISTINA","24-07-2006",'S'],

      ['MARI MEDICO','17-05-2011','C'],
      ['CERTFICADO FNMT, no olvides desactivar navegación privada en firefox','01-11-2020','C'],
      ['CERTIFICADO JORGE Y MAMÁ, no olvides desactivar navegación privada en firefox','14-12-2017','C'],
      ['RENOVAR SEGURO COCHE DOMICILIACION','29-06-2016','C'],
      ['REENVÍO DE CORRESPONDENCIA POSTAL','29-01-2017','C'],
      ['CURSOS INTEF 2ª ED','15-06-2016','S'],
      ['ubuin.noip.me','12-07-2016','C'],
      ['Dropbox Espacio tablet caduca','31-08-2016','C'],
      ['Cita Jorge Dentista Fenoll 18:30','17-01-2017','C'],
      ['Cita Jorge Lily 13:15','16-06-2017','C'],
      ['Comprobar Axa no ha cobrado seguro del piso','02/08/2018','C']
   ]
def rellena(cadena,tam):
    for i in range(1,tam-len(cadena)):
        cadena+=" "
    return cadena
        

#hoy=time.strftime("%d-%m-%Y", time.localtime())
if (len(sys.argv)>1):
    for i in range(1,len(sys.argv)):
        if (sys.argv[1]=="-l"):
            for j in range(0,len(cumples)):
                print " | "+rellena(cumples[j][0],26)+" | "+cumples[j][1]+" | "+cumples[j][2]+" | "
else:                
    hoy=date.today()
    mensaje=""
    for i in range(0, len(cumples)):
        cumpleActual=date(hoy.year,int(cumples[i][1][3:5]),int(cumples[i][1][0:2]))
        #dias=(datetime.date(int(hoy[6:10]),int(cumples[i][1][3:5]),int(cumples[i][1][0:2])) - datetime.date(int(hoy[6:10]), int(hoy[3:5]), int(hoy[0:2]))).days
        dias=(cumpleActual-hoy).days
        if (dias>=0 and dias<=5):
            if (dias==0):
                if (cumples[i][2]=="C"):
                    mensaje+="\nHoy es el Cumple de: "+cumples[i][0]+"  y cumple "+str(hoy.year-int(cumples[i][1][6:]))+" anios. "
                else:
                    mensaje+="\nHoy es el Santo de: "+cumples[i][0]
            else:
                if (cumples[i][2]=="C"):
                    mensaje+="\nFaltan "+str(dias)+" dias para el cumple de: "+cumples[i][0]+"  y cumplira "+str(hoy.year-int(cumples[i][1][6:]))+" anios. "
                else:
                    mensaje+="\nFaltan "+str(dias)+" dias para el Santo de: "+cumples[i][0]
    
    if (mensaje!=""):            
        print mensaje
        os.system("gxmessage \""+mensaje+"\"")
        os.system('hablaf "'+mensaje+'"')
                    
                     
