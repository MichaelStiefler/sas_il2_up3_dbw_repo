// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GWindowMenuBar.java

package com.maddox.gwindow;

import java.util.ArrayList;

// Referenced classes of package com.maddox.gwindow:
//            GWindow, GWindowMenuBarItem, GWindowMenuItem, GWindowMenu, 
//            GWindowFramed, GRegion, GWindowRoot, GPoint, 
//            GCaption, GWindowLookAndFeel, GSize

public class GWindowMenuBar extends com.maddox.gwindow.GWindow
{

    public GWindowMenuBar()
    {
        items = new ArrayList();
    }

    public com.maddox.gwindow.GWindowMenuBarItem addItem(java.lang.String s, java.lang.String s1)
    {
        com.maddox.gwindow.GWindowMenuBarItem gwindowmenubaritem = new GWindowMenuBarItem(this, s, s1);
        return addItem(gwindowmenubaritem);
    }

    public com.maddox.gwindow.GWindowMenuBarItem addItem(com.maddox.gwindow.GWindowMenuBarItem gwindowmenubaritem)
    {
        int i = items.size();
        com.maddox.gwindow.GRegion gregion = getClientRegion();
        float f = gregion.x;
        if(i > 0)
        {
            for(int j = 0; j < i; j++)
            {
                com.maddox.gwindow.GWindowMenuBarItem gwindowmenubaritem1 = (com.maddox.gwindow.GWindowMenuBarItem)items.get(j);
                f += gwindowmenubaritem1.win.dx;
            }

        }
        items.add(gwindowmenubaritem);
        gwindowmenubaritem.setPos(f, gregion.y);
        return gwindowmenubaritem;
    }

    public com.maddox.gwindow.GWindowMenuBarItem getItem(int i)
    {
        return (com.maddox.gwindow.GWindowMenuBarItem)items.get(i);
    }

    public int countItems()
    {
        return items.size();
    }

    public void resolutionChanged()
    {
        getMinSize();
        int i = items.size();
        com.maddox.gwindow.GRegion gregion = getClientRegion();
        float f = gregion.x;
        if(i > 0)
        {
            for(int j = 0; j < i; j++)
            {
                com.maddox.gwindow.GWindowMenuBarItem gwindowmenubaritem = (com.maddox.gwindow.GWindowMenuBarItem)items.get(j);
                gwindowmenubaritem.setPos(f, gregion.y);
                f += gwindowmenubaritem.win.dx;
            }

        }
    }

    protected void setOver(com.maddox.gwindow.GWindowMenuBarItem gwindowmenubaritem)
    {
        if(gwindowmenubaritem == null)
        {
            toolTip(null);
            over = null;
        } else
        {
            toolTip(gwindowmenubaritem.toolTip);
            over = gwindowmenubaritem;
        }
    }

    protected void setSelected(com.maddox.gwindow.GWindowMenuBarItem gwindowmenubaritem)
    {
        if(selected == gwindowmenubaritem)
            return;
        if(selected != null)
            selected.unSelect();
        selected = gwindowmenubaritem;
        if(gwindowmenubaritem != null)
            selected.select();
    }

    public void msgMouseButton(boolean flag, int i, boolean flag1, float f, float f1)
    {
        if(flag)
            return;
        com.maddox.gwindow.GWindow gwindow = root.findWindowUnder(root.mousePos.x, root.mousePos.y);
        com.maddox.gwindow.GWindow gwindow1 = gwindow.getParent(com.maddox.gwindow.GWindowMenuBar.class, false);
        if(gwindow1 != this || !(gwindow instanceof com.maddox.gwindow.GWindowMenuBarItem) && !(gwindow instanceof com.maddox.gwindow.GWindowMenuBar) && !(gwindow instanceof com.maddox.gwindow.GWindowMenuItem) && !(gwindow instanceof com.maddox.gwindow.GWindowMenu))
        {
            if(over != null)
                setOver(null);
            setSelected(null);
        }
    }

    public boolean hotKey(int i, boolean flag)
    {
        if(i != 18 || items.size() == 0)
            return false;
        bAltDown = flag;
        if(!bAltDown)
            return false;
        if(flag && (over != null || selected != null))
        {
            bAltDown = false;
            return false;
        }
        if(parentWindow instanceof com.maddox.gwindow.GWindowFramed)
        {
            if(!parentWindow.isActivated())
                bAltDown = false;
        } else
        {
            com.maddox.gwindow.GWindow gwindow = root.checkKeyFocusWindow();
            if(gwindow.getParent(com.maddox.gwindow.GWindowFramed.class, false) != null)
                bAltDown = false;
        }
        return false;
    }

    public boolean hotKeyChar(char c)
    {
        if(!bAltDown || over != null || selected != null)
            return false;
        int i = items.size();
        for(int j = 0; j < i; j++)
        {
            com.maddox.gwindow.GWindowMenuBarItem gwindowmenubaritem = (com.maddox.gwindow.GWindowMenuBarItem)items.get(j);
            if(java.lang.Character.toLowerCase(gwindowmenubaritem.cap.hotKey) == java.lang.Character.toLowerCase(c))
            {
                setOver(gwindowmenubaritem);
                setSelected(gwindowmenubaritem);
                return true;
            }
        }

        return false;
    }

    public void keyboardKey(int i, boolean flag)
    {
        super.keyboardKey(i, flag);
        if(flag)
            return;
        int j = items.size();
        if(j == 0)
            return;
        if(over == null)
            return;
        switch(i)
        {
        default:
            break;

        case 27: // '\033'
            setSelected(null);
            break;

        case 10: // '\n'
            setSelected(selected != null ? null : over);
            break;

        case 37: // '%'
            int k = items.indexOf(over);
            if(k == 0)
                k = j;
            setOver((com.maddox.gwindow.GWindowMenuBarItem)items.get(--k));
            if(selected != null)
                setSelected(over);
            break;

        case 39: // '\''
            int l = items.indexOf(over);
            if(l == j - 1)
                l = -1;
            setOver((com.maddox.gwindow.GWindowMenuBarItem)items.get(++l));
            if(selected != null)
                setSelected(over);
            break;
        }
    }

    public void created()
    {
        super.created();
        bAlwaysOnTop = true;
        bMouseListener = true;
        bAcceptsHotKeys = true;
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
    public com.maddox.gwindow.GWindowMenuBarItem selected;
    public com.maddox.gwindow.GWindowMenuBarItem over;
    public boolean bAltDown;
}
