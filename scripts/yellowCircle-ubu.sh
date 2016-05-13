#!/bin/bash
sshConfig() {
echo "Host localhost 127.0.0.1 172.18.163.*
	CheckHostIP no
	UserKnownHostsFile /dev/null 
	StrictHostKeyChecking no
	Compression yes
	Protocol 2
	ProxyCommand none 
	User lliurex	


Host *
	CheckHostIP no
	UserKnownHostsFile /dev/null 
	StrictHostKeyChecking no
	Compression yes
	Protocol 2
	ProxyCommand connect -4 -S 127.0.0.1:9050 $(tor-resolve %h localhost:9050) %p
" > /root/.ssh/config
}

if [ "$(whoami)" != "root" ]; then sudo $0 "$@"; exit $?; fi
echo ubu > /etc/hostname
hostname ubu
sudo apt-get install tor connect-proxy vnc4server icewm
wget -c https://raw.githubusercontent.com/javier-iesn/prj/master/scripts/aula/tunelSsh6.sh -O /root/tunelSsh6.sh
echo '*/5 *   *   *   *    root  /net/server-sync/home/teachers/franav/Desktop/Dropbox/Instituto/prj/scripts/aula/tunelSsh6.sh &> /dev/null' >> /etc/crontab

