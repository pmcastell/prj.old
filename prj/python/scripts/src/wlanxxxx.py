#!/usr/bin/python

import sys,md5
m=md5.new()

def imprimeDigest(cad):
    m.update(cad)
    cad=m.hexdigest()[0:20]
    print cad
    print cad.upper()
    
    

if (len(sys.argv)<2):
    print("Uso: wlanxxxx.py <mac-address> <essid>");
    exit(1)


mac=sys.argv[1]
essid=sys.argv[2]
mac=mac.replace(":",""); # quitamos los : de la mac si los hay

imprimeDigest("bcgbghgg"+mac[0:len(mac)-4]+essid[-4:]+mac)

imprimeDigest(mac[0:len(mac)-4]+essid[-4:]+mac)
imprimeDigest(mac[0:len(mac)-4]+essid[-4:])


