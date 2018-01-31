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

def mkpasswd(PASS,SALT):
    import crypt
    print(crypt.crypt(PASS,"$6$"+SALT))

def strxor(a,b):     # xor two strings of different lengths
    if len(a) > len(b):
        return "".join([chr(ord(x) ^ ord(y)) for (x, y) in zip(a[:len(b)], b)])
    else:
        return "".join([chr(ord(x) ^ ord(y)) for (x, y) in zip(a, b[:len(a)])])
    
def stradd(a,b):
    if (len(a)>len(b)):
        return "".join([chr((ord(x) + ord(y)) % 256) for (x,y) in zip(a[:len(b)],b)])
    else:
        return "".join([chr((ord(x) + ord(y)) % 256) for (x,y) in zip(a,b[:len(a)])])    


def stradd2(a,b):
    i=0
    res=""
    while(i<len(a) and i<len(b)):
        res+=chr( ord(a[i]) ^ ord(b[i])  )
        i+=1
    while(i<len(a)):
        res+=a[i]
        i+=1
    return res

def mkpasswd2(passwd, salt):
    #usuario:$6$yP8NymU/$rH/Bl2PnTleI07hfUqvk31.7BvAogs1dtXrU1/RktvfDG9dbf/SVh3jo3UhSf.VIrB7hh7n3zQGSNsgqOGiY5/:16498:0:99999:7:::
    import hashlib, base64
    if (len(salt)<len(passwd)):
        salt+=salt[:len(passwd)-len(salt)]
    elif (len(passwd)<len(salt)):
        salt=salt[:len(passwd)-len(salt)]
    print("passwd: "+passwd+" salt: "+salt)
    pasSalt=stradd2(passwd,salt)
    passHash=hashlib.sha512(pasSalt).digest()
    return base64.encodestring(passHash)
    
    