// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GWindowDialogControl.java

package com.maddox.gwindow;


// Referenced classes of package com.maddox.gwindow:
//            GWindow, GWindowDialogClient, GCaption

public class GWindowDialogControl extends com.maddox.gwindow.GWindow
{

    public boolean isDefault()
    {
        if(!(parentWindow instanceof com.maddox.gwindow.GWindowDialogClient))
        {
            return false;
        } else
        {
            com.maddox.gwindow.GWindowDialogClient gwindowdialogclient = (com.maddox.gwindow.GWindowDialogClient)parentWindow;
            return gwindowdialogclient.defaultControl == this;
        }
    }

    public boolean isEscape()
    {
        if(!(parentWindow instanceof com.maddox.gwindow.GWindowDialogClient))
        {
            return false;
        } else
        {
            com.maddox.gwindow.GWindowDialogClient gwindowdialogclient = (com.maddox.gwindow.GWindowDialogClient)parentWindow;
            return gwindowdialogclient.escapeControl == this;
        }
    }

    public boolean isEnable()
    {
        return bEnable;
    }

    public void setEnable(boolean flag)
    {
        bEnable = flag;
    }

    public void checkEnabling()
    {
    }

    public void setToolTip(java.lang.String s)
    {
        toolTip = s;
    }

    public boolean _notify(int i, int j)
    {
        return notify(i, j);
    }

    public void windowShown()
    {
        checkEnabling();
        super.windowShown();
    }

    public void resolutionChanged()
    {
        super.resolutionChanged();
        if(cap != null && cap.offsetHotKey > 0)
            cap.offsetHotKey = -1;
    }

    public void keyboardKey(int i, boolean flag)
    {
        super.keyboardKey(i, flag);
        if(i != 32)
            return;
        if(!bEnable)
        {
            bDown = false;
            return;
        }
        bDown = flag;
        if(!flag)
            _notify(2, 0);
    }

    public void mouseButton(int i, boolean flag, float f, float f1)
    {
        super.mouseButton(i, flag, f, f1);
        if(i != 0)
            return;
        if(!bEnable)
        {
            bDown = false;
            return;
        } else
        {
            bDown = flag;
            return;
        }
    }

    public void mouseClick(int i, float f, float f1)
    {
        super.mouseClick(i, f, f1);
        if(!bEnable)
        {
            bDown = false;
            return;
        }
        if(i == 0)
            _notify(2, 0);
    }

    public void mouseEnter()
    {
        super.mouseEnter();
        toolTip(toolTip);
    }

    public void mouseLeave()
    {
        super.mouseLeave();
        toolTip(null);
    }

    public GWindowDialogControl()
    {
        bEnable = true;
        bDown = false;
        bTabStop = false;
        align = 0;
        color = 0;
        font = 0;
        bNotify = true;
    }

    public GWindowDialogControl(com.maddox.gwindow.GWindow gwindow)
    {
        bEnable = true;
        bDown = false;
        bTabStop = false;
        align = 0;
        color = 0;
        font = 0;
        bNotify = true;
        doNew(gwindow, 0.0F, 0.0F, 12F, 12F, false);
    }

    public GWindowDialogControl(com.maddox.gwindow.GWindow gwindow, float f, float f1, float f2, float f3)
    {
        bEnable = true;
        bDown = false;
        bTabStop = false;
        align = 0;
        color = 0;
        font = 0;
        bNotify = true;
        doNew(gwindow, f, f1, f2, f3, true);
    }

    public boolean bEnable;
    public boolean bDown;
    public boolean bTabStop;
    public com.maddox.gwindow.GCaption cap;
    public int align;
    public int color;
    public int font;
    public java.lang.String toolTip;
}
