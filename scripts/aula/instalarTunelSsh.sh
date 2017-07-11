sshConfig() {
if [ "$1" = "yellowcircle" ]; then
   echo "Host localhost 127.0.0.1 172.18.163.*" > /root/.ssh/config
elif [ "$1" = "insti" ]; then
   echo "Host 10.2.1.* pc1* pc2* 10.10.10.* localhost 127.0.0.1 172.18.161.* server sp sa1 sa2 st sp2 sb sm spt sh
	User lliurex"  > /root/.ssh/config
else 
   echo "Host localhost 127.0.0.1" > /root/.ssh/config
fi   	  
echo "CheckHostIP no
	UserKnownHostsFile /dev/null 
	StrictHostKeyChecking no
	Compression yes
	Protocol 2
	ProxyCommand none 
	Forwardx11 yes

Host *
	CheckHostIP no
	UserKnownHostsFile /dev/null 
	StrictHostKeyChecking no
	Compression yes
	Protocol 2
	ProxyCommand connect -4 -S 127.0.0.1:9050 \$(tor-resolve %h localhost:9050) %p
" >> /root/.ssh/config
if [ "$(cat /etc/ssh/sshd_config | grep 'PermitTunnel')" != "" ]; then
   sed '/PermitTunnel/d' /etc/ssh/sshd_config
fi   
echo PermitTunnel yes >> /etc/ssh/sshd_config
}

