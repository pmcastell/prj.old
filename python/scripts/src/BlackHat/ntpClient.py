#!/usr/bin/python
# -*- coding: utf-8 -*-

# Fecha creación: 25 jul. 2017
# autor: usuario
import sys
import socket
import struct, time

targetHost=sys.argv[1]
targetPort=123
msg='\x1b' + 47 * '\0'
client=socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
client.sendto(msg,(targetHost,targetPort))
data,addr=client.recvfrom(1024)

TIME1970 = 2208988800L
t = struct.unpack( "!12I", data )[10]
t -= TIME1970
print "\t%s" % time.ctime(t)
