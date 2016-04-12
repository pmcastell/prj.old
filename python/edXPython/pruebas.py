import math
def myLog(x, b):
    '''
    x: a positive integer
    b: a positive integer; b >= 2

    returns: log_b(x), or, the logarithm of x relative to a base b.
    '''
    # Your Code Here
    if (x<0):
        return None
    if (b<2):
        return None
    result=1
    logarithm=0
    while (result<x):
        if (result*b>x):
            return logarithm
        else:
            result*=b
            logarithm+=1
    return logarithm

def laceStrings(s1, s2):
    """
    s1 and s2 are strings.

    Returns a new str with elements of s1 and s2 interlaced,
    beginning with s1. If strings are not of same length, 
    then the extra elements should appear at the end.
    """
    i=0
    l1=len(s1)
    l2=len(s2)
    res=''
    while (i<l1 and i<l2):
        res+=s1[i]+s2[i]
        i+=1
    while (i<l1):
        res+=s1[i]
        i+=1
    while (i<l2):
        res+=s2[i]
        i+=1
    return res

def laceStringsRecur(s1, s2):
    """
    s1 and s2 are strings.

    Returns a new str with elements of s1 and s2 interlaced,
    beginning with s1. If strings are not of same length, 
    then the extra elements should appear at the end.
    """
    def helpLaceStrings(s1, s2, out):
        if s1 == '':
            return s2
        if s2 == '':
            return s1
        else:
            return s1[0]+s2[0]+helpLaceStrings(s1[1:],s2[1:],'')
    return helpLaceStrings(s1, s2, '')

def McNuggets(n):
    """
    n is an int

    Returns True if some integer combination of 6, 9 and 20 equals n
    Otherwise returns False.
    """
    for a in range(n+1):
        for b in range(n+1):
            for c in range(n+1):
                if (6*a+9*b+20*c==n):
                    return True
    return False
def square(x):
    return x*x-4
def fixedPoint(f, epsilon):
    """
    f: a function of one argument that returns a float
    epsilon: a small float
  
    returns the best guess when that guess is less than epsilon 
    away from f(guess) or after 100 trials, whichever comes first.
    """
    guess = 1.0
    for i in range(100):
        if abs(f(guess) - guess) < epsilon:
            return guess
        else:
            guess = f(guess)
    return guess

def babylon(a):
    def test(x):
        return 0.5 * ((a / x) + x)
    return test

def sqrt(a):
    return fixedPoint(babylon(a), 0.0001)

#print sqrt(4)

def swapSort(L): 
    """ L is a list on integers """
    print "Original L: ", L
    for i in range(len(L)):
        for j in range(i+1, len(L)):
            if L[j] < L[i]:
                # the next line is a short 
                # form for swap L[i] and L[j]
                L[j], L[i] = L[i], L[j] 
                print L
    print "Final L: ", L
    
def modSwapSort(L): 
    """ L is a list on integers """
    print "Original L: ", L
    for i in range(len(L)):
        for j in range(len(L)):
            if L[j] < L[i]:
                # the next line is a short 
                # form for swap L[i] and L[j]
                L[j], L[i] = L[i], L[j] 
                print L
    print "Final L: ", L    
    
def genPrimes():
    def isPrime(n):
        if (n%2==0):
            return False
        i=3
        while (i<=math.ceil(math.sqrt(n))):
            if (n%i==0):
                return False
            i+=2
        return True
    
    yield 2
    n=3
    while(True):
        if (isPrime(n)):
            yield n
        n+=2

#prime=genPrimes()                       
#for i in range(78498):
#    print prime.next()
 
