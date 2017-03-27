// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ListSelection.java

package jclass.bwt;

import java.awt.Event;
import java.awt.Rectangle;
import java.awt.event.ItemEvent;
import java.util.Vector;
import jclass.base.BaseComponent;

// Referenced classes of package jclass.bwt:
//            BWTEnum, JCItemEvent, JCItemListener, JCListComponent, 
//            JCListEvent, JCListInterface, JCListListener

class ListSelection
{

    ListSelection()
    {
    }

    private static void arrangeRange(JCListComponent jclistcomponent, int i)
    {
        int j = jclistcomponent.start_item;
        int k = jclistcomponent.end_item;
        int l = i;
        boolean flag = jclistcomponent.selected[j];
        if(j < k)
        {
            if(l > k)
                selectRange(jclistcomponent, k, l, flag);
            else
            if(l < k && l >= j)
            {
                if(!flag)
                    restoreRange(jclistcomponent, l + 1, k, false);
                else
                    selectRange(jclistcomponent, l + 1, k, false);
            } else
            if(l <= j)
            {
                if(!flag)
                    restoreRange(jclistcomponent, j, k, false);
                else
                    selectRange(jclistcomponent, j, k, false);
                selectRange(jclistcomponent, l, j, flag);
            }
        } else
        if(j > k)
        {
            if(l <= k)
                selectRange(jclistcomponent, l, k, flag);
            else
            if(l > k && l <= j)
            {
                if(!flag)
                    restoreRange(jclistcomponent, k, l - 1, false);
                else
                    selectRange(jclistcomponent, k, l - 1, false);
            } else
            if(l >= j)
            {
                if(!flag)
                    restoreRange(jclistcomponent, k, j, false);
                else
                    selectRange(jclistcomponent, k, j, false);
                selectRange(jclistcomponent, j, l, flag);
            }
        } else
        {
            selectRange(jclistcomponent, j, l, flag);
        }
    }

    static boolean beginExtend(JCListComponent jclistcomponent, Event event)
    {
        if(!jclistcomponent.multiple_select)
        {
            return false;
        } else
        {
            jclistcomponent.appending = true;
            event.modifiers |= 1;
            return beginSelect(jclistcomponent, event);
        }
    }

    static boolean beginSelect(JCListComponent jclistcomponent, Event event)
    {
        if(event.clickCount > 1)
            return false;
        if(jclistcomponent.countItems() == 0)
            return false;
        int i = jclistcomponent.last_row = jclistcomponent.eventToRow(event, true);
        if(i == -999)
            return false;
        if(jclistcomponent.auto_select && jclistcomponent.selected[i] && i == jclistcomponent.focus_row)
            return true;
        jclistcomponent.selection_type = 0;
        if(jclistcomponent.multiple_select)
            if(event.shiftDown())
                jclistcomponent.selection_type = 1;
            else
            if(event.controlDown())
                jclistcomponent.selection_type = 2;
        if(!postEvent(jclistcomponent, 0, event, i))
            return false;
        jclistcomponent.did_selection = false;
        boolean flag = i >= jclistcomponent.selected.length ? false : jclistcomponent.selected[i];
        if(!jclistcomponent.appending)
        {
            for(int j = 0; j < jclistcomponent.selected.length && j < jclistcomponent.countItems(); j++)
                if(jclistcomponent.selected[j])
                {
                    jclistcomponent.selected[j] = false;
                    jclistcomponent.paintRow(j);
                }

        }
        if(jclistcomponent.multiple_select)
        {
            if(event.shiftDown())
                flag = jclistcomponent.selected[jclistcomponent.start_item];
            else
            if(event.controlDown())
            {
                jclistcomponent.selected[i] = jclistcomponent.selected[i] ^ true;
            } else
            {
                jclistcomponent.last_selected[i] = jclistcomponent.selected[i];
                jclistcomponent.selected[i] = jclistcomponent.selected[i] ^ true;
            }
        } else
        if(i < jclistcomponent.selected.length)
            jclistcomponent.selected[i] = flag ^ true;
        jclistcomponent.paintRow(i);
        jclistcomponent.last_selected_item = i;
        jclistcomponent.old_end_item = jclistcomponent.end_item;
        jclistcomponent.end_item = i;
        if(!jclistcomponent.multiple_select || !event.shiftDown())
        {
            jclistcomponent.old_start_item = jclistcomponent.start_item;
            jclistcomponent.start_item = i;
            if(jclistcomponent.multiple_select && !jclistcomponent.kbd_select)
                clickRow(jclistcomponent, event);
            return true;
        }
        int k = jclistcomponent.start_item;
        int l = jclistcomponent.old_end_item;
        if(k < l)
        {
            if(i > l)
                selectRange(jclistcomponent, l + 1, i, flag);
            else
            if(i < l && i >= k)
                restoreRange(jclistcomponent, i + 1, l, false);
            else
            if(i < k)
            {
                if(flag)
                    selectRange(jclistcomponent, k + 1, l, false);
                else
                    restoreRange(jclistcomponent, k + 1, l, false);
                selectRange(jclistcomponent, i, k, flag);
            }
        } else
        if(k > l)
        {
            if(i < l)
                selectRange(jclistcomponent, i, l + 1, flag);
            else
            if(i > l && i <= k)
                restoreRange(jclistcomponent, l, i - 1, false);
            else
            if(i > k)
            {
                if(flag)
                    selectRange(jclistcomponent, l, k - 1, false);
                else
                    restoreRange(jclistcomponent, l, k - 1, false);
                selectRange(jclistcomponent, k, i, flag);
            }
        } else
        if(k == l)
            selectRange(jclistcomponent, k, i, flag);
        clickRow(jclistcomponent, event);
        return true;
    }

