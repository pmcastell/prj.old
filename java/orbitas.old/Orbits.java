// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3)
// Source File Name:   Orbits.java




import controls.ThreeDButton;
import java.applet.Applet;
import java.awt.*;
import java.io.PrintStream;
import particles.*;
import physics2000.P2Applet;

public class Orbits extends P2Applet
{

    public void init()
    {
        super.init();
        GridBagLayout gbl = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = 0;
        gbc.anchor = 15;
        gbc.weighty = 1.0D;
        gbc.insets = new Insets(2, 2, 2, 2);
        setLayout(gbl);
        Image i1 = getImage(getCodeBase(), "orbits/forceOn.gif", false);
        Image i2 = getImage(getCodeBase(), "orbits/forceOff.gif", false);
        button = new ThreeDButton("Ver Fuerza", i1, "No- Fuerza", i2);
        gbl.setConstraints(button, gbc);
        add(button);
        vectorScale = Integer.parseInt(getParameter("vectorScale", "4"));
    }

    public boolean keyDown(Event e, int key)
    {
        if(key == 8 || key == 127)
        {
            nucleus.deleteAllOrbitals();
            repaint();
        }
        if(key == 116)
            testit();
        return true;
    }

    private void testit()
    {
        long t1 = System.currentTimeMillis();
        super.offscreen.getGraphics();
        for(int i = 0; i < 100; i++)
            step();

        long tf = System.currentTimeMillis() - t1;
        System.err.println((double)tf / 100D);
    }

    public boolean mouseDown(Event e, int x, int y)
    {
        paused = true;
        newPoint = new Point(x, y);
        newVector = new Point(0, 0);
        drawElectron(x, y);
        return true;
    }

    private void drawElectron(int x, int y)
    {
        int w = eImage.getWidth(this);
        int h = eImage.getHeight(this);
        getGraphics().drawImage(eImage, x - w / 2, y - h / 2, this);
    }

    public boolean mouseDrag(Event e, int x, int y)
    {
        if(paused)
        {
            drawArrow();
            newVector.x = x - newPoint.x;
            newVector.y = y - newPoint.y;
            drawArrow();
            return true;
        } else
        {
            return false;
        }
    }

    private void drawArrow()
    {
        Graphics g = getGraphics();
        g.setXORMode(Color.white);
        g.drawLine(newPoint.x, newPoint.y, newPoint.x + newVector.x, newPoint.y + newVector.y);
    }

    public boolean mouseUp(Event e, int x, int y)
    {
        if(paused)
        {
            Orbital o = new Orbital(eImage, newPoint.x - nucleus.centerx(), newPoint.y - nucleus.centery(), newVector.x / vectorScale, newVector.y / vectorScale, nucleus);
            o.toggleVector(forceOn);
            Orbital.setScaleFactor(1);
            nucleus.addOrbital(o);
            repaint();
        }
        paused = false;
        return true;
    }

    public void update(Graphics g)
    {
        paint(g);
    }

    public boolean action(Event e, Object arg)
    {
        if(e.target == button)
        {
            forceOn = ((ThreeDButton)e.target).getValue();
            for(int i = 0; i < nucleus.count(); i++)
                nucleus.orbitals()[i].toggleVector(forceOn);

            return true;
        } else
        {
            return false;
        }
    }

    private void paintOffscreen()
    {
        Dimension d = size();
        Graphics g = super.offscreen.getGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, d.width, d.height);
        g.setColor(Color.gray);
        g.drawRect(0, 0, d.width - 1, d.height - 1);
        nucleus.paintAll(g);
        if(paused)
            drawElectron(newPoint.x, newPoint.y);
        g.setColor(Color.gray);
        g.drawRect(0, 0, d.width - 1, d.height - 1);
    }

    private void initNucleus()
    {
        Dimension d = size();
        int gravity = Integer.parseInt(getParameter("gravity", "500"));
        nucleus = new Nucleus(nImage, d.width / 2, d.height / 2, gravity);
        int minDistance = (nImage.getWidth(this) + eImage.getWidth(this)) / 2;
        nucleus.setMinMax(minDistance, 800);
        Orbital.setScaleFactor(1);
        Image nShadow = getImage(getCodeBase(), getParameter("bigshadow", "null"), false);
        if(nShadow != null)
            Particle.setShadowImage(nShadow);
        Image eShadow = getImage(getCodeBase(), getParameter("littleshadow", "null"), false);
        if(nShadow != null)
            Orbital.setShadowImage(eShadow);
    }

    public void step()
    {
        if(paused)
            return;
        nucleus.step();
        paintOffscreen();
        Graphics g = getGraphics().create();
        if(!paused)
            g.drawImage(super.offscreen, 0, 0, this);
    }

    public void loadImages()
    {
        nImage = getImage(getCodeBase(), getParameter("nucleus", "nucleus.gif"), false);
        eImage = getImage(getCodeBase(), getParameter("electron", "electron.gif"), false);
        pImage = getImage(getCodeBase(), getParameter("positron", "positron.gif"), false);
        initNucleus();
    }

    public Orbits()
    {
        vectorScale = 4;
        forceOn = false;
    }

    private Nucleus nucleus;
    private boolean paused;
    private Point newPoint;
    private Point newVector;
    private Image nImage;
    private Image eImage;
    private Image pImage;
    private int vectorScale;
    private ThreeDButton button;
    private boolean forceOn;
    private static final Color ARROW_COLOR;
    private static final int CULL_RATE = 1;

    static
    {
        ARROW_COLOR = Color.black;
    }
}
