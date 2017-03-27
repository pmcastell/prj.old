// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   JCTextInterface.java

package jclass.bwt;

import java.awt.Color;
import java.awt.Dimension;

// Referenced classes of package jclass.bwt:
//            JCTextCursorListener, JCTextListener

public interface JCTextInterface
{

    public abstract void addTextCursorListener(JCTextCursorListener jctextcursorlistener);

    public abstract void addTextListener(JCTextListener jctextlistener);

    public abstract void append(String s);

    public abstract void beep();

    public abstract boolean getChanged();

    public abstract int getColumns();

    public abstract int getCursorPosition();

    public abstract boolean getEditable();

    public abstract int getLastPosition();

    public abstract Dimension getMinimumSize(int i);

    public abstract boolean getOverstrike();

    public abstract Color getSelectedBackground();

    public abstract Color getSelectedForeground();

    public abstract String getSelectedText();

    public abstract int getSelectionEnd();

    public abstract int[] getSelectionList();

    public abstract int getSelectionStart();

    public abstract boolean getShowCursorPosition();

    public abstract String getText();

    public abstract void insert(String s, int i);

    public abstract boolean isEditable();

    public abstract int pointToPosition(int i, int j);

    public abstract void replaceRange(String s, int i, int j);

    public abstract void select(int i, int j);

    public abstract void selectAll();

    public abstract void setAlignment(int i);

    public abstract void setColumns(int i);

    public abstract void setCursorPosition(int i);

    public abstract void setEditable(boolean flag);

    public abstract void setMaximumLength(int i);

    public abstract void setOverstrike(boolean flag);

    public abstract void setSelectedBackground(Color color);

    public abstract void setSelectedForeground(Color color);

    public abstract void setSelectionEnd(int i);

    public abstract void setSelectionList(int ai[]);

    public abstract void setSelectionStart(int i);

    public abstract void setShowCursorPosition(boolean flag);

    public abstract void setStringCase(int i);

    public abstract void setText(String s);

    public abstract void showPosition(int i);
}
