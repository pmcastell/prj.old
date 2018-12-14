#!/usr/bin/python
# -*- coding: utf-8 -*-

# Fecha creación: 17 jul. 2017
# autor: usuario

import base64, tempfile, time, socket, platform, sys, os, re
import signal, subprocess, errno, random
import hashlib 
#from sqlalchemy.sql.expression import false
try:
    from Crypto.Cipher import AES
    from Crypto import Random
    from Crypto.Util import Counter
except ImportError:
    os.system("sudo apt-get -y install python-crypto")
    os.system("sudo yum -y install python-crypto")

DEBUG=False
TMP_DIR=tempfile.gettempdir()

def debug(*mensa):
    global DEBUG
    if DEBUG:
        imprimir=""
        for m in mensa:
            imprimir+=str(m)
        print(imprimir)

def wakeOnLan(mac,dirBroad,port=9):
    """ Switches on remote computers using WOL. """

    # Check mac format and try to compensate.
    if len(mac) == 12:
        pass
    elif len(mac) == 12 + 5:
        sep = mac[2]
        mac = mac.replace(sep, '')
    else:
        raise ValueError('Incorrect MAC address format')

    # Pad the synchronization stream.
    data = ''.join(['FFFFFFFFFFFF', mac * 16])
    send_data = hexDecode(data)

    # Split up the hex values and pack.
    #for i in range(0, len(data), 2):
    #    send_data = b''.join([send_data,
    #                         struct.pack('B', int(data[i: i + 2], 16))])
    # Broadcast it to the LAN.
    sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    sock.setsockopt(socket.SOL_SOCKET, socket.SO_BROADCAST, 1)
    sock.sendto(send_data, (dirBroad, int(port)))
    print("wol: "+str(mac)+" - "+str(dirBroad)+":"+str(port))
    return True   

def wakeOnLanInsti(host):
    broadcast="172.18.161.255"
    MACS  = {"srvSalaProfes" :    "00:24:01:ee:22:59",
             "aula2srv"      :    "00:24:01:ed:30:c1 00:e0:4c:38:00:73",
             "aula1srv"      :    "00:19:99:88:6a:f8 c4:6e:1f:05:fc:71",
             "tecnologia"    :    "00:19:99:80:53:e1",
             "biblioSrv1"    :    "00:24:01:ee:21:c7",
             "DEPMAT2"       :    "44:8a:5b:c2:77:fc",
             "PT1"           :    "44:8a:5b:c2:71:e6",
             "Salaprofes2"   :    "00:0f:ea:40:57:3f",
             "DeptSociales1" :    "8c:89:a5:2e:88:ba"}
    if (host=="ALL"):
        for k in MACS.keys():
            for m in MACS[k].split():
                if (not wakeOnLan(m,broadcast)): return False
    else:
        try:
            wakeOnLan(MACS[host],broadcast)
        except:
            return False
    return True
 
def randomString(lon):
    res=""
    while(lon>0):
        res+=chr(random.randint(0,255))
        lon-=1    
    return res

def randomByteArray(lon):
    res=bytearray()
    while(lon>0):
        res.append(random.randint(0,255))
        lon-=1
    return res
            

def hexChar(c):
    c=int(c)
    letras=['a','b','c','d','e','f']
    if (c>=10):
        return letras[c-10]
    else:
        return str(c)

def hexCharValue(h):
    hexDigits=['0', '1', '2', '3', '4', '5', '6', '7', '8', '9','a','b','c','d','e','f']
    try:
        return hexDigits.index(h)
    except ValueError:
        return -1

def toByteArray(cad):
    res=bytearray()
    for c in cad:
        if (type(c)==int):
            res.append(c)
        else:
            res.append(ord(c))
    return res        

def byteArrayToStr(barr):
    res=""
    for o in barr:
        if (type(o)==str):
            res+=o
        elif (type(o)==int):
            res+=chr(o)
        else:
            raise TypeError
    return res

def hexEncode(cad):
    res=""
    if (type(cad)==str):
        cad=toByteArray(cad)
    for c in cad:
        res+=hexChar(c/16)+hexChar(c%16)
    return res

def hexDecode(cad):
    cad=cad.lower()
    res=bytearray()
    for i in range(0,len(cad),2):
        res.append(hexCharValue(cad[i])*16+hexCharValue(cad[i+1]))
    return res
        

