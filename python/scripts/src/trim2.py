#!/usr/bin/python

import os, sys, string

def quitaCadena(cadena, cadenaQuitar=' '):
   res=''
   palabras=cadena.split(cadenaQuitar)
   for palabra in palabras:
      if (len(palabra)>0):
         res+=palabra[0].upper()+palabra[1:]
   return res

def renombraRecursivo(quitar=' ',recursivo='n',reemplazar=''):
   for fname in os.listdir(os.getcwd()):
      #print fname
      nombre=fname
      if (fname.find(quitar)>=0):
         #nombre=quitaCadena(fname,quitar)
         if (quitar==' ' and capitaliza):
            primeraLetra=fname[0]
            nombre=string.replace(string.capwords(fname,' '),quitar,reemplazar)
            nombre=primeraLetra+nombre[1:]
         else:
            nombre=string.replace(fname,quitar,reemplazar)
         print os.getcwd()+'\\'+fname,'-->',os.getcwd()+'\\'+nombre
         os.rename(fname,nombre)
      if (os.path.isdir(nombre) and recursivo=='s'):
         os.chdir(nombre)
         renombraRecursivo(quitar,recursivo,reemplazar)
         os.chdir("..")

      #if (fname!=quitaAcentos(fname)):
      #   print os.getcwd()+'\\'+fname,'-->',os.getcwd()+'\\'+quitaAcentos(fname)
      #   os.rename(fname,quitaAcentos(fname))
      #print(fname)

def uso():
   print("Uso: "+sys.argv[0]+" [-r] [-q <cadena-a-quitar>]")
   print("Uso: "+sys.argv[0]+" [-r] [-R <cadena-a-reemplazar> <reemplazo>]")
   print("-r recursivo")
   print("-q quitar <cadena-a-quitar> de los nombres de los ficheros en lugar de los espacios")
      
recursivo='n'
quitar=' ' 
reemplaza=''   
capitaliza=0
i=0
print "Opciones: "
while i <len(sys.argv):
   param=sys.argv[i]
   if (param=='-r'):
      print "Recursivo"
      recursivo='s'
   elif (param=='-R'):
      i+=1;
      quitar=sys.argv[i];
      i+=1;
      reemplaza=sys.argv[i];
      print 'Reemplazar: '+quitar+' por '+reemplaza
   elif (param=='-q'):
      i+=1;
      quitar=sys.argv[i];
      print "Quitar: "+quitar
   elif (param=='-C'):
      capitaliza=1
      print "Capitalizar"
   elif (param=='-h' or param=='-help' or param=='--help'):
      uso();
      exit(-1);
   i+=1

renombraRecursivo(quitar,recursivo,reemplaza)

