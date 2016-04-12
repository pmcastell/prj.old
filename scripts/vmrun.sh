#ssh franc@iesinclan.dyndns.org vmrun -vp galileoeraungenio  -T ws start  /home/franc/vmware/miUbuntu64/miUbuntu64.vmx nogui
#ssh franc@iesinclan.dyndns.org vmrun -vp galileoeraungenio  -T ws start  /home/franc/vmware/miUbuntu64/$1.vmx nogui
ssh franc@iesinclan.dyndns.org 'ecryptfs-mount-private && cd /home/franc && vmrun -vp galileoeraungenio  -T ws start  /home/franc/vmware/miUbuntu64/miUbuntu64.vmx nogui'