def pid_exists(pid):
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
    except os.error as error:
        if error.errno == errno.ESRCH:
            # ESRCH == No such process
            return False
        elif error.errno == errno.EPERM:
            # EPERM clearly means there's a process to deny access to
            return True
        else:
            # According to "man 2 kill" possible error values are
            # (EINVAL, EPERM, ESRCH)
            return False
    return True

def conexionActiva(host):
    nPings=4; timeout=2
    # Ping parameters as function of OS
    if (platform.system().lower()=="windows"): parameters = "-n "+str(nPings)+" -w "+str(timeout)
    else: parameters = "-c "+str(nPings)+" -W "+str(timeout)
    #cmd=['ping',parameters,host]
    # Pinging
    return os.system("ping " + parameters + " " + host) == 0
    #return subprocess.Popen(cmd).wait()==0


def hostname():
    return socket.gethostname().split(".")[0]

def derive_key_and_iv(password, salt, key_length, iv_length, hashMethod='md5' ):
    hashFuncts={'md5':hashlib.md5, 'sha1':hashlib.sha1, 'sha224':hashlib.sha224,'sha256':hashlib.sha256,
                'sha384':hashlib.sha384,'sha512':hashlib.sha512}
    if (type(password)==str): password=toByteArray(password)
    if (type(salt)==str): salt=toByteArray(salt)
    d = d_i = b''
    while len(d) < key_length + iv_length:
        #print("Tipo d_i: "+str(type(d_i))+" Tipo password: "+str(type(password))+" Tipo salt: "+str(type(salt)))
        #break
        hasMethodFunct=hashFuncts[hashMethod]
        d_i = hasMethodFunct(d_i + password + salt).digest()
#         if (hashMethod=="md5"):
#             d_i = hashlib.md5(d_i + password + salt).digest()
#         elif (hashMethod=="sha256"):
#             d_i = hashlib.sha256(d_i + password + salt).digest()
            
        d += d_i
    return d[:key_length], d[key_length:key_length+iv_length]


def base64Dec(inf,outf):
    inf=open(inf,"r")
    outf=open(outf,"wb")
    base64.decode(inf,outf)
    #for l in inf:
    #    outf.write(base64.b64decode(l))
    outf.close()
    inf.close()

def base64Enc(inf,outf):
    inf=open(inf,"rb")
    outf=open(outf,"wb")
    base64.encode(inf,outf)
    #for l in inf:
    #    outf.write(base64.b64encode(l)+"\n")
    outf.close()
    inf.close()


def encryptCTR(inf, outf, password="clave"+time.strftime("%Y-%m-%d"), key_length=32, base64=True,padding=False):
    bs = AES.block_size
    salt = Random.new().read(bs - len('Salted__'))
    key, iv = derive_key_and_iv(password, salt, key_length, bs)
    ctr=Counter.new(bs*8,initial_value=int(hexEncode(iv),16))
    cipher = AES.new(key, AES.MODE_CTR, counter = ctr)
    in_file=open(inf,"r")
    out_file=open(outf,"wb")
    out_file.write(b'Salted__' + salt)
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

