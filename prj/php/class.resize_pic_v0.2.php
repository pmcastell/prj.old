<?php
/*
* Class resize_pic by http://www.wincs.de 2004
* 
* For any bugs, suggestions etc., write an email to wilkening@wincs.de
* 
* How to use:
* You can choose, if you want to resize your pics with ImageMagick or the 
* GD-Library (1 and 2).
* I prefer ImageMagick...
* Just include the class and use the following syntax to resize your pics:
* 
* ImageMagick:
* $res_obj1 = new resize_pic();
* $res_obj1->set_rp_vars("$PictureName","$PictureDirectory","$MaybeAPrefix",$Width,$PathToImageMagickConvert,0);
* $res_obj1->magick();
* 
* Example:
* $res_obj1 = new resize_pic();
* $res_obj1->set_rp_vars("abc.gif","/home/web/abcd.com/gallery","thb_",150,/usr/X11R6/bin/convert,0);
* $res_obj1->magick();
* 
* 
** GD-Library:
* $res_obj1 = new resize_pic();
* $res_obj1->set_rp_vars("$PictureName","$PictureDirectory","$MaybeAPrefix",$Width,0,$GDVersion); 
* $res_obj1->magick();
* For $GDVersion type 1 for the GD > 2.0, type 2 for GD >= 2.0 ;-)
*
* Example:
* 
* $res_obj1 = new resize_pic();
* $res_obj1->set_rp_vars("abc.gif","/home/web/abcd.com/gallery","thb_",150,0,2); 
* $res_obj1->magick(); 
* 
* 
* The ImageMagick function can handle various formats... Take a look at http://www.imagemagick.org/.
* ImageMagick is available for Linux, Unix, MacOS 9/10 and Windows.
*
* The GD function can handle only jpegs. PNG and gif will follow...
*
* 
* THIS SOFTWARE IS PROVIDED BY THE AUTHOR "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
* LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
* IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
* USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER
* IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE
* USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
class resize_pic {
    
    var $pic_name;
    var $direc;
    var $prefix;
    var $width;
    var $gd_vers;
    var $path_to_magick; 
    
    function set_rp_vars($pn, $d, $pr, $w, $ptm, $gd)
    {
        $this->path_to_magick = $ptm;
        $this->pic_name = $pn;
        $this->direc = $d;
        $this->prefix = $pr;
        $this->width = $w;
        $this->gd_vers = $gd;
    } 
    
    function magick()
    {
        $new_pic_name = "$this->prefix" . "$this->pic_name";
        $size = GetImageSize("$this->direc/$this->pic_name");
        $true_height = $size[1];
        $true_width = $size[0];
        $height = round($true_height / ($true_width / $this->width));
        $scale = "$this->width" . "x" . "$height";
        if ($true_width > $this->width) {
            $height2 = $true_height / ($true_width / $this->width);
            $scale2 = "$this->width" . "x" . "$height2";
            exec("$this->path_to_magick -geometry $scale2 $this->direc/$this->pic_name $this->direc/$new_pic_name");
        }else{
			exec("$this->path_to_magick -geometry $scale $this->direc/$this->pic_name $this->direc/$new_pic_name");
		} 
        chmod ("$this->direc/$new_pic_name", 0777);
    } 
    // 
    // GD 1 und 2
    
    function gd_vers12($pn, $d, $pr, $w, $gd)
    {
        $img = "$this->direc/$this->pic_name";
        $picture_save = "$this->direc/$this->prefix" . "$this->pic_name";
        $img_src = ImageCreateFromjpeg ($img);
        $true_width = imagesx($img_src);
        $true_height = imagesy($img_src);
        $width = $this->width;
        $height = round(($width / $true_width) * $true_height);
        if ($this->gd_vers == 1) {
            $img_des = ImageCreate($width, $height);
        } else {
            $img_des = imagecreatetruecolor($width, $height);
        } 
        imagecopyresized ($img_des, $img_src, 0, 0, 0, 0, $width, $height, $true_width, $true_height);
        imagejpeg($img_des, $picture_save);
    } 
} 

?>