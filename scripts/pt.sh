#!/bin/bash

while [ "$1" != "" ]; do
   ping -W 1 -c 6 10.$1.$1.$1
   shift
done
