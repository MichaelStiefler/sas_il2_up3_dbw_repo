// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GWindowManager.java

package com.maddox.gwindow;


// Referenced classes of package com.maddox.gwindow:
//            GRegion, GWindowRoot, GCanvas, GSize, 
//            GPoint, GWindowLookAndFeel

public abstract class GWindowManager
{

    public GWindowManager()
    {
        bTimeActive = false;
        bKeyboardActive = false;
        bMouseActive = false;
        bJoyActive = false;
        bTimeGameActive = true;
        bKeyboardGameActive = true;
        bMouseGameActive = true;
        bJoyGameActive = true;
    }

    public boolean isTimeActive()
    {
        return bTimeActive;
    }

    public boolean isKeyboardActive()
    {
        return bKeyboardActive;
    }

    public boolean isMouseActive()
    {
        return bMouseActive;
    }

    public boolean isJoyActive()
    {
        return bJoyActive;
    }

    public boolean isTimeGameActive()
    {
        return bTimeGameActive;
    }

    public boolean isKeyboardGameActive()
    {
        return bKeyboardGameActive;
    }

    public boolean isMouseGameActive()
    {
        return bMouseGameActive;
    }

    public boolean isJoyGameActive()
    {
        return bJoyGameActive;
    }

    public void setTimeGameActive(boolean flag)
    {
        bTimeGameActive = flag;
    }

    public void setKeyboardGameActive(boolean flag)
    {
        bKeyboardGameActive = flag;
    }

    public void setMouseGameActive(boolean flag)
    {
        bMouseGameActive = flag;
    }

    public void setJoyGameActive(boolean flag)
    {
        bJoyGameActive = flag;
    }

    public void activateTime(boolean flag)
    {
        bTimeActive = flag;
    }

    public void activateKeyboard(boolean flag)
    {
        bKeyboardActive = flag;
    }

    public void activateMouse(boolean flag)
    {
        bMouseActive = flag;
    }

    public void activateJoy(boolean flag)
    {
        bJoyActive = flag;
    }

    public void unActivateAll()
    {
        activateTime(false);
        activateKeyboard(false);
        activateMouse(false);
        activateJoy(false);
    }

    public void doPreRender(float f)
    {
        root.deltaTimeSec = f;
        root.doPreRender();
    }

    public void doRender()
    {
        root.bInRender = true;
        root.doRender();
        root.bInRender = false;
    }

    public void doCanvasResize(int i, int j)
    {
        root.C.size.set(i, j);
        root.C.clip.set(0.0F, 0.0F, i, j);
        root.win.set(0.0F, 0.0F, i, j);
        root.doResolutionChanged();
    }

    public void doMouseButton(int i, boolean flag)
    {
        if(!bMouseActive)
        {
            return;
        } else
        {
            root.doMouseButton(i, flag, root.mousePos.x, root.mousePos.y);
            return;
        }
    }

    public void doMouseMove(int i, int j, int k)
    {
        if(!bMouseActive)
        {
            return;
        } else
        {
            root.doMouseMove(i, j, k);
            return;
        }
    }

    public void doMouseAbsMove(int i, int j, int k)
    {
        if(!bMouseActive)
        {
            return;
        } else
        {
            root.doMouseAbsMove(i, j, k);
            return;
        }
    }

    public void doKeyboardKey(int i, boolean flag)
    {
        if(!bKeyboardActive)
        {
            return;
        } else
        {
            root.doKeyboardKey(i, flag);
            return;
        }
    }

    public void doKeyboardChar(char c)
    {
        if(!bKeyboardActive)
        {
            return;
        } else
        {
            root.doKeyboardChar(c);
            return;
        }
    }

    public void doJoyButton(int i, int j, boolean flag)
    {
        if(!bJoyActive)
        {
            return;
        } else
        {
            root.doJoyButton(i, j, flag);
            return;
        }
    }

    public void doJoyMove(int i, int j, int k)
    {
        if(!bJoyActive)
        {
            return;
        } else
        {
            root.doJoyMove(i, j, k);
            return;
        }
    }

    public void doJoyPov(int i, int j)
    {
        if(!bJoyActive)
        {
            return;
        } else
        {
            root.doJoyPov(i, j);
            return;
        }
    }

    public void doJoyPoll()
    {
        if(!bJoyActive)
        {
            return;
        } else
        {
            root.doJoyPoll();
            return;
        }
    }

    public void doCreate(com.maddox.gwindow.GCanvas gcanvas, com.maddox.gwindow.GWindowRoot gwindowroot, com.maddox.gwindow.GWindowLookAndFeel gwindowlookandfeel)
    {
        root = gwindowroot;
        gwindowroot.beforeCreate();
        gwindowroot.C = gcanvas;
        gwindowroot.manager = this;
        gwindowroot.win = new GRegion(0.0F, 0.0F, gcanvas.size.dx, gcanvas.size.dy);
        gwindowroot.lookAndFeel = gwindowlookandfeel;
        gwindowlookandfeel.init(gwindowroot);
        gwindowroot.created();
    }

    protected boolean bTimeActive;
    protected boolean bKeyboardActive;
    protected boolean bMouseActive;
    protected boolean bJoyActive;
    protected boolean bTimeGameActive;
    protected boolean bKeyboardGameActive;
    protected boolean bMouseGameActive;
    protected boolean bJoyGameActive;
    public com.maddox.gwindow.GWindowRoot root;
}
