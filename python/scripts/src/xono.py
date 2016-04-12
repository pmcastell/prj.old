#!/usr/bin/python
ceros="0000000000"
i=0
while(i<=100000000):
    contra=str(i)
    contra=ceros[:10-len(contra)]+contra
    print contra
    i=i+1