    static boolean beginToggle(JCListComponent jclistcomponent, Event event)
    {
        if(!jclistcomponent.multiple_select)
            return false;
        jclistcomponent.appending = true;
        event.modifiers |= 2;
        jclistcomponent.old_start_item = jclistcomponent.start_item;
        jclistcomponent.old_end_item = jclistcomponent.end_item;
        int i = Math.min(jclistcomponent.old_start_item, jclistcomponent.old_end_item);
        int j = Math.max(jclistcomponent.old_start_item, jclistcomponent.old_end_item);
        if(i != 0 || j != 0)
            for(; i <= j; i++)
                jclistcomponent.last_selected[i] = jclistcomponent.selected[i];

        return beginSelect(jclistcomponent, event);
    }

    private static void clickRow(JCListComponent jclistcomponent, Event event)
    {
        int i = jclistcomponent.last_selected_item;
        jclistcomponent.did_selection = true;
        postEvent(jclistcomponent, 1, event, i);
    }

    static boolean ctrlNextRow(JCListComponent jclistcomponent, Event event)
    {
        if(!jclistcomponent.hasFocus())
        {
            return false;
        } else
        {
            jclistcomponent.appending = true;
            event.modifiers |= 2;
            jclistcomponent.selection_type = 2;
            boolean flag = nextRow(jclistcomponent, event);
            jclistcomponent.appending = false;
            return flag;
        }
    }

    static boolean ctrlPrevRow(JCListComponent jclistcomponent, Event event)
    {
        if(!jclistcomponent.hasFocus())
        {
            return false;
        } else
        {
            jclistcomponent.appending = true;
            event.modifiers |= 2;
            jclistcomponent.selection_type = 2;
            boolean flag = prevRow(jclistcomponent, event);
            jclistcomponent.appending = false;
            return flag;
        }
    }

    static boolean ctrlShiftNextRow(JCListComponent jclistcomponent, Event event)
    {
        if(!jclistcomponent.multiple_select)
            return false;
        if(!jclistcomponent.hasFocus())
        {
            return false;
        } else
        {
            jclistcomponent.appending = true;
            event.modifiers |= 3;
            jclistcomponent.selection_type = 1;
            boolean flag = nextRow(jclistcomponent, event);
            jclistcomponent.appending = false;
            return flag;
        }
    }

    static boolean ctrlShiftPrevRow(JCListComponent jclistcomponent, Event event)
    {
        if(!jclistcomponent.multiple_select)
            return false;
        if(!jclistcomponent.hasFocus())
        {
            return false;
        } else
        {
            jclistcomponent.appending = true;
            event.modifiers |= 3;
            jclistcomponent.selection_type = 1;
            boolean flag = prevRow(jclistcomponent, event);
            jclistcomponent.appending = false;
            return flag;
        }
    }

    static boolean endExtend(JCListComponent jclistcomponent, Event event)
    {
        if(!jclistcomponent.multiple_select)
        {
            return false;
        } else
        {
            jclistcomponent.appending = false;
            return endSelect(jclistcomponent, event);
        }
    }

