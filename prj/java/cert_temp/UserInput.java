import java.awt.*;
import java.awt.event.*;
import com.sun.java.swing.*;
import terminador;
class FDialog extends com.sun.java.swing.JDialog {
   public FDialog(Frame f, String title, boolean modal)
   {
      super(f, title, modal);
   }

   public void pack() {
      int anchura;
      super.pack();
      Dimension d=this.getSize();
      anchura=(int) (1.5*this.getFontMetrics(this.getFont()).stringWidth(this.getTitle()));
      if (d.width>anchura) {
         this.setSize(d.width+10,d.height);
      } else {
         this.setSize(anchura, d.height);
      }   
   }
}


public class UserInput extends JFrame implements ActionListener, terminador {
   protected static final String TITLE = "Aplicacion de certificados";
   protected static final String[] FROMUSEROPT = { "Aceptar", "Cancelar" };
   protected static final String BROWSELABEL = "Explorar...";
   protected int ch;
   protected String ust = null;
//   protected JDialog miDialog = null;
   protected FDialog miDialog = null;
//   protected JDialog fromUser = null;
   protected FDialog fromUser = null;
   protected JTextField utf,utgf;
   protected JComboBox uc;
   protected JTextField utfa[];
   protected JComboBox uca[];
   protected String userValue[];

   public static void center (Window w) {
     Dimension ds = w.getToolkit().getScreenSize ();
     Dimension dw = w.getPreferredSize ();
     w.setLocation ((ds.width - dw.width) / 2, (ds.height - dw.height) / 2);
   }
   protected JPanel buildTextMessage (String text) {
     JPanel rtP = new JPanel ();
     JLabel [] aOJLabel;
     int nl = 1, lc;

     for (int i = 0; i < text.length (); i++) {
        if (text.charAt (i) == '\n') {
           nl++;
        }
     }
     aOJLabel = new JLabel[nl];
     rtP.setLayout (new GridLayout (nl+2, 1, 0, 0));
     lc = 0; nl = 0;
     rtP.add(new JLabel(""));
     for (int i = 0; i < text.length (); i++) {
        if (text.charAt (i) == '\n') {
           aOJLabel[nl] = new JLabel (text.substring (lc, i),
                                      SwingConstants.CENTER);
           Font f= aOJLabel[nl].getFont();
           String name = f.getName(); int style=f.getStyle(), size=f.getSize();
           aOJLabel[nl].setFont(new Font(name,Font.BOLD,size));
           lc = i + 1;
           rtP.add (aOJLabel [nl++]);
        }
     }
     aOJLabel[nl] = new JLabel (text.substring (lc, text.length ()),
                                 SwingConstants.CENTER);
     rtP.add (aOJLabel[nl]);
     rtP.add(new JLabel(""));
     return rtP;
   }

   protected String getFile (String value) {
     JFrame auxFr = new JFrame ();
     FileDialog fd = new FileDialog (auxFr, TITLE);
     String rtv;

     fromUser.setEnabled (false);
     fd.setFile (value);
     fd.pack ();
//     center(fd);
     fd.setLocation(600,400);
     fd.setVisible (true);
//     auxFr.pack ();
//     auxFr.setVisible (true);
     if (fd.getFile () == null) {
        rtv = null;
     } else {
        rtv = fd.getDirectory() + fd.getFile ();
     }
     fromUser.setEnabled (true);
     auxFr.dispose ();
     return rtv;
   }
   public int showDialog(String mensa) {
      String []b={};
      return showDialog(mensa,b);
   }

