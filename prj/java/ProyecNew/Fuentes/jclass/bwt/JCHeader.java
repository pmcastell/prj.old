// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JCHeader.java

package jclass.bwt;

import java.applet.Applet;
import java.awt.*;
import java.io.PrintStream;
import java.util.Vector;
import javax.swing.JComponent;
import jclass.base.BaseComponent;
import jclass.base.TransientComponent;
import jclass.util.JCVector;

// Referenced classes of package jclass.bwt:
//            JCContainer, BWTEnum, HeaderButton, HeaderConverter, 
//            HeaderLabel, JCActionListener, JCButton, JCComponent, 
//            JCLabel, JCMultiColumnData, JCMultiColumnInterface, JCScrollableInterface, 
//            Viewport

public class JCHeader extends JCContainer
    implements JCMultiColumnInterface
{

    public JCHeader()
    {
        this(null, null, null);
    }

    public JCHeader(JCVector jcvector)
    {
        this(null, null, null);
        if(jcvector != null)
            setLabels(jcvector);
    }

    public JCHeader(String as[])
    {
        this(as, null, null);
    }

    public JCHeader(String as[], Applet applet, String s)
    {
        super(applet, s);
        data = new JCMultiColumnData(this);
        resize_col = -999;
        resizing = false;
        num_columns_set = false;
        update_parent = true;
        batched = false;
        actionListeners = new JCVector(0);
        mouse_down_x = -999;
        if(s == null)
            setName("header" + nameCounter++);
        setLayout(null);
        if(getClass().getName().equals("jclass.bwt.JCHeader"))
            getParameters(applet);
        if(as != null)
            setLabels(new JCVector(as));
    }

    public void addActionListener(JCActionListener jcactionlistener)
    {
        actionListeners.addUnique(jcactionlistener);
        Component acomponent[] = getComponents();
        for(int i = 0; i < acomponent.length; i++)
            if(acomponent[i] instanceof JCButton)
                ((JCButton)acomponent[i]).addActionListener(jcactionlistener);

    }

    public JCButton addButton(Object obj)
    {
        HeaderButton headerbutton = new HeaderButton(this, obj);
        for(int i = 0; i < actionListeners.size(); i++)
            headerbutton.addActionListener((JCActionListener)actionListeners.elementAt(i));

        if(!num_columns_set)
            data.setNumColumns(getNumColumns() + 1);
        add(headerbutton);
        updateParent();
        return headerbutton;
    }

    public JCLabel addLabel(Object obj)
    {
        HeaderLabel headerlabel = new HeaderLabel(this, obj);
        if(!num_columns_set)
            data.setNumColumns(getNumColumns() + 1);
        add(headerlabel);
        updateParent();
        return headerlabel;
    }

    public void addNotify()
    {
        super.addNotify();
        data.calcColumnWidths();
    }

    public int calcWidth(int i)
    {
        Component acomponent[] = getComponents();
        if(i >= acomponent.length)
            return 0;
        JCLabel jclabel = (JCLabel)acomponent[i];
        int j = 0;
        int ai[] = comp == null ? new int[0] : comp.getColumnWidths();
        if(comp != null)
        {
            int k = getColumnLeftMargin(i) + getColumnRightMargin(i);
            j = comp.getColumnWidth(i);
            if(i < ai.length && ai[i] != -998)
            {
                if(j - k > 0)
                    return j;
                j = 0;
            }
        }
        Insets insets = jclabel.getInsets();
        insets.left = getColumnLeftMargin(i);
        insets.right = getColumnRightMargin(i);
        jclabel.setInsets(insets);
        int l = JCComponent.getPreferredSize(jclabel).width - jclabel.getBorderThickness();
        if(l > j && i < ai.length && ai[i] == -998 && comp.getMultiColumnData() != null)
            comp.getMultiColumnData().setColumnWidthInternal(i, l);
        return Math.max(j, l);
    }

    protected void drawLine(boolean flag, int i)
    {
        if(!flag)
            x_save = i;
        else
        if(x_save == -999)
            return;
        i = x_save;
        Component component = locate(i, 0);
        if(component == null)
        {
            return;
        } else
        {
            Graphics g = component.getGraphics();
            g.translate(-component.location().x, 0);
            g.setXORMode(getBackground());
            g.drawLine(i, 0, i, size().height);
            g.dispose();
            return;
        }
    }

    private int firstColumnOffset()
    {
        int i = comp == null ? 0 : comp.getColumnPosition(0);
        if(comp instanceof JCScrollableInterface)
            i += ((JCScrollableInterface)comp).getHorizOrigin();
        return i;
    }

    public int getColumnAlignment(int i)
    {
        return data.getColumnAlignment(i);
    }

    public int[] getColumnAlignments()
    {
        return data.getColumnAlignments();
    }

    public int getColumnDisplayWidth(int i)
    {
        int j = data.getColumnDisplayWidth(i);
        if((i == 0 || i == data.getNumColumns() - 1) && comp != null && (comp instanceof JCComponent))
        {
            JCComponent jccomponent = (JCComponent)comp;
            if(i == 0)
                j += jccomponent.getBorderThickness() + JCComponent.getInsets(this).left;
            else
            if(j > 0 && getColumnResizePolicy() == 2)
                j += jccomponent.getBorderThickness() + JCComponent.getInsets(this).right + jccomponent.getHighlightThickness();
        }
        return j;
    }

    public int[] getColumnDisplayWidths()
    {
        return data.getColumnDisplayWidths();
    }

    public int getColumnLeftMargin(int i)
    {
        return data.getColumnLeftMargin(i);
    }

    public int getColumnPosition(int i)
    {
        return data.getColumnPosition(i) + JCComponent.getInsets(this).left;
    }

    public int getColumnResizePolicy()
    {
        return data.getColumnResizePolicy();
    }

    public int getColumnRightMargin(int i)
    {
        return data.getColumnRightMargin(i);
    }

    public int getColumnWidth(int i)
    {
        return data.getColumnWidth(i);
    }

    public int[] getColumnWidths()
    {
        return data.getColumnWidths();
    }

    public JCComponent getLabel(int i)
    {
        Component acomponent[] = getComponents();
        return (JCComponent)acomponent[i];
    }

    public JCComponent[] getLabels()
    {
        Component acomponent[] = getComponents();
        JCComponent ajccomponent[] = new JCComponent[acomponent.length];
        for(int i = 0; i < acomponent.length; i++)
            ajccomponent[i] = (JCComponent)acomponent[i];

        return ajccomponent;
    }

    public JCMultiColumnInterface getMultiColumnComponent()
    {
        return comp;
    }

    public JCMultiColumnData getMultiColumnData()
    {
        return data;
    }

    public int getNumColumns()
    {
        return data.num_columns;
    }

    protected void getParameters()
    {
        super.getParameters();
        HeaderConverter.getParams(this);
    }

    public Dimension getPreferredSize()
    {
        Dimension dimension = super.getPreferredSize();
        dimension.width = preferredWidth();
        return dimension;
    }

    public void layout()
    {
        int i = 0;
        synchronized(this)
        {
            i = getNumColumns();
            if(i == 0 && comp != null && !num_columns_set)
            {
                boolean flag = num_columns_set;
                num_columns_set = true;
                setNumColumns(i = comp.getNumColumns());
                num_columns_set = flag;
            }
        }
        Component acomponent[] = getComponents();
        int j = 0;
        int k = firstColumnOffset();
        for(int l = 0; l < acomponent.length; l++)
        {
            JCLabel jclabel = (JCLabel)acomponent[l];
            if(l >= i)
            {
                jclabel.hide();
            } else
            {
                Insets insets = jclabel.getInsets();
                insets.left = getColumnLeftMargin(l) - jclabel.getBorderThickness();
                insets.right = getColumnRightMargin(l) - jclabel.getBorderThickness();
                jclabel.setInsets(insets);
                int i1 = getColumnDisplayWidth(l);
                if(l == 0)
                {
                    Insets insets1 = jclabel.getInsets();
                    insets1.left += k;
                    jclabel.setInsets(insets1);
                }
                jclabel.alignment = getColumnAlignment(l);
                JCComponent.setBounds(jclabel, j, 0, i1 + jclabel.getBorderThickness(), size().height);
                jclabel.show();
                j += i1;
            }
        }

    }

    protected boolean mouseDown(Event event)
    {
        if(resize_col == -999 || getColumnResizePolicy() == 1)
        {
            return false;
        } else
        {
            resizing = true;
            JCComponent jccomponent = (JCComponent)event.target;
            mouse_down_x = event.x + jccomponent.location().x;
            drawLine(false, mouse_down_x);
            return true;
        }
    }

    protected boolean mouseDrag(Event event)
    {
        if(!resizing || getColumnResizePolicy() == 1)
            return false;
        Component component = (Component)event.target;
        int i = event.x + component.location().x;
        if(getColumnResizePolicy() == 0 && i - getColumnPosition(resize_col) - getColumnLeftMargin(resize_col) < 20)
        {
            return false;
        } else
        {
            drawLine(true, 0);
            drawLine(false, i);
            return true;
        }
    }

    protected boolean mouseExit(Event event)
    {
        if(resizing)
        {
            return true;
        } else
        {
            resize_col = -999;
            setCursor(0);
            return true;
        }
    }

    public boolean mouseExit(Event event, int i, int j)
    {
        if(resizing)
        {
            return false;
        } else
        {
            resize_col = -999;
            resizing = false;
            setCursor(0);
            return true;
        }
    }

    protected boolean mouseMove(Event event)
    {
        if(resizing)
            return true;
        JCComponent jccomponent = (JCComponent)event.target;
        resize_col = getComponent(jccomponent);
        if(getColumnResizePolicy() != 1)
        {
            if(event.x > jccomponent.size().width - 5)
            {
                jccomponent.setCursor(10);
                return true;
            }
            if(event.x < 5)
            {
                for(resize_col--; data.getColumnDisplayWidth(resize_col) == 0 && resize_col >= 0; resize_col--);
                if(getColumnResizePolicy() == 2 && resize_col < 0 && data.getColumnDisplayWidth(0) == 0)
                    resize_col = Math.max(0, resize_col);
                if(resize_col >= 0)
                {
                    jccomponent.setCursor(10);
                    return true;
                }
            }
        }
        return mouseExit(event);
    }

    protected boolean mouseUp(Event event)
    {
        if(!resizing)
            return false;
        Component component = (Component)event.target;
        drawLine(true, 0);
        int i = event.x + component.location().x;
        i -= firstColumnOffset();
        int j = getColumnResizePolicy();
        int k = 0;
        if(j == 0)
            k = Math.max(10, ((getColumnWidth(resize_col) + i) - getColumnPosition(resize_col + 1)) + firstColumnOffset());
        else
        if(j == 2)
            k = i - mouse_down_x;
        Object obj = comp == null ? ((Object) (this)) : ((Object) (comp));
        if(j == 0)
        {
            ((JCMultiColumnInterface) (obj)).setColumnWidth(resize_col, k);
            ((JCMultiColumnInterface) (obj)).setColumnDisplayWidth(resize_col, k);
        } else
        if(j == 2)
        {
            boolean flag = false;
            boolean flag1 = false;
            int j1 = Math.abs(k);
            boolean flag2 = k > 0;
            for(int k1 = resize_col; j1 > 0 && k1 >= 0;)
            {
                int l1 = getColumnDisplayWidth(k1);
                int i2 = getColumnWidth(k1);
                int l;
                int i1;
                if(flag2)
                {
                    if(l1 >= i2 && getColumnDisplayWidth(k1 + 1) != 0)
                        break;
                    if(l1 + j1 > i2)
                    {
                        i1 = i2;
                        l = Math.max(0, i2 - l1);
                    } else
                    {
                        i1 = l1 + j1;
                        l = j1;
                    }
                } else
                {
                    l = Math.min(getColumnDisplayWidth(k1), j1);
                    i1 = getColumnDisplayWidth(k1) - l;
                }
                ((JCMultiColumnInterface) (obj)).setColumnDisplayWidth(k1, i1);
                j1 -= l;
                if(flag2)
                    k1++;
                else
                    k1--;
            }

        }
        resize_col = -999;
        resizing = false;
        setCursor(0);
        return true;
    }

    protected int preferredHeight()
    {
        int i = 0;
        Component acomponent[] = getComponents();
        for(int j = 0; j < acomponent.length; j++)
            i = Math.max(i, JCComponent.getPreferredSize(acomponent[j]).height);

        return i + JCComponent.getInsets(this).top + JCComponent.getInsets(this).bottom;
    }

    protected int preferredWidth()
    {
        JCComponent jccomponent;
        if((jccomponent = (JCComponent)comp) == null)
            return data.preferredWidth();
        else
            return data.preferredWidth() + 2 * jccomponent.getBorderThickness() + 2 * jccomponent.getHighlightThickness() + JCComponent.getInsets(this).left + JCComponent.getInsets(this).right;
    }

    public void recalc()
    {
        data.calcColumnWidths();
        resize(JCComponent.getPreferredSize(this).width, size().height);
        layout();
        repaint();
    }

    public void removeActionListener(JCActionListener jcactionlistener)
    {
        Component acomponent[] = getComponents();
        for(int i = 0; i < acomponent.length; i++)
            if(acomponent[i] instanceof JCButton)
                ((JCButton)acomponent[i]).removeActionListener(jcactionlistener);

        actionListeners.removeElement(jcactionlistener);
    }

    public void setButtons(JCVector jcvector)
    {
        removeAll();
        if(!num_columns_set)
            data.setNumColumns(0);
        update_parent = false;
        for(int i = 0; jcvector != null && i < jcvector.size(); i++)
            addButton(jcvector.elementAt(i));

        update_parent = true;
        recalc();
        updateParent();
    }

    public void setColumnAlignment(int i, int j)
    {
        data.setColumnAlignment(i, j);
        if(i < countComponents())
            ((JCLabel)getComponent(i)).setAlignment(j);
        repaint();
    }

    public void setColumnAlignments(int ai[])
    {
        data.setColumnAlignments(ai);
        layout();
        repaint();
    }

    public void setColumnButtons(JCVector jcvector)
    {
        setButtons(jcvector);
    }

    public void setColumnDisplayWidth(int i, int j)
    {
        int l = j - data.getColumnDisplayWidth(i);
        data.setColumnDisplayWidth(i, j);
        if(getParent() == null)
            return;
        int k;
        if(getParent() instanceof Viewport)
            k = Math.max(size().width + l, getParent().size().width);
        else
            k = JCComponent.getPreferredSize(this).width;
        resize(k, size().height);
        updateParent();
        repaint();
    }

    public void setColumnDisplayWidths(int ai[])
    {
        data.setColumnDisplayWidths(ai);
        layout();
        resize(JCComponent.getPreferredSize(this).width, size().height);
        repaint();
    }

    public void setColumnLabels(JCVector jcvector)
    {
        setLabels(jcvector);
    }

    public void setColumnLeftMargin(int i, int j)
    {
        int k = getColumnLeftMargin(i);
        data.setColumnLeftMargin(i, j);
        if(getParent() == null || i >= getNumColumns())
        {
            return;
        } else
        {
            resize((size().width + j) - k, size().height);
            updateParent();
            return;
        }
    }

    public void setColumnResizePolicy(int i)
    {
        data.setColumnResizePolicy(i);
    }

    public void setColumnRightMargin(int i, int j)
    {
        int k = getColumnRightMargin(i);
        data.setColumnRightMargin(i, j);
        if(getParent() == null || i >= getNumColumns())
        {
            return;
        } else
        {
            resize((size().width + j) - k, size().height);
            updateParent();
            return;
        }
    }

    public void setColumnWidth(int i, int j)
    {
        int l = j - data.getColumnWidth(i);
        data.setColumnWidth(i, j);
        if(getParent() == null)
            return;
        int k;
        if(getParent() instanceof Viewport)
            k = Math.max(size().width + l, getParent().size().width);
        else
            k = JCComponent.getPreferredSize(this).width;
        resize(k, size().height);
        updateParent();
        repaint();
    }

    public void setColumnWidths(int ai[])
    {
        data.setColumnWidths(ai);
        layout();
        resize(JCComponent.getPreferredSize(this).width, size().height);
        repaint();
    }

    public void setFont(Font font)
    {
        super.setFont(font);
        if(getPeer() == null)
            return;
        Component acomponent[] = getComponents();
        for(int i = 0; i < acomponent.length; i++)
            ((JCLabel)acomponent[i]).setFontInternal(font);

        data.calcColumnWidths();
        resize(JCComponent.getPreferredSize(this).width, size().height);
        layout();
        repaint();
    }

    public void setLabels(JCVector jcvector)
    {
        removeAll();
        if(!num_columns_set)
            data.setNumColumns(0);
        update_parent = false;
        for(int i = 0; jcvector != null && i < jcvector.size(); i++)
            addLabel(jcvector.elementAt(i));

        update_parent = true;
        recalc();
        updateParent();
    }

    public final void setLayout(LayoutManager layoutmanager)
    {
    }

    public void setMultiColumnComponent(JCMultiColumnInterface jcmulticolumninterface)
    {
        if(comp == jcmulticolumninterface)
            return;
        comp = jcmulticolumninterface;
        if(jcmulticolumninterface instanceof JCComponent)
            super.insets = new Insets(0, ((JCComponent)jcmulticolumninterface).getInsets().left, 0, ((JCComponent)jcmulticolumninterface).getInsets().right);
        int ai[] = jcmulticolumninterface.getColumnWidths();
        int ai1[] = jcmulticolumninterface.getColumnDisplayWidths();
        for(int i = 0; i < jcmulticolumninterface.getNumColumns(); i++)
        {
            data.setColumnAlignment(i, jcmulticolumninterface.getColumnAlignment(i));
            data.setColumnRightMargin(i, jcmulticolumninterface.getColumnRightMargin(i));
            data.setColumnLeftMargin(i, jcmulticolumninterface.getColumnLeftMargin(i));
            if(i < ai.length)
                data.setColumnWidth(i, ai[i]);
            if(i < ai1.length)
                data.setColumnDisplayWidth(i, ai1[i]);
        }

        layout();
        repaint();
    }

    public void setNumColumns(int i)
    {
        data.setNumColumns(i == -998 ? countComponents() : i);
        num_columns_set = i != -998;
        data.calcColumnWidths();
        if(!batched)
        {
            resize(JCComponent.getPreferredSize(this).width, size().height);
            updateParent();
        }
    }

    public void updateParent()
    {
        if(update_parent && getPeer() != null)
        {
            data.calcColumnWidths();
            invalidate();
            Container container = getParent();
            if(container instanceof Viewport)
                container = container.getParent();
            container.invalidate();
            container.validate();
        }
    }

    protected JCMultiColumnData data;
    protected JCMultiColumnInterface comp;
    protected int resize_col;
    protected boolean resizing;
    boolean num_columns_set;
    boolean update_parent;
    boolean batched;
    protected JCVector actionListeners;
    private static final String base = "header";
    private static int nameCounter = 0;
    private static final boolean TRACE = false;
    private int mouse_down_x;
    private static int x_save = -999;

}
