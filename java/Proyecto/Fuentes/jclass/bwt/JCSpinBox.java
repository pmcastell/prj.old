// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JCSpinBox.java

package jclass.bwt;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.event.FocusListener;
import java.awt.event.KeyListener;
import java.util.EventObject;
import java.util.Vector;
import javax.swing.border.Border;
import jclass.base.BaseComponent;
import jclass.base.TransientComponent;
import jclass.util.JCVector;

// Referenced classes of package jclass.bwt:
//            JCContainer, BWTEnum, JCActionListener, JCArrowButton, 
//            JCButton, JCComponent, JCSpinBoxEvent, JCSpinBoxListener, 
//            JCTextComponent, JCTextField, JCTextInterface, SpinArrowButton, 
//            SpinBoxConverter, SpinField, JCActionEvent, JCTextCursorListener, 
//            JCTextListener

public class JCSpinBox extends JCContainer
    implements JCTextInterface, JCActionListener
{

    public JCSpinBox()
    {
        this(null, null);
    }

    public JCSpinBox(int i)
    {
        this(null, null);
        text.setColumns(i);
    }

    public JCSpinBox(Applet applet, String s)
    {
        super(applet, s);
        position = 0;
        min = 0;
        max = 0x7fffffff;
        incr = 1;
        decimals = 0;
        auto_arrow_disable = true;
        border = 2;
        pow = (int)Math.pow(10D, decimals);
        listeners = new JCVector(0);
        if(s == null)
            setName("spinbox" + nameCounter++);
        if(getClass().getName().equals("jclass.bwt.JCSpinBox"))
            getParameters(applet);
        setLayout(null);
        add(text = new SpinField(this));
        add(incr_arrow = new SpinArrowButton(10, this));
        add(decr_arrow = new SpinArrowButton(9, this));
        incr_arrow.initial_delay = decr_arrow.initial_delay = 250;
        text.addActionListener(this);
        incr_arrow.setTraversable(false);
        decr_arrow.setTraversable(false);
        text.setBorderThickness(0);
        incr_arrow.addActionListener(this);
        decr_arrow.addActionListener(this);
        initTextValue();
    }

    public void actionPerformed(JCActionEvent jcactionevent)
    {
        if(jcactionevent.getSource() == incr_arrow)
            setTextAction(1);
        else
        if(jcactionevent.getSource() == decr_arrow)
            setTextAction(-1);
        else
        if(jcactionevent.getSource() == text && text.getEditable())
            setTextAction(0);
    }

    public void addFocusListener(FocusListener focuslistener)
    {
        if(text != null)
            text.addFocusListener(focuslistener);
    }

    public void addKeyListener(KeyListener keylistener)
    {
        if(text != null)
            text.addKeyListener(keylistener);
    }

    public void addSpinBoxListener(JCSpinBoxListener jcspinboxlistener)
    {
        listeners.addUnique(jcspinboxlistener);
    }

    public void addTextCursorListener(JCTextCursorListener jctextcursorlistener)
    {
        text.addTextCursorListener(jctextcursorlistener);
    }

    public void addTextListener(JCTextListener jctextlistener)
    {
        text.addTextListener(jctextlistener);
    }

    public void append(String s)
    {
        text.append(s);
    }

    public void beep()
    {
        text.beep();
    }

    protected Object calcValue(int i)
    {
        int j = i != 0 ? i != 1 ? -incr : incr : 0;
        if(value == null)
            value = new Integer(min);
        int k = ((Integer)value).intValue() + j;
        k = Math.max(Math.min(k, max), min);
        position = (k - min) / incr;
        return new Integer(k);
    }

    protected void enableArrowButtons()
    {
        if(!auto_arrow_disable)
        {
            incr_arrow.enable(true);
            decr_arrow.enable(true);
        } else
        {
            int i = ((Integer)value).intValue();
            incr_arrow.enable(i < max);
            decr_arrow.enable(i > min);
        }
    }

    public int getAlignment()
    {
        return text.getAlignment();
    }

    public boolean getAutoArrowDisable()
    {
        return auto_arrow_disable;
    }

    public boolean getChanged()
    {
        return text.getChanged();
    }

    public int getColumns()
    {
        return text.getColumns();
    }

    public int getCursorPosition()
    {
        return text.getCursorPosition();
    }

    public int getDecimalPlaces()
    {
        return decimals;
    }

    protected char getDecimalPointChar()
    {
        if(decimal_point_char != 0)
            return decimal_point_char;
        else
            return decimal_point_char = (new Double(5.5D)).toString().charAt(1);
    }

    public JCArrowButton getDecrementArrow()
    {
        return decr_arrow;
    }

    public boolean getEditable()
    {
        return text.getEditable();
    }

    public Color getHighlightColor()
    {
        return text.getHighlightColor();
    }

    public int getHighlightThickness()
    {
        return text.getHighlightThickness();
    }

    public int getIncrement()
    {
        return incr;
    }

    public JCArrowButton getIncrementArrow()
    {
        return incr_arrow;
    }

    public int getIntValue()
    {
        if(value instanceof Integer)
            return ((Integer)value).intValue();
        else
            return Integer.parseInt(text.getText());
    }

    public int getLastPosition()
    {
        return text.getLastPosition();
    }

    public int getMaximum()
    {
        return max;
    }

    public int getMaximumLength()
    {
        return text.getMaximumLength();
    }

    public int getMinimum()
    {
        return min;
    }

    public Dimension getMinimumSize(int i)
    {
        return text.getMinimumSize(i);
    }

    public boolean getOverstrike()
    {
        return text.getOverstrike();
    }

    protected void getParameters()
    {
        super.getParameters();
        SpinBoxConverter.getParams(this);
    }

    public int getPosition()
    {
        return position;
    }

    public Color getSelectedBackground()
    {
        return text.getSelectedBackground();
    }

    public Color getSelectedForeground()
    {
        return text.getSelectedForeground();
    }

    public String getSelectedText()
    {
        return text.getSelectedText();
    }

    public int getSelectionEnd()
    {
        return text.getSelectionEnd();
    }

    public int[] getSelectionList()
    {
        return text.getSelectionList();
    }

    public int getSelectionStart()
    {
        return text.getSelectionStart();
    }

    public int getShadowThickness()
    {
        return text.getBorderThickness();
    }

    public boolean getShowCursorPosition()
    {
        return text.getShowCursorPosition();
    }

    public int getStringCase()
    {
        return text.getStringCase();
    }

    public synchronized String getText()
    {
        return text.getText();
    }

    public JCTextField getTextField()
    {
        return text;
    }

    public String getValue()
    {
        return text.getText();
    }

    protected void initTextValue()
    {
        value = new Integer(min);
        position = 0;
        setTextValue(value);
    }

    public void insert(String s, int i)
    {
        text.insert(s, i);
    }

    public boolean isEditable()
    {
        return text.isEditable();
    }

    public synchronized void layout()
    {
        int i = JCComponent.getPreferredSize(text).height;
        int j = i / 2;
        int k = JCComponent.getPreferredSize(incr_arrow).width;
        int l = size().width - k - border;
        int i1 = i % 2;
        JCComponent.setBounds(text, border, border, size().width - k - 2 * border, j * 2);
        JCComponent.setBounds(incr_arrow, l, border, k, j);
        JCComponent.setBounds(decr_arrow, l, j + border, k, j + i1);
        enableArrowButtons();
    }

    public void paintInterior(Graphics g)
    {
        g.clearRect(0, 0, size().width, size().height);
        if(super.swing_border != null)
            super.swing_border.paintBorder(this, g, 0, 0, size().width, size().height);
        else
            jclass.base.Border.draw(g, 3, border, 0, 0, size().width, size().height, getBackground(), getForeground());
    }

    public int pointToPosition(int i, int j)
    {
        return text.pointToPosition(i, j);
    }

    protected JCSpinBoxEvent postSpinBoxEvent(int i, Object obj)
    {
        if(listeners.size() == 0)
            return null;
        JCSpinBoxEvent jcspinboxevent = new JCSpinBoxEvent(this, position, obj);
        for(int j = 0; j < listeners.size(); j++)
        {
            JCSpinBoxListener jcspinboxlistener = (JCSpinBoxListener)listeners.elementAt(j);
            if(i == 0)
                jcspinboxlistener.spinBoxChangeBegin(jcspinboxevent);
            else
                jcspinboxlistener.spinBoxChangeEnd(jcspinboxevent);
        }

        return jcspinboxevent;
    }

    protected int preferredHeight()
    {
        return 2 * border + JCComponent.getPreferredSize(text).height;
    }

    protected int preferredWidth()
    {
        return JCComponent.getPreferredSize(text).width + JCComponent.getPreferredSize(incr_arrow).width + 2 * border;
    }

    public void removeFocusListener(FocusListener focuslistener)
    {
        if(text != null)
            text.removeFocusListener(focuslistener);
    }

    public void removeKeyListener(KeyListener keylistener)
    {
        if(text != null)
            text.removeKeyListener(keylistener);
    }

    public void removeSpinBoxListener(JCSpinBoxListener jcspinboxlistener)
    {
        listeners.removeElement(jcspinboxlistener);
    }

    public void removeTextCursorListener(JCTextCursorListener jctextcursorlistener)
    {
        text.removeTextCursorListener(jctextcursorlistener);
    }

    public void removeTextListener(JCTextListener jctextlistener)
    {
        text.removeTextListener(jctextlistener);
    }

    public void replaceRange(String s, int i, int j)
    {
        text.replaceRange(s, i, j);
    }

    public void select(int i, int j)
    {
        text.select(i, j);
    }

    public void selectAll()
    {
        text.selectAll();
    }

    public void setAlignment(int i)
    {
        text.setAlignment(i);
    }

    public void setAutoArrowDisable(boolean flag)
    {
        auto_arrow_disable = flag;
        enableArrowButtons();
    }

    public void setBackground(Color color)
    {
        super.setBackground(color);
        decr_arrow.setBackground(color);
        incr_arrow.setBackground(color);
    }

    public void setBorder(Border border1)
    {
        super.swing_border = border1;
        setChildrenBorder(border1);
    }

    public void setChanged(boolean flag)
    {
        text.setChanged(flag);
    }

    public void setColumns(int i)
    {
        text.setColumns(i);
    }

    public void setCursorPosition(int i)
    {
        text.setCursorPosition(i);
    }

    public void setDecimalPlaces(int i)
    {
        decimals = i;
        pow = (int)Math.pow(10D, i);
        setTextValue(value);
    }

    public void setEditable(boolean flag)
    {
        text.setEditable(flag);
    }

    public void setForeground(Color color)
    {
        super.setForeground(color);
        decr_arrow.setForeground(color);
        incr_arrow.setForeground(color);
    }

    public void setHighlightColor(Color color)
    {
        text.setHighlightColor(color);
    }

    public void setHighlightThickness(int i)
    {
        text.setHighlightThickness(i);
    }

    public void setIncrement(int i)
    {
        incr = i;
    }

    public void setIntValue(int i)
    {
        value = new Integer(i);
        setTextValue(value);
        enableArrowButtons();
    }

    public final void setLayout(LayoutManager layoutmanager)
    {
    }

    public void setMaximum(int i)
    {
        max = i;
        validate(value);
    }

    public void setMaximumLength(int i)
    {
        text.setMaximumLength(i);
    }

    public void setMinimum(int i)
    {
        min = i;
        validate(value);
    }

    public void setOverstrike(boolean flag)
    {
        text.setOverstrike(flag);
    }

    public void setPosition(int i)
    {
        position = i;
        value = new Integer((position - min) / incr + min);
        setTextValue(value);
        enableArrowButtons();
    }

    public void setSelectedBackground(Color color)
    {
        text.setSelectedBackground(color);
    }

    public void setSelectedForeground(Color color)
    {
        text.setSelectedForeground(color);
    }

    public void setSelectionEnd(int i)
    {
        text.setSelectionEnd(i);
    }

    public void setSelectionList(int ai[])
    {
        text.setSelectionList(ai);
    }

    public void setSelectionStart(int i)
    {
        text.setSelectionStart(i);
    }

    public void setShadowThickness(int i)
    {
        text.setBorderThickness(i);
    }

    public void setShowCursorPosition(boolean flag)
    {
        text.setShowCursorPosition(flag);
    }

    public void setStringCase(int i)
    {
        text.setStringCase(i);
    }

    public synchronized void setText(String s)
    {
        if(s == null || s.length() == 0)
        {
            setIntValue(min);
        } else
        {
            setTextValue(s);
            enableArrowButtons();
        }
    }

    public void setTextAction(int i)
    {
        if(text.getChanged() && !validate(text.getText()))
        {
            text.beep();
            return;
        }
        Object obj = calcValue(i);
        JCSpinBoxEvent jcspinboxevent = postSpinBoxEvent(0, obj);
        if(jcspinboxevent != null)
        {
            if(!jcspinboxevent.doit)
                return;
            if(obj != jcspinboxevent.value && !validate(jcspinboxevent.value))
                return;
            obj = jcspinboxevent.value;
        }
        value = obj;
        setTextValue(value);
        enableArrowButtons();
    }

    protected void setTextValue(Object obj)
    {
        String s = obj == null ? null : obj.toString();
        if(decimals > 0)
        {
            s = (new Double(((Integer)obj).doubleValue() / (double)pow)).toString();
            int i = s.indexOf(getDecimalPointChar());
            if(i == -1)
            {
                s = s + getDecimalPointChar();
                i = s.length() - 1;
            }
            for(int j = s.length() - i - 1; j < decimals; j++)
                s = s + '0';

        }
        text.setText(s);
        postSpinBoxEvent(1, obj);
    }

    public void showPosition(int i)
    {
        text.showPosition(i);
    }

    protected boolean validate(Object obj)
    {
        boolean flag = false;
        char c = getDecimalPointChar();
        if(obj instanceof String)
        {
            String s = (String)obj;
            try
            {
                int j;
                if(decimals == 0)
                {
                    j = Integer.parseInt(s);
                } else
                {
                    if(s.indexOf(c) == 0)
                        s = s + '.';
                    double d1 = Double.valueOf(s).doubleValue();
                    j = (int)Math.ceil(d1 * (double)pow);
                }
                if(flag = j >= min && j <= max)
                {
                    position = (j - min) / incr;
                    value = new Integer(j);
                }
            }
            catch(Exception _ex)
            {
                flag = false;
            }
        } else
        if(obj instanceof Integer)
        {
            int i = ((Integer)obj).intValue();
            if(flag = i >= min && i <= max)
                position = (i - min) / incr;
        } else
        if(obj instanceof Number)
        {
            double d = ((Double)obj).doubleValue();
            if(flag = d >= (double)min && d <= (double)max)
                position = ((int)d - min) / incr;
        }
        return flag;
    }

    protected boolean validateKey(char c)
    {
        if(decimals == 0)
            return Character.isDigit(c) || c == '-' && text.getText().indexOf('-') == -1;
        else
            return Character.isDigit(c) || c == '.' && text.getText().indexOf('.') == -1 || c == '-' && text.getText().indexOf('-') == -1;
    }

    protected SpinField text;
    protected SpinArrowButton decr_arrow;
    protected SpinArrowButton incr_arrow;
    protected int position;
    protected int min;
    protected int max;
    protected int incr;
    protected int decimals;
    protected Object value;
    protected boolean auto_arrow_disable;
    protected int border;
    protected static final int NONE = 0;
    protected static final int INCREMENT = 1;
    protected static final int DECREMENT = -1;
    static final int BEGIN = 0;
    static final int END = 1;
    private int pow;
    private char decimal_point_char;
    protected JCVector listeners;
    private static final String base = "spinbox";
    private static int nameCounter = 0;

}
