#openssl s_client -connect smtp.gmail.com:465 -crlf
echo -e "\0$1\0$2"|base64
