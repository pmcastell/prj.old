BASE=""
unshadow $BASE/etc/passwd $BASE/etc/shadow > /tmp/john.txt
john /tmp/john.txt
