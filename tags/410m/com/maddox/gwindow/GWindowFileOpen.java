// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames 
// Source File Name:   GWindowFileOpen.java

package com.maddox.gwindow;


// Referenced classes of package com.maddox.gwindow:
//            GWindowFileBox, GWindow, GWindowLookAndFeel, GFileFilter

public class GWindowFileOpen extends com.maddox.gwindow.GWindowFileBox
{

    public GWindowFileOpen(com.maddox.gwindow.GWindow gwindow, boolean flag, java.lang.String s, com.maddox.gwindow.GFileFilter agfilefilter[])
    {
        super(gwindow, flag, gwindow.lAF().i18n("Open_file"), s, agfilefilter);
    }

    public GWindowFileOpen(com.maddox.gwindow.GWindow gwindow, boolean flag, java.lang.String s, java.lang.String s1, com.maddox.gwindow.GFileFilter agfilefilter[])
    {
        super(gwindow, flag, s, s1, agfilefilter);
    }
}
