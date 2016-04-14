#openssl s_client -starttls smtp -crlf -connect 1.2.3.4:25
#
#And for an SSL server (where you connect to a different port number and have to establish an SSL connection before the SMTP conversation even #starts) on IP address 1.2.3.4 port 465, you would use this command:
#
#% openssl s_client -crlf -connect 1.2.3.4:465 
uso () {
   echo uso: $0 '<dir-ip> <port> [<pop3|smmtp>]'
   exit 1
}
if [ "$1" = "" -o "$2" = "" ];
then
   uso
fi   
if [ "$3" != "" -a "$3" = "pop3" ];
then
   TLS=' -starttls pop3 '
fi
if [ "$3" != "" -a "$3" = "smtp" ];
then
   TLS=' -starttls smtp '
fi
echo openssl s_client $TLS -crlf -connect $1:$2 -crlf -ign_eof

