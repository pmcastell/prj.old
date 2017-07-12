#!/bin/bash
if [ "$1" != "" ]; then
   wget -c https://raw.githubusercontent.com/javier-iesn/prj/master/scripts/$1
else
   wget -cr https://raw.githubusercontent.com/javier-iesn/prj/master/scripts/
fi
