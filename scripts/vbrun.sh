#!/bin/bash
#if [ "$(whoami)" != "root" ]; then gksudo $0: exit ; fi
[ "$1" = "" ] && MAQUINA=$(zenity --entry --title="Dime que máquina arranco" 2>/dev/null) || MAQUINA="$1"
[ "$MAQUINA" = "" ] && zenity --info --text="Necesito saber que máquina arrancar" 2>/dev/null && exit 1
if [ "${MAQUINA}" = "win7" ] && [ ! -f /l/virtualbox-enc/$MAQUINA/${MAQUINA}.vbox ]; then
   USUPASS=$(zenity --password --title="Instroduce contraseña de usuario" 2>/dev/null)
   PASS=$(/scripts/getMyPass.sh encfs)
   echo $USUPASS | sudo -S bash -c "echo $PASS | encfs -S --public /l/.virtualbox.enc /l/virtualbox-enc"
fi  
vboxmanage startvm $MAQUINA --type headless &
#Esto es por que a veces el firewall hacía cosas "raras" y no llegaba el ping a la tarjeta virtual
for((i=1;i<30;i++)); do
   [ "$(nmap ${MAQUINA} | grep 5900 | grep open)" != "" ] && break
   #ping -c 2 ${MAQUINA}
   #[ $? -eq 0 ] && break
   sleep 1
done
vncviewer $MAQUINA &

exit 0
#script antiguo:




#!/bin/bash
#if [ "$(whoami)" != "root" ]; then gksudo $0: exit ; fi
[ "$1" = "" ] && MAQUINA=$(zenity --entry --title="Dime que máquina arranco") || MAQUINA="$1"
[ "$MAQUINA" = "" ] && zenity --info --text="Necesito saber que máquina arrancar" 2>/dev/null && exit 1
USUPASS=$(zenity --password --title="Instroduce contraseña de usuario")
if [ ! -f /l/virtualbox-enc/win7/win7.vbox ]; then
   PASS=$(echo $PASS | desencripta)
   echo $USUPASS | sudo -S bash -c "echo $PASS | encfs -S --public /l/.virtualbox.enc /l/virtualbox-enc"
fi   
sudo -H -u usuario vboxmanage startvm $MAQUINA --type headless &
#Esto es por que a veces el firewall hacía cosas "raras" y no llegaba el ping a la tarjeta virtual
ping -c 2 -W 2 $MAQUINA &> /dev/null
echo $USUPASS | sudo -S  iptables -F &> /dev/null
ping -c 2 -W 2 $MAQUINA &> /dev/null
echo $USUPASS | sudo -S  firewall &> /dev/null
sleep 5
sudo -H -u usuario vncviewer $MAQUINA &

exit 0


