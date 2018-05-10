#!/bin/bash

time (dd_rescue /dev/sdd - | pigz -5 -p 8 | gpg --batch --symmetric --passphrase galileo > /media/usuario/BACKUPS/tmp/4gb-pigz.gz.gpg)
time (dd_rescue /dev/sdd - | gzip -5 | gpg --batch --symmetric --passphrase galileo > /media/usuario/BACKUPS/tmp/4gb-gzip.gz.gpg)
time (dd_rescue /dev/sdd - | 7za -mx -si -pgalileo a /media/usuario/BACKUPS/tmp/4gb-7za.7z)

time (dd_rescue /dev/sdb - | 7za -si -mx -pgalileoeraungenio1564 -mx=9 a /media/usuario/BACKUPS/tmp/discoJorge.7z)
time (dd_rescue /dev/sdb - | 7za -si -mx -pgalileoeraungenio1564 -mx=3 a /media/usuario/BACKUPS/tmp/discoJorge.7z)
time (dd_rescue /dev/sdb - | 7za -si -pgalileoeraungenio1564 -mx=9 -m0=LZMA2 -mmt=8 a /media/usuario/BACKUPS/tmp/discoJorge.7z)