   public int showDialog (String text, String[] opts, boolean modal) {
     miDialog = new FDialog(this, TITLE, modal);
     JPanel butPanel = new JPanel();
     JButton[] aOfButton = new JButton[opts.length];

     for (int i = 0; i < opts.length; i++) {
       aOfButton[i] = new JButton (opts[i]);
       aOfButton[i].setActionCommand (Integer.toString(i));
       aOfButton[i].addActionListener (this);
       butPanel.add (aOfButton[i]);
     }
     miDialog.addWindowListener(new UserInput_this_windowAdapter(this));
     miDialog.getContentPane().add (this.buildTextMessage (text), BorderLayout.NORTH);
     miDialog.getContentPane().add (butPanel, BorderLayout.SOUTH);
     miDialog.pack ();
     center (miDialog);
     miDialog.setVisible (true);
     if (miDialog != null) {
        miDialog.dispose ();
        miDialog = null;
     }
     return ch;
   }
   public int showDialog (String text, String[] opts)
   {
      return showDialog(text, opts, true);
   }

   public int showDialog ( String[] opts,String text) {
     miDialog = new FDialog(this, TITLE, true);
     JPanel butPanel = new JPanel();
     butPanel.setLayout (new GridLayout (opts.length,1));
     JButton[] aOfButton = new JButton[opts.length];

     for (int i = 0; i < opts.length; i++) {
       aOfButton[i] = new JButton (opts[i]);
       aOfButton[i].setActionCommand (Integer.toString(i));
       aOfButton[i].addActionListener (this);
       butPanel.add (aOfButton[i]);
     }
     miDialog.addWindowListener(new UserInput_this_windowAdapter(this));
     JPanel textPanel=new JPanel ();
     textPanel.add (this.buildTextMessage (text));
     miDialog.getContentPane().add (textPanel,BorderLayout.EAST);
     miDialog.getContentPane().add (butPanel, BorderLayout.CENTER);
     miDialog.pack ();
     center (miDialog);
     miDialog.setVisible (true);
     if (miDialog != null) {
        miDialog.dispose ();
        miDialog = null;
     }
     return ch;
   }

   public String[] getFromUser (String label, String name[], String value[][]) {
      int i,j;
      userValue=null;
      fromUser = new FDialog(this, TITLE, true);

      JPanel strPanel = new JPanel ();
      strPanel.setLayout (new GridLayout (name.length,2));
      uca=new JComboBox [value.length];
      for (i=0;i<value.length;i++) {
          uca[i]=new JComboBox ();
          strPanel.add (new JLabel (name[i],SwingConstants.RIGHT));
          for (j=0;j<value[i].length;j++) {
             uca[i].addItem (value[i][j]);
          }
          strPanel.add (uca[i]);
      }
      JPanel butPanel = new JPanel ();
      JButton[] aOfButton = new JButton[FROMUSEROPT.length];

      for (i = 0; i < FROMUSEROPT.length; i++) {
        aOfButton[i] = new JButton (FROMUSEROPT[i]);
        aOfButton[i].setActionCommand (Integer.toString(i));
        aOfButton[i].addActionListener (this);
        butPanel.add (aOfButton[i]);
      }
      fromUser.addWindowListener(new UserInput_this_windowAdapter(this));
      fromUser.getContentPane().add (this.buildTextMessage (label), BorderLayout.NORTH);
      fromUser.getContentPane().add (strPanel, BorderLayout.CENTER);
      fromUser.getContentPane().add (butPanel, BorderLayout.SOUTH);
      fromUser.pack ();
      center (fromUser);
      fromUser.setVisible (true);
      if (fromUser != null) {
         fromUser.dispose ();
         fromUser = null;
      }
      return userValue;
   }

