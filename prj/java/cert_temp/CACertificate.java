import java.awt.*;
import com.sun.java.swing.*;
import java.awt.event.*;
import java.io.*;






import java.text.*;
import java.security.*;
import java.security.cert.*;

import iaik.pkcs.*;
import iaik.asn1.*;
import iaik.asn1.structures.*;
import iaik.utils.*;

public class CACertificate extends JFrame {
  JButton datosDeUsuario = new JButton();
  JButton generarParDeClavesDeUsuario = new JButton();
  JButton crearUnCertificadoDeUsuario = new JButton();
  JButton importarCertificado = new JButton();
  JButton importarPkcs1 = new JButton();
  JButton importarPkcs8 = new JButton();
  JButton importarPkcs10 = new JButton();
  JButton importarPkcs12 = new JButton();
  JButton exportarUnCertificado = new JButton();
  JButton exportarPkcs1 = new JButton();
  JButton exportarPkcs8 = new JButton();
  JButton exportarPkcs10 = new JButton();
  JButton exportarPkcs12 = new JButton();
  JButton borrarUltimoCertificado = new JButton();
  JButton verificarCertificado = new JButton();
  JButton utilizarUsuarioComoCA = new JButton();
  JButton utilizarCAComoUsuario = new JButton();
  JButton salir = new JButton();
  JButton nuevo = new JButton();
  Authority autoridad=new Authority();
  JLabel jLabel1 = new JLabel();
  JLabel jLabel2 = new JLabel();
  private Thread current;
  JScrollPane jScrollPane1 = new JScrollPane();
  JTextArea datosCa = new JTextArea();
  JScrollPane jScrollPane2 = new JScrollPane();
  JTextArea datosUsuario = new JTextArea();
  static String aceptar[]={"Aceptar"},
         aceptarCancelar[] = {"Aceptar","Cancelar"};
      

  /**
  * Habilita-deshabilita los botones según deban estar o no disponibles
  */
  void actualizarEstadoBotones() {
     //datosDeUsuario.enable(true);
     generarParDeClavesDeUsuario.setEnabled(autoridad.userName!=null);
     crearUnCertificadoDeUsuario.setEnabled((autoridad.userName!=null &&
                                             autoridad.userPrivateKey!=null &&
                                             autoridad.userPublicKey!=null) ||
                                            (autoridad.userName!=null &&
                                             autoridad.userPublicKey!=null &&
                                             autoridad.caName!=null &&
                                             autoridad.caPrivateKey!=null &&
                                             autoridad.caPublicKey!=null));
     importarCertificado.setEnabled(true);
     importarPkcs1.setEnabled(true);
     importarPkcs8.setEnabled(true);
     importarPkcs10.setEnabled(true);
     importarPkcs12.setEnabled(true);
     exportarUnCertificado.setEnabled(autoridad.userChain!=null);
     exportarPkcs1.setEnabled(autoridad.userPrivateKey!=null);
     exportarPkcs8.setEnabled(autoridad.userPrivateKey!=null);
     exportarPkcs10.setEnabled(autoridad.userName!=null &&
                               autoridad.userPrivateKey!=null &&
                               autoridad.userPublicKey!=null);
     exportarPkcs12.setEnabled(autoridad.userName!=null);
     borrarUltimoCertificado.setEnabled(autoridad.userChain!=null);
     verificarCertificado.setEnabled(autoridad.userChain!=null);
     utilizarUsuarioComoCA.setEnabled(autoridad.userName!=null &&
                                      autoridad.userPrivateKey!=null &&
                                      autoridad.userPublicKey!=null);
     utilizarCAComoUsuario.setEnabled(autoridad.caName!=null &&
                                      autoridad.caPrivateKey !=null &&
                                      autoridad.caPublicKey !=null);
     //nuevo.setEnabled(true);
  }


