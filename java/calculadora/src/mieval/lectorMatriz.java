package mieval;


import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.Vector;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.*;
import mieval.tipo.miArray;
import mieval.tipo.variable;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author usuario
 */
public class lectorMatriz extends JDialog implements lectorMatrizInterfaz {
   JTable jt;
   JSpinner filas=new JSpinner(),cols=new JSpinner(), incremento=new JSpinner(
           new javax.swing.SpinnerNumberModel(new Double(1.0), new Double(Double.NEGATIVE_INFINITY), new Double(Double.POSITIVE_INFINITY), new Double(1.0)));
           //new javax.swing.SpinnerNumberModel(1.0, Double.MIN_VALUE, 10000.0, 0.1));
           //new javax.swing.SpinnerNumberModel()
           //1.0, 0.0, 10000.0, 0.1));
   JScrollPane jst=new JScrollPane();
   JPanel panelNorte=new JPanel(),panelSur=new JPanel();
   JLabel leyendaFils=new JLabel("Filas: ");
   JLabel leyendaCols=new JLabel("Cols:: ");
   JLabel leyendaInc=new JLabel("Inc. Autom.:");
   JButton Aceptar=new JButton("Aceptar");
   JButton AutoRellenar=new JButton("AutoRellenar");
   JButton Borrar=new JButton("Borrar");
   ListModel cabFilas;
   String titulosColumnas[];
   Double datos[][];
   JList rowHeader;        
   DefaultTableModel dtm;
   Dimension pos;
   Rectangle dimensionCelda;
   int ancho, alto;
   int nFilas,nCols;
           
   public miArray getMatriz(){
      int numFils=jt.getRowCount();
      Vector v=new Vector(numFils);
      double valor;
      
      if (numFils==1) {
         for (int i=0;i<jt.getColumnCount();i++) {
            if (jt.getValueAt(0, i)==null) {
               valor=0;
            } else {
               valor=((Double)jt.getValueAt(0, i)).doubleValue();
            }
            v.addElement(new variable(valor));
         }
      } else {
         for(int i=0;i<jt.getRowCount();i++) {
            Vector fil=new Vector();
            for(int j=0;j<jt.getColumnCount();j++) {
               if (jt.getValueAt(i, j)==null) {
                  valor=0;
               } else {
                  valor=((Double)jt.getValueAt(i, j)).doubleValue();
               }
               fil.addElement(new variable(valor));
            }
            v.addElement(new variable(new miArray(fil)));
         }
      }
      return new miArray(v);
   }
   public double[][] getMatriz2() {
      double [][]res=new double[jt.getRowCount()][jt.getColumnCount()];
      for(int i=0;i<jt.getRowCount();i++) {
         for(int j=0;j<jt.getColumnCount();j++) {
            //res[i][j]=Double.parseDouble(((String)jt.getValueAt(i, j)));
            res[i][j]=((Double)jt.getValueAt(i, j)).doubleValue();
         }
      }
      return res;
   }
   
   void reEmpaquetar() {
      
      alto=61+panelNorte.getHeight()+dimensionCelda.height*jt.getRowCount()+panelSur.getHeight();
      ancho=18+dimensionCelda.width*(jt.getColumnCount()+1);
      ancho=Math.max(ancho, (int)panelNorte.getMinimumSize().getWidth());
      if (ancho>this.getWidth()) {
         ancho=this.getWidth();
         alto+=10;
      } 
      if (alto>this.getHeight()) {
         alto=this.getHeight();
      } else {
         alto+=0;
      }
      this.setSize(ancho,alto); 
   }
   
   AbstractListModel nuevaListModel(int f) {
      final int filas=f;
      return new AbstractListModel() {
         String headers[] = new String[filas];
         {
            for(int i=0;i<filas;i++) { headers[i]=""+i; }
         }
         public int getSize() {return headers.length; }
         public Object getElementAt(int index) { return headers[index]; }
      };
   }
   
