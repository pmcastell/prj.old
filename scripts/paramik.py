#!/usr/bin/python

import paramiko
ssh = paramiko.SSHClient()
ssh.set_missing_host_key_policy(paramiko.AutoAddPolicy())
ssh.connect('10.10.10.101', username='usuario', password='15galileo64')
(stdi,stdo,stde)=ssh.exec_command("ls -l")
for l in stdo.readlines(): print l,
ssh.close()
