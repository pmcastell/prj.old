#!/usr/bin/python

from Crypto.Cipher import AES

key = "140b41b22a29beb4061bda66b6747e14".decode('hex')
iv = "4ca00ff4c898d61e1edbf1800618fb2828a226d160dad07883d04e008a7897ee".decode('hex')
dcrpt = AES.new(key, AES.MODE_CBC, iv[:16])
print(dcrpt.decrypt(iv))
