import java.net.URL;
import java.util.*;
import java.io.*;

public class marco {

 public static void main(String[] args) {
    InputStream in=null;
    BufferedInputStream in_=null;
    BufferedReader in2;
    URL u;
    try {
       u=new URL("http://www.cica.es/index.en.html");
       in=u.openStream();
       in_ = new BufferedInputStream(in);
    } catch (Exception ex) {}
    byte b[]=new byte[512];
    String r="";
    String r2;
    int off=0;
    try {
       while (true) {
          in_.read(b,off,512);
          if (b.length==0) {
             break;
          }
          off+=512;
          r+=new String(b);
       }
    } catch (Exception ex) {}

    b.toString();

 }

}