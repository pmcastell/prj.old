//<applet code=probabilidad_oposiciones.class width=400 height=200></applet>
import java.awt.*;
import java.applet.Applet;
import java.awt.event.*;
import java.lang.Math;
public class probabilidad_oposiciones extends Applet {
    TextField npea = new TextField(5),
              nra  = new TextField("4"),
              pnp  = new TextField(60);

    Button Calcular = new Button("&Calcular");
    Panel p = new Panel();

    public void init() {
        Calcular.addActionListener(procesa_evento);
        npea.addActionListener(procesa_evento);
        nra.addActionListener(procesa_evento);
        p.setLayout(new GridLayout(3,2));
        p.add(new Label("Nº de preguntas echadas a suerte: ", Label.RIGHT));
        p.add(npea);
        p.add(new Label("Nº de respuestas por pregunta   : ", Label.RIGHT));
        p.add(nra);
        p.add(new Label("Probabilidad de no perder       : ", Label.RIGHT));
        p.add(pnp);
        add(p,BorderLayout.CENTER);
        add(Calcular,BorderLayout.SOUTH);
    }
    class CL implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            long lnpea= Long.parseLong(npea.getText());
            long lnra = Long.parseLong(nra.getText());
            pnp.setText(probabilidad_no_perder(lnpea,lnra));
        }
    }
    CL procesa_evento= new CL();
    String probabilidad_no_perder(long npea, long nra)
    {
        long total=0;
        for(long i=0; i<npea/4; i++) {
            total+= Combinatorio(npea,i)*Math.pow( nra-1, npea-i);
        }
        return (Double.toString((Math.pow(nra,npea)-total)/Math.pow(nra,npea)));
    }    
    long Combinatorio(long m,long n )
    {
        long r=1, s=1;
        for(long i=m-n+1; i<=m; i++) {
            r*=i;
        }
        for(long i=2; i<=n; i++) {
            s*=i;
        }
        return (r/s);
     }    
     public static void main (String args[])
     {
        probabilidad_oposiciones miApplet= new probabilidad_oposiciones();
        Frame miFrame= new Frame();
        miFrame.addWindowListener( 
            new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    System.exit(0);
                }
            }
        );
        miFrame.add(miApplet,BorderLayout.CENTER);
        miFrame.setSize(400,200);
        miApplet.init();
        miApplet.start();
        miFrame.setVisible(true);
     }

  public probabilidad_oposiciones() {
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void jbInit() throws Exception{
    this.setSize(new Dimension(407, 232));
  }    
}   

        

            
        

    