sshRsa() {
echo "-----BEGIN RSA PRIVATE KEY-----
MIIEogIBAAKCAQEAwgZZM4lKh4n46R91sVjR1jyDl6c3XUtQgne198u23lswkcSe
PSBHcKGPHE2MrQNeA/fPaBtxFTTNfLfoahCqP2BH/7KVuha84vnNUwEjhVwMAWen
rRytnlrJ9pWZF/OTobf2cirFEBH6JXCyXzwNIzrqgZ11p+JGG+eXD7VJ2UTtlXqE
QAxKYMbctKIXYeQ90OoGUXexkju2ZCVFqKFjo8X+JFEiUm3epgDcGth31+R524DU
ivDVK8fvnuLhsyWSMjV7mUs/GzodsYrgZxuyKwfpEyEx/LsrPoLyxttpNOpPcMbS
LEfhxMx4DXNvy1JiZQ8w79ufpEXxfzGHfp6XxQIDAQABAoIBABc16zXfFqtgNgat
XMb6eR/H3XYm6tieSeNRrtMV66pt/kFx4/wsAF/Y8+XO4SVRYc3Xt1gc8Eo5Jtvk
pEkL9VHfkr1j/8VjMvV0LLvhRv6NJGKGo+hxpdNryP1WqBnVabPO7gx6UVi6zdHs
yWnbJTcxtRwMWibbSsfLzKZ0aYE0KC4xr5o/gYc503XIYcq8o5PzFwbnW+1PuVMy
IgRBkQV0SAHd1dUr/qn4Gm+1LYvJYGO8ZMQBs3h2UUzsUuJJyPTwxeO6hTb3Pn7B
NkaWArtmdPruf02zd1Hbs8diP9pCux3CRO3ZmBfgKLysnuWGzjrjpy2yxsW7086R
PSTuDAECgYEA88kZYGeFD9OIx19WnsPpLm1SjxT7kWNiD8PAal8Q0TuUWiW+XiC/
2qBcjUtOJqZWlYo09oYuuNo21AsGxD9a7+K5GFNBSc2iiyIoMc/KB6d/H2J7U319
sRlTq1Uh1jO0gClKYE9ltOkR9XHQvfBzWSgO9j/SO5nSpvqRBjh2k90CgYEAy77+
/db9cKqmvyRSBAJXlq8hCKxXdjwNrdhxv2OR94hpoCL0NoMfQwsq9LHF900EnNYa
er1+eHXRkpcSdL8iCcwIkqthcAezFX+4uMkSqv18wlSVVsjQpM/tRDVySO5RUuue
1rELxu7KKeUgOiTfb5U7uI3An9g12l/kCnoJKQkCgYBacJt6eU0S7v4KQONIqROD
xsjfDgC6Ei2Z/ZwPmk8J4FmMC/6QuwOR3vYW8P4ANqtedNRrNKJ2r3Wxupl7Cqn4
3pqeXJyW/bO0fCp/OEASEOCHSyjHc4HCxsuUf1O7PJlVBnxyOGJZ+DFSZjPzkxWn
2MWcM1cSB2Hv0nsk3KOY/QKBgAmRsNwvKrwN58L2a7uweHilRQHpRjpRW7scfUsl
3AcqKvvQaHPe+KrnbV8MsmWJAnxzOc8l8zipH+yol79P8sNPB+ALmInwcqLB2uUi
3HOXcDKYUmM/Dz2OXnBjqjhuqPciBdXDfvAa6vfIwRi5/px4/9MNJUzclLrq4gTz
8lYxAoGAFbQ+mza8Q1Kwm5AqgQ0X1P0/x8WSwYl9HZlCd2UhsoeIOv1ovnNny/HN
19OPWYCK2a05NwHXA2u9YMbfx2hqo5LxcEzPRzJs16SyTdB7H3kLw5DSPPxGwFD7
6kShNGZ8c5fhx3WATUbw0RO90jmr0MoNYn5jdd/T/6ytAS+x/Fk=
-----END RSA PRIVATE KEY-----

" > /root/.ssh/id_rsa
chmod 600 /root/.ssh/id_rsa
echo "ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQDCBlkziUqHifjpH3WxWNHWPIOXpzddS1CCd7X3y7beWzCRxJ49IEdwoY8cTYytA14D989oG3EVNM18t+hqEKo/YEf/spW6Frzi+c1TASOFXAwBZ6etHK2eWsn2lZkX85Oht/ZyKsUQEfolcLJfPA0jOuqBnXWn4kYb55cPtUnZRO2VeoRADEpgxty0ohdh5D3Q6gZRd7GSO7ZkJUWooWOjxf4kUSJSbd6mANwa2HfX5HnbgNSK8NUrx++e4uGzJZIyNXuZSz8bOh2xiuBnG7IrB+kTITH8uys+gvLG22k06k9wxtIsR+HEzHgNc2/LUmJlDzDv25+kRfF/MYd+npfF franav@aulasrv1" > /root/.ssh/id_rsa.pub
}
crontab_encabezado() {
echo "# Edit this file to introduce tasks to be run by cron.
# 
# Each task to run has to be defined through a single line
# indicating with different fields when the task will be run
# and what command to run for the task
# 
# To define the time you can provide concrete values for
# minute (m), hour (h), day of month (dom), month (mon),
# and day of week (dow) or use '*' in these fields (for 'any').# 
# Notice that tasks will be started based on the cron's system
# daemon's notion of time and timezones.
# 
# Output of the crontab jobs (including errors) is sent through
# email to the user the crontab file belongs to (unless redirected).
# 
# For example, you can run a backup of all your user accounts
# at 5 a.m every week with:
# 0 5 * * 1 tar -zcf /var/backups/home.tgz /home/
# 
# For more information see the manual pages of crontab(5) and cron(8)
# 
# m h  dom mon dow   command"
}
instalaScriptTunelSsh() {
   SCRIPT="tunelSsh6.sh"
   wget https://raw.githubusercontent.com/javier-iesn/prj/master/scripts/aula/$SCRIPT -O /root/$SCRIPT
   wget https://raw.githubusercontent.com/javier-iesn/prj/master/scripts/aula/sshTun.sh -O /root/sshTun.sh
   TEMP=$(tempfile)
   if [ "$(sudo crontab -l | grep -i 'no crontab')" != "" ] || [ "$(sudo crontab -l)" = "" ]; then crontab_encabezado > $TEMP; fi 
   (cat $TEMP; sudo crontab -l | grep -v tunelSsh6 ; echo '*/5 *   *   *   *    /root/$SCRIPT &> /dev/null' ) | sudo crontab -
   rm $TEMP
   chmod +x /root/$SCRIPT
   chmod +x /root/sshTun.sh
}
aptSourcesList() {
   S1="deb http://es.archive.ubuntu.com/ubuntu trusty main universe multiverse restricted"
   S2="deb http://es.archive.ubuntu.com/ubuntu trusty-updates main universe multiverse restricted"
   S3="deb http://es.archive.ubuntu.com/ubuntu trusty-security main universe multiverse restricted"
   SOURCES="/etc/apt/sources.list"
   if [ "$(cat $SOURCES | grep "$S1")" = "" ]; then echo $S1 >> $SOURCES; fi
   if [ "$(cat $SOURCES | grep "$S2")" = "" ]; then echo $S2 >> $SOURCES; fi
   if [ "$(cat $SOURCES | grep "$S3")" = "" ]; then echo $S3 >> $SOURCES; fi
}
if [ "$(whoami)" != "root" ]; then sudo $0 "$@"; exit $?; fi
sshConfig insti
sshRsa
instalaScriptTunelSsh
aptSourcesList
echo 'net.ipv4.ip_forward = 1' >> /etc/sysctl.conf
sudo apt-get update
sudo apt-get -y install tor connect-proxy vnc4server wireshark

