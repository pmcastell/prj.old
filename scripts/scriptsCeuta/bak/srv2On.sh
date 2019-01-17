#!/bin/bash

IFACE=$(ip a | grep 172.124 | awk '{print $NF;}')
sudo etherwake -i $IFACE 20:cf:30:90:dc:18
