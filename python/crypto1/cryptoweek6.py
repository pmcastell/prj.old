'''
Created on 27/02/2015

@author: usuario
'''
import gmpy2
from gmpy2 import mpz
from Z import Z

def q5():

    p=5
    q=17
    N=p*q
    z=Z(p*q)
    z_asterisk=z.asterisk(True)
    print z_asterisk
    pka=[N,3]
    ska=[N,43]
    pkb=[N,5]
    skb=[N,13]
    
    x=3*ska[1]-1
    print x
    g=z.pickRandomInvertible()
    i=1
    while (True):
        y=z.pow(g,x/i)
        if y!=1 and ( ( y%p==1 and y%q==(-1+q) ) or ( y%q==1 and y%p==(-1+p) ) ):
            print y,' ',i,' ',g
            break
        elif y!=1:
            g=z.pickRandomInvertible()
            i=1 
        else:
            i*=2
            if (x/i % 2 != 0):
                g=z.pickRandomInvertible()
                i=1
            print "elevando a x/i: ",x/i
    
    print "y: ",y    
    print z.gcd(N,y*y-1)
    print z.gcd(N, y)
    print z.gcd(N, y-1)
    print z.gcd(N-1, y)      
    
def q6():
    p=23
    q=17
    r=19
    z=Z(p*q*r)
    print z.asterisk(True)
    print z.phi
    print (p-1)*(q-1)*(r-1)
    
def q7():
    p=5
    q=17
    z=Z(p*q)
    #s=z.pickRandomInvertible()
    s=47
    print "s: ",s
    i=5;j=6;t=9;
    ri=z.ithPrime(i);si=z.pow(s,ri)
    rj=z.ithPrime(j);sj=z.pow(s,rj)
    rt=z.ithPrime(t);st=z.pow(s,rt)
    k=z.pow(s,ri*rj*rt)
    print k
    print ri,rj,rt
    gcde=z.gcdExt(ri,rj)
    a=gcde[1]
    b=gcde[2]
    print gcde
    print a,b
    print z.mul(z.pow(si,a),z.pow(sj,b))
    
def q10():
    p=23
    z=Z(p)
    g=7
    x=z.pickRandomZ()
    y=z.pickRandomZ()
    print "x: ",x,"y: ",y
    a=b=5
    A0=z.pow(g,x)
    A1=z.pow(g,y)
    A2=z.pow(g,x*y+a)
    r=z.pickRandomZ()
    s=z.pickRandomZ()
    print "r: ",r,"s: ",s
    B11=z.mul(z.pow(A1,r),z.pow(g,s))
    B22=z.mul(z.pow( z.div( A2, z.pow(g,b) ) , r ),z.pow(A0,s))
    B1=( A1**r*g**s ) % p
    gb=z.pow(g,b)
    A2divgb=z.div(A2,gb)
    A2divgbpowr=z.pow(A2divgb,r)
    A0pows=z.pow(A0,s)
    B2=z.mul(A2divgbpowr,A0pows)
    
    #B2=( (z.div(A2,g**b%p))**r*A0**s ) %p
    print "A0:",A0,"A1:",A1,"A2:",A2
    print "B1:",B1,"B2:",B2
    print "B11:",B11,"B22:",B22
    print z.div(B1,z.pow(B2,x)) == 1
    print z.mul(B2,z.pow(B1,x)) == 1
    print z.div(B2,z.pow(B1,x)) == 1
    print z.mul(B1,z.pow(B2,x)) == 1

def isqrt(n):
    x = n
    y = (x + 1) // 2
    while y < x:
        x = y
        y = (x + n // x) // 2
    return x    
    
def factoringChallenge1():
    N = int("17976931348623159077293051907890247336179769789423065727343008115\
77326758055056206869853794492129829595855013875371640157101398586\
47833778606925583497541085196591615128057575940752635007475935288\
71082364994994077189561705436114947486504671101510156394068052754\
0071584560878577663743040086340742855278549092581")
    A=isqrt(N)+1 #N no puede tener raiz cuadrada exacta pues es producto de p*q primos distintos
    x=isqrt(A*A-N)
    print A-x
    
   
def factoringChallenge2():
    N = int("6484558428080716696628242653467722787263437207069762630604390703787\
9730861808111646271401527606141756919558732184025452065542490671989\
2428844841839353281972988531310511738648965962582821502504990264452\
1008852816733037111422964210278402893076574586452336833570778346897\
15838646088239640236866252211790085787877")
    A=isqrt(N) #N no puede tener raiz cuadrada exacta pues es producto de p*q primos distintos
    i=1
    while (i<2**20):
        A+=1
        x=isqrt(A*A-N)
        if (A-x)*(A+x) == N:
            print A-x
            break
        i+=1

def factoringChallenge33():
    N = int("72006226374735042527956443552558373833808445147399984182665305798191\
63556901883377904234086641876639384851752649940178970835240791356868\
77441155132015188279331812309091996246361896836573643119174094961348\
52463970788523879939683923036467667022162701835329944324119217381272\
9276147530748597302192751375739387929")
    A=isqrt(6*N)+1 #N no puede tener raiz cuadrada exacta pues es producto de p*q primos distintos
    x=isqrt(A*A-6*N)
    print x
    p=(A-x)/3
    q=(A+x)/2
    while p*q!=N:
        if p*q>N:
            p-=2
        else:
            p+=2
        print N-p*q

def factoringChallenge3():
    N = int("72006226374735042527956443552558373833808445147399984182665305798191\
63556901883377904234086641876639384851752649940178970835240791356868\
77441155132015188279331812309091996246361896836573643119174094961348\
52463970788523879939683923036467667022162701835329944324119217381272\
9276147530748597302192751375739387929")
    N24=24*N
    #(A-x)*(A+x)=6p*4q=24N
    A=isqrt(N24)+1
    x=isqrt(A*A-N24)
    p=(A-x)/6
    q=(A+x)/4
    print p*q==N
    print p

def factoringChallenge4():
    N = int("17976931348623159077293051907890247336179769789423065727343008115\
77326758055056206869853794492129829595855013875371640157101398586\
47833778606925583497541085196591615128057575940752635007475935288\
71082364994994077189561705436114947486504671101510156394068052754\
0071584560878577663743040086340742855278549092581")
    A=isqrt(N)+1 #N no puede tener raiz cuadrada exacta pues es producto de p*q primos distintos
    x=isqrt(A*A-N)
    p=A-x
    q=A+x
    phiN=(p-1)*(q-1)
    e=65537
    d=gmpy2.invert(e,phiN)
    ct=22096451867410381776306561134883418017410069787892831071731839143676135600120538004282329650473509424343946219751512256465839967942889460764542040581564748988013734864120452325229320176487916666402997509188729971690526083222067771600019329260870009579993724077458967773697817571267229951148662959627934791540
    t=gmpy2.powmod(ct, d, N)
    ct2=gmpy2.powmod(t,e,N)
    if ct != ct2:
        print "Error encrypting or decrypting."
        return
    #for b in lt:
    #    print hex(b
    block=""+hex(t)
    print block[block.find("00")+2:].decode("hex")

print "---------------------------------------------"
factoringChallenge1()
print "---------------------------------------------"
factoringChallenge2()
print "---------------------------------------------"
factoringChallenge3()
print "---------------------------------------------"
factoringChallenge4()    
print "---------------------------------------------"