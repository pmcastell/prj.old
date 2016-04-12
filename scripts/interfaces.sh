ifconfig -a | grep -oE "^[a-zA-Z].*" | gawk '{print $1;}'
