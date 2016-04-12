ls -l | awk '{print "sudo ln -s "$10" /usr/local/bin/"$8;}'
