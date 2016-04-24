'''
Created on 09/02/2015

@author: usuario
'''

import os

from hashlib import sha256
from utilidades import *

BLOCK_SIZE=1024

#print sha256("hola").hexdigest()
vidFile="/l/cursos/crypto/3/6-2-Genericbirthdayattack(16min).mp4"
vidFile="/l/cursos/crypto/3/6-1-Introduction(11min).mp4"
videoSize = os.stat(vidFile).st_size
fvid=open(vidFile,"rb")
if (videoSize % BLOCK_SIZE==0):
   numBlocks=videoSize/BLOCK_SIZE
else:
   numBlocks=videoSize/BLOCK_SIZE+1
pos=(numBlocks-1)*BLOCK_SIZE
i=1
fvid.seek(pos)
block=fvid.read(BLOCK_SIZE)
lastHash=sha256(block).digest()
pos-=BLOCK_SIZE
while (pos>=0):
   fvid.seek(pos)
   block=fvid.read(BLOCK_SIZE)
   lastHash=sha256(block+lastHash).digest()
   pos-=BLOCK_SIZE
   i+=1
print lastHash.encode("hex")   
print pos
print i
fvid.close()      

print '----------------------------------------------------'

def f1(x,y):
   #enc=encAES(y,x)
   #return strxor(enc,y)
   return BlockEncAES(y,x) ^ y

def f2(x,y):
   #enc=encAES(x,x)
   #return strxor(enc,y)   
   return BlockEncAES(x,x) ^ y

x1=Block("bcbf217cb280cf30b2517052193ab979")
y1=Block("a17e9f69e4f25a8b8620b4af78eefd6f")
y2=Block("b0ddfe68d46f4f88c46e3b9892000c39")
#x2=decAES(y2,f1(x1,y1)+y2)
#x2=decAES(y2, strxor(f1(x1,y1),y2))
x2=BlockDecAES(y2, f1(x1,y1) ^ y2)
print f1(x1,y1)
print f1(x2,y2)

print "----------------------------------------------"   
   
x3=Block("bcbf217cb280cf30b2517052193ab979")
y3=Block("a17e9f69e4f25a8b8620b4af78eefd6f")

x4=Block("b0ddfe68d46f4f88c46e3b9892000c39")

y4=BlockEncAES(x3,x3) ^ y3 ^ BlockEncAES(x4,x4)
print f2(x3,y3)
print f2(x4,y4)

a=b=Block("00000000000000000000000000000000")
print BlockEncAES(a,b)
print eAES(str(a).decode("hex"),str(b).decode("hex")).encode("hex")
print encAES(str(a).decode("hex"),str(b).decode("hex")).encode("hex")
