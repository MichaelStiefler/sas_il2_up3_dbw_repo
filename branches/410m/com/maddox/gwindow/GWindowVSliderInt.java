// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GWindowVSliderInt.java

package com.maddox.gwindow;


// Referenced classes of package com.maddox.gwindow:
//            GWindowDialogControl, GRegion, GWindowRoot, GWindowLookAndFeel, 
//            GWindow

public class GWindowVSliderInt extends com.maddox.gwindow.GWindowDialogControl
{

    public int pos()
    {
        return pos;
    }

    public boolean setPos(int i, boolean flag)
    {
        int j = pos;
        pos = i;
        resized();
        if(j != pos)
        {
            if(flag)
                notify(2, 0);
            return true;
        } else
        {
            return false;
        }
    }

    public void setScrollPos(boolean flag, boolean flag1)
    {
        setPos(pos + (flag ? 1 : -1), flag1);
    }

    public void setRange(int i, int j, int k)
    {
        posStart = i;
        posCount = j;
        pos = k;
        resized();
    }

    public void checkRange()
    {
        if(pos < posStart)
            pos = posStart;
        if(pos >= posStart + posCount)
            pos = (posStart + posCount) - 1;
        if(posEnable != null)
        {
            for(int i = 0; i < posCount; i++)
            {
                if(posEnable[pos - posStart])
                    return;
                if(pos == posStart)
                    return;
                pos--;
            }

        }
    }

    public void keyboardKey(int i, boolean flag)
    {
        switch(i)
        {
        case 40: // '('
            if(flag && bEnable)
                setScrollPos(false, true);
            return;

        case 38: // '&'
            if(flag && bEnable)
                setScrollPos(true, true);
            return;

        case 35: // '#'
            if(flag && bEnable)
                setPos(posStart, true);
            return;

        case 36: // '$'
            if(flag && bEnable)
                setPos((posStart + posCount) - 1, true);
            return;

        case 37: // '%'
        case 39: // '\''
        default:
            super.keyboardKey(i, flag);
            return;
        }
    }

    public void mouseButton(int i, boolean flag, float f, float f1)
    {
        super.mouseButton(i, flag, f, f1);
        if(!bEnable)
        {
            mouseCapture(false);
            return;
        }
        if(i != 0)
            return;
        if(isMouseCaptured() && !flag)
        {
            mouseCapture(false);
            if(!bSlidingNotify && posSlidingSave != pos && bNotify)
                notify(2, 0);
            return;
        }
        if(f1 < yM)
        {
            if(flag)
                setScrollPos(true, true);
        } else
        if(f1 > yM + dyM)
        {
            if(flag)
                setScrollPos(false, true);
        } else
        if(flag)
        {
            mouseCapture(true);
            posSlidingSave = pos;
            mouseSlidingY = f1;
        }
    }

    public void mouseMove(float f, float f1)
    {
        if(bEnable && isMouseCaptured())
            setPos((int)(((mouseSlidingY - f1) / win.dy) * (float)posCount + (float)posSlidingSave + 0.5F), bSlidingNotify);
    }

    public void mouseRelMove(float f, float f1, float f2)
    {
        if(bEnable && !isMouseCaptured() && root.mouseRelMoveZ != 0.0F)
            setScrollPos(root.mouseRelMoveZ > 0.0F, true);
    }

    public void render()
    {
        lookAndFeel().render(this);
    }

    public void resized()
    {
        checkRange();
        lookAndFeel().setupVSliderIntSizes(this);
    }

    public void created()
    {
        super.created();
        resized();
    }

    public GWindowVSliderInt(com.maddox.gwindow.GWindow gwindow)
    {
        posStart = 0;
        posCount = 8;
        pos = posStart + posCount / 2;
        bSlidingNotify = false;
        doNew(gwindow, 0.0F, 0.0F, 1.0F, 1.0F, false);
    }

    public GWindowVSliderInt(com.maddox.gwindow.GWindow gwindow, int i, int j, int k, float f, float f1, float f2)
    {
        posStart = 0;
        posCount = 8;
        pos = posStart + posCount / 2;
        bSlidingNotify = false;
        posStart = i;
        posCount = j;
        pos = k;
        float f3 = gwindow.lookAndFeel().getVSliderIntW() / gwindow.lookAndFeel().metric();
        doNew(gwindow, f, f1, f3, f2, true);
    }

    public int posStart;
    public int posCount;
    public int pos;
    public boolean posEnable[];
    public boolean bSlidingNotify;
    private int posSlidingSave;
    private float mouseSlidingY;
    public float yM;
    public float dyM;
}
