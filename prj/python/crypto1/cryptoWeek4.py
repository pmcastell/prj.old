'''
Created on 15/02/2015

@author: usuario
'''


import urllib2
import sys
from utilidades import *

TARGET = 'http://crypto-class.appspot.com/po?er='
#--------------------------------------------------------------
# padding oracle
#--------------------------------------------------------------
class PaddingOracle(object):
    def query(self, q):
        target = TARGET + urllib2.quote(q)    # Create query URL
        req = urllib2.Request(target)         # Send HTTP request to server
        try:
            f = urllib2.urlopen(req) # Wait for response
        except urllib2.HTTPError, e:          
            #print "We got: %d" % e.code       # Print response code
            #------------------------------------------------- if e.code == 404:
                #------------------------------------ return True # good padding
            #---------------------------------------- return False # bad padding
            return e.code
        except e:
            return e.code

###if __name__ == "__main__":
###   po = PaddingOracle()
###   po.query(sys.argv[1])       # Issue HTTP query with the given argument

def q1():
    IV =Block("20814804c1767293b99f1d9cab3bc3e7")
    IV5=Block("00000000000000003500000000000000")
    IV1=Block("00000000000000003100000000000000")
#   pth=Block("50617920426f62203130302400000000")
#   pt =      "P a y   B o b   1 0 0 $"
#   ct =Block("ac1e37bfb15599e5f40eef805488281d")
    print IV
    print IV ^ IV5 ^ IV1

def encadena(listaBloques,n):
    b=listaBloques[0].clone()
    for i in range(1,n+2):
        b.append(listaBloques[i])
    return b

q1()

po = PaddingOracle()
url="http://crypto-class.appspot.com/po?er="
ct="f20bdba6ff29eed7b046d1df9fb7000058b1ffb4210a580f748b4ac714c001bd4a61044426fb515dad3f21f18aa577c0bdf302936266926ff37dbf7035d5eeb4"
c0=Block(ct[0:32])
c1=Block(ct[32:64])
c2=Block(ct[64:96])
c3=Block(ct[96:128])
bct=Block(ct)

res=['','','','','','','','','','','','','','','','']
lc=[c0,c1,c2,c3]
b=2
cc=lc[b].clone()
m=Block("The Magic Words are Squeamish Ossifrage").pad()
for pos in range(15,-1,-1):
    pad=16-pos
    lc[b]=cc.clone()
    for k in range(1,pad):
        lc[b].replace(pos+k, lc[b].getByte(pos+k)^ord(res[pos+k])^pad)
    encontrado=False
    charNone=-1
    for i in range(256):
        lc[b].replace(pos,cc.getByte(pos)^i^pad)
        q=encadena(lc,b)
        print "bloque no moodif.: "+str(bct.subBlock(2))
        print "bloque modificado: "+str(q.subBlock(2))
        print "mensaje original:  "+str(m.subBlock(2))
        print "bloque desencip:   "+str(m.subBlock(2) ^ bct.subBlock(2))
        print "resulltado:        "+str(m.subBlock(2) ^ bct.subBlock(2) ^ q.subBlock(2))
        #print q.subBlock(2) ^ m.subBlock(2)
        print "---------------------------"
        code=po.query(str(q))
        if (code == 404):
            res[pos]=chr(i)
            encontrado=True
            print res
            break
        if (code == None): #esto ocurre porque no hay error de padding ni de mac
            charNone=i
        if (type(code) != int):
            print code
    if (not encontrado and charNone>=0):
        res[pos]=chr(charNone)
    elif (not encontrado):
        res[pos]=0
print res      
         
   
#po.query(url+ct)       # Issue HTTP query with the given argument