PASS="Vm0wd2QyUXlVWGxWV0d4V1YwZDRWMVl3WkRSV01WbDNXa1JTV0ZKdGVGWlZNakExVmpBeFYySkVUbGhoTWsweFZtcEtTMUl5U2tWVWJHaG9UVlZ3VlZadGNFSmxSbGw1VTJ0V1ZXSkhhRzlVVjNOM1pVWmFkR05GZEZSTlZXdzFWVEowVjFaWFNraGhSemxWVmpOT00xcFZXbUZrUjA1R1drWndWMDFFUlRGV1ZFb3dWakZhV0ZOcmFHaFNlbXhXVm0xNFlVMHhXbk5YYlVaclVqQTFSMXBGV2xOVWJGcFlaSHBHVjFaRmIzZFdha1poVjBaT2NtRkhhRk5sYlhoWFZtMHhORmxWTUhoWGJrNVlZbFZhY2xWcVFURlNNV1J5VjJ4T1ZXSlZjRWRaTUZaM1ZqSktWVkpZWkZwbGEzQklWbXBHVDJSV1ZuUmhSazVzWWxob1dGWnRNWGRVTVZGM1RVaG9hbEpzY0ZsWmJHaFRWMFpTVjJGRlRsUmlSM1F6VjJ0U1UxWnJNWEpqUld4aFUwaENSRlpxUVhoa1ZsWjFWMnhrYUdFelFrbFdWM0JIVkRKU1YxWnVUbGhpVjNoVVdWUk9RMlJzV25STldHUlZUVlpXTkZVeGFHOWhiRXB6WTBac1dtSkhhRlJXTUZwVFZqSkdSbFJzVG1sU2JrSmFWMnhXWVZReFdsaFRiRnBZVmtWd1YxbHJXa3RTUmxweFUydGFiRlpzU2xwWlZWcHJWVEZLV1ZGcmJGZGlXRUpJVmtSR2ExWXlUa1phUjJoVFRXNW9WVlpHWTNoaU1XUnpWMWhvV0dKWVVrOVZiVEUwVjBaYVdHUkhkRmROVjFKSldWVmFjMWR0U2tkWGJXaGFUVlp3ZWxreWVHdGtSa3AwWlVaa2FWWnJiekZXYlhCS1RWZEZlRmRZWkU1V1ZscFVXV3RrVTFsV1VsWlhiVVpzWWtad2VGVXlkREJXTVZweVYyeHdXbFpXY0hKV1ZFWkxWMVpHY21KR1pGZE5NRXBKVm10U1MxVXhXWGhhU0ZaVllrWktjRlpxVG05V1ZscEhXVE5vYVUxWFVucFdNV2h2V1ZaS1IxTnNaRlZXYkhCNlZHdGFWbVZYVWtoa1IyaHBVbGhCZDFac1pEUmpNV1IwVTJ0b2FGSnNTbGhVVlZwM1ZrWmFjVkp0ZEd0U2EzQXdXbFZhYTJGV1duSmlla1pYWWxoQ1MxUldaRVpsUm1SWldrVTFWMVpzY0ZWWFYzUnJWVEZzVjFWc1dsaGlWVnB5V1d0YWQyVkdWblJrUkVKV1RXdHdTVlpYY0VOWGJGcFhZMGhLV2xaWFVrZGFWV1JQVTBkR1IyRkhiRk5pYTBwMlZtMTBVMU14VVhsVmEyUlVZbXR3YUZWdE1XOWpSbHB4VkcwNWEySkdjRWhXYlRBMVZXc3hXRlZ1Y0ZkTlYyaDJWakJrUzFKck5WZFZiRlpYVFRKb1NWWkhlR0ZXTWxKSVZXdG9hMUp0YUZSWmJGcExVMnhrVjFadFJtcE5WMUl3VlRKMGIyRkdTbk5UYkdoVlZsWndNMVpyV21GalZrcDBaRWQwVjJKclNraFdSM2hoVkRKR1YxTnVVbEJXUlRWWVZGYzFiMWRHYkhGVGExcHNVbTFTV2xkclZURldNa3BYVTI1b1YxWXphSEpaYWtaclVqRldjMXBIUmxObGJYaDRWMWQwWVdReVZrZFdibEpPVmxkU1ZsUlhkSGRTTVd0M1YyNWtXRkl3VmpSWk1HaExWMnhhV0ZWclpHRldWMUpRVlRCVk5WWXlSa2hoUlRWWFltdEtNbFp0TVRCVk1VMTRWVmhzVlZkSGVGWlpWRVozWVVaV2NWTnRPVmRTYkVwWlZHeGpOV0V5U2tkalJXaFhZbFJCTVZaWGMzaFhSbFp6WVVaa1RsWXlhREpXTVZwaFV6RkplRlJ1VmxKaVJscFlXV3RvUTFkV1drZFZhMlJXVFZad01GVnRkRzlWUmxsNVlVWm9XbFpGTlVSVWJYaHJWbFpHZEZKdGNFNVdNVWwzVmxSS01HRXhaRWhUYkdob1VtMW9ZVlpyVm1GTk1WcHlWMjFHYWxacmNEQmFSV1F3VmpKS2NsTnJjRmhpUmxwb1dWUktSMVl4VGxsalJuQk9UVzFvV1ZaR1l6RmlNV1JIVjI1R1UySkZjSE5WYlRGVFYyeGtjbFpVUmxoU2EzQmFWVmQ0YzFkR1duUlZhbHBWVm14d2VsWnFSbGRqTVdSellVZHNhVlpyY0ZwV2JHTjRUa2RSZVZaclpGZGliRXBQVm14a1UxZEdVbFpWYTJSc1ZteEtlbFp0TURWV01ERldZbnBLVm1KWVVuWldha1poVW14a2NtVkdaR2hoTTBKUlZsY3hORmxYVFhoalJXaHBVbXMxVDFac1dscGxiRnAwWlVkR1ZrMVZiRFJaYTFwclYwZEtjbU5GT1ZkaVdHZ3pWakJhYzFkWFRrZGFSbVJUWWtad05WWnRNVEJaVmxGNFZteFdUbEpIY3prPQ=="
