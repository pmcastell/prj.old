import java.util.Vector;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;


public class miJList extends JScrollPane {
   Vector actListeners=new Vector();
   Vector lista=new Vector();
   JList jlista=new JList();
   lineFormater lf=null;
   public miJList() {
      super();
      getViewport().add(jlista, null);
      createHorizontalScrollBar();
      createVerticalScrollBar();
      setAutoscrolls(true);
      setDoubleBuffered(true);
      jlista.setListData(lista);
//      lista.addElement("   ");
      jlista.addMouseListener(new MouseAdapter() {
         public void mousePressed(MouseEvent e) {
            if (e.getClickCount()>=2) {
               for(int i=0; i<actListeners.size(); i++) {
                  ((ActionListener) actListeners.elementAt(i)).actionPerformed(
                    new ActionEvent(miJList.this, ActionEvent.ACTION_PERFORMED, null));
               }
            }
         }
      });

   }
   public void setLf(lineFormater lf) {
      this.lf=lf;
   }
   public miJList(lineFormater lf) {
      this();
      this.lf=lf;
   }
   public void sort() {
      String s1, s2;
      for(int i=0; i<lista.size()-1; i++) {
         s1=(String) lista.elementAt(i);
         for(int j=i+1; j<lista.size(); j++) {
            s2=(String) lista.elementAt(j);
            if (s1.compareTo(s2)>0) {
               lista.setElementAt(s2,i);
               lista.setElementAt(s1,j);
               s1=s2;
            }
         }
      }
   }

   public void cargaFichero() {
      Frame f=new Frame();
      FileDialog fichero=new FileDialog(f,"Abrir Fichero: ",FileDialog.LOAD);
      fichero.show();
      cargaFichero(fichero.getDirectory()+fichero.getFile());
   }



   public void cargaFichero(String file) {
      BufferedReader b;
      String r;

      try {
         b=new BufferedReader(new FileReader(file));
         while (true) {
            r=b.readLine();
            if (r==null) {
               break;
            } else {
               if (this.lf!=null) {
                  this.addItem(lf.formatLine(r));
               } else {
                  this.addItem(r);
               }
            }
         }
         b.close();
      } catch (Exception ex) {}
   }
   public void salvaFichero() {
      Frame f=new Frame();
      FileDialog fichero=new FileDialog(f,"Abrir Fichero: ",FileDialog.SAVE);
      fichero.show();
      salvaFichero(fichero.getDirectory()+fichero.getFile());
   }

   public void salvaFichero(String file) {
      BufferedWriter b;
      String r;

      try {
         b=new BufferedWriter(new FileWriter(file));
         for(int i=0; i<this.getItemCount(); i++) {
            b.write(this.getItem(i)+'\n');
         }
         b.flush();
         b.close();
      } catch (Exception ex) {}
   }
   public boolean isSelectedIndex(int i) {
      return this.jlista.isSelectedIndex(i);
   }
   public void addActionListener(ActionListener a) {
      actListeners.addElement(a);
   }
   public void removeActionListener(ActionListener a) {
      actListeners.removeElement(a);
   }

   public void addItem(String s) {
      lista.addElement(s);
      jlista.setListData(lista);
      jlista.ensureIndexIsVisible(jlista.getModel().getSize());
      jlista.repaint();
   }
   public String getSelectedItem() {
      return jlista.getSelectedValue().toString();
   }
   public int getItemCount() {
      return jlista.getModel().getSize();
   }
   public String getItem(int i) {
      return (String) lista.elementAt(i);
   }
   public void select(int i) {
      if (jlista!=null) {
         jlista.setSelectedIndex(i);
         jlista.repaint();
      }
   }
   public void removeElement(int i) {
      lista.removeElementAt(i);
      jlista.setListData(lista);
      jlista.repaint();
   }
   public void setFont(Font f) {
      if (jlista!=null) {
         jlista.setFont(f);
         jlista.repaint();
      }
   }
   public void setBounds(Rectangle r) {
      super.setBounds(r);
      if (jlista!=null) {
         jlista.setBounds(r);
         jlista.repaint();
      }
   }
   public int getSelectedIndex() {
      if (jlista!=null) {
         return jlista.getSelectedIndex();
      }
      return -1;
   }
   public void borrar() {
      lista.removeAllElements();
      jlista.setListData(lista);
      jlista.repaint();
   }
}


