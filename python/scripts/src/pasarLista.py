#!/usr/bin/python
# -*- coding: utf-8 -*-

# Fecha creación: 3 feb. 2018
# autor: usuario

import datetime, os

def getHorario():
    return {'lun': {'11:30':'SIN', '12:25':'SER', '13:20':'SER'}, 
            'mar': {'10:15':'SER', '11:30':'SER', '12:25':'APW', '13:20': 'APW'},
            'mie': {'08:25':'SIN', '09:20':'SIN', '10:15':'SIN', '11:30': 'SER'},
            'jue': {'08:25':'APW', '09:20':'APW', '10:15':'SER', '12:25': 'SIN', '13:20': 'SIN'},
            'vie': {'08:25':'SER', '09:20':'SER'},
            'sab': {'08:25':'Levantarse','21:25': 'Lavadora'},
            'dom': {'08:25':'Levantarse'}
            }
def getWeekDay():
    return ['lun','mar','mie','jue','vie','sab','dom'][datetime.datetime.today().weekday()]  

def getHora():
    h=datetime.datetime.today()
    hor=h.timetuple()[3]
    strhor=str(hor)
    if (hor<10):
        strhor='0'+strhor
    min=h.timetuple()[4]
    strmin=str(min)
    if (min<10):
        strmin='0'+strmin
    return strhor+':'+strmin

dia=getWeekDay()
horDia=getHorario()[dia]
hora=getHora()
if (len(horDia)>0):
    horas=horDia.keys()
    for h in horas:
        if (h==hora):
            os.system("export DISPLAY=:0 && /usr/bin/fire /scripts/pcbit.html")
#if (len(hor)>0):
    