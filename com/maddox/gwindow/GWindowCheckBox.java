// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GWindowCheckBox.java

package com.maddox.gwindow;


// Referenced classes of package com.maddox.gwindow:
//            GWindowDialogControl, GWindowLookAndFeel, GSize, GRegion, 
//            GWindow

public class GWindowCheckBox extends com.maddox.gwindow.GWindowDialogControl
{

    public boolean isChecked()
    {
        return bChecked;
    }

    public void setChecked(boolean flag, boolean flag1)
    {
        if(bChecked == flag)
            return;
        if(flag1)
            _notify(2, 0);
        else
            bChecked = flag;
    }

    public boolean _notify(int i, int j)
    {
        if(i == 2)
            bChecked = !bChecked;
        return notify(i, j);
    }

    public boolean notify(int i, int j)
    {
        if(i == 2)
            lAF().soundPlay("clickCheckBox");
        return super.notify(i, j);
    }

    public void render()
    {
        lookAndFeel().render(this);
    }

    public com.maddox.gwindow.GSize getMinSize(com.maddox.gwindow.GSize gsize)
    {
        return lookAndFeel().getMinSize(this, gsize);
    }

    public void resolutionChanged()
    {
        _setSize();
    }

    private void _setSize()
    {
        com.maddox.gwindow.GSize gsize = getMinSize();
        win.dx = gsize.dx;
        win.dy = gsize.dy;
        if(metricWin != null)
        {
            metricWin.dx = win.dx / lookAndFeel().metric();
            metricWin.dy = win.dy / lookAndFeel().metric();
        }
    }

    public void created()
    {
        super.created();
        _setSize();
    }

    public GWindowCheckBox()
    {
        bChecked = false;
    }

    public GWindowCheckBox(com.maddox.gwindow.GWindow gwindow, float f, float f1, java.lang.String s)
    {
        bChecked = false;
        toolTip = s;
        align = 0;
        doNew(gwindow, f, f1, 1.0F, 1.0F, true);
    }

    public boolean bChecked;
}
