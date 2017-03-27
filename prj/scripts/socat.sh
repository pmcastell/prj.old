echo "
Maybe you’re concerned about anonymity or you just want know and experiment how actually things work. You can find a lot of documentation about such topics on the Internet.
This is my take on a little portion of the argument, inspired by some questions recently appeared on netfilter and redsocks mailing lists.

So, you want to telnet a server from your workstation and need to establish an anonymous connection via a third machine (let’s name it proxy), I mean that the server cannot trace back your IP address (neither workstation nor proxy): I’ll show you the Tor method.

I guess you already know what Tor is, otherwise maybe this article is not for you.
Let’s assume you have full control of the proxy machine and can install a Gnu/Linux distribution on it, Ubuntu server 13.10 for instance.

Install Tor and tor-arm (a terminal status monitor):
apt-get install tor tor-arm

Verify Tor is running and listening:

netstat -antup|grep tor
tcp        0      0 127.0.0.1:9050          0.0.0.0:*               LISTEN      1806/tor

On that port Tor is waiting for a TCP session able to carry the final destination of the transiting packets. That means you need another piece of software to do the job: socat, a multipurpose relay.

Install socat:
apt-get install socat

Socat can listen for a connection and forward it to a host via a socks4 server (Tor in our example).

Now, let’s put down some names and addresses:

    client: 192.0.2.1/24
    proxy server: 198.51.100.128/24
    telnet server: 203.0.113.254/24 

192.0.2.1 wants to telnet 203.0.113.254 via 198.51.100.128, right.

This is a possible schema:

    192.0.2.1:[random port] (client)
    |
    |
    198.51.100.128:2300 (proxy server: socat)
    |
    127.0.0.1:9050 (TOR)
    |
    |
    |—-TOR CLOUD—-|
    |
    |
    203.0.113.254:23 (telnet server)

Run socat:
socat TCP4-LISTEN:2300,reuseaddr,fork SOCKS4:127.0.0.1:203.0.113.254:23,socksport=9050

Everything is ready: go for an anonymous telnet session from the client:

telnet 198.51.100.128 2300
Trying 198.51.100.128...
Connected to 198.51.100.128
Escape character is '^]'.

The session is flowing through the Tor network thanks to a socks proxy in the middle. To view a bandwidth graph of your connection, use arm.
If you want to select Tor exit nodes, you could put something like this in your torrc:


StrictExitNodes 1
ExitNodes {IT}, {UK}, {FR}, {DE}, {CH}, {ES}, {AT}, {PT}, {GR}, {RU}, {NL}, {LU}, {BE}, {UA}, {DK}, {LT}, {LV}, {VA}, {FI}, {SE}, {NO}, {IS}, {CS}, {CZ}, {PL}, {EE}, {HU}, {IR}, {LI}, {MC}, {ME}, {MF}, {SI}


Pretty simple, huh?

Disclaimer.
This is just a quick and practical how to: you are warmly encouraged to read the documentation of each piece of software involved to know what exactly you are doing.
"
