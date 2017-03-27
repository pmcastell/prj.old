#[Pass1]
ffmpeg -i $1.mp4 -vcodec mpeg4 -vtag XVID -b 990k -bf 2 -g 300 -s 640x360 -pass 1 -an -threads 0 -f rawvideo -y /dev/null

#[Pass2]
ffmpeg -i $1.mp4 -vcodec mpeg4 -vtag XVID -b 990k -bf 2 -g 300 -s 640x360 -acodec libmp3lame -ab 128k -ar 48000 -ac 2 -pass 2 -threads 0 -f avi $1.avi
