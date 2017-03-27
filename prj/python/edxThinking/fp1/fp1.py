import pylab

def cargaDatos(path="julyTemps.txt"):
   try:
      inFile = open(path, 'r', 0)
   except:
      return (None,None)
   high=[]
   low=[]
   for line in inFile:
      fields=line.split()
      if (len(fields)<3 or not fields[0].isdigit()):
         pass
      else:
         high.append(int(fields[1]))
         low.append(int(fields[2]))
      
   return (high,low)

def producePlot(lowTemps, highTemps):
   diffTemps=[]
   for i in range(len(lowTemps)):
      diffTemps.append(highTemps[i]-lowTemps[i])
   pylab.figure(1)
   pylab.plot(range(1,32),diffTemps)
   pylab.title('Day by Day Ranges in Temperature in Boston in July 2012')
   pylab.xlabel('Days')
   pylab.ylabel('Temperature Ranges')
   pylab.show()
   

t=cargaDatos()
print t
producePlot(t[1],t[0])

      
   
   