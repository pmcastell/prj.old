#!/bin/bash

ethtool -s eth0 wol g

#auto eth0
#iface eth0 inet static
#        address 10.0.0.1
#        netmask 255.255.255.0
#        gateway 10.0.0.138
#        up ethtool -s eth0 wol g
