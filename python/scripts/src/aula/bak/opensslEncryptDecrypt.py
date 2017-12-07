#!/usr/bin/python
# -*- coding: utf-8 -*-

# Fecha creación: 29 jul. 2017
# autor: usuario

from hashlib import md5
from Crypto.Cipher import AES
from Crypto import Random
from Crypto.Util import Counter
import base64
import tempfile
import time
import sys

def derive_key_and_iv(password, salt, key_length, iv_length):
    d = d_i = ''
    while len(d) < key_length + iv_length:
        d_i = md5(d_i + password + salt).digest()
        d += d_i
    return d[:key_length], d[key_length:key_length+iv_length]

def encryptCBC(in_file, out_file, password, key_length=32):
    bs = AES.block_size
    salt = Random.new().read(bs - len('Salted__'))
    key, iv = derive_key_and_iv(password, salt, key_length, bs)
    cipher = AES.new(key, AES.MODE_CBC, iv)
    out_file.write('Salted__' + salt)
    finished = False
    while not finished:
        chunk = in_file.read(1024 * bs)
        if len(chunk) == 0 or len(chunk) % bs != 0:
            padding_length = (bs - len(chunk) % bs) or bs
            chunk += padding_length * chr(padding_length)
            finished = True
        out_file.write(cipher.encrypt(chunk))

def decryptCBC(in_file, out_file, password, key_length=32):
    bs = AES.block_size
    salt = in_file.read(bs)[len('Salted__'):]
    key, iv = derive_key_and_iv(password, salt, key_length, bs)
    cipher = AES.new(key, AES.MODE_CBC, iv)
    next_chunk = ''
    finished = False
    while not finished:
        chunk, next_chunk = next_chunk, cipher.decrypt(in_file.read(1024 * bs))
        if len(next_chunk) == 0:
            padding_length = ord(chunk[-1])
            chunk = chunk[:-padding_length]
            finished = True
        out_file.write(chunk)


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
    out_file.close()

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


decryptCTR()    
