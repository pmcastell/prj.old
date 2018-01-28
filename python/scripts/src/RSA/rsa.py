#!/usr/bin/python
# -*- coding: utf-8 -*-

# Fecha creación: 28 ene. 2018
# autor: usuario


def primo(n,debug=False):
    import math
    r=int(math.sqrt(n))+1
    i=2
    while (i<r):
        if float(n)/i == int(n/i):
            #print(str(n)+" es divisible por "+str(i))
            return False
        i+=1
        if (debug and i%100000==0):
            print i
    #print(str(n)+" es Primo.")
    return True


def inverso(e,z):
    for d in range(2,z):
        if (e*d%z==1):
            return d
    print("Error, no se ha podido encontrar d.")

def modulo(p,q):
    if (not primo(p)):
        print(str(p)+" no es Primo.")
        return None
    if (not primo(q)):
        print(str(q)+" no es Primo.")
        return None
    return p*q

def fiden(p,q):
    return (p-1)*(q-1)

def claves(p,q):
    n=modulo(p,q)
    if (n==None): return
    z=(p-1)*(q-1)
    e=z-1;
    while(e>0):
        if (primo(e)):
            d=inverso(e,z)
            if (d!=e):
                break
        e-=1
    print((e,n))
    print((d,n))