  /**
  * Muestra en TextArea los datos de usuario
  */
  void actualizarDatos()
  {
     String texto=pkcsUtil.rdnToString(autoridad.caName);
     texto+="                         Clave: ";
     if (autoridad.caPublicKey!=null) {
        texto+=(autoridad.caPublicKey.getAlgorithm ()+"\n");
     } else {
        texto+="\n";
     }
     texto+=("                      Firmas: "+"\n");
     if (autoridad.caChain!=null) {
       for (int i=0;i<autoridad.caChain.length;i++) {
         texto+= ("    "+((Name)autoridad.caChain[i].getIssuerDN ()).getRDN (ObjectID.commonName)+" "+Util.toString (autoridad.caChain[i].getFingerprint ())+"\n");
       }
     } else {
        texto+="\n";
     }
     datosCa.setText(texto);
     texto=pkcsUtil.rdnToString(autoridad.userName);
     texto+="                         Clave: ";
     if (autoridad.userPublicKey!=null) {
        texto+=(autoridad.userPublicKey.getAlgorithm ()+"\n");
     } else {
        texto+="\n";
     }
     texto+=("                      Firmas: "+"\n");
     if (autoridad.userChain!=null) {
       for (int i=0;i<autoridad.userChain.length;i++) {
         texto+= ("    "+((Name)autoridad.userChain[i].getIssuerDN ()).getRDN (ObjectID.commonName)+" "+Util.toString (autoridad.userChain[i].getFingerprint ())+"\n");
       }
     } else {
        texto+="\n";
     }
     datosUsuario.setText(texto);
  }







