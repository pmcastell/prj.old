#!/bin/bash

#ifconfig -a | grep -oE "^[a-zA-Z].*" | gawk '{print $1;}'
ifconfig -a -s | tail -n +2 | awk '{print $1;}'
