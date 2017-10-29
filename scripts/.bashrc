# ~/.bashrc: executed by bash(1) for non-login shells.
# see /usr/share/doc/bash/examples/startup-files (in the package bash-doc)
# for examples

# If not running interactively, don't do anything
[ -z "$PS1" ] && return

# don't put duplicate lines in the history. See bash(1) for more options
# ... or force ignoredups and ignorespace
#HISTCONTROL=ignoredups:ignorespace
HISTCONTROL=ignoredups:erasedups

# append to the history file, don't overwrite it
shopt -s histappend

# for setting history length see HISTSIZE and HISTFILESIZE in bash(1)
HISTSIZE=2000
HISTFILESIZE=2000

# check the window size after each command and, if necessary,
# update the values of LINES and COLUMNS.
shopt -s checkwinsize

# make less more friendly for non-text input files, see lesspipe(1)
[ -x /usr/bin/lesspipe ] && eval "$(SHELL=/bin/sh lesspipe)"

# set variable identifying the chroot you work in (used in the prompt below)
if [ -z "$debian_chroot" ] && [ -r /etc/debian_chroot ]; then
    debian_chroot=$(cat /etc/debian_chroot)
fi

# set a fancy prompt (non-color, unless we know we "want" color)
case "$TERM" in
    xterm-color) color_prompt=yes;;
esac

# uncomment for a colored prompt, if the terminal has the capability; turned
# off by default to not distract the user: the focus in a terminal window
# should be on the output of commands, not on the prompt
#force_color_prompt=yes

if [ -n "$force_color_prompt" ]; then
    if [ -x /usr/bin/tput ] && tput setaf 1 >&/dev/null; then
	# We have color support; assume it's compliant with Ecma-48
	# (ISO/IEC-6429). (Lack of such support is extremely rare, and such
	# a case would tend to support setf rather than setaf.)
	color_prompt=yes
    else
	color_prompt=
    fi
fi

if [ "$color_prompt" = yes ]; then
    PS1='${debian_chroot:+($debian_chroot)}\[\033[01;32m\]\u@\h\[\033[00m\]:\[\033[01;34m\]\w\[\033[00m\]\$ '
else
    PS1='${debian_chroot:+($debian_chroot)}\u@\h:\w\$ '
fi
unset color_prompt force_color_prompt

# If this is an xterm set the title to user@host:dir
case "$TERM" in
xterm*|rxvt*)
    PS1="\[\e]0;${debian_chroot:+($debian_chroot)}\u@\h: \w\a\]$PS1"
    ;;
*)
    ;;
esac

# enable color support of ls and also add handy aliases
if [ -x /usr/bin/dircolors ]; then
    test -r ~/.dircolors && eval "$(dircolors -b ~/.dircolors)" || eval "$(dircolors -b)"
    alias ls='ls --color=auto'
    #alias dir='dir --color=auto'
    #alias vdir='vdir --color=auto'

    alias grep='grep --color=auto'
    alias fgrep='fgrep --color=auto'
    alias egrep='egrep --color=auto'
fi

# some more ls aliases
alias ll='ls -alF'
alias la='ls -A'
alias l='ls -CF'

# Alias definitions.
# You may want to put all your additions into a separate file like
# ~/.bash_aliases, instead of adding them here directly.
# See /usr/share/doc/bash-doc/examples in the bash-doc package.

if [ -f ~/.bash_aliases ]; then
    . ~/.bash_aliases
fi

# enable programmable completion features (you don't need to enable
# this, if it's already enabled in /etc/bash.bashrc and /etc/profile
# sources /etc/bash.bashrc).
if [ -f /etc/bash_completion ] && ! shopt -oq posix; then
    . /etc/bash_completion
fi
CHHOME=/home/usuario/programas/ch6.3.0
PATH=/home/usuario/programas/ch6.3.0/bin:$PATH
MANPATH=$MANPATH:/home/usuario/programas/ch6.3.0/docs/man
export IPLAYER_OUTDIR="/home/usuario/Escritorio/Varios/bbc"
[[ -f "/home/usuario/.config/autopackage/paths-bash" ]] && . "/home/usuario/.config/autopackage/paths-bash"
alias hi='hablai -n '
alias h='habla -n '
alias hf='hablaf -n '

export XAUTHORITY=/home/usuario/.Xauthority
unset http_proxy
unset ftp_proxy
unset socks_proxy
unset https_proxy
alias sshh='sshpass -p $(/scripts/aula/claves.sh $(eval echo $SSHUSER)) ssh -o UserKnownHostsFile=/dev/null -o StrictHostKeyChecking=no -Y '
export BASE_DIR="."
alias sa1='export SSHUSER="franav"; sshh $SSHUSER@sa1'
#alias sa1='sshpass -p $(/scripts/aula/claves.sh franav) ssh -o UserKnownHostsFile=/dev/null -o StrictHostKeyChecking=no -Y franav@sa1'
alias sa2='export SSHUSER="franav"; sshh $SSHUSER@sa2'
alias sb='export SSHUSER="franav"; sshh $SSHUSER@sb'
alias spt='export SSHUSER="lliurex"; sshh $SSHUSER@spt'
alias sm='export SSHUSER="lliurexsm"; sshh lliurex@sm'
alias sh='export SSHUSER="lliurex"; sshh $SSHUSER@sh'
alias sp='export SSHUSER="fcriadon"; sshh $SSHUSER@sp'
alias sn='export SSHUSER="franav"; sshh $SSHUSER@sn'
alias ltsp21='export SSHUSER="ltspadmin"; sshh $SSHUSER@ltsp21'
alias ltsp22='export SSHUSER="ltspadmin"; sshh $SSHUSER@ltsp22'
alias ltsp23='export SSHUSER="ltspadmin"; sshh $SSHUSER@ltsp23'
alias scf='sshpass -p $(/scripts/aula/claves.sh franav) scp -o UserKnownHostsFile=/dev/null -o StrictHostKeyChecking=no'
alias scfp='sshpass -p $(/scripts/aula/claves.sh fcriadon) scp -o UserKnownHostsFile=/dev/null -o StrictHostKeyChecking=no'
alias scl='sshpass -p $(/scripts/aula/claves.sh ltspadmin) scp -o UserKnownHostsFile=/dev/null -o StrictHostKeyChecking=no'
complete -W "ganimedes inkscape limpress lcalc lwriter scratch javi-moodle" -o bashdefault -o default 'hostinger'
alias nem="nemo --no-desktop "
#PROMPT_COMMAND="history -n; history -w; history -c; history -r; $PROMPT_COMMAND"
alias n='ncal -M -C $(date +%Y)'
