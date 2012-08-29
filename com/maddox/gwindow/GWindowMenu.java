// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GWindowMenu.java

package com.maddox.gwindow;

import java.util.ArrayList;

// Referenced classes of package com.maddox.gwindow:
//            GWindow, GWindowMenuItem, GWindowMenuBarItem, GSize, 
//            GRegion, GPoint, GWindowRoot, GWindowLookAndFeel, 
//            GWindowMenuBar, GCaption

public class GWindowMenu extends com.maddox.gwindow.GWindow
{

    public GWindowMenu()
    {
        items = new ArrayList();
        columns = 1;
    }

    public com.maddox.gwindow.GWindowMenuItem addItem(java.lang.String s, java.lang.String s1)
    {
        com.maddox.gwindow.GWindowMenuItem gwindowmenuitem = new GWindowMenuItem(this, s, s1);
        if("-".equals(s))
            gwindowmenuitem.bEnable = false;
        return addItem(gwindowmenuitem);
    }

    public com.maddox.gwindow.GWindowMenuItem addItem(int i, java.lang.String s, java.lang.String s1)
    {
        com.maddox.gwindow.GWindowMenuItem gwindowmenuitem = new GWindowMenuItem(this, s, s1);
        if("-".equals(s))
            gwindowmenuitem.bEnable = false;
        return addItem(i, gwindowmenuitem);
    }

    public com.maddox.gwindow.GWindowMenuItem addItem(com.maddox.gwindow.GWindowMenuItem gwindowmenuitem)
    {
        items.add(gwindowmenuitem);
        computePosSize();
        return gwindowmenuitem;
    }

    public com.maddox.gwindow.GWindowMenuItem addItem(int i, com.maddox.gwindow.GWindowMenuItem gwindowmenuitem)
    {
        items.add(i, gwindowmenuitem);
        computePosSize();
        return gwindowmenuitem;
    }

    public com.maddox.gwindow.GWindowMenuItem getItem(int i)
    {
        return (com.maddox.gwindow.GWindowMenuItem)items.get(i);
    }

    public int size()
    {
        return items.size();
    }

    public void clearItems()
    {
        int i = items.size();
        if(i == 0)
            return;
        for(int j = 0; j < i; j++)
        {
            com.maddox.gwindow.GWindowMenuItem gwindowmenuitem = (com.maddox.gwindow.GWindowMenuItem)items.get(j);
            gwindowmenuitem.close(false);
        }

        items.clear();
        selected = null;
        close(false);
    }

    private void computePosSize()
    {
        com.maddox.gwindow.GSize gsize = getMinSize();
        setSize(gsize.dx, gsize.dy);
        int i = items.size();
        com.maddox.gwindow.GRegion gregion = getClientRegion();
        float f = gregion.x;
        float f1 = gregion.y;
        float f2 = (gregion.dx - (win.dx - gregion.dx) * (float)(columns - 1)) / (float)columns;
        float f3 = f2 + (win.dx - gregion.dx);
        for(int j = 0; j < i; j++)
        {
            com.maddox.gwindow.GWindowMenuItem gwindowmenuitem = (com.maddox.gwindow.GWindowMenuItem)items.get(j);
            com.maddox.gwindow.GSize gsize1 = gwindowmenuitem.getMinSize();
            gwindowmenuitem.setSize(f2, gsize1.dy);
            gwindowmenuitem.setPos(f, f1);
            if(f1 + gwindowmenuitem.win.dy > gregion.dy)
            {
                f1 = gregion.y;
                f += f3;
            } else
            {
                f1 += gwindowmenuitem.win.dy;
            }
        }

    }

    public void resolutionChanged()
    {
        computePosSize();
    }

    public void setPos(float f, float f1)
    {
        if(parentWindow != null)
        {
            com.maddox.gwindow.GPoint gpoint = parentWindow.windowToGlobal(f, f1);
            float f2 = gpoint.x;
            float f3 = gpoint.y;
            if(f2 + win.dx > root.win.dx)
                f2 = root.win.dx - win.dx;
            if(f3 + win.dy > root.win.dy)
                f3 = root.win.dy - win.dy;
            if(f2 < 0.0F)
                f2 = 0.0F;
            if(f3 < 0.0F)
                f3 = 0.0F;
            gpoint = parentWindow.globalToWindow(f2, f3);
            f = gpoint.x;
            f1 = gpoint.y;
        }
        super.setPos(f, f1);
    }

