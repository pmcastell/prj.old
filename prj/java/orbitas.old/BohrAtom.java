// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3)
// Source File Name:   BohrAtom.java



import java.applet.Applet;
import java.awt.*;
import particles.*;
import particles.Nucleus;
import particles.Orbital;
import particles.Photon;

import physics2000.P2Applet;

public class BohrAtom extends P2Applet
{

    private int ringRadius()
    {
        return radiusAt(energyLevel);
    }

    public BohrAtom()
    {
        PHOTON_SPEED = 3;
    }

    public void initiatePhoton(int aLevel)
    {
        Dimension d = size();
        int newColor = 0;
        int wavelength = 0;
        int i = 0;
        do
            if(energyLevel == levels[i][0] && aLevel == levels[i][1])
            {
                newColor = levels[i][2];
                wavelength = levels[i][3];
            }
        while(++i <= 2);
        if(newColor != 0)
          photon = new Photon(d.width / 2 + radiusAt(energyLevel), d.height, 0.0D, (double) -PHOTON_SPEED,
                              wavelength, energyLevel, new Color(newColor), this);

    }

    public void step()
    {
        Dimension d = size();
        nucleus.step();
        if(photon != null)
            if(photon.top() < d.height / 2)
            {
                absorbPhoton();
                photon = null;
            } else
            if(photon.top() > d.height)
                photon = null;
            else
                photon.step();
        paintOffscreen();
        getGraphics().drawImage(super.offscreen, 0, 0, this);
    }

    private void paintOffscreen()
    {
        Graphics g = super.offscreen.getGraphics();
        Dimension d = size();
        g.setColor(Color.white);
        g.fillRect(0, 0, d.width, d.height);
        drawRings(g);
        nucleus.paintAll(g);
        if(photon != null)
            photon.paint(g);
    }

    private int middle(int a, int b)
    {
        int r1 = radiusAt(a);
        int r2 = radiusAt(b);
        return r1 + r2 / 2;
    }

    private int gap()
    {
        return 18;
    }

    public void update(Graphics g)
    {
        paint(g);
    }

    private void drawRings(Graphics g)
    {
        Dimension d = size();
        int l = 4;
        do
        {
            g.setColor(l != energyLevel ? RING_COLOR_OFF : RING_COLOR);
            int r = radiusAt(l);
            g.fillOval(nucleus.centerx() - r - 1, nucleus.centery() - r - 1, r * 2 + 2, r * 2 + 2);
            g.setColor(Color.white);
            g.fillOval((nucleus.centerx() - r) + 1, (nucleus.centery() - r) + 1, r * 2 - 2, r * 2 - 2);
        } while(--l >= 2);
    }

    public void loadImages()
    {
        nImage = getImage(getCodeBase(), "particles/red24.gif");
        eImage = getImage(getCodeBase(), "particles/yellow13.gif");
        initParticles();
    }

    private void initParticles()
    {
        Dimension d = size();
        int gravity = Integer.parseInt(getParameter("gravity", "6000"));
        nucleus = new Nucleus(nImage, d.width / 2, d.height / 2, gravity);
        energyLevel = 4;
        nucleus.addOrbital(electron = new Orbital(eImage, ringRadius(), 0, nucleus));
    }

    private void absorbPhoton()
    {
        int dest = 0;
        int i = 0;
        do
            if(levels[i][2] == photon.energy())
                dest = levels[i][1];
        while(++i <= 2);
        if(dest != 0)
            changeLevel(dest);
    }

    public boolean mouseDown(Event e, int x, int y)
    {
        Dimension d = size();
        int dx = x - d.width / 2;
        int dy = y - d.height / 2;
        int distance = (int)Math.sqrt(dx * dx + dy * dy);
        int max = 16;
        double square = (1.0D * (double)distance * (double)max) / (double)(d.width / 2);
        int level = (int)Math.round(Math.sqrt(square));
        setLevel(Math.max(2, level));
        return true;
    }

    public void init()
    {
        super.init();
        Dimension d = size();
        Orbital.setScaleFactor(1);
        setBackground(Color.white);
        PHOTON_SPEED = Integer.parseInt(getParameter("photonSpeed", "3"));
    }

    private int radiusAt(int level)
    {
        Dimension d = size();
        return (level * level * (d.width / 2 - gap())) / 16;
    }

    public void setLevel(int aLevel)
    {
        if(aLevel < energyLevel)
            changeLevel(aLevel);
        else
        if(aLevel > energyLevel)
            initiatePhoton(aLevel);
    }

    private void changeLevel(int aLevel)
    {
        if(aLevel < energyLevel)
            emitPhoton(aLevel);
        energyLevel = aLevel;
        electron.changeRadius(ringRadius());
    }

    private void emitPhoton(int aLevel)
    {
        Dimension d = size();
        int newColor = 0;
        int wavelength = 0;
        int i = 0;
        do
            if(energyLevel == levels[i][1] && aLevel == levels[i][0])
            {
                newColor = levels[i][2];
                wavelength = levels[i][3];
            }
        while(++i <= 2);
        if(newColor != 0)
        {
            Photon p = new Photon(d.width / 2 + radiusAt(energyLevel),
                                  d.height / 2 + 1, 0.0D, PHOTON_SPEED,
                                  wavelength, energyLevel,new Color(newColor), this);
            photon = p;
        }
    }

    Nucleus nucleus;
    Orbital electron;
    Photon photon;
    Image nImage;
    Image eImage;
    int levels[][] = {
        {
            2, 3, (new Color(255, 128, 0)).getRGB() & 0xffffff, 12
        }, {
            3, 4, (new Color(50, 0, 0)).getRGB() & 0xffffff, 24
        }, {
            2, 4, (new Color(0, 0, 170)).getRGB() & 0xffffff, 9
        }
    };
    Dimension size;
    int energyLevel;
    private int PHOTON_SPEED;
    private static final int MIN_LEVEL = 2;
    private static final int MAX_LEVEL = 4;
    private static final int RING_WIDTH = 2;
    private static final Color RING_COLOR = new Color(255, 255, 80);
    private static final Color RING_COLOR_OFF = new Color(200, 200, 200);

}
