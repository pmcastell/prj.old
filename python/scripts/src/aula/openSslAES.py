#!/usr/bin/python
# -*- coding: utf-8 -*-

# Fecha creación: 17 jul. 2017
# autor: usuario

import re
from Crypto.Cipher import AES
from Crypto.Util import Counter

# ================================================================
# get_key_and_iv
# ================================================================
def get_key_and_iv(password, salt, klen=32, ilen=16, msgdgst='md5'):
    '''
    Derive the key and the IV from the given password and salt.

    This is a niftier implementation than my direct transliteration of
    the C++ code although I modified to support different digests.

    CITATION: http://stackoverflow.com/questions/13907841/implement-openssl-aes-encryption-in-python

    @param password  The password to use as the seed.
    @param salt      The salt.
    @param klen      The key length.
    @param ilen      The initialization vector length.
    @param msgdgst   The message digest algorithm to use.
    '''
    # equivalent to:
    #   from hashlib import <mdi> as mdf
    #   from hashlib import md5 as mdf
    #   from hashlib import sha512 as mdf
    mdf = getattr(__import__('hashlib', fromlist=[msgdgst]), msgdgst)
    password = password.encode('ascii', 'ignore')  # convert to ASCII

    try:
        maxlen = klen + ilen
        keyiv = mdf(password + salt).digest()
        tmp = [keyiv]
        while len(tmp) < maxlen:
            tmp.append( mdf(tmp[-1] + password + salt).digest() )
            keyiv += tmp[-1]  # append the last byte
        key = keyiv[:klen]
        iv = keyiv[klen:klen+ilen]
        return key, iv
    except UnicodeDecodeError:
        return None, None


# ================================================================
# encrypt
# ================================================================
def encrypt(password, plaintext, chunkit=True, msgdgst='md5'):
    '''
    Encrypt the plaintext using the password using an openssl
    compatible encryption algorithm. It is the same as creating a file
    with plaintext contents and running openssl like this:

    $ cat plaintext
    <plaintext>
    $ openssl enc -e -aes-256-cbc -base64 -salt \\
        -pass pass:<password> -n plaintext

    @param password  The password.
    @param plaintext The plaintext to encrypt.
    @param chunkit   Flag that tells encrypt to split the ciphertext
                     into 64 character (MIME encoded) lines.
                     This does not affect the decrypt operation.
    @param msgdgst   The message digest algorithm.
    '''
    salt = os.urandom(8)
    key, iv = get_key_and_iv(password, salt, msgdgst=msgdgst)
    if key is None:
        return None

    # PKCS#7 padding
    padding_len = 16 - (len(plaintext) % 16)
    if isinstance(plaintext, str):
        padded_plaintext = plaintext + (chr(padding_len) * padding_len)
    else: # assume bytes
        padded_plaintext = plaintext + (bytearray([padding_len] * padding_len))

    # Encrypt
    cipher = AES.new(key, AES.MODE_CTR, iv)
    ciphertext = cipher.encrypt(padded_plaintext)

    # Make openssl compatible.
    # I first discovered this when I wrote the C++ Cipher class.
    # CITATION: http://projects.joelinoff.com/cipher-1.1/doxydocs/html/
    openssl_ciphertext = b'Salted__' + salt + ciphertext
    b64 = base64.b64encode(openssl_ciphertext)
    if not chunkit:
        return b64

    LINELEN = 64
    chunk = lambda s: b'\n'.join(s[i:min(i+LINELEN, len(s))]
                                for i in range(0, len(s), LINELEN))
    return chunk(b64)


# ================================================================
# decrypt
# ================================================================
def decrypt(password, ciphertext, msgdgst='md5'):
    '''
    Decrypt the ciphertext using the password using an openssl
    compatible decryption algorithm. It is the same as creating a file
    with ciphertext contents and running openssl like this:

    $ cat ciphertext
    # ENCRYPTED
    <ciphertext>
    $ egrep -v '^#|^$' | \\
        openssl enc -d -aes-256-cbc -base64 -salt -pass pass:<password> -in ciphertext
    @param password   The password.
    @param ciphertext The ciphertext to decrypt.
    @param msgdgst    The message digest algorithm.
    @returns the decrypted data.
    '''

    # unfilter -- ignore blank lines and comments
    if isinstance(ciphertext, str):
        filtered = ''
        nl = '\n'
        re1 = r'^\s*$'
        re2 = r'^\s*#'
    else:
        filtered = b''
        nl = b'\n'
        re1 = b'^\\s*$'
        re2 = b'^\\s*#'

    for line in ciphertext.split(nl):
        line = line.strip()
        if re.search(re1,line) or re.search(re2, line):
            continue
        filtered += line + nl

    # Base64 decode
    raw = base64.b64decode(filtered)
    assert(raw[:8] == b'Salted__' )
    salt = raw[8:16]  # get the salt

    # Now create the key and iv.
    key, iv = get_key_and_iv(password, salt, msgdgst=msgdgst)
    if key is None:
        return None

    # The original ciphertext
    ciphertext = raw[16:]

    # Decrypt
    #ctr=Counter.new(32*8, initial_value=long(ciphertext[:16].encode("hex"), 16))
    ctr=Counter.new(32*8,prefix=iv)
    cipher = AES.new(key, AES.MODE_CTR, counter=ctr)
    padded_plaintext = cipher.decrypt(ciphertext)
    print padded_plaintext

    if isinstance(padded_plaintext, str):
        padding_len = ord(padded_plaintext[-1])
    else:
        padding_len = padded_plaintext[-1]
    plaintext = padded_plaintext[:-padding_len]
    return plaintext