#!/bin/bash
#. /scripts/scriptsCeuta/funcionesAula.sh
. /scripts/srvOn.sh
. /scripts/scriptsCeuta/encenderAula.sh
fd() { 
    for d in $(sudo fdisk -l $1 2>&1 | grep sd | grep -o /dev/sd. | uniq); do sudo fdisk -l $d; echo "------"; done
}    
alias pi="ssh -p 443 usuario@172.16.1.1 "
alias pir="ssh -p 443 root@172.16.1.1 "
alias mov="ssh -p 8022 movil "
alias dpto="ssh -p 22 jcriadon@10.35.35.35 "
alias pa1="ssh -p 22 jcriadon@10.36.36.36 "
alias pa2="ssh -p 22 jcriadon@10.37.37.37 "
alias mic="ssh -p 443 usuario@micasa6543.duckdns.org "
alias mir="ssh -p 443 root@micasa6543.duckdns.org "
alias ceu="ssh -p 443 usuario@ceuta6543.duckdns.org "
alias cer="ssh -p 443 root@ceuta6543.duckdns.org "
alias hn="h -n "
alias hfn="hf -n "
alias hin="hi -n  "
alias fir="firefox --private-window "
alias delor="gsettings set org.gnome.desktop.interface gtk-theme 'DeLorean-Inspired' "
alias matea="gsettings set org.gnome.desktop.interface gtk-theme 'MATEmbiance-Flat-Dark' "
alias arc="gsettings set org.gnome.desktop.interface gtk-theme 'Arc-Darker' "
alias alt+f2="xdotool key Alt+F3 "
alias ejon="sudo /usr/bin/eject -i on /dev/sr0 "
alias ejoff="sudo /usr/bin/eject -i off /dev/sr0 "
alias hs="history | grep -i "
alias gtts="/home/usuario/programas/simple-google-tts/simple_google_tts "
alias gttss="/home/usuario/programas/simple-google-tts/simple_google_tts es  "
#alias ff="fdisk -l | grep 'Disco /dev/sd' -A 15 | less "

export IPLAYER_OUTDIR="/home/usuario/Escritorio/Varios/bbc"
alias hi='hablai -n '
alias hf='hablaf -n '
export XAUTHORITY=/home/usuario/.Xauthority
unset http_proxy
unset ftp_proxy
unset socks_proxy
unset https_proxy
export BASE_DIR="."
complete -W "ganimedes inkscape limpress lcalc lwriter scratch javi-moodle" -o bashdefault -o default 'hostinger'
alias nem="nemo --no-desktop "

alias n='ncal -M -b $(date +%Y) '

alias wwget="wget --show-progress --user-agent 'Mozilla/5.0 (Windows 10; en-En; rv:57.0) Gecko/20170422 Firefox/57.0' " 
alias ccurl="curl --user-agent 'Mozilla/5.0 (Windows 10; en-En; rv:57.0) Gecko/20170422 Firefox/57.0' "
alias ser="/scripts/kvnet.sh -m 1024 -hda /home/usuario/VirtualBox\ VMs/SER200/SERDisk.vmdk"
alias vbser="/scripts/vbrun SER200 "
alias vmser="/scripts/vmr SER200 "
alias vbw10="/scripts/vbrun win10Pr "
alias vmw10="/scripts/vmr win10Pr "
### /l/VMS/vmware/win10Pr/Windows\ 10\ x64.vmx nogui"
alias dfh="df -h | grep -v loop "
###alias w10="vncviewer win10Pr &"
alias w10="source /scripts/vbrun.sh && vncConnect win10Pr &"
alias smb="sudo systemctl start smbd "
alias miMoo="sudo systemctl start apache2 mysql && (firefox -private -new-tab http://server/moodle &>/dev/null &)"
alias prox="ssh -D 10.10.10.100:9999 -N usuario@localhost &"
alias python="ipython "
alias rprox="ssh -L 10.10.10.100:3128:172.16.1.1:3128 -N usuario@localhost &"
alias gci="google-chrome --incognito "
alias gc="google-chrome "
alias wprox="ssh -L 0.0.0.0:3128:51.38.71.101:8080 -N usuario@localhost "
alias kv="kvm -machine q35 -soundhw hda -vga vmware -k es -boot menu=on -localtime "
alias kvd="kvm -machine q35 -soundhw hda -vga vmware -k es -boot menu=on -localtime -boot d -cdrom "
alias yt="/usr/local/bin/youtube-dl --write-srt --sub-lang es,en --sub-format srt -f bestvideo+bestaudio "
alias srcd="kv -m 1024 -boot d -cdrom /l/ImagenesCdDvd/systemrescuecd-x86-5.3.2.iso "
alias nat="(echo 1 | sudo tee /proc/sys/net/ipv4/ip_forward) && sudo iptables -t nat -A POSTROUTING -j MASQUERADE -s 10.10.10.0/24 "
alias grepIp=" egrep -o '([0-9]{1,3}\.){3}[0-9]{1,3}' "
alias mojave="/scripts/vmr Mojave "



























#alias miMoodle="gksudo systemctl start apache2 mysql"
#PROMPT_COMMAND="history -n; history -w; history -c; history -r; $PROMPT_COMMAND"
#CHHOME=/home/usuario/programas/ch6.3.0
#PATH=/scripts:/home/usuario/programas/ch6.3.0/bin:$PATH
#MANPATH=$MANPATH:/home/usuario/programas/ch6.3.0/docs/man
#[[ -f "/home/usuario/.config/autopackage/paths-bash" ]] && . "/home/usuario/.config/autopackage/paths-bash"
#alias h='habla -n '
#alias sshh='sshpass -p $(/scripts/aula/claves.sh $(eval echo $SSHUSER)) ssh -o UserKnownHostsFile=/dev/null -o StrictHostKeyChecking=no -Y '
##alias sa1='export SSHUSER="franav"; sshh $SSHUSER@sa1'
##alias sa1='sshpass -p $(/scripts/aula/claves.sh franav) ssh -o UserKnownHostsFile=/dev/null -o StrictHostKeyChecking=no -Y franav@sa1'
#alias sa2='export SSHUSER="franav"; sshh $SSHUSER@sa2'
#alias sb='export SSHUSER="franav"; sshh $SSHUSER@sb'
#alias spt='export SSHUSER="lliurex"; sshh $SSHUSER@spt'
#alias sm='export SSHUSER="lliurexsm"; sshh lliurex@sm'
#alias sh='export SSHUSER="lliurex"; sshh $SSHUSER@sh'
#alias sp='export SSHUSER="fcriadon"; sshh $SSHUSER@sp'
#alias sn='export SSHUSER="franav"; sshh $SSHUSER@sn'
#alias ltsp21='export SSHUSER="ltspadmin"; sshh $SSHUSER@ltsp21'
#alias ltsp22='export SSHUSER="ltspadmin"; sshh $SSHUSER@ltsp22'
#alias ltsp23='export SSHUSER="ltspadmin"; sshh $SSHUSER@ltsp23'
#alias scf='sshpass -p $(/scripts/aula/claves.sh franav) scp -o UserKnownHostsFile=/dev/null -o StrictHostKeyChecking=no'
#alias scfp='sshpass -p $(/scripts/aula/claves.sh fcriadon) scp -o UserKnownHostsFile=/dev/null -o StrictHostKeyChecking=no'
#alias scl='sshpass -p $(/scripts/aula/claves.sh ltspadmin) scp -o UserKnownHostsFile=/dev/null -o StrictHostKeyChecking=no'
