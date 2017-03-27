#!/usr/bin/python2.7
# -*- coding: utf-8 -*-
'''
Created on 02/02/2015

@author: usuario
'''

from cryptoaes import AES
from Crypto.Util import Counter

def strxor(a,b):     # xor two strings of different lengths
    if len(a) > len(b):
        return "".join([chr(ord(x) ^ ord(y)) for (x, y) in zip(a[:len(b)], b)])
    else:
        return "".join([chr(ord(x) ^ ord(y)) for (x, y) in zip(a, b[:len(a)])])

def __xxor__(a,b):
    return strxor(a,b)

def hsxor(a,b):
    a=a.decode("hex")
    b=b.decode("hex")
    return strxor(a,b)

def random(size=16):
    return open("/dev/urandom").read(size)

def encrypt(key, msg):
    c = strxor(key, msg)
    #print
    #print c.encode('hex')
    return c

def isalpha(s):
    return len(s)==1 and ('A'<=s and s<='Z' or 'a'<=s and s<='z' or s==' ' or s=="." or s=="," or s=="'" or s=="(" or s==")")
    #return len(s)==1 and ('a'<=s and s<='z' or s==' ' or s=="." or s=="," or s=="'")

def eAES(k, m):
    e=AES.new(k)
    return e.encrypt(m)

def dAES(k, c):
    e=AES.new(k)
    return e.decrypt(c)

class Block(object):
    def __init__(self,b):
        try:
            b.decode("hex")
        except TypeError:
            b=b.encode("hex")
        self.b = b.decode("hex")
        self.blockSize = len(self.b)

    def __xor__(self,b2):
        return Block(strxor(self.b,b2.b).encode("hex"))
    
    def __add__(self,b2):
        return Block(self.b+b2.b)
    
    def __str__(self):
        return self.hexEnc()

    def pad(self):
        padding = (len(self.b)/16+1)*16 - len(self.b) 
        if (padding == 0):
            hexPad = chr(16)*16
        else:
            hexPad = chr(padding)*padding
        return Block(self.b+hexPad)

    def isHex(self, b):
        hexDigits="0123456789aAbBcCdDeEfF"
        for i in range(len(b)):
            if (b[i] not in hexDigits):
                return False
        return True
    
    def getBlockSize(self):
        return self.blockSize
    
    def hexEnc(self):
        return self.b.encode("hex")
    
    def hexDec(self):
        return self.b.decode("hex")
    
    def eAES(self, k):
        #return Block(eAES(k.b,self.b).encode("hex"))
        if (type(k)==str):
            if (len(k)==self.blockSize*2):
                k=k.decode("hex")
        elif (type(k)==Block):
            k=str(k).decode("hex")
        return Block(AES.new(k).encrypt(self.b).encode("hex"))
    
    def dAES(self, k):
        #return Block(dAES(k.b,self.b).encode("hex"))
        if (type(k)==str):
            if (len(k)==self.blockSize*2):
                k=k.decode("hex")
        elif (type(k)==Block):
            k=str(k).decode("hex")
        return Block(AES.new(k).decrypt(self.b).encode("hex"))

    def subBlock(self, n,n2=-1):
        if (n2==-1):
            n2=n
        if (n2*16>len(self.b) or n>n2):
            raise IndexError("Bloque fuera de Rango.")
        else:
            res=Block("")
            return Block(self.b[n*16:(n2+1)*16])

    def intVal(self):
        res=0
        for i in range(len(self.b)):
            res*=256
            res+=ord(self.b[i])
        return res
    
    def inc(self):
        listb=list(self.b)
        i=len(listb)-1
        carry=1
        while (i>=0 and carry==1):
            c=ord(listb[i])
            if (c==255):
                c=0
            else:
                c+=1
                carry=0
            listb[i]=chr(c)
            i-=1
        self.b="".join(listb)

    def append(self, b):
        self.b += b.b
        self.blockSize += b.blockSize

    def encAEScbc(self, k):
        selfPad=self.pad()
        IV = selfPad.subBlock(0)
        res = IV.clone()
        for i in range(1, selfPad.getBlockSize()/16):
            IV = (IV ^ selfPad.subBlock(i)).eAES(k)
            res.append(IV)
        return res

    def decAEScbc(self, k):
        IV = self.subBlock(0)
        res = Block("")
        for i in range(1, self.getBlockSize()/16):
            d = self.subBlock(i)
            res.append(d.dAES(k) ^ IV)
            IV = d
        return res

    def encAESctr(self,k):
        IV = self.subBlock(0)
        res = Block("")
        for i in range(1, (self.getBlockSize()+(16-1))/16):
            res.append(self.subBlock(i) ^ IV.eAES(k))
            IV.inc()
        return res
                       
    def decAESctr(self,k):
        return self.encAESctr(k)

    def replace(self,pos,value):
        self.b=self.b[:pos]+chr(value)+self.b[pos+1:]
        
    def get(self,i):
        return self.b[i]
    
    def getByte(self,i):
        return ord(self.get(i))
    
    def clone(self):
        return Block(self.b.encode("hex"))

    # def decAESCBC(self, k, IV="00000000000000000000000000000000"):
    #     IV=str(IV).decode("hex")
    #     k=str(k).decode("hex")
    #     obj = AES.new(k, AES.MODE_CBC, IV)
    #     return obj.decrypt(self.b)
    # def encAESCBC(self,k,IV="00000000000000000000000000000000"):
    #     IV=str(IV).decode("hex")
    #     k=str(k).decode("hex")
    #     obj = AES.new(k, AES.MODE_CBC, IV)
    #     return obj.encrypt(self.b)

    # def __init__(self, b, blockSize=16):
    #     if (self.isHex(b)):
    #         if (len(b) % 16 == 0):
    #
    #     self.blockSize=blockSize
    #     if (len(b)==self.blockSize):
    #         self.b=b
    #     elif (len(b)==2*self.blockSize):
    #         self.b=str(b).decode("hex")
    #     else:

        # else:
        #     raise TypeError(u"Clase Block: Error en cadena de inicialización")

#    def __init__(self, b, format="hex"):
#        if (format != "hex"):
#            b = b.encode("hex")
#        if (len(b) % (2*16) != 0):
#            b = self.pad(b)
#        self.b = b.decode("hex")
#        self.blockSize = len(self.b)
    


def BlockEncAES(bk,bm):
    return bm.eAES(bk)

def BlockDecAES(bk,bc):
    return bc.dAES(bk)

def d_AES_nbl_bin(key, ciphertext):
    d=AES.new(key,AES.MODE_CBC,ciphertext[:16])
    return d.decrypt(ciphertext[16:])

def d_AES_CTR_nbl_bin(key, ciphertext):
    ctr=Counter.new(16*8, initial_value=long(ciphertext[:16].encode("hex"), 16))
    d=AES.new(key, AES.MODE_CTR, counter = ctr)
    return d.decrypt(ciphertext[16:])

#a = Block("00000000000000000000000000000000")
#print(a.hexEnc())
#k = Block("00000000000000000000000000000000")
#print(a, "\n", k)
#b = a.eAES(k)
#c = b.dAES(k)
#print(b)
#print(c)


#bt = Block(t)
#btpad = bt.pad()
#print(btpad)
#------------------------------------------------------------------------------ 
#----------------------------------------------- b=Block("holaholaholaholahola")
#------------------------------------------------------------- print(b.intVal())
#----------------------------------------------------------------------- b.inc()
#------------------------------------------------------------- print(b.intVal())
#----------------------------------------------------------------- print(str(b))
#--------------------------------------------------- print(str(b).decode("hex"))