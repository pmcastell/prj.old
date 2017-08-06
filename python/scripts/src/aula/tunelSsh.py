#!/usr/bin/python
# -*- coding: utf-8 -*-

# Fecha creación: 17 jul. 2017
# autor: usuario

import requests
import base64
import tempfile
import time
import socket
import platform
import subprocess
from hashlib import md5
from Crypto.Cipher import AES
from Crypto import Random
from Crypto.Util import Counter
import sys


DEBUG=True

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

def derive_key_and_iv(password, salt, key_length, iv_length):
    d = d_i = ''
    while len(d) < key_length + iv_length:
        d_i = md5(d_i + password + salt).digest()
        d += d_i
    return d[:key_length], d[key_length:key_length+iv_length]


def deBase64(inf,outf):
    inf=open(inf,"r")
    outf=open(outf,"wb")
    for l in inf:
        outf.write(base64.b64decode(l))
    outf.close()
    inf.close()

def decryptCTR(in_file="/tmp/indice6.html", out_file=None, 
               password="clave"+time.strftime("%Y-%m-%d"), key_length=32,
               base64=True,padding=False):
    if (base64):
        tmpFile=tempfile.mktemp()
        deBase64(in_file,tmpFile)
        in_file=tmpFile
    in_file=open(in_file,"rb")
    if (out_file==None):
        out_file=sys.stdout
    else:
        out_file=open(out_file,"w")
    bs = AES.block_size
    salt = in_file.read(bs)[len('Salted__'):]
    key, iv = derive_key_and_iv(password, salt, key_length, bs)
    #print "key: "+key.encode("hex")
    #print "iv: "+iv.encode("hex")
    #sys.exit(0)
    ctr=Counter.new(bs*8,initial_value=long(iv.encode("hex"),16))
    cipher = AES.new(key, AES.MODE_CTR, counter = ctr)
    next_chunk = ''
    finished = False
    while not finished:
        chunk, next_chunk = next_chunk, cipher.decrypt(in_file.read(1 * bs))
        if len(next_chunk) == 0:
            if (padding):
                padding_length = ord(chunk[-1])
                chunk = chunk[:-padding_length]
            finished = True
        out_file.write(chunk)
    in_file.close()
    if (out_file!=sys.stdout):
       out_file.close()


def obtenerFicheroIndice(urls=None,salida=None):
    indice="indice6.html"
    if (urls==None):
        urls=["http://ganimedes.atwebpages.com/", "https://ganimedes.000webhostapp.com/","http://scratch.hol.es/","http://xyz.hit.to/",
          "http://ubuin.hopto.org/","http://ganimedes.esy.es/"]
    for url in urls:
        try: 
            r=requests.get(url+indice);  
            break
        except: r=None
            
    if (r==None or r.status_code!=200):
        return "Error"
    outfile = tempfile.mktemp()
    out=open(outfile,"w")
    out.write(r.content)
    out.close()
    #return encryptDecryptCtr256Shell(r.content,"clave"+time.strftime("%Y-%m-%d"),'d',hostname())
    return decryptCTR(outfile,salida)

def psutilMata():
    import psutil,os,signal,time
    encontrado=False
    for pid in psutil.pids():
        p=psutil.Process(pid)
        if ("ssh" in p.name() and " -w " in " ".join(p.cmdline())):
            encontrado=True
            break
    if (not encontrado):
        return
    cont=0
    while (pid in psutil.pids() and cont<20):
        if (cont<10):
            #print "Enviando SIGTERM a "+str(pid)
            os.kill(pid,signal.SIGTERM)
        else:
            #print "Enviando SIGKILL a "+str(pid)
            os.kill(pid,signal.SIGKILL)
        cont+=1
        time.sleep(1) 
            

def commandsMata():
    import commands, signal
    #proc = subprocess.Popen(["pgrep", process_name], stdout=subprocess.PIPE) 
    cont=0
    while True:
        #err,out=commands.getstatusoutput("ps awwx | grep -i ssh | grep '\-w' | grep -v grep")
        err,out=commands.getstatusoutput("pgrep -u root -f 'ssh.*-w'")
        if (err==0 and out!=""):
            if (cont<10):
                os.kill(out,signal.SIGTERM)
            else:
                os.kill(out,signal.SIGKILL)
        else:
            break 
        cont+=1  

def mata():
    try:
        import psutil
        psutilMata()
    except:
        commandsMata()        

def procesarParametros():
    from collections import OrderedDict
    tmpFile=tempfile.mktemp()
    obtenerFicheroIndice(salida=tmpFile)
    fp=open(tmpFile,"r")
    h=hostname()
    if DEBUG: h="aula2srv"
    res=OrderedDict()
    for p in fp:
        if (p[-1]=="\n"): p=p[:-1]
        if (p.startswith("GLOBAL") or p.startswith(h)):
            lp=p.split("=")
            res[lp[0]]=lp[1]
    fp.close()
    #quitar comillas dobles
    for k in res.keys():
        res[k]=res[k].replace('"','')
    for k in res.keys():
        for k2,v in res.iteritems():
            var="$"+k
            if var in v:
                pos=v.find(var)
                if (pos+len(var)<len(v) and (v[pos+len(var)].isalpha() or v[pos+len(var)] in "_-")):
                    pass
                else:
                    res[k2]=res[k2].replace(var,res[k])
    parametros={}
    parametros['TUN_SSH']=res[h+"_TUN_SSH"]
    parametros['TUN_SSH_IP']=res[h+"_TUN_SSH_IP"]
    parametros['TUN_SSH_PORT']=res[h+"_TUN_SSH_PORT"]
    parametros['TUN_SSH_DEV']=res[h+"_TUN_SSH_DEV"]
    parametros['TUN_SSH_DEV_IP']=res[h+"_TUN_SSH_DEV_IP"]
    parametros['TUN_SSH_DEV_GW']=res[h+"_TUN_SSH_RED"]+".1"
    parametros['TUN_SSH_CMD']=res[h+"_TUN_SSH_CMD"]
    return parametros

procesarParametros()
    
            
