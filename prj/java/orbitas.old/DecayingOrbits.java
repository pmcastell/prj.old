// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3)
// Source File Name:   DecayingOrbits.java



import java.applet.Applet;
import java.awt.*;
import particles.*;
import physics2000.P2Applet;

public class DecayingOrbits extends P2Applet
{

    private int startRange()
    {
        Dimension d = size();
        return d.width / 2 - eImage.getWidth(this) / 2;
    }

    public void step()
    {
        nucleus.step();
        paintOffscreen();
        getGraphics().drawImage(super.offscreen, 0, 0, this);
        implementDrag();
        checkCount();
    }

    private void checkCount()
    {
        if(nucleus.count() < eCount)
            nucleus.addOrbital(new Orbital(eImage, startRange(), 0, nucleus));
    }

    private void paintOffscreen()
    {
        Dimension d = size();
        Graphics g = super.offscreen.getGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, d.width, d.height);
        nucleus.paintAll(g);
        g.setColor(Color.gray);
        g.drawRect(0, 0, d.width - 1, d.height - 1);
    }

    private void initNucleus()
    {
        Dimension d = size();
        int gravity = Integer.parseInt(getParameter("gravity", "5000"));
        nucleus = new Nucleus(nImage, d.width / 2, d.height / 2, gravity);
        int max = startRange();
        for(int i = 0; i < eCount; i++)
        {
            int dist = ((i + 1) * max) / eCount;
            nucleus.addOrbital(new Orbital(eImage, dist, 0, nucleus));
        }

        nucleus.setMinMax((eImage.getWidth(this) + nImage.getWidth(this)) / 2, 1000);
    }

    public DecayingOrbits()
    {
        drag = 0.999D;
    }

    public void loadImages()
    {
        nImage = getImage(getCodeBase(), getParameter("nucleus"));
        eImage = getImage(getCodeBase(), getParameter("electron"));
        initNucleus();
    }

    public void init()
    {
        super.init();
        eCount = Integer.parseInt(getParameter("count", "2"));
        Orbital.setShadowImage(null);
        Particle.setShadowImage(null);
        drag = Double.valueOf(getParameter("drag", "0.999")).doubleValue();
    }

    private void implementDrag()
    {
        Orbital o[] = nucleus.orbitals();
        for(int i = 0; i < nucleus.count(); i++)
            o[i].moveTo(o[i].relativex(), o[i].relativey());

    }

    Image nImage;
    Image eImage;
    Nucleus nucleus;
    double drag;
    int eCount;
}
