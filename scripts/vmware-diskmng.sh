#!/bin/bash
#Cambiar un disco monolítico a un disco split - 2GB (-t 1)
#vmware-diskmanager -r "$1" -t 1  "$2"
vmware-vdiskmanager -r "$1" -t 1  "$2"
