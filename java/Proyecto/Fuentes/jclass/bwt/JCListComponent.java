// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JCListComponent.java

package jclass.bwt;

import java.applet.Applet;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.Vector;
import javax.swing.JComponent;
import jclass.base.BaseComponent;
import jclass.base.TransientComponent;
import jclass.util.JCString;
import jclass.util.JCVector;

// Referenced classes of package jclass.bwt:
//            JCComponent, BWTEnum, BWTUtil, JCActionEvent, 
//            JCActionListener, JCItemEvent, JCItemListener, JCItemSelectable, 
//            JCListInterface, JCScrollableInterface, JCScrolledWindow, ListConverter, 
//            ListSelection

public class JCListComponent extends JCComponent
    implements JCListInterface, JCScrollableInterface, JCItemSelectable
{

    public JCListComponent()
    {
        this(null, null, null);
    }

    public JCListComponent(int i, boolean flag)
    {
        this(null, null, null);
        visible_rows = i;
        multiple_select = flag;
    }

    public JCListComponent(JCVector jcvector)
    {
        this(jcvector, null, null);
    }

    public JCListComponent(JCVector jcvector, Applet applet, String s)
    {
        super(applet, s);
        items = new JCVector();
        visible_rows = 0;
        selected = new boolean[0];
        last_selected = new boolean[0];
        multiple_select = false;
        spacing = 0;
        batched = false;
        needs_recalc = false;
        needs_repaint = false;
        row_height_ext = -997;
        vert_origin = -999;
        top_row = 0;
        bottom_row = -999;
        last_row = -999;
        auto_select = false;
        draw_rect = new Rectangle();
        vis = new Insets(0, 0, 0, 0);
        did_selection = false;
        last_selected_item = 0;
        start_item = 0;
        old_start_item = 0;
        end_item = 0;
        old_end_item = 0;
        appending = false;
        focus_row = -1;
        kbd_select = false;
        wrap_around_search = true;
        actionListeners = new JCVector(0);
        itemListeners = new JCVector(0);
        scrolled_window = null;
        clip_rect = new Rectangle();
        if(s == null)
            setName("list" + nameCounter++);
        super.insets = new Insets(5, 5, 5, 5);
        if(getClass().getName().equals("jclass.bwt.JCListComponent"))
            getParameters(applet);
        if(jcvector != null)
            setItems(jcvector);
    }

    public void addActionListener(JCActionListener jcactionlistener)
    {
        actionListeners.addUnique(jcactionlistener);
    }

    public void addItem(Object obj)
    {
        addItem(obj, 0x7fffffff);
    }

    public void addItem(Object obj, int i)
    {
        synchronized(this)
        {
            if(i < 0 || i >= items.size())
            {
                i = items.size();
                items.addElement(obj);
            } else
            {
                items.insertElementAt(obj, i);
            }
        }
        calcSize(i, true);
        repaint();
    }

    public void addItemListener(JCItemListener jcitemlistener)
    {
        itemListeners.addUnique(jcitemlistener);
    }

    public void addNotify()
    {
        super.addNotify();
        calcSize(false);
        enableEvents(32L);
    }

    public boolean allowsMultipleSelections()
    {
        return multiple_select;
    }

    void calcBottomRow()
    {
        int i = vert_origin == -999 ? getRowPosition(top_row) : vert_origin;
        int k = i + getDrawingAreaHeight();
        int j;
        for(j = top_row + 1; j < items.size(); j++)
            if(getRowPosition(j) >= k)
                break;

        bottom_row = j - 1;
    }

    protected int calcRowWidth(int i)
    {
        return BWTUtil.getWidth(items.elementAt(i), this);
    }

    protected void calcSize(int i, boolean flag)
    {
        if(getPeer() == null)
            return;
        boolean flag1 = false;
        synchronized(this)
        {
            if(batched)
            {
                needs_recalc = true;
                return;
            }
            int j = selected.length;
            updateSelectedList();
            if(flag)
            {
                if(j < selected.length && i < j)
                {
                    System.arraycopy(selected, i, selected, i + 1, j - i);
                    System.arraycopy(last_selected, i, last_selected, i + 1, j - i);
                }
                selected[i] = last_selected[i] = false;
            }
            j = userdata_list == null ? 0 : userdata_list.length;
            updateUserdataList();
            if(flag && userdata_list != null)
            {
                if(j < userdata_list.length && i < j)
                    System.arraycopy(((Object) (userdata_list)), i, ((Object) (userdata_list)), i + 1, j - i);
                if(i >= 0 && i < userdata_list.length)
                    userdata_list[i] = null;
            }
        }
        if(row_height_ext != -998)
        {
            calcSize(true);
            return;
        }
        synchronized(this)
        {
            if(flag)
            {
                int k = row_heights.length;
                row_heights = ensureCapacity(row_heights);
                row_pos = ensureCapacity(row_pos);
                if(k < row_heights.length && i < k)
                    System.arraycopy(row_heights, i, row_heights, i + 1, k - i);
            }
            row_heights[i] = BWTUtil.getHeight(items.elementAt(i), this) + space();
            for(int l = 1; l < items.size(); l++)
                row_pos[l] = row_pos[l - 1] + row_heights[l - 1];

            if(visible_rows == 0)
            {
                pref_height_internal = 0;
                for(int i1 = 0; i1 < items.size(); i1++)
                    pref_height_internal += row_heights[i1];

            }
            pref_width_internal = 0;
        }
        updateParent();
    }

    protected void calcSize(boolean flag)
    {
        if(getPeer() == null)
            return;
        synchronized(this)
        {
            if(batched)
            {
                needs_recalc = true;
                return;
            }
            updateSelectedList();
            updateUserdataList();
            int i = 0;
            row_height = 0;
            if(row_height_ext != -998)
            {
                row_heights = row_pos = null;
                if(row_height_ext == -997)
                    row_height = getToolkit().getFontMetrics(getFont()).getHeight() + space();
                else
                    row_height = row_height_ext;
                i = items.size() * row_height;
            } else
            {
                if(row_heights == null || items.size() > row_heights.length)
                {
                    row_heights = new int[items.size()];
                    row_pos = new int[items.size()];
                }
                for(int j = 0; j < items.size(); j++)
                {
                    row_heights[j] = BWTUtil.getHeight(items.elementAt(j), this) + space();
                    if(j > 0)
                        row_pos[j] = row_pos[j - 1] + row_heights[j - 1];
                    i += row_heights[j];
                }

            }
            if(visible_rows > 0)
            {
                if(visible_rows < items.size())
                {
                    pref_height_internal = prevItemPos(visible_rows);
                } else
                {
                    int k = i <= 0 ? row_height : i;
                    if(k == 0)
                        k = getToolkit().getFontMetrics(getFont()).getHeight() + space();
                    int i1 = k / Math.max(1, items.size());
                    int j1 = Math.max(0, visible_rows - items.size() - 1);
                    pref_height_internal = k + j1 * i1;
                }
            } else
            {
                int l = row_height;
                if(l == 0)
                    l = getToolkit().getFontMetrics(getFont()).getHeight() + space();
                pref_height_internal = l * 4;
                if(visible_rows == -998)
                    pref_height_internal = Math.max(i, pref_height_internal);
            }
            pref_width_internal = 0;
        }
        if(flag)
            updateParent();
    }

    protected int calcWidestRow()
    {
        int i = 0;
        for(int j = 0; j < items.size(); j++)
            i = Math.max(i, BWTUtil.getWidth(items.elementAt(j), this));

        return i + 2 * super.highlight;
    }

    public void clear()
    {
        setItems(((JCVector) (null)));
    }

    public int countItems()
    {
        return items.size();
    }

    private void delete(int i, int j)
    {
        j = Math.min(items.size() - 1, j);
        for(int k = j; k >= i; k--)
            items.removeElementAt(k);

        if(selected.length > i)
        {
            System.arraycopy(selected, j + 1, selected, i, selected.length - j - 1);
            System.arraycopy(last_selected, j + 1, last_selected, i, selected.length - j - 1);
        }
        if(userdata_list != null && userdata_list.length > i)
            System.arraycopy(((Object) (userdata_list)), j + 1, ((Object) (userdata_list)), i, userdata_list.length - j - 1);
    }

    public void deleteItem(int i)
    {
        deleteItems(i, i);
    }

    public void deleteItems(int i, int j)
    {
        synchronized(this)
        {
            delete(i, j);
        }
        if(focus_row != -1)
        {
            if(focus_row >= i && focus_row <= j)
                focus_row = -1;
            if(focus_row > j)
            {
                focus_row -= j - i;
                if(focus_row < -1)
                    focus_row = -1;
            }
        }
        calcSize(true);
        repaint();
    }

    public void deselect(int i)
    {
        JCVector jcvector = null;
        JCItemEvent jcitemevent = null;
        boolean flag = false;
        boolean flag1 = false;
        synchronized(this)
        {
            if(i >= selected.length || !selected[i])
                return;
            if(multiple_select)
            {
                flag1 = true;
            } else
            {
                selected[i] = last_selected[i] = false;
                flag = true;
            }
            Object obj = this;
            if(scrolled_window != null)
                obj = (JCListInterface)scrolled_window;
            jcitemevent = new JCItemEvent(obj, 702, items.elementAt(i), 2);
            jcvector = (JCVector)itemListeners.clone();
        }
        if(flag1)
            deselectAll();
        if(flag)
            paintRow(i);
        if(jcvector != null && jcvector.size() > 0)
        {
            for(int j = 0; j < jcvector.size(); j++)
                ((JCItemListener)jcvector.elementAt(j)).itemStateChanged(jcitemevent);

        }
    }

    public void deselectAll()
    {
        int ai[] = new int[0];
        int i = 0;
        synchronized(this)
        {
            int j = items.size();
            ai = new int[j];
            for(int l = 0; l < selected.length && l < j; l++)
                if(selected[l])
                {
                    selected[l] = last_selected[l] = false;
                    ai[i] = l;
                    i++;
                }

        }
        for(int k = 0; k < i; k++)
            paintRow(ai[k]);

    }

    protected boolean doubleClickAction(Event event, int i)
    {
        if(i < 0 || i >= selected.length)
            return false;
        if(!selected[i])
            select(i, true);
        if(scrolled_window != null)
            event.target = scrolled_window;
        JCActionEvent jcactionevent = new JCActionEvent(event.target, event.id, null);
        for(int j = 0; j < actionListeners.size(); j++)
            ((JCActionListener)actionListeners.elementAt(j)).actionPerformed(jcactionevent);

        return true;
    }

    protected void draw(Graphics g, Object obj, int i, Rectangle rectangle)
    {
        BWTUtil.draw(this, g, obj, i, rectangle);
    }

    protected void drawHighlight(int i, boolean flag)
    {
        drawHighlight(null, i, flag);
    }

    protected void drawHighlight(Graphics g, int i, boolean flag)
    {
        if(!isShowing() || i < 0 || i >= items.size())
            return;
        getDrawingArea(draw_rect);
        int j = preferredWidth();
        if(j > draw_rect.width)
            j += 2 * (super.border + super.highlight);
        JCComponent.setBounds(draw_rect, draw_rect.x - horiz_origin, (draw_rect.y + getRowPosition(i)) - vert_origin, Math.max(draw_rect.width, j), getRowHeight(i));
        Rectangle rectangle = getDrawingArea();
        Color color = g == null ? null : g.getColor();
        boolean flag1 = false;
        if(g == null)
        {
            flag1 = true;
            g = getGraphics();
            g.clipRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
        }
        Color color1;
        if(flag)
            color1 = getHighlightColor();
        else
            color1 = isSelected(i) ? getSelectedBackground() : getBackground();
        if(color1 == null)
            color1 = getForeground();
        if(flag && isSelected(i))
            g.setColor(Color.yellow);
        else
            g.setColor(color1);
        if(flag)
            BWTUtil.drawDashedRect(g, draw_rect.x, draw_rect.y, draw_rect.width - 1, draw_rect.height - 1);
        else
            g.drawRect(draw_rect.x, draw_rect.y, draw_rect.width - 1, draw_rect.height - 1);
        if(flag1)
            g.dispose();
        else
        if(color != null)
            g.setColor(color);
    }

    synchronized int[] ensureCapacity(int ai[])
    {
        if(ai == null || items.size() >= ai.length)
            if(ai == null)
            {
                ai = new int[items.size()];
            } else
            {
                int i = ai.length;
                int ai1[] = ai;
                ai = new int[items.size()];
                System.arraycopy(ai1, 0, ai, 0, i);
            }
        return ai;
    }

    public int eventToRow(Event event, boolean flag)
    {
        if(getPeer() == null || items.size() == 0)
            return -999;
        getVisibleRange(vis);
        if(getBounds(vis.top, true).inside(event.x, event.y))
            return vis.top;
        if(getBounds(vis.bottom, true).inside(event.x, event.y))
            return vis.bottom;
        getDrawingArea(draw_rect);
        if(flag && !draw_rect.inside(event.x, event.y + space()))
            return -999;
        int l = (event.y + vert_origin) - draw_rect.y;
        if(event.y > draw_rect.y + draw_rect.height)
        {
            for(int i = vis.bottom; i < items.size(); i++)
                if(l < getRowPosition(i))
                    return i - 1;

            return -999;
        }
        if(event.y < draw_rect.y)
        {
            for(int j = vis.top; j >= 0; j--)
                if(l > getRowPosition(j))
                    return j;

            return -999;
        }
        for(int k = vis.top + 1; k <= Math.min(items.size(), vis.bottom + 1); k++)
            if(l < getRowPosition(k))
                return k - 1;

        if(vis.bottom == items.size() - 1 && l < getRowPosition(vis.bottom) + getRowHeight(vis.bottom))
            return vis.bottom;
        else
            return -999;
    }

    public int find(Object obj)
    {
        int i = items.indexOf(obj);
        return i < 0 ? '\uFC19' : i;
    }

    public int findItem(char c)
    {
        return findNextItem(c, 0);
    }

    public int findNextItem(char c, int i)
    {
        c = Character.toUpperCase(c);
        for(int j = i; j < items.size(); j++)
            if(BWTUtil.startsWith(items.elementAt(j), c))
                return j;

        return -999;
    }

    public int findPrevItem(char c, int i)
    {
        c = Character.toUpperCase(c);
        i = Math.min(i, items.size());
        for(int j = i; j >= 0; j--)
            if(BWTUtil.startsWith(items.elementAt(j), c))
                return j;

        return -999;
    }

    public boolean getAllowMultipleSelections()
    {
        return multiple_select;
    }

    public boolean getAutoSelect()
    {
        return auto_select;
    }

    public boolean getBatched()
    {
        return batched;
    }

    public Rectangle getBounds(int i, boolean flag)
    {
        if(i < 0 || i >= items.size())
        {
            return null;
        } else
        {
            getDrawingArea(draw_rect);
            JCComponent.setBounds(draw_rect, draw_rect.x - (flag ? horiz_origin : 0), (draw_rect.y + getRowPosition(i)) - (flag ? vert_origin : 0), Math.max(draw_rect.width, preferredWidth()), getRowHeight(i));
            return draw_rect;
        }
    }

    public int getHorizOrigin()
    {
        return horiz_origin;
    }

    public Object getItem(int i)
    {
        return items.elementAt(i);
    }

    public JCVector getItems()
    {
        return items;
    }

    public synchronized String[] getItemsStrings()
    {
        String as[] = new String[items.size()];
        for(int i = 0; i < as.length; i++)
        {
            Object obj = items.elementAt(i);
            as[i] = obj == null ? "" : obj.toString();
        }

        return as;
    }

    protected int getNextAutoSearchIndex(Event event, int i)
    {
        int j = -999;
        int k = getSelectedIndex();
        int l = items.size();
        char c = ' ';
        if(l < 1)
            return -999;
        Object obj = getSelectedItem();
        if(obj != null)
            if(obj instanceof String)
                c = ((String)obj).charAt(0);
            else
            if(obj instanceof JCString)
                c = ((JCString)obj).getString().charAt(0);
        if(!String.valueOf(c).equalsIgnoreCase(String.valueOf((char)i)))
        {
            k = -999;
            c = (char)i;
        }
        if(c == ' ')
        {
            j = Math.max(0, k + 1);
            if(getWrapAroundSearch() && j >= l)
                j = 0;
            return j;
        }
        if(k == -999)
        {
            j = findItem(c);
        } else
        {
            if(k + 1 < l)
                j = findNextItem(c, k + 1);
            if(getWrapAroundSearch() && j == -999)
                j = findItem(c);
        }
        return j;
    }

    protected void getParameters()
    {
        super.getParameters();
        ListConverter.getParams(this);
    }

    public int getRowHeight()
    {
        return row_height_ext;
    }

    protected int getRowHeight(int i)
    {
        if(row_height_ext != -998)
            return row_height;
        else
            return i >= row_heights.length ? 0 : row_heights[i];
    }

    protected int getRowPosition(int i)
    {
        i = Math.max(0, Math.min(i, items.size() - 1));
        if(row_height_ext != -998)
            return i * row_height;
        if(row_pos == null)
            return 0;
        else
            return i >= row_pos.length ? 0 : row_pos[i];
    }

    public int getRows()
    {
        return visible_rows;
    }

    public Color getSelectedBackground()
    {
        if(selected_bg == null && BaseComponent.use_system_colors)
            return SystemColor.textHighlight;
        else
            return selected_bg == null ? getForeground() : selected_bg;
    }

    public Color getSelectedForeground()
    {
        if(selected_fg == null && BaseComponent.use_system_colors)
            return SystemColor.textHighlightText;
        else
            return selected_fg == null ? getBackground() : selected_fg;
    }

    public synchronized int getSelectedIndex()
    {
        int i = 0;
        for(int j = items.size(); i < selected.length && i < j; i++)
            if(selected[i])
                return i;

        return -999;
    }

    public synchronized int[] getSelectedIndexes()
    {
        int k = 0;
        int i = 0;
        int j;
        for(j = items.size(); i < selected.length && i < j; i++)
            if(selected[i])
                k++;

        if(k == 0)
            return null;
        int ai[] = new int[k];
        i = 0;
        k = 0;
        for(; i < selected.length && i < j; i++)
            if(selected[i])
                ai[k++] = i;

        return ai;
    }

    public synchronized Object getSelectedItem()
    {
        int i = getSelectedIndex();
        return i < 0 || i >= items.size() ? null : items.elementAt(i);
    }

    public Object[] getSelectedObjects()
    {
        int ai[] = getSelectedIndexes();
        if(ai == null)
            return null;
        Object aobj[] = new Object[ai.length];
        for(int i = 0; i < ai.length; i++)
            aobj[i] = items.elementAt(ai[i]);

        return aobj;
    }

    public int getSpacing()
    {
        return spacing;
    }

    public int getTopRow()
    {
        return top_row;
    }

    public Object[] getUserDataList()
    {
        return userdata_list;
    }

    public int getVertOrigin()
    {
        return vert_origin;
    }

    public int getVisibleIndex()
    {
        return last_visible;
    }

    public void getVisibleRange(Insets insets)
    {
        if(bottom_row < 0)
            calcBottomRow();
        insets.top = top_row;
        insets.bottom = bottom_row;
    }

    public int getVisibleRows()
    {
        return visible_rows;
    }

    protected boolean getWrapAroundSearch()
    {
        return wrap_around_search;
    }

    private boolean inside(int i, int j, int k)
    {
        getDrawingArea(draw_rect);
        k += vert_origin - draw_rect.y;
        j += horiz_origin;
        return j >= draw_rect.x && j - draw_rect.x < draw_rect.width && k >= getRowPosition(i) && k - getRowPosition(i) < getRowHeight(i);
    }

    static boolean inside(Insets insets, int i)
    {
        return insets != null && Math.min(insets.top, insets.bottom) <= i && i <= Math.max(insets.top, insets.bottom);
    }

    public synchronized boolean isSelected(int i)
    {
        return i >= selected.length || i < 0 || i >= items.size() ? false : selected[i];
    }

    public boolean keyDown(Event event, int i)
    {
        if(event.key == 1005)
        {
            if(event.controlDown() && event.shiftDown())
                return ListSelection.ctrlShiftNextRow(this, event);
            if(event.controlDown())
                return ListSelection.ctrlNextRow(this, event);
            if(event.shiftDown())
                return ListSelection.shiftNextRow(this, event);
            else
                return ListSelection.normalNextRow(this, event);
        }
        if(event.key == 1004)
        {
            if(event.controlDown() && event.shiftDown())
                return ListSelection.ctrlShiftPrevRow(this, event);
            if(event.controlDown())
                return ListSelection.ctrlPrevRow(this, event);
            if(event.shiftDown())
                return ListSelection.shiftPrevRow(this, event);
            else
                return ListSelection.normalPrevRow(this, event);
        }
        if(event.key == 10 && focus_row >= 0)
            return doubleClickAction(event, focus_row);
        if(event.key == 32)
            return ListSelection.kbdSelect(this, event);
        if(wrap_around_search)
        {
            int j = getNextAutoSearchIndex(event, i);
            if(j != -999)
            {
                deselectAll();
                makeVisible(j);
                ListSelection.select(this, j, true, event);
                return true;
            }
        }
        return super.keyDown(event, i);
    }

    public void makeVisible(int i)
    {
        if(i < 0 || i >= items.size())
            return;
        last_visible = i;
        int j;
        if(i <= top_row)
            j = getRowPosition(i);
        else
        if(i == items.size() - 1)
            j = 0x7fffffff;
        else
        if(i >= bottom_row)
            j = (vert_origin + getRowPosition(i + 1)) - getRowPosition(bottom_row);
        else
            return;
        if(scrolled_window != null)
            scrolled_window.scrollVertical(j);
    }

    public boolean mouseDown(Event event, int i, int j)
    {
        super.mouseDown(event, i, j);
        if(BWTUtil.getMouseButton(event) != 1)
            return false;
        long l = event.when - TransientComponent.popdown_event_timestamp;
        if(l >= 0L && l < 50L)
            return true;
        if(event.clickCount > 1)
        {
            int k = eventToRow(event, true);
            if(k == last_row)
                return doubleClickAction(event, k);
            event.clickCount = 1;
        }
        if(multiple_select && event.shiftDown())
            return ListSelection.beginExtend(this, event);
        if(multiple_select && event.controlDown())
            return ListSelection.beginToggle(this, event);
        else
            return ListSelection.beginSelect(this, event);
    }

    public boolean mouseDrag(Event event, int i, int j)
    {
        return ListSelection.mouseDrag(this, event);
    }

    public boolean mouseUp(Event event, int i, int j)
    {
        if(event.clickCount > 1)
        {
            int k = eventToRow(event, true);
            if(k == last_row)
                return true;
            event.clickCount = 1;
        }
        if(multiple_select && event.shiftDown())
            return ListSelection.endExtend(this, event);
        if(multiple_select && event.controlDown())
            return ListSelection.endToggle(this, event);
        else
            return ListSelection.endSelect(this, event);
    }

    protected void paintComponent(Graphics g)
    {
        if(items.size() == 0)
            return;
        top_row = Math.max(0, Math.min(top_row, items.size() - 1));
        if(vert_origin == -999)
            vert_origin = getRowPosition(top_row);
        calcBottomRow();
        Color color = null;
        if(!isEnabled())
        {
            color = g.getColor();
            g.setColor(Color.lightGray.darker().darker());
        }
        for(int i = top_row; i <= bottom_row; i++)
            paintRow(i, g);

        if(!isEnabled())
            g.setColor(color);
    }

    public void paintRow(int i)
    {
        if(!isShowing() || i < top_row || i > bottom_row)
            return;
        if(i < 0 || i >= items.size())
        {
            return;
        } else
        {
            paintRow(i, null);
            return;
        }
    }

    protected void paintRow(int i, Graphics g)
    {
        getDrawingArea(clip_rect);
        JCComponent.setBounds(draw_rect, clip_rect.x - horiz_origin, (clip_rect.y + getRowPosition(i)) - vert_origin, clip_rect.width + horiz_origin, getRowHeight(i));
        boolean flag = false;
        if(g == null)
        {
            flag = true;
            g = getGraphics();
            g.clipRect(clip_rect.x, clip_rect.y, clip_rect.width, clip_rect.height);
            if(i < selected.length && !selected[i])
            {
                g.setColor(getBackground());
                g.fillRect(draw_rect.x, draw_rect.y, draw_rect.width, draw_rect.height);
            }
            if(!isEnabled())
                g.setColor(Color.lightGray.darker().darker());
            else
                g.setColor(getForeground());
            g.setFont(getFont());
        } else
        if(!draw_rect.intersects(getPaintRect()))
            return;
        if(i < selected.length && selected[i])
        {
            g.setColor(getSelectedBackground());
            g.fillRect(draw_rect.x, draw_rect.y, draw_rect.width, draw_rect.height);
            g.setColor(getSelectedForeground());
        }
        if(isEnabled() && i == focus_row)
            drawHighlight(g, i, true);
        draw_rect.translate(super.highlight, super.highlight);
        draw_rect.width -= 2 * super.highlight;
        draw_rect.height -= 2 * super.highlight;
        if(items.elementAt(i) != null)
            draw(g, items.elementAt(i), 3, draw_rect);
        if(flag)
            g.dispose();
        else
        if(i < selected.length && selected[i])
            g.setColor(getForeground());
    }

    protected int preferredHeight()
    {
        return pref_height_internal;
    }

    protected int preferredWidth()
    {
        if(pref_width_internal == 0)
            pref_width_internal = calcWidestRow();
        return pref_width_internal;
    }

    private int prevItemPos(int i)
    {
        return i <= 0 ? 0 : getRowPosition(i - 1) + getRowHeight(i - 1);
    }

    public void remove(String s)
    {
        synchronized(this)
        {
            int i = items.indexOf(s);
            if(i < 0)
                throw new IllegalArgumentException("item " + s + " not found in JCList");
            deleteItems(i, i);
        }
        calcSize(true);
        repaint();
    }

    public void removeActionListener(JCActionListener jcactionlistener)
    {
        actionListeners.removeElement(jcactionlistener);
    }

    public void removeItemListener(JCItemListener jcitemlistener)
    {
        itemListeners.removeElement(jcitemlistener);
    }

    public void repaint()
    {
        if(batched)
            needs_repaint = true;
        else
            super.repaint();
    }

    public void replaceItem(Object obj, int i)
    {
        synchronized(this)
        {
            if(i >= items.size())
                return;
            items.removeElementAt(i);
            if(i < 0 || i >= items.size())
            {
                i = items.size();
                items.addElement(obj);
            } else
            {
                items.insertElementAt(obj, i);
            }
        }
        calcSize(i, true);
        repaint();
    }

    public void select(int i)
    {
        select(i, true);
    }

    public void select(int i, boolean flag)
    {
        ListSelection.select(this, i, flag, null);
    }

    public void select(Object obj)
    {
        select(obj, true);
    }

    public void select(Object obj, boolean flag)
    {
        int i = items.indexOf(obj);
        if(i >= 0)
            ListSelection.select(this, i, flag, null);
    }

    public void setAllowMultipleSelections(boolean flag)
    {
        multiple_select = flag;
    }

    public void setAutoSelect(boolean flag)
    {
        auto_select = flag;
        repaint();
    }

    public void setBatched(boolean flag)
    {
        if(batched == flag)
            return;
        batched = flag;
        if(batched)
            return;
        if(needs_recalc)
            calcSize(true);
        if(needs_recalc || needs_repaint)
            repaint();
        needs_recalc = needs_repaint = false;
    }

    void setFocus(int i)
    {
        if(i == focus_row)
            return;
        if(focus_row >= 0 && focus_row < items.size())
            drawHighlight(focus_row, false);
        focus_row = i;
        drawHighlight(focus_row, true);
        if(auto_select)
            ListSelection.selectFocusRow(this, null);
    }

    public void setFont(Font font)
    {
        synchronized(this)
        {
            if(getFont() != null && getFont().equals(font))
                return;
            super.setFont(font);
        }
        calcSize(true);
    }

    public void setHorizOrigin(int i)
    {
        horiz_origin = i;
    }

    public void setItems(JCVector jcvector)
    {
        synchronized(this)
        {
            items = jcvector == null ? new JCVector() : jcvector;
            for(int i = 0; i < selected.length; i++)
                selected[i] = last_selected[i] = false;

            focus_row = -1;
        }
        calcSize(true);
        repaint();
    }

    public void setItems(String as[])
    {
        setItems(new JCVector(as));
    }

    public void setRowHeight(int i)
    {
        if(i < 0 && i != -997 && i != -998)
        {
            throw new IllegalArgumentException("invalid row height: " + i);
        } else
        {
            row_height_ext = i;
            calcSize(true);
            repaint();
            return;
        }
    }

    public void setSelectedBackground(Color color)
    {
        selected_bg = color;
        if(getSelectedIndex() >= 0)
            repaint();
    }

    public void setSelectedForeground(Color color)
    {
        selected_fg = color;
        if(getSelectedIndex() >= 0)
            repaint();
    }

    public void setSpacing(int i)
    {
        spacing = i;
        calcSize(true);
        repaint();
    }

    public void setTopRow(int i)
    {
        top_row = i;
        vert_origin = -999;
        repaint();
    }

    public void setUserDataList(Object aobj[])
    {
        userdata_list = aobj;
        updateUserdataList();
    }

    public void setVertOrigin(int i)
    {
        vert_origin = i;
        for(top_row = 0; top_row < items.size(); top_row++)
            if(getRowPosition(top_row) + getRowHeight(top_row) > i)
                break;

    }

    public void setVisibleRows(int i)
    {
        visible_rows = i;
        calcSize(true);
    }

    protected void setWrapAroundSearch(boolean flag)
    {
        wrap_around_search = flag;
    }

    private int space()
    {
        return 2 * super.highlight + spacing;
    }

    protected synchronized void updateSelectedList()
    {
        selected = BWTUtil.copyList(selected, items.size(), false);
        last_selected = BWTUtil.copyList(last_selected, items.size(), false);
    }

    protected synchronized void updateUserdataList()
    {
        if(userdata_list != null)
            userdata_list = BWTUtil.copyList(userdata_list, items.size(), null);
    }

    JCVector items;
    int visible_rows;
    boolean selected[];
    boolean last_selected[];
    Color selected_bg;
    Color selected_fg;
    boolean multiple_select;
    int spacing;
    boolean batched;
    boolean needs_recalc;
    boolean needs_repaint;
    int pref_height_internal;
    int pref_width_internal;
    int row_height_ext;
    int row_height;
    int row_heights[];
    int row_pos[];
    int horiz_origin;
    int vert_origin;
    int top_row;
    int bottom_row;
    int last_visible;
    int last_row;
    boolean auto_select;
    Object userdata_list[];
    Rectangle draw_rect;
    Insets vis;
    boolean did_selection;
    int last_selected_item;
    int start_item;
    int old_start_item;
    int end_item;
    int old_end_item;
    boolean appending;
    int focus_row;
    boolean kbd_select;
    int selection_type;
    boolean wrap_around_search;
    protected JCVector actionListeners;
    protected JCVector itemListeners;
    private static final String base = "list";
    private static int nameCounter = 0;
    static final int DEFAULT_VISIBLE_ROWS = 4;
    protected JCScrolledWindow scrolled_window;
    Rectangle clip_rect;

}
