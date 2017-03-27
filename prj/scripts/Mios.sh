#!/bin/bash
DIR_DEST=/l/Mios-7z
ULT=$(ls -p $DIR_DEST/ | grep -v / | sort | tail -1)
ULT=$DIR_DEST/$ULT
DEST=$(ls $DIR_DEST/ | sort | awk -F\. '{print $2;}' | tail -1)
DEST=$(expr $DEST + 1)
CONTRASENIA=$(echo Vm0wd2QyUXlVWGxWV0d4V1YwZDRWMVl3WkRSV01WbDNXa1JTV0ZKdGVGWlZNakExVmpBeFYySkVUbGhoTWsweFZtcEtTMUl5U2tWVWJHaG9UVlZ3VlZadGNFSmxSbGw1VTJ0V1ZXSkhhRzlVVjNOM1pVWmFkR05GZEZSTlZXdzFWVEowVjFaWFNraGhSemxWVmpOT00xcFZXbUZrUjA1R1drWndWMDFFUlRGV1ZFb3dWakZhV0ZOcmFHaFNlbXhXVm0xNFlVMHhXbk5YYlVaclVqQTFSMXBGV2xOVWJGcFlaSHBHVjFaRmIzZFdha1poVjBaT2NtRkhhRk5sYlhoWFZtMHhORmxWTUhoWGJrNVlZbFZhY2xWcVFURlNNV1J5VjJ4T1ZXSlZjRWRaTUZaM1ZqSktWVkpZWkZwbGEzQklWbXBHVDJSV1ZuUmhSazVzWWxob1dGWnRNWGRVTVZGM1RVaG9hbEpzY0ZsWmJHaFRWMFpTVjJGRlRsUmlSM1F6VjJ0U1UxWnJNWEpqUld4aFUwaENSRlpxUVhoa1ZsWjFWMnhrYUdFelFrbFdWM0JIVkRKU1YxWnVUbGhpVjNoVVdWUk9RMlJzV25STldHUlZUVlpXTkZVeGFHOWhiRXB6WTBac1dtSkhhRlJXTUZwVFZqSkdSbFJzVG1sU2JrSmFWMnhXWVZReFdsaFRiRnBZVmtWd1YxbHJXa3RTUmxweFUydGFiRlpzU2xwWlZWcHJWVEZLV1ZGcmJGZGlXRUpJVmtSR2ExWXlUa1phUjJoVFRXNW9WVlpHWTNoaU1XUnpWMWhvV0dKWVVrOVZiVEUwVjBaYVdHUkhkRmROVjFKSldWVmFjMWR0U2tkWGJXaGFUVlp3ZWxreWVHdGtSa3AwWlVaa2FWWnJiekZXYlhCS1RWZEZlRmRZWkU1V1ZscFVXV3RrVTFsV1VsWlhiVVpzWWtad2VGVXlkREJXTVZweVYyeHdXbFpXY0hKV1ZFWkxWMVpHY21KR1pGZE5NRXBKVm10U1MxVXhXWGhhU0ZaVllrWktjRlpxVG05V1ZscEhXVE5vYVUxWFVucFdNV2h2V1ZaS1IxTnNaRlZXYkhCNlZHdGFWbVZYVWtoa1IyaHBVbGhCZDFac1pEUmpNV1IwVTJ0b2FGSnNTbGhVVlZwM1ZrWmFjVkp0ZEd0U2EzQXdXbFZhYTJGV1duSmlla1pYWWxoQ1MxUldaRVpsUm1SWldrVTFWMVpzY0ZWWFYzUnJWVEZzVjFWc1dsaGlWVnB5V1d0YWQyVkdWblJrUkVKV1RXdHdTVlpYY0VOWGJGcFhZMGhLV2xaWFVrZGFWV1JQVTBkR1IyRkhiRk5pYTBwMlZtMTBVMU14VVhsVmEyUlVZbXR3YUZWdE1XOWpSbHB4VkcwNWEySkdjRWhXYlRBMVZXc3hXRlZ1Y0ZkTlYyaDJWakJrUzFKck5WZFZiRlpYVFRKb1NWWkhlR0ZXTWxKSVZXdG9hMUp0YUZSWmJGcExVMnhrVjFadFJtcE5WMUl3VlRKMGIyRkdTbk5UYkdoVlZsWndNMVpyV21GalZrcDBaRWQwVjJKclNraFdSM2hoVkRKR1YxTnVVbEJXUlRWWVZGYzFiMWRHYkhGVGExcHNVbTFTV2xkclZURldNa3BYVTI1b1YxWXphSEpaYWtaclVqRldjMXBIUmxObGJYaDRWMWQwWVdReVZrZFdibEpPVmxkU1ZsUlhkSGRTTVd0M1YyNWtXRkl3VmpSWk1HaExWMnhhV0ZWclpHRldWMUpRVlRCVk5WWXlSa2hoUlRWWFltdEtNbFp0TVRCVk1VMTRWVmhzVlZkSGVGWlpWRVozWVVaV2NWTnRPVmRTYkVwWlZHeGpOV0V5U2tkalJXaFhZbFJCTVZaWGMzaFhSbFp6WVVaa1RsWXlhREpXTVZwaFV6RkplRlJ1VmxKaVJscFlXV3RvUTFkV1drZFZhMlJXVFZad01GVnRkRzlWUmxsNVlVWm9XbFpGTlVSVWJYaHJWbFpHZEZKdGNFNVdNVWwzVmxSS01HRXhaRWhUYkdob1VtMW9ZVlpyVm1GTk1WcHlWMjFHYWxacmNEQmFSV1F3VmpKS2NsTnJjRmhpUmxwb1dWUktSMVl4VGxsalJuQk9UVzFvV1ZaR1l6RmlNV1JIVjI1R1UySkZjSE5WYlRGVFYyeGtjbFpVUmxoU2EzQmFWVmQ0YzFkR1duUlZhbHBWVm14d2VsWnFSbGRqTVdSellVZHNhVlpyY0ZwV2JHTjRUa2RSZVZaclpGZGliRXBQVm14a1UxZEdVbFpWYTJSc1ZteEtlbFp0TURWV01ERldZbnBLVm1KWVVuWldha1poVW14a2NtVkdaR2hoTTBKUlZsY3hORmxYVFhoalJXaHBVbXMxVDFac1dscGxiRnAwWlVkR1ZrMVZiRFJaYTFwclYwZEtjbU5GT1ZkaVdHZ3pWakJhYzFkWFRrZGFSbVJUWWtad05WWnRNVEJaVmxGNFZteFdUbEpIY3prPQ== | desencripta)
if [ $DEST -lt 10 ]; then
   DEST=000$DEST
