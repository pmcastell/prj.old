#!/usr/bin/python
# -*- coding: utf-8 -*-


import base64


def strxor(a,b):     # xor two strings of different lengths
    if len(a) > len(b):
        return "".join([chr(ord(x) ^ ord(y)) for (x, y) in zip(a[:len(b)], b)])
    else:
        return "".join([chr(ord(x) ^ ord(y)) for (x, y) in zip(a, b[:len(a)])])


def encriptaClaveBase64(clave,passwd):
    while(len(clave)>len(passwd)):
        passwd+=passwd
    return base64.encodestring(strxor(clave,passwd)).replace("\n","")
    
def desencriptaClaveBase64(clave,passwd):
    claveCript=base64.decodestring(clave)
    while(len(claveCript)>len(passwd)):
        passwd+=passwd
    return strxor(claveCript,passwd)    