   JTable nuevaJTable(int f,int c) {
      final int filas=f,cols=c;

      jt= new JTable(f,c) {
        DefaultTableCellRenderer renderRight=new DefaultTableCellRenderer();
        {//initializer block
            renderRight.setHorizontalAlignment(SwingConstants.RIGHT);
        }
        public TableCellRenderer getCellRenderer(int arg0, int arg1) {
             return renderRight;
        }
      };
      this.cabFilas = nuevaListModel(filas);
      jt.setAutoscrolls(true); jt.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
      rowHeader = new JList(this.cabFilas);
      rowHeader.setFixedCellWidth((int)jt.getCellRect(0, 0, true).getWidth());
      rowHeader.setFixedCellHeight(jt.getRowHeight()+jt.getRowMargin()); //+ table.getIntercellSpacing().height);
      rowHeader.setCellRenderer(new RowHeaderRenderer(jt));
      rowHeader.setBorder(BorderFactory.createEtchedBorder());//1));//1, 1, 1, 1));
      rowHeader.setVisibleRowCount(filas);
      titulosColumnas=new String[c];
      for(int i=0;i<c;i++) {
         titulosColumnas[i]=""+i;
      }
      datos=new Double[f][c];
      dtm=new javax.swing.table.DefaultTableModel(
            datos,
            titulosColumnas
        ) {
            //Class[] types = new Class [] {
            //    java.lang.String.class, java.lang.Double.class
            //};
            //boolean[] canEdit = new boolean [] {
            //    false, false
            //};
            public Class getColumnClass(int columnIndex) {return java.lang.Double.class;}
            public boolean isCellEditable(int rowIndex, int columnIndex) { //return canEdit [columnIndex];
               return true; }
      };;
      jt.setModel(dtm); jt.setShowGrid(true); jt.setCellSelectionEnabled(true);
      jst.setViewportView(jt);
      jst.setRowHeaderView(rowHeader);
      return jt;
   }
   public lectorMatriz() {
      this.setLayout(new BorderLayout());
      filas.setName("filas");filas.addChangeListener(new SpinnerListener(this));
      cols.setName("cols");cols.addChangeListener(new SpinnerListener(this));
      //JLabel prompt=new JLabel("Introduce la matriz:");
      //prompt.setFont(new java.awt.Font("Dialog",1,12));
      panelNorte.setLayout(new java.awt.FlowLayout(FlowLayout.LEFT));
      panelNorte.add(leyendaFils);
      panelNorte.add(filas);
      panelNorte.add(leyendaCols);
      panelNorte.add(cols);
      panelNorte.add(leyendaInc);
      panelNorte.add(incremento);
      //panelNorte.add(prompt);
      this.add(panelNorte,BorderLayout.NORTH);
      panelSur.setLayout(new GridLayout());
      final JDialog self=this;
      Aceptar.addActionListener(new ActionListener() { 
         public void actionPerformed(java.awt.event.ActionEvent evt) {
               WindowEvent wev = new WindowEvent(self, WindowEvent.WINDOW_CLOSING);
               Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(wev);
         }
      });
      AutoRellenar.setToolTipText("AutoRellenar");
      AutoRellenar.addActionListener(new ActionListener() { 
         public void actionPerformed(java.awt.event.ActionEvent evt) {
               int filas=jt.getRowCount(),cols=jt.getColumnCount();
               double valor=0.0; double inc=((Double)incremento.getValue()).doubleValue();
               if (jt.getValueAt(0, 0)!=null) {
                  valor=((Double)jt.getValueAt(0, 0)).doubleValue();
               }
               for(int i=0;i<filas;i++) {
                  for(int j=0;j<cols;j++) {
                     jt.setValueAt(new Double(valor), i, j);
                     valor+=inc;
                  }
               }
         }
      });
      Borrar.addActionListener(new ActionListener() { 
         public void actionPerformed(java.awt.event.ActionEvent evt) {
               int filas=jt.getRowCount(),cols=jt.getColumnCount();
               for(int i=0;i<filas;i++) {
                  for(int j=0;j<cols;j++) {
                     jt.setValueAt(null, i, j);
                  }
               }
         }
      });
      panelSur.add(Aceptar);
      panelSur.add(AutoRellenar);
      panelSur.add(Borrar);
      this.add(panelSur,BorderLayout.SOUTH);
      this.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
      this.nuevaJTable(1, 1);
      this.add(jst,BorderLayout.CENTER);
      this.pack();
      this.dimensionCelda=jt.getCellRect(0, 0, true);
      pos = Toolkit.getDefaultToolkit().getScreenSize();
      this.setLocation((pos.width - this.getSize().width) / 2,
                                          (pos.height - this.getSize().height) / 2);
      this.setModal(true);
   }
   public void leerMatriz(miArray a) {
      try {
         if (a.elemento(0).tipo=='M') {
            leerMatriz(a.getDimension(0),a.elemento(0).getDimension(0),a);
         } else {
            leerMatriz(1,a.getDimension(0),a);
         }
      } catch (Exception ex) {
         ex.printStackTrace();
      }
   }
   public void leerMatriz(int f, int c,miArray a) {
      this.setTitle("Lectura Matriz["+f+","+c+"]");      
      this.nuevaJTable(f, c);
      filas.setValue(new Integer(f));
      cols.setValue(new Integer(c));
      
      if (a!=null) {
         try {
            variable v;
            for(int i=0;i<f;i++) {
               v=a.elemento(i);
               for(int j=0;j<c;j++) {
                  if (v.tipo=='M') {
                        jt.setValueAt(new Double(((miArray)v.valor).elemento(j).doubleValue()),i,j);
                  } else {
                        jt.setValueAt(new Double(a.elemento(j).doubleValue()),i,j);
                  }
                }
            }
         } catch (Exception ex) {
            ex.printStackTrace();
         }
   
      }
      //this.getContentPane().setSize(ancho, alto);
      //this.add(prompt,BorderLayout.NORTH);
      //this.pack();
      this.reEmpaquetar();
      //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      this.setVisible(true);

            //jt.getTableHeader().setVisible(true);
      //javax.swing.JPanel jtablePanel=new javax.swing.JPanel();
      //jst.setRowHeaderView(jst);
      //jst.setViewportView(jt);
      //jst.setHorizontalScrollBar(new JScrollBar());
      //jtablePanel.setLayout(new BorderLayout());
      //jtablePanel.add(jt.getTableHeader(),BorderLayout.NORTH);
      
      //javax.swing.JScrollPane jst=new javax.swing.JScrollPane();
      //jst.setViewportView(jt);
      //jst.setHorizontalScrollBar(jst.createHorizontalScrollBar());
      
      //jst.setAutoscrolls(true);
      //jtablePanel.add(jst,BorderLayout.CENTER);
      
      //jst.getCon
      //((DefaultTableModel)jt.getModel()).getC
      //this.add(jst,BorderLayout.CENTER);
      
      
      //jt.setSize(ancho, alto);
      //jst.setSize(ancho,alto);
      //jst.setCorner(JScrollPane.UPPER_LEFT_CORNER, jt.getTableHeader());

      
   }
   public void leerMatriz(int c) {
      
      leerMatriz(1,c,null);
   }
   public void leerMatriz(int f,int c) {
      leerMatriz(f,c,null);
   }
   public static void main(String args[]) {
       try {
           //Correcion hecha por Chuster Boy ;)
           //UIManager.setLookAndFeel("net.sourceforge.napkinlaf.NapkinLookAndFeel");
               //UIManager.setLookAndFeel(new InfoNodeLookAndFeel());
               //UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
               //UIManager.setLookAndFeel(new net.infonode.gui.laf.InfoNodeLookAndFeel());
                 //UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
                 //UIManager.setLookAndFeel(new com.sun.java.swing.plaf.gtk.GTKLookAndFeel());
                 UIManager.setLookAndFeel(new com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel());
       }
       catch (Exception e)
       {
          e.printStackTrace();
       }
       lectorMatriz le=new lectorMatriz();
       le.leerMatriz(1,2);
       double m[][]=le.getMatriz2();
       le.dispose();
       System.out.println(m);
       for(int i=0;i<m.length;i++) {
          System.out.print("\n");
          System.out.print("{");
          for(int j=0; j<m[0].length;j++) {
             System.out.print(m[i][j]+",");
             
          }
          System.out.println("}");
       }
   }
   
}
class SpinnerListener implements ChangeListener {
    lectorMatriz lm=null;
    
