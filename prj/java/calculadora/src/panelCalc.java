import java.awt.Color;
import java.awt.event.*;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import mieval.ctes_eval;
import mieval.lectorMatriz;
import mieval.tipo.variable;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * panelCalc.java
 *
 * Created on 10-mar-2011, 12:41:25
 */

/**
 *
 * @author usuario
 */
public class panelCalc extends javax.swing.JPanel implements mieval.impresor,mieval.notificado {

    /** Creates new form panelCalc */
    public panelCalc() {
        initComponents();
        DefaultTableModel model = (DefaultTableModel) this.funciones.getModel();
         String s="+ , 3+3->6 | 'ho'+'la'->'hola' \n++ , pre/post-incremento: ++a"
    +" | a++\n- , resta: 3- 3->0 | 'hola'-'la'->'hola'\n-- , pre/post-"
    +"decremento: --a | a--\n>= , mayor o igual: 3>=2->true\n"
    +"<= , menor o igual: 3<=2->false\nmod , módulo: 4 mod 3->1\n% , idem"
    +" anterior\nnot , negación lógica: not true->false | not false->true\n!"
    +" , idem anterior\n"
    +"or , 'o' lógico: true or false->true | false or false->false\n| , idem"
    +" anterior\n|| , idem anterior\n!= , distinto: 3!=3->false\n<> , idem"
    +" anterior\n== , comprobación igualdad: 4==5->false | 5==5->true\n"
    +"? , ultimo valor calculado: ? + 3\nabs , valor absoluto: abs(-5.5)->5.5"
    +"\nacos , arcocoseno: acos 0->pi/2\nand | && | & , 'y' logico: x and y\n"
    +"asen , arcoseno: asen 1->pi/2\n"
    +"atan , arcotangente: atan 1->pi/4\natan2 , atan2(3;4)\nceil , siguiente"
    +" entero: ceil 3.45->4\ncos , coseno: cos 0\ncosh , coseno hiperbolico: "
    +"cosh x\nderiv , derivada: deriv('x^3')\n"
    +"derivada , derivada: deriv('x^3')\ndim , crear matrices: dim a[3;3;3]\n"
    +"div , division entera: 4 div 3\ne , 2.718281828459045\neval , eval('3+5')"
    +"->8\nexec , ejecuta programa: exec('prg';a;b;c)\n"
    +"exp , exponencial: exp(2)->e^2\nfalse , falso: 4!=4->false\nfloor , "
    +"anterior entero: floor(4.5)->4\n"
    +"for , for(<lista-coma-insts.>;<condicion>;<lista-coma-insts>) { <lista-"
    +"instrs.> }\nif , if (<condicion>) { <lista-insts.> } [elseif { "
    +"<lista-insts.> }...] [else {<lista-insts.>}]\n"
    +"iif , funcion condicional: iif(3>5;3;5)->5\nint , parte entera: int 3.5"
    +" ->3\nintegral , integral definida: integral('x^2';0;2)->9\nlen , "
    +"longitud elemento: len('hola')->4\n"
    +"ln , logaritmo neperiano: ln e->1\nlog , logaritmo base: log(2;2^3)->3\n"
    +"pi , 3.141592653589793\nsen , seno: sen(pi/2)->1\nsenh , seno"
    +" hiperbólico: senh 1-> 1.1752011936\n"
    +"simp , simplifica expresiones: simp('x*x*x*x*x*x*x')->x^7\nsqrt , raíz"
    +" cuadrada: sqrt 2->1.4142135624\nsubstr , subcadena: substr('hola';0;1)"
    +"->'ho'\ntan , tangente: tan(pi/4)->1\n"
    +"tanh , tangente hiperbólica: tanh 1->0.761594156\ntolower , convierte"
    +" minúsculas: tolower('FRAN')->'fran'\ntoupper , convierte mayúsculas: "
    +"toupper('fran')->'FRAN'\n"
    +"true , verdadero: 3==4->true\ntype , tipo de la variable: type('hola')"
    +"->'S'\nwhile , while(<condicion>) { <lista-insts.> }\n";
        java.util.StringTokenizer st=new java.util.StringTokenizer(s,"\n");
        while (st.hasMoreTokens()) {
           model.addRow(st.nextToken().split(","));
        }
    historicoAdd.setToolTipText("Sustituir Expresión por Selección");
    calcular.setToolTipText("Evaluar la expresi¢n");
    borrarExpresion.setToolTipText("Limpia el campo de expresión");
    borrarResultado.setToolTipText("Limpia el campo de resultado");
    salvarVariables.setToolTipText("Salva todas las variables de la calculadora");
    cargarVariables.setToolTipText("Carga las variables previamente salvadas de un fichero");
    historicoConcatenar.setToolTipText("A¤adir a la expresión existente");
    funcionesConcatenar.setToolTipText("A¤adir a la expresión existente");
    funcionesAdd.setToolTipText("Sustituir expresión existente por selección");
    final int ancho=50000;
    funciones.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
    funciones.getColumnModel().getColumn(0).setPreferredWidth(120);
    funciones.getColumnModel().getColumn(1).setPreferredWidth(ancho);
    funciones.addMouseListener(new MouseAdapter(){
         public void mouseClicked(MouseEvent e){
            if (e.getClickCount() == 2){
               expresion.setText(expresion.getText()+funciones.getValueAt(funciones.getSelectedRow(), 0).toString().trim());
               expresion.grabFocus();
            }
         }
     });
    variables.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
    variables.getColumnModel().getColumn(0).setPreferredWidth(80);
    variables.getColumnModel().getColumn(1).setPreferredWidth(ancho);
    variables.addMouseListener(new MouseAdapter(){
         public void mouseClicked(MouseEvent e){
            if (e.getClickCount() == 2){
               expresion.setText(expresion.getText()+variables.getValueAt(variables.getSelectedRow(), 0).toString().trim());
               expresion.grabFocus();
            }
         }
     });
   

    historico.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
    historico.getColumnModel().getColumn(0).setPreferredWidth(ancho);
    historico.addMouseListener(new MouseAdapter(){
         public void mouseClicked(MouseEvent e){
            if (e.getClickCount() == 2){
               expresion.setText(expresion.getText()+historico.getValueAt(historico.getSelectedRow(), 0).toString().trim());
               expresion.grabFocus();
            }
         }
     });
    salidaProgramas.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
    salidaProgramas.getColumnModel().getColumn(0).setPreferredWidth(ancho);

        DefaultTableModel model2 = (DefaultTableModel) this.variables.getModel();
        /*for(int i=0;i<=20;i++) {
            //this.tablaFunciones.getModel().addRow(new java.lang.String[]{"func"+i, "f"+i});
            //this.tablaFunciones.add
            model.addRow(new String[]{"func"+i, "f"+i});
            model2.addRow(new String[]{"func"+i, "f"+i});
            //historico.setText(historico.getText()+"\nLínea: "+i);
            ((DefaultTableModel) historico.getModel()).addRow(new String[]{"Comando: "+i});
            ((DefaultTableModel) salidaProgramas.getModel()).addRow(new String[]{"Programa: "+i});
            //salidaProgramas.setText(salidaProgramas.getText()+"\nLínea: "+i);
        }*/
        expresion.grabFocus();
        expresion.requestFocus();
        expresion.requestFocusInWindow();
        eval.addNotificado(this);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        funciones = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        variables = new javax.swing.JTable();
        funcionesAdd = new javax.swing.JButton();
        funcionesConcatenar = new javax.swing.JButton();
        historicoConcatenar = new javax.swing.JButton();
        historicoAdd = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        salidaProgramas = new javax.swing.JTable() {
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }

        };
        jScrollPane4 = new javax.swing.JScrollPane();
        historico = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }

        };
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        expresion = new javax.swing.JTextField();
        resultado = new javax.swing.JTextField();
        calcular = new javax.swing.JButton();
        borrarResultado = new javax.swing.JButton();
        borrarExpresion = new javax.swing.JButton();
        salvarVariables = new javax.swing.JButton();
        cargarVariables = new javax.swing.JButton();

        funciones.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        funciones.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Función/Operador", "Ejemplo"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(funciones);
        funciones.getColumnModel().getColumn(1).setHeaderValue("Ejemplo");

        variables.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        variables.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Variable", "Valor"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        variables.setCellSelectionEnabled(false);
        variables.setFocusCycleRoot(true);
        variables.setRowSelectionAllowed(true);
        jScrollPane2.setViewportView(variables);
        variables.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        funcionesAdd.setText(">");
        funcionesAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                funcionesAddActionPerformed(evt);
            }
        });

        funcionesConcatenar.setText(">>");
        funcionesConcatenar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                funcionesConcatenarActionPerformed(evt);
            }
        });

        historicoConcatenar.setText(">>");
        historicoConcatenar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                historicoConcatenarActionPerformed(evt);
            }
        });

        historicoAdd.setText(">");
        historicoAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                historicoAddActionPerformed(evt);
            }
        });

        salidaProgramas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Salida de Programas"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane3.setViewportView(salidaProgramas);

        historico.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        historico.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Histórico de Comandos"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane4.setViewportView(historico);

        jLabel3.setText("Expresión:");

        jLabel4.setText("Resultado:");

        expresion.setFont(new java.awt.Font("Verdana", 1, 22)); // NOI18N
        expresion.setMaximumSize(new java.awt.Dimension(1100, 1100));
        expresion.setPreferredSize(new java.awt.Dimension(700, 38));
        expresion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                expresionActionPerformed(evt);
            }
        });
        expresion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                expresionKeyPressed(evt);
            }
        });

        resultado.setFont(new java.awt.Font("Verdana", 1, 22)); // NOI18N
        resultado.setMaximumSize(new java.awt.Dimension(1280, 1280));
        resultado.setPreferredSize(new java.awt.Dimension(700, 38));

        calcular.setText("Calcular");
        calcular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calcularActionPerformed(evt);
            }
        });

        borrarResultado.setText("Borrar Res.");
        borrarResultado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                borrarResultadoActionPerformed(evt);
            }
        });

        borrarExpresion.setText("Borrar Expr.");
        borrarExpresion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                borrarExpresionActionPerformed(evt);
            }
        });

        salvarVariables.setText("Salvar Variables");
        salvarVariables.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salvarVariablesActionPerformed(evt);
            }
        });

        cargarVariables.setText("Cargar Variables");
        cargarVariables.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cargarVariablesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addGap(50, 50, 50)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(resultado, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 797, Short.MAX_VALUE)
                            .addComponent(expresion, javax.swing.GroupLayout.DEFAULT_SIZE, 797, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(83, 83, 83)
                                .addComponent(funcionesAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(funcionesConcatenar, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(198, 198, 198))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(51, 51, 51)
                                        .addComponent(historicoAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(historicoConcatenar, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(calcular)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(borrarResultado)
                                        .addGap(2, 2, 2))
                                    .addGroup(layout.createSequentialGroup()
                                        .addContainerGap()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))))
                                .addGap(16, 16, 16)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 505, Short.MAX_VALUE)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 505, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(borrarExpresion)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(salvarVariables)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cargarVariables)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(funcionesConcatenar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(funcionesAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(3, 3, 3)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(historicoConcatenar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(historicoAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(calcular)
                    .addComponent(borrarResultado)
                    .addComponent(borrarExpresion)
                    .addComponent(salvarVariables)
                    .addComponent(cargarVariables))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(expresion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(resultado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void historicoConcatenarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_historicoConcatenarActionPerformed
        expresion.setText(expresion.getText()+historico.getModel().getValueAt(historico.getSelectedRow(), 0).toString().trim());
        expresion.grabFocus();
    }//GEN-LAST:event_historicoConcatenarActionPerformed

    private void borrarExpresionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_borrarExpresionActionPerformed
        expresion.setText("");
        expresion.grabFocus();
}//GEN-LAST:event_borrarExpresionActionPerformed

    private void borrarResultadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_borrarResultadoActionPerformed
        resultado.setText("");
}//GEN-LAST:event_borrarResultadoActionPerformed

   private void expresionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_expresionActionPerformed
     String s=expresion.getText();
     if (s.equalsIgnoreCase("")) {
        return;
     }
     variable r=new variable();
     if (s.charAt(s.length()-1)=='\n') {
        s=s.substring(0,s.length()-1);
     }
     expresion.setText("");
     if (s.length()>0) {
        resultado.setText("");

        this.eval.setNotificacion(false);
        r=eval.programa(s+(char)ctes_eval.FIN);
        this.actualizarLasVariables();
        if (r!=null) {
           resultado.setText(r.StringValue());
           if (r.tipo!='E') { // si no la expresión fue válida
              int n=historico.getRowCount();
              int i;
              for(i=0; i<n; i++) {
                 if (s.equalsIgnoreCase(((DefaultTableModel)historico.getModel()).getValueAt(i, 0).toString())) {
                    ((DefaultTableModel)historico.getModel()).removeRow(i);  
                    break;
                 }
              }
              ((DefaultTableModel)historico.getModel()).addRow(new String[]{s});
              int numItems=historico.getRowCount();
              historico.setRowSelectionInterval(numItems-1,numItems-1);
              historico.scrollRectToVisible(historico.getCellRect(historico.getRowCount()-1, 0, true));
              //historico.select(eval.hist.getItemCount()-1);
              //Rectangle rect=new Rectangle(historico.getVisibleRect());
              //rect.setBounds(rect.x,rect.y+100,rect.width,rect.height+100);
              //historico.scrollRectToVisible(rect);
              //historico.jlista.ensureIndexIsVisible(historico.getItemCount()-1);
           } else {
              if (r.toString().indexOf("exec")<0) {
                 expresion.setText(s);
                 expresion.setSelectionStart(eval.getUltimoToken());
                 expresion.setSelectionEnd(s.length());
              } else {
                 expresion.setText(s);
              }
           }
        }
     }
   }//GEN-LAST:event_expresionActionPerformed
   private boolean ultimo=true;
   private void expresionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_expresionKeyPressed
     if (eval.hist.getItemCount()>0) {
        if (evt.getKeyCode()==KeyEvent.VK_UP) {
           if (historico.getSelectedRow()<=0) {
              historico.setRowSelectionInterval(historico.getRowCount()-1, historico.getRowCount()-1);
              expresion.setText(((DefaultTableModel)historico.getModel()).getValueAt(historico.getRowCount()-1, 0).toString());
           } else if (historico.getSelectedRow()>0) {
              if (!ultimo) {
                 historico.setRowSelectionInterval(historico.getSelectedRow()-1,historico.getSelectedRow()-1);
              } else {
                 ultimo=false;
              }
              expresion.setText(((DefaultTableModel)historico.getModel()).getValueAt(historico.getSelectedRow(), 0).toString());
           } else {
              getToolkit().beep();
           }
        } else if (evt.getKeyCode()==KeyEvent.VK_DOWN) {
           if (historico.getSelectedRow()<historico.getRowCount()-1) {
              if (historico.getSelectedRow()<historico.getRowCount()-1) {
                 historico.setRowSelectionInterval(historico.getSelectedRow()+1,historico.getSelectedRow()+1);
              }
              expresion.setText(((DefaultTableModel)historico.getModel()).getValueAt(historico.getSelectedRow(), 0).toString());
           } else {
              getToolkit().beep();
           }
        } else if (evt.getKeyCode()==KeyEvent.VK_ESCAPE) {
           //historico.select(-1);
           expresion.setText("");
        } else {
           ultimo=true;
        }

     }
      
   }//GEN-LAST:event_expresionKeyPressed

   private void calcularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calcularActionPerformed
      expresionActionPerformed(evt);
   }//GEN-LAST:event_calcularActionPerformed

   private void salvarVariablesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salvarVariablesActionPerformed
        DataOutputStream outStream;
        JFrame frame=new JFrame("Salvar variables en: ");
        JFileChooser fichero=new JFileChooser();

        fichero.setCurrentDirectory(fichero.getCurrentDirectory());
        fichero.setApproveButtonText("Guardar");
        fichero.setDialogTitle("Guardar Variables");
        fichero.showOpenDialog(frame);
        if (fichero.getSelectedFile()!=null) {
           try {
              String ficheroName=fichero.getSelectedFile().getAbsolutePath();
              outStream= new DataOutputStream(new FileOutputStream(ficheroName));
              try {
                 String s;
                 for(int i=0;i<eval.getTablaSimbolos().getNumSimbolos();i++) {
                    s="";
                    s+=eval.getTablaSimbolos().getSimbolo(i).nombre+"="+eval.getTablaSimbolos().getSimbolo(i);
                    //s+=variables.getValueAt(i,0)+"="+variables.getValueAt(i,1);
                    outStream.writeBytes(s+(char) ctes_eval.FIN+"\n");
                 }
                 outStream.close();
              } catch (IOException ex) {
                 mieval.dialogo.cuadro_mensaje("Error escribiendo en "+
                                                              fichero.getSelectedFile().getAbsolutePath(),
                                          "Mensaje de Error");
              }
           } catch (IOException ex) {
              mieval.dialogo.cuadro_mensaje("Error al Abrir Fichero "+
                                                              fichero.getSelectedFile().getAbsolutePath(),
                                       "Mensaje de Error");
           }
        }
   }//GEN-LAST:event_salvarVariablesActionPerformed

   private void cargarVariablesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cargarVariablesActionPerformed
        JFrame frame=new JFrame("Salvar variables en: ");
        JFileChooser fichero=new javax.swing.JFileChooser();

        fichero.setApproveButtonText("Abrir");
        fichero.setDialogTitle("Cargar Variables");
        fichero.setCurrentDirectory(fichero.getCurrentDirectory());
        fichero.showOpenDialog(frame);
        if (fichero.getSelectedFile()!=null) {
           if (!eval.abrirFichero(fichero.getSelectedFile().getAbsolutePath())) {
              mieval.dialogo.cuadro_mensaje("Error al abrir el fichero "+
                                                              fichero.getSelectedFile().getAbsolutePath(),
                                       "Mensaje de Error");
              return;
           }
           String prg=eval.getContenidoFichero(fichero.getSelectedFile().getAbsolutePath());
           variable r=eval.programa(prg);
           if (r.tipo=='E') {
             mieval.dialogo.cuadro_mensaje("Error al cargar variables del fichero: "+
                                           fichero.getSelectedFile().getAbsolutePath()+" : "+r.toString(),
                             "Mensaje de Error");
           }
        }
   }//GEN-LAST:event_cargarVariablesActionPerformed

   private void historicoAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_historicoAddActionPerformed
      expresion.setText(historico.getModel().getValueAt(historico.getSelectedRow(), 0).toString().trim());
      expresion.grabFocus();
   }//GEN-LAST:event_historicoAddActionPerformed

   private void funcionesConcatenarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_funcionesConcatenarActionPerformed
      expresion.setText(expresion.getText() + funciones.getModel().getValueAt(funciones.getSelectedRow(), 0).toString().trim());
      expresion.grabFocus();
   }//GEN-LAST:event_funcionesConcatenarActionPerformed

   private void funcionesAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_funcionesAddActionPerformed
      expresion.setText(funciones.getModel().getValueAt(funciones.getSelectedRow(), 0).toString().trim());
      expresion.grabFocus();
   }//GEN-LAST:event_funcionesAddActionPerformed

   private void actualizarLasVariables() {
      this.eval.setNotificacion(true);
         int j = eval.getNumSimbolos();
         variable v;
         for (int i = 0; i < j; i++) {
            v = eval.getSimbolo(i);
            nuevaVariable(v.nombre, v.StringValue());
         }
   }    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton borrarExpresion;
    private javax.swing.JButton borrarResultado;
    private javax.swing.JButton calcular;
    private javax.swing.JButton cargarVariables;
    private javax.swing.JTextField expresion;
    private javax.swing.JTable funciones;
    private javax.swing.JButton funcionesAdd;
    private javax.swing.JButton funcionesConcatenar;
    private javax.swing.JTable historico;
    private javax.swing.JButton historicoAdd;
    private javax.swing.JButton historicoConcatenar;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextField resultado;
    private javax.swing.JTable salidaProgramas;
    private javax.swing.JButton salvarVariables;
    private javax.swing.JTable variables;
    // End of variables declaration//GEN-END:variables
    mieval.evaluador eval=new mieval.evaluador(this,new lectorMatriz());
    public void expresionSetFocus() {
       expresion.grabFocus();
       //expresion.requestFocus();
       //expresion.requestFocus(false);
       //expresion.requestFocusInWindow();
       /*MouseEvent me = new MouseEvent(expresion, // which
       MouseEvent.MOUSE_CLICKED, // what
       System.currentTimeMillis(), // when
       0, // no modifiers
       10, 10, // where: at (10, 10}
       1, // only 1 click 
       false); // not a popup trigger

       expresion.dispatchEvent(me);*/

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
        JFrame j=new JFrame();
        panelCalc pC=new panelCalc();
        j.setTitle("Calculadora Científica/Programable (c) 1999-2011 F.J.Criado");
        j.getContentPane().add(pC, java.awt.BorderLayout.CENTER);
        j.add(pC);
        j.setSize(pC.getPreferredSize());
        j.addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent e) {
            System.exit(0);
         }
        });
        j.setVisible(true);
   }
   public void clear() {
      DefaultTableModel model = (DefaultTableModel) this.salidaProgramas.getModel();
      while( model.getRowCount() > 0 ){
          model.removeRow(0);
      }
   }
   public void imprime(String s) {
       ((DefaultTableModel)salidaProgramas.getModel()).addRow(new String[]{s});
       salidaProgramas.setRowSelectionInterval(salidaProgramas.getRowCount()-1, salidaProgramas.getRowCount()-1);
       salidaProgramas.scrollRectToVisible(salidaProgramas.getCellRect(salidaProgramas.getRowCount()-1, 0, true));
       //salidaProgramas.select(salidaProgramas.getItemCount()-1);
       //salidaProgramas.jlista.ensureIndexIsVisible(salidaProgramas.getItemCount()-1);
   }

   public void imprimec(String color, String s) {
      Color c=new Color(0,0,0);
     
     if (color.equalsIgnoreCase("rojo")) {
         c=new Color(255,0,0);
         //System.out.println("color rojo");
     } else if (color.equalsIgnoreCase("verde")) {
         c=new Color(0,255,0);
     } else if (color.equalsIgnoreCase("azul")) {
         c=new Color(0,0,255);
     }
     //System.out.println(c);
     this.salidaProgramas.setForeground(c);
     this.imprime(s);
     //this.salidaProgramas.jlista.setForeground(new Color(0,0,0));
   }
   /*********************************************************************
    * para notificación de las variables
    */
   private boolean variableEncontrada=false;
   public int busca(String s) {
      int i=0,j=variables.getRowCount()-1,k=0;
      String is;
      int cmp=0;

      while (i<=j) {
         //System.out.println("i: "+i+", j: "+j+" variables row count"+variables.getRowCount()+" model row count: "+((DefaultTableModel)variables.getModel()).getRowCount());
         k=(i+j) / 2;
         is=variables.getValueAt(k, 0).toString();
         cmp=s.compareToIgnoreCase(is);
         if (cmp<0) {
            j=k-1;
         } else if (cmp>0) {
            i=k+1;
         } else {
            variableEncontrada=true;
            return k;
         }
      }
      if (i>j) {
         return i;
      } else {
         return k;
      }
   }
   public void nuevaVariable(String nombre, String valor) {
     int  i;
     DefaultTableModel tm;
     String as[];

     i=busca(nombre);
     if (variableEncontrada) {
        variables.setValueAt(valor, i, 1);
        variableEncontrada=false;
     } else {
        tm=(DefaultTableModel)variables.getModel();
        as=new String[]{nombre,valor};
        tm.insertRow(i, as);
        //((DefaultTableModel)variables.getModel()).insertRow(i, new String[]{nombre,valor});
     }
     if (variables.getSelectedRow()>=0) {
        variables.scrollRectToVisible(variables.getCellRect(variables.getSelectedRow(), 0, true));
     } else {
        variables.scrollRectToVisible(variables.getCellRect(i, 0, true));
     }
   }
   java.util.Vector<DefaultTableModel> internalList=new java.util.Vector<DefaultTableModel>();
   public void nuevoEntorno() {
      internalList.addElement((DefaultTableModel)variables.getModel());
      variables.setModel(new DefaultTableModel(new String[]{"Variable","valor"},0));
      ((DefaultTableModel)variables.getModel()).fireTableDataChanged();
   }

   public void finNuevoEntorno() {
     variables.setModel((DefaultTableModel) internalList.elementAt(internalList.size()-1));
     ((DefaultTableModel)variables.getModel()).fireTableDataChanged();
     internalList.removeElementAt(internalList.size()-1);
   }
}
