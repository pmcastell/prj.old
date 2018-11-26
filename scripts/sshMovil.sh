#!/bin/bash


[ "$(ip a | grep 172.124)" != "" ] && IP="172.124.117.96"
[ "$(ip a | grep 172.16.1)" != "" ] && IP="172.16.1.6" && echo $IP
[ "$(ip a | grep 172.18.1)" != "" ] && IP="172.18.1.6" && echo $IP
PORT="8022"
if [ "$1" = "-c" ]; then
    echo scp -P $PORT $2 $IP:./
    scp -P $PORT $2 $IP:./
elif [ "$1" = "-r" ]; then
    echo scp -P $PORT $IP:$2 ./
    scp -P $PORT $IP:$2 ./
else
    echo ssh -p $PORT $IP
    ssh -p $PORT $IP
fi
