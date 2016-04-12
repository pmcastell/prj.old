s = 'azcbobobegghakl'
#problema 1
i=0
count=0
while i<len(s):
    if s[i:i+1] in 'aeiou':
        count+=1
    i+=1
print 'Number of vowels:',count        
        
        
#problema 2
s = 'azcbobobegghakl'
search='bob'
count=0
pos=0
while (pos>=0 and pos<len(s) and s.find(search,pos)>=0):
    pos=s.index(search,pos)+1
    if pos>0:
        count+=1
print 'Number of times bob occurs is:',count

#problema 3
s = 'azcbobobegghakl'
i=0
actual=''
while i<len(s)-1:
    j=i
    nueva=s[j]
    while j<len(s)-1 and s[j]<=s[j+1]:
        nueva+=s[j+1]
        j+=1
    if len(nueva)>len(actual):
        actual=nueva
    i+=1
print 'Longest substring in alphabetical order is:',actual    


        