  public CACertificate() {
    try  {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    CACertificate CACertificate1 = new CACertificate();
    CACertificate1.setSize(610,420);
    CACertificate1.setTitle("Gestión de Certificados");
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    CACertificate1.setLocation((d.width - CACertificate1.getSize().width) / 2,
                             (d.height - CACertificate1.getSize().height) / 2);
    CACertificate1.setResizable(false);
    CACertificate1.setVisible(true);
//    CACertificate1.datosUsuario.setText(CACertificate1.rdnToString(CACertificate1.autoridad.userName));
    CACertificate1.actualizarDatos();
    UserInput ui=new UserInput();
    try {
       CACertificate1.setVisible(true);
    } catch (Exception ex) {
       String l[]={"Ha ocurrido el siguiente error: ",""};
       new UserInput().showDialog(l,ex.toString());
    }

  }

  private void jbInit() throws Exception {
     this.addWindowListener(new java.awt.event.WindowAdapter() {
       public void windowClosing(WindowEvent e) {
         this_windowClosing(e);
       }
     });
     datosDeUsuario.setMaximumSize(new Dimension(140, 20));
     datosDeUsuario.setText("Datos de Usuario");
     datosDeUsuario.setPreferredSize(new Dimension(140, 20));
     datosDeUsuario.setFont(new Font("Dialog", 1, 10));
     datosDeUsuario.setMinimumSize(new Dimension(140, 20));
     datosDeUsuario.setBounds(new Rectangle(8, 8, 205, 20));
     datosDeUsuario.addActionListener(new java.awt.event.ActionListener() {
       public void actionPerformed(ActionEvent e) {
         datosDeUsuario_actionPerformed(e);
       }
     });
     generarParDeClavesDeUsuario.setBounds(new Rectangle(8, 28, 205, 20));
     generarParDeClavesDeUsuario.addActionListener(new java.awt.event.ActionListener() {
       public void actionPerformed(ActionEvent e) {
         generarParDeClavesDeUsuario_actionPerformed(e);
       }
     });
     crearUnCertificadoDeUsuario.setEnabled(false);
     crearUnCertificadoDeUsuario.setMaximumSize(new Dimension(140, 20));
     crearUnCertificadoDeUsuario.setText("Crear Un Certificado de Usuario");
     crearUnCertificadoDeUsuario.setPreferredSize(new Dimension(140, 20));
     crearUnCertificadoDeUsuario.setFont(new Font("Dialog", 1, 10));
     crearUnCertificadoDeUsuario.setMinimumSize(new Dimension(140, 20));
     crearUnCertificadoDeUsuario.setBounds(new Rectangle(8, 48, 205, 20));
     crearUnCertificadoDeUsuario.addActionListener(new java.awt.event.ActionListener() {
       public void actionPerformed(ActionEvent e) {
         crearUnCertificadoDeUsuario_actionPerformed(e);
       }
     });
     importarCertificado.setBounds(new Rectangle(8, 68, 205, 20));
     importarCertificado.addActionListener(new java.awt.event.ActionListener() {
       public void actionPerformed(ActionEvent e) {
         importarCertificado_actionPerformed(e);
       }
     });
     importarPkcs1.setMaximumSize(new Dimension(140, 20));
     importarPkcs1.setText("Importar PKCS#1");
     importarPkcs1.setPreferredSize(new Dimension(140, 20));
     importarPkcs1.setFont(new Font("Dialog", 1, 10));
     importarPkcs1.setMinimumSize(new Dimension(140, 20));
     importarPkcs1.setBounds(new Rectangle(8, 88, 205, 20));
     importarPkcs1.addActionListener(new java.awt.event.ActionListener() {
       public void actionPerformed(ActionEvent e) {
         importarPkcs1_actionPerformed(e);
       }
     });
     importarPkcs8.setBounds(new Rectangle(8, 108, 205, 20));
     importarPkcs8.addActionListener(new java.awt.event.ActionListener() {
       public void actionPerformed(ActionEvent e) {
         importarPkcs8_actionPerformed(e);
       }
     });
     importarPkcs10.setMaximumSize(new Dimension(140, 20));
     importarPkcs10.setText("Importar PKCS#10");
     importarPkcs10.setPreferredSize(new Dimension(140, 20));
     importarPkcs10.setFont(new Font("Dialog", 1, 10));
     importarPkcs10.setMinimumSize(new Dimension(140, 20));
     importarPkcs10.setBounds(new Rectangle(8, 128, 205, 20));
     importarPkcs10.addActionListener(new java.awt.event.ActionListener() {
       public void actionPerformed(ActionEvent e) {
         importarPkcs10_actionPerformed(e);
       }
     });
     importarPkcs12.setMaximumSize(new Dimension(140, 20));
     importarPkcs12.setText("Importar PKCS#12");
     importarPkcs12.setPreferredSize(new Dimension(140, 20));
     importarPkcs12.setFont(new Font("Dialog", 1, 10));
     importarPkcs12.setMinimumSize(new Dimension(140, 20));
     importarPkcs12.setBounds(new Rectangle(8, 148, 205, 20));
     importarPkcs12.addActionListener(new java.awt.event.ActionListener() {
       public void actionPerformed(ActionEvent e) {
         importarPkcs12_actionPerformed(e);
       }
     });
     exportarUnCertificado.setEnabled(false);
     exportarUnCertificado.setMaximumSize(new Dimension(140, 20));
     exportarUnCertificado.setText("Exportar un Certificado");
     exportarUnCertificado.setPreferredSize(new Dimension(140, 20));
     exportarUnCertificado.setFont(new Font("Dialog", 1, 10));
     exportarUnCertificado.setMinimumSize(new Dimension(140, 20));
     exportarUnCertificado.setBounds(new Rectangle(8, 168, 205, 20));
     exportarUnCertificado.addActionListener(new java.awt.event.ActionListener() {
       public void actionPerformed(ActionEvent e) {
         exportarUnCertificado_actionPerformed(e);
       }
     });
     exportarPkcs1.setEnabled(false);
     exportarPkcs1.setMaximumSize(new Dimension(140, 20));
     exportarPkcs1.setText("Exportar PKCS#1");
     exportarPkcs1.setPreferredSize(new Dimension(140, 20));
     exportarPkcs1.setFont(new Font("Dialog", 1, 10));
     exportarPkcs1.setMinimumSize(new Dimension(140, 20));
     exportarPkcs1.setBounds(new Rectangle(8, 188, 205, 20));
     exportarPkcs1.addActionListener(new java.awt.event.ActionListener() {
       public void actionPerformed(ActionEvent e) {
         exportarPkcs1_actionPerformed(e);
       }
     });
     exportarPkcs8.setBounds(new Rectangle(8, 208, 205, 20));
     exportarPkcs8.addActionListener(new java.awt.event.ActionListener() {
       public void actionPerformed(ActionEvent e) {
         exportarPkcs8_actionPerformed(e);
       }
     });

     exportarPkcs10.setEnabled(false);
     exportarPkcs10.setMaximumSize(new Dimension(140, 20));
     exportarPkcs10.setText("Exportar PKCS#10");
     exportarPkcs10.setPreferredSize(new Dimension(140, 20));
     exportarPkcs10.setFont(new Font("Dialog", 1, 10));
     exportarPkcs10.setMinimumSize(new Dimension(140, 20));
     exportarPkcs10.setBounds(new Rectangle(8, 228, 205, 20));
     exportarPkcs12.addActionListener(new java.awt.event.ActionListener() {
       public void actionPerformed(ActionEvent e) {
         exportarPkcs12_actionPerformed(e);
       }
     });
     exportarPkcs12.setBounds(new Rectangle(8, 248, 205, 20));
     borrarUltimoCertificado.setEnabled(false);
     borrarUltimoCertificado.setMaximumSize(new Dimension(140, 20));
     borrarUltimoCertificado.setText("Borrar Ultimo Certificado");
     borrarUltimoCertificado.setPreferredSize(new Dimension(140, 20));
     borrarUltimoCertificado.setFont(new Font("Dialog", 1, 10));
     borrarUltimoCertificado.setMinimumSize(new Dimension(140, 20));
     borrarUltimoCertificado.setBounds(new Rectangle(8, 268, 205, 20));
     verificarCertificado.addActionListener(new java.awt.event.ActionListener() {
       public void actionPerformed(ActionEvent e) {
         verificarCertificado_actionPerformed(e);
       }
     });
     verificarCertificado.setEnabled(false);
     verificarCertificado.setMaximumSize(new Dimension(140, 20));
     verificarCertificado.setText("Verificar Certificado");
     verificarCertificado.setPreferredSize(new Dimension(140, 20));
     verificarCertificado.setFont(new Font("Dialog", 1, 10));
     verificarCertificado.setMinimumSize(new Dimension(140, 20));
     verificarCertificado.setBounds(new Rectangle(8, 288, 205, 20));
     utilizarUsuarioComoCA.setEnabled(false);
     utilizarUsuarioComoCA.setMaximumSize(new Dimension(140, 20));
     utilizarUsuarioComoCA.setText("Utilizar Usuario como CA");
     utilizarUsuarioComoCA.setPreferredSize(new Dimension(140, 20));
     utilizarUsuarioComoCA.setFont(new Font("Dialog", 1, 10));
     utilizarUsuarioComoCA.setMinimumSize(new Dimension(140, 20));
     utilizarUsuarioComoCA.setBounds(new Rectangle(8, 308, 205, 20));
     utilizarCAComoUsuario.addActionListener(new java.awt.event.ActionListener() {
       public void actionPerformed(ActionEvent e) {
         utilizarCAComoUsuario_actionPerformed(e);
       }
     });
     utilizarCAComoUsuario.setBounds(new Rectangle(8, 328, 205, 20));
     salir.addActionListener(new java.awt.event.ActionListener() {
       public void actionPerformed(ActionEvent e) {
         salir_actionPerformed(e);
       }
     });
     salir.setBounds(new Rectangle(8, 368, 205, 20));
     jLabel1.setText("Datos de Usuario:");
     jLabel1.setHorizontalAlignment(0);
     jLabel1.setFont(new Font("Dialog", 1, 11));
     jLabel1.setBounds(new Rectangle(222, 195, 355, 21));
     jLabel2.setBounds(new Rectangle(223, 3, 355, 21));
  jScrollPane1.setBounds(new Rectangle(224, 21, 358, 165));
  datosCa.setFont(new Font("Dialog", 1, 12));
  datosCa.setEditable(false);
  jScrollPane2.setBounds(new Rectangle(224, 214, 358, 170));
  datosUsuario.setEditable(false);
  datosUsuario.setFont(new Font("Dialog", 1, 12));
  datosUsuario.setText("");
  datosCa.setText("");
  datosCa.setFont(new Font("Dialog", 1, 12));
     nuevo.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
           nuevo_actionPerformed(e);
        }
     });
     nuevo.setBounds(new Rectangle(8, 348, 205, 20));
     nuevo.setMaximumSize(new Dimension(140, 20));
     nuevo.setText("Nuevo");
     nuevo.setPreferredSize(new Dimension(140, 20));
     nuevo.setFont(new Font("Dialog", 1, 10));
     nuevo.setMinimumSize(new Dimension(140, 20));
     jLabel2.setFont(new Font("Dialog", 1, 11));
     jLabel2.setHorizontalAlignment(0);
     jLabel2.setText("Datos de la C.A.:");


     salir.setMaximumSize(new Dimension(140, 20));
     salir.setText("Salir");
     salir.setPreferredSize(new Dimension(140, 20));
     salir.setFont(new Font("Dialog", 1, 10));
     salir.setMinimumSize(new Dimension(140, 20));
     utilizarCAComoUsuario.setEnabled(false);
     utilizarCAComoUsuario.setMaximumSize(new Dimension(140, 20));
     utilizarCAComoUsuario.setText("Utilizar CA como Usuario");
     utilizarCAComoUsuario.setPreferredSize(new Dimension(140, 20));
     utilizarCAComoUsuario.setFont(new Font("Dialog", 1, 10));
     utilizarCAComoUsuario.setMinimumSize(new Dimension(140, 20));
     utilizarUsuarioComoCA.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
            utilizarUsuarioComoCA_actionPerformed(e);
        }
     });
     borrarUltimoCertificado.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
           borrarUltimoCertificado_actionPerformed(e);
        }
     });
     exportarPkcs12.setEnabled(false);
  exportarPkcs12.setMaximumSize(new Dimension(140, 20));
     exportarPkcs12.setText("Exportar PKCS#12");
     exportarPkcs12.setPreferredSize(new Dimension(140, 20));
     exportarPkcs12.setFont(new Font("Dialog", 1, 10));
     exportarPkcs12.setMinimumSize(new Dimension(140, 20));
     exportarPkcs10.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(ActionEvent e) {
           exportarPkcs10_actionPerformed(e);
        }
     });
     exportarPkcs8.setEnabled(false);
     exportarPkcs8.setMaximumSize(new Dimension(140, 20));
     exportarPkcs8.setText("Exportar PKCS#8");
     exportarPkcs8.setPreferredSize(new Dimension(140, 20));
     exportarPkcs8.setFont(new Font("Dialog", 1, 10));
     exportarPkcs8.setMinimumSize(new Dimension(140, 20));
     importarPkcs8.setMaximumSize(new Dimension(140, 20));
     importarPkcs8.setText("Importar PKCS#8");
     importarPkcs8.setPreferredSize(new Dimension(140, 20));
     importarPkcs8.setFont(new Font("Dialog", 1, 10));
     importarPkcs8.setMinimumSize(new Dimension(140, 20));
     importarCertificado.setMaximumSize(new Dimension(140, 20));
     importarCertificado.setText("Importar Certificado");
     importarCertificado.setPreferredSize(new Dimension(140, 20));
     importarCertificado.setFont(new Font("Dialog", 1, 10));
     importarCertificado.setMinimumSize(new Dimension(140, 20));
     generarParDeClavesDeUsuario.setEnabled(false);
     generarParDeClavesDeUsuario.setMaximumSize(new Dimension(140, 20));
     generarParDeClavesDeUsuario.setText("Generar Par de Claves de Usuario");
     generarParDeClavesDeUsuario.setPreferredSize(new Dimension(140, 20));
     generarParDeClavesDeUsuario.setFont(new Font("Dialog", 1, 10));
     generarParDeClavesDeUsuario.setMinimumSize(new Dimension(140, 20));
     this.getContentPane().setLayout(null);
     this.getContentPane().add(datosDeUsuario, null);
     this.getContentPane().add(generarParDeClavesDeUsuario, null);
     this.getContentPane().add(crearUnCertificadoDeUsuario, null);
     this.getContentPane().add(importarCertificado, null);
     this.getContentPane().add(importarPkcs1, null);
     this.getContentPane().add(importarPkcs8, null);
     this.getContentPane().add(importarPkcs10, null);
     this.getContentPane().add(importarPkcs12, null);
     this.getContentPane().add(exportarUnCertificado, null);
     this.getContentPane().add(exportarPkcs1, null);
     this.getContentPane().add(exportarPkcs8, null);
     this.getContentPane().add(exportarPkcs10, null);
     this.getContentPane().add(exportarPkcs12, null);
     this.getContentPane().add(borrarUltimoCertificado, null);
     this.getContentPane().add(verificarCertificado, null);
     this.getContentPane().add(utilizarUsuarioComoCA, null);
     this.getContentPane().add(utilizarCAComoUsuario, null);
     this.getContentPane().add(salir, null);
  this.getContentPane().add(jLabel1, null);
     this.getContentPane().add(jLabel2, null);
     this.getContentPane().add(nuevo, null);
  this.getContentPane().add(jScrollPane1, null);
  jScrollPane1.getViewport().add(datosCa, null);
  this.getContentPane().add(jScrollPane2, null);
  jScrollPane2.getViewport().add(datosUsuario, null);
  jScrollPane1.getViewport().add(datosCa, null);

  }

  void datosDeUsuario_actionPerformed(ActionEvent e) {
     if (autoridad.userName==null) {
        autoridad.userName=new Name();
     }
     pkcsUtil.subjectUser(autoridad.userName);
     actualizarDatos();
     actualizarEstadoBotones();
  }

  void exportarPkcs10_actionPerformed(ActionEvent e) {
     if (autoridad.userName==null || autoridad.userPublicKey==null ||
        autoridad.userPrivateKey==null) {
        new UserInput().showDialog ("Es necesario tener los datos de usuario junto con su clave publica\ny privada para firmar.",aceptar);
     } else {
        pkcsUtil.escribirPKCS10(autoridad);
        actualizarDatos();
        actualizarEstadoBotones();
     }
  }

  void exportarPkcs12_actionPerformed(ActionEvent e) {
     pkcsUtil.escribirPKCS12(autoridad);
     actualizarDatos();
     actualizarEstadoBotones();
  }
  void  generarParDeClavesDeUsuario_actionPerformed(ActionEvent e) {
     String cancelar[]={"Cancelar"};
     String aceptar[]={"Aceptar"};
     String mensaje="Generando Par de Claves usuario. Puede Abortar el\n proceso pulsando Cancelar\n";
     Thread curThr= Thread.currentThread();
     UserInput ui= new UserInput();
     parClavesUsuario k=new parClavesUsuario(autoridad, ui);


     if (k.obtenerParametros()) {
        k.start();
        ui.showDialog(mensaje, cancelar, true);
        actualizarDatos();
        if (k.getTerminado()) {
           ui.showDialog("Par de Claves Generado", aceptar);
        } else {
           k.stop();
           autoridad.userPrivateKey=null;
           autoridad.userPublicKey=null;
        }
     }
     actualizarEstadoBotones();
  }

  void crearUnCertificadoDeUsuario_actionPerformed(ActionEvent e) {
     pkcsUtil.nuevoCertificado(autoridad);
     actualizarDatos();
     actualizarEstadoBotones();
  }


  void importarCertificado_actionPerformed(ActionEvent e) {
     pkcsUtil.leerCertificado(autoridad);
     actualizarDatos();
     actualizarEstadoBotones();
  }

  void importarPkcs1_actionPerformed(ActionEvent e) {
     pkcsUtil.leerPKCS1(autoridad);
     actualizarDatos();
     actualizarEstadoBotones();
  }

  void importarPkcs8_actionPerformed(ActionEvent e) {
     pkcsUtil.leerPKCS8(autoridad);
     actualizarDatos();
     actualizarEstadoBotones();
  }

  void importarPkcs10_actionPerformed(ActionEvent e) {
     pkcsUtil.leerPKCS10(autoridad);
     actualizarDatos();
     actualizarEstadoBotones();
  }

  void importarPkcs12_actionPerformed(ActionEvent e) {
     pkcsUtil.leerPKCS12(autoridad);
     actualizarDatos();
     actualizarEstadoBotones();
  }

  void exportarUnCertificado_actionPerformed(ActionEvent e) {
     if (autoridad.userChain==null) {
        new UserInput().showDialog ("Es necesario tener un certificado del usuario.",aceptar);
     } else {
        pkcsUtil.escribirCert(autoridad);
        actualizarDatos();
        actualizarEstadoBotones();
     }
  }
  
  void exportarPkcs1_actionPerformed(ActionEvent e) {
     if (autoridad.userPrivateKey==null) {
        new UserInput().showDialog ("Es necesario tener la clave privada del usuario.",aceptar);
     } else {
	     pkcsUtil.escribirPKCS1 (autoridad);
        actualizarDatos();
        actualizarEstadoBotones();
     }
  }


  void exportarPkcs8_actionPerformed(ActionEvent e) {
     if (autoridad.userPrivateKey==null) {
        new UserInput().showDialog ("Es necesario tener la clave privada del usuario.",aceptar);
     } else {
	     pkcsUtil.escribirPKCS8(autoridad);
        actualizarDatos();
        actualizarEstadoBotones();
     }
  }
  void verificarCertificado_actionPerformed(ActionEvent e) {
	  if (autoridad.userChain==null) {
	    new UserInput().showDialog ("No hay una cadena de certificados para el usuario.",aceptar);
	  } else if (autoridad.verifyCertificateChain ()) {
        if (autoridad.isTrusted ()) {
           new UserInput().showDialog ("La cadena de certificados para el usuario es correcta\ny la autoridad de certificacion es conocida y valida.",aceptar);
        } else {
           new UserInput().showDialog ("La cadena de certificados para el usuario es correcta\naunque la autoridad de certificacion es desconocida.",aceptar);
        }
     }
     actualizarDatos();
     actualizarEstadoBotones();
  }

  void utilizarUsuarioComoCA_actionPerformed(ActionEvent e) {
	  if (autoridad.userName==null || autoridad.userPublicKey==null ||
         autoridad.userPrivateKey==null) {
 	     new UserInput().showDialog ("Es necesario tener los datos de usuario\njunto con las claves publica y privada",aceptar);
	  } else {
        autoridad.userToCa ();
     }
     actualizarDatos();
     actualizarEstadoBotones();
  }
  void utilizarCAComoUsuario_actionPerformed(ActionEvent e) {
     if (autoridad.caName==null || autoridad.caPublicKey==null ||
         autoridad.caPrivateKey==null) {
        new UserInput().showDialog ("Es necesario tener los datos de la CA\njunto con las claves publica y privada",aceptar);
     } else {
        autoridad.caToUser ();
     }
     actualizarDatos();
     actualizarEstadoBotones();   

  }

  void borrarUltimoCertificado_actionPerformed(ActionEvent e) {
     if (autoridad.userChain==null) {
        new UserInput().showDialog ("No hay una cadena de certificados para el usuario.",aceptar);
     } else if (autoridad.userChain.length==1) {
        autoridad.userChain=null;
     } else {
        autoridad.deleteCertificate ();
     }
     actualizarDatos();
     actualizarEstadoBotones();
  }
  void nuevo_actionPerformed(ActionEvent e) {
     autoridad=new Authority();
     actualizarDatos();
//     datosUsuario.setText(rdnToString(autoridad.userName));
     actualizarEstadoBotones();

  }
  void salir_actionPerformed(ActionEvent e) {

    System.exit(0);
  }

  void this_windowClosing(WindowEvent e) {
    System.exit(0);

  }
}





