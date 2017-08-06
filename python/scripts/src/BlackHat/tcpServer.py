#!/usr/bin/python
# -*- coding: utf-8 -*-

# Fecha creación: 25 jul. 2017
# autor: usuario

import socket, threading

def handle_client(client_socket):
    request = client_socket.recv(1024)
    print "[*] Received: %s" % request
    client_socket.send("ACK!")
    client_socket.close()

bind_ip="0.0.0.0"
bind_port=9999

server=socket.socket(socket.AF_INET,socket.SOCK_STREAM)
server.bind((bind_ip,bind_port))
server.listen(5)
print "[*] Listening on %s,%d" % (bind_ip,bind_port)

while True:
    client,addr=server.accept()
    print "[*] Accepted connection from: %s:%d " % addr
    client_handler=threading.Thread(target=handle_client,args=(client,))
    client_handler.start()
    