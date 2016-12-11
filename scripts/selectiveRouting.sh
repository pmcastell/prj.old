

You need to use policy based routing. Something kind of like

ip rule add from <source>/<mask> table <name>
ip route add 1.2.3.4/24 table <name> dev eth4

<name> is either table name specified in /etc/iproute2/rt_tables or you can use numeric id ...

This pretty much says, that all traffic from 1.2.3.4/24 will be routed using routing table <name>. IIRC it doesen't use the default table after going through this, so if you need other routes (ie. default gateway), you need to add them to the table as well.

(can't test it ATM but i believe it's pretty much right :) )