   public String[] getFromUser (String label, String name[], String value[]) {
      int i;
      userValue=null;
      fromUser = new FDialog(this, TITLE, true);
      JPanel strPanel = new JPanel ();
      strPanel.setLayout (new GridLayout (name.length,2));
      utfa=new JTextField[value.length];
      for (i=0;i<value.length;i++) {
         utfa[i]=new JTextField (value[i]);
         strPanel.add (new JLabel (name[i],SwingConstants.RIGHT));
         strPanel.add (utfa[i]);
      }
      JPanel butPanel = new JPanel ();
      JButton[] aOfButton = new JButton[FROMUSEROPT.length];
      for (i = 0; i < FROMUSEROPT.length; i++) {
         aOfButton[i] = new JButton (FROMUSEROPT[i]);
         aOfButton[i].setActionCommand (Integer.toString(i));
         aOfButton[i].addActionListener (this);
         butPanel.add (aOfButton[i]);
      }
      fromUser.addWindowListener(new UserInput_this_windowAdapter(this));
      fromUser.getContentPane().add (this.buildTextMessage (label), BorderLayout.NORTH);
      fromUser.getContentPane().add (strPanel, BorderLayout.CENTER);
      fromUser.getContentPane().add (butPanel, BorderLayout.SOUTH);
      fromUser.pack ();
      center (fromUser);
      fromUser.setVisible (true);
      if (fromUser != null) {
         fromUser.dispose ();
         fromUser = null;
      }
      return userValue;
   }

   public String[] getFromUser (String label,String n1[],String v1[],String n2[],String v2[][])
   {
     return getFromUser (label,n1,v1,-1,n2,v2);
   }

   public String[] getFromUser (String label,String n1[],String v1[],int brow,String n2[],String v2[][])
   {
      int i,j;
      userValue=null;
      fromUser = new FDialog(this, TITLE, true);
      JPanel strPanel = new JPanel ();
      strPanel.setLayout (new GridLayout (n1.length+n2.length,2));
      utfa=new JTextField[v1.length];
      uca=new JComboBox[v2.length];
      for (i=0;i<v1.length;i++) {
          utfa[i]=new JTextField (v1[i]);
          if (brow==i) {
             utgf=utfa[i];
             JPanel auxp=new JPanel ();
             strPanel.add (new JLabel (n1[i],SwingConstants.RIGHT));
             auxp.add (utfa[i]);
             JButton browseButton = new JButton (BROWSELABEL);
             browseButton.setActionCommand (Integer.toString(-1));
             browseButton.addActionListener (this);
             auxp.add (browseButton);
             strPanel.add (auxp);
          } else {
             strPanel.add (new JLabel (n1[i],SwingConstants.RIGHT));
             strPanel.add (utfa[i]);
          }
      }
      for (i=0;i<v2.length;i++) {
          uca[i]=new JComboBox();
          strPanel.add (new JLabel (n2[i],SwingConstants.RIGHT));
          for (j=0;j<v2[i].length;j++) {
             uca[i].addItem (v2[i][j]);
          }
          strPanel.add (uca[i]);
      }
      JPanel butPanel = new JPanel ();
      JButton[] aOfButton = new JButton[FROMUSEROPT.length];
      for (i = 0; i < FROMUSEROPT.length; i++) {
         aOfButton[i] = new JButton (FROMUSEROPT[i]);
         aOfButton[i].setActionCommand (Integer.toString(i));
         aOfButton[i].addActionListener (this);
         butPanel.add (aOfButton[i]);
      }
      fromUser.addWindowListener(new UserInput_this_windowAdapter(this));
      fromUser.getContentPane().add (this.buildTextMessage (label), BorderLayout.NORTH);
      fromUser.getContentPane().add (strPanel, BorderLayout.CENTER);
      fromUser.getContentPane().add (butPanel, BorderLayout.SOUTH);
      fromUser.pack ();
      center (fromUser);
      fromUser.setVisible (true);
      if (fromUser != null) {
         fromUser.dispose ();
         fromUser = null;
      }
      return userValue;
   }

   public String[] getFromUser (String label,String n1,String v1,String n2,String v2[])
   {
      return getFromUser (label,n1,v1,false,n2,v2);
   }

   public String[] getFromUser (String label,String n1,String v1,boolean brow,String n2,String v2[])
   {
      String n1aux[]=new String[1],v1aux[]=new String[1],n2aux[]=new String[1],v2aux[][]=new String[1][v2.length];
      n1aux[0]=n1;
      n2aux[0]=n2;
      v1aux[0]=v1;
      for (int i=0;i<v2.length;i++) {
         v2aux[0][i]=v2[i];
      }
      return getFromUser (label,n1aux,v1aux,(brow ? 0:-1),n2aux,v2aux);
   }

