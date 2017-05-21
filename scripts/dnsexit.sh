uso() {
   echo uso: $0 '<host-a-actualizar> [<dir-ip>]'
   exit
}
   
if [ "$1" = "" ]; then
   uso
fi
LOGIN="Vm0wd2QyUXlVWGxWV0d4V1YwZDRWMVl3WkRSWFJteFZVMjA1VjAxV2JETlhhMk0xVmpGYWMySkVUbGhoTWsweFZqQmFTMk15U2tWVWJHaG9UV3N3ZUZadGNFdFRNVTVJVm10V1VtSlZXbGhXYlhoelRURmFjVkZ0UmxSTmJFcEpWbTEwYTFkSFNrZGpSVGxhWWxSR2RsWldXbUZqVmtaMFVteHdWMDFFUlRGV1ZFb3dWakZhV0ZOcmFHaFNlbXhXVm0xNFlVMHhXbk5YYlVaclVqQTFSMWRyV25kV01ERldZMFZ3VjJKVVJYZFdha1pYWkVaT2MxZHNhR2xTYTNCWlYxWmtNR1F5VW5OalJtUllZbFZhY2xWc1VrZFdiRnBZWlVaT1ZXSlZXVEpWYkZKSFZqRmFSbUl6WkZkaGExcG9WakJhVDJOdFJrZFhiV3hvVFVoQ1dsWXhXbE5TTVd4WVVtdGtWMWRIYUZsWmJGWmhZMVphZEdSSFJrNVNiRm93V2xWYVQxWlhTbFpYVkVwV1lrWktTRlpxU2tabFZsWlpXa1p3YkdFelFrbFdiWEJIVkRKU1YxWnVUbGhpVjNodlZGVm9RMWRzV25KWGJHUmFWakZHTkZaSGRHdFdiVXBIVjJ4U1dtSkdXbWhaTW5oWFkxWktkRkpzVWxOaVIzY3hWa1phVTFVeFduSk5XRXBxVW0xb1YxUlhOVzlsYkZweFVtMUdUMkpGV2xwWlZWcGhZa2RGZWxGcmJGaFhTRUpJVmtSS1UxWXhaSFZVYkZKcFZqTm9XVlpYY0U5aU1rbDRWMjVTYWxKVk5YQlVWbFpYVGtaa2NsWnRkR2hpUlhBd1ZsZDRjMWR0U2tkWGJXaGFUVlp3VkZacVJtdGtWbkJHVGxaT2FWSnRPVE5XTW5oWFlUQXhSMWRzYUZSaE1sSnhWV3RXWVZZeFduRlViVGxyWWtad2VGVnRkREJoYXpGeVRsVm9XbFpXY0hKWlZXUkdaV3hHY21KR1pGZFNWWEJ2VmpGYWExVXhXWGhVYmxaVllrWktjRlZxUmt0V1ZscEhWV3RLYTAxRVJsTlZSbEYzVUZFOVBRPT0="
LOGIN=$(echo $LOGIN | desencripta)
PASSWORD="$(cat /m/Mios/Personal/Privado/AgendasClaves/genClavesIndicePass.sh | grep $LOGIN | awk '{print $2;}')"
HOST=$1
IP_ULTIMA=$(dig $HOST | grep -E "^$HOST" | grep IN | grep A | awk '{print $NF;}')
if [ "$2" != "" ]; then IP_ACTUAL=$2; else IP_ACTUAL=$(realIp); fi
if [ "$IP_ACTUAL" != "$IP_ULTIMA" ]; then
   eecho wget "http://update.dnsexit.com/RemoteUpdate.sv?login=$LOGIN&password=$PASSWORD&host=$HOST&myip=$IP_ACTUAL" -O /tmp/dnsexit.inf 2> /dev/null
   cat /tmp/dnsexit.inf
   echo ----------------------
else
   echo no hacía falta actualizar: $IP_ACTUAL
fi   