    static boolean endSelect(JCListComponent jclistcomponent, Event event)
    {
        if(jclistcomponent.countItems() == 0)
            return false;
        int i = jclistcomponent.last_row = jclistcomponent.eventToRow(event, false);
        jclistcomponent.old_start_item = jclistcomponent.start_item;
        jclistcomponent.old_end_item = jclistcomponent.end_item;
        if(jclistcomponent.hasFocus())
        {
            if(jclistcomponent.multiple_select)
            {
                jclistcomponent.drawHighlight(jclistcomponent.focus_row, false);
                jclistcomponent.focus_row = i;
                jclistcomponent.drawHighlight(jclistcomponent.focus_row, true);
            } else
            {
                jclistcomponent.drawHighlight(jclistcomponent.focus_row, false);
                jclistcomponent.focus_row = jclistcomponent.last_selected_item;
                jclistcomponent.drawHighlight(jclistcomponent.focus_row, true);
            }
        } else
        {
            jclistcomponent.focus_row = i;
        }
        if((!event.shiftDown() && !event.controlDown() || !jclistcomponent.multiple_select) && !jclistcomponent.did_selection)
        {
            if(event.clickCount > 1)
                jclistcomponent.doubleClickAction(event, i);
            else
                clickRow(jclistcomponent, event);
        } else
        if(event.clickCount > 1)
            jclistcomponent.doubleClickAction(event, i);
        jclistcomponent.drawHighlight(jclistcomponent.focus_row, true);
        jclistcomponent.appending = false;
        return true;
    }

    static boolean endToggle(JCListComponent jclistcomponent, Event event)
    {
        if(!jclistcomponent.multiple_select)
        {
            return false;
        } else
        {
            jclistcomponent.appending = false;
            return endSelect(jclistcomponent, event);
        }
    }

    private static void kbdExtendedItem(JCListComponent jclistcomponent, int i, Event event)
    {
        if(jclistcomponent.last_selected_item == i)
            return;
        if(event.shiftDown())
        {
            arrangeRange(jclistcomponent, i);
            jclistcomponent.end_item = jclistcomponent.last_selected_item = i;
            clickRow(jclistcomponent, event);
        }
    }

    private static void kbdNewItem(JCListComponent jclistcomponent, int i, Event event)
    {
        if(jclistcomponent.last_selected_item == i)
            return;
        if(!jclistcomponent.multiple_select)
            return;
        if(!jclistcomponent.did_selection)
        {
            arrangeRange(jclistcomponent, i);
            jclistcomponent.last_selected_item = jclistcomponent.end_item = i;
            clickRow(jclistcomponent, event);
            return;
        }
        jclistcomponent.did_selection = true;
        arrangeRange(jclistcomponent, i);
        jclistcomponent.last_selected_item = jclistcomponent.end_item = i;
        if(!jclistcomponent.did_selection)
            clickRow(jclistcomponent, event);
    }

    static boolean kbdSelect(JCListComponent jclistcomponent, Event event)
    {
        if(jclistcomponent.focus_row < 0)
            return false;
        Rectangle rectangle = jclistcomponent.getBounds(jclistcomponent.focus_row, true);
        event.x = rectangle.x + 1;
        event.y = rectangle.y + 1;
        if(jclistcomponent.multiple_select && (event.shiftDown() || event.controlDown()))
            jclistcomponent.appending = true;
        jclistcomponent.kbd_select = true;
        boolean flag = beginSelect(jclistcomponent, event);
        if(flag)
            clickRow(jclistcomponent, event);
        jclistcomponent.kbd_select = false;
        return flag;
    }

    static boolean mouseDrag(JCListComponent jclistcomponent, Event event)
    {
        if(!jclistcomponent.multiple_select)
            return false;
        if(Math.abs(event.x - last_x) + Math.abs(event.y - last_y) < 20)
            return false;
        last_x = event.x;
        last_y = event.y;
        jclistcomponent.did_selection = false;
        int i = jclistcomponent.last_row = jclistcomponent.eventToRow(event, false);
        if(i < 0)
            return false;
        jclistcomponent.makeVisible(i);
        jclistcomponent.selection_type = 1;
        if(!postEvent(jclistcomponent, 0, event, i))
        {
            return false;
        } else
        {
            kbdNewItem(jclistcomponent, i, event);
            return true;
        }
    }

    private static boolean nextRow(JCListComponent jclistcomponent, Event event)
    {
        int i = jclistcomponent.focus_row + 1;
        if(i >= jclistcomponent.countItems())
            return false;
        jclistcomponent.makeVisible(i);
        if(!postEvent(jclistcomponent, 0, event, i))
            return false;
        jclistcomponent.drawHighlight(jclistcomponent.focus_row, false);
        jclistcomponent.focus_row = i;
        jclistcomponent.drawHighlight(jclistcomponent.focus_row, true);
        if(jclistcomponent.auto_select)
            selectFocusRow(jclistcomponent, event);
        else
        if(jclistcomponent.multiple_select)
            kbdExtendedItem(jclistcomponent, i, event);
        return true;
    }

