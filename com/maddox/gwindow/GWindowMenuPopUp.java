// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GWindowMenuPopUp.java

package com.maddox.gwindow;

import java.util.ArrayList;

// Referenced classes of package com.maddox.gwindow:
//            GWindowMenu, GWindowLookAndFeel, GWindowRoot, GPoint, 
//            GWindow, GWindowMenuItem

public class GWindowMenuPopUp extends com.maddox.gwindow.GWindowMenu
{

    public GWindowMenuPopUp()
    {
    }

    public void beforeExecute(com.maddox.gwindow.GWindowMenuItem gwindowmenuitem)
    {
        if(gwindowmenuitem != null)
            lAF().soundPlay("WindowOpen");
        close(false);
        setSelected(null);
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
                close(false);
                setSelected(null);
                break;

            case 10: // '\n'
                if(selected != null)
                    doExecute(selected);
                break;

            case 38: // '&'
                if(selected == null)
                {
                    nextSelected(j - 1, -1);
                    break;
                }
                int k = items.indexOf(selected);
                if(k == 0)
                    k = j;
                nextSelected(k - 1, -1);
                break;

            case 40: // '('
                if(selected == null)
                {
                    nextSelected(0, 1);
                    break;
                }
                int l = items.indexOf(selected);
                if(l == j - 1)
                    l = -1;
                nextSelected(l + 1, 1);
                break;
            }
    }

    public void msgMouseButton(boolean flag, int i, boolean flag1, float f, float f1)
    {
        if(flag)
            return;
        if(!flag1)
            return;
        com.maddox.gwindow.GWindow gwindow = root.findWindowUnder(root.mousePos.x, root.mousePos.y);
        com.maddox.gwindow.GWindow gwindow1 = gwindow.getParent(com.maddox.gwindow.GWindowMenuPopUp.class, false);
        if(gwindow1 != this)
        {
            close(false);
            setSelected(null);
        }
    }

    public void created()
    {
        super.created();
        bMouseListener = true;
        bAcceptsHotKeys = true;
    }
}
