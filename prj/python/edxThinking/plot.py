'''
Created on 03/11/2014

@author: usuario
'''
import pylab, math

def dibuja(mf,x1=-10,x2=10,inc=0.01):
   pylab.figure(1)
   maxy=0
   miny=0
   #ejex()
   pylab.plot([x1,x2],[0,0])
   while(x1<=x2):
      for f in mf:
         try:
            y1=f(x1)
            y2=f(x1+inc)
            if (y1<miny):
               miny=f(x1)
            if (y1>maxy):
               maxy=f(x1)
            pylab.plot([x1,x1+inc],[y1,y2])
         except ZeroDivisionError:
            print ("Encontrada Singularidad en la funci&oacute;n "+str(f)+" en "+str(x1))
         
      x1+=inc
   
   #ejey(miny,maxy)
   pylab.plot([0,0],[miny-1,maxy+1])
   pylab.show()

def x2(x):
   return x*x

def x3(x):
   return x*x*x

def x4(x):
   return x*x*x*x

def sample(x):
   return math.sin(x)/x

#dibuja([x2,x3,x4,sample])


import random
xVals = []
yVals = []
wVals = []
for i in range(1000):
   xVals.append(random.random())
   yVals.append(random.random())
   wVals.append(random.random())
xVals = pylab.array(xVals)
yVals = pylab.array(yVals)
wVals = pylab.array(wVals)
xVals = xVals + xVals
zVals = xVals + yVals
tVals = xVals + yVals + wVals
#pylab.figure(1)

#pylab.plot(sorted(xVals), sorted(yVals))
#pylab.show()



def sampleQuizzes():
   students = 0.0
   for i in range(10000):
      mid1 = random.randrange(50,81)
      mid2 = random.randrange(60,91)
      finalEx = random.randrange(55,96)
      finalGrade = mid1*0.25 + mid2*0.25 + finalEx*0.50
      if finalGrade >= 70 and finalGrade <= 75:
         students += 1.0
   return students/10000


def plotQuizzes():
   scores = generateScores(10000)
   pylab.hist(scores,bins = 7)
   pylab.title('Distribution of Scores')
   pylab.xlabel('Final Score')
   pylab.ylabel('Number of Trials')
   pylab.show()
   
def MC(lista,k=10):
   
   pos=int(random.random()*1000)
   print pos
   numTries=1
   for i in range(10):
      if (lista[pos]=='W'):
         return numTries
      else:
         numTries+=1
         pos=(pos+1) % len(lista)
   return 0
      

def LV():
   numTries=0
   while True:
      r=random.random()
      print(r)
      numTries+=1
      if (r<0.5):
         return numTries
         

def probTest(limit):
   prob = 1.0
   n = 1
   while prob > limit:
      prob = (5.0/6)**(n-1)*1/6.0
      n += 1
   return n-1

lista=[]
for i in range(1000):
   r=random.random()
   if (r<0.5):
      lista.append('W')
   else:
      lista.append('B')
print(len(lista))
print(lista)
histogram = [ 0 for i in range(1,1000)]  # intialize the list to be all zeros
for i in range(1000):
   result = MC(lista)
   histogram[ result ] += 1
pylab.figure(1)
pylab.plot(histogram)
pylab.xlabel("histo")
pylab.show()