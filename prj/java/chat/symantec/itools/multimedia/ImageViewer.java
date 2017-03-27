// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ImageViewer.java

package symantec.itools.multimedia;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ResourceBundle;
import symantec.itools.beans.PropertyChangeSupport;
import symantec.itools.beans.VetoableChangeSupport;

public class ImageViewer extends Component
    implements Serializable
{

    public ImageViewer()
    {
        vetos = new VetoableChangeSupport(this);
        changes = new PropertyChangeSupport(this);
        image = null;
        fileName = null;
        url = null;
        imageStyle = 1;
    }

    public ImageViewer(Image image1)
    {
        this();
        try
        {
            setImage(image1);
            return;
        }
        catch(PropertyVetoException _ex)
        {
            return;
        }
    }

    public ImageViewer(String s)
        throws MalformedURLException
    {
        this();
        try
        {
            setFileName(s);
            return;
        }
        catch(PropertyVetoException _ex)
        {
            return;
        }
    }

    public ImageViewer(URL url1)
    {
        this();
        try
        {
            setURL(url1);
            return;
        }
        catch(PropertyVetoException _ex)
        {
            return;
        }
    }

    public void addPropertyChangeListener(PropertyChangeListener propertychangelistener)
    {
        changes.addPropertyChangeListener(propertychangelistener);
    }

    public void addVetoableChangeListener(VetoableChangeListener vetoablechangelistener)
    {
        vetos.addVetoableChangeListener(vetoablechangelistener);
    }

    /**
     * @deprecated Method getCenterMode is deprecated
     */

    public boolean getCenterMode()
    {
        return getStyle() == 1;
    }

    public String getFileName()
    {
        return fileName;
    }

    public Image getImage()
    {
        return image;
    }

    public URL getImageURL()
    {
        return url;
    }

    public int getStyle()
    {
        return imageStyle;
    }

    /**
     * @deprecated Method getURL is deprecated
     */

    public URL getURL()
    {
        return getImageURL();
    }

    public Dimension minimumSize()
    {
        return preferredSize();
    }

    public void paint(Graphics g)
    {
        super.paint(g);
        Dimension dimension = size();
        if(image != null)
        {
            int i = image.getWidth(this);
            int j = image.getHeight(this);
            switch(imageStyle)
            {
            case 1: // '\001'
            default:
                g.drawImage(image, (dimension.width - i) / 2, (dimension.height - j) / 2, i, j, this);
                return;

            case 0: // '\0'
                int k = dimension.width / i;
                if(dimension.width % i != 0)
                    k++;
                int l = dimension.height / j;
                if(dimension.height % j != 0)
                    l++;
                int j1 = 0;
                for(int k1 = 0; k1 < l; k1++)
                {
                    int i1 = 0;
                    for(int l1 = 0; l1 < k; l1++)
                    {
                        g.drawImage(image, i1, j1, i, j, this);
                        i1 += i;
                    }

                    j1 += j;
                }

                return;

            case 2: // '\002'
                g.drawImage(image, 0, 0, dimension.width, dimension.height, this);
                return;

            case 3: // '\003'
                g.drawImage(image, 0, 0, this);
                return;
            }
        } else
        {
            g.clearRect(0, 0, dimension.width, dimension.height);
            return;
        }
    }

    public Dimension preferredSize()
    {
        if(image != null)
            return new Dimension(image.getWidth(this), image.getHeight(this));
        else
            return new Dimension(10, 10);
    }

    private void readObject(ObjectInputStream objectinputstream)
        throws ClassNotFoundException, IOException
    {
        objectinputstream.defaultReadObject();
        errors = ResourceBundle.getBundle("symantec.itools.resources.ErrorsBundle");
        if(url != null)
        {
            image = getToolkit().getImage(url);
            MediaTracker mediatracker = new MediaTracker(this);
            mediatracker.addImage(image, 0);
            try
            {
                mediatracker.waitForAll();
                return;
            }
            catch(InterruptedException _ex)
            {
                Object aobj[] = {
                    url
                };
                throw new IOException(MessageFormat.format(errors.getString("ErrorLoadingImageForURL"), aobj));
            }
        } else
        {
            return;
        }
    }

    public void removePropertyChangeListener(PropertyChangeListener propertychangelistener)
    {
        changes.removePropertyChangeListener(propertychangelistener);
    }

    public void removeVetoableChangeListener(VetoableChangeListener vetoablechangelistener)
    {
        vetos.removeVetoableChangeListener(vetoablechangelistener);
    }

    /**
     * @deprecated Method setCenterMode is deprecated
     */

    public void setCenterMode(boolean flag)
        throws PropertyVetoException
    {
        if(flag)
        {
            if(getStyle() != 1)
            {
                setStyle(1);
                return;
            }
        } else
        if(getStyle() != 3)
            setStyle(3);
    }

    public void setFileName(String s)
        throws PropertyVetoException
    {
        String s1 = fileName;
        try
        {
            vetos.fireVetoableChange("fileName", s1, s);
            fileName = s;
            if(fileName != null && fileName != "")
                setURL(new URL(fileName));
            else
                setURL(null);
        }
        catch(MalformedURLException _ex)
        {
            fileName = s1;
        }
        repaint();
        changes.firePropertyChange("fileName", s1, s);
    }

    public void setImage(Image image1)
        throws PropertyVetoException
    {
        fileName = null;
        Image image2 = image;
        vetos.fireVetoableChange("image", image2, image1);
        image = image1;
        if(image1 != null)
            try
            {
                MediaTracker mediatracker = new MediaTracker(this);
                mediatracker.addImage(image, 0);
                mediatracker.waitForID(0);
            }
            catch(InterruptedException interruptedexception) { }
        else
            repaint();
        changes.firePropertyChange("image", image2, image1);
    }

    public void setImageURL(URL url1)
        throws PropertyVetoException
    {
        URL url2 = url;
        vetos.fireVetoableChange("imageURL", url2, url1);
        url = url1;
        fileName = null;
        Image image1 = null;
        if(url != null)
            image1 = getToolkit().getImage(url);
        setImage(image1);
        repaint();
        changes.firePropertyChange("imageURL", url2, url1);
    }

    public void setStyle(int i)
        throws PropertyVetoException
    {
        if(i != imageStyle)
        {
            Integer integer = new Integer(imageStyle);
            Integer integer1 = new Integer(i);
            vetos.fireVetoableChange("style", integer, integer1);
            imageStyle = i;
            repaint();
            changes.firePropertyChange("style", integer, integer1);
        }
    }

    /**
     * @deprecated Method setURL is deprecated
     */

    public void setURL(URL url1)
        throws PropertyVetoException
    {
        setImageURL(url1);
    }

    public static final int IMAGE_TILED = 0;
    public static final int IMAGE_CENTERED = 1;
    public static final int IMAGE_SCALED_TO_FIT = 2;
    public static final int IMAGE_NORMAL = 3;
    protected transient Image image;
    protected String fileName;
    protected URL url;
    protected int imageStyle;
    protected transient ResourceBundle errors;
    private VetoableChangeSupport vetos;
    private PropertyChangeSupport changes;

}
