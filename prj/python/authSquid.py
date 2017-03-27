#!/usr/bin/python
 
import sys
import socket
"""USAGE:The function returns True if the user and passwd match False otherwise"""
def matchpasswd(login,passwd):
    # Write your own function definition. 
    # Use mysql, files, /etc/passwd or some service or whatever you want
    if (login == "usuario" and passwd == "usuario" ):
	return True
    else:
	return False
 
while True:
    # read a line from stdin
    line = sys.stdin.readline()
    # remove '\n' from line
    line = line.strip()
    # extract username and password from line
    username = line[:line.find(' ')]
    password = line[line.find(' ')+1:]
 
    if matchpasswd(username, password):
        sys.stdout.write('OK\n')
    else:
        sys.stdout.write('ERR\n')
    # Flush the output to stdout.
    sys.stdout.flush()

