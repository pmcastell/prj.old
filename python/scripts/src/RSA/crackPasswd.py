#!/usr/bin/python
# -*- coding: utf-8 -*-

# Fecha creación: 11 feb. 2018
# autor: usuario

import sys
from rsa import mkpasswd,misComb



if (sys.argv[1][:2]=="-v"):
    if (sys.argv[1]=="-v"):
        paso=100
    elif (sys.argv[1]=="-vv"):
        paso=10
    elif (sys.argv[1]=="-vvv"):
        paso=1
    del sys.argv[1]
else:
    paso=1000
shadow=open(sys.argv[1],"r")
#print("Inicio: "+mkpasswd("password","yP8NymU/"))
for user in shadow:
    hash=user[user.index(":")+1:]
    hash=hash[:hash.index(":")]
    salt=hash.split("$")[2]
    #print("Hash: "+hash)
    #print("Salt: "+salt)
    #print("Intentando: "+user)
    if (sys.argv[2]=="--words"):
        words=sys.argv[3]
        passdic=misComb(words)
    else:
        passdic=open(sys.argv[2],"r")
    i=0
    for w in passdic:
        if (w[-1:]=="\n"): w=w[:-1]
        if (i%paso==0):
            stri=str(i)
            stri=" "*(15-len(stri))+stri
            sys.stdout.write("\r"+stri+" Probando usuario: "+user[:user.index(":")]+" pass: "+w+" con salt: "+salt)
            sys.stdout.flush()
        i+=1
        #print("mkpasswd: "+mkpasswd(w,salt))
        if (mkpasswd(w,salt)==hash):
            print("\nEncontrado: "+user[:user.index(":")]+" --> "+w)
            break
    print("")
