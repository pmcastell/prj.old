#!/bin/bash

IFACE=$(ip a | grep 172.124 | awk '{print $NF;}')
echo sudo etherwake -i $IFACE 20:cf:30:90:dc:18
sudo etherwake -i $IFACE 20:cf:30:90:dc:18
