#!/usr/bin/env bash

# gsettings list-recursively org.gnome.system.proxy

# Change de ip address and port number accordingly.
function myProxyOn() {
    gsettings set org.gnome.system.proxy mode 'manual' # ' manual / nome / automatic '
    gsettings set org.gnome.system.proxy.http host '10.0.0.1'
    gsettings set org.gnome.system.proxy.http port 8080
    gsettings set org.gnome.system.proxy.https host '10.0.0.1'
    gsettings set org.gnome.system.proxy.https port 8080
    gsettings set org.gnome.system.proxy.ftp host '10.0.0.1'
    gsettings set org.gnome.system.proxy.ftp port 8080

    echo "Configuração do 'System Proxy' settada para 'manual', com host 10.0.0.1, port 8080."
}

function myProxyOff() {
    gsettings set org.gnome.system.proxy mode 'none' # ' manual / nome / automatic '
    echo "Proxy cofigurado para 'none'."
}

function proxyOn() { # {{{
    #echo -n 'Username: '
    #read -e username
    #echo -n 'Password: '
    #read -es password
    #echo ''
    echo 'Setting variable "http_proxy"...'
    #export http_proxy="http://$username:$password@172.17.0.1:8080/"
    export http_proxy="http://10.0.0.1:8080/"
    echo 'Setting variable "https_proxy..."'
    export https_proxy="https://10.0.0.1:8080/"
    echo 'Setting variable "ftp_proxy"...'
    #export ftp_proxy="http://$username:$password@172.17.0.1:8080/"
    export ftp_proxy="ftp://10.0.0.1:8080/"
    echo 'Done!'
}

function proxyOff() {
    unset HTTP_PROXY
    unset http_proxy
    unset FTP_proxy
    unset ftp_proxy
    echo -e "\nProxy environment variables removed!"
}