    static boolean normalNextRow(JCListComponent jclistcomponent, Event event)
    {
        if(!jclistcomponent.hasFocus())
        {
            return false;
        } else
        {
            jclistcomponent.appending = false;
            jclistcomponent.selection_type = 0;
            return nextRow(jclistcomponent, event);
        }
    }

    static boolean normalPrevRow(JCListComponent jclistcomponent, Event event)
    {
        if(!jclistcomponent.hasFocus())
        {
            return false;
        } else
        {
            jclistcomponent.appending = false;
            event.modifiers &= 0xfffffff4;
            jclistcomponent.selection_type = 0;
            return prevRow(jclistcomponent, event);
        }
    }

    private static boolean postEvent(JCListComponent jclistcomponent, int i, Event event, int j)
    {
        Object obj = (JCListInterface)jclistcomponent.scrolled_window;
        if(obj == null)
            obj = jclistcomponent;
        if(jclistcomponent.scrolled_window != null && event != null)
            event.target = obj;
        int k = event == null ? 0 : event.id;
        boolean flag = j >= jclistcomponent.selected.length ? false : jclistcomponent.selected[j];
        byte byte0 = ((byte)(flag ? 2 : 1));
        if(i == 1)
            byte0 = 1;
        if(i == 1 && event != null)
            event.id = flag ? 701 : 702;
        if(jclistcomponent.itemListeners.size() == 0)
            return true;
        if(i == 1 && !flag)
        {
            JCItemEvent jcitemevent = new JCItemEvent(obj, 701, jclistcomponent.getItem(j), 2);
            for(int l = 0; l < jclistcomponent.itemListeners.size(); l++)
                ((JCItemListener)jclistcomponent.itemListeners.elementAt(l)).itemStateChanged(jcitemevent);

            return true;
        }
        if(event != null)
            event.id = k;
        JCListEvent jclistevent = new JCListEvent(((JCListInterface) (obj)), event, j, jclistcomponent.selection_type, byte0);
        for(int i1 = 0; i1 < jclistcomponent.itemListeners.size(); i1++)
        {
            JCItemListener jcitemlistener = (JCItemListener)jclistcomponent.itemListeners.elementAt(i1);
            if(i == 1)
                jcitemlistener.itemStateChanged(jclistevent);
            if(jcitemlistener instanceof JCListListener)
                if(i == 0)
                {
                    ((JCListListener)jcitemlistener).listItemSelectBegin(jclistevent);
                    if(!jclistevent.doit)
                        return false;
                } else
                {
                    ((JCListListener)jcitemlistener).listItemSelectEnd(jclistevent);
                }
        }

        return true;
    }

    private static boolean prevRow(JCListComponent jclistcomponent, Event event)
    {
        if(jclistcomponent.countItems() == 0)
            return false;
        int i = jclistcomponent.focus_row - 1;
        if(i < 0)
            return false;
        if(!postEvent(jclistcomponent, 0, event, i))
            return false;
        jclistcomponent.makeVisible(i);
        int j = jclistcomponent.focus_row;
        jclistcomponent.drawHighlight(j, false);
        jclistcomponent.focus_row = i;
        jclistcomponent.drawHighlight(jclistcomponent.focus_row, true);
        if(jclistcomponent.auto_select)
            selectFocusRow(jclistcomponent, event);
        else
        if(jclistcomponent.multiple_select)
            kbdExtendedItem(jclistcomponent, i, event);
        return true;
    }

    private static void restoreRange(JCListComponent jclistcomponent, int i, int j, boolean flag)
    {
        int k = Math.min(i, j);
        int l = Math.max(i, j);
        int i1 = jclistcomponent.start_item;
        int ai[] = new int[(l - k) + 1];
        int j1 = 0;
        synchronized(jclistcomponent)
        {
            for(int l1 = k; l1 <= l; l1++)
                if(l1 != i1 || flag)
                {
                    jclistcomponent.selected[l1] = jclistcomponent.last_selected[l1];
                    ai[j1] = l1;
                    j1++;
                }

        }
        for(int k1 = 0; k1 < j1; k1++)
            jclistcomponent.paintRow(ai[k1]);

    }

