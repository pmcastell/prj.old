#!/bin/bash

#$sudo fdisk -lu /media/usuario/678881953B427B1B/COPIAS/ESPACIO/mint.img
#
#Disk /media/usuario/678881953B427B1B/COPIAS/ESPACIO/mint.img: 10 GB, 10737418240 bytes
#4 heads, 32 sectors/track, 163840 cylinders, total 20971520 sectors
#Units = sectors of 1 * 512 = 512 bytes
#
#                                                  Device Boot      Start         End      Blocks   Id  System 
#/media/usuario/678881953B427B1B/COPIAS/ESPACIO/mint.img1   *        2048    18874367     9436912   83  Linux
#/media/usuario/678881953B427B1B/COPIAS/ESPACIO/mint.img2        18876414    20969471     1046528    5  Extended
#/media/usuario/678881953B427B1B/COPIAS/ESPACIO/mint.img5        18876416    20969471     1046464   82  Linux swap

sudo losetup /dev/loop0 /media/usuario/678881953B427B1B/COPIAS/ESPACIO/mint.img -o $(( 2048 * 512 ))
sudo mount -o loop,offset=$(( 2048 * 512 )) /media/usuario/678881953B427B1B/COPIAS/ESPACIO/mint.img /mnt

