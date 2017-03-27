Yes, I've found the solution.
Try this way below please (which is in my environment).

1. define .sb2 file's type
 sudo vi /usr/share/mime/package/sb2.xml
sb2is new file's name. Set is as you like. ex)scratch2
 write text below in the file

<?xml version='1.0' encoding='UTF-8'?>
<mime-info xmlns='http://www.freedesktop.org/standards/shared-mime-info'>
   <mime-type type='application/x-scratch2'>
      <comment></comment>
      <glob pattern='*.sb2'/>
   </mime-type>
</mime-info> 

2. associate .sb2 file with scratch 2 application
 Find ”edu.media.mit.scratch2editor.desktop" file under directory of /usr/share/applications/ , 
 and add only one line in the last line of that desktop file.
 MimeType=application/x-scratch2

3. update
 sudo update-desktop-databese
 sudo update-mime-database

4. reboot PC


