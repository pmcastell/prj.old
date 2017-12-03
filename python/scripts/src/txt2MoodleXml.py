#!/usr/bin/python3
# -*- coding: utf-8 -*-

# Fecha creación: 4 nov. 2017
# autor: usuario
import sys

def uso():
    print("Tienes que dar 4 parámetros como mínimo:")
    print("1. Nombre pregunta")
    print("2. Enunciado")
    print("3,4. Respuestas alternativas")
    sys.exit(1)
    
if (len(sys.argv)>1 and sys.argv[1]=="--head"):
    print("""<?xml version="1.0" encoding="UTF-8"?>
    <quiz>
    <question type="category">
        <category>
            <text>$course$/{}</text>
        </category>
    </question>""".format(sys.argv[2]))
    sys.exit(0)
    
if (len(sys.argv)<4):
    uso()

qname=sys.argv[1]
quest=sys.argv[2].replace("<m>","<b><i>").replace("</m>","</i></b>")
answers=[]
for i in range(3,len(sys.argv)):
    answers.append(sys.argv[i])
correctas=0
for a in answers:
    if (a[0]=="#"):
        correctas+=1

incorrectas=len(answers)-correctas
#print("Pregunta: "+qname)
#print("Questión: "+quest)
#print("Answers: "+str(answers))
if (correctas==1): single="true" 
else: single="false"
fraction=1.0/correctas*100
fratIncorrect=1.0/incorrectas*100
if (fratIncorrect>fraction): fratIncorrect=fraction

if (fraction==int(fraction)): fraction=int(fraction)
else: fraction=round(fraction,5)
    
if (fratIncorrect==int(fratIncorrect)): fratIncorrect=int(fratIncorrect)
else: fratIncorrect=round(fratIncorrect,5)
print("""    <question type='multichoice'>
        <name><text>{}</text></name>
        <questiontext format='html'>
            <text><![CDATA[{}]]></text>
        </questiontext>
        <defaultgrade>1.0000000</defaultgrade>
        <single>{}</single>
        <shuffleanswers>true</shuffleanswers>""".format(qname,quest,single))
for a in answers:
    if (a[0]=="#"):
        fr=fraction
        a=a[1:]
    else:
        fr=-fratIncorrect
    print("""        <answer fraction='{}' format='html'><text>{}</text></answer>""".format(fr,a))
print("""    </question>""")            