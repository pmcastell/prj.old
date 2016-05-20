sudo du -aBM / 2>/dev/null | sort -nr | grep -v /media | grep -v /l | head -n 75 > /tmp/fichGrandes.txt
