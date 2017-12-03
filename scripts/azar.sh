#!/bin/bash

CAD=$(dd_rescue  -m $1 /dev/urandom - 2>/dev/null | base64)
echo ${CAD:0:$1}
