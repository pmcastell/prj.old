// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JCTextComponent.java

package jclass.bwt;

import java.applet.Applet;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import jclass.base.*;
import jclass.bwt.resources.LocaleInfo;
import jclass.cell.*;
import jclass.util.JCEnvironment;
import jclass.util.JCVector;

// Referenced classes of package jclass.bwt:
//            JCComponent, BWTEnum, BWTUtil, JCTextCursorEvent, 
//            JCTextCursorListener, JCTextEvent, JCTextInterface, JCTextListener, 
//            LabelConverter, TextComponentConverter

public abstract class JCTextComponent extends JCComponent
    implements JCTextInterface, Runnable, CellEditor, CellRenderer
{
    public class Cut
        implements ActionListener, Serializable
    {

        public void actionPerformed(ActionEvent actionevent)
        {
            cutToClipboard(null);
        }

        public Cut()
        {
        }
    }

    public class Copy
        implements ActionListener, Serializable
    {

        public void actionPerformed(ActionEvent actionevent)
        {
            copyToClipboard(null);
        }

        public Copy()
        {
        }
    }

    public class Paste
        implements ActionListener, Serializable
    {

        public void actionPerformed(ActionEvent actionevent)
        {
            pasteFromClipboard(null);
        }

        public Paste()
        {
        }
    }

    public class Delete
        implements ActionListener, Serializable
    {

        public void actionPerformed(ActionEvent actionevent)
        {
            deleteSelection();
        }

        public Delete()
        {
        }
    }

    public class SelectAll
        implements ActionListener, Serializable
    {

        public void actionPerformed(ActionEvent actionevent)
        {
            selectAll();
        }

        public SelectAll()
        {
        }
    }


    public JCTextComponent()
    {
        this(null, null);
    }

    public JCTextComponent(Applet applet, String s)
    {
        super(applet, s);
        li = null;
        popup_menu = null;
        is_cell_editor = false;
        cursor_state = false;
        cursor_visible = true;
        paint_cursor = true;
        display_cursor = true;
        show_cursor = true;
        disabled_repaint_count = 0;
        action_count = 0;
        needs_repaint = false;
        do_repaint = true;
        max_length = 0x7fffffff;
        columns = 20;
        text = new char[4];
        alignment = 0;
        cursorListeners = new JCVector(0);
        valueListeners = new JCVector(0);
        cell_editor_support = new CellEditorSupport();
        editable = true;
        overstrike = false;
        changed = false;
        string_case = 0;
        cursor_blink_rate = 500;
        ignore_lose_focus = false;
        ignore_select_extensions = false;
        rect = new Rectangle();
        last_state = false;
        last_overstrike = false;
        last_x = -999;
        first = true;
        pasting = false;
        if(getClass().getName().equals("jclass.bwt.JCTextComponent"))
            getParameters(applet);
        super.border_style = 8;
        if(BaseComponent.use_system_colors)
        {
            setBackground(SystemColor.text);
            setForeground(SystemColor.textText);
            selected_bg = SystemColor.textHighlight;
            selected_fg = SystemColor.textHighlightText;
        }
        li = ResourceBundle.getBundle("jclass.bwt.resources.LocaleInfo", Locale.getDefault());
    }

    public void addCellEditorListener(CellEditorListener celleditorlistener)
    {
        cell_editor_support.addCellEditorListener(celleditorlistener);
    }

    public void addNotify()
    {
        if(getPeer() == null)
        {
            super.addNotify();
            setFm();
        }
        window = BWTUtil.getWindow(this);
        if(window instanceof Dialog)
            dialog_bug = true;
        enableEvents(32L);
    }

    public void addTextCursorListener(JCTextCursorListener jctextcursorlistener)
    {
        cursorListeners.addUnique(jctextcursorlistener);
    }

    public void addTextListener(JCTextListener jctextlistener)
    {
        valueListeners.addUnique(jctextlistener);
    }

    public void append(String s)
    {
        select(num_char);
        replaceRange(s, num_char, num_char);
    }

    public void beep()
    {
        getToolkit().beep();
    }

    protected void blinkCursor(boolean flag)
    {
        if(!super.has_focus || !display_cursor || !isEnabled())
            return;
        if(flag)
        {
            if(cursor_thread != null && cursor_thread.isAlive())
                return;
            cursor_thread = new Thread(this);
            if(JCEnvironment.getBrowser(this) != 3)
                cursor_thread.setPriority(1);
            if(cursor_thread != null && !cursor_thread.isAlive())
                cursor_thread.start();
        } else
        if(cursor_thread != null && cursor_thread.isAlive())
            try
            {
                Thread.yield();
                cursor_thread.stop();
                paintCursor(false);
                cursor_thread = null;
            }
            catch(Exception _ex) { }
    }

    public void cancelCellEditing()
    {
    }

    protected void cancelSelection()
    {
        if(select_start >= select_end)
        {
            return;
        } else
        {
            int i = select_start;
            int j = select_end;
            select_start = select_end = cursor_pos;
            repaintPositions(i, j);
            return;
        }
    }

    protected int checkCursorPosition(int i)
    {
        return i >= 0 ? i <= num_char ? i : num_char : 0;
    }

    protected boolean cmpKeyStroke(String s, int i)
    {
        Integer integer = (Integer)li.getObject(s);
        if(integer == null)
            return false;
        return integer.intValue() == i;
    }

    public void containedInitialize(InitialEvent initialevent, CellInfo cellinfo, Object obj)
    {
        is_cell_editor = true;
        ignore_lose_focus = true;
        String s = (String)obj;
        setHighlightThickness(0);
        setAlignment(cellinfo.getHorizontalAlignment());
        setBackground(cellinfo.getBackground());
        setForeground(cellinfo.getForeground());
        setSelectedBackground(cellinfo.getSelectedBackground());
        setSelectedForeground(cellinfo.getSelectedForeground());
        setText(s);
        if(initialevent.getEventType() == 1)
            ignore_select_extensions = true;
        if(cellinfo.getSelectAll())
            selectAll();
        else
        if(initialevent.getEventType() == 1)
            setCursorPosition(initialevent, cellinfo, s);
        if(initialevent.getEventType() == 2)
            insertChar(null, initialevent.getKey());
    }

    public void copyToClipboard(Event event)
    {
        if(select_start < select_end)
            writeClipboard(getText().substring(select_start, select_end));
    }

    private static final JMenuItem createMenuItem(String s)
    {
        return new JMenuItem(s);
    }

    protected void createPopup(Component component, int i, int j)
    {
        if(popup_menu != null)
            remove(popup_menu);
        popup_menu = new JPopupMenu();
        boolean flag = false;
        boolean flag1 = false;
        if(select_start == 0 && select_end + 1 >= num_char && num_char != 0)
            flag = true;
        if(select_start == select_end || num_char == 0)
            flag1 = true;
        JMenuItem jmenuitem = createMenuItem(li.getString("Cut"));
        if(flag1 || !isEditable())
            jmenuitem.setEnabled(false);
        else
            jmenuitem.addActionListener(new Cut());
        popup_menu.add(jmenuitem);
        jmenuitem = createMenuItem(li.getString("Copy"));
        if(flag1)
            jmenuitem.setEnabled(false);
        else
            jmenuitem.addActionListener(new Copy());
        popup_menu.add(jmenuitem);
        jmenuitem = createMenuItem(li.getString("Paste"));
        if(!isEditable())
            jmenuitem.setEnabled(false);
        else
            jmenuitem.addActionListener(new Paste());
        popup_menu.add(jmenuitem);
        jmenuitem = createMenuItem(li.getString("Delete"));
        if(flag1 || !isEditable())
            jmenuitem.setEnabled(false);
        else
            jmenuitem.addActionListener(new Delete());
        popup_menu.add(jmenuitem);
        popup_menu.addSeparator();
        jmenuitem = createMenuItem(li.getString("SelectAll"));
        if(flag || num_char == 0)
            jmenuitem.setEnabled(false);
        else
            jmenuitem.addActionListener(new SelectAll());
        popup_menu.add(jmenuitem);
        add(popup_menu);
        popup_menu.show(component, i, j);
    }

    public void cutToClipboard(Event event)
    {
        if(select_start < select_end)
        {
            copyToClipboard(event);
            deleteSelection();
        }
    }

    protected boolean deleteChar(Event event, boolean flag)
    {
        if(!editable)
            return false;
        boolean flag1 = false;
        startAction(event);
        if(select_start < select_end)
            flag1 = deleteRange(select_start, select_end);
        else
        if(!flag && cursor_pos > 0)
            flag1 = deleteRange(cursor_pos - 1, cursor_pos);
        else
        if(flag && cursor_pos < num_char)
            flag1 = deleteRange(cursor_pos, cursor_pos + 1);
        endAction();
        return true;
    }

    protected boolean deleteCurrentChar(Event event)
    {
        return deleteChar(event, true);
    }

    protected boolean deletePreviousChar(Event event)
    {
        return deleteChar(event, false);
    }

    protected boolean deleteRange(int i, int j)
    {
        if(i >= j)
            return true;
        String s = "";
        boolean flag = replaceRangeInternal(s, i, j);
        if(flag)
            changed = true;
        return flag;
    }

    protected boolean deleteSelection()
    {
        return deleteRange(select_start, select_end);
    }

    public synchronized void disable()
    {
        if(isEnabled())
        {
            blinkCursor(false);
            super.disable();
        }
    }

    public void draw(Graphics g, CellInfo cellinfo, Object obj, boolean flag)
    {
        g.setFont(cellinfo.getFont());
        g.setColor(flag ? cellinfo.getSelectedForeground() : cellinfo.getForeground());
        Rectangle rectangle = cellinfo.getDrawingArea();
        Dimension dimension = getPreferredSize(cellinfo, obj);
        int i = cellinfo.getVerticalAlignment();
        int j = 0;
        if(i == 1)
            j = (rectangle.height - dimension.height) / 2;
        else
        if(i == 2)
            j = rectangle.height - dimension.height;
        if(j != 0)
            g.translate(0, j);
        paintComponent(g, 0, (String)obj, cellinfo.getDrawingArea(), cellinfo.getHorizontalAlignment(), cellinfo.getFontMetrics(), true, cellinfo.getForeground(), cellinfo.getBackground(), cellinfo.getSelectedForeground(), cellinfo.getSelectedBackground(), 0, 0);
        if(j != 0)
            g.translate(0, -j);
        if((dimension.width > rectangle.width || dimension.height > rectangle.height) && cellinfo.getClipHints() != 0)
            Utilities.drawClipArrows(g, cellinfo, dimension, 4, true);
    }

    void endAction()
    {
        synchronized(this)
        {
            if(action_count > 0)
            {
                action_count--;
                if(action_count > 0)
                    return;
            }
            paint_cursor = true;
            last_event = null;
            unfreeze();
        }
        if(needs_repaint)
            repaint();
        updateScrollbars();
        if(show_cursor)
            showPosition(cursor_pos);
        paintCursor(true);
        blinkCursor(true);
    }

    protected void freeze()
    {
        disabled_repaint_count++;
    }

    public int getAlignment()
    {
        return alignment;
    }

    public int getCaretPosition()
    {
        return getCursorPosition();
    }

    public CellEditorSupport getCellEditorSupport()
    {
        return cell_editor_support;
    }

    public Object getCellEditorValue()
    {
        return getText();
    }

    public boolean getChanged()
    {
        return changed;
    }

    public int getColumns()
    {
        return columns;
    }

    public Component getComponent()
    {
        return this;
    }

    public int getCursorPosition()
    {
        return cursor_pos;
    }

    public boolean getEditable()
    {
        return editable;
    }

    public int getLastPosition()
    {
        return num_char;
    }

    public int getMaximumLength()
    {
        return max_length;
    }

    public abstract Dimension getMinimumSize(int i);

    public int getNumChar()
    {
        return num_char;
    }

    public boolean getOverstrike()
    {
        return overstrike;
    }

    protected void getParameters()
    {
        super.getParameters();
        TextComponentConverter.getParams(this);
    }

    public abstract Dimension getPreferredSize(CellInfo cellinfo, Object obj);

    public abstract KeyModifier[] getReservedKeys();

    public Color getSelectedBackground()
    {
        return selected_bg == null ? getForeground() : selected_bg;
    }

    public Color getSelectedForeground()
    {
        return selected_fg == null ? getBackground() : selected_fg;
    }

    public synchronized String getSelectedText()
    {
        return new String(text, select_start, select_end - select_start);
    }

    public int getSelectionEnd()
    {
        return select_end;
    }

    public int[] getSelectionList()
    {
        return select_list;
    }

    public int getSelectionStart()
    {
        return select_start;
    }

    protected int getSelectionType(int i)
    {
        int j = Math.max(0, Math.min(select_list.length - 1, i - 1));
        return select_list[j];
    }

    public boolean getShowCursorPosition()
    {
        return display_cursor;
    }

    public int getStringCase()
    {
        return string_case;
    }

    protected static String getStringFromTransferable(Transferable transferable)
    {
        if(transferable == null)
            return null;
        String s = null;
        if(transferable.isDataFlavorSupported(DataFlavor.stringFlavor))
            try
            {
                s = (String)transferable.getTransferData(DataFlavor.stringFlavor);
            }
            catch(Exception _ex)
            {
                s = null;
            }
        else
        if(transferable.isDataFlavorSupported(DataFlavor.plainTextFlavor))
            try
            {
                InputStream inputstream = (InputStream)transferable.getTransferData(DataFlavor.plainTextFlavor);
                int i = inputstream.available();
                if(i > 0)
                {
                    byte abyte0[] = new byte[i];
                    if(i == inputstream.read(abyte0))
                        s = new String(abyte0, 0, abyte0.length, "unicode");
                }
            }
            catch(Exception _ex)
            {
                s = null;
            }
        return s;
    }

    public synchronized String getText()
    {
        return new String(text, 0, num_char);
    }

    char[] getTextChars()
    {
        return text;
    }

    public boolean gotFocus102(Event event, Object obj)
    {
        boolean flag = super.gotFocus102(event, obj);
        setFm();
        if(flag)
            blinkCursor(true);
        return flag;
    }

    public void initialize(InitialEvent initialevent, CellInfo cellinfo, Object obj)
    {
        containedInitialize(initialevent, cellinfo, obj);
        setInsets(cellinfo.getMarginInsets());
        setShadowThickness(cellinfo.getBorderInsets().top);
        if(cellinfo.isEditable())
            setEditable(true);
        else
            setEditable(false);
    }

    public synchronized void insert(String s, int i)
    {
        replaceRange(s, i, i);
    }

    protected boolean insertChar(Event event, int i)
    {
        if(!editable)
            return false;
        startAction(event);
        char ac[] = {
            (char)i
        };
        int j = cursor_pos;
        int k = cursor_pos;
        if(select_end > select_start)
        {
            j = select_start;
            k = select_end;
        }
        if(overstrike && j == k && k < num_char)
            k++;
        if(replaceRangeInternal(new String(ac), j, k))
            changed = true;
        endAction();
        return true;
    }

    public boolean isEditable()
    {
        return editable;
    }

    protected boolean isFrozen()
    {
        return disabled_repaint_count != 0;
    }

    protected boolean isLineDelimiter(char c)
    {
        return c == '\n' || c == '\r';
    }

    public boolean isModified()
    {
        return getChanged();
    }

    public boolean isPositionSelected(int i)
    {
        return i >= select_start && i < select_end;
    }

    protected boolean isWordDelimiter(char c)
    {
        return Character.isJavaLetterOrDigit(c) ^ true;
    }

    public boolean keyDown(Event event, int i)
    {
        boolean flag = true;
        boolean flag1 = true;
        last_event = event;
        if(event.controlDown())
        {
            if(cmpKeyStroke("HomeKey", i))
                scrollLineBegin(event);
            else
            if(cmpKeyStroke("CopyKey", i))
                copyToClipboard(event);
            else
            if(cmpKeyStroke("DeleteKey", i))
                flag = deleteCurrentChar(event);
            else
            if(cmpKeyStroke("EndKey", i))
                scrollLineEnd(event);
            else
            if(cmpKeyStroke("BackSpaceKey", i))
                flag = deletePreviousChar(event);
            else
            if(cmpKeyStroke("OverStrikeKey", i))
                toggleOverstrike(event);
            else
            if(cmpKeyStroke("CutKey", i))
                cutToClipboard(event);
            else
            if(cmpKeyStroke("PasteKey", i))
                flag = pasteFromClipboard(event);
            else
            if(i == 1000)
            {
                paintCursor(false);
                scrollHome(event);
            } else
            if(i == 1001)
            {
                paintCursor(false);
                scrollEnd(event);
            } else
            {
                flag1 = false;
            }
        } else
        if(i == 1007)
            moveForwardChar(event);
        else
        if(i == 1006)
            moveBackwardChar(event);
        else
        if(i == 8)
            flag = deletePreviousChar(event);
        else
        if(i == 127)
            flag = deleteCurrentChar(event);
        else
        if(i == 1000)
        {
            paintCursor(false);
            scrollLineBegin(event);
        } else
        if(i == 1001)
        {
            paintCursor(false);
            scrollLineEnd(event);
        } else
        if(i == 9)
            flag1 = false;
        else
        if(i == 27)
        {
            flag = true;
            cell_editor_support.fireCancelEditing(new CellEditorEvent(new AWTEvent(this, 0) {

            }));
        } else
        if(event.id != 403)
            flag = insertChar(event, i);
        else
            flag1 = false;
        if(!flag)
            beep();
        last_event = null;
        return flag1 ? true : super.keyDown(event, i);
    }

    int leftMargin()
    {
        return getDrawingArea().x;
    }

    public boolean lostFocus102(Event event, Object obj)
    {
        blinkCursor(false);
        if(!ignore_lose_focus)
            cancelSelection();
        return super.lostFocus102(event, obj);
    }

    public boolean mouseDown(Event event, int i, int j)
    {
        super.mouseDown(event, i, j);
        if(BWTUtil.getMouseButton(event) == 2)
        {
            paste(event);
            return true;
        }
        if(BWTUtil.getMouseButton(event) == 3)
        {
            createPopup(this, i, j);
        } else
        {
            if(BWTUtil.getMouseButton(event) != 1)
                return false;
            if(event.shiftDown())
                selectExtend(event);
            else
                selectStart(event);
        }
        return true;
    }

    public boolean mouseDrag(Event event, int i, int j)
    {
        if(pasting)
        {
            return true;
        } else
        {
            selectExtend(event);
            return true;
        }
    }

    public boolean mouseUp(Event event, int i, int j)
    {
        ignore_select_extensions = false;
        if(BWTUtil.getMouseButton(event) == 3)
            return false;
        if(!pasting)
            selectEnd(event);
        pasting = false;
        return true;
    }

    protected void moveBackwardChar(Event event)
    {
        if(cursor_pos == 0)
            return;
        startAction(event);
        if(event.shiftDown())
        {
            int i = select_start;
            int j = select_end;
            int k;
            if(cursor_pos > i)
                k = j = Math.max(0, j - 1);
            else
                k = i = Math.max(0, i - 1);
            select(i, j, 0);
            setCursorPosition(k);
        } else
        {
            select(cursor_pos - 1);
        }
        endAction();
    }

    protected void moveForwardChar(Event event)
    {
        if(cursor_pos == num_char)
            return;
        startAction(event);
        if(event.shiftDown())
        {
            int i = select_start;
            int j = select_end;
            int k;
            if(cursor_pos < j)
                k = i = Math.min(num_char, i + 1);
            else
                k = j = Math.min(num_char, j + 1);
            setCursorPosition(k);
            select(i, j, 0);
        } else
        {
            select(cursor_pos + 1);
        }
        endAction();
    }

    protected abstract void paintComponent(Graphics g, int i, String s, Rectangle rectangle, int j, FontMetrics fontmetrics, boolean flag, 
            Color color, Color color1, Color color2, Color color3, int k, int l);

    protected void paintCursor(boolean flag)
    {
        if(!isShowing() || !paint_cursor || !super.has_focus)
            return;
        getDrawingArea(rect);
        int i = last_x;
        int j = last_y;
        if(isShowing() && display_cursor && flag != last_state)
        {
            Graphics g;
            try
            {
                g = getGraphics();
            }
            catch(Exception _ex)
            {
                return;
            }
            if(g == null)
                return;
            clipGCToAncestors(g);
            synchronized(BaseComponent.LOCK)
            {
                if(flag)
                {
                    i = positionToX(cursor_pos);
                    j = positionToY(cursor_pos);
                }
                int k = super.border + super.highlight;
                g.clipRect(k, k, size().width - 2 * k, size().height - 2 * k);
                g.translate(-horiz_origin, -vert_origin);
                g.setXORMode(getBackground());
                if(!flag && last_overstrike != overstrike)
                {
                    boolean flag1 = overstrike;
                    overstrike = last_overstrike;
                    paintCursor(flag, g, i, j);
                    overstrike = flag1;
                } else
                {
                    paintCursor(flag, g, i, j);
                }
                g.dispose();
            }
        }
        last_state = flag;
        if(flag)
        {
            last_x = i;
            last_y = j;
        }
        last_overstrike = overstrike;
    }

    void paintCursor(boolean flag, Graphics g, int i, int j)
    {
        int k = fm.getHeight();
        j += k - 1;
        if(!flag && (JCEnvironment.isJBuilder() || dialog_bug))
            repaint(new Rectangle(i, (j - k) + 1, 1, k));
        else
        if(dialog_bug)
        {
            g.setPaintMode();
            g.setColor(getForeground());
            g.drawLine(i, (j - k) + 1, i, j);
        } else
        {
            g.drawLine(i, (j - k) + 1, i, j);
        }
    }

    protected void paste(Event event)
    {
        startAction(event);
        setCursorPosition(pointToPosition(event.x, event.y));
        pasting = true;
        boolean flag = pasteFromClipboard(event);
        endAction();
        if(!flag)
            beep();
        else
            changed = true;
    }

    public boolean pasteFromClipboard(Event event)
    {
        if(!editable)
            return false;
        String s = readClipboard();
        if(s == null)
            return false;
        boolean flag = true;
        if(overstrike && max_length - cursor_pos > s.length())
        {
            deleteSelection();
            replaceRange(s, cursor_pos, cursor_pos + s.length());
        } else
        if(num_char + s.length() <= max_length)
        {
            deleteSelection();
            insert(s, cursor_pos);
        } else
        {
            flag = false;
        }
        if(flag)
            changed = true;
        return flag;
    }

    public abstract int pointToPosition(int i, int j);

    protected Point positionToPoint(int i, Point point)
    {
        if(point != null)
        {
            point.x = positionToX(i);
            point.y = positionToY(i);
            return null;
        } else
        {
            return new Point(positionToX(i), positionToY(i));
        }
    }

    public abstract int positionToX(int i);

    public int positionToY(int i)
    {
        return getDrawingArea().y;
    }

    protected int preferredHeight()
    {
        setFm();
        return fm == null ? 30 : fm.getHeight();
    }

    protected int preferredWidth()
    {
        return fm == null ? columns * 10 : columns * fm.charWidth('N');
    }

    public void processKeyEvent(KeyEvent keyevent)
    {
        if(!is_cell_editor)
        {
            super.processKeyEvent(keyevent);
            return;
        } else
        {
            FocusManager focusmanager = FocusManager.getCurrentManager();
            FocusManager.disableSwingFocusManager();
            super.processKeyEvent(keyevent);
            FocusManager.setCurrentManager(focusmanager);
            return;
        }
    }

    protected String readClipboard()
    {
        String s = null;
        try
        {
            Clipboard clipboard1 = getToolkit().getSystemClipboard();
            Transferable transferable = clipboard1.getContents(this);
            if(transferable != null)
                s = getStringFromTransferable(transferable);
        }
        catch(Exception _ex)
        {
            s = clipboard;
        }
        return s;
    }

    public void removeCellEditorListener(CellEditorListener celleditorlistener)
    {
        cell_editor_support.removeCellEditorListener(celleditorlistener);
    }

    public void removeTextCursorListener(JCTextCursorListener jctextcursorlistener)
    {
        cursorListeners.removeElement(jctextcursorlistener);
    }

    public void removeTextListener(JCTextListener jctextlistener)
    {
        valueListeners.removeElement(jctextlistener);
    }

    public void repaint()
    {
        if(!isFrozen())
        {
            super.repaint();
            needs_repaint = false;
        } else
        {
            needs_repaint = true;
        }
    }

    protected void repaintPositions(int i, int j)
    {
        if(i == j)
            return;
        synchronized(this)
        {
            positionToPoint(i, p1);
            positionToPoint(j, p2);
        }
        repaintPositions(p1, p2);
    }

    protected void repaintPositions(Point point, Point point1)
    {
        int i = 0;
        int j = 0;
        synchronized(this)
        {
            if(point.equals(point1))
                return;
            i = Math.min(point.x, point1.x);
            j = Math.min(point.y, point1.y);
        }
        paintImmediately(i - horiz_origin, j, size().width - (i - horiz_origin), (Math.max(point.y, point1.y) - j) + fm.getHeight());
    }

    protected boolean replace(String s, int i, int j)
    {
        int k = s == null ? 0 : s.length();
        if(last_event != null && max_length < 0x7fffffff && (num_char + k) - (j - i) > max_length)
        {
            beep();
            return false;
        }
        JCTextEvent jctextevent = null;
        Rectangle rectangle = null;
        if(valueListeners.size() > 0)
        {
            jctextevent = new JCTextEvent(this, i, j, s);
            if(last_event != null && (last_event.key == 8 || last_event.key == 127))
                jctextevent.is_deletion = true;
            else
                jctextevent.is_deletion = j - i > 0 && k < j - i;
            for(int l = 0; l < valueListeners.size(); l++)
                ((JCTextListener)valueListeners.elementAt(l)).textValueChangeBegin(jctextevent);

            if(!jctextevent.doit)
                return false;
            i = jctextevent.start;
            j = jctextevent.end;
            s = jctextevent.text;
        }
        if(last_event == null)
            changed = false;
        if(s == null)
            s = "";
        if(getPeer() != null)
        {
            rectangle = new Rectangle(positionToPoint(i, null));
            rectangle.add(positionToPoint(j, null));
            if(alignment != 0)
                rectangle.x = getDrawingArea().x;
        }
        replaceTextInternal(s, i, j);
        setCursorPosition(i + k);
        select_start = select_end = cursor_pos;
        if(getPeer() != null)
        {
            rectangle.add(positionToPoint(cursor_pos, null));
            paintImmediately(rectangle.x - horiz_origin, rectangle.y - vert_origin, size().width - (rectangle.x - horiz_origin), rectangle.height + fm.getHeight());
        }
        if(jctextevent != null)
        {
            for(int i1 = 0; i1 < valueListeners.size(); i1++)
                ((JCTextListener)valueListeners.elementAt(i1)).textValueChangeEnd(jctextevent);

        }
        return true;
    }

    public void replaceRange(String s, int i, int j)
    {
        replaceRangeInternal(s, i, j);
    }

    boolean replaceRangeInternal(String s, int i, int j)
    {
        startAction(null);
        if(s == null)
            s = "";
        if(string_case == 1)
            s = s.toLowerCase();
        else
        if(string_case == 2)
            s = s.toUpperCase();
        boolean flag = replace(s, i, j);
        endAction();
        return flag;
    }

    protected void replaceTextInternal(String s, int i, int j)
    {
        boolean flag = false;
        synchronized(this)
        {
            if(j - i > 0)
            {
                System.arraycopy(text, j, text, i, num_char - j);
                num_char -= j - i;
            }
            int k = s == null ? 0 : s.length();
            if(k > 0)
            {
                if(num_char + k + i >= text.length)
                {
                    char ac[] = new char[Math.max(num_char * 2, num_char + k + i + 1)];
                    System.arraycopy(text, 0, ac, 0, num_char);
                    text = ac;
                }
                if(num_char > i)
                    System.arraycopy(text, i, text, i + k, num_char - i);
                s.getChars(0, k, text, i);
                i += k;
                select_start = select_end = i;
                num_char += k;
                flag = do_repaint;
            }
        }
        if(flag && getPeer() != null)
        {
            Rectangle rectangle = new Rectangle(positionToPoint(i, null));
            rectangle.add(positionToPoint(j, null));
            if(alignment != 0)
                rectangle.x = getDrawingArea().x;
            paintImmediately(rectangle.x - horiz_origin, rectangle.y - vert_origin, size().width - (rectangle.x - horiz_origin), rectangle.height + fm.getHeight());
        }
    }

    int rightMargin()
    {
        return super.insets.right + 2 * (super.highlight + super.border);
    }

    public void run()
    {
        paintCursor(true);
        do
        {
            try
            {
                Thread.sleep(cursor_blink_rate);
            }
            catch(Throwable _ex)
            {
                return;
            }
            if(!Thread.currentThread().isAlive() || cursor_thread == null)
                return;
            cursor_visible = cursor_visible ^ true;
            paintCursor(cursor_visible);
        } while(true);
    }

    protected void scrollEnd(Event event)
    {
        if(event.shiftDown())
        {
            if(select_start != select_end && cursor_pos < select_end)
                select(select_end, num_char, 1);
            else
                select(select_start, num_char, 1);
            setCursorPosition(select_start);
        } else
        {
            select(num_char);
            setCursorPosition(num_char);
        }
    }

    protected void scrollHome(Event event)
    {
        if(event.shiftDown())
        {
            if(select_start != select_end && cursor_pos > select_start)
                select(0, select_start, 1);
            else
                select(0, select_end, 1);
        } else
        {
            select(0);
        }
        setCursorPosition(0);
    }

    protected void scrollLineBegin(Event event)
    {
        scrollHome(event);
    }

    protected void scrollLineEnd(Event event)
    {
        scrollEnd(event);
    }

    void select(int i)
    {
        select(i, i);
    }

    public void select(int i, int j)
    {
        synchronized(this)
        {
            select(i, j, 0);
        }
        last_event = new Event(null, 0, null);
        setCursorPosition(i);
    }

    protected void select(int i, int j, int k)
    {
        if(i > j)
        {
            int l = i;
            i = j;
            j = l;
        }
        i = Math.max(0, Math.min(i, num_char));
        j = Math.max(0, Math.min(j, num_char));
        int i1 = i;
        int j1 = j;
        switch(k)
        {
        case 2: // '\002'
            for(; i > 0 && !isWordDelimiter(text[i - 1]); i--);
            for(; j < num_char && !isWordDelimiter(text[j]); j++);
            if(i == i1 && j == j1 && getSelectionType(k + 1) == 3)
            {
                i = 0;
                j = num_char;
            }
            break;

        case 3: // '\003'
            for(; i > 0 && !isLineDelimiter(text[i - 1]); i--);
            for(; j < num_char && !isLineDelimiter(text[j]); j++);
            break;

        case 5: // '\005'
            i = 0;
            j = num_char;
            break;
        }
        if(i == select_start && j == select_end)
            return;
        i1 = select_start;
        j1 = select_end;
        select_start = i;
        select_end = j;
        if(j < i1 || i > j1)
        {
            repaintPositions(i1, j1);
            repaintPositions(select_start, select_end);
        } else
        {
            repaintPositions(i1, i);
            repaintPositions(j1, j);
        }
    }

    public void selectAll()
    {
        select(0, num_char);
    }

    protected void selectEnd(Event event)
    {
        startAction(event);
        setCursorPosition(select_start);
        endAction();
    }

    protected void selectExtend(Event event)
    {
        if(ignore_select_extensions)
        {
            return;
        } else
        {
            startAction(event);
            int i = pointToPosition(event.x, event.y);
            select(select_anchor, i, selection_type);
            setCursorPosition(i);
            endAction();
            return;
        }
    }

    protected void selectStart(Event event)
    {
        startAction(event);
        int i = pointToPosition(event.x, event.y);
        select_anchor = i;
        selection_type = getSelectionType(event.clickCount);
        select(i, i, selection_type);
        setCursorPosition(i);
        endAction();
    }

    public void setAlignment(int i)
    {
        LabelConverter.checkAlignment(i);
        alignment = i;
        repaint();
    }

    public void setBounds(int i, int j, int k, int l)
    {
        synchronized(this)
        {
            if(i == location().x && j == location().y && k == size().width && l == size().height)
                return;
        }
        super.setBounds(i, j, k, l);
        synchronized(this)
        {
            if(getPeer() != null)
            {
                if(first && show_cursor)
                    showPosition(cursor_pos);
                first = false;
            }
        }
    }

    public void setCaretPosition(int i)
    {
        setCursorPosition(i);
    }

    public void setChanged(boolean flag)
    {
        changed = flag;
    }

    public void setColumns(int i)
    {
        if(i < 0)
        {
            throw new IllegalArgumentException("columns less than zero.");
        } else
        {
            columns = i;
            return;
        }
    }

    public void setCursorPosition(int i)
    {
        if(i < 0)
            throw new IllegalArgumentException("cursor position less than zero.");
        JCTextCursorEvent jctextcursorevent = null;
        if(last_event == null)
            startAction(null);
        if(cursor_pos == i)
        {
            endAction();
            return;
        }
        synchronized(this)
        {
            i = checkCursorPosition(i);
            if(cursorListeners.size() > 0)
            {
                jctextcursorevent = new JCTextCursorEvent(this, cursor_pos, i);
                for(int k = 0; k < cursorListeners.size(); k++)
                    ((JCTextCursorListener)cursorListeners.elementAt(k)).textCursorMoveBegin(jctextcursorevent);

                if(!jctextcursorevent.doit)
                    return;
                i = checkCursorPosition(jctextcursorevent.new_pos);
            }
            if(last_x >= 0 && last_y >= 0)
                paintImmediately(0, 0, size().width, size().height);
            cursor_pos = i;
        }
        if(last_event == null)
            select(cursor_pos);
        endAction();
        if(jctextcursorevent != null)
        {
            for(int j = 0; j < cursorListeners.size(); j++)
                ((JCTextCursorListener)cursorListeners.elementAt(j)).textCursorMoveEnd(jctextcursorevent);

        }
    }

    public abstract void setCursorPosition(InitialEvent initialevent, CellInfo cellinfo, String s);

    public void setEditable(boolean flag)
    {
        if(editable == flag)
            return;
        synchronized(this)
        {
            editable = flag;
            if(editable)
                super.traversable = true;
        }
        repaint();
    }

    private void setFm()
    {
        fm = getFont() == null ? null : getToolkit().getFontMetrics(getFont());
    }

    public void setFont(Font font)
    {
        if(getPeer() != null)
            fm = getToolkit().getFontMetrics(font);
        super.setFont(font);
        repaint();
    }

    public void setMaximumLength(int i)
    {
        max_length = i;
    }

    public void setOverstrike(boolean flag)
    {
        overstrike = flag;
    }

    public void setSelectedBackground(Color color)
    {
        selected_bg = color;
    }

    void setSelectedBg(Graphics g)
    {
        g.setColor(selected_bg == null ? getForeground() : selected_bg);
    }

    void setSelectedFg(Graphics g)
    {
        g.setColor(selected_fg == null ? getBackground() : selected_fg);
    }

    public void setSelectedForeground(Color color)
    {
        selected_fg = color;
    }

    public synchronized void setSelectionEnd(int i)
    {
        select(getSelectionStart(), i);
    }

    public void setSelectionList(int ai[])
    {
        select_list = ai;
    }

    public synchronized void setSelectionStart(int i)
    {
        select(i, getSelectionEnd());
    }

    public void setShowCursorPosition(boolean flag)
    {
        display_cursor = flag;
        if(show_cursor)
            showPosition(cursor_pos);
    }

    public void setStringCase(int i)
    {
        TextComponentConverter.checkStringCase(i);
        if(i != string_case)
        {
            string_case = i;
            if(i != 0)
                replaceRange(getText(), 0, num_char);
        }
    }

    public void setText(String s)
    {
        if(s == null)
            s = "";
        synchronized(this)
        {
            horiz_origin = vert_origin = 0;
            replaceRange(s, 0, num_char);
            changed = false;
        }
    }

    public abstract void showPosition(int i);

    void startAction(Event event)
    {
        synchronized(this)
        {
            action_count++;
            if(action_count > 1)
                return;
            last_event = event;
        }
        paintCursor(false);
        paint_cursor = false;
        blinkCursor(false);
        freeze();
    }

    public boolean stopCellEditing()
    {
        return true;
    }

    protected void toggleOverstrike(Event event)
    {
        overstrike = overstrike ^ true;
    }

    protected void unfreeze()
    {
        if(disabled_repaint_count > 0)
            disabled_repaint_count--;
    }

    protected void updateScrollbars()
    {
    }

    protected synchronized void writeClipboard(String s)
    {
        try
        {
            Clipboard clipboard1 = getToolkit().getSystemClipboard();
            StringSelection stringselection = new StringSelection(getText().substring(select_start, select_end));
            clipboard1.setContents(stringselection, stringselection);
        }
        catch(Exception _ex)
        {
            clipboard = s;
        }
    }

    private static final boolean TRACE = false;
    public static final int LEFT = 0;
    public static final int CENTER = 1;
    public static final int RIGHT = 2;
    public static final int CASE_AS_IS = 0;
    public static final int CASE_LOWER = 1;
    public static final int CASE_UPPER = 2;
    public static final int SELECT_NONE = 0;
    public static final int SELECT_CHAR = 1;
    public static final int SELECT_WORD = 2;
    public static final int SELECT_LINE = 3;
    public static final int SELECT_PARAGRAPH = 4;
    public static final int SELECT_ALL = 5;
    protected ResourceBundle li;
    protected JPopupMenu popup_menu;
    protected boolean is_cell_editor;
    transient Thread cursor_thread;
    int cursor_pos;
    boolean cursor_state;
    boolean cursor_visible;
    boolean paint_cursor;
    boolean display_cursor;
    boolean show_cursor;
    int disabled_repaint_count;
    int action_count;
    boolean needs_repaint;
    boolean do_repaint;
    Window window;
    static boolean dialog_bug = false;
    int num_char;
    int max_length;
    int columns;
    char text[];
    int alignment;
    int select_start;
    int select_end;
    int select_anchor;
    int selection_type;
    protected int horiz_origin;
    protected int vert_origin;
    protected JCVector cursorListeners;
    protected JCVector valueListeners;
    protected CellEditorSupport cell_editor_support;
    boolean editable;
    boolean overstrike;
    boolean changed;
    int string_case;
    Color selected_fg;
    Color selected_bg;
    transient FontMetrics fm;
    transient Event last_event;
    protected int cursor_blink_rate;
    protected boolean ignore_lose_focus;
    protected boolean ignore_select_extensions;
    Rectangle rect;
    int select_list[] = {
        1, 2, 3, 5
    };
    static String clipboard;
    boolean last_state;
    boolean last_overstrike;
    int last_x;
    int last_y;
    int last_cursor_width;
    boolean first;
    private static Point p1 = new Point(0, 0);
    private static Point p2 = new Point(0, 0);
    boolean pasting;

}
