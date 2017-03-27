#!/usr/bin/python2.7
# -*- coding: utf-8 -*-

from Crypto.Cipher import AES
from Crypto.Util import Counter
from utilidades import Block



def d_AES_nbl_bin(key, ciphertext):
    d=AES.new(key,AES.MODE_CBC,ciphertext[:16])
    return d.decrypt(ciphertext[16:])

def d_AES_CTR_nbl_bin(key, ciphertext):
    ctr=Counter.new(16*8, initial_value=long(ciphertext[:16].encode("hex"), 16))
    d=AES.new(key, AES.MODE_CTR, counter = ctr)
    return d.decrypt(ciphertext[16:])


# t1 = "7b50baab07640c3d".decode("hex")
# t2 = "ac343a22cea46d60".decode("hex")
# print(strxor(t1, t2).encode("hex"))
# t1 = "e86d2de2e1387ae9"
# t2 = "1792d21db645c008"
# t1 = t1.decode("hex")
# t2 = t2.decode("hex")
# print(strxor(t1, t2).encode("hex"))
# t1 = "7c2822ebfdc48bfb"
# t2 = "325032a9c5e2364b"
# t1 = t1.decode("hex")
# t2 = t2.decode("hex")
# print(strxor(t1, t2).encode("hex"))
#
# t1 = "4af532671351e2e1"
# t2 = "87a40cfa8dd39154"
# t1 = t1.decode("hex")
# t2 = t2.decode("hex")
# print(strxor(t1,t2).encode("hex"))
#

#d_AES_nbl = unhexlify_args(d_AES_nbl_bin)
#d_AES_CTR_nbl = unhexlify_args(d_AES_CTR_nbl_bin)
def cbcDecrypt(k,ct,pt):
    print(d_AES_nbl_bin(k.decode('hex'),ct.decode('hex')))
    bk = Block(k)
    IV = Block(ct.decode("hex")[:16])
    bt = Block(pt)
    bct = (IV + bt).encAEScbc(bk)
    bpt = bct.decAEScbc(bk)
    print(str(bpt).decode("hex"))

k="140b41b22a29beb4061bda66b6747e14"
ct = "4ca00ff4c898d61e1edbf1800618fb2828a226d160dad07883d04e008a7897ee\
2e4b7465d5290d0c0e6c6822236e1daafb94ffe0c5da05d9476be028ad7c1d81"
pt = "Basic CBC mode encryption needs padding."
cbcDecrypt(k,ct,pt)    

k="140b41b22a29beb4061bda66b6747e14"
ct = "5b68629feb8606f9a6667670b75b38a5b4832d0f26e1ab7da33249de7d4afc48\
e713ac646ace36e872ad5fb8a512428a6e21364b0c374df45503473c5242a253"
pt ="Our implementation uses rand. IV"
cbcDecrypt(k,ct,pt)

def ctrDecrypt(k,ct,pt):
    bk=Block(k)
    bct=Block(ct)
    return bct.decAESctr(bk)

k = "36f18357be4dbd77f050515c73fcf9f2"
ct = "69dda8455c7dd4254bf353b773304eec0ec7702330098ce7f7520d1cbbb20fc3\
88d1b0adb5054dbd7370849dbf0b88d393f252e764f1f5f7ad97ef79d59ce29f5f51eeca32eabedd9afa9329"
pt = ""
print(d_AES_CTR_nbl_bin(k.decode('hex'),ct.decode('hex')))
print(str(ctrDecrypt(k,ct,pt)).decode("hex"))

k = "36f18357be4dbd77f050515c73fcf9f2"
ct = "770b80259ec33beb2561358a9f2dc617e46218c0a53cbeca695ae45faa8952aa\
0e311bde9d4e01726d3184c34451"
print(d_AES_CTR_nbl_bin(k.decode('hex'),ct.decode('hex')))
print(str(ctrDecrypt(k,ct,pt)).decode("hex"))



