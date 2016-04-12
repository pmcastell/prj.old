
//Title:        Your Product Name
//Version:
//Copyright:    Copyright (c) 1998
//Author:       Your Name
//Company:      Your Company
//Description:  Your description



import java.awt.*;
import java.awt.event.*;

public class Frame1 extends Frame {
  MenuBar menCuBar1 = new MenuBar();
  Menu carrera = new Menu();
  Menu ayuda = new Menu();
  Menu salir = new Menu();
  MenuItem ingSup = new MenuItem();
  MenuItem itSis = new MenuItem();
  MenuItem itGest = new MenuItem();
  MenuItem manUsu = new MenuItem();
  Label label1 = new Label();
  static II ingInf= new II();
  static ITIG itG= new ITIG();
  static ITIS itS= new ITIS();


  public Frame1() {
    try  {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
  public void main2() {
     main(null);
  }   

  public static void main(String[] args) {
    Frame1 f = new Frame1();
    f.setTitle("Facultad de Informática");
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    f.setLocation((d.width - f.getSize().width) / 2, (d.height - f.getSize().height) / 2);
    f.addWindowListener(new WindowAdapter() {
       public void windowClosing(WindowEvent e) {
          System.exit(0);
       }
    });
    ingInf.matricula("Francisco Javier Criado Navarro", 5);
    ingInf.matricula("Bill Gates", 1);
    ingInf.matricula("Alfredo Fuentes Pérez", 5);
    ingInf.matricula("Carlos Bellido Cuéllar", 5);
    ingInf.matricula("Tomás Bueno Géntil", 5);
    itG.matricula("Pedro Pérez Pérez", 1);
    itG.matricula("Antonio López López", 2);
    itG.matricula("Luis González González", 3);
    itS.matricula("Luisa Fernández González", 1);
    itS.matricula("Marisa Paredes López", 2);
    itS.matricula("Victoria Abril Mayo", 3);
    f.setVisible(true);

  }

  private void jbInit() throws Exception {
    this.setFont(new Font("Helvetica", 0, 25));
    this.setBackground(new Color(44, 109, 192));
    this.setSize(new Dimension(666, 348));
    this.setTitle("Calificaciones");
    carrera.setLabel("Carrera");
    ayuda.setLabel("Ayuda");
    ayuda.setShortcut(new MenuShortcut(65));
    salir.setLabel("Salir");
      salir.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(ActionEvent e) {
            salir_actionPerformed(e);
         }
      });
    label1.setForeground(new Color(0, 60, 0));
    label1.setAlignment(1);
    label1.setText("Busqueda de Calificaciones en la Facultad de Informática");
    ingSup.setLabel("Ingenieria Informatica");
    ingSup.setShortcut(new MenuShortcut(73));
    ingSup.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        ingSup_actionPerformed(e);
      }
    });
    itSis.setLabel("Ingenieria Tecnica de Sistemas");
    itSis.setShortcut(new MenuShortcut(83));
      itSis.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(ActionEvent e) {
            itSis_actionPerformed(e);
         }
      });
    itGest.setLabel("Ingenieria Tecnica de Gestion");
    itGest.setShortcut(new MenuShortcut(71));
      itGest.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(ActionEvent e) {
            itGest_actionPerformed(e);
         }
      });
    manUsu.setLabel("Manual de Usuario");
    manUsu.setShortcut(new MenuShortcut(77));
      manUsu.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(ActionEvent e) {
            manUsu_actionPerformed(e);
         }
      });
    this.setMenuBar(menCuBar1);
    menCuBar1.add(carrera);
    menCuBar1.add(ayuda);
    menCuBar1.add(salir);
    carrera.add(ingSup);
    carrera.add(itSis);
    carrera.add(itGest);
    ayuda.add(manUsu);
    this.add(label1, BorderLayout.NORTH);
  }

  void ingSup_actionPerformed(ActionEvent e) {
    dialogCarrera f=new dialogCarrera("Ingeniería Informática", this.ingInf);
    f.actualiza();
    f.setVisible(true);
  }

  void itSis_actionPerformed(ActionEvent e) {
    dialogCarrera f=new dialogCarrera("Ingeniería Técnica de Sistemas", this.itS);
    f.actualiza();
    f.setVisible(true);
  }

   void itGest_actionPerformed(ActionEvent e) {
    dialogCarrera f=new dialogCarrera("Ingeniería Técnica de Gestión", this.itG);
    f.actualiza();
    f.setVisible(true);
   }

   void salir_actionPerformed(ActionEvent e) {
      System.exit(0);
   }

   void manUsu_actionPerformed(ActionEvent e) {
      ventanaDialogo.vMensaje("Búsqueda de Calificaciones (c) 2000 F.J.Criado");
   }


}


