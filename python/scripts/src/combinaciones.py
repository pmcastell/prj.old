#!/usr/local/bin/python
'''
Created on 24/09/2014

@author: usuario
'''
import sys
def imprime(cadena,pos):
    for i in range(len(pos)):
        sys.stdout.write(cadena[pos[i]])
    print
    
def permutaciones(cadena):
    pos=[]
    l=len(cadena)
    for i in range(l):
        pos.insert(i,0)
    #while(pos[i]<l):
        
       
def combinaciones(cadena,lon):
    pos=[]
    l=len(cadena)
    for i in range(lon):
        pos.insert(0,0)
    while(pos[0]<l):
        imprime(cadena,pos)
        i=lon-1
        pos[i]+=1
        while (i>0 and pos[i]>=l):
            pos[i]=0
            pos[i-1]+=1
            i-=1
            
        #for i in range(l-1,0,-1):
        #    pos[i]+=1
        #    if (pos[i])>=ll:
        #        pos[i]=0
        #        pos[i-1]+=1
        #     else:
        #        break
if (len(sys.argv)<3):
    print "uso",sys.argv[0]," <cadena> <longitud>"
else:        
    combinaciones(sys.argv[1],int(sys.argv[2]))
