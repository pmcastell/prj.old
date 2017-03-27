#!/usr/bin/python
import sys

if len(sys.argv)<5:
    print "uso: ",sys.argv[0]," fich-ent fich-sal cadena-buscada cadena-reemplazo"
    sys.exit(1)
try:
    fin=open(sys.argv[1],"r")
    fout=open(sys.argv[2],"w")
except:
    print "error abriendo ficheros"    
    sys.exit(2)
busc=sys.argv[3]
reemp=sys.argv[4]
for linea in fin:
    fout.write(linea.replace(busc,reemp))
fin.close()
fout.close()    
    

    