   public String getFromUser (String label, String name, String value[]) {
      fromUser = new FDialog(this, TITLE, true);
      JPanel strPanel = new JPanel ();
      uc=new JComboBox ();

      for (int i=0;i<value.length;i++) {
         uc.addItem (value[i]);
      }
      JPanel butPanel = new JPanel ();
      JButton[] aOfButton = new JButton[FROMUSEROPT.length];
      for (int i = 0; i < FROMUSEROPT.length; i++) {
         aOfButton[i] = new JButton (FROMUSEROPT[i]);
         aOfButton[i].setActionCommand (Integer.toString(i));
         aOfButton[i].addActionListener (this);
         butPanel.add (aOfButton[i]);
      }
      strPanel.add (new JLabel (name,SwingConstants.RIGHT));
      strPanel.add (uc);
      fromUser.addWindowListener(new UserInput_this_windowAdapter(this));
      fromUser.getContentPane().add (this.buildTextMessage (label), BorderLayout.NORTH);
      fromUser.getContentPane().add (strPanel, BorderLayout.CENTER);
      fromUser.getContentPane().add (butPanel, BorderLayout.SOUTH);
      fromUser.pack ();
      center (fromUser);
      fromUser.setVisible (true);
      if (fromUser != null) {
         fromUser.dispose ();
         fromUser = null;
      }
      return ust;
   }

   public String getFromUserKey (String label)
   {
     fromUser = new FDialog(this, TITLE, true);
//     utf = new JTextField (40);
     utf = new JPasswordField(40);
//     utf.setEchoChar ('*');
     JPanel strPanel = new JPanel ();
     JPanel butPanel = new JPanel ();
     JButton[] aOfButton = new JButton[FROMUSEROPT.length];
     for (int i = 0; i < FROMUSEROPT.length; i++) {
       aOfButton[i] = new JButton (FROMUSEROPT[i]);
       aOfButton[i].setActionCommand (Integer.toString(i));
       aOfButton[i].addActionListener (this);
       butPanel.add (aOfButton[i]);
     }
     strPanel.add (utf);
     fromUser.addWindowListener(new UserInput_this_windowAdapter(this));
     fromUser.getContentPane().add (this.buildTextMessage (label), BorderLayout.NORTH);
     fromUser.getContentPane().add (strPanel, BorderLayout.CENTER);
     fromUser.getContentPane().add (butPanel, BorderLayout.SOUTH);
     fromUser.pack ();
     center (fromUser);
     fromUser.setVisible (true);
     if (fromUser != null) {
        fromUser.dispose ();
        fromUser = null;
     }
     return ust;
   }

   public String getFromUser (String label, String name, String value,
                              boolean useFile) {
      fromUser = new FDialog(this, TITLE, true);
      JPanel strPanel = new JPanel ();
      utf = new JTextField (value, value.length () > 20 ? value.length () : 20);
      JPanel butPanel = new JPanel ();
      JButton[] aOfButton = new JButton[FROMUSEROPT.length];
      JButton browseButton;

      for (int i = 0; i < FROMUSEROPT.length; i++) {
         aOfButton[i] = new JButton (FROMUSEROPT[i]);
         aOfButton[i].setActionCommand (Integer.toString(i));
         aOfButton[i].addActionListener (this);
         butPanel.add (aOfButton[i]);
      }
      strPanel.add (new JLabel (name,SwingConstants.RIGHT));
      strPanel.add (utf);
      if (useFile) {
        utgf=utf;
        browseButton = new JButton (BROWSELABEL);
        browseButton.setActionCommand (Integer.toString(-1));
        browseButton.addActionListener (this);
        strPanel.add (browseButton);
      }
      fromUser.addWindowListener(new UserInput_this_windowAdapter(this));
      fromUser.getContentPane().add (this.buildTextMessage (label), BorderLayout.NORTH);
      fromUser.getContentPane().add (strPanel, BorderLayout.CENTER);
      fromUser.getContentPane().add (butPanel, BorderLayout.SOUTH);
      fromUser.pack ();
      center (fromUser);
      fromUser.setVisible (true);
      if (fromUser != null) {
         fromUser.dispose ();
         fromUser = null;
      }
      return ust;
   }

