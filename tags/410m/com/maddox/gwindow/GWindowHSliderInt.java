// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GWindowHSliderInt.java

package com.maddox.gwindow;


// Referenced classes of package com.maddox.gwindow:
//            GWindowDialogControl, GRegion, GWindowLookAndFeel, GWindow

public class GWindowHSliderInt extends com.maddox.gwindow.GWindowDialogControl
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
        case 37: // '%'
            if(flag && bEnable)
                setPos(pos - 1, true);
            return;

        case 39: // '\''
            if(flag && bEnable)
                setPos(pos + 1, true);
            return;

        case 36: // '$'
            if(flag && bEnable)
                setPos(posStart, true);
            return;

        case 35: // '#'
            if(flag && bEnable)
                setPos((posStart + posCount) - 1, true);
            return;

        case 38: // '&'
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
        if(f < xM)
        {
            if(flag)
                setPos(pos - 1, true);
        } else
        if(f > xM + dxM)
        {
            if(flag)
                setPos(pos + 1, true);
        } else
        if(flag)
        {
            mouseCapture(true);
            posSlidingSave = pos;
            mouseSlidingX = f;
        }
    }

    public void mouseMove(float f, float f1)
    {
        if(bEnable && isMouseCaptured())
            setPos((int)(((f - mouseSlidingX) / win.dx) * (float)posCount + (float)posSlidingSave + 0.5F), bSlidingNotify);
    }

    public void render()
    {
        lookAndFeel().render(this);
    }

    public void resized()
    {
        checkRange();
        lookAndFeel().setupHSliderIntSizes(this);
    }

    public void created()
    {
        super.created();
        resized();
    }

    public GWindowHSliderInt(com.maddox.gwindow.GWindow gwindow)
    {
        posStart = 0;
        posCount = 8;
        pos = posStart + posCount / 2;
        bSlidingNotify = false;
        doNew(gwindow, 0.0F, 0.0F, 1.0F, 1.0F, false);
    }

    public GWindowHSliderInt(com.maddox.gwindow.GWindow gwindow, int i, int j, int k, float f, float f1, float f2)
    {
        posStart = 0;
        posCount = 8;
        pos = posStart + posCount / 2;
        bSlidingNotify = false;
        posStart = i;
        posCount = j;
        pos = k;
        float f3 = gwindow.lookAndFeel().getHSliderIntH() / gwindow.lookAndFeel().metric();
        doNew(gwindow, f, f1, f2, f3, true);
    }

    public int posStart;
    public int posCount;
    public int pos;
    public boolean posEnable[];
    public boolean bSlidingNotify;
    private int posSlidingSave;
    private float mouseSlidingX;
    public float xM;
    public float dxM;
}
