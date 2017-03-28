def bisiesto(year):
   if (year%4 == 0):
      if (year%400 == 0):
         return True
      elif (year%100 == 0):
         return False
      return True
   return False

def bisiesto2(year):
   return year % 4 == 0 and (year % 100 != 0 or year % 400 == 0)
   
for y in range(1900,2201):
   if (bisiesto(y)):
      print y   
