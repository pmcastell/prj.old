DISP=$(ls /dev/dvdrw* | head -1)
DISP=$(ls /dev/sr* | head -1)
dvd+rw-format -blank $DISP
