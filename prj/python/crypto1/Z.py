'''
Created on 27/02/2015

@author: usuario
'''
import gmpy2,random

class Z(object):
    def __init__(self,N):
        self.N=N
        if gmpy2.is_prime(self.N):
        #if self.isPrime(N):
            self.prime=True
        else:
            self.prime=False
        #self.phi=self.__phi__()

    def add(self,x,y):
        return (x+y) % self.N
    
    def __pos__(self):
        return self.asterisk()
    
    def sub(self,x,y):
        return (x-y) % self.N
    
    def mul(self,x,y):
        return (x*y) % self.N
    
    def div(self,x,y):
        return int(gmpy2.divm(x,y,self.N))
    
    def pow(self,x,y):
        return int(gmpy2.powmod(x, y, self.N))

    def getN(self):
        return self.N
    
    def isqrt(self,n):
        x = n
        y = (x + 1) // 2
        while y < x:
            x = y
            y = (x + n // x) // 2
        return x    

    def isPrime(self,n=-1):
        if (n==-1):
            n=self.N
        l=self.isqrt(n)+1
        i=2
        while i <l+1:
            if (n % i == 0):
                return False
            i+=1
        return True          

    def gcdExt(self,a,b=-1):
        if (b == -1):
            b=self.N
        s=[1,0]; t=[0,1]
        while(b != 0):
            rem= a % b
            s0=s[0];s[0]=s[1]
            t0=t[0];t[0]=t[1]
            q=a/b
            s[1]=s0-q*s[1]
            t[1]=t0-q*t[1]
            a=b
            b=rem
        return [a,s[0],t[0]]
    
    def gcd(self,a,b=-1):
        return self.gcdExt(a, b)[0]
    
    def asterisk(self,sort=True):
        if (self.prime):
            return range(1,self.N)
        else:
            res=[1]
            for i in range(2,self.N):
                if (self.gcd(i) == 1):
                    res.append(i)
            if (sort):
                res.sort()
            return res
    
    def inversEuler(self,x):
        if (self.gcd(x) == 1):
            return gmpy2.powmod(x, self.phi-1, self.N)
        return ("Error clase Z: "+str(x)+" no tiene inverso en Z("+str(self.N)+")")
    
    def invers(self,x):
        iex=self.gcdExt(x)
        if (iex[0]==1):
            return self.add(iex[1],self.N)
        return ("Error clase Z: "+str(x)+" no tiene inverso en Z("+str(self.N)+")")
            
    def generate(self,g,sort=False):
        g%=self.N
        if (g==0):
            return []
        res=[1]
        e=0;i=1
        while(e!=1):
            e=int(gmpy2.powmod(g,i,self.N))
            if (e not in res):
                res.append(e)
            i+=1
        if (sort):
            res.sort()
        return res
    
    def generators(self):
        zn=range(1,self.N)
        res=[]
        for g in range(self.N):
            if (self.generate(g, sort=True)==zn):
                res.append(g)
        return res
                
    def solveLinearEq(self,a,b,c=0):
        return self.mul(-b+c,self.invers(a))      
    
    def solveQuadraticEq(self,a,b,c):
        sol=[]
        for x in range(self.N):
            if ((a*x*x+b*x+c) % self.N == 0):
                sol.append(x)
        return sol
                
    
    def __phi__(self):
        if self.prime:
            return self.N-1
        else:
            return len(self.asterisk())
    
    def order(self,g):
        for i in range(1,self.N):
            if (gmpy2.powmod(g,i,self.N)==1):
                return i
            
    def isQuadraticResidue(self,x):
        #return self.pow(x,(self.N-1)/2)==1
        x%=self.N
        i=1
        while i<self.N:
            if (gmpy2.powmod(i,2,self.N)==x):
                return True
            i+=1
        return False
    
    def quadraticResidue(self):
        sol=[]
        for x in range(self.N):
            if self.isQuadraticResidue(x):
                sol.append(x)
        return sol
    
    def squareRoot2(self,c):
        sol=[]
        for x in range(self.N):
            if ((x*x) % self.N == c):
                sol.append(x)  
        return sol
    
    def squareRoot(self,c):
        if (self.prime):
            if self.gcd(2, self.phi)==1:
                return Z(self.phi).invers(c)
            elif (self.N) % 4 == 3:
                return self.pow(c,(self.N+1)/4)
        else:
            return self.squareRoot2(c)
            
    def ithPrime(self,i):
        if (i==1): return 2
        j=3; k=2
        while j<self.N:
            jisPrime=self.isPrime(j)
            if jisPrime and k == i:
                return j
            elif jisPrime:
                k+=1
            j+=2
            
    
    def root(self,c,n):
        sol=[]
        for x in range(self.N):
            if (self.pow(x, n) == c):
                sol.append(x)
        return sol
    
    def pickRandomInvertible(self):
        while (True):
            g=random.randrange(2,self.N)
            if (self.gcd(g,self.N)==1):
                return g
        
    def pickRandomZ(self):
        return random.randrange(1,self.N)
    
    def sumaDigitos(self):
        d=self.N
        suma=0
        while(d!=0):
            suma+=d % 10
            d/=10
        return suma
    
    def divisible3(self):
        return self.sumaDigitos() % 3 == 0

def pruebas():
    z=Z(123456789123456789)
    print z.sumaDigitos()
    print z.divisible3()
    print z.div(z.N,3)
    print z.N / 3.0
    print z.N / 3      
    