   public String getFromUser (String label, String name, String value) {
      return getFromUser (label, name, value, false);
   }

   public void actionPerformed (ActionEvent e) {
      if (miDialog != null) {
         ch = Integer.parseInt (e.getActionCommand());
         miDialog.dispose ();
         miDialog = null;
      }
      if (fromUser != null) {
         switch (Integer.parseInt (e.getActionCommand())) {
            case -1:
               utgf.setText (getFile (utgf.getText ()));
               break;
            case 0:
               if (utf!=null) {
                  ust = utf.getText ();
               } else if (uc!=null) {
                  ust=(String)uc.getSelectedItem ();
               } else if (utfa!=null && uca!=null)	{
                 userValue=new String[utfa.length+uca.length];
                 int i;
                 for (i=0;i<utfa.length;i++) {
                    userValue[i]=utfa[i].getText ();
                 }
                 for (;i<uca.length+utfa.length;i++) {
                    userValue[i]=(String) uca[i-utfa.length].getSelectedItem ();
                 }
               } else if (utfa!=null) {
                 userValue=new String[utfa.length];
                 for (int i=0;i<utfa.length;i++) {
                    userValue[i]=utfa[i].getText ();
                 }
               } else if (uca!=null) {
                 userValue=new String[uca.length];
                 for (int i=0;i<uca.length;i++) {
                    userValue[i]=(String)uca[i].getSelectedItem ();
                 }
               }
               utf=null;
               uc=null;
               utfa=null;
               uca=null;
               fromUser.dispose (); fromUser = null;
               break;
             default:
               ust = null;
               fromUser.dispose (); fromUser = null;
               break;
         }
      }
   }

   void this_windowClosing(WindowEvent e) {
      ch = -1; ust = null;
      if (miDialog != null) {
         miDialog.dispose ();
         miDialog = null;
      }
      if (fromUser != null) {
         fromUser.dispose ();
         fromUser = null;
      }
   }
   /**
   * 'Mata' este Thread
   */
   public void finaliza()
   {
      this_windowClosing(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
   }

   public static void main(String[] args) {
      String label="Esto es una prueba\n del método getFromUser() de la clase UserInput";
      String n1[]={"Algoritno"};
      String n2[]={"nº bits"};
      String v1[]={"Uno"};
      String v2[][]={{"512","1024","2048"}};
      UserInput userInput1 = new UserInput();
      System.out.println(userInput1.getFromUserKey("Esto es una Mierda"));
      System.out.println(userInput1.getFromUser (label,n1,v1,-1,n2,v2));      
      System.out.println (userInput1.showDialog ("Esto es una prueba\ndel metodo showDialog()\nde la clase UserInput",
                                                 args));
      System.out.println (userInput1.getFromUser ("Esto es una prueba\ndel metodo fromUser()\nde la clase UserInput",
                                                  "Nombre del campo",
                                                  "Valor del campo"));
      System.out.println (userInput1.getFromUser ("Esto es una prueba\ndel metodo fromUser()\nde la clase UserInput",
                                                  "Nombre del campo",
                                                  "Valor del campo", true));


      System.exit(0);
   }
}

class UserInput_this_windowAdapter extends WindowAdapter {
   UserInput adaptee;

   UserInput_this_windowAdapter(UserInput adaptee) {
     this.adaptee = adaptee;
   }

   public void windowClosing(WindowEvent e) {
     adaptee.this_windowClosing(e);
   }
}

