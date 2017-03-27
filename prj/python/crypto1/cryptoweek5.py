'''
Created on 22/02/2015

@author: usuario
'''
import gmpy2, Z
from math import ceil,sqrt
from gmpy2 import mpz

def qprg():
    p=mpz("134078079299425970995740249982058461274793658205923933\
    77723561443721764030073546976801874298166903427690031\
    858186486050853753882811946569946433649006084171")
    g=mpz("11717829880366207009516117596335367088558084999998952205\
    59997945906392949973658374667057217647146031292859482967\
    5428279466566527115212748467589894601965568")
    h=mpz("323947510405045044356526437872806578864909752095244\
    952783479245297198197614329255807385693795855318053\
    2878928001494706097394108577585732452307673444020333")
    B=mpz(2**20)
    gB=gmpy2.powmod(g,B,p)
    d={}
    x1=0
    while(x1<=2**20):
        d[gmpy2.divm(h, gmpy2.powmod(g,x1,p),p)]=x1
        x1+=1
    x0=0
    while(x0<=2**20):
        k=gmpy2.powmod(gB,x0,p)
        if (k in d):
            x1=d[k]
            break
        x0+=1
        
    
    print "x0: "+str(x0)+" x1: "+str(x1)
    x=mpz(x0*(2**20)+x1)
    print x
    print gmpy2.powmod(g,x,p) 
    print h
    print h-gmpy2.powmod(g,x,p) 


def inv7():
    a=1
    while (True):
        if (7*a % 23 ==1 ):
            print a
            break
        else:
            a+=1
            print a

def isPrime(n):
    l=int(ceil(sqrt(n)))
    for i in range(2,l+1):
        if (n % i == 0):
            return False
    return True          
            
            
def q11():    
    print Z(13).generators()

    
def q12():
    z=Z(23)
    for x in range(23):
        v=(z.pow(x, 2)+z.mul(4,x)+1) % z.getN()
        if ( v == 0):
            print x      
def q13():
    z=Z(19)
    for x in range(19):
        if (z.pow(x,11)== 2):
            print x
            
def q15(p):
    for i in Z(p).generators():
        print Z(p).generate(i, sort=True)
    print Z(p).generators()        
    print Z(p).asterisk(True)
    print Z(p-1).phi
    
def q5():    
    print Z(23).gcdExt(7,23)
    print Z(23).invers(7)

def pruebas():
    z=Z(6)
    for i in range(1,6):
        print "inverso de "+str(i)+": "+str( z.invers(i))        
    print z.asterisk()
    print z.phi    
    print z.gcdExt(5)
    z=Z(13)
    for i in range(1,13):
        gcdex=z.gcdExt(i)
        inv=gcdex[1]
        while (inv<0):
            inv+=z.getN()
        print str(i)+":"+str(gcdex)+" - "+str(inv)+" - "+str(i*inv % z.getN())
    z=Z(7)
    print z.generate(10)
    print z.generate(2)
    z=Z(35)
    print z.generate(2)
    print z.order(2)
    print z.asterisk(True)
    print z.phi
    
    z=Z(11)
    for i in range(z.getN()):
        if (z.isQuadraticResidue(i)):
            print i


def pr():    
    print Z(11).quadraticResidue()
    for x in range(11):
        print str(x)+"^1/2 = "+str(Z(11).squareRoot(x))
        
    for i in Z(11).quadraticResidue():
        print str(i),":",;print Z(11).squareRoot(i),; print " | ",;print Z(11).squareRoot2(i)

    print Z(40).phi
    print Z(40).asterisk(True)
    print Z(14).quadraticResidue()
