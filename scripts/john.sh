#BASE=""
#unshadow $BASE/etc/passwd $BASE/etc/shadow > /tmp/john.txt
#john /tmp/john.txt
cd /home/usuario/programas/john/run
./john "$@"
