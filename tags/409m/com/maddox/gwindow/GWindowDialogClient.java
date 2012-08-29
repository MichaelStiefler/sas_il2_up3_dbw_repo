// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GWindowDialogClient.java

package com.maddox.gwindow;

import java.util.ArrayList;

// Referenced classes of package com.maddox.gwindow:
//            GWindowClient, GWindowDialogControl, GWindowRoot, GCaption, 
//            GWindow

public class GWindowDialogClient extends com.maddox.gwindow.GWindowClient
{

    public GWindowDialogClient()
    {
        tabs = new ArrayList();
    }

    public com.maddox.gwindow.GWindowDialogControl addLabel(com.maddox.gwindow.GWindowDialogControl gwindowdialogcontrol)
    {
        gwindowdialogcontrol.bAcceptsKeyFocus = false;
        gwindowdialogcontrol.bTransient = true;
        return gwindowdialogcontrol;
    }

    public com.maddox.gwindow.GWindowDialogControl addDefault(com.maddox.gwindow.GWindowDialogControl gwindowdialogcontrol)
    {
        return addControl(3, gwindowdialogcontrol);
    }

    public com.maddox.gwindow.GWindowDialogControl addControl(com.maddox.gwindow.GWindowDialogControl gwindowdialogcontrol)
    {
        return addControl(1, gwindowdialogcontrol);
    }

    public com.maddox.gwindow.GWindowDialogControl addEscape(com.maddox.gwindow.GWindowDialogControl gwindowdialogcontrol)
    {
        com.maddox.gwindow.GWindowDialogControl gwindowdialogcontrol1 = addControl(1, gwindowdialogcontrol);
        setEscape(gwindowdialogcontrol1);
        return gwindowdialogcontrol1;
    }

    public com.maddox.gwindow.GWindowDialogControl addControl(int i, com.maddox.gwindow.GWindowDialogControl gwindowdialogcontrol)
    {
        int j = tabs.indexOf(gwindowdialogcontrol);
        if(j >= 0)
            tabs.remove(j);
        tabs.add(gwindowdialogcontrol);
        gwindowdialogcontrol.bTabStop = (i & 1) != 0;
        if((i & 2) != 0)
            defaultControl = gwindowdialogcontrol;
        setDefault(defaultControl);
        return gwindowdialogcontrol;
    }

    public void removeControl(com.maddox.gwindow.GWindowDialogControl gwindowdialogcontrol)
    {
        int i = tabs.indexOf(gwindowdialogcontrol);
        if(i >= 0)
            tabs.remove(i);
        if(gwindowdialogcontrol == defaultControl)
            defaultControl = null;
        if(gwindowdialogcontrol == escapeControl)
            escapeControl = null;
    }

    public void setDefault(com.maddox.gwindow.GWindowDialogControl gwindowdialogcontrol)
    {
        defaultControl = gwindowdialogcontrol;
        if(defaultControl != null && defaultControl.bEnable)
            defaultControl.activateWindow();
    }

    public void setEscape(com.maddox.gwindow.GWindowDialogControl gwindowdialogcontrol)
    {
        escapeControl = gwindowdialogcontrol;
    }

    public void keyboardKey(int i, boolean flag)
    {
        super.keyboardKey(i, flag);
        doNotify(flag ? 10 : 11, i);
    }

    public void keyboardChar(char c)
    {
        super.keyboardChar(c);
        doNotify(12, c);
    }

    public boolean notify(com.maddox.gwindow.GWindow gwindow, int i, int j)
    {
        if(super.notify(gwindow, i, j))
            return true;
        else
            return doNotify(i, j);
    }

