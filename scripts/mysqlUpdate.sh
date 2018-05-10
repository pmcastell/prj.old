#!/bin/bash

sudo mkdir -p /var/run/mysqld
sudo chown mysql:mysql /var/run/mysqld
sudo mysqld_safe &
sudo apt-get -f install
