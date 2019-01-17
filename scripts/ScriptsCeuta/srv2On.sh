#!/bin/bash
IFACE=$(ip a | grep 172.124 | awk '{print $NF;}')
echo sudo etherwake -i $IFACE 1c:1b:0d:0d:2d:71
sudo etherwake -i $IFACE 1c:1b:0d:0d:2d:71
