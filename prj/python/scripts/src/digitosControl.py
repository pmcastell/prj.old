#!/usr/bin/env python
# -*- coding: utf-8 -*-

'''
Created on 04/02/2010

@author: usuario
'''
import sys;
def cerosIzquierda(cadena,longitud):
    cadena=str(cadena)
    for i in range(0,longitud-len(cadena)):
        cadena="0"+cadena
    return cadena
        
def digitosControlCuenta(entidad,oficina,cuenta):
    #pesos=new Array(6,3,7,9,10,5,8,4,2,1);
    if (len(entidad)!=4):
        return u"Error la entidad debe tener 4 dígitos"
    if (len(oficina)!=4):
        return u"Error la oficina debe tener 4 dígitos"
    if (len(cuenta)!=10):
        return u"Error la cuenta debe tener 10 dígitos"
    pesos=[1,2,4,8,5,10,9,7,3,6];
    dc1=dc2=0;
    entidadOficina="00"+cerosIzquierda(entidad,4)+cerosIzquierda(oficina,4);
    cuenta=cerosIzquierda(cuenta,10);
    for i in range(0,len(pesos)):
       dc1+=pesos[i]*int(entidadOficina[i]);
       dc2+=pesos[i]*int(cuenta[i]);
    print "d1: "+str(dc1)+" d2: "+str(dc2);
    dc1=11-dc1%11; dc2=11-dc2%11;
    if (dc1==11):
       dc1=0;
    elif (dc1==10):
       dc1=1;
    if (dc2==11):
       dc2=0;
    elif (dc2==10):
       dc2=1;
    return str(dc1)+str(dc2);

if (len(sys.argv)<4):
    print "Uso: "+sys.argv[0]+" <Entidad> <Oficina> <Cuenta";
else:    
    print digitosControlCuenta(sys.argv[1],sys.argv[2],sys.argv[3]);
