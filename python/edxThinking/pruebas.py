'''
Created on 27/11/2014

@author: usuario
'''
import random
def genEven():
   '''
    Returns a random even number x, where 0 <= x < 100
   '''
   while(True):
      r=random.randint(0,99)
      if (r%2==0):
         return r

def f():
   mylist = []
   
   for i in xrange(random.randint(1, 10)):
      random.seed(0)
      r=random.randint(1,10)
      if r > 3:
         number = random.randint(1, 10)
         mylist.append(number)
   print mylist
   
f()   