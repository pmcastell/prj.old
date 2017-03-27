#!/usr/bin/python
# -*- coding: utf-8 -*-

# Fecha creación: 21 mar. 2017
# autor: usuario


def menor(m,fil,col):
    res=[]
    ri=0
    for i in range(len(m)):
        if (i!=fil):
            res.append([])
            for j in range(len(m[0])):
                 if (j!=col):
                     res[ri].append(m[i][j])
            ri+=1
    return res
        

def det(m):
    if (len(m)==1):
        return m[0][0]

    signo=-1
    res=0
    for j in range(len(m[0])):
        signo=-signo
        res+=signo*m[0][j]*det(menor(m,0,j))
    return res

m=eval(sys.argv[1])
print det(m)        