def decryptCTR(in_file=TMP_DIR+"/indice6.html", out_file=None, 
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
    ctr=Counter.new(bs*8,initial_value=int(hexEncode(iv),16))
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
        out_file.write(byteArrayToStr(chunk))
    in_file.close()
    if (out_file!=sys.stdout):
        out_file.close()

def obtenerFicheroRed2(urls,salida=None,nombre="",tipo="asc"):
    if (type(urls)==str):
        urls=[urls]
    for url in urls:
        try:
            #content=requests.get(url+nombre)
            r=requests.get(url+nombre)
            break
        except: r=None
    if (r==None or r.status_code!=200):
        return False
    if (tipo=="asc" and type(r.content)==bytes or type(r.content)==bytearray):
        contenido=byteArrayToStr(r.content)
    else:
        contenido=r.content
    if (salida==None): return contenido
    if (tipo=="asc"):
        out=open(salida,"w")
    else:
        out=open(salida,"wb")
    out.write(contenido)
    if (salida!="/dev/stdout"):
        out.close()
    return True


def obtenerFicheroRed(urls,salida=None,nombre="",tipo="asc"):
    if (type(urls)==str):
        urls=[urls]
    pyth3=(int(sys.version[0])>2)
    if (pyth3):
        import urllib.request
        opener=urllib.request.build_opener()
    else:
        import urllib2
        opener=urllib2.build_opener()
    opener.addheaders = [('User-Agent', 'curl/7.47.0')]
    for url in urls:
        try:
            #content=requests.get(url+nombre)
            r=opener.open(url+nombre)
            break
        except: r=None
    if (r==None or r.code!=200):
        return False
    contenido=r.read()
    if (tipo=="asc" and type(contenido)==bytes or type(contenido)==bytearray):
        contenido=byteArrayToStr(contenido)
    if (salida==None): return contenido
    if (tipo=="asc"):
        out=open(salida,"w")
    else:
        out=open(salida,"wb")
    out.write(contenido)
    if (salida!="/dev/stdout"):
        out.close()
    return True
    
    
def obtenerFicheroIndice(urls=None,salida=None,indice="indice6.html"):
    if (urls==None):
        #urls=["http://ganimedes.atwebpages.com/", "https://ganimedes.000webhostapp.com/","http://scratch.hol.es/","http://xyz.hit.to/",
        #  "http://ubuin.hopto.org/","http://ganimedes.esy.es/"]
        sitios=obtenerClavesFtp(); urls=[]
        for k in sitios.keys():
            urls.append(sitios[k][3])
    outfile = tempfile.mktemp()
    #print(urls)
    if (obtenerFicheroRed(urls,outfile,indice)):
        return decryptCTR(outfile,salida)
    else:
        return ""

def osMata(pid):
    if (not pid_exists(pid)):
        return False
    cont=0
    while (pid_exists(pid) and cont<20):
        try:
            if (cont<10):
                os.kill(pid,signal.SIGTERM)
            else:
                os.kill(pid,signal.SIGKILL)
        except os.error as error:
            if error.errno == errno.ESRCH:
                # ESRCH == No such process
                print(str(error))
                print("No Existe el Proceso")
                return False
            elif error.errno == errno.EPERM:
                # EPERM clearly means there's a process to deny access to
                print("Permiso Denegado")
                return True
            else:
                # According to "man 2 kill" possible error values are
                # (EINVAL, EPERM, ESRCH)
                print("Error: ",errno)
                return False
        cont+=1
        time.sleep(0.5) 
    return True
    
def psutilMata(dev):
    import psutil
    encontrado=False
    for pid in psutil.pids():
        p=psutil.Process(pid)
        if ("ssh" in p.name() and " -w "+str(dev) in " ".join(p.cmdline())):
            encontrado=True
            break
    if (not encontrado):
        print("No Encontrado")
        return
    osMata(pid)
#     cont=0
#     while (pid in psutil.pids() and cont<20):
#         if (cont<10):
#             #print "Enviando SIGTERM a "+str(pid)
#             os.kill(pid,signal.SIGTERM)
#         else:
#             #print "Enviando SIGKILL a "+str(pid)
#             os.kill(pid,signal.SIGKILL)
#         cont+=1
#         time.sleep(1) 
             

def commandsMata(dev):
    #proc = subprocess.Popen(["pgrep", process_name], stdout=subprocess.PIPE) 
    #err,out=commands.getstatusoutput("ps awwx | grep -i ssh | grep '\-w' | grep -v grep")
    #err,out=commands.getstatusoutput("pgrep -u root -f 'ssh.*-w"+str(dev)+"'")
    cmd="pgrep -u root -f ssh.*-w.16"
    proc=subprocess.Popen(cmd.split(),stdout=subprocess.PIPE,stderr=subprocess.PIPE)
    out,salida=proc.communicate(); err=proc.returncode
    print("err: "+str(err)+"out: "+str(out)+" dev: "+str(dev))
    if (err==0 and out!=""):
        osMata(int(out))


def commandsMata2(dev):
    #proc = subprocess.Popen(["pgrep", process_name], stdout=subprocess.PIPE) 
    cont=0
    while True:
        #err,out=commands.getstatusoutput("ps awwx | grep -i ssh | grep '\-w' | grep -v grep")
        #err,out=commands.getstatusoutput("pgrep -u root -f 'ssh.*-w"+str(dev)+"'")
        cmd="pgrep -u root -f ssh.*-w.16"
        proc=subprocess.Popen(cmd.split(),stdout=subprocess.PIPE,stderr=subprocess.PIPE)
        out,salida=proc.communicate(); err=proc.returncode
        print("err: "+str(err)+"out: "+str(out)+" dev: "+str(dev))
        if (err==0 and out!=""):
            try:
                if (cont<10):
                    os.kill(int(out),signal.SIGTERM)
                else:
                    os.kill(int(out),signal.SIGKILL)
            except os.error as error:
                if error.errno == errno.ESRCH:
                    # ESRCH == No such process
                    print("No Existe el Proceso")
                    return False
                elif error.errno == errno.EPERM:
                    # EPERM clearly means there's a process to deny access to
                    print("Permiso Denegado")
                    return True
                else:
                    # According to "man 2 kill" possible error values are
                    # (EINVAL, EPERM, ESRCH)
                    print("Error: ",errno)
                    return False 
        else:
            break 
        cont+=1  

def mata(dev):
    try:
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
        #for k2,v in res.iteritems():
        for k2,v in res.items():
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



def md5Lineas(fich,nlineas):
    f=open(fich,"r")
    lineas=bytearray()
    cont=1
    pyth3=(int(sys.version[0])>2)
    for l in f:
        debug("l: ",l," type(l): ",type(l)," type(lineas): ",type(lineas))
        if (pyth3):
            lb=l.encode()
        else:
            lb=toByteArray(l)
        for c in lb:
            lineas.append(c)
        cont+=1
        if (cont>nlineas): break
    debug("len(lineas):",len(lineas))
    f.close()    
    return hashlib.md5(lineas).hexdigest()        

def username():
    import getpass
    return getpass.getuser()

def tunelSSH(param):
    DEV=param['TUN_SSH_DEV']; DEV_IP=param['TUN_SSH_DEV_IP'];DEV_GW=param['TUN_SSH_DEV_GW']
    CMD=param['TUN_SSH_CMD']; IP=param['TUN_SSH_IP']; PORT=param['TUN_SSH_PORT']
    cmds=[]
    #cmds.append("/usr/bin/ssh -o UserKnownHostsFile=/dev/null -o StrictHostKeyChecking=no -o ConnectTimeout=10 -p "+PORT+" root@"+IP+" -w "+DEV+":"+DEV+" -CTf bash -c \'/bin/ls; /bin/sleep 5; /sbin/ifconfig tun"+DEV+"  "+DEV_GW+"/24 pointopoint "+DEV_IP+" up; /bin/sleep 3; "+CMD+" \'")
    cmds.append("/usr/bin/ssh -o UserKnownHostsFile=/dev/null -o StrictHostKeyChecking=no -o ConnectTimeout=10 -p "+PORT+" root@"+IP+" -w "+DEV+":"+DEV+" -CTf bash -c \'/bin/ls; /bin/sleep 5; ip addr add local "+DEV_GW+"/24 remote "+DEV_IP+"/24 dev tun"+DEV+"; ip link set dev tun"+DEV+" up; /bin/sleep 3; "+CMD+" \'")
    #cmds.append("/sbin/ifconfig tun"+DEV+" "+DEV_IP+"/24 pointopoint "+DEV_GW+"/24 up &> /dev/null")
    #ip a add local 10.16.16.2/24 remote 10.16.16.16/24 dev tun16
    cmds.append("ip addr add local "+DEV_IP+"/24 remote "+DEV_GW+"/24 dev tun"+DEV)
    cmds.append("ip link set tun"+DEV+" up")
    cmds.append("/sbin/iptables -t nat -D POSTROUTING -j MASQUERADE -s 10."+DEV+"."+DEV+".0/24 &> /dev/null")
    cmds.append("/sbin/iptables -t nat -A POSTROUTING -j MASQUERADE -s 10."+DEV+"."+DEV+".0/24")
    for j in range(10):
        for i in range(len(cmds)):
            print(cmds[i])
            if not DEBUG: os.system(cmds[i])
            if (i==0): time.sleep(1)
        if conexionActiva(param['TUN_SSH_DEV_GW']): break

def debeSerAdmin():
    usuario=username()
    if DEBUG: usuario="root"
    if (usuario!="root" and not usuario.lower().startswith("admin")):
        print("Debes ejecutar este programa como administrador.")
        sys.exit(1)

def loopTunel():
    debeSerAdmin()
    md5Name=hashlib.md5(toByteArray(os.path.basename(sys.argv[0]))).hexdigest()
    pidfile=tempfile.gettempdir()+"/"+md5Name
    if (os.path.isfile(pidfile)):
        pid=open(pidfile,"r").read();
        if (pid_exists(pid)): sys.exit(2)
        else: os.remove(pidfile)
    
    pdf=open(pidfile,"w"); pdf.write(str(os.getpid())); pdf.close()
    horaComienzo=time.strftime("%H")
    conexionEstablecida=False
    parametros={}
    while (horaComienzo==time.strftime("%H")):
        if (not conexionEstablecida):
            parametros=procesarParametros()
            debug("Parametros:",parametros)
        conexionEstablecida=False
        if (len(parametros)>0 and 'TUN_SSH' in parametros.keys() and parametros['TUN_SSH']=="si"):
            if (conexionActiva(parametros['TUN_SSH_DEV_GW'])):
                conexionEstablecida=True
            else:
                mata(parametros['TUN_SSH_DEV'])
                tunelSSH(parametros)
        debug("conexionEstablecida:",conexionEstablecida) 
        try:
            print("Durmiendo:",parametros['GLOBAL_ESPERA'])
            time.sleep(int(parametros['GLOBAL_ESPERA']))
        except:
            print("Durmiendo: 300")
            time.sleep(300) 
        print("Fin Durmiendo")
    print("Saliendo")

def numLineas(filename):
    cont=0; ult=""
    with open(filename,"r") as f:
        for l in f:
            cont+=1
            ult=l
    return (cont,ult)

def numLineas2(filename):
    import mmap
    f = open(filename, "r+")
    buf = mmap.mmap(f.fileno(), 0)
    lines = 0
    #readline = buf.readline
    l=buf.readline()
    penUltLinea=""
    while l:
        lines += 1
        penUltLinea=l
        l=buf.readline()
    f.close()
    return (lines,penUltLinea)

def numLineas3(filename):
    for n,l in enumerate(open(filename,"r")):
        pass
    return (n,l)

def ficheroLine(fichName,nlin):
    res=""
    with open(fichName,"r") as f:
        cont=1
        for l in f:
            if (cont==nlin):
                res=l
                break
            cont+=1
    return res
    

def ficheroReplace(fichName,buscada,reemplaza):
    import fileinput
    for line in fileinput.input(fichName, inplace=True):
        line=line.replace(buscada,reemplaza)
        if (line[-1]=="\n"): line=line[:-1]
        print("%s" % (line))

def ficheroAppend(fichName,text):
    with open(fichName,"a") as fichero:
        if (text[-1]=="\n" or text[-1]=="\r"):
            fichero.write(text)
        else:
            fichero.write(text+"\n")

def ficheroContiene(fichname,text):
    with open(fichname,"r") as fichero:
        for l in fichero:
            if text in l:
                return True
    return False

def appendSiNoEsta(fichname,text):
    if (ficheroContiene(fichname,text)):
        return False
    else:
        ficheroAppend(fichname,text)
        return True
        
def ponerMD5(fichero):
    numLin,ultLin=numLineas(fichero)
    if (ultLin.upper().startswith("MD5SUM")):
        MD5=md5Lineas(fichero,numLin-1)
        if (ultLin!="MD5SUM="+MD5+"\n"):
            ficheroReplace(fichero,ultLin,"MD5SUM="+MD5+"\n")
    else:
        MD5=md5Lineas(fichero,numLin)
        ficheroAppend(fichero,"MD5SUM="+MD5)
#     MD51=md5Lineas(fichero,numLin-1)
#     MD52=ultLin[:-1].split("=")
#     print "MD51: "+MD51+" MD52: "+MD52
#     if (MD51!=MD52[1]):
#         if (MD52[0]=="MD5SUM"):
#             ficheroReplace(fichero,"MD5SUM="+MD52,"MD5SUM="+MD51)
#         else:
def cambiarRutaSitio(sitio):
    router=os.popen("ip route | grep default | grep -Eo '([0-9]{1,3}\.){3}[0-9]{1,3}'").read()
    sIps=os.popen("dig "+sitio+" | grep A | grep IN | grep -vE '^;' | awk '{print $NF;}'").read().split("\n")
    for dirIp in sIps:
        if (dirIp!=""):
            #os.system("sudo -S route add -host "+dirIp+" gw "+router)
            os.system("route add -host "+dirIp+" gw "+router)
    return sIps

def borrarRutasSitio(sIps):
    for dirIp in sIps:
        if (dirIp!=""):
            #os.system("sudo -S route del -host "+dirIp)
            os.system("route del -host "+dirIp)

def direccionIp(real=True,getDict=False,nuevo=False):
    if (not nuevo and real and not getDict and os.path.isfile(TMP_DIR+"/direccionIpReal.txt")):
        ip=open(TMP_DIR+"/direccionIpReal.txt","r").read()
        if (re.match("([0-9]{1,3}\.){3}[0-9]{1,3}$",ip)):
            if (ip[-1]=="\n"):
                ip=ip[:-1]
            return ip
    servicioIp="ipinfo.io"
    if (real):
        sIps=cambiarRutaSitio(servicioIp)
        
    datos={}
    for i in range(10):
        resp=obtenerFicheroRed("http://"+servicioIp)
        if (resp):
            datos=eval(resp)
            break
    if (real):
        borrarRutasSitio(sIps)
    if (len(datos)>0):
        if (getDict):
            return datos
        else:
            return datos['ip']
        if (real and nuevo):
            with open(TMP_DIR+"/direccionIpReal.txt","w") as fDirIp:
                fDirIp.write(datos['ip'])
    else:
        return ""
        
def direccionIp2(real=True):
    if (os.path.isfile(TMP_DIR+"/direccionIpReal.txt")):
        ip=open(TMP_DIR+"/direccionIpReal.txt","r").read()
        if (re.match("([0-9]{1,3}\.){3}[0-9]{1,3}$",ip)):
            return ip
    import netifaces
    servicioIp="http://ipinfo.io"
    cont=0
    while True:
        if (cont>10): return None
        datos=eval(requests.get(servicioIp).content)
        if (not real): return datos
        if (datos and datos['country']=="ES"): break
        debeSerAdmin()
        router=netifaces.gateways()['default'][2][0]
        #os.system("sudo -S route add -host ipinfo.io gw "+router)
        os.system("route add -host ipinfo.io gw "+router)
        cont+=1
    if (cont>0):
        cont=0; err=1;
        while (cont<10 and err!=0):
            #err=os.system("sudo -S route del -host ipinfo.io")
            err=os.system("route del -host ipinfo.io")
    with open(TMP_DIR+"/direccionIpReal.txt","w") as fDirIp:
        fDirIp.write(datos['ip'])
    return datos['ip']

def cambiarParametrosIndice(fichName,parametros):
    import fileinput
    for linea in fileinput.input(fichName, inplace=True):
        for k in parametros.keys():
            if (linea.startswith(k)):
                linea=k+"="+parametros[k]
                break
        while (linea!="" and linea[-1]=="\n"):
            linea=linea[:-1]
        print("%s" % (linea))


def obtenerFicheroGitHub(fichero,fichDest,tipo="asc",
                         url="https://raw.githubusercontent.com/javier-iesn/prj/master/scripts/"):
    return obtenerFicheroRed(url,fichDest,fichero,tipo)

def obtenerClavesFtp():
    res={}
    if (os.path.isfile("\scripts\hostinger.sh")):
        fClaves="\scripts\hostinger.sh"
    elif (os.path.isfile("/scripts/hostinger.sh")):
        fClaves="/scripts/hostinger.sh"
    else:
        obtenerFicheroGitHub("hostinger.sh",TMP_DIR+"/hostinger.sh")
        fClaves=TMP_DIR+"/hostinger.sh"
    if (fClaves==None):
        #claves=listadoDeClaves()
        pass
    else:
        fc=open(fClaves,"r")
        for l in fc:
            if (l.endswith("###\n")):
                datosHost=l.split()
                res[datosHost[0]+":"+datosHost[1]]=datosHost
        fc.close()
    return res
                

def subirFtp(fich):
    import ftplib
    claves=obtenerClavesFtp()
    for k in claves.keys():
        try:
            sIps=cambiarRutaSitio(claves[k][1])
            ftp = ftplib.FTP(claves[k][1],claves[k][0],"basura68")
            ftp.cwd(claves[k][2])
            resp=ftp.storbinary('STOR '+os.path.basename(fich), open(fich,"rb"))
            borrarRutasSitio(sIps)
            print("Subido: "+fich+" a: "+str(claves[k]))
            #if (not resp.startswith("226")):
        except:
            print("Error transfiriendo: "+fich+" a "+claves[k][1])
        
def comprobarSubidaCorrecta(fichParam):
    tmpFile=tempfile.mktemp()
    obtenerFicheroIndice(salida=tmpFile)
    with open(tmpFile,"r") as fContSubido:
        contenidoSubido=toByteArray(fContSubido.read())
    md51=hashlib.md5(contenidoSubido).hexdigest()
    md52=hashlib.md5(toByteArray(open(fichParam).read())).hexdigest()
    if (md51!=md52):
        print("Error en fichero subido")
    else:
        print("Fichero subido correctamente")
    
        
def subirFicheros(realIp=None,globalTunSsh="si",globalTunPort="443"):
    #./tunelSsh.py --start <SSH|OVPN> <si|no> [<dir-base>]
    if (len(sys.argv)<4):
        service="SSH"; habilitar="si"
    else:
        service=sys.argv[2]; habilitar=sys.argv[3]
    if (len(sys.argv)<5):
        dirBase="/home/usuario/hostinger"
    else:
        dirBase=sys.argv[4]
    #indices=("indice6.html","indice5.html")
    #indices=("indice6.html",)
    indices=("indice6.html","indicepass.html")
    if (realIp==None): realIp=direccionIp()
    for indice in indices:
        fichParam=dirBase+"/"+indice; fichParamTemp=tempfile.gettempdir()+"/"+indice
        newParams={'GLOBAL_TUN_SSH': globalTunSsh,'GLOBAL_TUN_IP':realIp, 'GLOBAL_TUN_PORT': globalTunPort }
        print(newParams)
        cambiarParametrosIndice(fichParam,newParams)
        ponerMD5(fichParam)
        encryptCTR(fichParam,fichParamTemp)
        subirFtp(fichParamTemp)
        comprobarSubidaCorrecta(fichParam)
        

def start(dirIp=None):
    if (len(sys.argv)>2): dirIp=sys.argv[2]
    subirFicheros(dirIp)

def sshConfig(target="insti",usuario=None):
    import zipfile, pwd, grp, stat
    if (usuario==None): usuario=username()
    #url="https://raw.githubusercontent.com/javier-iesn/prj/master/scripts/aula/root_ssh.zip"
    dest="/"+usuario+"/.ssh/"
    if DEBUG: dest=TMP_DIR+"/pr4/"
    if (not os.path.exists(dest)): os.mkdir(dest)
    salida=dest+"root_ssh.zip"
    #if (not obtenerFicheroRed(url,salida)):
    if (not obtenerFicheroGitHub("aula/root_ssh.zip",salida,"bin")):
        print("Error obteniendo fichero")
        sys.exit(4)
    zipfile.ZipFile(salida).extractall(pwd=b'tunelSsh',path=dest)   
    if (target=="yellowcircle"):
        busca="Host pc1* pc2* 10.* localhost 127.0.0.1 172.18.161.* server sp sa1 sa2 st sp2 sb sm spt sh"
        ficheroReplace(dest+"config",busca,"Host localhost 127.0.0.1 172.18.163.*")
    uid = pwd.getpwnam(usuario).pw_uid
    gid = grp.getgrnam(usuario).gr_gid
    os.chown(dest, uid, gid)
    for fich in os.listdir(dest):
        os.chown(dest+fich, uid, gid)
        os.chmod(dest+fich,stat.S_IRUSR | stat.S_IWUSR)

def cabeceraCrontab():
    return """# Edit this file to introduce tasks to be run by cron.
#
# Each task to run has to be defined through a single line
# indicating with different fields when the task will be run
# and what command to run for the task
#
# To define the time you can provide concrete values for
# minute (m), hour (h), day of month (dom), month (mon),
# and day of week (dow) or use '*' in these fields (for 'any').#
# Notice that tasks will be started based on the cron's system
# daemon's notion of time and timezones.
#
# Output of the crontab jobs (including errors) is sent through
# email to the user the crontab file belongs to (unless redirected).
#
# For example, you can run a backup of all your user accounts
# at 5 a.m every week with:
# 0 5 * * 1 tar -zcf /var/backups/home.tgz /home/
#
# For more information see the manual pages of crontab(5) and cron(8)
#
# m h  dom mon dow   command

"""

def ponerCrontab(linea):
    tmpFile=tempfile.mktemp()   
    #err,out=commands.getstatusoutput("sudo crontab -l")
    #proc=subprocess.Popen("sudo crontab -l".split(),stdout=subprocess.PIPE,stderr=subprocess.PIPE)
    proc=subprocess.Popen("crontab -l".split(),stdout=subprocess.PIPE,stderr=subprocess.PIPE)
    err,out=proc.communicate()
    out=byteArrayToStr(out); err=byteArrayToStr(err)
    if ("no crontab" in out):
        with open(tmpFile,"w") as salida:
            salida.write(cabeceraCrontab()+"\n")
    else:
        #os.system("sudo crontab -l > "+tmpFile)
        os.system("crontab -l > "+tmpFile)
    appendSiNoEsta(tmpFile,linea)
    #os.system("sudo crontab < "+tmpFile)
    os.system("crontab < "+tmpFile)
    #os.system("sudo rm "+tmpFile)
    os.system("rm "+tmpFile)
    
def lliurexUbuntuRepo():
    return [["deb http://es.archive.ubuntu.com/ubuntu trusty main universe multiverse restricted",True],
     ["deb http://es.archive.ubuntu.com/ubuntu trusty-updates main universe multiverse restricted",True],
     ["deb http://es.archive.ubuntu.com/ubuntu trusty-security main universe multiverse restricted",True]
    ]

def aptSourcesList(sources="/etc/apt/sources.list"):
    lineas=lliurexUbuntuRepo(); cont=len(lineas)
    with open(sources,"r") as fs:
        for s in fs:
            debug(s)
            if (len(s.strip()) and s.strip()[0]!="#"):
                for l in lineas:
                    if (l[0] in s):
                        l[1]=False
                        cont-=1
    if (cont>0):
        with open(sources,"a") as fs:
            for l in lineas:
                if (l[1]):
                    fs.write(l[0]+"\n")
def esTipoDebian(os):
    os=os.lower()
    debians=['debian','ubuntu','mint']
    for s in debians:
        if (os.find(s)>=0): return True
    return False

def esTipoRedHat(os):
    os=os.lower()
    redhats=['centos','redhat','suse']
    for s in redhats:
        if (os.find(s)>=0): return True
    return False
def instalarTunel():
    debeSerAdmin()
    if (len(sys.argv)>2):
        target=sys.argv[2]
    else:
        target="insti"
    sshConfig(target)
    ponerCrontab("*/5 * * * *     /root/tunelSsh.py &> /dev/null\n")
    #aptSourcesList()
    #os.system("sudo apt-get update; sudo apt-get --allow-unauthenticated -y install tor connect-proxy vnc4server")
    ops=platform.platform().lower()
    if esTipoDebian(ops):
        os.system("apt-get update; apt-get --allow-unauthenticated -y install python-crypto tor connect-proxy ssh")
    if esTipoRedHat(ops):
        os.system("yum -y install epel-release")
        os.system("yum -y install tor connect-proxy ssh")
        os.system("ln -s /usr/bin/connect-proxy /usr/bin/connect")
    #if (os.name==sysresccd): modprobe tun; emerge pycrypto; 


if ( __name__ == '__main__'):
    #if DEBUG: sys.argv=[sys.argv[0],"--start","SSH","si","/home/usuario/hostinger"]
    if (not DEBUG):
        if (len(sys.argv)>1):
            if (sys.argv[1]=="--start"):
                start()
            elif (sys.argv[1]=="--getconf"):
                obtenerFicheroIndice()
            elif (sys.argv[1]=="--install"):
                instalarTunel()
            elif (sys.argv[1]=="--wol"):
                if (len(sys.argv)>4):
                    wakeOnLan(sys.argv[2],sys.argv[3],sys.argv[4])
                elif (len(sys.argv)>3):
                    wakeOnLan(sys.argv[2],sys.argv[3])
                else:
                    print("Debes suministrar al menos 2 parámetros.")
            elif (sys.argv[1]=="--wolInsti"):
                wakeOnLanInsti(sys.argv[2])
                
        else:
            #print("hola")
            loopTunel()
