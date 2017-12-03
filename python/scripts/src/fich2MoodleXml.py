#!/usr/bin/python
# -*- coding: utf-8 -*-

# Fecha creación: 3 dic. 2017
# autor: usuario

import sys

def question(qname,quest,answers):
    quest=quest.replace("<m>","<b><i>").replace("</m>","</i></b>")
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
    questXml="""    <question type='multichoice'>
        <name><text>{}</text></name>
        <questiontext format='html'>
            <text><![CDATA[{}]]></text>
        </questiontext>
        <defaultgrade>1.0000000</defaultgrade>
        <single>{}</single>
        <shuffleanswers>true</shuffleanswers>""".format(qname,quest,single)
    for a in answers:
        if (a[0]=="#"):
            fr=fraction
            a=a[1:]
        else:
            fr=-fratIncorrect
        questXml+="""\n        <answer fraction='{}' format='html'><text>{}</text></answer>""".format(fr,a)
    questXml+="""\n    </question>"""
    return questXml            

def cabecera(category):
    return """<?xml version="1.0" encoding="UTF-8"?>
<quiz>
    <question type="category">
        <category>
            <text>{}</text>
        </category>
    </question>""".format(category)

def uso():
    print("Uso: {} <nombre-fichero-cuestionario-txt>".format(sys.argv[0]))
    sys.exit(1)

#if (len(sys.argv)<2): uso()
#entrada=open(sys.argv[1],"r")
entrada=open("/m/tmp/preguntas/Examen-Temas-1-2-SIN","r")
l=entrada.readline()
while (l):
    if (l[0]=="#" or l[0]=="\n"): l=entrada.readline(); continue
    if (l.startswith("Category")):
        category=l[8:].strip()
        print(cabecera(category))
        l=entrada.readline()
        continue
    if (l[0]==" " or l[0]=="\t"):
        print("Error hay respuestas antes que preguntas");sys.exit(2)
    posEsp=l.index(" ")
    qname=l[:posEsp].strip()
    quest=l[posEsp:].strip()
    l=entrada.readline(); answers=[]
    while (l and (l[0]==" " or l[0]=="\t")):
        answers.append(l.strip())
        l=entrada.readline()
    print(question(qname,quest,answers))
    
print("</quiz>")

sys.exit(0)
    
print(question("P01","En Seguridad Informática el objetivo de <m>No repudio</m>, se refiere a:",
               ["#La garantía de la participación de las partes en una comunicación",
                "Que la información una vez enviada tiene que ser aceptada por la otra parte",
                "La garantía de la integridad de la información",
                "La garantía de la disponibilidad de la información"
               ]
               ))    