elif [ $DEST -lt 100 ]; then
   DEST=00$DEST
elif [ $DEST -lt 1000 ]; then
   DEST=0$DEST
fi
      
DEST=$DIR_DEST/Mios.$DEST.7z; ORIGEN=/m/Mios
echo Origen:  $ORIGEN; echo Destino: $DEST; echo Último: $ULT

if [ "$ULT" = "$DIR_DEST/" -o "$1" = "-n" ]; then
   echo ¿Crear uno nuevo? \(S/N\)
   read RESP
   if [ "$RESP" = "S" ]; then
      eecho nice -19 7za a -w$DIR_DEST/ -mhe -p$CONTRASENIA -r -ms=off -mx=9 $DEST $ORIGEN
   else
      echo ok! No hacemos nada
   fi
   exit 1
fi
if [ ! -f $ULT ]; then
   echo Error no encuentro $ULT
   exit 2
fi
eecho nice -19 7za u -w$DIR_DEST/ -mhe -p$CONTRASENIA -r -ms=off -mx=9 -u- -uq0!$DEST $ULT $ORIGEN


#del %FICH_DEST%
#move %FICH_DEST%.tmp %FICH_DEST%

   
      


#SET UNI_DEST=k:
#SET FICH_DEST=%UNI_DEST%\Mios.7z
#SET ORIGEN=k:\Mios\*
#time /t 
#if exist %FICH_DEST%.tmp del %FICH_DEST%.tmp
#if exist %FICH_DEST% (
#   start /low /b /wait 7za u -w%UNI_DEST%\ -mhe -p$CONTRASENIA -ssw -r -ms=off -mx=9 -u- -uq0!%FICH_DEST%.tmp %FICH_DEST% %ORIGEN%
#   del %FICH_DEST%
#   move %FICH_DEST%.tmp %FICH_DEST%
#) else (
#   start /low /b /wait 7za a -w%UNI_DEST%\ -mhe -p$CONTRASENIA -ssw -r -ms=off -mx=9 %FICH_DEST% %ORIGEN%
#)
#time /t