    public SpinnerListener(lectorMatriz lm) {
       this.lm=lm;
    }
    public void stateChanged(ChangeEvent evt) {
        int nFilas, nCols;
        
        JSpinner spinner = (JSpinner)evt.getSource();
        if (spinner.getName()=="filas") {
            int nFilasOld=lm.jt.getRowCount();
            nFilas=((Integer)lm.filas.getValue()).intValue();
            ((DefaultTableModel)lm.jt.getModel()).setRowCount(nFilas);
            lm.cabFilas=lm.nuevaListModel(nFilas);
            this.lm.rowHeader.setModel(lm.cabFilas);
            lm.jst.setRowHeaderView(lm.rowHeader);
            //lm.setSize(lm.getWidth(), (int) (lm.getHeight()+(nFilas-nFilasOld)*lm.dimensionCelda.getHeight()));
        } else if (spinner.getName()=="cols") {
            int nColsOld=lm.jt.getColumnCount();
            nCols=((Integer)lm.cols.getValue()).intValue();
            ((DefaultTableModel)lm.jt.getModel()).setColumnCount(nCols);
            for(int i=0;i<nCols;i++) {
               TableColumn tc = lm.jt.getTableHeader().getColumnModel().getColumn(i);
               tc.setHeaderValue( ""+i );
            }
            //lm.setSize((int)(lm.getWidth()+(nCols-nColsOld)*lm.dimensionCelda.getWidth()),lm.getHeight());
        }
        lm.jst.setSize(lm.jt.getMinimumSize());
        lm.pack();
        lm.reEmpaquetar();
        lm.setTitle("Lectura Matriz["+lm.jt.getRowCount()+","+lm.jt.getColumnCount()+"]");
    }
}

class RowHeaderRenderer extends JLabel implements ListCellRenderer {

  RowHeaderRenderer(JTable table) {
    JTableHeader header = table.getTableHeader();
    setOpaque(true);
    setBorder(UIManager.getBorder("TableHeader.cellBorder"));
    setHorizontalAlignment(CENTER);
    setForeground(header.getForeground());
    setBackground(header.getBackground());
    setFont(header.getFont());
  }

  public Component getListCellRendererComponent(JList list, Object value,
      int index, boolean isSelected, boolean cellHasFocus) {
    setText((value == null) ? "" : value.toString());
    return this;
  }
}
