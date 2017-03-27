for i in $(seq 10 10 100);
do
    echo $i
    sleep 1s
done | ldm-dialog --progress --auto-close 'This system will halt in 10s.'
