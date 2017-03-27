#openssl s_client -connect smtp.gmail.com:465 -crlf
AUTHPLAIN=$(echo -ne "\0$1\0$2"|base64)
#echo -n $AUTHPLAIN | xclip
echo $AUTHPLAIN
