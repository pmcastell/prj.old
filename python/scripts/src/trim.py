#!/usr/bin/python
# -*- coding: utf-8 -*-

# Fecha creación: 4 feb. 2018
# autor: usuario

import os, sys, string

def quitaCadena(cadena, cadenaQuitar=' '):
    res=''
    palabras=cadena.split(cadenaQuitar)
    for palabra in palabras:
        if (len(palabra)>0):
            res+=palabra[0].upper()+palabra[1:]
    return res

def seqNumber(leng=2,sep="."):
    i=0
    while True:
        i+=1
        res=str(i)
        while(len(res)<leng):
            res='0'+res
        yield res+sep

def renombrarFichero(fname,quitar,reemplazar,capitalizar,renumerar,number):
    nombre=fname
    if (fname.find(quitar)>=0):
        #nombre=quitaCadena(fname,quitar)
        if (quitar==' ' and capitalizar):
            index=0
            while (fname[index]==quitar):
                index+=1
            primeraLetra=fname[index]
            #print("primeraLetra: "+primeraLetra)
            nombre=string.replace(string.capwords(fname,' '),quitar,reemplazar)
            nombre=primeraLetra+nombre[1:]
        else:
            nombre=string.replace(fname,quitar,reemplazar)
    if (renumerar):
        nombre=number.next()+nombre
    if (nombre!=fname):
        print os.getcwd()+'\\'+fname,'-->',os.getcwd()+'\\'+nombre
        os.rename(fname,nombre)

def renombraRecursivo(quitar=' ',recursivo=False,reemplazar='',capitalizar=False,renumerar=False,todo=False):
    number=seqNumber()
    listDir=os.listdir(os.getcwd())
    listDir.sort()
    for fname in listDir:
        #print fname
        if (os.path.isdir(fname) and recursivo):
            os.chdir(fname)
            renombraRecursivo(quitar,recursivo,reemplazar,capitalizar,renumerar,todo)
            os.chdir("..")
            if (not todo): continue
        renombrarFichero(fname,quitar,reemplazar,capitalizar,renumerar,number)

        #if (fname!=quitaAcentos(fname)):
        #   print os.getcwd()+'\\'+fname,'-->',os.getcwd()+'\\'+quitaAcentos(fname)
        #   os.rename(fname,quitaAcentos(fname))
        #print(fname)

def uso():
    print("Uso: "+sys.argv[0]+" [-r] [-q <cadena-a-quitar>]")
    print("Uso: "+sys.argv[0]+" [-r] [-R <cadena-a-reemplazar> <reemplazo>]")
    print("-r recursivo")
    print("-q quitar <cadena-a-quitar> de los nombres de los ficheros en lugar de los espacios")
    print("-A actuar también sobre los directorios")
    print("-C capitalizar")
    print("-N renumerar los ficheros 01...0n")
      
recursivo=False
quitar=' ' 
reemplazar=''   
capitalizar=False
renumerar=False
todo=False
i=1

print "Opciones: "
while i <len(sys.argv):
    param=sys.argv[i]
    print("param: "+str(param))
    if (param=='-r'):
        print "Recursivo"
        recursivo=True
    elif (param=='-R'):
        i+=1;
        quitar=sys.argv[i];
        i+=1;
        reemplazar=sys.argv[i];
        print 'Reemplazar: '+quitar+' por '+reemplazar
    elif (param=='-q'):
        i+=1;
        quitar=sys.argv[i];
        print "Quitar: "+quitar
    elif (param=='-C'):
        capitalizar=True
        print "Capitalizar"
    elif (param=='-N'):
        renumerar=True
        print "Renumerar"
    elif (param=="-A"):
        todo=True
    elif (param=='-h' or param=='-help' or param=='--help'):
        uso();
        exit(-1);
    else: 
        break
    i+=1
while (i<len(sys.argv)):
    arch=sys.argv[i]
    if (os.path.isdir(arch)):
        os.chdir(arch)
    elif (os.path.isfile(arch)):
        renombrarFichero(arch, quitar, reemplazar, capitalizar, renumerar, seqNumber())
        if (i>=len(sys.argv)): sys.exit(0)
    i+=1
renombraRecursivo(quitar,recursivo,reemplazar,capitalizar,renumerar,todo)

