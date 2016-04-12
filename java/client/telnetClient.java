import java.io.*;
import java.net.*;

import java.util.StringTokenizer;
import java.util.Date;
import java.applet.Applet;
import java.applet.AudioClip;
import sun.audio.*;



public class telnetClient {
    private final int TIMEOUT=10;
  private final int TIME_SLEEP=1000;
  private Applet appletSonido=new Applet();
  public telnetClient() {
  }
  String cogeRespuesta(BufferedReader fromRemote) {
    String str="";
    int intentos=0;

    try {
      while(!fromRemote.ready() && intentos++<TIMEOUT) {
        Thread.sleep(TIME_SLEEP);
      }
      while (fromRemote.ready()) {
        str+=fromRemote.readLine()+'\n';
      }
      System.out.println(str);
    } catch (Exception ex) {
      System.out.println(ex.toString());
    }
    return str;
  }
  String siguienteToken(StringTokenizer st) {
    if (st.hasMoreTokens()) {
      return st.nextToken();
    } else {
      return "";
    }
  }
  public int contestaNumMensajes(String s) {
    StringTokenizer st = new StringTokenizer(s);
    String sAux;
    int numMensas=-1, numBytes=-1, pos=-1;


    while (st.hasMoreTokens() && numMensas<0 && numBytes<0) {
      do {
        sAux=siguienteToken(st);
      }while(!sAux.equalsIgnoreCase("+OK") && !sAux.equalsIgnoreCase(""));
      try {
        sAux=siguienteToken(st);
        numMensas=Integer.parseInt(sAux);
        sAux=siguienteToken(st);
        numBytes=Integer.parseInt(sAux);
      } catch (Exception ex) {
      }
    }
    if (numMensas>=0 && numBytes>=0) {
      return numMensas;
    } else {
      return -1;
    }
  }
  String decodificar(String s) {
     String res="";
     char ch;
     int i=0, valor;

     if (s.charAt(0)=='=') {
        i++;
     }
/*     for(; i<s.length(); i++) {
        if (s.charAt(i)=='=') {
           ch=s.charAt(++i);
           valor= (ch >='A' ? ch-'A' + 10 : ch-'0') * 16;
           ch=s.charAt(++i);
           valor+= (ch >='A' ? ch-'A' + 10 : ch-'0');
           res+=(char ) valor;
        } else {
           res+=s.charAt(i);
        }
     }
     return res;*/
     return s;
  }

  public void sendRequest() {
    Socket remoteSocket;
    PrintWriter toRemote;
    BufferedReader fromRemote, fileInput;
    String str = "", user, maquina, password;
    MessageBox m=new MessageBox();
    int numMensas, intentos=0;



    try {
      fileInput = new BufferedReader(new FileReader("cuentas.txt"));
      while (true) {
        str=fileInput.readLine();
        intentos=0;
        if (str==null || str.equalsIgnoreCase("")) {
          break;
        }
        int posEsp=str.indexOf(" ",0);
        user=str.substring(0,posEsp);
        maquina=str.substring(posEsp+1,str.indexOf(" ",posEsp+1));
        posEsp=str.indexOf(" ",posEsp+1);
        password=str.substring(posEsp+1,str.length());
        InetAddress remoteHost=InetAddress.getByName(maquina);
        System.out.println("conectando a: "+maquina+" usuario: "+user);
        try {
          remoteSocket = new Socket(remoteHost,110);
        } catch (Exception ex) {
          System.out.println(ex.toString());
          System.out.println("Falló en conexión con: "+maquina);
          continue;
        }

        toRemote = new PrintWriter(
        new BufferedWriter (
            new OutputStreamWriter (
               remoteSocket.getOutputStream())),true);
        fromRemote = new BufferedReader(
            new InputStreamReader (
               remoteSocket.getInputStream()));
        toRemote.println("USER "+user);
        toRemote.println("PASS "+password);
        str=cogeRespuesta(fromRemote);
        do {
          numMensas=-1;
          toRemote.println("STAT");
          for(int i=0; i<3 && numMensas<0; i++) {
           str=cogeRespuesta(fromRemote);
           numMensas=contestaNumMensajes(str);
          }
          intentos++;
        } while(numMensas<0 && intentos<3);
        if (numMensas>0) {
          String mensa="Tienes correo: " + maquina + "-> " + str;
          toRemote.println("LIST");
          do {
            str=cogeRespuesta(fromRemote);
            if (str!="") {
              mensa+=str;
            }
          } while(str!="");
          if (numMensas>0) {
            int pos;
            for(int i=1; i<=numMensas; i++) {
              pos=-1;
              toRemote.println("TOP "+i+" 1");
              for(int j=1; j<3 && pos<0; j++) {
                str=cogeRespuesta(fromRemote);
                pos=str.indexOf("From:");
              }
              if (pos>=0) {
                mensa+="M"+i+"->De: "+decodificar(str.substring(pos,str.indexOf('\n',pos)));
              }
              pos=str.indexOf("Subject:");
              if (pos>=0) {

                mensa+="\n   Asunto: "+decodificar(str.substring(pos,str.indexOf('\n',pos+1)))+"  \n";
              }
            }
            AudioClip ad=Applet.newAudioClip(new URL("file:///Mios/prj/java/client/spacemusic.au"));
            ad.play();
            //ventanaDialogo.cuadroMensaje(mensa);
            m.setText(mensa);
            m.setModal(true);
            m.setVisible(true);
            m.dispose();
            ad.stop();
          }
        }
        toRemote.println("QUIT");
        cogeRespuesta(fromRemote);
      }
      } catch ( IOException e ) {
        System.out.println(e.toString());
      }
  }



  public static void main(String args[]) throws IOException {
      int tiempo=15, intentos=1;

      telnetClient t=new telnetClient();
      if (args.length>=1 && args[0]!=null) {
        tiempo=Integer.parseInt(args[0]);
      }
      while (true) {
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("Obtenidoendo Correo. Pasada: "+(intentos++)+" Hora: "+new Date().toString());
        System.out.println("--------------------------------------------------------------------------------");
        t.sendRequest();
        try {
          Thread.sleep(tiempo*1000);
        } catch (Exception ex) {
          System.out.println(ex.toString());
        }
      }
      //System.exit(0);
  }
}
