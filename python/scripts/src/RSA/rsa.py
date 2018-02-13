#!/usr/bin/python
# -*- coding: utf-8 -*-

# Fecha creación: 28 ene. 2018
# autor: usuario
from string import maketrans

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
    return(crypt.crypt(PASS,"$6$"+SALT))

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

def base64Enc(s):
  # the result/encoded string, the padding string, and the pad count
  base64chars="./0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"
  #base64chars="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
  r = ""; 
  p = ""; 
  c = len(s) % 3;

  # add a right zero pad to make this string a multiple of 3 characters
  if (c > 0):
    while (c<3):
      p += '='; 
      s += "\0";
      c+=1; 

  # increment over the length of the string, three characters at a time
  c=0
  while(c<len(s)):
    # we add newlines after every 76 output characters, according to the MIME specs
    #if (c > 0 and (c / 3.0 * 4) % 76 == 0):
    #  r += "\r\n"; 

    # these three 8-bit (ASCII) characters become one 24-bit number
    #n = (s.charCodeAt(c) << 16) + (s.charCodeAt(c+1) << 8) + s.charCodeAt(c+2);
    n = (ord(s[c])<<16) + (ord(s[c+1])<<8) + (ord(s[c+2]));

    # this 24-bit number gets separated into four 6-bit numbers
    n = [(n >> 18) & 63, (n >> 12) & 63, (n >> 6) & 63, n & 63];

    # those four 6-bit numbers are used as indices into the base64 character list
    r += base64chars[n[0]] + base64chars[n[1]] + base64chars[n[2]] + base64chars[n[3]];
    c+=3
   # add the actual padding string, after removing the zero pad
  #return r.substring(0, r.length - p.length) + p;
  return r[0:len(r)-len(p)]+p

def mkpasswd2(passwd, salt):
    #usuario:$6$yP8NymU/$rH/Bl2PnTleI07hfUqvk31.7BvAogs1dtXrU1/RktvfDG9dbf/SVh3jo3UhSf.VIrB7hh7n3zQGSNsgqOGiY5/:16498:0:99999:7:::
    import hashlib, base64
    #if (len(salt)<len(passwd)):
    #    salt+=salt[:len(passwd)-len(salt)]
    #elif (len(passwd)<len(salt)):
    #    salt=salt[:len(passwd)-len(salt)]
    print("passwd: "+passwd+" salt: "+salt)
    #pasSalt=stradd2(passwd,salt)
    pasSalt=passwd+salt;
    #pasSalt=salt+passwd;
    passHash=hashlib.sha512(pasSalt).digest()
    return base64Enc(passHash)
    #b64Orig=base64.encodestring(passHash)
    #print("b64Orig: "+b64Orig);
    #base64fixTable = maketrans(
    #"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/",
    #"./0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz");
    #return b64Orig.translate(base64fixTable)

#print(mkpasswd2("18mariecurie67","yP8NymU/"))
#r="a"
#while(len(r)<100):
#    print(base64Enc(r))
#    r+="a"
def combinaciones(letters,rep):
    import itertools
    return itertools.product(letters,repeat=rep)


def permutaciones(letters,rep):
    import itertools
    return itertools.permutations(letters,rep)

def misComb(letters):
    if (type(letters)==int):
        letters=str(letters)
    elif (type(letters)==tuple or type(letters)==list):
        letters="".join(letters)
    for i in range(len(letters)):
        comb=combinaciones(letters,i+1)
        while(True):
            try:
                next=comb.next()
            except:
                break
            yield "".join(next)
        
        
