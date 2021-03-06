#!/bin/bash

ESTA=$(sudo cat /etc/sudoers | grep "lliurex ALL = NOPASSWD: ALL")
if [ "$ESTA" = "" ]; then
   echo lliurex ALL = NOPASSWD: ALL >> /etc/sudoers
fi
sudo mkdir /home/lliurex/.ssh
sudo echo "ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQDCBlkziUqHifjpH3WxWNHWPIOXpzddS1CCd7X3y7beWzCRxJ49IEdwoY8cTYytA14D989oG3EVNM18t+hqEKo/YEf/spW6Frzi+c1TASOFXAwBZ6etHK2eWsn2lZkX85Oht/ZyKsUQEfolcLJfPA0jOuqBnXWn4kYb55cPtUnZRO2VeoRADEpgxty0ohdh5D3Q6gZRd7GSO7ZkJUWooWOjxf4kUSJSbd6mANwa2HfX5HnbgNSK8NUrx++e4uGzJZIyNXuZSz8bOh2xiuBnG7IrB+kTITH8uys+gvLG22k06k9wxtIsR+HEzHgNc2/LUmJlDzDv25+kRfF/MYd+npfF franav@aula1srv franav@aula2srv" >> /home/lliurex/.ssh/authorized_keys
echo "Host 10.2.1.* pc1* pc2* server
	CheckHostIP no
	UserKnownHostsFile /dev/null 
	StrictHostKeyChecking no
	Compression yes
	Protocol 2
	ProxyCommand none 
	ForwardX11 yes
" > /home/lliurex/.ssh/config
sudo chown -R lliurex:lliurex /home/lliurex/.ssh
sudo echo sudo x11vnc -noshm -24to32 -connect_or_exit server:\$1 -auth /var/run/lightdm/root/:0 -display :0 > /usr/local/bin/pantalla
sudo chmod +x /usr/local/bin/pantalla
PCNAME=$(ifconfig | grep inet | grep 10.2.1 | awk '{print $2;}' | awk -F '.' '{print $4;}')
PCNAME="pc${PCNAME:1:2}"
sudo bash -c "echo $PCNAME > /etc/hostname"
