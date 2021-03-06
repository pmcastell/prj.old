#Locking CDROM/DVD eject button on Linux

#In recent Linux kernels something changed in the way CDROM/DVD ejects are handled. On my notebook with current Ubuntu (11.10) the result was that “eject -i” does not work any more to block the physical eject button. Thats very annoying as I kept hitting it accidentally. But I found a way to get it working again: Copy /lib/udev/rules.d/60-cdrom_id.rules to /etc/udev/rules.d and comment out the line with DISC_EJECT_REQUEST. Now “eject -i” works again.
uso() {
   cat<<EOF
   uso: $0 <on|off>
   bloquea (on) o desbloquea(off) la unidad de cdrom para que se pueda/no se pueda abrir con el botón físico de la misma
EOF
   exit 1
}
[ "$1" != "on" ] && [ "$1" != "off" ] && uso
/usr/bin/eject -i $1 /dev/cdrom

