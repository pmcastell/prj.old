#!/usr/bin/python
# -*- coding: utf-8 -*-

# Fecha creación: 26 jul. 2017
# autor: usuario

import sys, socket, getopt, threading, subprocess
from getopt import GetoptError

listen             = False
command            = False
upload             = False
execute            = ""
target             = ""
upload_destination = ""
port               = 0

def usage():
    print
    print "BHP Net Tool"
    print
    print "Usage: "+sys.argv[0]+" -t target_host -p port"
    print "-l --listen              - listen on [host]:[port] for incoming connections"
    print "-e --execute=file-to-run - execute then given file upon receiving a connection"
    print "-c --command             - initialize a command shell"
    print "-u --upload-destination  - upon receiving a connection upload a file and write to [destination]"
    print
    print "Examples: "
    print sys.argv[0]+" -t 192.168.0.1 -p 5555 -l -c"
    print sys.argv[0]+" -t 192.168.0.1 -p 5555 -l -u=c:\\target.exe"
    print sys.argv[0]+" -t 192.168.0.1 -p 5555 -l -e=\"cat /etc/passwd\""
    print "echo 'ABCDEFGHI' | "+sys.argv[0]+" -t 192.168.11.12 -p 135"
    print 
    print
    sys.exit(0)

def client_sender(buffer):
    client = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    

def main():
    global listen, port, execute, command, upload_destination, target
    
    if (not len(sys.argv[1:])):
        usage()
    #read the command line options
    try:
        opts, args = getopt.getopt(sys.argv[1:],"hle:t:p:cu:",
            ["help","listen","execute","target","port","command","upload"])
    except GetoptError as err:
        print str(err)
        usage()
    for o,a in opts:
        if o in ("-h","--help"):
            usage()
        elif o in ("-l","--liten"):
            listen = True
        elif o in ("-e","--execute"):
            execute = a
        elif o in ("-u","--upload"):
            upload_destination = a
        elif o in ("-c","--command"):
            command = True
        elif o in ("-t","--target"):
            target = a
        elif o in ("-p","--port"):
            port = a
        else:
            assert False,"Unhandled option"
        
    #are we going to listen or just send data from stdin?
    if (not listen and len(target) and port > 0):
        #read in the buffer from the commandline this will block, so send CTRL-D
        #if not sending input to stdin
        buffer = sys.stdin.read()
        #send data off
        client_sender(buffer)
        
    #we are going to listen and potentially upload things, execute commands, and drop
    #a shell back depending on our command line options above
    if (listen):
        server_loop()
        

main()        