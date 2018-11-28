#!/bin/bash

while [ "$1" != "" ]; do
   ping -W 1 -c 6 10.$1.$1.$1
   EXIT_CODE=$?
   shift
done
#echo "EXIT_CODE: $EXIT_CODE"
exit $EXIT_CODE