    public void beforeExecute(com.maddox.gwindow.GWindowMenuItem gwindowmenuitem)
    {
        if(gwindowmenuitem != null)
            lAF().soundPlay("WindowOpen");
        if(parentWindow instanceof com.maddox.gwindow.GWindowMenuItem)
        {
            com.maddox.gwindow.GWindowMenuItem gwindowmenuitem1 = (com.maddox.gwindow.GWindowMenuItem)parentWindow;
            gwindowmenuitem1.menu().beforeExecute(null);
        } else
        if(parentWindow instanceof com.maddox.gwindow.GWindowMenuBarItem)
        {
            com.maddox.gwindow.GWindowMenuBarItem gwindowmenubaritem = (com.maddox.gwindow.GWindowMenuBarItem)parentWindow;
            com.maddox.gwindow.GWindowMenuBar gwindowmenubar = gwindowmenubaritem.menuBar();
            gwindowmenubar.setOver(null);
            gwindowmenubar.setSelected(null);
        } else
        {
            close(false);
        }
        setSelected(null);
    }

    public void execute(com.maddox.gwindow.GWindowMenuItem gwindowmenuitem)
    {
        gwindowmenuitem.execute();
    }

    public void doExecute(com.maddox.gwindow.GWindowMenuItem gwindowmenuitem)
    {
        if(gwindowmenuitem.subMenu() == null)
        {
            beforeExecute(gwindowmenuitem);
            execute(gwindowmenuitem);
        } else
        {
            setSelected(gwindowmenuitem);
        }
    }

    public void windowShown()
    {
        super.windowShown();
        selected = null;
    }

    protected void setSelected(com.maddox.gwindow.GWindowMenuItem gwindowmenuitem)
    {
        if(selected == gwindowmenuitem)
            return;
        if(selected != null)
            selected.unSelect();
        selected = gwindowmenuitem;
        if(gwindowmenuitem != null)
        {
            toolTip(gwindowmenuitem.toolTip);
            selected.select();
        } else
        {
            toolTip(null);
        }
    }

    protected void nextSelected(int i, int j)
    {
        int k = items.size();
        for(int l = k; l-- > 0;)
        {
            com.maddox.gwindow.GWindowMenuItem gwindowmenuitem = (com.maddox.gwindow.GWindowMenuItem)items.get(i);
            if(gwindowmenuitem.bEnable)
            {
                setSelected(gwindowmenuitem);
                return;
            }
            i = (i + j) % k;
        }

    }

    public void keyboardKey(int i, boolean flag)
    {
        super.keyboardKey(i, flag);
        int j = items.size();
        if(!flag && j > 0)
            switch(i)
            {
            default:
                break;

            case 27: // '\033'
                if(selected != null)
                {
                    setSelected(null);
                    return;
                }
                break;

            case 10: // '\n'
                if(selected != null)
                    doExecute(selected);
                return;

            case 37: // '%'
                if(parentWindow instanceof com.maddox.gwindow.GWindowMenuItem)
                    i = 38;
                break;

            case 38: // '&'
                if(selected == null)
                {
                    nextSelected(j - 1, -1);
                } else
                {
                    int k = items.indexOf(selected);
                    if(k == 0)
                        k = j;
                    nextSelected(k - 1, -1);
                }
                return;

            case 39: // '\''
                if(parentWindow instanceof com.maddox.gwindow.GWindowMenuBarItem)
                    break;
                // fall through

            case 40: // '('
                if(selected == null)
                {
                    nextSelected(0, 1);
                } else
                {
                    int l = items.indexOf(selected);
                    if(l == j - 1)
                        l = -1;
                    nextSelected(l + 1, 1);
                }
                return;
            }
        parentWindow.keyboardKey(i, flag);
    }

    public void keyboardChar(char c)
    {
        super.keyboardChar(c);
        int i = items.size();
        int j = 0;
        do
        {
            if(j >= i)
                break;
            com.maddox.gwindow.GWindowMenuItem gwindowmenuitem = (com.maddox.gwindow.GWindowMenuItem)items.get(j);
            if(java.lang.Character.toLowerCase(gwindowmenuitem.cap.hotKey) == java.lang.Character.toLowerCase(c))
            {
                doExecute(gwindowmenuitem);
                break;
            }
            j++;
        } while(true);
    }

    public void created()
    {
        super.created();
        bAlwaysOnTop = true;
        bClip = false;
    }

    public void render()
    {
        lookAndFeel().render(this);
    }

    public com.maddox.gwindow.GSize getMinSize(com.maddox.gwindow.GSize gsize)
    {
        return lookAndFeel().getMinSize(this, gsize);
    }

    public com.maddox.gwindow.GRegion getClientRegion(com.maddox.gwindow.GRegion gregion, float f)
    {
        return lookAndFeel().getClientRegion(this, gregion, f);
    }

    public java.util.ArrayList items;
    public com.maddox.gwindow.GWindowMenuItem selected;
    public int columns;
}
