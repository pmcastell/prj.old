import java.io.*;

import java.util.Vector;
import java.lang.Exception;
import java.util.StringTokenizer;
import netscape.security.PrivilegeManager;
import netscape.security.ForbiddenTargetException;
import netscape.security.*;

public class netscapeUtil {
   public static String contenido=netscapeUtil.listaPredeterminada();

   public netscapeUtil() {
   }
   String leerPueblo(StringTokenizer stTokens) {
      String poblacion="", telefono="";
      if (stTokens.hasMoreTokens()) {
         poblacion=stTokens.nextToken();
         if (poblacion==null) {
            poblacion="";
         }
         poblacion=poblacion+spaces(25-poblacion.length());
      }
      if (stTokens.hasMoreTokens()) {
         telefono=stTokens.nextToken().trim();
      }
      return poblacion+' '+telefono;
   }
   public String[] pueblosProvincia(String prov) {
      String linea, provincia="", pueblo;
      StringTokenizer stLinea= new StringTokenizer(contenido, "\n");
      StringTokenizer stTokens=new StringTokenizer("","");
      Vector aux=new Vector();

      if (prov==null || prov.equals("")) {
         return null;
      }
      prov=prov.trim();
      while (stLinea.hasMoreTokens() && !provincia.trim().equalsIgnoreCase(prov)) {
         linea=stLinea.nextToken();
         stTokens=new StringTokenizer(linea, "|");
         if (stTokens.hasMoreTokens()) {
            provincia=stTokens.nextToken();
         }
      }
      if (provincia.trim().equalsIgnoreCase(prov)) {
         do {
            aux.addElement(leerPueblo(stTokens));
            if (!stLinea.hasMoreTokens()) {
               break;
            }
            linea=stLinea.nextToken();
            stTokens=new StringTokenizer(linea, "|");
            if (stTokens.hasMoreTokens()) {
               provincia=stTokens.nextToken();
            }
         } while (provincia.trim().length()<=0);
      }
      return VectorToStringArray(aux);
   }


   public String[] leerProvincias() {
      String linea, provincia;
      StringTokenizer stLinea= new StringTokenizer(contenido, "\n");
      StringTokenizer stTokens;
      Vector aux=new Vector();

      while (stLinea.hasMoreTokens()) {
         linea=stLinea.nextToken();
         stTokens=new StringTokenizer(linea, "|");
         if (stTokens.hasMoreTokens()) {
            provincia=stTokens.nextToken();
            if (provincia!=null && provincia.trim().length()>0) {
               aux.addElement(provincia);
            }
         }
      }
      return VectorToStringArray(aux);
   }