/*
  void actualizarDatos()
  {

      StringBuffer text;
      int value,i;
      UserInput userInput=new UserInput ();
       text=new StringBuffer ("Autoridad de certificacion.\n");
      if (autoridad.caName!=null)
      {
        text.append ("  Pais: ");
    text.append (autoridad.caName.getRDN (ObjectID.country)+"\n");
        text.append ("  Estado o Provincia: ");
    text.append (autoridad.caName.getRDN (ObjectID.stateOrProvince)+"\n");
    text.append ("  Organizacion: ");
    text.append (autoridad.caName.getRDN (ObjectID.organization)+"\n");
    text.append ("  Departamento: ");
    text.append (autoridad.caName.getRDN (ObjectID.organizationalUnit)+"\n");
    text.append ("  Email: ");
    text.append (autoridad.caName.getRDN (ObjectID.emailAddress)+"\n");
    text.append ("  Nombre: ");
    text.append (autoridad.caName.getRDN (ObjectID.commonName)+"\n");
      }
      else
      {
    text.append ("  No presente.\n");
      }
      if (autoridad.caPublicKey!=null)
      {
    text.append ("  Clave: ");
    text.append (autoridad.caPublicKey.getAlgorithm ()+"\n");
    if (autoridad.caChain!=null)
    {
      text.append ("  Firmas:\n");
      for (i=0;i<autoridad.caChain.length;i++)

        text.append ("    "+((Name)autoridad.caChain[i].getIssuerDN ()).getRDN (ObjectID.commonName)+"\n");
    }

      }
      text.append ("Usuario.\n");
      if (autoridad.userName!=null)
      {
          text.append ("  Pais: ");
          text.append (autoridad.userName.getRDN (ObjectID.country)+"\n");
          text.append ("  Estado o Provincia: ");
      text.append (autoridad.userName.getRDN (ObjectID.stateOrProvince)+"\n");
          text.append ("  Organizacion: ");
          text.append (autoridad.userName.getRDN (ObjectID.organization)+"\n");
          text.append ("  Departamento: ");
          text.append (autoridad.userName.getRDN (ObjectID.organizationalUnit)+"\n");
          text.append ("  Email: ");
          text.append (autoridad.userName.getRDN (ObjectID.emailAddress)+"\n");
          text.append ("  Nombre:");
          text.append (autoridad.userName.getRDN (ObjectID.commonName)+"\n");
      }
      else
      {
          text.append ("  No presente.\n");
      }
      if (autoridad.userPublicKey!=null)
      {
          text.append ("  Clave: ");
          text.append (autoridad.userPublicKey.getAlgorithm ()+"\n");
          if (autoridad.userChain!=null)
          {
            text.append ("  Firmas:\n");
            for (i=0;i<autoridad.userChain.length;i++)
              text.append ("    "+((Name)autoridad.userChain[i].getIssuerDN ()).getRDN (ObjectID.commonName)+" "+Util.toString (autoridad.userChain[i].getFingerprint ())+"\n");
          }
      }
      value=userInput.showDialog (options,text.toString ());
  }
    JButton matrizJButton[]={
     datosDeUsuario,
     generarParDeClavesDeUsuario,
     crearUnCertificadoDeUsuario,
     importarCertificado,
     importarPkcs1,
     importarPkcs8,
     importarPkcs10,
     importarPkcs12,
     exportarUnCertificado,
     exportarPkcs1,
     exportarPkcs8,
     exportarPkcs10,
     exportarPkcs12,
     borrarUltimoCertificado,
     verificarCertificado,
     utilizarUsuarioComoCA,
     utilizarCAComoUsuario,
     nuevo,
     salir
  };



  */





















