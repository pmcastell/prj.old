#!/bin/bash

sudo etherwake -i eth0 00:1c:c0:ec:ea:9c
sudo wakeonlan -i 10.2.1.247 00:1c:c0:ec:ea:9c
sleep 1
sudo etherwake -i eth0 00:1c:c0:ec:d3:9a
sudo wakeonlan -i 10.2.1.248 00:1c:c0:ec:d3:9a
sleep 1
sudo etherwake -i eth0 00:03:0d:49:6b:67
sudo wakeonlan -i 10.2.1.249 00:03:0d:49:6b:67



