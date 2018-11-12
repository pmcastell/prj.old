#!/bin/bash

sudo systemctl start mysql
sudo systemctl start apache2
firefox -private http://server/moodle
