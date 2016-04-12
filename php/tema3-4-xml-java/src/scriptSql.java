import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class scriptSql {
	static String listaValoresComa(String lista) {
		return listaValoresComa(lista,false);
	}
	static String listaValoresComa(String lista,boolean apostrofes) {
		int i=0;
		String apendix="";
		String res="";
		if (apostrofes) {
			apendix="'";
		}
		String tokens[]=lista.split("\\s");
		for(i=0; i<tokens.length;i++) {
			res+=apendix+tokens[i]+apendix;
			if (i<tokens.length-1) {
				res+=',';
			}
		}
		return res;
	}
	public static String scriptSqlToString(String fichero) {
		// TODO Auto-generated constructor stub
		String res="";
		try {
			File file = new File(fichero);
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setValidating(true);
			DocumentBuilder db = dbf.newDocumentBuilder();
			//ErrorHandler errHd=newErrorHandler();
			//db.setErrorHandler(errHd);
			Document doc = db.parse(file); 
			
			
			doc.getDocumentElement().normalize();
			res+="DROP DATABASE IF EXISTS "+doc.getDocumentElement().getAttribute("nombre")+";\n";
			res+="CREATE DATABASE "+doc.getDocumentElement().getAttribute("nombre")+";\n";
			res+="Use "+doc.getDocumentElement().getAttribute("nombre")+";\n";
			
			NodeList tablas = doc.getElementsByTagName("tabla");
			//System.out.println("Procesando las tablas:\n");
			for (int s = 0; s < tablas.getLength(); s++) {
				Element tabla = (Element) tablas.item(s);
				if (tabla.getNodeType() == Node.ELEMENT_NODE && tabla.getNodeName()=="tabla") {
					res+="CREATE TABLE "+tabla.getAttribute("nombre")+"(\n";
					NodeList campos = tabla.getElementsByTagName("campo");
					for(int c=0; c<campos.getLength(); c++) {
						Element campo=(Element) campos.item(c);
						res+="\t"+campo.getAttribute("nombre")+" ";
						if (campo.getAttribute("tipo").equalsIgnoreCase("ENUM")) {
							res+="ENUM ("+listaValoresComa(campo.getAttribute("lista_de_valores"),true)+")";
						} else {
							res+=campo.getAttribute("tipo");
							if (campo.getAttribute("longitud").length()!=0) {
								res+="("+campo.getAttribute("longitud")+")";
							}
						}
						if (campo.getAttribute("null")!=null) {
							if (campo.getAttribute("null").equalsIgnoreCase("1") || campo.getAttribute("null").equalsIgnoreCase("true")) {
								res+=" NULL,\n";
							} else {
								res+=" NOT NULL,\n";
							}
						}
					}
					NodeList claves=tabla.getElementsByTagName("primary_key");
					Element clave=(Element) claves.item(0);
					res+="\tPRIMARY KEY("+listaValoresComa(clave.getAttribute("campos_clave"))+")";
					NodeList clavesAjenas=tabla.getElementsByTagName("foreign_key");
					for(int cA=0;cA<clavesAjenas.getLength();cA++) {
						Element claveAjena=(Element)clavesAjenas.item(cA);
						res+=",\n\tFOREIGN KEY("+listaValoresComa(claveAjena.getAttribute("campos_clave"))+") REFERENCES "+claveAjena.getAttribute("tabla_ajena")+
								"("+listaValoresComa(claveAjena.getAttribute("campos_relacionados"))+")";
					}
					res+="\n) ENGINE=InnoDB;\n";
					
				}
				
			}
		} catch (org.xml.sax.SAXParseException ex) {
			//ex.printStackTrace();
			System.out.println("Error en línea: "+ex.getLineNumber()+" --> "+ex.getMessage());
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if (args.length==0) {
			System.out.println("Debes especificar la ruta completa al fichero xml");
			System.exit(1);
		}
		System.out.print(scriptSql.scriptSqlToString(args[0]));
		
	}

}
