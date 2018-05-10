#!/bin/bash

#[ "$(whoami)" != "root" ] && sudo $0 $@ && exit $?
sync
#time sh -c "dd if=/dev/zero of=testfile bs=100k count=1k  && sudo sync" 
#TESTFILE="/tmp/urandomTestDiskPrng.bin.bin.bin"
[ "$1" = "" ] && BS="100k" || BS="$1"
#dd if=/dev/urandom of=$TESTFILE bs=$BS count=1K &>/dev/null && sync
time bash -c "dd if=/dev/urandom of=testfile bs=$BS count=1K &>/dev/null && sync"
#time cat $TESTFILE > testfile



