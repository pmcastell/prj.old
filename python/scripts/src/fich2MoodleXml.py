#!/usr/bin/python
# -*- coding: utf-8 -*-

# Fecha creación: 3 dic. 2017
# autor: usuario

import sys

def reemplaza(cad):
    return cad.replace("<m>","&lt;b>&lt;i>").replace("</m>","&lt;/i>&lt;/b>")

def question(qname,quest,answers,positivo=False):
    quest=reemplaza(quest)
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
    if (incorrectas==0): fratIncorrect=0
    else: fratIncorrect=1.0/incorrectas*100
    if (fratIncorrect>fraction): fratIncorrect=fraction
    if (fraction==int(fraction)): fraction=int(fraction)
    else: fraction=round(fraction,5)
    if (fratIncorrect==int(fratIncorrect)): fratIncorrect=int(fratIncorrect)
    else: fratIncorrect=round(fratIncorrect,5)
    if (positivo): fratIncorrect=0
    questXml="""    <question type='multichoice'>
        <name><text>{}</text></name>
        <questiontext format='html'>
            <text>{}</text>
        </questiontext>
        <defaultgrade>1.0000000</defaultgrade>
        <single>{}</single>
        <shuffleanswers>1</shuffleanswers>""".format(qname,quest,single)
    for a in answers:
        if (a[0]=="#"):
            fr=fraction
            a=a[1:]
        else:
            fr=-fratIncorrect
        a=reemplaza(a)
        questXml+="""\n        <answer fraction='{}' format='html'><text>{}</text></answer>""".format(fr,a)
    if (single=="true"):
        questXml+="""\n        <answer fraction='0' format='html'><text>&lt;span style='color: red; text-decoration: underline; font-weight: bold;'>Dejar la pregunta en blanco&lt;/span></text></answer>"""
    questXml+="""\n    </question>"""
    return questXml            

def cabecera(category):
    return """    <question type="category">
        <category>
            <text>{}</text>
        </category>
    </question>""".format(category)

def uso():
    print("Uso: {} [-p] <nombre-fichero-cuestionario-txt>".format(sys.argv[0]))
    sys.exit(1)


entrada=None
entrada=open("/m/tmp/preguntas/pruebas/p1.txt","r")
positivo=False
if (entrada==None):
    if (len(sys.argv)<2): uso()
    if (sys.argv[1]=="-p"): 
        positivo=True
        entrada=open(sys.argv[2],"r")
    else: 
        positivo=False
        entrada=open(sys.argv[1],"r")


print("""<?xml version="1.0" encoding="UTF-8"?>
<quiz>""");
l=entrada.readline()
while (l):
    if (l[0]=="\n"): l=entrada.readline(); continue
    if (l[0]=="#"): print("<!-- "+l.strip()+" -->"); l=entrada.readline(); continue
    if (l.startswith("Category")): category=l[8:].strip(); print(cabecera(category)); l=entrada.readline(); continue
    if (l[0]==" " or l[0]=="\t"): print("Error hay respuestas antes que preguntas");sys.exit(2)
    posEsp=l.find(" ")
    qname=l[:posEsp].strip()
    quest=l[posEsp:].strip()
    l=entrada.readline(); answers=[]
    while (l and (l[0]==" " or l[0]=="\t")):
        if (l.find("<pre>")>=0):
            l=l.strip()
            while(l.find("</pre>")<0):
                l+=entrada.readline()
            l=l.replace("<pre>","&lt;pre>").replace("</pre>","&lt;/pre>")
            l+="&lt;hr/>"
            answers.append(l)
        else:
            answers.append(l.strip())
        l=entrada.readline()
    print(question(qname,quest,answers,positivo))
    
print("</quiz>")

sys.exit(0)
    
print(question("P01","En Seguridad Informática el objetivo de <m>No repudio</m>, se refiere a:",
               ["#La garantía de la participación de las partes en una comunicación",
                "Que la información una vez enviada tiene que ser aceptada por la otra parte",
                "La garantía de la integridad de la información",
                "La garantía de la disponibilidad de la información"
               ]
               ))    