   static String listaPredeterminada() {
      return
      "Álava       |Vitoria-Gasteiz         |945.196.000\n"
     +"Albacete    |Albacete                |967.258.000\n"
     +"Alicante    |Alcoy                   |965.534.000\n"
     +"            |Alicante                |965.912.000\n"
     +"            |Benidorm                |966.815.900\n"
     +"            |Denia                   |966.428.000\n"
     +"            |Elche                   |966.916.000\n"
     +"            |Elda                    |966.989.900\n"
     +"            |Moraira                 |966.486.000\n"
     +"            |Orihuela                |966.747.000\n"
     +"            |Torrevieja              |965.722.000\n"
     +"            |Villena                 |965.823.600\n"
     +"Almería     |Almería                 |950.185.000\n"
     +"            |El Ejido                |950.542.000\n"
     +"            |Vera                    |950.548.000\n"
     +"Asturias    |Avilés                  |985.125.000\n"
     +"            |Gijón                   |985.158.000\n"
     +"            |Oviedo                  |985.983.050\n"
     +"Ávila       |Ávila                   |920.205.000\n"
     +"Badajoz     |Badajoz                 |924.208.000\n"
     +"            |Mérida                  |924.383.200\n"
     +"            |Villanueva de la Serena |924.809.000\n"
     +"            |Zafra                   |924.565.000\n"
     +"Baleares    |Ibiza                   |971.309.000\n"
     +"            |Inca                    |971.887.100\n"
     +"            |Mahón                   |971.216.000\n"
     +"            |Manacor                 |971.848.000\n"
     +"            |Palma                   |971.210.050\n"
     +"            |Pollensa                |971.895.000\n"
     +"Barcelona   |Barcelona (2nodos)      |932.345.000\n"
     +"            |Berga                   |938.243.900\n"
     +"            |Canovelles              |938.642.400\n"
     +"            |Igualada                |938.075.000\n"
     +"            |Manlleu                 |938.521.900\n"
     +"            |Manresa                 |938.759.000\n"
     +"            |Mataró                  |937.413.000\n"
     +"            |Martorell               |937.768.600\n"
     +"            |Pineda                  |937.667.000\n"
     +"            |Sabadell                |937.345.000\n"
     +"            |Vic                     |938.815.000\n"
     +"            |Vilanova i la Geltrú    |938.116.800\n"
     +"            |Villafranca del Penedés |938.917.500\n"
     +"Burgos      |Burgos                  |947.417.000\n"
     +"            |Miranda de Ebro         |947.340.000\n"
     +"Cáceres     |Cáceres                 |927.623.000\n"
     +"            |Navalmoral de la Mata   |927.637.000\n"
     +"            |Plasencia               |927.428.900\n"
     +"            |Trujillo                |927.639.000\n"
     +"Cádiz       |Algeciras               |956.649.000\n"
     +"            |Arcos de la Frontera    |956.714.000\n"
     +"            |Cádiz                   |956.845.000\n"
     +"            |Jerez de la Frontera    |956.167.000\n"
     +"Cantabria   |Santander               |942.263.000\n"
     +"            |Torrelavega             |942.837.900\n"
     +"Castellón   |Benicarló               |964.408.000\n"
     +"            |Castellón               |964.735.000\n"
     +"Ciudad Real |Alcazar de San Juan     |926.580.400\n"
     +"            |Ciudad Real             |926.209.000\n"
     +"            |Puertollano             |926.449.600\n"
     +"            |Valdepeñas              |926.329.000\n"
     +"Córdoba     |Córdoba                 |957.225.000\n"
     +"            |Lucena                  |957.595.000\n"
     +"Cuenca      |Cuenca                  |969.170.000\n"
     +"Girona      |Figueres                |972.541.000\n"
     +"            |Girona                  |972.434.000\n"
     +"            |Lloret de Mar           |972.347.900\n"
     +"            |Olot                    |972.286.000\n"
     +"            |Palamós                 |972.609.000\n"
     +"            |Roses                   |972.252.900\n"
     +"            |Sant Feliu de Guixols   |972.804.000\n"
     +"            |Santa Coloma de Farners |972.879.900\n"
     +"            |Torroela de Montgri     |972.756.000\n"
     +"Granada     |Granada                 |958.182.000\n"
     +"            |Motril                  |958.832.100\n"
     +"Guadalajara |Guadalajara             |949.238.000\n"
     +"Guipúzcoa   |Beasain                 |943.803.000\n"
     +"            |Eibar/Zuloaga           |943.759.000\n"
     +"            |Mondragón               |943.785.000\n"
     +"            |San Sebastián-Donostia  |943.269.000\n"
     +"Huelva      |Huelva                  |959.205.000\n"
     +"Huesca      |Huesca                  |974.298.000\n"
     +"            |Monzón                  |974.418.900\n"
     +"Jaén        |Jaén                    |953.249.000\n"
     +"            |Linares                 |953.608.000\n"
     +"            |Ubeda                   |953.769.000\n"
     +"A Coruña    |A Coruña                |981.165.000\n"
     +"            |Ferrol                  |981.335.000\n"
     +"            |Ribeira                 |981.858.000\n"
     +"            |Santiago de Compostela  |981.529.000\n"
     +"La Rioja    |Logroño                 |941.498.000\n"
     +"Las Palmas  |Arrecife                |928.837.000\n"
     +"            |Las Palmas              |928.210.050\n"
     +"            |Puerto del Rosario      |928.853.000\n"
     +"            |San Agustín             |928.739.000\n"
     +"León        |León                    |987.899.000\n"
     +"            |Ponferrada              |987.469.900\n"
     +"Lleida      |Lleida                  |973.768.000\n"
     +"            |Seo de Urgel            |973.359.000\n"
     +"            |Tarrega                 |973.501.990\n"
     +"Lugo        |Lugo                    |982.286.000\n"
     +"Madrid      |Alcalá de Henares       |918.879.500\n"
     +"            |Aranjuez                |918.099.500\n"
     +"            |Arganda                 |918.769.900\n"
     +"            |Collado Villalba        |918.561.000\n"
     +"            |Colmenar Viejo          |918.488.900\n"
     +"            |Madrid (3 nodos)        |917.529.000\n"
     +"Málaga      |Antequera               |952.707.000\n"
     +"            |Fuengirola              |952.598.000\n"
     +"            |Málaga                  |952.079.000\n"
     +"            |Marbella                |952.767.000\n"
     +"            |Ronda                   |952.189.000\n"
     +"            |Vélez-Málaga            |952.548.000\n"
     +"Melilla     |Melilla                 |952.697.000\n"
     +"Murcia      |Cartagena               |968.557.000\n"
     +"            |Cieza                   |968.775.950\n"
     +"            |Lorca                   |968.479.900\n"
     +"            |Murcia                  |968.857.000\n"
     +"Navarra     |Pamplona                |948.367.000\n"
     +"            |Tudela                  |948.817.900\n"
     +"Orense      |Orense                  |988.379.000\n"
     +"Palencia    |Palencia                |979.164.000\n"
     +"Pontevedra  |Pontevedra              |986.777.000\n"
     +"            |Vigo                    |986.396.000\n"
     +"            |Villagarcía de Arosa    |986.545.000\n"
     +"Salamanca   |Salamanca               |923.319.000\n"
     +"Segovia     |Segovia                 |921.410.000\n"
     +"Sevilla     |Ecija                   |955.906.000\n"
     +"            |Morón de la Frontera    |955.855.000\n"
     +"            |Sevilla                 |954.547.000\n"
     +"            |Utrera                  |955.869.000\n"
     +"Soria       |Soria                   |975.259.000\n"
     +"Tarragona   |Amposta                 |977.716.900\n"
     +"            |El Vendrell             |977.156.900\n"
     +"            |Montroig del Mar        |977.813.900\n"
     +"            |Reus                    |977.123.000\n"
     +"            |Tarragona               |977.634.000\n"
     +"            |Tortosa                 |977.586.000\n"
     +"Tenerife    |Los Cristianos          |922.758.000\n"
     +"            |Puerto de la cruz       |922.339.000\n"
     +"            |Santa Cruz de la Palma  |922.189.000\n"
     +"            |Santa Cruz de Tenerife  |922.689.000\n"
     +"Teruel      |Teruel                  |978.640.000\n"
     +"Toledo      |Talavera de la Reina    |925.728.000\n"
     +"            |Toledo                  |925.330.000\n"
     +"Valencia    |Alzira                  |962.457.900\n"
     +"            |Gandía                  |962.967.000\n"
     +"            |Onteniente              |962.918.300\n"
     +"            |Sagunto                 |962.656.700\n"
     +"            |Valencia                |961.839.050\n"
     +"            |Xativa                  |962.289.000\n"
     +"Valladolid  |Valladolid              |983.240.000\n"
     +"Vizcaya     |Bilbao-Bilbo            |946.358.050\n"
     +"            |Durango                 |946.210.000\n"
     +"Zamora      |Benavente               |980.639.900\n"
     +"            |Zamora                  |980.506.000\n"
     +"Zaragoza    |Calatayud               |976.889.900\n"
     +"            |Zaragoza                |976.719.050\n"
     +"Zonas_sin_nodo_local|Zona sin nodo   |901.505.055";
//     +"--------------------|----------------|-----------";
  }
   public String [] VectorToStringArray(Vector v) {
      if (v.size()==0) {
         return null;
      }
      String res[]=new String[v.size()];
      for(int i=0; i<v.size(); i++) {
         res[i]=v.elementAt(i).toString();
      }
      return res;
   }
   public String StringArrayToString(String []sa) {
      if (sa==null) {
         return "";
      }
      String res="";
      for(int i=0; i<sa.length; i++) {
         res+=sa[i]+'\n';
      }
      return res;
   }

