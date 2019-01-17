#!/bin/bash
IFACE=$(ip a | grep 172.124 | awk '{print $NF;}')
sudo etherwake -i $IFACE 1c:1b:0d:0d:2d:71
