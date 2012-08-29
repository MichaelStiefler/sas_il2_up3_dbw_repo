// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GWindowFileBoxExec.java

package com.maddox.gwindow;


// Referenced classes of package com.maddox.gwindow:
//            GWindowFileBox

public abstract class GWindowFileBoxExec
{

    public GWindowFileBoxExec()
    {
    }

    public boolean isCloseBox()
    {
        return true;
    }

    public boolean isChangedBox()
    {
        return false;
    }

    public boolean isReturnResult()
    {
        return true;
    }

    public void exec(com.maddox.gwindow.GWindowFileBox gwindowfilebox, java.lang.String s)
    {
        gwindowfilebox.endExec();
    }
}
