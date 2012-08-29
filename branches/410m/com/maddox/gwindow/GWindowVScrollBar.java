// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GWindowVScrollBar.java

package com.maddox.gwindow;


// Referenced classes of package com.maddox.gwindow:
//            GWindowDialogControl, GWindowRoot, GWindowLookAndFeel, GWindow, 
//            GWindowButton, GSize, GRegion, GWindowButtonTexture

public class GWindowVScrollBar extends com.maddox.gwindow.GWindowDialogControl
{
    public class MButton extends com.maddox.gwindow.GWindowButton
    {

        public void mouseButton(int i, boolean flag, float f, float f1)
        {
            super.mouseButton(i, flag, f, f1);
            if(i != 0)
                return;
            if(!bEnable)
            {
                mouseCapture(false);
                return;
            } else
            {
                mouseCapture(flag);
                return;
            }
        }

        public void mouseMove(float f, float f1)
        {
            if(bEnable && isMouseCaptured())
            {
                com.maddox.gwindow.GWindowVScrollBar gwindowvscrollbar = (com.maddox.gwindow.GWindowVScrollBar)parentWindow;
                scroll((root.mouseStep.dy * (posMax - posMin)) / (gwindowvscrollbar.win.dy - gwindowvscrollbar.uButton.win.dy - gwindowvscrollbar.uButton.win.dy - win.dy));
            }
        }

        public void mouseRelMove(float f, float f1, float f2)
        {
            super.mouseRelMove(f, f1, f2);
            scrollDz(f2);
        }

        public void created()
        {
            bAcceptsKeyFocus = false;
            bDrawOnlyUP = true;
            bDrawActive = false;
        }

        public MButton(com.maddox.gwindow.GWindow gwindow)
        {
            super(gwindow);
        }
    }

    public class DButton extends com.maddox.gwindow.GWindowButtonTexture
    {

        public void mouseButton(int i, boolean flag, float f, float f1)
        {
            super.mouseButton(i, flag, f, f1);
            if(i != 0 || !flag || !bEnable)
            {
                return;
            } else
            {
                scroll(scroll);
                timeout = 0.5F;
                return;
            }
        }

        public void mouseRelMove(float f, float f1, float f2)
        {
            super.mouseRelMove(f, f1, f2);
            scrollDz(f2);
        }

        public void preRender()
        {
            super.preRender();
            if(bDown && bEnable)
            {
                timeout-= = root.deltaTimeSec;
                if(timeout <= 0.0F)
                {
                    timeout = 0.1F;
                    scroll(scroll);
                }
            }
        }

        public void created()
        {
            bAcceptsKeyFocus = false;
            lookAndFeel().setupScrollButtonDOWN(this);
        }

        public DButton(com.maddox.gwindow.GWindow gwindow)
        {
            super(gwindow);
        }
    }

    public class UButton extends com.maddox.gwindow.GWindowButtonTexture
    {

        public void mouseButton(int i, boolean flag, float f, float f1)
        {
            super.mouseButton(i, flag, f, f1);
            if(i != 0 || !flag || !bEnable)
            {
                return;
            } else
            {
                scroll(-scroll);
                timeout = 0.5F;
                return;
            }
        }

        public void mouseRelMove(float f, float f1, float f2)
        {
            super.mouseRelMove(f, f1, f2);
            scrollDz(f2);
        }

        public void preRender()
        {
            super.preRender();
            if(bDown && bEnable)
            {
                timeout-= = root.deltaTimeSec;
                if(timeout <= 0.0F)
                {
                    timeout = 0.1F;
                    scroll(-scroll);
                }
            }
        }

        public void created()
        {
            bAcceptsKeyFocus = false;
            lookAndFeel().setupScrollButtonUP(this);
        }

        public UButton(com.maddox.gwindow.GWindow gwindow)
        {
            super(gwindow);
        }
    }


    public float pos()
    {
        return pos;
    }

    public boolean scroll(float f)
    {
        return setPos(pos + f, true);
    }

    public boolean setPos(float f, boolean flag)
    {
        float f1 = pos;
        pos = f;
        resized();
        if(f1 != pos)
        {
            if(flag)
                notify(2, 0);
            return true;
        } else
        {
            return false;
        }
    }

    public void setRange(float f, float f1, float f2, float f3, float f4)
    {
        posMin = f;
        posMax = f1 - f2;
        posVisible = f2;
        pos = f4;
        scroll = f3;
        resized();
    }

    public boolean checkRange()
    {
        if(pos > posMax)
            pos = posMax;
        if(pos < posMin)
            pos = posMin;
        boolean flag = posMin < posMax;
        if(flag != bEnable)
            _setEnable(flag);
        return bEnable;
    }

    public void _setEnable(boolean flag)
    {
        super.setEnable(flag);
        uButton.setEnable(flag);
        mButton.setEnable(flag);
        dButton.setEnable(flag);
    }