    public boolean doNotify(int i, int j)
    {
        if(i == 17)
            return notify(i, j);
        if(root.bMouseCapture)
            return false;
        if(i == 12)
        {
            char c = (char)j;
            int l = tabs.size();
            for(int i1 = 0; i1 < l; i1++)
            {
                com.maddox.gwindow.GWindowDialogControl gwindowdialogcontrol = (com.maddox.gwindow.GWindowDialogControl)tabs.get(i1);
                if(gwindowdialogcontrol.bEnable && gwindowdialogcontrol.bVisible && gwindowdialogcontrol.cap != null && gwindowdialogcontrol.bAcceptsKeyFocus && !gwindowdialogcontrol.bTransient && java.lang.Character.toLowerCase(gwindowdialogcontrol.cap.hotKey) == java.lang.Character.toLowerCase(c))
                {
                    gwindowdialogcontrol.activateWindow();
                    gwindowdialogcontrol.notify(2, 0);
                    return true;
                }
            }

            return false;
        }
        if(i != 11)
            return false;
        int k = j;
        if(k == 10)
        {
            if(defaultControl != null && defaultControl.bEnable && defaultControl.bVisible)
            {
                defaultControl.activateWindow();
                defaultControl.notify(2, 0);
            }
            return false;
        }
        if(k == 27)
        {
            if(escapeControl != null && escapeControl.bEnable && escapeControl.bVisible)
            {
                escapeControl.activateWindow();
                escapeControl.notify(2, 0);
            }
            return false;
        }
        com.maddox.gwindow.GWindow gwindow = activeWindow;
        if(k == 9)
        {
            int j1 = tabs.indexOf(gwindow);
            if(j1 < 0)
                return false;
            int i2 = tabs.size();
            for(int l2 = 0; l2 < i2; l2++)
            {
                com.maddox.gwindow.GWindowDialogControl gwindowdialogcontrol3 = (com.maddox.gwindow.GWindowDialogControl)tabs.get(++j1 % i2);
                if(gwindowdialogcontrol3.bEnable && gwindowdialogcontrol3.bVisible && gwindowdialogcontrol3.bTabStop && gwindowdialogcontrol3.bAcceptsKeyFocus && !gwindowdialogcontrol3.bTransient)
                {
                    gwindowdialogcontrol3.activateWindow();
                    return true;
                }
            }

            return false;
        }
        if(k == 39 || k == 40)
        {
            int k1 = tabs.size();
            int j2 = tabs.indexOf(gwindow);
            if(j2 < 0)
                return false;
            com.maddox.gwindow.GWindowDialogControl gwindowdialogcontrol1 = (com.maddox.gwindow.GWindowDialogControl)tabs.get(j2);
            int i3 = j2;
            while(k1-- > 0) 
            {
                i3 = (i3 + 1) % tabs.size();
                com.maddox.gwindow.GWindowDialogControl gwindowdialogcontrol4 = (com.maddox.gwindow.GWindowDialogControl)tabs.get(i3);
                if(!gwindowdialogcontrol1.bTabStop && (gwindowdialogcontrol1.bTabStop || gwindowdialogcontrol4.bTabStop))
                    break;
                if(gwindowdialogcontrol4.bEnable && gwindowdialogcontrol4.bVisible && gwindowdialogcontrol4.bAcceptsKeyFocus && !gwindowdialogcontrol4.bTransient)
                {
                    gwindowdialogcontrol4.activateWindow();
                    break;
                }
            }
            return true;
        }
        if(k == 37 || k == 38)
        {
            int l1 = tabs.size();
            int k2 = tabs.indexOf(gwindow);
            if(k2 < 0)
                return false;
            com.maddox.gwindow.GWindowDialogControl gwindowdialogcontrol2 = (com.maddox.gwindow.GWindowDialogControl)tabs.get(k2);
            int j3 = k2;
            while(l1-- > 0) 
            {
                j3 = ((j3 + tabs.size()) - 1) % tabs.size();
                com.maddox.gwindow.GWindowDialogControl gwindowdialogcontrol5 = (com.maddox.gwindow.GWindowDialogControl)tabs.get(j3);
                if((!gwindowdialogcontrol2.bTabStop || !gwindowdialogcontrol5.bTabStop) && gwindowdialogcontrol2.bTabStop)
                    break;
                if(gwindowdialogcontrol5.bEnable && gwindowdialogcontrol5.bVisible && gwindowdialogcontrol5.bAcceptsKeyFocus && !gwindowdialogcontrol5.bTransient)
                {
                    gwindowdialogcontrol5.activateWindow();
                    break;
                }
            }
            return true;
        } else
        {
            return false;
        }
    }

    public void created()
    {
        bNotify = true;
    }

    public com.maddox.gwindow.GWindowDialogControl defaultControl;
    public com.maddox.gwindow.GWindowDialogControl escapeControl;
    public java.util.ArrayList tabs;
}
