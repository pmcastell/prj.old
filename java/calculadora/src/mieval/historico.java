/******************************************************************************/
/******************         entrada.java        *******************************/
/******************************************************************************/
package mieval; 
/******************************************************************************/
/********************** entradas de la tabla de s�mbolos **********************/
/******************************************************************************/
public class historico {
	java.util.Vector<String> lista=new java.util.Vector<String>();
	private int indice=0;
	
	public historico() {}
	public int getItemCount() {
      return lista.size();
   }
   public String getItem(int i) {
      return lista.elementAt(i);
   }
   public void removeElement(int i) {
      lista.removeElementAt(i);
   }
	public int añadir(String s) {
		String actual;
		s=s.trim();
		for(int i=0; i<lista.size();i++) {
			actual=lista.elementAt(i).trim();
			if (actual.equalsIgnoreCase(s)) {
				//return indice=i;
            lista.removeElementAt(i);
			}
		}
		if (lista.add(s)) {
			return indice=lista.size();
		} else {
			return -1;
		}
	}
	public int quitar(int ind) {
		if (lista.size()>0 && 0<=ind && ind<lista.size()) {
			lista.removeElementAt(ind);
			return ind;
		} else {
			return -1;
		}
	}
	public int quitar() {
		return quitar(lista.size()-1);
	}
	public String actual() {
		if (indice>lista.size() && lista.size()>0) {
			return lista.elementAt(lista.size()-1);
		} else if (indice<0 && lista.size()>0) {
			return lista.elementAt(0);
		} else if (0<=indice && indice<lista.size()) { 
			return lista.elementAt(indice);
		} else {
			return "";
		}
	}
	public int avanza() {
		if (indice<lista.size()-1) {
			return ++indice;
		} else {
			return -1;
		}
	}
	public int retrocede() {
		if (indice>0) {
			return --indice;
		} else {
			return -1;
		}
	}
}
