#-r recursive
#-p permissions
#-t preserve modification time
#-g preserver group
#-o preserver owner
#-D --devices --specials (preserve device files preserve special files)


sudo rsync -r -p -t -g -o -D --progress /net/ /bakNet/
