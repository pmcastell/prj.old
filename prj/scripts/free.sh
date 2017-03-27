sudo /etc/init.d/vmware start
sudo vmrun -T ws start /l/vmware/FreeBSD-64/FreeBSD-64.vmx nogui &
vncviewer localhost:28 &
