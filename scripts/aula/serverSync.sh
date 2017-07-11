#!/bin/bash

sudo /scripts/scpresume.sh root@sa1:/net/OpenSysClone /net/OpenSysClone
sudo /scripts/scpresume.sh root@sa1:/var/www/ipxeboot/pxemenu.d /var/www/ipxeboot/pxemenu.d
sudo -u lliurex /scripts/scpresume.sh lliurex@sa1:/home/lliurex/imagenes /home/lliurex/imagenes
sudo -u lliurex /scripts/scpresume.sh lliurex@sa1:/home/lliurex/VirtualBoxVMs/ /home/lliurex/VirtualBoxVMs/

