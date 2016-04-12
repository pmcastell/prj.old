for d in $(dpkg -l | awk '{print $2;}' | grep -i linux | grep -iE '2\.6\.(32|35|38)'); do sudo apt-get --purge -y remove $d; done 