    public void setEnable(boolean flag)
    {
        if(flag != bEnable)
        {
            if(!checkRange() && flag)
                return;
            _setEnable(flag);
        }
    }

    public void cancelAcceptsKeyFocus()
    {
        bAcceptsKeyFocus = false;
    }

    public void keyboardKey(int i, boolean flag)
    {
        bKeyDown = flag;
        switch(i)
        {
        case 38: // '&'
            if(flag && bEnable)
            {
                timeoutScroll = -scroll;
                scroll(timeoutScroll);
            }
            return;

        case 40: // '('
            if(flag && bEnable)
            {
                timeoutScroll = scroll;
                scroll(timeoutScroll);
            }
            return;

        case 33: // '!'
            if(flag && bEnable)
            {
                timeoutScroll = -(posVisible - scroll);
                scroll(timeoutScroll);
            }
            return;

        case 34: // '"'
            if(flag && bEnable)
            {
                timeoutScroll = posVisible - scroll;
                scroll(timeoutScroll);
            }
            return;

        case 36: // '$'
            if(flag && bEnable)
                setPos(posMin, true);
            bKeyDown = false;
            return;

        case 35: // '#'
            if(flag && bEnable)
                setPos(posMax, true);
            bKeyDown = false;
            return;

        case 37: // '%'
        case 39: // '\''
        default:
            bKeyDown = false;
            super.keyboardKey(i, flag);
            return;
        }
    }

    public void mouseButton(int i, boolean flag, float f, float f1)
    {
        super.mouseButton(i, flag, f, f1);
        if(i != 0 || !bEnable)
            return;
        if(!flag)
        {
            downState = 0;
            return;
        } else
        {
            downState = f1 > yM ? 2 : 1;
            timeoutScroll = downState != 1 ? posVisible - scroll : -(posVisible - scroll);
            scroll(timeoutScroll);
            timeout = 0.5F;
            return;
        }
    }

    public void scrollDz(float f)
    {
        if(f == 0.0F)
        {
            return;
        } else
        {
            scroll((-scroll * f) / 5F);
            return;
        }
    }

    public boolean notify(int i, int j)
    {
        if(i == 17)
        {
            scrollDz(root.mouseRelMoveZ);
            return true;
        } else
        {
            return super.notify(i, j);
        }
    }

    public void preRender()
    {
        super.preRender();
        if(bEnable && (bDown || bKeyDown))
        {
            timeout -= root.deltaTimeSec;
            if(timeout <= 0.0F)
            {
                timeout = 0.1F;
                scroll(timeoutScroll);
            }
        }
    }

    public void render()
    {
        lookAndFeel().render(this);
    }

    public void resized()
    {
        checkRange();
        lookAndFeel().setupVScrollBarSizes(this);
    }

    public void created()
    {
        super.created();
    }

    public void afterCreated()
    {
        super.afterCreated();
        uButton = new UButton(this);
        mButton = new MButton(this);
        dButton = new DButton(this);
        resized();
    }

    public void resolutionChanged()
    {
        boolean flag = bEnable;
        resized();
        if(!flag)
            setEnable(false);
    }

    public GWindowVScrollBar(com.maddox.gwindow.GWindow gwindow)
    {
        posMin = 0.0F;
        posVisible = 0.2F;
        posMax = 1.0F - posVisible;
        pos = (posMax - posMin) / 2.0F;
        scroll = 0.1F;
        downState = 0;
        timeout = 0.0F;
        timeoutScroll = 0.0F;
        bKeyDown = false;
        doNew(gwindow, 0.0F, 0.0F, 1.0F, 1.0F, false);
    }

    public GWindowVScrollBar(com.maddox.gwindow.GWindow gwindow, float f, float f1, float f2, float f3, float f4, float f5, 
            float f6)
    {
        posMin = 0.0F;
        posVisible = 0.2F;
        posMax = 1.0F - posVisible;
        pos = (posMax - posMin) / 2.0F;
        scroll = 0.1F;
        downState = 0;
        timeout = 0.0F;
        timeoutScroll = 0.0F;
        bKeyDown = false;
        posMin = f;
        posVisible = f2;
        posMax = f1 - f2;
        pos = (posMax + posMin) / 2.0F;
        scroll = f3;
        float f7 = gwindow.lookAndFeel().getVScrollBarW() / gwindow.lookAndFeel().metric();
        doNew(gwindow, f4, f5, f7, f6, true);
    }

    public float posMin;
    public float posVisible;
    public float posMax;
    public float pos;
    public float scroll;
    public com.maddox.gwindow.UButton uButton;
    public com.maddox.gwindow.DButton dButton;
    public com.maddox.gwindow.MButton mButton;
    public static final int DOWN_NONE = 0;
    public static final int DOWN_MIN = 1;
    public static final int DOWN_MAX = 2;
    public int downState;
    public float yM;
    public float dyM;
    private float timeout;
    private float timeoutScroll;
    public boolean bKeyDown;



}