    static void select(JCListComponent jclistcomponent, int i, boolean flag, Event event)
    {
        int ai[] = new int[0];
        int j = 0;
        synchronized(jclistcomponent)
        {
            if(jclistcomponent.selected.length < jclistcomponent.countItems())
                jclistcomponent.updateSelectedList();
            if(!jclistcomponent.multiple_select)
            {
                jclistcomponent.selection_type = 0;
                ai = new int[jclistcomponent.selected.length];
                for(int i1 = 0; i1 < jclistcomponent.countItems(); i1++)
                    if(jclistcomponent.selected[i1])
                    {
                        jclistcomponent.selected[i1] = jclistcomponent.last_selected[i1] = false;
                        ai[j] = i1;
                        j++;
                    }

            } else
            {
                jclistcomponent.selection_type = 1;
            }
        }
        for(int k = 0; k < j; k++)
            jclistcomponent.paintRow(ai[k]);

        synchronized(jclistcomponent)
        {
            jclistcomponent.selected[i] = jclistcomponent.last_selected[i] = true;
            jclistcomponent.last_selected_item = i;
        }
        jclistcomponent.paintRow(i);
        if(flag)
            clickRow(jclistcomponent, event);
        int l = 0;
        int j1 = 0;
        boolean flag1 = false;
        synchronized(jclistcomponent)
        {
            for(int k1 = jclistcomponent.countItems() - 1; k1 >= 0; k1--)
                if(jclistcomponent.selected[k1])
                {
                    int i2 = k1;
                    for(; k1 != 0 && jclistcomponent.selected[k1]; k1--);
                    int l1;
                    if(k1 == 0 && jclistcomponent.selected[k1])
                        l1 = k1;
                    else
                        l1 = k1 + 1;
                    jclistcomponent.old_end_item = jclistcomponent.end_item;
                    jclistcomponent.end_item = i2;
                    jclistcomponent.old_start_item = jclistcomponent.start_item;
                    jclistcomponent.start_item = l1;
                    jclistcomponent.last_selected_item = i2;
                    l = jclistcomponent.focus_row;
                    jclistcomponent.focus_row = i2;
                    j1 = jclistcomponent.focus_row;
                    flag1 = true;
                }

            if(!flag1)
            {
                jclistcomponent.old_end_item = jclistcomponent.end_item;
                jclistcomponent.end_item = 0;
                jclistcomponent.old_start_item = jclistcomponent.start_item;
                jclistcomponent.start_item = 0;
                jclistcomponent.last_selected_item = 0;
            }
        }
        if(flag1)
        {
            jclistcomponent.drawHighlight(l, false);
            jclistcomponent.drawHighlight(j1, true);
        }
    }

    static void selectFocusRow(JCListComponent jclistcomponent, Event event)
    {
        boolean flag = jclistcomponent.multiple_select;
        jclistcomponent.multiple_select = false;
        select(jclistcomponent, jclistcomponent.focus_row, true, event);
        jclistcomponent.multiple_select = flag;
    }

    private static void selectRange(JCListComponent jclistcomponent, int i, int j, boolean flag)
    {
        int ai[] = new int[Math.abs(j - i) + 1];
        int k = 0;
        synchronized(jclistcomponent)
        {
            int i1 = Math.min(i, j);
            for(int j1 = Math.max(i, j); i1 <= j1; i1++)
                if(jclistcomponent.selected[i1] != flag)
                {
                    jclistcomponent.selected[i1] = flag;
                    ai[k] = i1;
                    k++;
                }

        }
        for(int l = 0; l < k; l++)
            jclistcomponent.paintRow(ai[l]);

    }

    static boolean shiftNextRow(JCListComponent jclistcomponent, Event event)
    {
        if(!jclistcomponent.multiple_select)
            return false;
        if(!jclistcomponent.hasFocus())
        {
            return false;
        } else
        {
            jclistcomponent.appending = true;
            event.modifiers |= 1;
            jclistcomponent.selection_type = 1;
            boolean flag = nextRow(jclistcomponent, event);
            jclistcomponent.appending = false;
            return flag;
        }
    }

    static boolean shiftPrevRow(JCListComponent jclistcomponent, Event event)
    {
        if(!jclistcomponent.multiple_select)
            return false;
        if(!jclistcomponent.hasFocus())
        {
            return false;
        } else
        {
            jclistcomponent.appending = true;
            event.modifiers |= 1;
            jclistcomponent.selection_type = 1;
            boolean flag = prevRow(jclistcomponent, event);
            jclistcomponent.appending = false;
            return flag;
        }
    }

    static final int BEGIN = 0;
    static final int END = 1;
    static int last_x = -999;
    static int last_y;

}
