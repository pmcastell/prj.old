#!/bin/bash
sshConfig() {
echo "Host localhost 127.0.0.1 172.18.163.*
	CheckHostIP no
	UserKnownHostsFile /dev/null 
	StrictHostKeyChecking no
	Compression yes
	Protocol 2
	ProxyCommand none 
	User lliurex	


Host *
	CheckHostIP no
	UserKnownHostsFile /dev/null 
	StrictHostKeyChecking no
	Compression yes
	Protocol 2
	ProxyCommand connect -4 -S 127.0.0.1:9050 $(tor-resolve %h localhost:9050) %p
" > /root/.ssh/config
}
sshRsa() {
echo "-----BEGIN RSA PRIVATE KEY-----
MIIEpAIBAAKCAQEArnYsupG6MCPowVRzm9bg48trxA2odsEKIoUfDsXh4px1uD2e
BmmUtQs0qCRJ73e249Vt5qv7/arPRIsWymwuTCtG4c7iTCtDDgKgXKMgtxP6xY9z
1/UIx7XXUG481KAppMz50bjKT7mko3C67qWk7hyQgkXqle5bhbgV1kCWnhEdki+A
iIGYlzFPz+louwpK9Vu37e8KmvDBfTKeBgW0JGrzYgZfc9Why2o4zdx99mtQVJ9w
X66PROXUvqlWfEQlwcY28b7WH/5oN51IpIxxQzY7xngxjJFLh3fIRcT1yII16gyF
qz3bYv8WB9u63miSJx71pUumAMWyobmlIAN04wIDAQABAoIBAQCo/buR9Tr/CqmZ
K67Yk6o/vXnj7L70WUTx7zUIYFHP6nJ1Rupdl8P+Ip6dCJm9pp63zrOoL4vmSaB/
6qLm7SdjjMAaVwYJdhZ76btgQHueG3JcD/rMg4Q/vzqTpFS0OXhdNFgA+BiwZUi/
MKFCLZ23MGAicIstNDffLVOpt+KttJhAAh8g3ALbGJa2CGnH8i0bzjbE4sRu3v8X
cSnDKkOIjYd4dm4m/bcu6wvY7r7xHXMFJSeYCA31fcMFk1JL1ihxYq1q8GcomalW
o/DSvMwmN6AdtzdA5RTEfBqsBEpiDjCSwr/GRRphzJx6w57Uj02Gsdt/ZPpPhBN7
NNjBhIIxAoGBAOGO5Rax7aSpa5onVQTuTy13a01oVh5/L8iBumqVdmo/kkGvpTGC
WpcdwdHfWrW8LVRwIVbTevKqDzc8/3mCpmQT2JhAtHynIqwxVBwpwuYx7TNYhz9u
K4YbOtVLXRoewhF/LDl4jJiMHcLuWj7IARoJ8Ty6wbydjhy+7Xgf0D23AoGBAMYB
4LSPHW9Kc+FMFy9qD3tZALznfM0p1HuOsS1hn2qYaFQdixmtdWdD44qrQ6wEBpdQ
7fZUcqA0KyEWDBEIIcSVshIZY8TTMbf5fTidekIKV4yQyTAoCtIFw+SIYgqX8ruz
v6nS35XeJqsy/d57sgpYdfyHyRij2chDuYl97sI1AoGALrJmu0wa5vwsSr3Z3Ct6
OO+AR4ZSTf4G5nv1HBvVaCnBHdg/WwChwKdoeQrEvNs4h0KvpEokKhkLDWSTf5SM
3is9rEe5FhWEGCIeR7Zdo0qy0O6DEZ69ZAY/J+lyXPYBN54ciclrUimv/6tZA6Zk
k/968he4BFSaF1lv0G7FrgECgYA0M76IG8sI0jomxaa/K1kVjetx4h23ATYxtPbl
Hf2nXrLyqi9H/8EaDn3oyyo2KYGLVxzFepIVxmswd92Kh/hPGaAuwV7aP0u2UyI+
UNYwnwVh27IO7h+cK0Tv9TzTyieGcOcw4JVtr3nGXNUtWLMhoxzIDQxpYJlEAQbj
Rr4xmQKBgQDSTMwgEHcpHEcu+t9zqAH93YZU4hEiK84gAvzCGoodfFdsVSEtc7gH
mywVWMk49QxeiiKYoK1BTqJ3kcPCQk/9M75e1muJZ6PTGuTxLgIiTVv46R7dCUlY
uGRnIQ5lGZ+gLwFSi/YuDo8PsgO7Vc7FDWcEFNXv3mTF8mKpUbcYkw==
-----END RSA PRIVATE KEY-----
" > /root/.ssh/id_rsa
echo "ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQCudiy6kbowI+jBVHOb1uDjy2vEDah2wQoihR8OxeHinHW4PZ4GaZS1CzSoJEnvd7bj1W3mq/v9qs9EixbKbC5MK0bhzuJMK0MOAqBcoyC3E/rFj3PX9QjHtddQbjzUoCmkzPnRuMpPuaSjcLrupaTuHJCCReqV7luFuBXWQJaeER2SL4CIgZiXMU/P6Wi7Ckr1W7ft7wqa8MF9Mp4GBbQkavNiBl9z1aHLajjN3H32a1BUn3Bfro9E5dS+qVZ8RCXBxjbxvtYf/mg3nUikjHFDNjvGeDGMkUuHd8hFxPXIgjXqDIWrPdti/xYH27reaJInHvWlS6YAxbKhuaUgA3Tj root@ubuntu.localhost" > /root/.ssh/id_rsa.pub
}
if [ "$(whoami)" != "root" ]; then sudo $0 "$@"; exit $?; fi
echo ubu > /etc/hostname
hostname ubu
sudo apt-get update
sudo apt-get -y install tor connect-proxy vnc4server icewm xterm
wget -c https://raw.githubusercontent.com/javier-iesn/prj/master/scripts/aula/tunelSsh6.sh -O /root/tunelSsh6.sh
echo '*/5 *   *   *   *    root  /root/tunelSsh6.sh &> /dev/null' >> /etc/crontab
echo '*/5 *   *   *   *    usuario /usr/bin/vncserver -geometry 1366x768 1440x900 :1 > /dev/null' >> /etc/crontab
chmod +x /root/tunelSsh6.sh
echo 127.0.0.1 ubu >> /etc/hosts
sshConfig
sshRsa