   public String spaces(int n) {
      String res=new String();
      for(int i=0; i<n; i++) {
         res+=' ';
      }
      return res;
   }

 /*   public String cargarFichero(String f) throws FileNotFoundException, IOException {
      BufferedReader b;
      String t="",r;
      PrivilegeManager.enablePrivilege("UniversalFileAccess");
      try {
         PrivilegeManager.checkPrivilegeGranted("UniversalFileAccess");
      } catch (ForbiddenTargetException ex) {
          ventanaDialogo.vMensaje("Error No se concedió permiso para acceder al fichero.");
      }
      b=new BufferedReader(new FileReader(f));
      while (true) {
         r=b.readLine();
         if (r==null) {
            break;
         } else {
            t+=r+'\n';
         }
      }
      b.close();
      return t;
   }*/
 /*   public void salvarFichero(String f, String contenido) {
      BufferedWriter b;
      PrivilegeManager.enablePrivilege("UniversalFileAccess");
      try {
          PrivilegeManager.checkPrivilegeGranted("UniversalFileAccess");
      } catch (ForbiddenTargetException ex) {
          ventanaDialogo.vMensaje("Se denegó el permiso de acceso.");
      }
      try {
         b=new BufferedWriter(new FileWriter(f));
         b.write(contenido);
         b.flush();
         b.close();
      } catch (Exception ex) {
         ventanaDialogo.vMensaje(f+": "+ex);
      }
   }*/
/*   public void cargarListaProvincias(String f) {
      try {
         contenido=this.cargarFichero(f);
      } catch (FileNotFoundException ex) {
      } catch (IOException ex) {
      }
   }*/
/*   public static void main(String args[]) {
      netscapeUtil nu=new netscapeUtil();
      String pr[]=nu.pueblosProvincia("Zonas_sin_nodo_local");
      System.out.println(pr[0]);
   }*/


}
