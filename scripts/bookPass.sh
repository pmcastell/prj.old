rm /home/usuario/freeVpns/book/pass.txt
echo vpnbook > /home/usuario/freeVpns/book/pass.txt
#echo $(wget http://www.vpnbook.com/freevpn -O - 2> /dev/null | grep -i password | grep '<strong>' | head -1 | awk -F '<strong>' '{print $2;}' | awk -F '</strong>' '{ print $1;}') 
#echo $(wget http://www.vpnbook.com/freevpn -O - 2> /dev/null | grep -i password | grep '<strong>' | head -1 | awk -F '<strong>' '{print $2;}' | awk -F '</strong>' '{ print $1;}') >> /home/usuario/freeVpns/book/pass.txt
while [ "$PASS" = "" ];
do
   #PASS=$(wget --timeout=10 http://www.vpnbook.com/freevpn -O - 2> /dev/null | grep Password | tail -1 | awk -F'<strong>' '{print $2;}' | awk -F'</strong>' '{print $1;}')
   #PASS=$(wget --timeout=10 -O - http://www.vpnbook.com/freevpn 2>&1 | grep Password | tail -1 | awk -F 'Password:' '{print $2;}' | awk -F '</strong>' '{print $1;}' | awk '{print $1;}' | awk -F'<strong>' '{ print $2;}')
   wget -O /tmp/bookPass.png "https://www.vpnbook.com/$(wget -O - https://www.vpnbook.com/freevpn 2> /dev/null | grep Password | grep '?t'  | awk -F'<img src="' '{print $2;}' | awk '{print $1;}')" 2>/dev/null
   tesseract /tmp/bookPass.png /tmp/out &> /dev/null
   PASS=$(cat /tmp/out.txt | strings | head -1)
done   
echo  $PASS >> /home/usuario/freeVpns/book/pass.txt
echo $PASS
